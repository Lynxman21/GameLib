import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DatabaseConnectionTester {
    private final SessionFactory sessionFactory;

    public DatabaseConnectionTester(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean testConnection() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("SELECT 1").getSingleResult();
            session.getTransaction().commit();
            return true; // Connection successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Connection failed
        }
    }
}