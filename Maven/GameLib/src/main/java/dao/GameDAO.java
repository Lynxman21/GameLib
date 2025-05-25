package dao;

import Entities.Game;
import Entities.GameCountVW;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDAO {
    private SessionFactory sessionFactory;

    public GameDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Game> findAllAvailableGames() {
        try (Session session = sessionFactory.openSession()) {
            List<Integer> gamesId =  session.createQuery(
                    "select c.id from GameCountVW c where c.amount > 0",Integer.class
            ).list();

            List<Game> games = new ArrayList<>();

            for (Integer id: gamesId) {
                Game game = session.get(Game.class,id);
                if (game != null) {
                    games.add(game);
                }
            }
            return games;
        } catch (Exception e) {
            System.out.println("Błąd podczas wyszukiwania gier: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
