package Entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "category_game_link")
public class CategoryGameLink {
    @EmbeddedId
    private CategoryGameLinkId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public CategoryGameLink() {}

    public CategoryGameLinkId getId() {
        return id;
    }

    public void setId(CategoryGameLinkId id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "CategoryGameLink{" +
                "id=" + id +
                ", category=" + category +
                ", game=" + game +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryGameLink that = (CategoryGameLink) o;
        return Objects.equals(id, that.id) && Objects.equals(category, that.category) && Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, game);
    }
}