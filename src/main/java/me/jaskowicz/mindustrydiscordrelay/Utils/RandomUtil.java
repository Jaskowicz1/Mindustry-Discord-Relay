package me.jaskowicz.mindustrydiscordrelay.Utils;

import me.jaskowicz.mindustrydiscordrelay.Main;

import java.util.Random;

public class RandomUtil {

    public RandomUtil() {
    }

    public static User getRandomUser() {
        Random generator = new Random();
        Object[] users = Main.USERS.values().toArray();
        return (User) users[generator.nextInt(users.length)];
    }

    public static User getRandomUserNotSelf(User u) {
        Random generator = new Random();
        Object[] users = Main.USERS.values().toArray();
        User user = (User) users[generator.nextInt(users.length)];

        while(user == u) {
            user = (User) users[generator.nextInt(users.length)];
        }

        return user;
    }

    public static Object getRandomObject(Object[] objects) {
        Random generator = new Random();
        return objects[generator.nextInt(objects.length)];
    }
}
