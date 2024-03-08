package firstCrud;

import java.util.List;

public class App {

	
	private static EmployeeJdbcDao employeeJdbcDao = new EmployeeJdbcDao();
	
	public static void main(String[] args) {
		App app = new App();
		System.out.println("MySQL JDBC Connection Testing ~");

		// Obtener todos los empleados
		List<Employee> listaTotal = employeeJdbcDao.getAll();
		System.out.println("Lista total de empleados:");
		listaTotal.forEach(e -> System.out.println(
				e.getId() + " Nombre " + e.getName() + " salario " + e.getSalary() + " fecha " + e.getCreatedDate()));

		// Obtener empleados con salario menor a 1500.0
		try {
			listaTotal = employeeJdbcDao.getEmployeesWithLessSalary(2000.0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Empleados con salario menor a 2000.0:");
		listaTotal.forEach(e -> System.out.println(e.getSalary() + "nombre" + e.getName()));
	}
	
	

}
