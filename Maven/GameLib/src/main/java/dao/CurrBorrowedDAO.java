package dao;

import Entities.CurrBorrowed;
import Entities.GameCopy;
import Entities.Member;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class CurrBorrowedDAO {
    private static CurrBorrowedDAO instance = null;

    private CurrBorrowedDAO() {}

    public static CurrBorrowedDAO getInstance(){
        if(instance == null){
            instance = new CurrBorrowedDAO();
        }
        return instance;
    }

    public void addBorrowedRecord(int gameId, int memberId, LocalDate borrowedDate, LocalDate dueTo, Session session) throws IllegalArgumentException{
        String hql = "FROM GameCopy gc WHERE gc.game.id = :gameId AND gc.id NOT IN " +
                "(SELECT cb.gameCopy.id FROM CurrBorrowed cb)";
        List<GameCopy> availableCopies = session.createQuery(hql, GameCopy.class)
                .setParameter("gameId", gameId)
                .setLockMode(LockMode.PESSIMISTIC_WRITE.toJpaLockMode())
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

        session.persist(currBorrowed);

    }

    public void removeBorrowedRecord(int borrowedId, Session session) throws IllegalArgumentException{
        CurrBorrowed currBorrowed = session.get(CurrBorrowed.class, borrowedId);
        if (currBorrowed == null) {
            throw new IllegalArgumentException("No borrowed record found with the given ID.");
        }

        session.remove(currBorrowed);
    }

    public List<CurrBorrowed> checkBorrowedForMember(Member member, Session session) throws IllegalArgumentException{
        List <CurrBorrowed> result;

        if(member == null){
            throw new IllegalArgumentException("Provided member cannot be null");
        }
        int memberId = member.getId();
        String hql = "FROM CurrBorrowed cb WHERE cb.member.id = :memberId";
        result = session.createQuery(hql, CurrBorrowed.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return result;
    }

    public void printAllRecords(Session session){
        Query<CurrBorrowed> query = session.createQuery("FROM CurrBorrowed", CurrBorrowed.class);
        List<CurrBorrowed> borrowed_curr = query.getResultList();

        for (CurrBorrowed borrowed : borrowed_curr) {
            System.out.println(borrowed);
        }
    }
}