package dao;

import org.hibernate.SessionFactory;

public class MemberDAO {
    private SessionFactory sessionFactory;

    public MemberDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer login(String email) {
        return null;
    }
}
