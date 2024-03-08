package firstCrud;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeJdbcDao {


    public List<Employee> getAll() {
        List<Employee> listaTotal = new ArrayList<>();

        String SQL_SELECT_ALL = "SELECT * FROM EMPLOYEE";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC", "root", "castelao");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                BigDecimal salary = resultSet.getBigDecimal("SALARY");
                Timestamp createdDate = resultSet.getTimestamp("CREATED_DATE");

                Employee obj = new Employee();
                obj.setId(id);
                obj.setName(name);
                obj.setSalary(salary);
                obj.setCreatedDate(createdDate.toLocalDateTime());

                listaTotal.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaTotal;
    }
    
    private  PreparedStatement getPreparedStatement(String sql) throws SQLException {
    	Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC", "root", "castelao");
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
			return preparedStatement;
    }

    public  List<Employee> getEmployeesWithLessSalary(Double salaryCondition) {
        List<Employee> filteredEmployees = new ArrayList<>();

        String SQL_SELECT_LESS_SALARY = "SELECT * FROM EMPLOYEE WHERE SALARY < ?";

        try (PreparedStatement preparedStatement=  getPreparedStatement(SQL_SELECT_LESS_SALARY)) {

            preparedStatement.setDouble(1, salaryCondition);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                BigDecimal salary = resultSet.getBigDecimal("SALARY");
                Timestamp createdDate = resultSet.getTimestamp("CREATED_DATE");

                Employee obj = new Employee();
                obj.setId(id);
                obj.setName(name);
                obj.setSalary(salary);
                obj.setCreatedDate(createdDate.toLocalDateTime());

                filteredEmployees.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredEmployees;
    }
    
}

