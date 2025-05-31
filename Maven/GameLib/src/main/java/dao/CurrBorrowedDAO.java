package dao;

import Entities.CurrBorrowed;
import Entities.GameCopy;
import Entities.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrBorrowedDAO {
    private final SessionFactory sessionFactory;

    public CurrBorrowedDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addBorrowedRecord(int gameId, int memberId, LocalDate borrowedDate, LocalDate dueTo) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try{
                String hql = "FROM GameCopy gc WHERE gc.game.id = :gameId AND gc.id NOT IN " +
                        "(SELECT cb.gameCopy.id FROM CurrBorrowed cb)";
                List<GameCopy> availableCopies = session.createQuery(hql, GameCopy.class)
                        .setParameter("gameId", gameId)
                        .getResultList();

                if (availableCopies.isEmpty()) {
                    throw new IllegalArgumentException("No available copies for the specified game.");
                }

                Member member = session.get(Member.class, memberId);
                if (member == null) {
                    throw new IllegalArgumentException("Invalid memberId provided.");
                }

                GameCopy gameCopy = availableCopies.getFirst();

                CurrBorrowed currBorrowed = new CurrBorrowed();
                currBorrowed.setGameCopy(gameCopy);
                currBorrowed.setMember(member);
                currBorrowed.setBorrowedDate(borrowedDate);
                currBorrowed.setDueTo(dueTo);

                // Persist the entity
                session.persist(currBorrowed);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public void removeBorrowedRecord(int borrowedId){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                CurrBorrowed currBorrowed = session.get(CurrBorrowed.class, borrowedId);
                if (currBorrowed == null) {
                    throw new IllegalArgumentException("No borrowed record found with the given ID.");
                }

                session.remove(currBorrowed);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public List<CurrBorrowed> checkBorrowedForMember(Member member){
        List <CurrBorrowed> result = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                if(member == null){
                    throw new IllegalArgumentException("Provided member cannot be null");
                }
                int memberId = member.getId();
                String hql = "FROM CurrBorrowed cb WHERE cb.member.id = :memberId";
                result = session.createQuery(hql, CurrBorrowed.class)
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