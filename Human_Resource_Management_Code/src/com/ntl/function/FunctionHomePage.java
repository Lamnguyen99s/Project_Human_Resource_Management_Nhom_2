package com.ntl.function;

import com.ntl.database.JdbcData;
import com.ntl.entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;


public class FunctionHomePage {

    // check input integer
    private int checkInputInt(Scanner scanner) {
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
        out.print("Username: ");    // Dunglamabc, admin
        String userName = scanner.nextLine();
        out.print("Password: ");    // abc@123, admin
        String password = scanner.nextLine();

        JdbcData jdbcData = new JdbcData();
        List<Admin> adminList = new ArrayList<>();


        try {
            Statement statement = jdbcData.getStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM admin");
            while (resultSet.next()){
                adminList.add(new Admin(resultSet.getString("full_name"),
                                resultSet.getString("username"),
                                resultSet.getString("password")));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            boolean check = false;
            for (Admin a: adminList) {
                if (userName.equals(a.getUsername()) && password.equals(a.getPassword())) {
                    check = true;
                    out.println("Login successfully!");
                    out.println("Hello " + a.getFullName() );
                    break;
                }
            }

            if(check == true) {
                break;
            } else {
                out.println("Need to enter the correct administrator account !");
                out.println("------ Human Resource Management -------");
                out.println("                Login                   ");
                out.print("Username: ");    // Dunglamabc, admin
                userName = scanner.nextLine();
                out.print("Password: ");    // abc@123, admin
                password = scanner.nextLine();
            }
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
