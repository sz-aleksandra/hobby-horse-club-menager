package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Group;

import java.util.List;

public class GroupDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Group group) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(group);
            session.getTransaction().commit();
            System.out.println("[GroupDAO] Created group with id: " + group.getGroupId());
        }
    }

    public void delete(Group group) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(group);
            session.getTransaction().commit();
            System.out.println("[GroupDAO] Deleted group with id: " + group.getGroupId());
        }
    }

    public void update(Group group) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(group);
            session.getTransaction().commit();
            System.out.println("[GroupDAO] Updated group with id: " + group.getGroupId());
        }
    }

    public List<Group> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM groups", Group.class).list();
        }
    }

    public Group findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Group.class, id);
        }
    }

    public List<Group> findGroupsByClassId(int classId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM groups WHERE class_id = :classId", Group.class)
                    .setParameter("classId", classId)
                    .list();
        }
    }
}
