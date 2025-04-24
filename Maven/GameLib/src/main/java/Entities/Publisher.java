package Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id", nullable = false, unique = true)
    private int id;

    @Column(name = "company_name", length = 100, nullable = false, unique = true)
    private String companyName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "city", length = 50)
    private String city;

    public Publisher() {}

    public int getId(){
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", startDate=" + startDate +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(id, publisher.id) &&
                Objects.equals(companyName, publisher.companyName) &&
                Objects.equals(startDate, publisher.startDate) &&
                Objects.equals(country, publisher.country) &&
                Objects.equals(city, publisher.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, startDate, country, city);
    }
}
