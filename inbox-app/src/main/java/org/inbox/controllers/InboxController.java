/**
 * 
 */
package org.inbox.controllers;

import java.util.List;

import org.inbox.folders.Folder;
import org.inbox.folders.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author vijay
 *
 */

@Controller
public class InboxController {

	@Autowired
	private FolderRepository folderRepository;

	@GetMapping(value = "/")
	public String homePage(@AuthenticationPrincipal OAuth2User principal, Model model) {

		System.out.println("pincipal " + principal);
		if (null == principal || !StringUtils.hasText(principal.getAttribute("name"))) {
			return "index";
		}

		String userId = principal.getAttribute("login");

		List<Folder> userFolders = folderRepository.findAllById(userId);
		System.out.println("Folder " + userFolders.toString());
		model.addAttribute("userFolders", userFolders);
		return "inbox-page";

	}

	/*
	 * @GetMapping(value="/") public String homePage(@AuthenticationPrincipal
	 * OAuth2User principal) {
	 * 
	 * System.out.println("pincipal "+principal); if(null==principal ||
	 * !StringUtils.hasText(principal.getAttribute("name"))) { return "index"; }
	 * else return "inbox-page"; }
	 */

}
