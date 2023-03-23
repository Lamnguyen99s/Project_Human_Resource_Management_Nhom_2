package com.ntl.function;

import com.ntl.database.JdbcData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.in;
import static java.lang.System.out;

public class FunctionEmployee {
    JdbcData jdbcData = new JdbcData();

    // enum position
    public enum Position {
        MANAGER,
        EMPLOYEE1,
        EMPLOYEE2,
    }

    // Class PositionSalary
    public class PositionSalary {
        private static final double MANAGER_SALARY = 20000000;
        private static final double EMPLOYEE1_SALARY = 10000000;
        private static final double EMPLOYEE2_SALARY = 15000000;
    }

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

    // check input double
    private double checkInputDouble(Scanner scanner) {
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

    // check input employee_code
    private String checkInputEmployeeCode(Scanner scanner) {
        scanner = new Scanner(System.in);
        String employeeCode = scanner.nextLine();
        List <String> employeeCodeList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeePhone = statement.executeQuery("SELECT employee_code FROM employee");

            while (getEmployeePhone.next()) {
                employeeCodeList.add(getEmployeePhone.getString("employee_code"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (employeeCodeList.contains(employeeCode)) {
            out.println("Employee code already exists!");
            out.print("Enter another employee code again: ");
            employeeCode = scanner.nextLine();
        }
        return employeeCode;
    }

    // check input phone
    private String checkInputPhone(Scanner scanner) {
        scanner = new Scanner(System.in);
        String employeePhone = scanner.nextLine();
        List <String> employeePhoneList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeePhone = statement.executeQuery("SELECT phone FROM employee");

            while (getEmployeePhone.next()) {
                employeePhoneList.add(getEmployeePhone.getString("phone"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (employeePhoneList.contains(employeePhone)) {
            out.println("Employee phone already exists!");
            out.print("Enter another employee phone again: ");
            employeePhone = scanner.nextLine();
        }
        return employeePhone;
    }

    // check input email
    private String checkInputEmployeeEmail(Scanner scanner) {
        scanner = new Scanner(System.in);
        String employeeEmail = scanner.nextLine();
        List <String> employeeEmailList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeeEmail = statement.executeQuery("SELECT email FROM employee");

            while (getEmployeeEmail.next()) {
                employeeEmailList.add(getEmployeeEmail.getString("email"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (employeeEmailList.contains(employeeEmail)) {
            out.println("Employee email already exists!");
            out.print("Enter another employee email again: ");
            employeeEmail = scanner.nextLine();
        }
        return employeeEmail;
    }

    // check input gender
    private String checkInputGender(Scanner scanner) {
        scanner = new Scanner(in);
        String result = scanner.nextLine();
        while (!result.equals("MALE") && !result.equals("FEMALE")) {
            out.println("Must be input MALE or FEMALE!");
            out.print("Enter Gender Again: ");
            result = scanner.nextLine();
        }
        return result;
    }

    // check input position
    private int checkInputPosition(int check, Scanner scanner) {
        scanner = new Scanner(in);
        List<String> positionList = Arrays.asList(
                Position.MANAGER.name(), Position.EMPLOYEE1.name(), Position.EMPLOYEE2.name());

        while (check < 1 || check > positionList.size()) {
            out.println("Must be input 1 - " + positionList.size() + "!");
            out.print("Enter Positon Again: ");
            check = checkInputInt(scanner);
        }
        return check;
    }

    // check input department
    private int checkInputDepartment(int check, Scanner scanner) {
        scanner = new Scanner(in);
        List<Integer> idDepartmentList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM department");

            while (resultSet.next()) {
               idDepartmentList.add( resultSet.getInt("id"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (check < 0 || check > idDepartmentList.size()) {
            out.println("Must be input 0 - " + idDepartmentList.size() + "!");
            out.print("Enter Department ID Again: ");
            check = checkInputInt(scanner);
        }
        return check;
    }

    // check is manager
    private String checkInputisManager(Integer departmentID, Scanner scanner) {
        scanner = new Scanner(System.in);
        String checkManager = scanner.nextLine();
        String isManager = null;
        if (checkManager.equals("Yes")) {
            Map<Integer, String> mapManager = new HashMap<>();

            try {
                Statement statement = jdbcData.getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT department_id, is_manager FROM employee");

                while (resultSet.next()) {
                    mapManager.put(resultSet.getInt("department_id"),
                            resultSet.getString("is_manager"));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for(Map.Entry<Integer, String> entry : mapManager.entrySet()) {
                while (departmentID == entry.getKey() && entry.getValue().equals("1")) {
                    out.println("This employee cannot be selected as the manager because there is already a manager here!");
                    out.print("Enter Is Manager Again (Yes or No): ");
                    checkManager = scanner.nextLine();
                    if (checkManager.equals("No")) {
                        return null;
                    }
                }
            }
            return "'1'";
        }

        return isManager;
    }

    // calculate tax from salary
    private double calculateTax (double salary) {
        double tax = 0;
        double check = salary - 11000000;
        if (check <= 0) {
            tax = 0;
        } else if (check > 0 && check <= 5000000) {
            tax = check * 0.05;
        } else if (check > 5000000 && check <= 10000000) {
            tax = 250000.0 + (check - 5000000.0) * 0.1;
        } else if (check > 10000000 && check <= 18000000) {
            tax = 250000 + 500000 + (check - 10000000) * 0.15;
        } else if (check > 18000000 && check <= 32000000) {
            tax = 250000 + 500000 + 1200000 + (check - 18000000) * 0.2;
        } else if (check > 32000000 && check <= 52000000) {
            tax = 250000 + 500000 + 1200000 + 2800000 + (check - 32000000) * 0.25;
        } else if (check > 52000000 && check <= 80000000) {
            tax = 250000 + 500000 + 1200000 + 2800000 + 5000000 + (check - 52000000) * 0.3;
        } else {
            tax =  250000 + 500000 + 1200000 + 2800000 + 5000000 + 8400000 + (check - 80000000) * 0.35;
        }
        return tax;
    }

    // format double to string
    private String formatDouble(double num) {
        DecimalFormat df = new DecimalFormat("#,##0");
        String formattedNum = df.format(num);
        return formattedNum;
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

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee ORDER BY hire_date");

            out.printf("%-3s %-15s %-20s %-15s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Id",
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
                    int age = resultSet.getInt("age");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    double salary = resultSet.getDouble("salary");
                    double tax = resultSet.getDouble("tax");
                    String hireDate = resultSet.getString("hire_date");

                    out.printf("%-3s %-15s %-20s %-15s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", id1,
                            employeeCode, fullName, position, gender, age, phone, email, formatDouble(salary),
                            formatDouble(tax), hireDate);
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
        String employeeCode = checkInputEmployeeCode(scanner);
        out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();
        out.println("Select a Position: ");
        out.println("1. " + Position.MANAGER.name());
        out.println("2. " + Position.EMPLOYEE1.name());
        out.println("3. " + Position.EMPLOYEE2.name());
        out.print("Enter Positon 1 - 3: ");
        int positionCheck = checkInputInt(scanner);
        positionCheck = checkInputPosition(positionCheck, scanner);

        String position = null;
        double salary = 0;
        double tax  = 0;

        if (positionCheck == 1) {
            position = Position.MANAGER.name();
            salary = PositionSalary.MANAGER_SALARY;
            tax = calculateTax(salary);
        } else if (positionCheck == 2) {
            position = Position.EMPLOYEE1.name();
            salary = PositionSalary.EMPLOYEE1_SALARY;
            tax = calculateTax(salary);
        } else {
            position = Position.EMPLOYEE2.name();
            salary = PositionSalary.EMPLOYEE2_SALARY;
            tax = calculateTax(salary);
        }

        out.print("Enter Gender (MALE or FEMALE): ");
        String gender = checkInputGender(scanner);
        out.print("Enter Age: ");
        Integer age = checkInputInt(scanner);
        out.print("Enter Phone: ");
        String phone = checkInputPhone(scanner);
        out.print("Enter Email: ");
        String email = checkInputEmployeeEmail(scanner);


        Date dNow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String hireDate = format.format(dNow);

        out.println("Select a Department");
        out.println("0. Not select department");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("Select id, department_name FROM department");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String departmentName = resultSet.getString("department_name");
                out.println(id + ". " + departmentName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print("Enter Department ID: ");
        Integer departmentID = checkInputInt(scanner);
        departmentID = checkInputDepartment(departmentID, scanner);
        if(departmentID == 0) {
            departmentID = null;
        }

        out.print("Enter Is Manager (Yes or No): ");
        String isManager = checkInputisManager(departmentID, scanner);

        try {
            Statement statement = jdbcData.getStatement();
            String sql = "INSERT INTO employee (employee_code, full_name, position, gender, age, phone, email, salary, tax, hire_date, " +
                    "department_id, is_manager) " +
                    "VALUES ('" + employeeCode + "', '" + fullName + "', '" + position + "', '" + gender + "', '" + age + "', '" + phone + "'," +
                    "'" + email + "', '" + salary + "', '" + tax + "', '" + hireDate + "', " + departmentID + ", " + isManager + ")";
            statement.executeUpdate(sql);
            System.out.println("Insert data successfully");
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
        List <String> employeeCodeList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeeCode = statement.executeQuery("SELECT employee_code FROM employee");

            while (getEmployeeCode.next()) {
                employeeCodeList.add(getEmployeeCode.getString("employee_code"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.print("Enter Employee Code Need Update: ");
        String employeeCodeOld = scanner.nextLine();
        while (!employeeCodeList.contains(employeeCodeOld)) {
            out.println("------- No Find Employee ------");
            out.print("Enter Employee Code Need Update: ");
            employeeCodeOld = scanner.nextLine();
        }

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeeByCode = statement.executeQuery("SELECT * FROM employee WHERE employee_code = '" + employeeCodeOld + "'");

            while (getEmployeeByCode.next()) {
                out.println("Employee Code: " + getEmployeeByCode.getString("employee_code"));
                out.println("Full Name: " + getEmployeeByCode.getString("full_name"));
                out.println("Position: " + getEmployeeByCode.getString("position"));
                out.println("Gender: " + getEmployeeByCode.getString("gender"));
                out.println("Age: " + getEmployeeByCode.getInt("age"));
                out.println("Phone: " + getEmployeeByCode.getString("phone"));
                out.println("Email: " + getEmployeeByCode.getString("email"));
                out.println("Salary: " + formatDouble(getEmployeeByCode.getDouble("salary")));
                out.println("Tax: " + formatDouble(getEmployeeByCode.getDouble("tax")));
                out.println("Hire Date: " + getEmployeeByCode.getString("hire_date"));
                out.println("Department ID: " + getEmployeeByCode.getInt("department_id"));
                out.println("Is Manager: " + getEmployeeByCode.getString("is_manager"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.print("Would you like to update this employee? (Y/N): ");
        String choose = scanner.nextLine();
        while (!choose.equals("Y") && !choose.equals("N")) {
            out.println("Must be input Y or N");
            out.print("Would you like to update this employee? (Y/N): ");
            choose = scanner.nextLine();
        }

        if (choose.equals("Y")) {
            out.print("Enter Employee Code New: ");
            String employeeCodeNew = checkInputEmployeeCode(scanner);
            out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();
            out.println("Select a Position: ");
            out.println("1. " + Position.MANAGER.name());
            out.println("2. " + Position.EMPLOYEE1.name());
            out.println("3. " + Position.EMPLOYEE2.name());
            out.print("Enter Positon 1 - 3: ");
            int positionCheck = checkInputInt(scanner);
            positionCheck = checkInputPosition(positionCheck, scanner);

            String position = null;
            double salary = 0;
            double tax  = 0;

            if (positionCheck == 1) {
                position = Position.MANAGER.name();
                salary = PositionSalary.MANAGER_SALARY;
                tax = calculateTax(salary);
            } else if (positionCheck == 2) {
                position = Position.EMPLOYEE1.name();
                salary = PositionSalary.EMPLOYEE1_SALARY;
                tax = calculateTax(salary);
            } else {
                position = Position.EMPLOYEE2.name();
                salary = PositionSalary.EMPLOYEE2_SALARY;
                tax = calculateTax(salary);
            }

            out.print("Enter Gender (MALE or FEMALE): ");
            String gender = checkInputGender(scanner);
            out.print("Enter Age: ");
            Integer age = checkInputInt(scanner);
            out.print("Enter Phone: ");
            String phone = checkInputPhone(scanner);
            out.print("Enter Email: ");
            String email = checkInputEmployeeEmail(scanner);

            out.println("Select a Department");
            out.println("0. Not select department");

            try {
                Statement statement = jdbcData.getStatement();
                ResultSet resultSet = statement.executeQuery("Select id, department_name FROM department");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String departmentName = resultSet.getString("department_name");
                    out.println(id + ". " + departmentName);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.print("Enter Department ID: ");
            Integer departmentID = checkInputInt(scanner);
            departmentID = checkInputDepartment(departmentID, scanner);
            if(departmentID == 0) {
                departmentID = null;
            }

            out.print("Enter Is Manager (Yes or No): ");
            String isManager = checkInputisManager(departmentID, scanner);

            try {
                Statement statement = jdbcData.getStatement();
                String sql = "UPDATE employee SET employee_code = '" + employeeCodeNew + "', full_name = '" + fullName + "'," +
                        "position = '" + position + "', gender = '" + gender + "', age = '" + age + "', phone = '" + phone + "', email = '" + email + "'," +
                        "salary = '" + salary + "', tax = '" + tax + "', department_id = " + departmentID + ", " +
                        "is_manager = " + isManager + " WHERE employee_code = '" + employeeCodeOld + "'";
                statement.executeUpdate(sql);
                System.out.println("Update records successfully!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            out.println("Back to Employee");
        }
    }

    // delete data employee
    public void deleteDataEmployee() {
        out.println("                                Delete Employee");
        Scanner scanner = new Scanner(in);
        List <String> employeeCodeList = new ArrayList<>();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeeCode = statement.executeQuery("SELECT employee_code FROM employee");

            while (getEmployeeCode.next()) {
                employeeCodeList.add(getEmployeeCode.getString("employee_code"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.print("Enter Employee Code Need Delete: ");
        String employeeCode = scanner.nextLine();
        while (!employeeCodeList.contains(employeeCode)) {
            out.println("------- No Find Employee ------");
            out.print("Enter Employee Code Need Delete: ");
            employeeCode = scanner.nextLine();
        }

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeeByCode = statement.executeQuery("SELECT * FROM employee WHERE employee_code = '" + employeeCode + "'");

            while (getEmployeeByCode.next()) {
                out.println("Employee Code: " + getEmployeeByCode.getString("employee_code"));
                out.println("Full Name: " + getEmployeeByCode.getString("full_name"));
                out.println("Position: " + getEmployeeByCode.getString("position"));
                out.println("Gender: " + getEmployeeByCode.getString("gender"));
                out.println("Age: " + getEmployeeByCode.getInt("age"));
                out.println("Phone: " + getEmployeeByCode.getString("phone"));
                out.println("Email: " + getEmployeeByCode.getString("email"));
                out.println("Salary: " + formatDouble(getEmployeeByCode.getDouble("salary")));
                out.println("Tax: " + formatDouble(getEmployeeByCode.getDouble("tax")));
                out.println("Hire Date: " + getEmployeeByCode.getString("hire_date"));
                out.println("Department ID: " + getEmployeeByCode.getInt("department_id"));
                out.println("Is Manager: " + getEmployeeByCode.getString("is_manager"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.print("Would you like to delete this employee? (Y/N): ");
        String choose = scanner.nextLine();
        while (!choose.equals("Y") && !choose.equals("N")) {
            out.println("Must be input Y or N");
            out.print("Would you like to delete this employee? (Y/N): ");
            choose = scanner.nextLine();
        }

        if (choose.equals("Y")) {
            try {
                Statement statement = jdbcData.getStatement();
                String sql = "DELETE FROM employee WHERE employee_code = '" + employeeCode + "'";
                statement.executeUpdate(sql);
                System.out.println("Delete records successfully!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            out.println("Back To Employee");
        }
    }

    // search data employee
    public void searchDataEmployee() {
        out.println("                                Search Employee");
        Scanner scanner = new Scanner(in);
        out.print("Enter Info Need Search: ");
        String word = scanner.nextLine();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee WHERE full_name LIKE '%" + word + "%'" +
                    "OR employee_code LIKE '%" + word + "%' OR phone LIKE '%" + word + "%' OR email LIKE '%" + word + "%'" +
                    "ORDER BY hire_date");

            out.printf("%-3s %-15s %-20s %-15s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", "Id",
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
                    int age = resultSet.getInt("age");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    double salary = resultSet.getDouble("salary");
                    double tax = resultSet.getDouble("tax");
                    String hireDate = resultSet.getString("hire_date");

                    out.printf("%-3s %-15s %-20s %-15s %-10s %-3s %-15s %-25s %-15s %-15s %-15s \n", id1,
                            employeeCode, fullName, position, gender, age, phone, email, formatDouble(salary),
                            formatDouble(tax), hireDate);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
