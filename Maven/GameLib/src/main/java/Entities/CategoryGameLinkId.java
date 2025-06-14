package Entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CategoryGameLinkId implements Serializable {
    private Integer categoryId;
    private Integer gameId;

    public CategoryGameLinkId() {}

    public CategoryGameLinkId(Integer categoryId, Integer gameId) {
        this.categoryId = categoryId;
        this.gameId = gameId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryGameLinkId that = (CategoryGameLinkId) o;
        return categoryId == that.categoryId && gameId == that.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, gameId);
    }
}