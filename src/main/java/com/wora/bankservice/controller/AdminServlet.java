package com.wora.bankservice.controller;

import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.entity.DemandeStatut;
import com.wora.bankservice.entity.Statut;
import com.wora.bankservice.service.DemandeService;
import com.wora.bankservice.service.DemandeStatutService;
import com.wora.bankservice.service.StatutService;
import com.wora.bankservice.service.impl.DemandeServiceImpl;
import com.wora.bankservice.service.impl.DemandeStatutServiceImplt;
import com.wora.bankservice.service.impl.StatutServiceImpl;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.wora.bankservice.validation.validator.isStatutValid;

public class AdminServlet extends HttpServlet{

    private DemandeService DemandeService;
    private StatutService statutService;
    private DemandeStatutService DemandeStatutService;

    public void init() throws ServletException {
            DemandeService = new DemandeServiceImpl();
            statutService = new StatutServiceImpl();
            DemandeStatutService = new DemandeStatutServiceImplt();
    }

    public void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
        List<Demande> DemandList = List.of();
        DemandList  = DemandeService.findAllDemandes();
        request.setAttribute("DemandeList" , DemandList);
        request.getRequestDispatcher("./views/AdminPage.jsp").forward(request , response);
    }



    public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {

        if(request.getParameter("date")!=null){
            String datefilter = request.getParameter("date");
            List<Demande> DemandList = List.of();
            if(!datefilter.isEmpty()) {
                DemandList = DemandeService.findAllDemandesByDate(datefilter);
            }
            request.setAttribute("DemandeList" , DemandList);
            request.getRequestDispatcher("./views/AdminPage.jsp").forward(request , response);
        }else if(request.getParameter("statut") !=null && request.getParameter("demandeid") != null){
            String statut = isStatutValid(request.getParameter("statut"));
            Long demandeid = Long.parseLong(request.getParameter("demandeid"));
            Optional<Demande> demande = DemandeService.findDemandeById(demandeid);
            if(!statut.equals("NONE")){
                Optional<Statut> statut1 = statutService.findByStatut(statut);
                if(demande.isPresent() && statut1.isPresent()){
                    DemandeStatut demandeStatut = new DemandeStatut();
                    demandeStatut.setDemande(demande.get());
                    demandeStatut.setStatut(statut1.get());
                    demandeStatut.setDateInsert(LocalDateTime.now());
                    DemandeStatutService.save(demandeStatut);
                }
            }
            response.sendRedirect("/Admin");
        }

    }
}
