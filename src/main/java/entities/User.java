package entities;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private  Role role;


        public User(Builder builder) {
            this.id = builder.id;
            this.login = builder.login;
            this.password = builder.password;
            this.role = builder.role;

        }


        public static Builder builder() {
            return new Builder();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public Role getRole() {
            return role;
        }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }


    public static class Builder {
            private int id;
            private String login;
            private String password;
            private Role role;

            public Builder id(int id) {
                this.id = id;
                return this;
            }

            public Builder login(String login) {
                this.login = login;
                return this;
            }

            public Builder password(String password) {
                this.password = password;
                return this;
            }

            public Builder role(Role role) {
                this.role = role;
                return this;
            }

            public User build() {
                return new User(this);
            }
        }

    }
