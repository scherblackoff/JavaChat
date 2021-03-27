package server.services;

import server.services.AuthService;

import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {

    private class UserData{
        String login;
        String password;
        String username;

        public UserData(String login, String password, String username) {
            this.login = login;
            this.password = password;
            this.username = username;
        }
    }

    private List<UserData> users;

    public SimpleAuthService() {
        this.users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            users.add(new UserData(
                    "user" + i,
                    "password" + i,
                    "nick" + i
            ));
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (UserData user : users) {
            if (login.equals(user.login) && password.equals(user.password)) {
                return user.username;
            }
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        for (UserData o:users ) {
            if(o.login.equals(login)) {
                return false;
            }
        }
        users.add(new UserData(login, password, nickname));
        return true;
    }
}
