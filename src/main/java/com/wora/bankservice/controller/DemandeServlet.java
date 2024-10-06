package com.wora.bankservice.controller;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.service.DemandeService;
import com.wora.bankservice.service.impl.DemandeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "DemandeServlet", value = "/demande")
public class DemandeServlet extends HttpServlet {
    private DemandeService service;
    public void init() throws ServletException {
        service = new DemandeServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            if (isValid(request)) {
                Demande demande = savetoDatabase(request);
                HttpSession session = request.getSession();
                if(demande!=null) {
                    session.setAttribute("message", "success" );
                }
                else {
                    session.setAttribute("message", "error" );
                }
            response.sendRedirect("/");
            }
    }

    private Demande savetoDatabase(HttpServletRequest request) {
        Demande demande = new Demande();
        demande.setMonproject(request.getParameter("monproject"));
        demande.setJesuis(request.getParameter("jesuis_select"));
        demande.setMontant(Double.parseDouble(request.getParameter("montant")));
        demande.setDuree(Integer.parseInt(request.getParameter("dure")));
        demande.setMensualite(Double.parseDouble(request.getParameter("mensualite")));
        demande.setEmail(request.getParameter("email"));
        demande.setTelephone(request.getParameter("telephone"));
        demande.setCivilite(request.getParameter("civilite"));
        demande.setNom(request.getParameter("nom"));
        demande.setPrenom(request.getParameter("prenom"));
        demande.setCIN(request.getParameter("CIN"));
        demande.setTotalrevenue(Double.parseDouble(request.getParameter("totalrevenue")));

        String datenaissance = request.getParameter("datenaissance");
        String DateDembauche = request.getParameter("DateDembauche");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate datenaissanceDate = LocalDate.parse(datenaissance , formatter);
        LocalDate datedembauche = LocalDate.parse(DateDembauche , formatter);
        demande.setDatenaissance(datenaissanceDate);
        demande.setDatedebauche(datedembauche);

        Demande demandeservice = service.createDemande(demande);
        return demandeservice;
    }

    private boolean isValid(HttpServletRequest request) {
        String monproject = request.getParameter("monproject");
        String jesuis = request.getParameter("jesuis_select");
        String montantStr = request.getParameter("montant");
        String dureStr = request.getParameter("dure");
        String mensualiteStr = request.getParameter("mensualite");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String civilite = request.getParameter("civilite");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String CIN = request.getParameter("CIN");
        String datenaissance = request.getParameter("datenaissance");
        String DateDembauche = request.getParameter("DateDembauche");
        String totalrevenueStr = request.getParameter("totalrevenue");

        if (monproject == null || jesuis == null || montantStr == null || dureStr == null ||
                mensualiteStr == null || email == null || telephone == null ||
                civilite == null || nom == null || prenom == null || CIN == null ||
                datenaissance == null || DateDembauche == null || totalrevenueStr == null) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            Double montant = Double.parseDouble(montantStr);
            int dure = Integer.parseInt(dureStr);
            Double mensualite = Double.parseDouble(mensualiteStr);
            Double totalrevenue = Double.parseDouble(totalrevenueStr);
            LocalDate datenaissanceDate = LocalDate.parse(datenaissance.trim(), formatter);
            LocalDate datedembauche = LocalDate.parse(DateDembauche.trim(), formatter);
            if (montant <= 0 || dure <= 0 || mensualite <= 0 || totalrevenue <= 0) {
                return false;
            }
            if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                return false;
            }
            if (!telephone.matches("^\\+?[1-9]\\d{1,14}(\\s|-)?(\\(?\\d{1,4}\\)?|\\d+)(\\s|-)?(\\d+(\\s|-)?)*$")) {
                return false;
            }
            LocalDate today = LocalDate.now();
            if (datedembauche.isBefore(today)) {
                System.err.println("Date d'embauche must be today or in the future.");
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (DateTimeParseException e) {
            System.err.println("Date parcing error : "+e.getMessage());
        }

        return true;
    }



}