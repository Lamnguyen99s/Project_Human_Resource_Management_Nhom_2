package com.ntl.function;

import com.ntl.database.JdbcData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class FunctionStatistic {
    JdbcData jdbcData = new JdbcData();

    // check input integer
    private Integer checkInputInt(Scanner scanner) {
        scanner = new Scanner(in);
        while (true) {
            try {
                Integer result = Integer.parseInt(scanner.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                out.println("Must be input integer!");
                out.print("Enter again: ");

            }
        }
    }

    // format double to string
    private String formatDouble(double num) {
        String formattedNum = String.format("%,.0f", num);
        return formattedNum;
    }

    // statistic page
    public int statisticPage() {
        out.println("------ Human Resource Management -------");
        out.println("                Statistic                ");
        out.println("1. Statistics of the top 5 highest-paid employees");
        out.println("2. Statistics of the top 5 lowest-paid employees");
        out.println("3. Statistics of the number of employees per department");
        out.println("4. Statistics of the department with the highest average salary");
        out.println("5. Statistics of total salary payable to employees each month by year");
        out.println("6. Back to Home");
        out.println("----------------------------------------");
        Scanner scanner2 = new Scanner(in);
        out.print("Enter Option Number 1 - 6: ");
        int sta = checkInputInt(scanner2);
        while (sta < 1 || sta > 6) {
            out.println("Must be input the option 1 - 6!");
            out.print("Enter again: ");
            sta = checkInputInt(scanner2);
        }
        return sta;
    }

    // statistics of the top 5 highest-paid employees
    public void statistic1() {
        out.println("                                Top 5 Highest-Paid Employees");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT emp.*, dep.department_name FROM employee emp JOIN department dep " +
                    "ON emp.department_id = dep.id ORDER BY salary DESC LIMIT 5");

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            int number = 0;
            while (resultSet.next()) {
                number ++;
                String employeeCode = resultSet.getString("employee_code");
                String fullName = resultSet.getString("full_name");
                String position = resultSet.getString("position");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                double salary = resultSet.getDouble("salary");
                double tax = resultSet.getDouble("tax");
                String hireDate = resultSet.getString("hire_date");
                String departmentName = resultSet.getString("department_name");

                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s %-25s %-1s \n",
                        "|", number, "|", employeeCode, "|", fullName, "|", position, "|", gender, "|", age, "|", phone, "|", email, "|",
                        formatDouble(salary), "|", formatDouble(tax), "|", hireDate, "|", departmentName, "|");
            }

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // statistics of the top 5 highest-paid employees
    public void statistic2() {
        out.println("                                Top 5 Lowest-Paid Employees");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT emp.*, dep.department_name FROM employee emp JOIN department dep " +
                    "ON emp.department_id = dep.id ORDER BY salary ASC LIMIT 5");

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            int number = 0;
            while (resultSet.next()) {
                number ++;
                String employeeCode = resultSet.getString("employee_code");
                String fullName = resultSet.getString("full_name");
                String position = resultSet.getString("position");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                double salary = resultSet.getDouble("salary");
                double tax = resultSet.getDouble("tax");
                String hireDate = resultSet.getString("hire_date");
                String departmentName = resultSet.getString("department_name");

                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s %-25s %-1s \n",
                        "|", number, "|", employeeCode, "|", fullName, "|", position, "|", gender, "|", age, "|", phone, "|", email, "|",
                        formatDouble(salary), "|", formatDouble(tax), "|", hireDate, "|", departmentName, "|");
            }

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // statistics of the number of employees per department
    public void statistic3() {
        out.println("                                The Number Of Employees Per Department");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT department_name, COUNT(*) as employee_count " +
                    "FROM employee JOIN department ON employee.department_id = department.id " +
                    "GROUP BY department_name");

            out.println("----------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-25s %-1s %-20s %-1s \n", "|", "No.", "|", "Department Name", "|", "Number Of Emloyees", "|");
            out.println("----------------------------------------------------------");

            int number = 0;
            while (resultSet.next()) {
                number ++;
                String departmentName = resultSet.getString("department_name");
                int numEmployee = resultSet.getInt("employee_count");

                out.printf("%-1s %-3s %-1s %-25s %-1s %-20s %-1s \n", "|",number, "|", departmentName, "|", numEmployee, "|");
            }

            out.println("----------------------------------------------------------");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // statistics of the department with the highest average salary
    public void statistic4() {
        out.println("                                The Department With The Highest Average Salary");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT dep.department_name, AVG(salary) AS avg_salary " +
                    "FROM employee AS emp " +
                    "JOIN department AS dep ON emp.department_id = dep.id " +
                    "GROUP BY dep.department_name " +
                    "ORDER BY avg_salary DESC LIMIT 1");

            out.println("------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-25s %-1s %-22s %-1s \n", "|", "No.", "|", "Department Name", "|", "Highest Average Salary", "|");
            out.println("------------------------------------------------------------");

            int number = 0;
            while (resultSet.next()) {
                number ++;
                String departmentName = resultSet.getString("department_name");
                double avgSalary = resultSet.getDouble("avg_salary");

                out.printf("%-1s %-3s %-1s %-25s %-1s %-22s %-1s \n", "|",number, "|", departmentName, "|", formatDouble(avgSalary), "|");
            }

            out.println("------------------------------------------------------------");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // statistics of total salary payable to employees each month by year
    public void statistic5() {
        out.println("                                Total Salary Payable To Employees Each Month By Year");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT YEAR(hire_date) AS a , SUM(salary) AS b " +
                    "FROM employee GROUP BY YEAR(hire_date) ORDER BY YEAR(hire_date) ");

            out.println("------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s \n", "|", "No.", "|", "Year", "|", "Total Salary / Month", "|");
            out.println("------------------------------------------------");

            int number = 0;
            while (resultSet.next()) {
                number ++;
                String departmentName = resultSet.getString("a");
                double avgSalary = resultSet.getDouble("b");

                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s \n", "|",number, "|", departmentName, "|", formatDouble(avgSalary), "|");
            }

            out.println("------------------------------------------------");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
