package com.ntl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program {
    // check input integer
    public static int checkInputInt(Scanner scanner) {
        scanner = new Scanner(System.in);
        while (true) {
            try {
                int result = Integer.parseInt(scanner.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                System.out.println("Must be input integer!");
                System.out.print("Enter again: ");
            }
        }
    }

    // employee
    public static void employee (int emp) {
        JdbcEmployee jdbcEmployee = new JdbcEmployee();

        if (emp == 1) {
            System.out.println("            Show All Employee");
            ResultSet resultSet = null;
            try {
                resultSet = jdbcEmployee.getDataEmployee();
                System.out.printf("%-3s %-15s %-20s %-20s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Id",
                        "Employee Code", "Full Name", "Position", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");

                if (!resultSet.isBeforeFirst()) {
                    System.out.println("----- No Data -----");
                } else {
                    while (resultSet.next()) {
                        int id1 = resultSet.getInt("id");
                        String employeeCode = resultSet.getString("employee_code");
                        String fullName = resultSet.getString("full_name");
                        String position = resultSet.getString("position");
                        String age = resultSet.getString("age");
                        String phone = resultSet.getString("phone");
                        String email = resultSet.getString("email");
                        String salary = resultSet.getString("salary");
                        String tax = resultSet.getString("tax");
                        String hireDate = resultSet.getString("hire_date");

                        System.out.printf("%-3s %-15s %-20s %-20s %-3s %-15s %-25s %-15s %-15s %-15s \n", id1,
                                employeeCode, fullName, position, age, phone, email, salary, tax, hireDate);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (emp == 2) {
            System.out.println("            Insert Employee");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Employee Code: ");
            String employeeCode = scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Enter Position: ");
            String position = scanner.nextLine();
            System.out.print("Enter Age: ");
            int age = checkInputInt(scanner);
            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            int salary = checkInputInt(scanner);
            double tax = 0;
            if (salary <= 5000000) {
                tax = salary * 0.05;
            } else if (salary > 5000000 && salary <= 10000000) {
                tax = salary * 0.1;
            } else if (salary > 10000000 && salary <= 18000000) {
                tax = salary * 0.15;
            } else if (salary > 18000000 && salary <= 32000000) {
                tax = salary * 0.2;
            } else if (salary > 32000000 && salary <= 52000000) {
                tax = salary * 0.25;
            } else if (salary > 52000000 && salary <= 80000000) {
                tax = salary * 0.3;
            } else {
                tax = salary * 0.35;
            }

            Date dNow = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String hireDate = format.format(dNow);

            




        }
    }

    public static void main(String[] args) {
        System.out.println("------ Human Resource Management -------");
        Scanner scanner = new Scanner(System.in);

        // Login with admin
        System.out.println("                Login                   ");
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        JdbcAdmin jdbcAdmin = new JdbcAdmin();
        ResultSet resultSet = null;
        try {
            resultSet = jdbcAdmin.checkAdmin();
            if(resultSet.next()) {
                System.out.println(resultSet.getString("full_name"));

                System.out.println();
                while (!fullName.equals(resultSet.getString("full_name")) ||
                        !userName.equals(resultSet.getString("username")) ||
                        ! password.equals(resultSet.getString("password"))) {
                    System.out.println("Need to enter the correct administrator account !");
                    System.out.println("                Login                   ");
                    System.out.print("Full Name: ");
                    fullName = scanner.nextLine();
                    System.out.print("Username: ");
                    userName = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();
                }
                System.out.println("You have successfully login !");
                System.out.println("------ Human Resource Management -------");
                System.out.println("                    Home                ");
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("1. Employee");
                System.out.println("2. Department");
                System.out.println("3. Statistics");
                System.out.println("4. Exit");
                System.out.println("----------------------------------------");
                System.out.print("Enter Option Number 1 - 4: ");
                int h = checkInputInt(scanner1);
                while (h < 1 || h > 4) {
                    System.out.println("Must be input the option 1 - 4!");
                    System.out.print("Enter again: ");
                    h = checkInputInt(scanner1);
                }

                if(h == 1) {
                    System.out.println("------ Human Resource Management -------");
                    System.out.println("                Employee                ");
                    System.out.println("1. Show All Employee");
                    System.out.println("2. Insert Employee");
                    System.out.println("3. Update Employee");
                    System.out.println("4. Delete Employee");
                    System.out.println("5. Search Employee");
                    System.out.println("6. Back to Home");
                    System.out.println("----------------------------------------");
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.print("Enter Option Number 1 - 6: ");
                    int emp = checkInputInt(scanner2);
                    while (emp < 1 || emp > 6) {
                        System.out.println("Must be input the option 1 - 6!");
                        System.out.print("Enter again: ");
                        emp = checkInputInt(scanner2);
                    }
                    employee(emp);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
