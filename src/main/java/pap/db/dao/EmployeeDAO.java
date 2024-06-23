package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Employee;

import java.util.List;

public class EmployeeDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Employee employee) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(employee);
            session.getTransaction().commit();
            System.out.println("[EmployeeDAO] Created employee with id: " + employee.getEmployeeId());
        }
    }

    public void delete(Employee employee) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(employee);
            session.getTransaction().commit();
            System.out.println("[EmployeeDAO] Deleted employee with id: " + employee.getEmployeeId());
        }
    }

    public void update(Employee employee) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(employee);
            session.getTransaction().commit();
            System.out.println("[EmployeeDAO] Updated employee with id: " + employee.getEmployeeId());
        }
    }

    public List<Employee> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM employees", Employee.class).list();
        }
    }

    public Employee findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Employee.class, id);
        }
    }

    public List<Employee> findEmployeesByPositionId(int positionId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM employees WHERE position_id = :positionId", Employee.class)
                    .setParameter("positionId", positionId)
                    .list();
        }
    }

    public List<Employee> findEmployeesByDateEmployedRange(Date startDate, Date endDate) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM employees WHERE date_employed BETWEEN :startDate AND :endDate", Employee.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();
        }
    }
}
