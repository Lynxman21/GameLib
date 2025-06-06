package controler;

import Entities.BorrowedHist;
import Entities.CurrBorrowed;
import dao.BorrowedHistDAO;
import dao.CurrBorrowedDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.time.LocalDate;

public class BorrowedHIstGameViewControler {
    private Integer id;

    private SessionFactory sessionFactory;

    @FXML
    private VBox borrowInfo;

    private BorrowedHist borrowedHist;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setBorrowedHist(BorrowedHist borrowedHist) {
        this.borrowedHist = borrowedHist;
    }

    public void dataInit() {

        Label title = new Label("Tytuł: " + borrowedHist.getGameCopy().getGame().getName());
        Label borrowedDate = new Label("Data wypożyczenia: " + borrowedHist.getBorrowedDate().toString());
        Label dueTo = new Label("Data do kiedy trzeba oddać: " + borrowedHist.getDueDate().toString());
        Label actualReturn = new Label("Aktualna data zwrotu: " + borrowedHist.getActualReturnDate().toString());
        Label status = new Label("Status: " + borrowedHist.getStatus());
        Label penalty = new Label("Kara: " + borrowedHist.getPenalty());

        borrowInfo.getChildren().addAll(title,borrowedDate,dueTo,actualReturn,status,penalty);
    }

    @FXML
    public void back(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HistoryView.fxml"));
            Parent root = loader.load();

            HistoryViewControler controller = loader.getController();
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
