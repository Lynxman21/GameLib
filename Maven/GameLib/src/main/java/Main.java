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
            int rowId = 2;

            CurrBorrowedDAO currBorrowedDAO = new CurrBorrowedDAO(sessionFactory);
            BorrowedHistDAO borrowedHistDAO = new BorrowedHistDAO(sessionFactory);
//            currBorrowedDAO.addBorrowedRecord(1, 1, LocalDate.now(), LocalDate.of(2026, 5, 5));
//            currBorrowedDAO.removeBorrowedRecord(3);
            currBorrowedDAO.printAllRecords();
            System.out.println("------------------------------------------------");
            borrowedHistDAO.printAllRecords();
            System.out.println("------------------------------------------------");

            borrowedHistDAO.addBorrowedHistoryRecord(rowId);
            currBorrowedDAO.removeBorrowedRecord(rowId);

            currBorrowedDAO.printAllRecords();
            System.out.println("------------------------------------------------");
            borrowedHistDAO.printAllRecords();
            System.out.println("------------------------------------------------");
        }
    }
}