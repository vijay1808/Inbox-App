package org.inbox.emaillist;

import java.util.List;

import org.inbox.folders.Folder;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "messages_by_user_folder")
public class EmailListItem {

	/*
	 * EmailListItemKey as entity reference because when a repository is extended by
	 * CassandraRepository<Type, PrimaryKey> the value for primary key can be of single type only.
	 * If we have multiple primary keys in our cassandra table, we have to group them as an entity
	 * and pass the entity as a reference key.
	 */ 
	@PrimaryKey
	private EmailListItemKey key;

	@CassandraType(type = Name.TEXT)
	private String subject;

	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;

	@CassandraType(type = Name.BOOLEAN)
	private Boolean isUnRead;

	@Transient
	private String agoTimeStr;
	
	public EmailListItemKey getKey() {
		return key;
	}

	public void setKey(EmailListItemKey key) {
		this.key = key;
	}

	public String getAgoTimeStr() {
		return agoTimeStr;
	}

	public void setAgoTimeStr(String agoTimeStr) {
		this.agoTimeStr = agoTimeStr;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public Boolean getIsUnRead() {
		return isUnRead;
	}

	public void setIsUnRead(Boolean isUnRead) {
		this.isUnRead = isUnRead;
	}

}
