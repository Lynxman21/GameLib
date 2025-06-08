package controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.HibernateUtil;
import manager.LoginManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Transaction;

import java.io.IOException;

public class LoginControler {
    @FXML
    private TextField mailField;

    @FXML
    private Label errorLable;

    private Integer id;

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @FXML
    private void handleLogin(ActionEvent event) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                String mail = mailField.getText();

                if (mail.isBlank()) {
                    return;
                }

                LoginManager lg = new LoginManager(sessionFactory);
                id = lg.logIn(mail, session);
                if (id == null) {
                    errorLable.setVisible(true);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientPanel.fxml"));
                        Parent root = loader.load();

                        PanelControler controller = loader.getController();
                        controller.setId(id);
                        controller.setSessionFactory(sessionFactory);

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                transaction.commit();
            }catch (Exception e){
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
