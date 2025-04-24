package Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "curr_borrowed")
public class CurrBorrowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id", nullable = false, unique = true)
    private int id;

    @OneToOne
    @JoinColumn(name = "copy_id", nullable = false)
    private GameCopy gameCopy;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    public CurrBorrowed() {}

    public int getId(){
        return id;
    }

    public GameCopy getGameCopy() {
        return gameCopy;
    }

    public void setGameCopy(GameCopy gameCopy) {
        this.gameCopy = gameCopy;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "CurrBorrowed{" +
                "id=" + id +
                ", copyId=" + gameCopy.getId() +
                ", memberId=" + member.getId() +
                ", borrowedDate=" + borrowedDate +
                ", dueDate=" + dueDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrBorrowed that = (CurrBorrowed) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(gameCopy, that.gameCopy) &&
                Objects.equals(member, that.member) &&
                Objects.equals(borrowedDate, that.borrowedDate) &&
                Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameCopy, member, borrowedDate, dueDate);
    }
}
