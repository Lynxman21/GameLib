package manager;

import dao.MemberDAO;
import org.hibernate.*;

public class LoginManager {
    SessionFactory sessionFactory;

    public LoginManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer logIn(String mail) {
        MemberDAO memberDAO = MemberDAO.getInstance(sessionFactory);
        return memberDAO.logIn(mail);
    }
}
