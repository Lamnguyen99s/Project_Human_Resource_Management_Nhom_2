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

    // check input department
    private String checkInputDepartment (Scanner scanner) {
        scanner = new Scanner(in);
        String departmentName = scanner.nextLine();
        List<String> nameDepartmentList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT department_name FROM department");

            while (resultSet.next()) {
                nameDepartmentList.add( resultSet.getString("department_name"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (nameDepartmentList.contains(departmentName)) {
            out.println("Department name already exists!");
            out.println("Enter another department name again: ");
            departmentName = scanner.nextLine();
        }
        return departmentName;
    }

    // format double to string
    private String formatDouble(double num) {
        DecimalFormat df = new DecimalFormat("#,##0");
        String formattedNum = df.format(num);
        return formattedNum;
    }

    // department page
    public int departmentPage() {
        out.println("-----  HUMAN RESOURCE MANAGEMENT  -----");
        out.println("---------------------------------------");
        out.println("               DEPARTMENT              ");
        out.println("1. Show All Department");
        out.println("2. Show Employee By Department");
        out.println("3. Insert Department");
        out.println("4. Update Department");
        out.println("5. Delete Department");
        out.println("6. Transfer Employee To Another Department");
        out.println("7. Add New Employee To Department");
        out.println("8. Back to Home");
        out.println("----------------------------------------");
        Scanner scanner2 = new Scanner(in);
        out.println("Enter Option Number 1 - 8: ");
        int dep = checkInputInt(scanner2);
        while (dep < 1 || dep > 8) {
            out.println("Must be input the option 1 - 8!");
            out.println("Enter again: ");
            dep = checkInputInt(scanner2);
        }
        out.println();
        return dep;
    }

    // show data department
    public void showDataDepartment() {
        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department");
            out.println("----------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-25s %-1s %-20s %-1s \n", "|", "Id", "|", "Department Name", "|", "Address", "|");
            out.println("----------------------------------------------------------");

            if (!resultSet.isBeforeFirst()) {
                out.println("|----- No Data -----                                     |");
                out.println("----------------------------------------------------------");
                out.println();
            } else {
                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String departmentName = resultSet.getString("department_name");
                    String address = resultSet.getString("address");

                    out.printf("%-1s %-3s %-1s %-25s %-1s %-20s %-1s \n", "|", id1, "|", departmentName, "|", address, "|");
                }
                out.println("----------------------------------------------------------");
                out.println();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // show employee by department
    public void showEmployerByDepartment() {
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

        while (true) {
            out.println("List of all departments id and name");
            for (Department dep : departmentList) {
                out.println("   " + dep.getId() + ". " +  dep.getDepartmentName());
            }
            out.println("   0. Back to Department");

            out.print("Enter Department ID: ");
            int departmentID = checkInputInt(scanner);
            while (departmentID != 0 && !idDepartmentList.contains(departmentID)) {
                out.println("------- No Find Department ------");
                out.print("Enter Department ID: ");
                departmentID = checkInputInt(scanner);
            }
            out.println();
            if(departmentID == 0) {
                out.println("-----  Back to Department  -----");
                out.println();
                break;
            } else {
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
                    out.println("Department ID." + dep.getId() + ": " +  dep.getDepartmentName() +
                            " at the " + dep.getAddress() + " and has " + employeeList.size() + " employee");
                }

                if(employeeList.size() > 0) {
                    out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                    "%-1s %-25s %-1s \n",
                            "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                            "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|");
                    out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                    int number = 0;
                    for(Employee emp : employeeList) {
                        number++;
                        out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                        "%-1s %-25s %-1s \n",
                                "|", number, "|", emp.getEmployeeCode(), "|", emp.getFullName(), "|", emp.getPosition(), "|", emp.getGender(), "|",
                                emp.getAge(), "|", emp.getPhone(), "|", emp.getEmail(), "|", formatDouble(emp.getSalary()), "|",
                                formatDouble(emp.getTax()), "|", emp.getHireDate(), "|");
                    }

                    out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    out.println();
                } else {
                    out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                    "%-1s %-25s %-1s \n",
                            "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                            "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|");
                    out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    out.println("|------- No Employee in Department --------                                                                                                                                                       |");
                    out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    out.println();
                }
            }
        }
    }

    // insert data department
    public void insertDataDepartment() {
        Scanner scanner = new Scanner(in);
        out.println("Enter Department Name: ");
        String departmentName = checkInputDepartment(scanner);
        out.println("Enter Address: ");
        String address = scanner.nextLine();

        out.print("Would you like to insert this departmennt's information into the system? (Y/N): ");
        String choose = scanner.nextLine();
        while (!choose.equals("Y") && !choose.equals("N")) {
            out.println("Must be input Y or N");
            out.print("Would you like to insert this departmennt's information into the system? (Y/N): ");
            choose = scanner.nextLine();
        }

        if(choose.equals("Y")) {
            try {
                Statement statement = jdbcData.getStatement();
                String sql = "INSERT INTO department (department_name, address) " +
                        "VALUES ('" + departmentName + "', '" + address + "')";
                statement.executeUpdate(sql);
                System.out.println("Insert department's information successfully");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            out.println("-----  Back to Department   -----");
        }
        out.println();
    }

    // update data department
    public void updateDataDepartment() {
        showDataDepartment();
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

        out.println("Enter Department ID Need Update: ");
        int departmentID = checkInputInt(scanner);
        while (!idDepartmentList.contains(departmentID)) {
            out.println("------- No Find Department ------");
            out.println("Enter Department ID Need Update: ");
            departmentID = checkInputInt(scanner);
        }

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getDepartmentById = statement.executeQuery("SELECT * FROM department WHERE id = " + departmentID + "");

            out.println("----------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-25s %-1s %-20s %-1s \n", "|", "Id", "|", "Department Name", "|", "Address", "|");
            out.println("----------------------------------------------------------");

            while (getDepartmentById.next()) {
                int id1 = getDepartmentById.getInt("id");
                String departmentName = getDepartmentById.getString("department_name");
                String address = getDepartmentById.getString("address");

                out.printf("%-1s %-3s %-1s %-25s %-1s %-20s %-1s \n", "|", id1, "|", departmentName, "|", address, "|");
            }
            out.println("----------------------------------------------------------");
            out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            out.println("List Information Can Update");
            out.println("   1. Department Name");
            out.println("   2. Address");
            out.println("   3. Back to Department");

            String sql = null;
            String info = null;

            out.println("Enter Infomation Want To Update: ");
            int op = checkInputInt(scanner);

            while (op < 1 || op > 3) {
                out.println("Entered wrong information!");
                out.println("Only enter option 1 - 3!");
                out.println("Enter Infomation Want To Update: ");
                op = checkInputInt(scanner);
            }
            if (op == 1) {
                info = "department name";
                out.println("Enter Department Name Update: ");
                String departmentName = checkInputDepartment(scanner);
                sql = "UPDATE department SET department_name = '" + departmentName + "' WHERE id = " + departmentID + "";

            } else if (op == 2) {
                info = "address";
                out.println("Enter Address Update: ");
                String address = scanner.nextLine();
                sql = "UPDATE department SET address = '" + address + "' WHERE id = " + departmentID + "";
            } else {
                out.println("-----  Back to Department -----");
                break;
            }

            out.println("Would you like to update the " + info + " information a department has id " + departmentID + "? (Y/N): ");
            String choose = scanner.nextLine();
            while (!choose.equals("Y") && !choose.equals("N")) {
                out.println("Must be input Y or N");
                out.println("Would you like to update the " + info + " information a department has id " + departmentID + "? (Y/N): ");
                choose = scanner.nextLine();
            }

            if (choose.equals("Y")) {
                try {
                    Statement statement = jdbcData.getStatement();
                    Integer res = statement.executeUpdate(sql);
                    System.out.println("Update department's information successfully!");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                out.println();
            } else {
                out.println("Update is canceled!");
                out.println();
            }
        }
        out.println();
    }

    // delete data department
    public void deleteDataDepartment() {
        showDataDepartment();
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


        out.println("Enter Department ID Need Delete: ");
        int  departmentID = checkInputInt(scanner);
        while (!idDepartmentList.contains(departmentID)) {
            out.println("------- No Find Department ------");
            out.println("Enter Department ID Need Delete: ");
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

        out.println();
        for (Department dep : departmentList) {
            out.println("Department No." + dep.getId() + ": " +  dep.getDepartmentName() +
                    " at the " + dep.getAddress() + " and has " + employeeList.size() + " employee");
        }

        if(employeeList.size() > 0) {
            out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|");
            out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            int number = 0;
            for(Employee emp : employeeList) {
                number++;
                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s \n",
                        "|", number, "|", emp.getEmployeeCode(), "|", emp.getFullName(), "|", emp.getPosition(), "|", emp.getGender(), "|",
                        emp.getAge(), "|", emp.getPhone(), "|", emp.getEmail(), "|", formatDouble(emp.getSalary()), "|",
                        formatDouble(emp.getTax()), "|", emp.getHireDate(), "|");
            }
            out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            out.println("This department cannot be deleted because the department has employees!");
            out.println("Please select another department with no staff!");
            out.println();
            out.println("-----  Back to Department  -----");
        } else {
            out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|");
            out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println("|------- No Employee in Department --------                                                                                                                                                       |");
            out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println();

            out.println("This department can be deleted");
            out.println("Would you like to delete the information of department has id " + departmentID + "? (Y/N): ");
            String choose = scanner.nextLine();
            while (!choose.equals("Y") && !choose.equals("N")) {
                out.println("Must be input Y or N");
                out.println("Would you like to delete the information of department has id" + departmentID + "? (Y/N): ");
                choose = scanner.nextLine();
            }

            if(choose.equals("Y")) {
                try {
                    Statement statement = jdbcData.getStatement();
                    String sql = "DELETE FROM department WHERE id = " + departmentID + "";
                    statement.executeUpdate(sql);
                    System.out.println("Delete department's information successfully!");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                out.println("Delete is canceled!");
                out.println("-----  Back to Department  -----");
            }
        }
        out.println();
    }

    // transfer employee to department
    public void transferEmployee() {
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

        while (true) {
            showDataDepartment();
            out.println("Enter Department ID Need Tranfer Employee: ");
            int  departmentID = checkInputInt(scanner);
            while (!idDepartmentList.contains(departmentID)) {
                out.println("------- No Find Department ------");
                out.println("Enter Department ID Need Tranfer Employee: ");
                departmentID = checkInputInt(scanner);
            }

            List<Department> departmentListById = new ArrayList<>();
            List<Employee> employeeList = new ArrayList<>();
            List<String> employeeCodeList = new ArrayList<>();

            try {
                Statement statement = jdbcData.getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM department WHERE id = " + departmentID + "");
                while (resultSet.next()) {
                    departmentListById.add(new Department(resultSet.getInt("id"),
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

            out.println();
            for (Department dep : departmentListById) {
                out.println("Department No." + dep.getId() + ": " +  dep.getDepartmentName() +
                        " at the " + dep.getAddress() + " and has " + employeeList.size() + " employee");
            }

            if(employeeList.size() > 0) {
                out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s \n",
                        "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                        "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|");
                out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                int number = 0;
                for (Employee emp : employeeList) {
                    number++;
                    out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                    "%-1s %-25s %-1s \n",
                            "|", number, "|", emp.getEmployeeCode(), "|", emp.getFullName(), "|", emp.getPosition(), "|", emp.getGender(), "|",
                            emp.getAge(), "|", emp.getPhone(), "|", emp.getEmail(), "|", formatDouble(emp.getSalary()), "|",
                            formatDouble(emp.getTax()), "|", emp.getHireDate(), "|");
                }
                out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                out.println("This department can be transfer employee because the department has employees!");
                out.println();

                while (true) {
                    out.println("Enter The Employee Code Want To Change Department: ");
                    String employeeCode = scanner.nextLine();

                    while (!employeeCodeList.contains(employeeCode)) {
                        out.println("Employee code does not belong to this department. " +
                                "Re-enter employee code according to the list above!");
                        out.println("Enter the employee code want to change department: ");
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
                        if(employeeList.size() == 1) {
                            out.println("This employee code cannot be selected because this employee is a manager in this department!");
                            out.println("This department has only 1 employee as a manager, so the function of transfer employee cannot be used!");
                            out.println("-----  Back to Department  -----");
                            break;
                        } else {
                            out.println("This employee code cannot be selected because this employee is a manager in this department. " +
                                    "Re-enter employee code!");
                        }

                    } else {
                        out.println("Select a Department ID Want To Transfer Employee");
                        for (Department dep : departmentList) {
                            out.println("   " + dep.getId() + ". " + dep.getDepartmentName());
                        }
                        out.println("Enter Department ID: ");
                        int departmentIdNew = checkInputInt(scanner);

                        while (!idDepartmentList.contains(departmentIdNew)) {
                            out.println("------- No Find Department ------");
                            out.println("Enter Department ID: ");
                            departmentIdNew = checkInputInt(scanner);
                        }

                        while (departmentIdNew == departmentID) {
                            out.println("This department already contains this employee. Enter another department id!");
                            out.println("Enter Department ID Another: ");
                            departmentIdNew = checkInputInt(scanner);
                        }
                        out.println("Would you like to transfer employee has employee code " + employeeCode + " from department has id " + departmentID + " to department has id " + departmentIdNew + "? (Y/N): ");
                        String choose = scanner.nextLine();
                        while (!choose.equals("Y") && !choose.equals("N")) {
                            out.println("Must be input Y or N");
                            out.println("Would you like to transfer employee has employee code " + employeeCode + " from department has id " + departmentID + " to department has id " + departmentIdNew + "? (Y/N): ");
                            choose = scanner.nextLine();
                        }

                        if (choose.equals("Y")) {
                            try {
                                Statement statement = jdbcData.getStatement();
                                String sql = "UPDATE employee SET  department_id = " + departmentIdNew + " " +
                                        "WHERE employee_code = '" + employeeCode + "'";
                                statement.executeUpdate(sql);
                                System.out.println("Transfer Employee Successfully!");
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        } else {
                            out.println("Transfer is canceled!");
                            out.println("-----  Back to Department  -----");
                            break;
                        }
                    }
                }
                break;
            } else {
                out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s \n",
                        "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                        "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|");
                out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                out.println("|------- No Employee in Department --------                                                                                                                                                       |");
                out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                out.println("This department cannot be transfer employee because the department has not employees!");
                out.println("Select another department!");
                out.println();
            }
        }
        out.println();
    }

    // add new employee to department
    public void addNewEmployee() {
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

        out.println();

        out.println("-----  List All Employee Do Not Belong To Any Department   -----");

        if (employeeList.size() > 0) {
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            int number = 0;
            String departmentName = "--- None Department ---";
            for (Employee emp : employeeList) {
                number++;
                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s %-25s %-1s \n",
                        "|", number, "|", emp.getEmployeeCode(), "|", emp.getFullName(), "|", emp.getPosition(), "|", emp.getGender(), "|",
                        emp.getAge(), "|", emp.getPhone(), "|", emp.getEmail(), "|", formatDouble(emp.getSalary()), "|",
                        formatDouble(emp.getTax()), "|", emp.getHireDate(), "|", departmentName, "|");
            }
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println();

            out.println("Enter The Employee Code Want To Change Department: ");
            String employeeCode = scanner.nextLine();

            while (!employeeCodeList.contains(employeeCode)) {
                out.println("Employee code does not belong to this department. " +
                        "Re-enter employee code according to the list above!");
                out.println("Enter the employee code want to change department: ");
                employeeCode = scanner.nextLine();
            }

            out.println("Select a Department ID Want To Add New Employee");
            for (Department dep : departmentList) {
                out.println("   " + dep.getId() + ". " + dep.getDepartmentName());
            }
            out.println("Enter Department ID: ");
            int departmentID = checkInputInt(scanner);

            while (!idDepartmentList.contains(departmentID)) {
                out.println("------- No Find Department ------");
                out.println("Enter Department ID: ");
                departmentID = checkInputInt(scanner);
            }

            out.println("Would you like to add employee has employee code " + employeeCode + " into the department has department id " + departmentID + "? (Y/N): ");
            String choose = scanner.nextLine();
            while (!choose.equals("Y") && !choose.equals("N")) {
                out.println("Must be input Y or N");
                out.println("Would you like to add employee has employee code " + employeeCode + " into the department has department id " + departmentID + "? (Y/N): ");
                choose = scanner.nextLine();
            }

            if (choose.equals("Y")) {
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
                out.println("Add new employee is canceled");
                out.println();
            }

        } else {
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println("--- No New Employee ---                                                                                                                                                                                                        ");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println();
            out.println("-----  Back to Department  -----");

        }

        out.println();
    }
}
