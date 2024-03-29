package web.form;

import java.util.Objects;

public class UserForm {
    String login;
    String password;

    public UserForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForm userForm = (UserForm) o;
        return Objects.equals(login, userForm.login) && Objects.equals(password, userForm.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
