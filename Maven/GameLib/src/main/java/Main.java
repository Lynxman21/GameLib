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
            CurrBorrowedDAO currBorrowedDAO = new CurrBorrowedDAO(sessionFactory);
            currBorrowedDAO.addBorrowedRecord(1, 1, LocalDate.now(), LocalDate.of(2026, 5, 5));
//            currBorrowedDAO.removeBorrowedRecord(3);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                Query<CurrBorrowed> query = session.createQuery("FROM CurrBorrowed", CurrBorrowed.class);
                List<CurrBorrowed> borrowed_curr = query.getResultList();

                for (CurrBorrowed borrowed : borrowed_curr) {
                    System.out.println(borrowed);
                }

                session.getTransaction().commit();
            }
        }
    }
}