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
            currBorrowedDAO.addBorrowedRecord(1, 2, LocalDate.now(), LocalDate.of(2026, 5, 5));
//            currBorrowedDAO.removeBorrowedRecord(3);
            currBorrowedDAO.printAllRecords();
            System.out.println("------------------------------------------------");
            borrowedHistDAO.printAllRecords();
            System.out.println("------------------------------------------------");
            Member member1 = null;
            Member member2 = null;
            try(Session session = sessionFactory.openSession()){
                Transaction transaction = session.beginTransaction();
                try{
                    int member1Id = 1;
                    int member2Id = 2;
                    member1 = session.get(Member.class, member1Id);
                    member2 = session.get(Member.class, member2Id);
                    transaction.commit();
                }catch(Exception e){
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
            if(member1 == null || member2 == null){
                return;
            }
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
//            List<CurrBorrowed> member1result = currBorrowedDAO.checkBorrowedForMember(member1);
//            List<CurrBorrowed> member2result = currBorrowedDAO.checkBorrowedForMember(member2);
//            for(CurrBorrowed currBorrowed: member1result){
//                System.out.println(currBorrowed);
//            }
//            System.out.println("------------------------------------------------");
//            for(CurrBorrowed currBorrowed: member2result){
//                System.out.println(currBorrowed);
//            }

            List<BorrowedHist> member1result = borrowedHistDAO.checkBorrowedHistForMember(member1);
            for(BorrowedHist borrowedHist: member1result){
                System.out.println(borrowedHist);
            }
//            borrowedHistDAO.addBorrowedHistoryRecord(rowId);
//            currBorrowedDAO.removeBorrowedRecord(rowId);
//
//            currBorrowedDAO.printAllRecords();
//            System.out.println("------------------------------------------------");
//            borrowedHistDAO.printAllRecords();
//            System.out.println("------------------------------------------------");

        }
    }
}