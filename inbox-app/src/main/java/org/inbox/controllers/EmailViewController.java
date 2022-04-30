package org.inbox.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.inbox.email.Email.Email;
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
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EmailViewController {

	@Autowired
	private FolderRepository folderRepository;
	@Autowired
	private FolderService service;

	@Autowired
	private EmailRepository emailRepository;

	@GetMapping(value = "/emails/{id}")
	public String emailView(@AuthenticationPrincipal OAuth2User principal, Model model, @PathVariable UUID id) {
 
		System.out.println("pincipal " + principal);
		if (null == principal || !StringUtils.hasText(principal.getAttribute("name"))) {
			return "index";
		}

		String userId = principal.getAttribute("login");
		List<Folder> userFolders = folderRepository.findAllById(userId);
		List<Folder> defaultFolders = service.defaultFolder(userId);

		System.out.println("Folder " + userFolders.toString());
		System.out.println("User Folder " + defaultFolders.toString());

		model.addAttribute("userFolders", userFolders);
		model.addAttribute("defaultFolders", defaultFolders);
		
		System.out.println("before email "+id);

		Optional<Email> emails = emailRepository.findById(id);
		System.out.println("email "+emails);
		if (!emails.isPresent())
			return "inbox-page";
		
		String toList=String.join(", ", emails.get().getTo());
		model.addAttribute("email", emails.get());
		model.addAttribute("toList", toList);

		return "email-page";
	}

}
