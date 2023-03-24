package com.ntl.entity;

public class Employee {
    private String employeeCode;
    private String fullName;
    private String position;
    private String gender;
    private int age;
    private String phone;
    private String email;
    private double salary;
    private double tax;
    private String hireDate;
    private int departmentID;
    private String isManager;


    @Override
    public String toString() {
        return "Employee{" +
                "employeeCode='" + employeeCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", position='" + position + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", tax=" + tax +
                ", hireDate='" + hireDate + '\'' +
                ", departmentID=" + departmentID +
                ", isManager='" + isManager + '\'' +
                '}';
    }

    public Employee(String employeeCode, String fullName, String position, String gender, int age, String phone, String email, double salary, double tax, String hireDate, int departmentID, String isManager) {
        this.employeeCode = employeeCode;
        this.fullName = fullName;
        this.position = position;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.tax = tax;
        this.hireDate = hireDate;
        this.departmentID = departmentID;
        this.isManager = isManager;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }
}
