package repository;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> fetchAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        PropertiesLoader.loadProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.PROPERTIES.getProperty("database_url"),
                PropertiesLoader.PROPERTIES.getProperty("database_username"),
                PropertiesLoader.PROPERTIES.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setUser_email(resultSet.getString("user_email"));
                user.setUser_password(resultSet.getString("user_password"));
                user.setIs_deleted(resultSet.getBoolean("is_deleted"));
                user.setIs_verified(resultSet.getBoolean("is_verified"));
                user.setUser_uuid(resultSet.getString("user_uuid"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public int createUser(User user) {
        String sql = "INSERT INTO users (user_name, user_email, is_deleted, user_password, is_verified, user_uuid) VALUES (?,?,?,?,?,?)";
        PropertiesLoader.loadProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.PROPERTIES.getProperty("database_url"),
                PropertiesLoader.PROPERTIES.getProperty("database_username"),
                PropertiesLoader.PROPERTIES.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUser_name());
            preparedStatement.setString(2, user.getUser_email());
            preparedStatement.setBoolean(3, user.getIs_deleted());
            preparedStatement.setString(4, user.getUser_password());
            preparedStatement.setBoolean(5, user.getIs_verified());
            preparedStatement.setString(6, user.getUser_uuid());

            int rowInserted = preparedStatement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("User created successfully");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteUser(Integer userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        PropertiesLoader.loadProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.PROPERTIES.getProperty("database_url"),
                PropertiesLoader.PROPERTIES.getProperty("database_username"),
                PropertiesLoader.PROPERTIES.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("User not found in database");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateUser(int id, User user) {
        PropertiesLoader.loadProperties();

        String sql = "UPDATE users SET user_uuid = ?, user_name = ?, user_email = ?, user_password = ?, is_deleted = ?, is_verified = ? WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.PROPERTIES.getProperty("database_url"),
                PropertiesLoader.PROPERTIES.getProperty("database_username"),
                PropertiesLoader.PROPERTIES.getProperty("database_password")
        );
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            System.out.println("Connected to database");

            preparedStatement.setString(1, user.getUser_uuid());
            preparedStatement.setString(2, user.getUser_name());
            preparedStatement.setString(3, user.getUser_email());
            preparedStatement.setString(4, user.getUser_password());
            preparedStatement.setBoolean(5, user.getIs_deleted());
            preparedStatement.setBoolean(6, user.getIs_verified());
            preparedStatement.setInt(7, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Problem during updating data in database: " + sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public Optional<User> searchByID(Integer userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        PropertiesLoader.loadProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.PROPERTIES.getProperty("database_url"),
                PropertiesLoader.PROPERTIES.getProperty("database_username"),
                PropertiesLoader.PROPERTIES.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setUser_email(resultSet.getString("user_email"));
                user.setIs_deleted(resultSet.getBoolean("is_deleted"));
                user.setUser_password(resultSet.getString("user_password"));
                user.setIs_verified(resultSet.getBoolean("is_verified"));
                user.setUser_uuid(resultSet.getString("user_uuid"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
