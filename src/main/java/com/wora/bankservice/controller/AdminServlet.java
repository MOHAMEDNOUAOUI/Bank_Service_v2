package com.wora.bankservice.controller;

import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.service.DemandeService;
import com.wora.bankservice.service.impl.DemandeServiceImpl;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AdminServlet" , value="/Admin")
public class AdminServlet extends HttpServlet{
    private DemandeService DemandeService;
    public void init() throws ServletException {
            DemandeService = new DemandeServiceImpl();
    }

    public void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
        List<Demande> DemandList  = DemandeService.findAllDemandes();
        request.setAttribute("DemandeList" , DemandList);
        request.getRequestDispatcher("./views/AdminPage.jsp").forward(request , response);
    }
}
