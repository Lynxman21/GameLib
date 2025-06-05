package search;

import Entities.Category;

import java.util.List;

public class GameFilter {
    private String name = null;
    private String publisherName = null;
    private String categoryName = null;

    public GameFilter() {
    }

    public String getName() {
        return name;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean compareName(String text) {
        return text.toLowerCase().contains(name.toLowerCase());
    }

    public boolean comparePublisher(String text) {
        return text.toLowerCase().contains(publisherName.toLowerCase());
    }

    public boolean compareCategory(List<String> cat) {
        return cat.stream()
                .map(c -> c.toLowerCase())
                .anyMatch(c -> c.equals(categoryName.toLowerCase()));
    }

    @Override
    public String toString() {
        return "GameFilter{" +
                "name='" + name + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
