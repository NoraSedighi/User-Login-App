package com.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("[L]ogin, [S]ign up, [D]elete: ");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("s") || input.equals("sign up")) {
            signUp(scanner);
        } else if (input.equals("l") || input.equals("login")) {
            login(scanner);
        } else if (input.equals("d") || input.equals("delete")) {

        } else {
            System.out.println("Invalid option. Exiting.");
        }

        scanner.close();
        HibernateUtil.getSessionFactory().close();
    }

    private static void signUp(Scanner scanner) {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Email: ");
        String email = scanner.nextLine();

        String password;
        while (true) {
            System.out.print("Password: ");
            password = scanner.nextLine();

            if (password.length() >= 8) {
                break;
            } else {
                System.out.println("Weak password");
                System.out.println("Please try again");
            }
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        User existingUser = (User) session
                .createQuery("FROM User WHERE email = :email")
                .setParameter("email", email)
                .uniqueResult();

        if (existingUser != null) {
            System.out.println("An account with this email already exists");
            tx.rollback();
            session.close();
            return;
        }

        try {
            User newUser = new User(firstName, lastName, age, email, password);
            session.persist(newUser);
            tx.commit();
            System.out.println("Account created successfully!");
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error while saving user: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            User user = (User) session
                    .createQuery("FROM User WHERE email = :email AND password = :password")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();

            if (user != null) {
                System.out.println("Welcome, " + user.getFirst_name() + " " + user.getLast_name() + "!");
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private static void deleteUser(Scanner scn) {
        System.out.println("Enter email of the user to delete: ");
        String email = scn.nextLine();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            User user = session.get(User.class, email);
            if (user != null) {
                session.remove(user);
                tx.commit();
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
                tx.rollback();
            }
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error while deleting user: " + e.getMessage());
        } finally {
            session.close();
        }
    }

}
