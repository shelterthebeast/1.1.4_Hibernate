package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        //JDBC

        userService.createUsersTable();

        userService.saveUser("John", "Doe", (byte) 30);
        userService.saveUser("Jane", "Smith", (byte) 25);
        userService.saveUser("Mike", "Johnson", (byte) 40);
        userService.saveUser("Emily", "Davis", (byte) 35);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();

        //HIBERNATE

        userService.createUsersTable();

        userService.saveUser("John", "Doe", (byte) 30);
        userService.saveUser("Jane", "Smith", (byte) 25);
        userService.saveUser("Mike", "Johnson", (byte) 40);
        userService.saveUser("Emily", "Davis", (byte) 35);

        userService.getAllUsers().forEach(System.out::println);

        userService.removeUserById(2);

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
