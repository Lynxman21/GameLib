package controler;

import Entities.Game;
import dao.CurrBorrowedDAO;
import dao.GameDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.scene.control.Label;
import org.hibernate.Transaction;

import java.io.IOException;
import java.time.LocalDate;

public class GameViewControler {
    private Integer id;

    private Integer gameId;

    private SessionFactory sessionFactory;

    private String name;

    @FXML
    private VBox gameInfo;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void dataInit() {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try {
                GameDAO gd = GameDAO.getInstance();
                Game game = gd.findGameByName(name, session);

                Label title = new Label(name);
                Label publisher = new Label(game.getPublisher().getCompanyName());
                Label date = new Label(game.getReleaseDate().toString());

                gameId = game.getId();

                gameInfo.getChildren().addAll(title,publisher,date);
                transaction.commit();
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void borrow(ActionEvent event) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try {
                CurrBorrowedDAO cbr = CurrBorrowedDAO.getInstance();
                LocalDate dueTo = LocalDate.now().plusDays(60);

                cbr.addBorrowedRecord(gameId,id,LocalDate.now(),dueTo,session);

                setScene(event);

                transaction.commit();
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void back(ActionEvent event) {
        try {
            setScene(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameList.fxml"));
        Parent root = loader.load();

        GameListControler controller = loader.getController();
        controller.setId(id);
        controller.setSessionFactory(sessionFactory);
        controller.initData();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
