package service;

import model.User;

import java.util.List;
import java.util.Optional;

public interface Service {

    List<User> getAllUsers();
    Optional<User> searchByID(Integer id);
    int createUser(User user);
    int deleteUserById(int id);
    int updateUserById(int id, User user);
}
