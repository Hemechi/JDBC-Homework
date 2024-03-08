import model.User;
import repository.PropertiesLoader;
import repository.UserRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> fetchAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        PropertiesLoader.loadingProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserEmail(resultSet.getString("user_email"));
                user.setUserPassword(resultSet.getString("user_password"));
                user.setIsDeleted(resultSet.getBoolean("is_deleted"));
                user.setIsVerified(resultSet.getBoolean("is_verified"));
                user.setUuid(resultSet.getString("user_uuid"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO users (user_name, user_email, is_deleted, user_password, is_verified, user_uuid) VALUES (?,?,?,?,?,?)";
        PropertiesLoader.loadProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserEmail());
            preparedStatement.setBoolean(3, user.getIsDeleted());
            preparedStatement.setString(4, user.getUserPassword());
            preparedStatement.setBoolean(5, user.getIsVerified());
            preparedStatement.setString(6, user.getUuid());

            int rowInserted = preparedStatement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("User created successfully");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteUser(Integer userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        PropertiesLoader.loadingProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
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
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET user_name = ?, user_email = ?, is_deleted = ?, user_password = ?, is_verified = ?, user_uuid = ? WHERE user_id = ?";
        PropertiesLoader.loadingProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserEmail());
            preparedStatement.setBoolean(3, user.getIsDeleted());
            preparedStatement.setString(4, user.getUserPassword());
            preparedStatement.setBoolean(5, user.getIsVerified());
            preparedStatement.setString(6, user.getUuid());
            preparedStatement.setInt(7, user.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User updated successfully");
                return user;
            } else {
                System.out.println("User not found");
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<User> searchByID(Integer userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        PropertiesLoader.loadingProperties();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserEmail(resultSet.getString("user_email"));
                user.setIsDeleted(resultSet.getBoolean("is_deleted"));
                user.setUserPassword(resultSet.getString("user_password"));
                user.setIsVerified(resultSet.getBoolean("is_verified"));
                user.setUuid(resultSet.getString("user_uuid"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
