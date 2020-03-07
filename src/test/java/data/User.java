package data;

import lombok.Data;

@Data
public class User {
    String login;
    String password;
    String repeatPassword;
    String name;
    String email;
    String fullName;
    String jabber;
}
