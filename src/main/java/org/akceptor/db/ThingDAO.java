package org.akceptor.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.akceptor.core.Thing;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ThingDAO extends AbstractDAO<Thing> {
    public ThingDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Thing> findById(int id) {
        return Optional.ofNullable(get(id));
    }

    public Thing create(Thing e) {
        return persist(e);
    }

    public List<Thing> findAll() {
        return currentSession().createQuery("select T from Thing T").list();
    }
}
