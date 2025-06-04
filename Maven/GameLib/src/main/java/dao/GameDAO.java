package dao;

import Entities.Category;
import Entities.Game;
import Entities.GameCountVW;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import search.GameFilter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDAO {
    private SessionFactory sessionFactory;

    public GameDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private List<String> getAllCategories(Game game, Session session) {
        return session.createQuery("select cgl.category.categoryName from CategoryGameLink cgl where cgl.game.id = :gameId")
                .setParameter("gameId",game.getId())
                .list();
    }

    public List<Game> findAllAvailableGames(GameFilter gameFilter) {
        try (Session session = sessionFactory.openSession()) {

            List<Integer> gamesId =  session.createQuery(
                    "select c.id from GameCountVW c where c.amount > 0",Integer.class
            ).list();

            List<Game> games = new ArrayList<>();

            for (Integer id: gamesId) {
                Game game = session.get(Game.class,id);
                if (game == null) {
                    continue;
                }
                if (gameFilter.getName() != null && !gameFilter.compareName(game.getName())) { //TextBar
                    continue;
                }
                if (gameFilter.getPublisherName() != null &&
                        !gameFilter.comparePublisher(game.getPublisher().getCompanyName())) { //TextBar
                    continue;
                }
                if (gameFilter.getCategoryName() != null && !gameFilter.compareCategory(getAllCategories(game,session))) {
                    continue;
                }
                games.add(game);
            }
            return games;
        } catch (Exception e) {
            System.out.println("Błąd podczas wyszukiwania gier: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
