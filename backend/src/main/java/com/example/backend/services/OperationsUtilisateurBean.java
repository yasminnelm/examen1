package com.example.backend.services;

import com.example.backend.entities.CD;
import com.example.backend.entities.Emprunt;
import com.example.backend.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class OperationsUtilisateurBean {
    @PersistenceContext
    private EntityManager em;

    public List<CD> listerCDsDisponibles() {
        return em.createQuery("SELECT cd FROM CD cd WHERE cd.isEmprunte = false", CD.class).getResultList();
    }

    public void emprunterCD(Long cdId, Long userId) {
        CD cd = em.find(CD.class, cdId);
        User user = em.find(User.class, userId);
        if (cd != null && user != null && !cd.isEmprunte()) {
            cd.setEmprunte(true);
            em.persist(new Emprunt(user, cd));
        }
    }

    public void retournerCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null && cd.isEmprunte()) {
            cd.setEmprunte(false);
        }
    }
}

