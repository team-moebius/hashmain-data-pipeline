package com.moebius.backend.database.users;

import com.moebius.backend.database.Base;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
public class User extends Base {
	@Id
	private ObjectId id;
	private boolean isAdmin;
	private String name;
	private String email;
	private String password;
	private String authToken;
}
