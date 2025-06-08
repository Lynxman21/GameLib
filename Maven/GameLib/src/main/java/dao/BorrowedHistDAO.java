package dao;

import Entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowedHistDAO {
    private final SessionFactory sessionFactory;
    private static BorrowedHistDAO instance = null;

    private BorrowedHistDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static BorrowedHistDAO getInstance(SessionFactory sessionFactory){
        if(instance == null){
            instance = new BorrowedHistDAO(sessionFactory);
        }
        return instance;
    }

//    public BorrowedHistDAO(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public void addBorrowedHistoryRecord(int currBorrowedId){
        //TODO możliwe że do zmiany będą parametry funkcji ale to do przedyskutowania
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                CurrBorrowed currBorrowed = session.get(CurrBorrowed.class, currBorrowedId);

                if(currBorrowed == null){
                    throw new IllegalArgumentException("Cannot find row in curr_borrowed table corresponding to provided ID");
                }

                GameCopy gameCopy = currBorrowed.getGameCopy();
                Member member =  currBorrowed.getMember();

                if(gameCopy == null || member == null){
                    throw new IllegalArgumentException("Cannot find member or game copy with provided ID's");
                }

                BorrowedHist record = new BorrowedHist();

                record.setCopyId(gameCopy);
                record.setMemberId(member);
                record.setBorrowedDate(currBorrowed.getBorrowedDate());
                record.setDueDate(currBorrowed.getDueTo());
                record.setActualReturnDate(LocalDate.now());
                record.setPenalty(0);
                record.setStatus("R");

                session.persist(record);
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }

        }
    }

    public void addBorrowedHistoryRecord(int currBorrowedId, int penalty){
        //TODO możliwe że do zmiany będą parametry funkcji ale to do przedyskutowania
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                CurrBorrowed currBorrowed = session.get(CurrBorrowed.class, currBorrowedId);

                if(currBorrowed == null){
                    throw new IllegalArgumentException("Cannot find row in curr_borrowed table corresponding to provided ID");
                }

                GameCopy gameCopy = currBorrowed.getGameCopy();
                Member member =  currBorrowed.getMember();

                if(gameCopy == null || member == null){
                    throw new IllegalArgumentException("Cannot find member or game copy with provided ID's");
                }

                BorrowedHist record = new BorrowedHist();

                record.setCopyId(gameCopy);
                record.setMemberId(member);
                record.setBorrowedDate(currBorrowed.getBorrowedDate());
                record.setDueDate(currBorrowed.getDueTo());
                record.setActualReturnDate(LocalDate.now());
                record.setPenalty(penalty);
                record.setStatus("D");

                session.persist(record);
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }

        }
    }

    public List<BorrowedHist> checkBorrowedHistForMember(Member member){
        List <BorrowedHist> result = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                if(member == null){
                    throw new IllegalArgumentException("Provided member cannot be null");
                }
                int memberId = member.getId();
                String hql = "FROM BorrowedHist bc WHERE bc.member.id = :memberId";
                result = session.createQuery(hql, BorrowedHist.class)
                        .setParameter("memberId", memberId)
                        .getResultList();
                transaction.commit();
            }
            catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return result;
    }


    public void printAllRecords(){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Query<BorrowedHist> query = session.createQuery("FROM BorrowedHist", BorrowedHist.class);
            List<BorrowedHist> borrowedHists = query.getResultList();

            for (BorrowedHist borrowedRecord : borrowedHists) {
                System.out.println(borrowedRecord);
            }

            session.getTransaction().commit();
        }
    }

    public double SumOfPenalties(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Double sumPen = (Double) session.createQuery("select sum(h.penalty) from BorrowedHist h where h.member.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return sumPen != null ? sumPen : 0.0;
        }
    }
}
