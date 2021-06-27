package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;
import java.util.Collection;
import java.util.List;

@Repository
public class RuleHibernate {
    private final SessionFactory sessionFactory;

    public RuleHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Rule create(Rule rule) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(rule);
            session.getTransaction().commit();
            return rule;
        }
    }

    public Collection<Rule> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Rule> rules = session.createQuery("from Rule order by id", Rule.class).list();
            session.getTransaction().commit();
            return rules;
        }
    }

    public Rule findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Rule rule = session.get(Rule.class, id);
            session.getTransaction().commit();
            return rule;
        }
    }
}
