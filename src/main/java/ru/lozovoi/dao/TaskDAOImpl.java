package ru.lozovoi.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lozovoi.domain.Task;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TaskDAOImpl implements TaskDAO{

    private final SessionFactory sessionFactory;

    public TaskDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit) {
        Query<Task> tasks = getSession().createQuery("select t from Task t", Task.class);
        tasks.setFirstResult(offset);
        tasks.setMaxResults(limit);
        return tasks.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> query = getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Task getById(int id) {
        Query<Task> query = getSession().createQuery("select t from Task t where t.Id = :id", Task.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task) {
        getSession().persist(task);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        getSession().remove(task);
    }
}
