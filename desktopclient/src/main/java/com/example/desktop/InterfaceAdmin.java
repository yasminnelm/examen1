package com.example.desktop;

import com.example.backend.entities.CD;
import com.example.backend.entities.User;
import com.example.backend.services.OperationsAdminBean;


import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfaceAdmin extends JFrame {
    private OperationsAdminBean operationsAdminBean;

    private JTextField titreField;
    private JButton ajouterButton;
    private JButton supprimerButton;
    private JButton modifierButton;
    private JTextArea emprunteursArea;

    public InterfaceAdmin() {
        try {
            operationsAdminBean = EJBHelper.lookupRemoteEJB(OperationsAdminBean.class, "java:global/GestionCinethiqueBackend/OperationsAdminBean!com.example.backend.services.OperationsAdminBean");
        } catch (NamingException e) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion au serveur EJB : " + e.getMessage());
            e.printStackTrace();
        }

        setTitle("Gestion des CDs");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Titre du CD :"));
        titreField = new JTextField();
        formPanel.add(titreField);

        ajouterButton = new JButton("Ajouter CD");
        supprimerButton = new JButton("Supprimer CD");
        modifierButton = new JButton("Modifier CD");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(ajouterButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(modifierButton);

        formPanel.add(buttonPanel);


        emprunteursArea = new JTextArea(10, 30);
        emprunteursArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(emprunteursArea);


        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterCD();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerCD();
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierCD();
            }
        });

        JButton consulterEmprunteursButton = new JButton("Consulter Emprunteurs");
        consulterEmprunteursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consulterEmprunteurs();
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(consulterEmprunteursButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void ajouterCD() {
        String titre = titreField.getText();
        if (!titre.isEmpty()) {
            CD nouveauCD = new CD();
            nouveauCD.setTitre(titre);
            operationsAdminBean.ajouterCD(nouveauCD);
            JOptionPane.showMessageDialog(this, "CD ajouté avec succès !");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un titre.");
        }
    }

    private void supprimerCD() {
        String titre = titreField.getText();
        if (!titre.isEmpty()) {
            List<CD> cds = operationsAdminBean.consulterTousLesCDs();
            for (CD cd : cds) {
                if (cd.getTitre().equalsIgnoreCase(titre)) {
                    operationsAdminBean.supprimerCD(cd.getId());
                    JOptionPane.showMessageDialog(this, "CD supprimé avec succès !");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "CD non trouvé.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un titre.");
        }
    }

    private void modifierCD() {
        String titre = titreField.getText();
        if (!titre.isEmpty()) {
            List<CD> cds = operationsAdminBean.consulterTousLesCDs();
            for (CD cd : cds) {
                if (cd.getTitre().equalsIgnoreCase(titre)) {
                    String nouveauTitre = JOptionPane.showInputDialog(this, "Nouveau titre :", cd.getTitre());
                    if (nouveauTitre != null && !nouveauTitre.isEmpty()) {
                        cd.setTitre(nouveauTitre);
                        operationsAdminBean.modifierCD(cd);
                        JOptionPane.showMessageDialog(this, "CD modifié avec succès !");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "CD non trouvé.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un titre.");
        }
    }

    private void consulterEmprunteurs() {
        List<User> emprunteurs = operationsAdminBean.consulterEmprunteurs();
        StringBuilder emprunteursListe = new StringBuilder("Emprunteurs :\n");
        for (User user : emprunteurs) {
            emprunteursListe.append(user.getNom()).append("\n");
        }
        emprunteursArea.setText(emprunteursListe.toString());
    }

    public static void main(String[] args) {
        new InterfaceAdmin();
    }
}
