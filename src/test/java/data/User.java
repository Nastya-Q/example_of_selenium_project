package data;

import lombok.Data;

import java.util.Objects;

@Data
public class User {
    String login;
    String password;
    String repeatPassword;
    String email;
    String fullName;
    String jabber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(jabber, user.jabber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, fullName, jabber);
    }
}
