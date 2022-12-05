import org.hibernate.*;
import java.util.*;

public class EmployeeDAO {

    Session hibernateSession;

    public EmployeeDAO (Session session) {
        hibernateSession = session;
    }

    public void save(final Employee employeeEntity) {
        Transaction transaction = hibernateSession.beginTransaction();
        try {
            hibernateSession.save(employeeEntity);
            transaction.commit();
        } catch(RuntimeException rte) {
            transaction.rollback();
        }
    }

    public Employee findById(final Integer id) {
        return hibernateSession.get(Employee.class, id);
    }
}
