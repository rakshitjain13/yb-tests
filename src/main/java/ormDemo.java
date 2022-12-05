
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ormDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml" ).buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            System.out.println("Connected to the YugabyteDB Cluster successfully.");
            EmployeeDAO employeeDAO = new EmployeeDAO(session);
            // Save an employee
            Employee emp_obj = new Employee();
            emp_obj.setId(1);
            emp_obj.setName("John");
            emp_obj.setAge(26);
            emp_obj.setEmail("john@.com");
            employeeDAO.save(emp_obj);
            System.out.println("Employee Saved");
            // Find the employee by Id.
            Employee employee = employeeDAO.findById(1);
            System.out.println(String.format("Employee Details: name = %s, age = %s, email = %s", employee.getName(), employee.getAge().toString(), employee.getEmail()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
