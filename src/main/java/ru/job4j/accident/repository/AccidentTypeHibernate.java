package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;

import java.util.Collection;
import java.util.List;

//@Repository
public class AccidentTypeHibernate {
    private final SessionFactory sessionFactory;

    public AccidentTypeHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Collection<AccidentType> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<AccidentType> types = session.createQuery("from AccidentType order by id", AccidentType.class).list();
            session.getTransaction().commit();
            return types;
        }
    }

    public AccidentType findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            AccidentType accidentType = session.get(AccidentType.class, id);
            session.getTransaction().commit();
            return accidentType;
        }
    }

    public void create(AccidentType accidentType) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(accidentType);
            session.getTransaction().commit();
        }
    }
}
