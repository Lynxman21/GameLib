package manager;

import Entities.Game;
import dao.GameDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import search.GameFilter;
import search.SortData;
import search.SortType;

import java.util.Collections;
import java.util.List;

public class GamesDisplayer {

    private List<Game> games;
    private SessionFactory sessionFactory;
    private GameFilter gameFilter;
    private SortType mode;

    public GamesDisplayer(SessionFactory sessionFactory, GameFilter gameFilter, SortType mode) {
        this.sessionFactory = sessionFactory;
        games = Collections.emptyList();
        this.gameFilter = gameFilter;
        this.mode = mode;
    }
    public GamesDisplayer(SessionFactory sessionFactory, GameFilter gameFilter) {
        this.sessionFactory = sessionFactory;
        games = Collections.emptyList();
        this.gameFilter = gameFilter;
        this.mode = SortType.NORMAL;
    }

    public void getAllGames(Session session) {
        GameDAO gameDAO = GameDAO.getInstance();
        games = gameDAO.findAllAvailableGames(gameFilter, session);
    }

    public void printAllResults(Session session) {
        getAllGames(session);
        SortData.sortWithMode(games,mode);

        for (Game game: games) {
            System.out.println(game);
        }
    }
}
