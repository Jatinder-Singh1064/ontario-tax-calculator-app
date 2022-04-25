package com.example.n01419541_q2_taxcalculatorapp;

public class TaxCalculation {

    public double processTaxCalculation(double taxableIncome) {

        double taxRate = 0; //In percentage

        if(taxableIncome > 0 && taxableIncome <= 45142){
            taxRate = 5.05;
        }
        else if(taxableIncome > 45142 && taxableIncome <= 90287){
            taxRate = 9.15;
        }
        else if(taxableIncome > 90287 && taxableIncome <= 150000) {
            taxRate = 11.16;
        }
        else if(taxableIncome > 150000 && taxableIncome <= 220000) {
            taxRate = 12.16;
        }
        else if(taxableIncome > 220000) {
            taxRate = 13.16;
        }

        return (taxRate * taxableIncome) / 100;
    }

    public double calculateRRSP(double maxRRSPLimit, float percent) {
        return (percent * maxRRSPLimit) / 100;
    }
}
