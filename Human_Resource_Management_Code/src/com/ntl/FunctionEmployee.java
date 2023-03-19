package com.ntl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class FunctionEmployee {
    JdbcEmployee jdbcEmployee = new JdbcEmployee();

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

    // check input double
    public static double checkInputDouble(Scanner scanner) {
        scanner = new Scanner(in);
        while (true) {
            try {
                double result = Double.parseDouble(scanner.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                out.println("Must be input double!");
                out.print("Enter again: ");
            }
        }
    }

    // check input gender
    public static String checkInputGender(Scanner scanner) {
        scanner = new Scanner(in);
        String result = scanner.nextLine();
        while (!result.equals("Male") && !result.equals("Female")) {
            out.println("Must be input Male or Female!");
            out.print("Enter Gender Again: ");
            result = scanner.nextLine();
        }
        return result;
    }

    // check input department
    public static int checkInputDepartment(int check, Scanner scanner) {
        scanner = new Scanner(in);
        while (check < 1 || check > 6) {
            out.println("Must be input 1 - 6!");
            out.println("1.Technical, 2.Marketing, 3.Bussiness, 4.HR, 5.Production, 6.Accountant");
            out.print("Enter Department Again: ");
            check = checkInputInt(scanner);
        }
        return check;
    }

    // calculate tax from salary
    public static double calculateTax (double salary) {
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
        return tax;
    }

    // employee page
    public int employeePage() {
        out.println("------ Human Resource Management -------");
        out.println("                Employee                ");
        out.println("1. Show All Employee");
        out.println("2. Insert Employee");
        out.println("3. Update Employee");
        out.println("4. Delete Employee");
        out.println("5. Search Employee");
        out.println("6. Back to Home");
        out.println("----------------------------------------");
        Scanner scanner2 = new Scanner(in);
        out.print("Enter Option Number 1 - 6: ");
        int emp = checkInputInt(scanner2);
        while (emp < 1 || emp > 6) {
            out.println("Must be input the option 1 - 6!");
            out.print("Enter again: ");
            emp = checkInputInt(scanner2);
        }
        return emp;
    }

    // show data employee
    public void showDataEmployee() {
        out.println("                                Show All Employee");
        ResultSet resultSet = null;
        try {
            resultSet = jdbcEmployee.getDataEmployee();
            out.printf("%-3s %-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Id",
                    "Employee Code", "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");

            if (!resultSet.isBeforeFirst()) {
                out.println("----- No Data -----");
            } else {
                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String employeeCode = resultSet.getString("employee_code");
                    String fullName = resultSet.getString("full_name");
                    String position = resultSet.getString("position");
                    String gender = resultSet.getString("gender");
                    String age = resultSet.getString("age");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String salary = resultSet.getString("salary");
                    String tax = resultSet.getString("tax");
                    String hireDate = resultSet.getString("hire_date");

                    out.printf("%-3s %-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", id1,
                            employeeCode, fullName, position, gender, age, phone, email, salary, tax, hireDate);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // insert data employee
    public void insertDataEmployee() {
        out.println("                                Insert Employee");
        Scanner scanner = new Scanner(in);
        out.print("Enter Employee Code: ");
        String employeeCode = scanner.nextLine();
        out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();
        out.print("Enter Position: ");
        String position = scanner.nextLine();
        out.print("Enter Gender (Male or Female): ");
        String gender = checkInputGender(scanner);
        out.print("Enter Age: ");
        int age = checkInputInt(scanner);
        out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        out.print("Enter Email: ");
        String email = scanner.nextLine();
        out.print("Enter Salary: ");
        double salary = checkInputDouble(scanner);

        double tax = calculateTax(salary);

        Date dNow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String hireDate = format.format(dNow);

        out.print("Enter Department (1.Technical, 2.Marketing, 3.Bussiness, 4.HR, 5.Production, 6.Accountant): ");
        int departmentID = checkInputInt(scanner);
        departmentID = checkInputDepartment(departmentID, scanner);

        out.print("Enter Is Manager (Yes or No): ");
        String checkManager = scanner.nextLine();
        String isManager = null;
        if (checkManager.equals("Yes")) {
            isManager = "'1'";
        }

        Integer res = null;
        try {
            res = jdbcEmployee.insertDataEmployee(employeeCode, fullName, position, gender, age, phone, email, salary, tax,
                    hireDate, departmentID, isManager);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // update data employee
    public void updateDataEmployee() {
        out.println("                                Update Employee");
        Scanner scanner = new Scanner(in);
        out.print("Enter Employee Code Need Update: ");
        String employeeCodeOld = scanner.nextLine();
        out.print("Enter Employee Code New: ");
        String employeeCodeNew = scanner.nextLine();
        out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();
        out.print("Enter Position: ");
        String position = scanner.nextLine();
        out.print("Enter Gender (Male or Female): ");
        String gender = checkInputGender(scanner);
        out.print("Enter Age: ");
        int age = checkInputInt(scanner);
        out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        out.print("Enter Email: ");
        String email = scanner.nextLine();
        out.print("Enter Salary: ");
        double salary = checkInputDouble(scanner);

        double tax = calculateTax(salary);

        out.print("Enter Department (1.Technical, 2.Marketing, 3.Bussiness, 4.HR, 5.Production, 6.Accountanr): ");
        int departmentID = checkInputInt(scanner);
        departmentID = checkInputDepartment(departmentID, scanner);

        out.print("Enter Is Manager (Yes or No): ");
        String checkManager = scanner.nextLine();
        String isManager = null;
        if (checkManager.equals("Yes")) {
            isManager = "'1'";
        }

        Integer res = null;
        try {
            res = jdbcEmployee.updateDataEmployee(employeeCodeOld, employeeCodeNew, fullName, position, gender, age, phone,
                    email, salary, tax, departmentID, isManager);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete data employee
    public void deleteDataEmployee() {
        out.println("                                Delete Employee");
        Scanner scanner = new Scanner(in);
        out.print("Enter Employee Code Need Delete: ");
        String employeeCode = scanner.nextLine();

        Integer res = null;
        try {
            res = jdbcEmployee.deleteDataEmployee(employeeCode);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // search data employee
    public void searchDataEmployee() {
        out.println("                                Search Employee");
        Scanner scanner = new Scanner(in);
        out.print("Enter Name Need Search: ");
        String name = scanner.nextLine();

        ResultSet resultSet = null;
        try {
            resultSet = jdbcEmployee.searchDataEmployee(name);
            out.printf("%-3s %-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Id",
                    "Employee Code", "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");

            if (!resultSet.isBeforeFirst()) {
                out.println("----- No Data -----");
            } else {
                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String employeeCode = resultSet.getString("employee_code");
                    String fullName = resultSet.getString("full_name");
                    String position = resultSet.getString("position");
                    String gender = resultSet.getString("gender");
                    String age = resultSet.getString("age");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String salary = resultSet.getString("salary");
                    String tax = resultSet.getString("tax");
                    String hireDate = resultSet.getString("hire_date");

                    out.printf("%-3s %-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", id1,
                            employeeCode, fullName, position, gender, age, phone, email, salary, tax, hireDate);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
