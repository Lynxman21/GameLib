package Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "game_count_vw")
public class GameCountVW {
    @Id
    @Column(name = "game_id")
    private int id;

    private String name;

    private Long amount;

    public GameCountVW() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
