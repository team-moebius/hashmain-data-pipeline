package com.moebius.backend.domain.members;

import com.moebius.backend.domain.commons.Base;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "members")
@EqualsAndHashCode(callSuper = true)
public class Member extends Base {
	@Id
	private ObjectId id;
	@Nullable
	private Level level;
	private String name;
	@Email
	@Indexed(unique = true)
	private String email;
	private String password;

	private boolean active = false;
	@Nullable
	private String verificationCode;

	private Set<Role> roles = new HashSet<>();
}