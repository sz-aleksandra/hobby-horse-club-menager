package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Member;

import java.util.List;

public class MemberDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Member member) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(member);
            session.getTransaction().commit();
            System.out.println("[MemberDAO] Created member with id: " + member.getMemberId());
        }
    }

    public void delete(Member member) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(member);
            session.getTransaction().commit();
            System.out.println("[MemberDAO] Deleted member with id: " + member.getMemberId());
        }
    }

    public void update(Member member) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(member);
            session.getTransaction().commit();
            System.out.println("[MemberDAO] Updated member with id: " + member.getMemberId());
        }
    }

    public List<Member> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM members", Member.class).list();
        }
    }

    public Member findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Member.class, id);
        }
    }

    public List<Member> findMembersByAddressId(int addressId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM members WHERE address_id = :addressId", Member.class)
                    .setParameter("addressId", addressId)
                    .list();
        }
    }
}
