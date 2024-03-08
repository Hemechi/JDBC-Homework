package service;

import model.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class ServiceImpl implements Service {
    private static final UserRepository repository = new UserRepositoryImpl();
    @Override
    public List<User> getAllUsers() {
        return repository.fetchAllUsers();
    }

    @Override
    public Optional<User> searchByID(Integer id) {
        return repository.searchByID(id);
    }
    @Override
    public int createUser(User user) {
        return repository.createUser(user);
    }
    @Override
    public int deleteUserById(int id) {
        return repository.deleteUser(id);
    }
    @Override
    public int updateUserById(int id, User user) {
        return repository.updateUser(id,user);
    }

}
