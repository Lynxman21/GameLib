package manager;

import dao.MemberDAO;
import org.hibernate.*;

public class LoginManager {
    SessionFactory sf;

    public LoginManager(SessionFactory sf) {
        this.sf = sf;
    }

    public Integer logIn(String mail) {
        MemberDAO memberDAO = new MemberDAO(sf);
        return memberDAO.logIn(mail);
    }
}
