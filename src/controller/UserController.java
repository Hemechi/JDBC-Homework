package controller;

import model.User;
import service.Service;
import service.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

public class UserController {
    private static final Service service = new ServiceImpl();

    public void getAllUsers() {
        List<User> users = service.getAllUsers().stream()
                .filter(user -> !user.getIs_deleted())
                .toList();

        for (User user : users) {
            System.out.println("User Id: " + user.getUser_id());
            System.out.println("User UUID: " + user.getUser_uuid());
            System.out.println("User Name: " + user.getUser_name());
            System.out.println("User Email: " + user.getUser_email());
            System.out.println("User Password: " + user.getUser_password());
            System.out.println("Delete Status: " + user.getIs_deleted());
            System.out.println("Verification Status: " + user.getIs_verified());
            System.out.println("=".repeat(50));
        }
    }

    public void addUser() {
        System.out.println("> Insert user info".toUpperCase(Locale.ROOT));
        System.out.print("> User's name: ");
        String name = new Scanner(System.in).nextLine();
        System.out.print("> User's email: ");
        String email = new Scanner(System.in).nextLine();
        // Generate UUID for user
        String userUuid = UUID.randomUUID().toString();
        // Generate random password for user
        String password = UUID.randomUUID() + "@$#%^";
        // Add user and display the number of rows affected
        int rowsAffected = service.createUser(new User(null, false, name, email, password, userUuid, false));
        System.out.println("Row(s) affected: " + rowsAffected);
        System.out.println("> User added".toUpperCase(Locale.ROOT));
    }

    public void deleteUserById() {
        System.out.print("> Insert User ID: ");
        Integer userId = new Scanner(System.in).nextInt();
        // Check if the user with the given ID exists
        boolean userExists = service.getAllUsers().stream()
                .anyMatch(user -> user.getUser_id().equals(userId));
        if (userExists) {
            int rowsAffected = service.deleteUserById(userId);
            System.out.println("Row affected: " + rowsAffected);
        } else {
            System.out.println("User with ID " + userId + " does not exist.");
        }
    }

    public void updateUserById() {
        System.out.print("> Insert user ID: ");
        int id = new Scanner(System.in).nextInt();
        boolean userExists = service.getAllUsers().stream()
                .anyMatch(user -> user.getUser_id().equals(id));
        if (userExists) {
            System.out.print("> New User Name : ");
            String name = new Scanner(System.in).nextLine();
            System.out.print("> New User Email : ");
            String email = new Scanner(System.in).nextLine();
            int rowsAffected = service.updateUserById(id,
                    new User(id, false , name, email, UUID.randomUUID() + "@$#%^", UUID.randomUUID().toString(), false)
            );
            System.out.println("Row affected: " + rowsAffected);
        } else {
            System.out.println("User with ID " + id + " does not exist.");
        }
    }
    public void searchUserById() {
        System.out.print("> Enter User ID: ");
        Integer userId = new Scanner(System.in).nextInt();

        Optional<User> userOptional = service.searchByID(userId);

        userOptional.ifPresentOrElse(
                user -> {
                    System.out.println("User found:");
                    System.out.println("ID: " + user.getUser_id());
                    System.out.println("Name: " + user.getUser_name());
                    System.out.println("Email: " + user.getUser_email());
                    // Print other user details if needed
                },
                () -> System.out.println("User with ID " + userId + " not found.")
        );
    }
}
