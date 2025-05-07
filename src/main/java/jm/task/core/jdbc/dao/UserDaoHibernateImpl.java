package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age TINYINT)";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users успешно создана или уже существует");
        } catch (Exception e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(dropTableSQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица пользователей удалена, если она существовала");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("Пользователь " + name + " " + lastName + " успешно сохранён");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                System.out.println("Пользователь с id " + id + " удалён.");
            } else {
                System.err.println("Пользователь с id " + id + " не найден");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("from User").getResultList();
            System.out.println("Список всех пользователей загружен.");
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
            users = List.of();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица пользователей очищена.");
        } catch (Exception e) {
            System.err.println("Ошибка при очистке таблицы пользователей: " + e.getMessage());
        }
    }
}
