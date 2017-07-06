package fraktalis.androidtestapp.service;

/**
 * Created by vincentale on 06/07/17.
 */

public class LoginHandler implements LoginHandlerInterface {

    private static String username = "Toto";
    private static String password = "781227";

    public boolean log(String username, String password) {
        return (username.equals(LoginHandler.username) && password.equals(LoginHandler.password));
    }
}
