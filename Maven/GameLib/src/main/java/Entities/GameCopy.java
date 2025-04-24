package Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "game_copies")
public class GameCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "copy_id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "acquired_day")
    private LocalDate acquiredDay;

    public GameCopy() {}

    public int getId(){
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDate getAcquiredDay() {
        return acquiredDay;
    }

    public void setAcquiredDay(LocalDate acquiredDay) {
        this.acquiredDay = acquiredDay;
    }

    @Override
    public String toString() {
        return "GameCopy{" +
                "id=" + id +
                ", gameId=" + game.getId() +
                ", acquiredDay=" + acquiredDay +
                '}';
    }


}
