import Entities.Game;
import dao.GameDAO;
import org.hibernate.SessionFactory;
import search.GameFilter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GamesDisplayer {

    private List<Game> games;
    private SessionFactory sessionFactory;
    private GameFilter gameFilter;

    public GamesDisplayer(SessionFactory sessionFactory, GameFilter gameFilter) {
        this.sessionFactory = sessionFactory;
        games = Collections.emptyList();
        this.gameFilter = gameFilter;
    }

    public void getAllGames() {
        GameDAO gameDAO = new GameDAO(sessionFactory);
        games = gameDAO.findAllAvailableGames(gameFilter);
    }

    public void printAllResults() {
        getAllGames();

        for (Game game: games) {
            System.out.println(game);
        }
    }
}
