package search;

import Entities.Game;

import java.util.List;

public class SortData {
    public SortData() {
    }

    public static void sortWithMode(List<Game> games, SortType mode) {
        switch (mode) {
            case NORMAL -> games.sort((g1,g2) -> g1.getId() - g2.getId());
            case NAME_ASC -> games.sort((g1,g2) -> g1.getName().compareTo(g2.getName()));
            case NAME_DESC -> games.sort((g1,g2) -> g2.getName().compareTo(g1.getName()));
            case REALISED_ASC -> games.sort((g1,g2) -> g1.getReleaseDate().compareTo(g2.getReleaseDate()));
            case REALISED_DESC -> games.sort((g1,g2) -> g2.getReleaseDate().compareTo(g1.getReleaseDate()));
        }
    }
}
