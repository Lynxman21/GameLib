package Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "borrowed_hist")
public class BorrowedHist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hist_id", nullable = false, unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "copy_id", nullable = false)
    private GameCopy gameCopy;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "penalty")
    private double penalty;

    public BorrowedHist() {}

    public int getId(){
        return id;
    }

    public GameCopy getGameCopy() {
        return gameCopy;
    }

    public void setCopyId(GameCopy gameCopy) {
        this.gameCopy = gameCopy;
    }

    public Member getMember() {
        return member;
    }

    public void setMemberId(Member member) {
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

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return "BorrowedHist{" +
                "id=" + id +
                ", copyId=" + gameCopy.getId() +
                ", memberId=" + member.getId() +
                ", borrowedDate=" + borrowedDate +
                ", dueDate=" + dueDate +
                ", actualReturnDate=" + actualReturnDate +
                ", status='" + status + '\'' +
                ", penalty=" + penalty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowedHist that = (BorrowedHist) o;
        return Double.compare(penalty, that.penalty) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(gameCopy, that.gameCopy) &&
                Objects.equals(member, that.member) &&
                Objects.equals(borrowedDate, that.borrowedDate) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(actualReturnDate, that.actualReturnDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameCopy, member, borrowedDate, dueDate, actualReturnDate, status, penalty);
    }
}
