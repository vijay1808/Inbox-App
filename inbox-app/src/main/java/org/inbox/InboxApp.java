
package org.inbox;

import java.nio.file.Path;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.inbox.emaillist.EmailListItem;
import org.inbox.emaillist.EmailListItemKey;
import org.inbox.emaillist.EmailListItemRepository;
import org.inbox.folders.Folder;
import org.inbox.folders.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.oss.driver.api.core.uuid.Uuids;

@SpringBootApplication
@RestController
public class InboxApp {

	@Autowired
	FolderRepository folderRepository;

	@Autowired
	EmailListItemRepository emailListItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(InboxApp.class, args);
	}

	@RequestMapping("/user")
	public String user(@AuthenticationPrincipal OAuth2User principal) {
		System.out.println(principal);
		return principal.getAttribute("name");
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {

		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return a -> a.withCloudSecureConnectBundle(bundle);

	}

	@PostConstruct
	public void init() {

		folderRepository.save(new Folder("vijay1808", "inbox", "blue"));
		folderRepository.save(new Folder("vijay1808", "sent", "green"));
		folderRepository.save(new Folder("vijay1808", "important", "red"));

		for (int i = 0; i < 10; i++) {

			EmailListItemKey key = new EmailListItemKey();
			key.setId("vijay1808");
			key.setLabel("inbox");
			key.setTimeUUID(Uuids.timeBased());

			EmailListItem emailListItem = new EmailListItem();
			emailListItem.setKey(key);
			emailListItem.setIsUnRead(true);
			emailListItem.setSubject("Subject is:" + i);
			emailListItem.setTo(Arrays.asList("vijay1808"));
			
			emailListItemRepository.save(emailListItem);
		}

	}

}
