package sample.example.collections;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class ListFilterVsMap {

	public static class Employee {
		private String id;
		private String name;
		private int age;
		private double salary;
		
		public Employee(String id, String name, int age, double salary) {
			super();
			this.id = id;
			this.name = name;
			this.age = age;
			this.salary = salary;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		public double getSalary() {
			return salary;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Employee other = (Employee) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
		
		
	}
	
	public static void main(String[] args) {
		int size = 10000000;
		List<Employee> employees = new LinkedList<>();
		for(int i=0; i<size; i++) {
			String id = "id" + i;
			String name = "Employee" + i;
			int age = 20 + new Double(Math.random()*10).intValue();
			double salary = 100000 + 1000000 * Math.random();
			employees.add(new Employee(id, name, age, salary));
			if(i%(size/10)==0) {
				System.out.println("Added " + i);
			}
		}
		
		long start = System.currentTimeMillis();
		long count = employees.stream().filter(e -> e.getAge()>25 && e.getAge()<27).count();
		long end = System.currentTimeMillis();
		System.out.println("List filtering  : count=" + count +" t=" + (end-start));
		
		TreeMap<Integer, List<Employee>> employeeSortedByAge = new TreeMap<>();
		for(Employee emp : employees) {
			if(!employeeSortedByAge.containsKey(emp.age)) {
				employeeSortedByAge.put(emp.age, new LinkedList<Employee>());
			}
			employeeSortedByAge.get(emp.age).add(emp);
		}
		
		start = System.currentTimeMillis();
		count = 0;
//		for(List<Employee> emps : employeeSortedByAge.subMap(25+1, 27).values()) {
		for(List<Employee> emps : employeeSortedByAge.headMap(27, false).tailMap(25, false).values()) {
			count += emps.size();
		}
		end = System.currentTimeMillis();
		System.out.println("Ordered collection filtering  : count=" + count +" t=" + (end-start));
		
	}

}
