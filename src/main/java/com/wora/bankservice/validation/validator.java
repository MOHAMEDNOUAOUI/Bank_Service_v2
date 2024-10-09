package com.wora.bankservice.validation;

import com.wora.bankservice.entity.Demande;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class validator {
    private static final double MONTHLY_INTEREST_RATE = 0.05 / 12;
    private static final int DURATION_STEP = 6;

    public static boolean validationCalcule(Demande demande) {
        double montant = demande.getMontant();
        double mensualite = demande.getMensualite();
        double duree = demande.getDuree();
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("");
        df2.setDecimalSeparatorAlwaysShown(false);

        if (!isValid(demande)) {
            return false;
        }

        System.out.println("after checked if isvalid");
        double newMensualite = calculateMensualtie(montant, duree);
        if(!Objects.equals(df.format(newMensualite), String.valueOf(mensualite))){
            return false;
        }



        Double durenew = Math.max(6, Math.min(120, duree));
        durenew = (double) (Math.round(durenew / DURATION_STEP) * DURATION_STEP);
        demande.setMontant(montant);
        demande.setMensualite(mensualite);
        demande.setDuree(durenew);

        return true;
    }

    private static double calculateMensualtie(double montant, double dure) {
        return (montant * MONTHLY_INTEREST_RATE) / (1 - Math.pow(1 + MONTHLY_INTEREST_RATE, -dure));
    }

    private static boolean isValid(Demande demande) {
        if (demande == null) {
            return false;
        }
        return demande.getDatedebauche() != null
                && demande.getTotalrevenue() > 0
                && demande.getJesuis() != null
                && demande.getDuree() >= 6
                && demande.getDuree() <= 120
                && demande.getMensualite() > 0
                && demande.getMontant() > 0
                && demande.getCivilite() != null
                && demande.getMonproject() != null;
    }

    public static LocalDate validateDate(String date){
        if(date == null){
            return null;
        }
        LocalDate datedembauche;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            datedembauche = LocalDate.parse(date.trim(), formatter);
        }catch (DateTimeParseException e){
            e.getMessage();
            return null;
        }

        return datedembauche;
    }
}
