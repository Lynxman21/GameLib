package dao;

import Entities.Game;
import org.hibernate.Session;
import search.GameFilter;

import java.util.ArrayList;
import java.util.List;

public class GameDAO {
    private static GameDAO instance = null;

    private GameDAO() {}

    public static GameDAO getInstance(){
        if(instance == null){
            instance = new GameDAO();
        }
        return instance;
    }

    private List<String> getAllCategories(Game game, Session session) throws IllegalArgumentException{
        if(game == null){
            throw new IllegalArgumentException("Game is null");
        }
        return session.createQuery("select cgl.category.categoryName from CategoryGameLink cgl where cgl.game.id = :gameId")
                .setParameter("gameId",game.getId())
                .list();
    }

    public Game findGameByName(String name, Session session) {
        return session.createQuery("select g from Game g where g.name=:name",Game.class)
                .setParameter("name",name)
                .getSingleResult();
    }

    public List<Game> findAllAvailableGames(GameFilter gameFilter, Session session){
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
    }
}
