package view;

import controller.UserController;
import java.util.Locale;
import java.util.Scanner;

public class ViewUI {
    private final UserController userController = new UserController();

    private void displayMenu() {
        System.out.println("+".repeat(20) + " CRUD OPERATIONS " + "+".repeat(20));
        System.out.println("1. Get All Users".toUpperCase(Locale.ROOT));
        System.out.println("2. Create User".toUpperCase(Locale.ROOT));
        System.out.println("3. Delete User by ID".toUpperCase(Locale.ROOT));
        System.out.println("4. Update User by ID".toUpperCase(Locale.ROOT));
        System.out.println("5. Search User by ID".toUpperCase(Locale.ROOT));
        System.out.println("6. Exit Program".toUpperCase(Locale.ROOT));
        System.out.println("+".repeat(55));
    }

    private int getUserOption() {
        displayMenu();
        System.out.print("> Enter Option (1-6): ");
        return new Scanner(System.in).nextInt();
    }

    public void runUI() {
        while (true) {
            switch (getUserOption()) {
                case 1 -> userController.getAllUsers();
                case 2 -> userController.addUser();
                case 3 -> userController.deleteUserById();
                case 4 -> userController.updateUserById();
                case 5 -> userController.searchUserById();
                case 6 -> {
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Option!!!");
            }
        }
    }
}
