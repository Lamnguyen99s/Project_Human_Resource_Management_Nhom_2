package com.ntl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class FunctionHomePage {
    // check input integer
    public static int checkInputInt(Scanner scanner) {
        scanner = new Scanner(in);
        while (true) {
            try {
                int result = Integer.parseInt(scanner.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                out.println("Must be input integer!");
                out.print("Enter again: ");
            }
        }
    }

    // login admin
    public void loginAdmin() {
        Scanner scanner = new Scanner(in);
        out.println("------ Human Resource Management -------");
        out.println("                Login                   ");
        out.print("Full Name: ");   // Dung Lam
        String fullName = scanner.nextLine();
        out.print("Username: ");    // Dunglamabc
        String userName = scanner.nextLine();
        out.print("Password: ");    // abc@123
        String password = scanner.nextLine();

        JdbcAdmin jdbcAdmin = new JdbcAdmin();
        ResultSet resultSet = null;
        try {
            // check login admin
            resultSet = jdbcAdmin.checkAdmin();

            if(resultSet.next()){
                while (!fullName.equals(resultSet.getString("full_name")) ||
                        !userName.equals(resultSet.getString("username")) ||
                        ! password.equals(resultSet.getString("password"))) {
                    out.println("Need to enter the correct administrator account !");

                    out.println("------ Human Resource Management -------");
                    out.println("                Login                   ");
                    out.print("Full Name: ");   // Dung Lam
                    fullName = scanner.nextLine();
                    out.print("Username: ");    // Dunglamabc
                    userName = scanner.nextLine();
                    out.print("Password: ");    // abc@123
                    password = scanner.nextLine();
                }

                out.println("Login successfully!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // home page
    public int homePage() {
        out.println("------ Human Resource Management -------");
        out.println("                    Home                ");
        Scanner scanner1 = new Scanner(in);
        out.println("1. Employee");
        out.println("2. Department");
        out.println("3. Statistics");
        out.println("4. Exit");
        out.println("----------------------------------------");
        out.print("Enter Option Number 1 - 4: ");
        int h = checkInputInt(scanner1);
        while (h < 1 || h > 4) {
            out.println("Must be input the option 1 - 4!");
            out.print("Enter again: ");
            h = checkInputInt(scanner1);
        }
        return h;
    }
}
