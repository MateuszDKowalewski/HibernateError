package com.gmail.aspoka1.taniaszafa.db.dao;

import com.gmail.aspoka1.taniaszafa.db.HibernateConfig;
import com.gmail.aspoka1.taniaszafa.db.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public Order getOrder(Long id) {
        Session session = HibernateConfig.INSTANCE.getSessionFactory().openSession();
        Order order = session.get(Order.class, id);
        session.close();
        return order;
    }

    public void setOrderAsPaid(Long id) {
        Session session = HibernateConfig.INSTANCE.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Order order = session.find(Order.class, id);
            order.setPaid(true);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void setOrderAsMade(Long id) {
        Session session = HibernateConfig.INSTANCE.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Order order = session.find(Order.class, id);
            order.setMade(true);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Order> getAllOrders() {
        Session session = HibernateConfig.INSTANCE.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        criteria.from(Order.class);
        List<Order> data = session.createQuery(criteria).getResultList();
        session.close();
        return data;
    }

    public List<Order> getOrdersReadyToMade() {
        Session currentSession = HibernateConfig.INSTANCE.getSessionFactory().openSession();

        List<Order> list = currentSession.createCriteria(Order.class)
                .add(Restrictions.eq("paid", true))
                .add(Restrictions.eq("made", false))
                .list();
        return list;
    }

    public List<Order> getMadeOrders() {
        Session currentSession = HibernateConfig.INSTANCE.getSessionFactory().openSession();

        List<Order> list = currentSession.createCriteria(Order.class)
                .add(Restrictions.eq("made", true))
                .list();
        return list;
    }
}
