package com.example.webclient.controllers;


import com.example.backend.entities.CD;
import com.example.backend.services.OperationsUtilisateurBean;

import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

import java.util.List;

@ManagedBean
@RequestScoped
public class ControleurUtilisateur {
    @EJB
    private OperationsUtilisateurBean operationsUtilisateurBean;

    private List<CD> cdsDisponibles;
    private Long cdSelectionneId;

    public List<CD> getCdsDisponibles() {
        if (cdsDisponibles == null) {
            cdsDisponibles = operationsUtilisateurBean.listerCDsDisponibles();
        }
        return cdsDisponibles;
    }

    public void emprunterCD(Long userId) {
        operationsUtilisateurBean.emprunterCD(cdSelectionneId, userId);
        cdsDisponibles = operationsUtilisateurBean.listerCDsDisponibles();
    }

    public void retournerCD(Long cdId) {
        operationsUtilisateurBean.retournerCD(cdId);
        cdsDisponibles = operationsUtilisateurBean.listerCDsDisponibles();
    }


}

