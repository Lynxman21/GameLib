package dao;

import Entities.Member;
import org.hibernate.Session;

public class MemberDAO {
    private static MemberDAO instance = null;

    private MemberDAO() {}

    public static MemberDAO getInstance(){
        if(instance == null){
            instance = new MemberDAO();
        }
        return instance;
    }

    public Member getMember(int id, Session session) {
            return session
                    .createQuery("select m from Member m where m.id=:id",Member.class)
                    .setParameter("id",id)
                    .getSingleResult();
    }

    public Integer logIn(String email, Session session) {
        Member member = session
                .createQuery("select m from Member m where m.email=:email",Member.class)
                .setParameter("email",email)
                .getSingleResult();

        return member == null ? null : member.getId();
    }
}
