package repository;

import model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAllUsers();
    void createUser(User user);
    void deleteUser(Integer userId);
    User updateUser(User user);
    Optional<User> searchByID(Integer id);
}
