package com.example.backend.services;

import com.example.backend.entities.CD;
import com.example.backend.entities.User;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateful
public class OperationsAdminBean {
    @PersistenceContext
    private EntityManager em;

    public void ajouterCD(CD cd) {
        em.persist(cd);
    }

    public void modifierCD(CD cd) {
        em.merge(cd);
    }

    public void supprimerCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null) {
            em.remove(cd);
        }
    }
    public List<CD> consulterTousLesCDs() {
        return em.createQuery("SELECT cd FROM CD cd", CD.class).getResultList();
    }

    public List<User> consulterEmprunteurs() {
        return em.createQuery("SELECT DISTINCT e.user FROM Emprunt e", User.class).getResultList();
    }
}

