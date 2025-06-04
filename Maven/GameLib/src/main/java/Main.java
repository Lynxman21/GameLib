import dao.BorrowedHistDAO;
import dao.CurrBorrowedDAO;
import dao.GameDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

import Entities.*;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
            LoginManager loginManager = new LoginManager(sessionFactory);
            loginManager.logIn("ann.kowalska@example.com");
            System.out.println(loginManager.getId());
        }
    }
}