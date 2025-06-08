package controler;

import Entities.BorrowedHist;
import dao.BorrowedHistDAO;
import dao.MemberDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

public class HistoryViewControler {
    private Integer id;

    private SessionFactory sessionFactory;

    @FXML
    private VBox histList;

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
                BorrowedHistDAO borrowedHistDAO = BorrowedHistDAO.getInstance();
                MemberDAO memberDAO = MemberDAO.getInstance();
                List<BorrowedHist> borrowed = borrowedHistDAO.checkBorrowedHistForMember(memberDAO.getMember(id, session), session);

                for (BorrowedHist b : borrowed) {
                    Button button = new Button(b.getGameCopy().getGame().getName());
                    button.setPrefHeight(50);
                    button.setPrefWidth(200);
                    button.setStyle("-fx-font-size: 16px;");
                    button.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BorrowedHIstGameView.fxml"));
                            Parent root = loader.load();

                            BorrowedHistGameViewControler controller = loader.getController();
                            controller.setId(id);
                            controller.setSessionFactory(sessionFactory);
                            controller.setBorrowedHist(b);
                            controller.dataInit();

                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    histList.getChildren().add(button);
                }

                double sumary = borrowedHistDAO.SumOfPenalties(id, session);
                Label sum = new Label(String.valueOf(sumary));
                histList.getChildren().add(sum);

                transaction.commit();
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
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
}
