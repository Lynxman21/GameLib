import dao.GameDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

import Entities.*;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) {
//        Session session = null;
//        Transaction transaction = null;
//        GameDAO gameDao = null;
//
//        //Kod testowy, sprawdza czy dodawanie do tych pojedyńczych tabel działa
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()){

//            transaction = session.beginTransaction();
//
//            // Add a Publisher
//            Publisher publisher = new Publisher();
//            publisher.setCompanyName("Game Studios 2");
//            publisher.setStartDate(LocalDate.of(2001, 1, 1));
//            publisher.setCountry("USA");
//            publisher.setCity("Chicago");
//            session.persist(publisher);
//
//            // Add a Category
//            Category category = new Category();
//            category.setCategoryName("Strategy");
//            session.persist(category);
//
//            // Add a Game
//            Game game = new Game();
//            game.setName("Game1");
//            game.setReleaseDate(LocalDate.of(2022, 5, 15));
//            game.setPublisher(publisher);
//            session.persist(game);
//
//            transaction.commit();
//            System.out.println("Rows inserted successfully!");
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }

//        GamesDisplayer gamesDisplayer = new GamesDisplayer();
//        gamesDisplayer.printAllResults();

        DataGenerator dataGenerator = new DataGenerator(sessionFactory);
        dataGenerator.generateData();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Query to fetch all members
        Query<Member> query = session.createQuery("FROM Member", Member.class);
        List<Member> members = query.getResultList();

        // Print each member
        for (Member member : members) {
            System.out.println(member);
        }

        session.getTransaction().commit();
        }
    }
}