package com.ntl.function;

import com.ntl.database.JdbcData;
import com.ntl.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.*;

public class FunctionEmployee {
    JdbcData jdbcData = new JdbcData();

    // enum gender
    public enum Gender {
        MALE,
        FEMALE
    }

    // enum position
    public enum Position {
        STAFF,
        FRESHER,
        INTERN
    }

    // Class PositionSalary
    public class PositionSalary {
        private static final double MANAGER_SALARY = 20000000;
        private static final double STAFF_SALARY = 15000000;
        private static final double FRESHER_SALARY = 12000000;
        private static final double INTERN_SALARY = 9000000;
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
                out.println("Enter again: ");

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
            out.println("Enter another employee code again: ");
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
            out.println("Enter another employee phone again: ");
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
            out.println("Enter another employee email again: ");
            employeeEmail = scanner.nextLine();
        }
        return employeeEmail;
    }

    // check input gender
    private int checkInputGender(int check, Scanner scanner) {
        scanner = new Scanner(in);
        List<String> genderList = Arrays.asList(Gender.MALE.name(), Gender.FEMALE.name());

        while (check < 1 || check > genderList.size()) {
            out.println("Must be input 1 - " + genderList.size() + "!");
            out.println("Enter Positon Again: ");
            check = checkInputInt(scanner);
        }
        return check;
    }

    // check input position
    private int checkInputPosition(int check, Scanner scanner) {
        scanner = new Scanner(in);
        List<String> positionList = Arrays.asList(
                Position.STAFF.name(), Position.FRESHER.name(), Position.FRESHER.name());

        while (check < 1 || check > positionList.size()) {
            out.println("Must be input 1 - " + positionList.size() + "!");
            out.println("Enter Positon Again: ");
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

        while (check != 0 && !idDepartmentList.contains(check)) {
            out.println("Must be input is number in list department");
            out.println("Enter Department ID Again: ");
            check = checkInputInt(scanner);
        }
        return check;
    }

    // check is manager
    private String checkInputisManager(Integer departmentID, Scanner scanner) {
        scanner = new Scanner(System.in);
        String checkManager = scanner.nextLine();
        String isManager = null;
        if (checkManager.equals("Y")) {
            List<Employee> employeeList = new ArrayList<>();

            try {
                Statement statement = jdbcData.getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

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
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for(Employee emp : employeeList) {
                if(emp.getIsManager() == null) {
                    break;
                } else {
                    while (departmentID == emp.getDepartmentID()) {
                        out.println("This employee cannot be selected as the manager because there is already a manager here!");
                        out.println("Enter Is Manager Again (Y/N): ");
                        checkManager = scanner.nextLine();
                        if (checkManager.equals("N")) {
                            return isManager;
                        }
                    }
                }
            }
            isManager = "'1'";
            return isManager;
        } else {
            return isManager;
        }
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
        String pattern = "###,###.##";
        Locale locale = new Locale("vni", "VN");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat
                .getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        String res = decimalFormat.format(num);
        return res;
    }

    // employee page
    public int employeePage() {
        out.println("-----  HUMAN RESOURCE MANAGEMENT  -----");
        out.println("---------------------------------------");
        out.println("               EMPLOYEE                ");
        out.println("1. Show All Employee");
        out.println("2. Insert Employee");
        out.println("3. Update Employee");
        out.println("4. Delete Employee");
        out.println("5. Search Employee");
        out.println("6. Back to Home");
        out.println("---------------------------------------");
        Scanner scanner2 = new Scanner(in);
        out.println("Enter Option Number 1 - 6: ");
        int emp = checkInputInt(scanner2);
        while (emp < 1 || emp > 6) {
            out.println("Must be input the option 1 - 6!");
            out.println("Enter again: ");
            emp = checkInputInt(scanner2);
        }
        out.println();
        return emp;
    }

    // show data employee
    public void showDataEmployee() {
        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT emp.*, dep.department_name FROM employee emp " +
                    "LEFT JOIN department dep " +
                    "ON emp.department_id = dep.id ORDER BY hire_date");

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            int number = 0;
            if (!resultSet.isBeforeFirst()) {
                out.println("|----- No Data -----                                                                                                                                                                                                          |");
                out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                out.println();
            } else {
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

                    String departmentName = null;
                    if (resultSet.getString("department_name") == null) {
                        departmentName = "--- None Department ---";
                    } else {
                        departmentName = resultSet.getString("department_name");
                    }


                    out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                            "|", number, "|", employeeCode, "|", fullName, "|", position, "|", gender, "|", age, "|", phone, "|", email, "|",
                            formatDouble(salary), "|", formatDouble(tax), "|", hireDate, "|", departmentName, "|");
                }
            }
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // insert data employee
    public void insertDataEmployee() {
        Scanner scanner = new Scanner(in);
        out.println("Enter Employee Code: ");
        String employeeCode = checkInputEmployeeCode(scanner);
        out.println("Enter Full Name: ");
        String fullName = scanner.nextLine();

        out.println("Select a Position: ");
        out.println("   1. " + Position.STAFF.name());
        out.println("   2. " + Position.FRESHER.name());
        out.println("   3. " + Position.INTERN.name());
        out.println("Enter Positon 1 - 3: ");
        int positionCheck = checkInputInt(scanner);
        positionCheck = checkInputPosition(positionCheck, scanner);

        out.println("Select Gender: ");
        out.println("   1. " + Gender.MALE.name());
        out.println("   2. " + Gender.FEMALE.name());
        out.println("Enter Gender 1 - 2: ");
        int genderCheck = checkInputInt(scanner);
        genderCheck = checkInputGender(genderCheck, scanner);

        String gender = null;
        if(genderCheck == 1) {
            gender = Gender.MALE.name();
        } else  {
            gender = Gender.FEMALE.name();
        }

        out.println("Enter Age: ");
        Integer age = checkInputInt(scanner);

        out.println("Enter Phone: ");
        String phone = checkInputPhone(scanner);

        out.println("Enter Email: ");
        String email = checkInputEmployeeEmail(scanner);

        Date dNow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String hireDate = format.format(dNow);

        out.println("Select a Department");
        out.println("   0. Not select department");

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("Select id, department_name FROM department");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String departmentName = resultSet.getString("department_name");
                out.println("   " + id + ". " + departmentName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("Enter Department ID: ");
        Integer departmentID = checkInputInt(scanner);
        departmentID = checkInputDepartment(departmentID, scanner);
        String isManager = null;

        if(departmentID == 0) {
            departmentID = null;
        } else if (positionCheck == 2 || positionCheck == 3) {
            isManager = null;
        } else {
            out.println("Enter Is Manager (Y/N): ");
            isManager = checkInputisManager(departmentID, scanner);
        }

        String position = null;
        double salary = 0;
        double tax  = 0;

        if (isManager == null) {
            if (positionCheck == 1) {
                position = Position.STAFF.name();
                salary = PositionSalary.STAFF_SALARY;
                tax = calculateTax(salary);
            } else if (positionCheck == 2) {
                position = Position.FRESHER.name();
                salary = PositionSalary.FRESHER_SALARY;
                tax = calculateTax(salary);
            } else {
                position = Position.INTERN.name();
                salary = PositionSalary.INTERN_SALARY;
                tax = calculateTax(salary);
            }
        } else {
            position = Position.STAFF.name();
            salary = PositionSalary.MANAGER_SALARY;
            tax = calculateTax(salary);
        }

        out.println("Would you like to insert this employee's information into the system? (Y/N): ");
        String choose = scanner.nextLine();
        while (!choose.equals("Y") && !choose.equals("N")) {
            out.println("Must be input Y or N");
            out.println("Would you like to insert this employee's information into the system? (Y/N): ");
            choose = scanner.nextLine();
        }

        if(choose.equals("Y")) {
            try {
                Statement statement = jdbcData.getStatement();
                String sql = "INSERT INTO employee (employee_code, full_name, position, gender, age, phone, email, salary, tax, hire_date, " +
                        "department_id, is_manager) " +
                        "VALUES ('" + employeeCode + "', '" + fullName + "', '" + position + "', '" + gender + "', '" + age + "', '" + phone + "'," +
                        "'" + email + "', '" + salary + "', '" + tax + "', '" + hireDate + "', " + departmentID + ", " + isManager + ")";
                statement.executeUpdate(sql);
                System.out.println("Insert employee's information successfully!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            out.println("-----  Back to Employee   -----");
        }
        out.println();
    }

    // update data employee
    public void updateDataEmployee() {
        showDataEmployee();
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

        out.println("Enter Employee Code Need Update: ");
        String employeeCode = scanner.nextLine();
        while (!employeeCodeList.contains(employeeCode)) {
            out.println("------- No Find Employee ------");
            out.println("Enter Employee Code Need Update: ");
            employeeCode = scanner.nextLine();
        }

        Integer departmentId = null;
        int number = 0;

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeeByCode = statement.executeQuery("SELECT emp.*, dep.department_name FROM employee emp LEFT JOIN department dep " +
                    "ON emp.department_id = dep.id WHERE employee_code = '" + employeeCode + "'");

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (getEmployeeByCode.next()) {
                number ++;
                String fullName = getEmployeeByCode.getString("full_name");
                String position = getEmployeeByCode.getString("position");
                String gender = getEmployeeByCode.getString("gender");
                int age = getEmployeeByCode.getInt("age");
                String phone = getEmployeeByCode.getString("phone");
                String email = getEmployeeByCode.getString("email");
                double salary = getEmployeeByCode.getDouble("salary");
                double tax = getEmployeeByCode.getDouble("tax");
                String hireDate = getEmployeeByCode.getString("hire_date");
                departmentId = getEmployeeByCode.getInt("department_id");
                String departmentName = null;
                if (getEmployeeByCode.getString("department_name") == null) {
                    departmentName = "--- None Department ---";
                } else {
                    departmentName = getEmployeeByCode.getString("department_name");
                }

                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s %-25s %-1s \n",
                        "|", number, "|", employeeCode, "|", fullName, "|", position, "|", gender, "|", age, "|", phone, "|", email, "|",
                        formatDouble(salary), "|", formatDouble(tax), "|", hireDate, "|", departmentName, "|");
            }
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            out.println("List Information Can Update");
            out.println("   1. Full Name");
            out.println("   2. Position");
            out.println("   3. Gender");
            out.println("   4. Age");
            out.println("   5. Phone");
            out.println("   6. Back to Employee");

            String sql = null;
            String info = null;

            out.println("Enter Infomation Want To Update: ");
            int op = checkInputInt(scanner);

            while (op < 1 || op > 6) {
                out.println("Entered wrong information!");
                out.println("Only enter option 1 - 6!");
                out.println("Enter Infomation Want To Update: ");
                op = checkInputInt(scanner);
            }
            if (op == 1) {
                info = "full name";
                out.println("Enter Full Name Update: ");
                String fullName = scanner.nextLine();
                sql = "UPDATE employee SET full_name = '" + fullName + "' WHERE employee_code = '" + employeeCode + "'";

            } else if (op == 2) {
                info = "position";
                out.println("Select a Position Update: ");
                out.println("   1. " + Position.STAFF.name());
                out.println("   2. " + Position.FRESHER.name());
                out.println("   3. " + Position.INTERN.name());
                out.println("Enter Positon 1 - 3: ");
                int positionCheck = checkInputInt(scanner);
                positionCheck = checkInputPosition(positionCheck, scanner);

                String positon = null;
                double salary = 0;
                double tax  = 0;
                String isManager = null;

                if (departmentId == 0) {
                    if (positionCheck == 1) {
                        positon = Position.STAFF.name();
                        salary = PositionSalary.STAFF_SALARY;
                        tax = calculateTax(salary);
                    } else if (positionCheck == 2) {
                        positon = Position.FRESHER.name();
                        salary = PositionSalary.FRESHER_SALARY;
                        tax = calculateTax(salary);
                    } else {
                        positon = Position.INTERN.name();
                        salary = PositionSalary.INTERN_SALARY;
                        tax = calculateTax(salary);
                    }
                    sql = "UPDATE employee SET position = '" + positon + "', salary = '" + salary + "'," +
                            "tax = '" + tax + "' WHERE employee_code = '" + employeeCode + "'";
                } else {
                    if (positionCheck == 1) {
                        out.println("Enter Is Manager (Y/N): ");
                        isManager = checkInputisManager(departmentId, scanner);
                        positon = Position.STAFF.name();
                        if(isManager == null) {
                            salary = PositionSalary.STAFF_SALARY;
                            tax = calculateTax(salary);
                        } else {
                            salary = PositionSalary.MANAGER_SALARY;
                            tax = calculateTax(salary);
                        }
                        sql = "UPDATE employee SET position = '" + positon + "', salary = '" + salary + "'," +
                                "tax = '" + tax + "', is_manager = " + isManager + " WHERE employee_code = '" + employeeCode + "'";
                    } else if (positionCheck == 2) {
                        positon = Position.FRESHER.name();
                        salary = PositionSalary.FRESHER_SALARY;
                        tax = calculateTax(salary);
                        sql = "UPDATE employee SET position = '" + positon + "', salary = '" + salary + "'," +
                                "tax = '" + tax + "' WHERE employee_code = '" + employeeCode + "'";
                    } else {
                        positon = Position.INTERN.name();
                        salary = PositionSalary.INTERN_SALARY;
                        tax = calculateTax(salary);
                        sql = "UPDATE employee SET position = '" + positon + "', salary = '" + salary + "'," +
                                "tax = '" + tax + "' WHERE employee_code = '" + employeeCode + "'";
                    }
                }


            } else if(op == 3) {
                info = "gender";
                out.println("Select Gender: ");
                out.println("   1. " + Gender.MALE.name());
                out.println("   2. " + Gender.FEMALE.name());
                out.println("Enter Gender 1 - 2: ");
                int genderCheck = checkInputInt(scanner);
                genderCheck = checkInputGender(genderCheck, scanner);

                String gender = null;
                if(genderCheck == 1) {
                    gender = Gender.MALE.name();
                } else  {
                    gender = Gender.FEMALE.name();
                }
                sql = "UPDATE employee SET gender = '" + gender + "' WHERE employee_code = '" + employeeCode + "'";
            } else if(op == 4) {
                info = "age";
                out.println("Enter Age: ");
                Integer age = checkInputInt(scanner);
                sql = "UPDATE employee SET age = '" + age + "' WHERE employee_code = '" + employeeCode + "'";
            } else if(op == 5){
                info = "phone";
                out.println("Enter Phone: ");
                String phone = checkInputPhone(scanner);
                sql = "UPDATE employee SET phone = '" + phone + "' WHERE employee_code = '" + employeeCode + "'";
            } else {
                out.println("----- Back to Employee -----");
                break;
            }

            out.println("Would you like to update the " + info  + " information of an employee has employee code " + employeeCode + "? (Y/N): ");
            String choose = scanner.nextLine();
            while (!choose.equals("Y") && !choose.equals("N")) {
                out.println("Must be input Y or N");
                out.println("Would you like to update the " + info  + " information of an employee has employee code " + employeeCode + "? (Y/N): ");
                choose = scanner.nextLine();
            }

            if (choose.equals("Y")) {
                try {
                    Statement statement = jdbcData.getStatement();
                    statement.executeUpdate(sql);
                    System.out.println("Update employee's information successfully!");
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

    // delete data employee
    public void deleteDataEmployee() {
        showDataEmployee();
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

        out.println("Enter Employee Code Need Delete: ");
        String employeeCode = scanner.nextLine();
        while (!employeeCodeList.contains(employeeCode)) {
            out.println("------- No Find Employee ------");
            out.println("Enter Employee Code Need Delete: ");
            employeeCode = scanner.nextLine();
        }

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet getEmployeeByCode = statement.executeQuery("SELECT emp.*, dep.department_name FROM employee emp LEFT JOIN department dep " +
                    "ON emp.department_id = dep.id WHERE employee_code = '" + employeeCode + "'");

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            int number = 0;
            while (getEmployeeByCode.next()) {
                number ++;
                String fullName = getEmployeeByCode.getString("full_name");
                String position = getEmployeeByCode.getString("position");
                String gender = getEmployeeByCode.getString("gender");
                int age = getEmployeeByCode.getInt("age");
                String phone = getEmployeeByCode.getString("phone");
                String email = getEmployeeByCode.getString("email");
                double salary = getEmployeeByCode.getDouble("salary");
                double tax = getEmployeeByCode.getDouble("tax");
                String hireDate = getEmployeeByCode.getString("hire_date");

                String departmentName = null;
                if (getEmployeeByCode.getString("department_name") == null) {
                    departmentName = "--- None Department ---";
                } else {
                    departmentName = getEmployeeByCode.getString("department_name");
                }

                out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                "%-1s %-25s %-1s %-25s %-1s \n",
                        "|", number, "|", employeeCode, "|", fullName, "|", position, "|", gender, "|", age, "|", phone, "|", email, "|",
                        formatDouble(salary), "|", formatDouble(tax), "|", hireDate, "|", departmentName, "|");
            }
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("Would you like to delete the information of an employee has employee code " + employeeCode + "? (Y/N): ");
        String choose = scanner.nextLine();
        while (!choose.equals("Y") && !choose.equals("N")) {
            out.println("Must be input Y or N");
            out.println("Would you like to delete the information of an employee has employee code " + employeeCode + "? (Y/N): ");
            choose = scanner.nextLine();
        }

        if (choose.equals("Y")) {
            try {
                Statement statement = jdbcData.getStatement();
                String sql = "DELETE FROM employee WHERE employee_code = '" + employeeCode + "'";
                statement.executeUpdate(sql);
                System.out.println("Delete employee's information successfully!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            out.println("Delete is canceled!");
            out.println("-----  Back to Employee   -----");
        }
        out.println();
    }

    // search data employee
    public void searchDataEmployee() {
        Scanner scanner = new Scanner(in);
        out.println("Enter Info Need Search: ");
        String word = scanner.nextLine();

        try {
            Statement statement = jdbcData.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT emp.*, dep.department_name FROM employee emp LEFT JOIN department dep " +
                    "ON emp.department_id = dep.id  WHERE emp.full_name LIKE '%" + word + "%'" +
                    "OR emp.employee_code LIKE '%" + word + "%' OR emp.phone LIKE '%" + word + "%' OR emp.email LIKE '%" + word + "%'" +
                    "OR dep.department_name LIKE '%" + word + "%' ORDER BY hire_date");

            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                            "%-1s %-25s %-1s %-25s %-1s \n",
                    "|", "No.", "|", "Employee Code", "|", "Full Name", "|", "Position", "|", "Gender", "|", "Age", "|", "Phone", "|",
                    "Email", "|", "Salary", "|", "Tax", "|", "Hire Date", "|", "Department Name", "|");
            out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            if (!resultSet.isBeforeFirst()) {
                out.println("|----- No Data -----                                                                                                                                                                                                          |");
                out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                out.println();
            } else {
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

                    String departmentName = null;
                    if (resultSet.getString("department_name") == null) {
                        departmentName = "--- None Department ---";
                    } else {
                        departmentName = resultSet.getString("department_name");
                    }

                    out.printf("%-1s %-3s %-1s %-15s %-1s %-20s %-1s %-15s %-1s %-10s %-1s %-3s %-1s %-15s %-1s %-25s %-1s %-15s %-1s %-15s " +
                                    "%-1s %-25s %-1s %-25s %-1s \n",
                            "|", number, "|", employeeCode, "|", fullName, "|", position, "|", gender, "|", age, "|", phone, "|", email, "|",
                            formatDouble(salary), "|", formatDouble(tax), "|", hireDate, "|", departmentName, "|");
                }

                out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                out.println();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
