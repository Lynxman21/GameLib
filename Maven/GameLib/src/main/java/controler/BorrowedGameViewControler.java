package controler;

import Entities.CurrBorrowed;
import Entities.Game;
import dao.BorrowedHistDAO;
import dao.CurrBorrowedDAO;
import dao.GameDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;
import java.time.LocalDate;

public class BorrowedGameViewControler {
    private Integer id;

    private SessionFactory sessionFactory;

    private CurrBorrowed currBorrowed;

    @FXML
    private VBox borrowInfo;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setCurrBorrowed(CurrBorrowed currBorrowed) {
        this.currBorrowed = currBorrowed;
    }


    public void dataInit() {

        Label title = new Label("Tytuł: " + currBorrowed.getGameCopy().getGame().getName());
        Label borrowedDate = new Label("Data wypożyczenia: " + currBorrowed.getBorrowedDate().toString());
        Label dueTo = new Label("Data do kiedy trzeba oddać: " + currBorrowed.getDueTo().toString());

        borrowInfo.getChildren().addAll(title,borrowedDate,dueTo);
    }

    public void giveBack(ActionEvent actionEvent) {
        try(Session session = sessionFactory.getCurrentSession()){

        }
        CurrBorrowedDAO currBorrowedDAO = CurrBorrowedDAO.getInstance(sessionFactory);
        BorrowedHistDAO borrowedHistDAO = BorrowedHistDAO.getInstance(sessionFactory);
        LocalDate dueTo = currBorrowed.getDueTo();

        if (LocalDate.now().isBefore(dueTo)) {
            borrowedHistDAO.addBorrowedHistoryRecord(currBorrowed.getId());
        } else {
            borrowedHistDAO.addBorrowedHistoryRecord(currBorrowed.getId(),20);
        }

        currBorrowedDAO.removeBorrowedRecord(currBorrowed.getId());
        back(actionEvent);
    }

    @FXML
    public void back(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BorrowedView.fxml"));
            Parent root = loader.load();

            BorrowedViewControler controller = loader.getController();
            controller.setId(id);
            controller.setSessionFactory(sessionFactory);
            controller.initData();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
