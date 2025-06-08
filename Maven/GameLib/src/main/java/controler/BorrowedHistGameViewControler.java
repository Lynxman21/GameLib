package controler;

import Entities.BorrowedHist;
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

public class BorrowedHistGameViewControler {
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
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try {
                Label title = new Label("Tytuł: " + borrowedHist.getGameCopy().getGame().getName());
                Label borrowedDate = new Label("Data wypożyczenia: " + borrowedHist.getBorrowedDate().toString());
                Label dueTo = new Label("Data do kiedy trzeba oddać: " + borrowedHist.getDueDate().toString());
                Label actualReturn = new Label("Aktualna data zwrotu: " + borrowedHist.getActualReturnDate().toString());
                Label status = new Label("Status: " + borrowedHist.getStatus());
                Label penalty = new Label("Kara: " + borrowedHist.getPenalty());

                borrowInfo.getChildren().addAll(title, borrowedDate, dueTo, actualReturn, status, penalty);
                transaction.commit();
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
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
