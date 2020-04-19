package data;

import java.util.Objects;


public class User {
    String login;
    String password;
    String repeatPassword;
    String email;
    String fullName;
    String jabber;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJabber() {
        return jabber;
    }

    public void setJabber(String jabber) {
        this.jabber = jabber;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", jabber='" + jabber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
//        if (user.getFullName() == null) {
//            user.setFullName(user.getLogin()); //if user full name is not defined, then login name is used instead
//        }
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
