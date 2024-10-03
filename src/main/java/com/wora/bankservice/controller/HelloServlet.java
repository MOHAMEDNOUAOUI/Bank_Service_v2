package com.wora.bankservice.controller;

import java.io.*;

import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.service.DemandeService;
import com.wora.bankservice.service.impl.DemandeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/demande")
public class HelloServlet extends HttpServlet {
    private DemandeService service;
    public void init() throws ServletException {
        service = new DemandeServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String monproject = request.getParameter("monproject");
        String jesuis = request.getParameter("jesuis_select");
        Double montant = Double.parseDouble(request.getParameter("montant"));
        int dure = Integer.parseInt(request.getParameter("dure"));
        Double mensualite = Double.parseDouble(request.getParameter("mensualite"));

        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");

        String civilite = request.getParameter("civilite");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String CIN = request.getParameter("CIN");
        String datenaissance = request.getParameter("datenaissance");
        String DateDembauche = request.getParameter("DateDembauche");
        Double totalrevenue = Double.parseDouble(request.getParameter("totalrevenue"));

        Demande demande = new Demande();
        demande.setMonproject(monproject);
        demande.setJesuis(jesuis);
        demande.setMontant(montant);
        demande.setDuree(dure);
        demande.setMensualite(mensualite);
        demande.setEmail(email);
        demande.setTelephone(telephone);
        demande.setCivilite(civilite);
        demande.setNom(nom);
        demande.setPrenom(prenom);
        demande.setCIN(CIN);
        demande.setTotalrevenue(totalrevenue);

        service.createDemande(demande);

    }

}