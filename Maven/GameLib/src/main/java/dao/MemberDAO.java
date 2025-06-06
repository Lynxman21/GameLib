package dao;

import Entities.Member;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class MemberDAO {
    private SessionFactory sessionFactory;

    public MemberDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Member getMember(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            List<Member> list = session.createQuery("select m from Member m where m.id=:id",Member.class).setParameter("id",id).getResultList();

            if (list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Błąd podczas wyszukiwania użytkownika: " + e.getMessage());
            return null;
        }
    }

    public Integer logIn(String email) {
        try(Session session = sessionFactory.openSession()) {
            List<Member> list =  session.createQuery("select m from Member m where m.email=:email",Member.class).setParameter("email",email).getResultList();
            if (list.size() > 0) {
                return list.get(0).getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Błąd podczas wyszukiwania użytkownika: " + e.getMessage());
            return null;
        }
    }
}
