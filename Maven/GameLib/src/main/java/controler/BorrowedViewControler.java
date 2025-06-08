package controler;

import Entities.CurrBorrowed;
import Entities.Game;
import Entities.Member;
import dao.CurrBorrowedDAO;
import dao.MemberDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;

public class BorrowedViewControler {
    private Integer id;

    private SessionFactory sessionFactory;

    @FXML
    private VBox borrowedList;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void initData() {
        CurrBorrowedDAO currBorrowedDAO = CurrBorrowedDAO.getInstance(sessionFactory);
        MemberDAO memberDAO = MemberDAO.getInstance(sessionFactory);
        Member member = memberDAO.getMember(id);
        List<CurrBorrowed> borrowed =  currBorrowedDAO.checkBorrowedForMember(member);

        for (CurrBorrowed b : borrowed) {
            Button button = new Button(b.getGameCopy().getGame().getName());
            button.setPrefHeight(50);
            button.setPrefWidth(200);
            button.setStyle("-fx-font-size: 16px;");
            button.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/BorrowedGameView.fxml"));
                    Parent root = loader.load();

                    BorrowedGameViewControler controller = loader.getController();
                    controller.setId(id);
                    controller.setSessionFactory(sessionFactory);
                    controller.setCurrBorrowed(b);
                    controller.dataInit();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            borrowedList.getChildren().add(button);
        }
    }

    @FXML
    public void back(javafx.event.ActionEvent actionEvent) {
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
}
