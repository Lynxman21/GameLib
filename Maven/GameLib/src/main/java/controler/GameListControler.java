package controler;


import Entities.Game;
import dao.GameDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import search.GameFilter;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import search.SortData;
import search.SortType;

import java.io.IOException;
import java.util.List;

public class GameListControler {
    private Integer id;

    private SessionFactory sessionFactory;

    private GameDAO gdao = null;

    private GameFilter filter = new GameFilter();

    private SortType sortType = SortType.NORMAL;

    @FXML
    private VBox gameL;

    @FXML
    private TextField title;
    @FXML
    private TextField publisher;

    @FXML
    private ChoiceBox<String> genre;

    @FXML
    private ChoiceBox<SortType> sortT;


    public void setId(Integer id) {
        this.id = id;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void initData() {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try {
                if (gdao == null) {
                    gdao = GameDAO.getInstance();
                }
                List<Game> games = gdao.findAllAvailableGames(filter, session);
                SortData.sortWithMode(games,sortType);

                for (Game g : games) {
                    Button button = getButton(g);
                    gameL.getChildren().add(button);
                }
                transaction.commit();
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleChoice() {
        String type = genre.getValue();
        if (type.equals("Brak")) {
            filter.setCategoryName(null);
        } else {
            filter.setCategoryName(type);
        }
        gameL.getChildren().clear();
        initData();
    }

    @FXML
    public void handleSort() {
        SortType type = sortT.getValue();

        if (type.equals("Brak")) {
            sortType = SortType.NORMAL;
        } else {
            sortType = type;
        }
        gameL.getChildren().clear();
        initData();
    }

    @FXML
    public void handleTitle() {
        String name = title.getText();

        if (name.isBlank()) {
            filter.setName(null);
        } else {
            filter.setName(name);
        }

        gameL.getChildren().clear();
        initData();
    }

    @FXML
    public void handlePublisher() {
        String name = publisher.getText();

        if (name.isBlank()) {
            filter.setPublisherName(null);
        } else {
            filter.setPublisherName(name);
        }

        gameL.getChildren().clear();
        initData();
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientPanel.fxml"));
            Parent root = loader.load();

            PanelControler controller = loader.getController();
            controller.setId(id);
            controller.setSessionFactory(sessionFactory);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Button getButton(Game g) {
        Button button = new Button(g.getName());
        button.setPrefHeight(50);
        button.setPrefWidth(200);
        button.setStyle("-fx-font-size: 16px;");
        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameView.fxml"));
                Parent root = loader.load();

                GameViewControler controller = loader.getController();
                controller.setId(id);
                controller.setSessionFactory(sessionFactory);
                controller.setName(g.getName());
                controller.dataInit();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return button;
    }
}
