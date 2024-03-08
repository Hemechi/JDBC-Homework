package repository;

import model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> fetchAllUsers();
    int createUser(User user);
    int deleteUser(Integer userId);
    User updateUser(Integer id,User user);
    Optional<User> searchByID(Integer id);
}
