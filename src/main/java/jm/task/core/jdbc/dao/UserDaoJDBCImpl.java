package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(50) NOT NULL, "
                + "lastname VARCHAR(50) NOT NULL, "
                + "age TINYINT NOT NULL"
                + ");";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица users успешно создана или уже существует");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(dropTableSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица users успешно удалена, если она существовала");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUsersSQL = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUsersSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + name + " " + lastName + " успешно сохранен.");
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String deleteUsersSQL = "DELETE FROM users WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUsersSQL)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь с id " + id + " успешно удален");
            } else {
                System.err.println("Пользователь с id " + id + " не найден");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanUsersSQL = "DELETE FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(cleanUsersSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица пользователей очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы пользователей: " + e.getMessage());
        }
    }
}
