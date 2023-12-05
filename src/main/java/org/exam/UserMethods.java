package org.exam;

public class UserMethods {
    User user = new User();

    private final String email = "oppenheimer@yandex.ru";
    private final String password = "QAtest123";
    private final String name = "Robert";

    public User getUser(){
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        return user;
    }

    public User getUserWithoutEmail(){
        user.setPassword(password);
        user.setName(name);
        return user;
    }

    public User getUserWithoutPassword(){
        user.setEmail(email);
        user.setName(name);
        return user;
    }

    public User getUserWithoutName(){
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public User loginUser(){
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public User loginUserWithWrongEmail(){
        String wrongEmail = "@yandex.ru";
        user.setEmail(wrongEmail);
        user.setPassword(password);
        return user;
    }

    public User loginUserWithWrongPassword(){
        String wrongPassword = "pass";
        user.setEmail(email);
        user.setPassword(wrongPassword);
        return user;
    }
}