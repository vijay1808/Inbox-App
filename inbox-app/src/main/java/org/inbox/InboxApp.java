
package org.inbox;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

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

@SpringBootApplication
@RestController
public class InboxApp {

	
	@Autowired
	FolderRepository folderRepository;
	
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
		
		folderRepository.save(new Folder("vijay1808","inbox","blue"));
		folderRepository.save(new Folder("vijay1808","sent","green"));
		folderRepository.save(new Folder("vijay1808","important","red"));
		
	}
	
	
}
