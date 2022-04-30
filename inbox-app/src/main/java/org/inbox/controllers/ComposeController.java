package org.inbox.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.inbox.email.Email.EmailRepository;
import org.inbox.folders.Folder;
import org.inbox.folders.FolderRepository;
import org.inbox.folders.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComposeController {

	@Autowired
	private FolderRepository folderRepository;
	@Autowired
	private FolderService service;

	@GetMapping(value = "/compose")
	public String getComposePage(@RequestParam(required = false) String to,
			@AuthenticationPrincipal OAuth2User principal, Model model) {

		if (null == principal || !StringUtils.hasText(principal.getAttribute("name"))) {
			return "index";
		}

		String userId = principal.getAttribute("login");
		List<Folder> userFolders = folderRepository.findAllById(userId);
		List<Folder> defaultFolders = service.defaultFolder(userId);

		model.addAttribute("userFolders", userFolders);
		model.addAttribute("defaultFolders", defaultFolders);

		String[] toIdsSplt = to.split(",");
		List<String> toList = Arrays.asList(toIdsSplt).stream().map(id -> StringUtils.trimWhitespace(id)).filter(id -> StringUtils.hasText(id)).distinct().collect(Collectors.toList());
	
		model.addAttribute("to",String.join(", ", toList));		
		return "compose-page";

	}

}
