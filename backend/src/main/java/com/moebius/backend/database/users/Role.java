package com.moebius.backend.database.users;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Builder
@Document(collection = "roles")
@EqualsAndHashCode
public class Role implements GrantedAuthority {
	@Id
	private String id;

	@Override
	public String getAuthority() {
		return id;
	}
}
