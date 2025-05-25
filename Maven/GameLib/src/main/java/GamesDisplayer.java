import Entities.Game;
import dao.GameDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class GamesDisplayer {

    public GamesDisplayer() {
    }

    public void printAllResults() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        GameDAO gameDAO = new GameDAO(sessionFactory);
        List<Game> games = gameDAO.findAllAvailableGames();

        for (Game game: games) {
            System.out.println(game);
        }
    }
}
