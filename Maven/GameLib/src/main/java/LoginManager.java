import dao.MemberDAO;
import org.hibernate.*;

public class LoginManager {
    private Integer id = null;
    SessionFactory sf;

    public LoginManager(SessionFactory sf) {
        this.sf = sf;
    }

    public void logIn(String mail) {
        MemberDAO memberDAO = new MemberDAO(sf);
        id = memberDAO.logIn(mail);
    }

    public Integer getId() {
        return id;
    }

    public boolean isLogged() {
        return id != null;
    }

    public void logOut() {
        this.id = null;
    }
}
