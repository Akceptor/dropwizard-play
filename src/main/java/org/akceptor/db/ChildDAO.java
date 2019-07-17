package org.akceptor.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.akceptor.core.Child;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ChildDAO extends AbstractDAO<Child> {
    public ChildDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Child> findById(int id) {
        return Optional.ofNullable(get(id));
    }

    public Child create(Child child) {
        return persist(child);
    }

    public List<Child> findAll() {
        return currentSession().createQuery("select C from Child C").list();
    }
}
