package dao;

import Entities.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class BorrowedHistDAO {
    private static BorrowedHistDAO instance = null;

    private BorrowedHistDAO() {}

    public static BorrowedHistDAO getInstance(){
        if(instance == null){
            instance = new BorrowedHistDAO();
        }
        return instance;
    }

    public void addBorrowedHistoryRecord(int currBorrowedId, int penalty, Session session) throws IllegalArgumentException{
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
        record.setStatus("R");

        session.persist(record);
    }

    public List<BorrowedHist> checkBorrowedHistForMember(Member member, Session session) throws IllegalArgumentException{
        List <BorrowedHist> result;

        if(member == null){
            throw new IllegalArgumentException("Provided member cannot be null");
        }
        int memberId = member.getId();
        String hql = "FROM BorrowedHist bc WHERE bc.member.id = :memberId";
        result = session.createQuery(hql, BorrowedHist.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return result;
    }


    public void printAllRecords(Session session) throws IllegalArgumentException{
        Query<BorrowedHist> query = session.createQuery("FROM BorrowedHist", BorrowedHist.class);
        List<BorrowedHist> borrowedHists = query.getResultList();

        for (BorrowedHist borrowedRecord : borrowedHists) {
            System.out.println(borrowedRecord);
        }
    }

    public double SumOfPenalties(int id, Session session) throws IllegalArgumentException{
        Double sumPen = (Double) session
                .createQuery("select sum(h.penalty) from BorrowedHist h where h.member.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return sumPen != null ? sumPen : 0.0;
    }
}
