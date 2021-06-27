package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.List;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public void createOrUpdate(Accident accident) {
        if (accident.getId() == 0) {
            create(accident);
        } else {
            update(accident);
        }
    }

    private void create(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
        }
    }

    private void update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
        }
    }

    public Collection<Accident> findAll() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<Accident> accidents =
                    session.createQuery(
                            "select distinct a from Accident a " +
                                    "join fetch a.type " +
                                    "join fetch a.rules " +
                                    "order by a.id", Accident.class).list();
            session.getTransaction().commit();
            return accidents;
        }
    }

    public Accident findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Accident accident = (Accident) session.createQuery(
                    "select distinct a from Accident a " +
                            "join fetch a.type " +
                            "join fetch a.rules where a.id = :id")
                    .setParameter("id", id).uniqueResult();
            session.getTransaction().commit();
            return accident;
        }
    }
}