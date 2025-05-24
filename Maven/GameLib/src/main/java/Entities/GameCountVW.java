package Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "game_count_vw")
public class GameCountVW {
    @Id
    @OneToOne
    @JoinColumn(name = "game_id")
    @Column(name = "game_id")
    private Game game;

    private String name;

    private Long amount;

    public GameCountVW() {
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
