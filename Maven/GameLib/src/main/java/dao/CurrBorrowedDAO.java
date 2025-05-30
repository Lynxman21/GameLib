package dao;

import Entities.CurrBorrowed;
import Entities.GameCopy;
import Entities.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
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
                currBorrowed.setDueDate(dueTo);

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
}