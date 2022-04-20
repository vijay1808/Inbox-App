/**
 * 
 */
package org.inbox.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.inbox.emaillist.EmailListItem;
import org.inbox.emaillist.EmailListItemRepository;
import org.inbox.folders.Folder;
import org.inbox.folders.FolderRepository;
import org.inbox.folders.FolderService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.datastax.oss.driver.api.core.uuid.Uuids;

/**
 * @author vijay
 *
 */

@Controller
public class InboxController {

	@Autowired
	private FolderRepository folderRepository;
	@Autowired
	private FolderService service;
	
	@Autowired
	private EmailListItemRepository emailListItemRepository;
	
	@GetMapping(value = "/")
	public String homePage(@AuthenticationPrincipal OAuth2User principal, Model model) {

		System.out.println("pincipal " + principal);
		if (null == principal || !StringUtils.hasText(principal.getAttribute("name"))) {
			return "index";
		}

		String userId = principal.getAttribute("login");

		List<Folder> userFolders = folderRepository.findAllById(userId);
		List<Folder> defaultFolders=service.defaultFolder(userId);
		
		System.out.println("Folder " + userFolders.toString());
		System.out.println("User Folder " + defaultFolders.toString());

		model.addAttribute("userFolders", userFolders);
		model.addAttribute("defaultFolders", defaultFolders);
		
		String label="inbox";
		List<EmailListItem> emailListItem=emailListItemRepository.findAllByKey_IdAndKey_Label(userId, label);	
		
		PrettyTime p= new PrettyTime();
		emailListItem.stream().forEach(emailItem->{
			UUID  uuisTimeStr= emailItem.getKey().getTimeUUID();
			Date dateTime=new Date(Uuids.unixTimestamp(uuisTimeStr));
			emailItem.setAgoTimeStr(p.format(dateTime));
		});
		
		model.addAttribute("emailListItem", emailListItem);
		System.out.println("emailListItem "+emailListItem.toString());
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
