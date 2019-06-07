package com.moebius.backend.domain.members;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "roles")
@EqualsAndHashCode
@AllArgsConstructor
public class Role implements GrantedAuthority {
	@Setter
	private String name;

	@Override
	public String getAuthority() {
		return name;
	}
}
