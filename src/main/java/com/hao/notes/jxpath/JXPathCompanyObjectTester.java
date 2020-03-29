package com.hao.notes.jxpath;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.junit.Test;

public class JXPathCompanyObjectTester {

    private static Company newCompany() {
        Company company = new Company("Acme Inc.");
        List<Department> departments = new ArrayList<>();
        departments.add(new Department("Sales"));
        departments.add(new Department("Accounting"));

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Johnny", "Sales rep", 45));
        employees.add(new Employee("Sarah", "Sales rep", 33));
        employees.add(new Employee("Magda", "Office assistant", 27));
        departments.get(0).setEmployees(employees);

        employees = new ArrayList<>();
        employees.add(new Employee("Steve", "Head Controller", 51));
        employees.add(new Employee("Peter", "Assistant Controller", 31));
        employees.add(new Employee("Susan", "Office assistant", 27));
        departments.get(1).setEmployees(employees);

        company.setDepartmentList(departments);
        return company;
    }

    @Test
    public void testModifying() {
        Company company = newCompany();
        JXPathContext context = JXPathContext.newContext(company);
        System.out.println(context.getValue("/"));
        context.setValue("/departmentList/employees[name='Johnny']", null);
        System.out.println(context.getValue("/"));
    }

	public static void main(String[] args) {

	    Company company = newCompany();
		JXPathContext context = JXPathContext.newContext(company);

		//Simple JXPath queries
		Company c = (Company)context.getValue(".");
		System.out.println(c);

		//Using predicates
		Employee emp = (Employee)context.getValue("/departmentList/employees[name='Johnny']");
		System.out.println(emp);
		emp = (Employee)context.getValue("/departmentList/employees[name='Susan' and age=27]");
		System.out.println(emp);

		//Using variables
		context.getVariables().declareVariable("name", "Susan");
		context.getVariables().declareVariable("age", new Integer(27));
		emp = (Employee)context.getValue("/departmentList/employees[name=$name and age=$age]");
		System.out.println(emp);

		//Iterating
		for(Iterator iter = context.iterate("/departmentList"); iter.hasNext();){
			Department d = (Department)iter.next();
			System.out.println(d);
		}
		for(Iterator iter = context.iterate("/departmentList/employees"); iter.hasNext();){
			emp = (Employee)iter.next();
			System.out.println(emp);
		}
		for(Iterator iter = context.iterate("/departmentList[name='Sales']/employees[age>30]"); iter.hasNext();){
			emp = (Employee)iter.next();
			System.out.println(emp);
		}

		//Iteratin using variables
		context.getVariables().declareVariable("deptName", "Sales");
		context.getVariables().declareVariable("minAge", new Integer(30));
		for(Iterator iter =  context.iterate("/departmentList[name=$deptName]/employees[age>$minAge]"); iter.hasNext();){
			emp = (Employee)iter.next();
			System.out.println(emp);
		}
	}
}
