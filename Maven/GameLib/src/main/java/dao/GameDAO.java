package dao;

import Entities.Game;
import Entities.GameCountVW;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class GameDAO {
    private SessionFactory sessionFactory;

    public GameDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Game> FindAllAvailableGames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "select c.game from GameCountVW c where c.amount > 0", Game.class
            ).list();
        } catch (Exception e) {
            System.out.println("Błąd podczas wyszukiwania gier: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
