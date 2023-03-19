package com.ntl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class FunctionDepartment {
    JdbcDepartment jdbcDepartment = new JdbcDepartment();

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

    // department page
    public int departmentPage() {
        out.println("------ Human Resource Management -------");
        out.println("                Department                ");
        out.println("1. Show All Department");
        out.println("2. Insert Department");
        out.println("3. Update Department");
        out.println("4. Delete Department");
        out.println("5. Back to Home");
        out.println("----------------------------------------");
        Scanner scanner2 = new Scanner(in);
        out.print("Enter Option Number 1 - 5: ");
        int dep = checkInputInt(scanner2);
        while (dep < 1 || dep > 5) {
            out.println("Must be input the option 1 - 5!");
            out.print("Enter again: ");
            dep = checkInputInt(scanner2);
        }
        return dep;
    }

    // show data department
    public void showDataDepartment() {
        out.println("                                Show All Department");
        ResultSet resultSet = null;
        try {
            resultSet = jdbcDepartment.getDataDepartment();
            out.printf("%-3s %-25s %-20s \n", "Id", "Department Name", "Address");

            if (!resultSet.isBeforeFirst()) {
                out.println("----- No Data -----");
            } else {
                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String departmentName = resultSet.getString("department_name");
                    String address = resultSet.getString("address");

                    out.printf("%-3s %-25s %-20s \n", id1, departmentName, address);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // insert data department
    public void insertDataDepartment() {
        out.println("                                Insert Department");
        Scanner scanner = new Scanner(in);
        out.print("Enter Department Name: ");
        String departmentName = scanner.nextLine();
        out.print("Enter Address: ");
        String address = scanner.nextLine();


        Integer res = null;
        try {
            res = jdbcDepartment.insertDataDepartment(departmentName, address);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // update data department
    public void updateDataDepartment() {
        out.println("                                Update Employee");
        Scanner scanner = new Scanner(in);
        out.print("Enter Department ID Need Update: ");
        int departmentID = checkInputInt(scanner);
        out.print("Enter Department Name: ");
        String departmentName = scanner.nextLine();
        out.print("Enter Address: ");
        String address = scanner.nextLine();

        Integer res = null;
        try {
            res = jdbcDepartment.updateDataDepartment(departmentID, departmentName, address);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete data department
    public void deleteDataDepartment() {
        out.println("                                Delete Employee");
        Scanner scanner = new Scanner(in);
        out.print("Enter Department ID Need Delete: ");
        int  departmentID = checkInputInt(scanner);

        Integer res = null;
        try {
            res = jdbcDepartment.deleteDataDepartment(departmentID);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
