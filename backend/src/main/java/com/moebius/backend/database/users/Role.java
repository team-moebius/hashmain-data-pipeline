package com.moebius.backend.database.users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@Document(collection = "roles")
public class Role implements GrantedAuthority {
	@Id
	private String id;

	@Override
	public String getAuthority() {
		return id;
	}
}
