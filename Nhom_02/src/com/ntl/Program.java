package com.ntl;

import com.ntl.function.FunctionDepartment;
import com.ntl.function.FunctionEmployee;
import com.ntl.function.FunctionHomePage;
import com.ntl.function.FunctionStatistic;

import static java.lang.System.*;

public class Program {
    public static void main(String[] args) {
        // Login
        FunctionHomePage functionHomePage = new FunctionHomePage();
        functionHomePage.loginAdmin();

        // Home page
        while (true) {
            int h = functionHomePage.homePage();

            // Employee
            if(h == 1) {
                while (true) {
                    FunctionEmployee functionEmployee = new FunctionEmployee();
                    // Employee page
                    int emp = functionEmployee.employeePage();

                    // Employee option
                    if(emp == 1) {
                        functionEmployee.showDataEmployee();
                    } else if(emp == 2) {
                        functionEmployee.insertDataEmployee();
                    } else if(emp == 3) {
                        functionEmployee.updateDataEmployee();
                    } else if(emp == 4) {
                        functionEmployee.deleteDataEmployee();
                    } else if(emp == 5) {
                        functionEmployee.searchDataEmployee();
                    } else {
                        out.println("Back to Home!");
                        break;
                    }
                }
            } else if(h == 2) {
                while (true) {
                    FunctionDepartment functionDepartment = new FunctionDepartment();

                    // Department page
                    int dep = functionDepartment.departmentPage();

                    // Department option
                    if(dep == 1) {
                        functionDepartment.showDataDepartment();
                    } else if(dep == 2) {
                        functionDepartment.insertDataDepartment();
                    } else if(dep == 3) {
                        functionDepartment.updateDataDepartment();
                    } else if(dep == 4) {
                        functionDepartment.deleteDataDepartment();
                    } else if(dep == 5) {
                        functionDepartment.transferEmployee();
                    } else if(dep == 6) {
                        functionDepartment.addNewEmployee();
                    } else {
                        out.println("Back to Home!");
                        break;
                    }
                }
            } else if(h == 3) {
                while (true) {
                    FunctionStatistic functionStatistic = new FunctionStatistic();

                    // Statistic page
                    int dep = functionStatistic.statisticPage();

                    // Statistic option
                    if(dep == 1) {
                        functionStatistic.statistic1();
                    } else if(dep == 2) {
                        functionStatistic.statistic2();
                    } else if(dep == 3) {
                        functionStatistic.statistic3();
                    } else if(dep == 4) {
                        functionStatistic.statistic4();
                    } else if(dep == 5) {
                        functionStatistic.statistic5();
                    } else {
                        out.println("Back to Home!");
                        break;
                    }
                }
            } else {
                out.println("Exit Program!");
                break;
            }
        }
    }
}
