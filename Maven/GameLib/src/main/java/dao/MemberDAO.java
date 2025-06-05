package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;

public class MemberDAO {
    private SessionFactory sessionFactory;

    public MemberDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer logIn(String email) {
        try(Session session = sessionFactory.openSession()) {
            Integer id = null;
            id = (Integer) session.createQuery("select m.id from Member m where m.email=:email").setParameter("email",email).getSingleResult();
            return id;
        } catch (Exception e) {
            System.out.println("Błąd podczas wyszukiwania użytkownika: " + e.getMessage());
            return null;
        }
    }
}
