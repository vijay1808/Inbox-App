package org.inbox.folders;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FolderService {
	
	public  List<Folder> defaultFolder(String userId) {

		return Arrays.asList(new Folder(userId, "inbox", "blue"), new Folder(userId, "important", "red"),
				new Folder(userId, "sent", "green"));

	}

}
