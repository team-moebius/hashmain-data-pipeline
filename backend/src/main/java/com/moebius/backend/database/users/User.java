package com.moebius.backend.database.users;

import com.moebius.backend.database.Base;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
public class User extends Base {

}
