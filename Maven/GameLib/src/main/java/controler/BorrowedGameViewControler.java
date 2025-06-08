package controler;

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
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Label title = new Label("Tytuł: " + currBorrowed.getGameCopy().getGame().getName());
                Label borrowedDate = new Label("Data wypożyczenia: " + currBorrowed.getBorrowedDate().toString());
                Label dueTo = new Label("Data do kiedy trzeba oddać: " + currBorrowed.getDueTo().toString());

                borrowInfo.getChildren().addAll(title, borrowedDate, dueTo);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public void giveBack(ActionEvent actionEvent) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                CurrBorrowedDAO currBorrowedDAO = CurrBorrowedDAO.getInstance();
                BorrowedHistDAO borrowedHistDAO = BorrowedHistDAO.getInstance();
                LocalDate dueTo = currBorrowed.getDueTo();

                int penalty = LocalDate.now().isBefore(dueTo) ? 0 : 20;
                borrowedHistDAO.addBorrowedHistoryRecord(currBorrowed.getId(), penalty, session);

                currBorrowedDAO.removeBorrowedRecord(currBorrowed.getId(), session);
                back(actionEvent);

                transaction.commit();
            }catch(Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
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
