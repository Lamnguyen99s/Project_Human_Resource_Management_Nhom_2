package com.ntl.function;

import com.ntl.database.JdbcData;
import com.ntl.entity.Department;
import com.ntl.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class FunctionDepartment {
    JdbcData jdbcData = new JdbcData();

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

    // format double to string
    private String formatDouble(double num) {
        DecimalFormat df = new DecimalFormat("#,##0");
        String formattedNum = df.format(num);
        return formattedNum;
    }

    // department page
    public int departmentPage() {
        out.println("------ Human Resource Management -------");
        out.println("                Department                ");
        out.println("1. Show All Department");
        out.println("2. Insert Department");
        out.println("3. Update Department");
        out.println("4. Delete Department");
        out.println("5. Transfer Employee To Another Department");
        out.println("6. Add New Employee To Department");
        out.println("7. Back to Home");
        out.println("----------------------------------------");
        Scanner scanner2 = new Scanner(in);
        out.print("Enter Option Number 1 - 7: ");
        int dep = checkInputInt(scanner2);
        while (dep < 1 || dep > 7) {
            out.println("Must be input the option 1 - 7!");
            out.print("Enter again: ");
            dep = checkInputInt(scanner2);
        }
        return dep;
    }

    // show data department
    public void showDataDepartment() {
        out.println("                                Show All Department");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department");
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

        try {
            Statement statement = jdbcData.getStatement();
            String sql = "INSERT INTO department (department_name, address) " +
                    "VALUES ('" + departmentName + "', '" + address + "')";
            statement.executeUpdate(sql);
            System.out.println("Insert data successfully");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // update data department
    public void updateDataDepartment() {
        out.println("                                Update Department");
        Scanner scanner = new Scanner(in);
        List<Integer> idDepartmentList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getDepartmentId = statement.executeQuery("SELECT id FROM department");

            while (getDepartmentId.next()) {
                idDepartmentList.add(getDepartmentId.getInt("id"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.print("Enter Department ID Need Update: ");
        int departmentID = checkInputInt(scanner);
        while (!idDepartmentList.contains(departmentID)) {
            out.println("------- No Find Department ------");
            out.print("Enter Department ID Need Update: ");
            departmentID = checkInputInt(scanner);
        }

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getDepartmentById = statement.executeQuery("SELECT * FROM department WHERE id = " + departmentID + "");

            while (getDepartmentById.next()) {
                out.println("Department ID: " + getDepartmentById.getString("id"));
                out.println("Department Name: " + getDepartmentById.getString("department_name"));
                out.println("Address: " + getDepartmentById.getString("address"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.print("Would you like to update this department? (Y/N): ");
        String choose = scanner.nextLine();
        while (!choose.equals("Y") && !choose.equals("N")) {
            out.println("Must be input Y or N");
            out.print("Would you like to update this department? (Y/N): ");
            choose = scanner.nextLine();
        }

        if (choose.equals("Y")) {
            out.print("Enter Department Name: ");
            String departmentName = scanner.nextLine();
            out.print("Enter Address: ");
            String address = scanner.nextLine();

            try {
                Statement statement = jdbcData.getStatement();
                String sql = "UPDATE department SET department_name = '" + departmentName + "', " +
                        "address = '" + address + "' WHERE id = " + departmentID + "";
                Integer res = statement.executeUpdate(sql);
                System.out.println("Update records successfully!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            out.println("Back to Department");
        }
    }

    // delete data department
    public void deleteDataDepartment() {
        out.println("                                Delete Department");
        Scanner scanner = new Scanner(in);

        List<Integer> idDepartmentList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getDepartmentId = statement.executeQuery("SELECT id FROM department");

            while (getDepartmentId.next()) {
                idDepartmentList.add(getDepartmentId.getInt("id"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            out.print("Enter Department ID Need Delete: ");
            int  departmentID = checkInputInt(scanner);
            while (!idDepartmentList.contains(departmentID)) {
                out.println("------- No Find Department ------");
                out.print("Enter Department ID Need Delete: ");
                departmentID = checkInputInt(scanner);
            }

            List<Department> departmentList = new ArrayList<>();
            List<Employee> employeeList = new ArrayList<>();

            try {
                Statement statement = jdbcData.getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM department WHERE id = " + departmentID + "");
                while (resultSet.next()) {
                    departmentList.add(new Department(resultSet.getInt("id"),
                            resultSet.getString("department_name"),
                            resultSet.getString("address")));
                }

                ResultSet resultSet1 = statement.executeQuery("SELECT * FROM employee WHERE department_id = " + departmentID + "");
                while (resultSet1.next()) {
                    employeeList.add(new Employee(resultSet1.getString("employee_code"),
                            resultSet1.getString("full_name"),
                            resultSet1.getString("position"),
                            resultSet1.getString("gender"),
                            resultSet1.getInt("age"),
                            resultSet1.getString("phone"),
                            resultSet1.getString("email"),
                            resultSet1.getDouble("salary"),
                            resultSet1.getDouble("tax"),
                            resultSet1.getString("hire_date"),
                            resultSet1.getInt("department_id"),
                            resultSet1.getString("is_manager")));
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (Department dep : departmentList) {
                out.println("Department No." + dep.getId() + ": " +  dep.getDepartmentName() +
                        " at the " + dep.getAddress() + " and has " + employeeList.size() + " employeee");
            }

            if(employeeList.size() > 0) {
                out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Employee Code",
                        "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");
                for(Employee emp : employeeList) {
                    out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n",
                            emp.getEmployeeCode(), emp.getFullName(), emp.getPosition(), emp.getGender(),
                            emp.getAge(), emp.getPhone(), emp.getEmail(), formatDouble(emp.getSalary()) ,
                            formatDouble(emp.getTax()), emp.getHireDate());
                }

                out.println("This department cannot be deleted because the department has employees!");
                out.println("Please select another department with no staff!");
            } else {
                out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Employee Code",
                        "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");

                out.println("------- No Employee in Department --------");
                out.println("This department can be deleted");

                out.print("Would you like to delete this department? (Y/N): ");
                String choose = scanner.nextLine();
                while (!choose.equals("Y") && !choose.equals("N")) {
                    out.println("Must be input Y or N");
                    out.print("Would you like to update this department? (Y/N): ");
                    choose = scanner.nextLine();
                }

                if(choose.equals("Y")) {
                    try {
                        Statement statement = jdbcData.getStatement();
                        String sql = "DELETE FROM department WHERE id = '" + departmentID + "'";
                        statement.executeUpdate(sql);
                        System.out.println("Delete records successfully!");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                } else {
                    out.println("Back to Department");
                    break;
                }
            }
        }
    }

    // transfer employee to department
    public void transferEmployee() {
        out.println("                                Transfer Employee To Another Department");
        Scanner scanner = new Scanner(in);

        List<Integer> idDepartmentList = new ArrayList<>();
        List<Department> departmentList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getDepartmentId = statement.executeQuery("SELECT * FROM department");

            while (getDepartmentId.next()) {
                idDepartmentList.add(getDepartmentId.getInt("id"));
                departmentList.add(new Department(getDepartmentId.getInt("id"),
                        getDepartmentId.getString("department_name"),
                        getDepartmentId.getString("address")));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("List of all departments");
        for (Department dep : departmentList) {
            out.println(dep.getId() + ". " +  dep.getDepartmentName());
        }

        while (true) {
            out.print("Enter Department ID: ");
            int  departmentID = checkInputInt(scanner);
            while (!idDepartmentList.contains(departmentID)) {
                out.println("------- No Find Department ------");
                out.print("Enter Department ID: ");
                departmentID = checkInputInt(scanner);
            }

            List<Department> departmentListByID = new ArrayList<>();
            List<Employee> employeeList = new ArrayList<>();
            List<String> employeeCodeList = new ArrayList<>();

            try {
                Statement statement = jdbcData.getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM department WHERE id = " + departmentID + "");
                while (resultSet.next()) {
                    departmentListByID.add(new Department(resultSet.getInt("id"),
                            resultSet.getString("department_name"),
                            resultSet.getString("address")));
                }

                ResultSet resultSet1 = statement.executeQuery("SELECT * FROM employee WHERE department_id = " + departmentID + "");
                while (resultSet1.next()) {
                    employeeList.add(new Employee(resultSet1.getString("employee_code"),
                            resultSet1.getString("full_name"),
                            resultSet1.getString("position"),
                            resultSet1.getString("gender"),
                            resultSet1.getInt("age"),
                            resultSet1.getString("phone"),
                            resultSet1.getString("email"),
                            resultSet1.getDouble("salary"),
                            resultSet1.getDouble("tax"),
                            resultSet1.getString("hire_date"),
                            resultSet1.getInt("department_id"),
                            resultSet1.getString("is_manager")));

                    employeeCodeList.add(resultSet1.getString("employee_code"));
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (Department dep : departmentListByID) {
                out.println("Department No." + dep.getId() + ": " +  dep.getDepartmentName() +
                        " at the " + dep.getAddress() + " and has " + employeeList.size() + " employeee");
            }

            if(employeeList.size() > 0) {
                out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Employee Code",
                        "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");
                for(Employee emp : employeeList) {
                    out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n",
                            emp.getEmployeeCode(), emp.getFullName(), emp.getPosition(), emp.getGender(),
                            emp.getAge(), emp.getPhone(), emp.getEmail(), formatDouble(emp.getSalary()) ,
                            formatDouble(emp.getTax()), emp.getHireDate());
                }

                out.println("This department can transfer employee");
                out.print("Would you like to transfer employee out this department? (Y/N): ");
                String choose = scanner.nextLine();
                while (!choose.equals("Y") && !choose.equals("N")) {
                    out.println("Must be input Y or N");
                    out.print("Would you like to transfer employee out this department? (Y/N): ");
                    choose = scanner.nextLine();
                }

                if(choose.equals("Y")) {
                    while (true) {
                        out.print("Enter The Employee Code Want To Change Departments: ");
                        String employeeCode = scanner.nextLine();

                        while (!employeeCodeList.contains(employeeCode)) {
                            out.println("Employee code does not belong to this department. " +
                                    "Re-enter employee code according to the list above!");
                            out.print("Enter the employee code want to change departments: ");
                            employeeCode = scanner.nextLine();
                        }

                        String isManagerCheck = null;
                        for (Employee emp : employeeList) {
                            if (employeeCode.equals(emp.getEmployeeCode())) {
                                isManagerCheck = emp.getIsManager();
                                break;
                            }
                        }

                        if (isManagerCheck != null) {
                            out.println("This employee code cannot be selected because this employee is a manager in this department. " +
                                    "Re-enter employee code!");
                        } else {
                            out.println("Select a Department ID Want To Transfer Employee");
                            for (Department dep : departmentList) {
                                out.println(dep.getId() + ". " +  dep.getDepartmentName());
                            }
                            out.print("Enter Department ID: ");
                            int departmentIdNew = checkInputInt(scanner);

                            while (!idDepartmentList.contains(departmentIdNew)) {
                                out.println("------- No Find Department ------");
                                out.print("Enter Department ID: ");
                                departmentIdNew = checkInputInt(scanner);
                            }

                            while (departmentIdNew == departmentID) {
                                out.println("This department already contains this employee. Enter another department id!");
                                out.print("Enter Department ID Another: ");
                                departmentIdNew = checkInputInt(scanner);
                            }

                            try {
                                Statement statement = jdbcData.getStatement();
                                String sql = "UPDATE employee SET  department_id = " + departmentIdNew + "" +
                                        "WHERE employee_code = '" + employeeCode + "'";
                                statement.executeUpdate(sql);
                                System.out.println("Transfer Employee Successfully!");
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    break;
                } else {
                    out.println("Back to Department");
                    break;
                }
            } else {
                out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Employee Code",
                        "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");

                out.println("------- No Employee in Department --------");
                out.println("Please choose another department with staff to transfer!");
            }
        }
    }

    // add new employee to department
    public void addNewEmployee() {
        out.println("                                Add New Employee To Department");
        Scanner scanner = new Scanner(in);

        List<Employee> employeeList = new ArrayList<>();
        List<String> employeeCodeList = new ArrayList<>();
        List<Department> departmentList = new ArrayList<>();
        List<Integer> idDepartmentList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee WHERE department_id IS NULL");
            while (resultSet.next()) {
                employeeList.add(new Employee(resultSet.getString("employee_code"),
                        resultSet.getString("full_name"),
                        resultSet.getString("position"),
                        resultSet.getString("gender"),
                        resultSet.getInt("age"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getDouble("salary"),
                        resultSet.getDouble("tax"),
                        resultSet.getString("hire_date"),
                        resultSet.getInt("department_id"),
                        resultSet.getString("is_manager")));

                employeeCodeList.add(resultSet.getString("employee_code"));
            }

            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM  department");
            while (resultSet1.next()){
                idDepartmentList.add(resultSet1.getInt("id"));
                departmentList.add(new Department(resultSet1.getInt("id"),
                        resultSet1.getString("department_name"),
                        resultSet1.getString("address")));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        out.println("List All Employee Do Not Belong To Any Department");

        if(employeeList.size() > 0) {
            out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Employee Code",
                    "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");
            for(Employee emp : employeeList) {
                out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n",
                        emp.getEmployeeCode(), emp.getFullName(), emp.getPosition(), emp.getGender(),
                        emp.getAge(), emp.getPhone(), emp.getEmail(), formatDouble(emp.getSalary()) ,
                        formatDouble(emp.getTax()), emp.getHireDate());
            }

            out.print("Enter The Employee Code Want To Change Departments: ");
            String employeeCode = scanner.nextLine();

            while (!employeeCodeList.contains(employeeCode)) {
                out.println("Employee code does not belong to this department. " +
                        "Re-enter employee code according to the list above!");
                out.print("Enter the employee code want to change departments: ");
                employeeCode = scanner.nextLine();
            }

            out.println("Select a Department ID Want To Add New Employee");
            for (Department dep : departmentList) {
                out.println(dep.getId() + ". " +  dep.getDepartmentName());
            }
            out.print("Enter Department ID: ");
            int departmentID = checkInputInt(scanner);

            while (!idDepartmentList.contains(departmentID)) {
                out.println("------- No Find Department ------");
                out.print("Enter Department ID: ");
                departmentID = checkInputInt(scanner);
            }

            try {
                Statement statement = jdbcData.getStatement();
                String sql = "UPDATE employee SET  department_id = " + departmentID + " " +
                        "WHERE employee_code = '" + employeeCode + "'";
                statement.executeUpdate(sql);
                System.out.println("Add New Employee To Department Successfully!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            out.printf("%-15s %-20s %-20s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Employee Code",
                    "Full Name", "Position", "Gender", "Age", "Phone", "Email", "Salary", "Tax", "Hire Date");

            out.println("------- No New Employee --------");
            out.println("Back to Department!");
        }
    }
}
