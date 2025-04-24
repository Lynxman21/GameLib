import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import Entities.*;

public class Main {
    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;

        //Kod testowy, sprawdza czy dodawanie do tych pojedyńczych tabel działa
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Add a Publisher
            Publisher publisher = new Publisher();
            publisher.setCompanyName("Game Studios");
            publisher.setStartDate(LocalDate.of(2000, 1, 1));
            publisher.setCountry("USA");
            publisher.setCity("New York");
            session.persist(publisher);

            // Add a Member
            Member member = new Member();
            member.setFirstName("John");
            member.setLastName("Doe");
            member.setEmail("john.doe@example.com");
            member.setJoinDate(LocalDate.now());
            member.setStreet("123 Main St");
            member.setHouseNumber("A1");
            member.setPostalCode("12345");
            member.setCity("Springfield");
            member.setCountry("USA");
            session.persist(member);

            // Add a Category
            Category category = new Category();
            category.setCategoryName("Action");
            session.persist(category);

            // Add a Game
            Game game = new Game();
            game.setName("Adventure Quest");
            game.setReleaseDate(LocalDate.of(2022, 5, 15));
            game.setPublisher(publisher);
            session.persist(game);

            // Add a GameCopy
            GameCopy gameCopy = new GameCopy();
            gameCopy.setGame(game);
            gameCopy.setAcquiredDay(LocalDate.now());
            session.persist(gameCopy);

            transaction.commit();
            System.out.println("Rows inserted successfully!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}