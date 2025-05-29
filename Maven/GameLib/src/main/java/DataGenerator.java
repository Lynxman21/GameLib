import Entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    private final SessionFactory sessionFactory;

    public DataGenerator(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void generateData() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Create Publishers
            Publisher publisher1 = new Publisher();
            publisher1.setCompanyName("Publisher 1");
            publisher1.setCountry("Poland");
            publisher1.setCity("Warsaw");
            publisher1.setStartDate(LocalDate.of(1999, 2,20));

            session.persist(publisher1);


            // Create Categories
            Category category1 = new Category();
            category1.setCategoryName("Category 1");
            session.persist(category1);

            Category category2 = new Category();
            category2.setCategoryName("Category 2");
            session.persist(category2);

            Category category3 = new Category();
            category3.setCategoryName("Category 3");
            session.persist(category3);

            session.flush();

            // Create Games
            List<Game> games = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                Game game = new Game();
                game.setName("Game " + i);
                game.setPublisher(publisher1);
                game.setReleaseDate(LocalDate.of(2010,12,1));
                games.add(game);
                session.persist(game);

                int gameId = game.getId();

                if(i != 1){
                    // Create CategoryGameLink
                    CategoryGameLink link = new CategoryGameLink();
                    System.out.println("CATEGORY 1: " + category1.getId());
                    link.setId(new CategoryGameLinkId(category1.getId(), gameId));
                    link.setCategory(category1);
                    link.setGame(game);
                    session.persist(link);
                }

                if (i != 2) {
                    CategoryGameLink link = new CategoryGameLink();
                    System.out.println("CATEGORY 2: " + category2.getId());
                    link.setId(new CategoryGameLinkId(category2.getId(), gameId));
                    link.setCategory(category2);
                    link.setGame(game);
                    session.persist(link);
                }

                if(i != 3){
                    CategoryGameLink link = new CategoryGameLink();
                    System.out.println("CATEGORY 3: " + category3.getId());
                    CategoryGameLinkId linkId = new CategoryGameLinkId(category3.getId(), gameId);
                    link.setId(linkId);
                    link.setCategory(category3);
                    link.setGame(game);
                    session.persist(link);
                }

                // Create GameCopies
                for (int j = 1; j <= 4; j++) {
                    GameCopy copy = new GameCopy();
                    copy.setGame(game);
                    copy.setAcquiredDay(LocalDate.of(2020,5,6));
                    session.persist(copy);
                }
            }

            // Create Member
            Member member1 = new Member();
            member1.setFirstName("Anna");
            member1.setLastName("Kowalska");
            member1.setEmail("anna.kowalska@example.com");
            member1.setJoinDate(LocalDate.of(2023, 1, 15));
            member1.setStreet("Main Street");
            member1.setHouseNumber("12A");
            member1.setPostalCode("12345");
            member1.setCity("Warsaw");
            member1.setCountry("Poland");

            session.persist(member1);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}