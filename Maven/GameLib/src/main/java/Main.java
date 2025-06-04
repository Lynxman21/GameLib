import dao.BorrowedHistDAO;
import dao.CurrBorrowedDAO;
import dao.GameDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

import Entities.*;
import org.hibernate.query.Query;
import search.GameFilter;

public class Main {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
            GameFilter gameFilte = new GameFilter();
            gameFilte.setCategoryName("Category 1");
            System.out.println(gameFilte);
            GamesDisplayer gamesDisplayer = new GamesDisplayer(sessionFactory,gameFilte);
            gamesDisplayer.printAllResults();
        }
    }
}