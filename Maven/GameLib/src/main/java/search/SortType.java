package search;

public enum SortType {
    NORMAL("Brak"),
    NAME_ASC("Rosnący po tytule"),
    NAME_DESC("Malejący po tytule"),
    REALISED_ASC("Rosnący po dacie wydania"),
    REALISED_DESC("Malejący po dacie wydania");

    private final String displayName;

    SortType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
