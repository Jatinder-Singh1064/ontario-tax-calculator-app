package com.example.n01419541_q2_taxcalculatorapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    TaxCalculation taxCalculation;
    EditText etAnnualIncome, etMaxRRSPLimit, etRRSPContribution, etRRSPNextYear, etTaxableIncome, etTaxCalculated;
    Slider sliderPercentage;
    double maxRRSPLimit, contributedRRSP, nextYearRRSP,annualIncome, taxableIncome, totalTax;
    String maxRRSPLimitSt, contributedRRSPSt, nextYearRRSPSt,annualIncomeSt, taxableIncomeSt, totalTaxSt, percentSliderSt;
    float percentSlider;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taxCalculation = new TaxCalculation();

        etAnnualIncome = findViewById(R.id.et_AnnualIncome);
        etMaxRRSPLimit = findViewById(R.id.et_MaxRRSPLimit);
        etRRSPContribution = findViewById(R.id.et_RRSP_Contribution);
        etRRSPNextYear = findViewById(R.id.et_RRSP_NextYear);
        etTaxableIncome = findViewById(R.id.et_TaxableIncome);
        etTaxCalculated = findViewById(R.id.et_CalculatedTax);
        sliderPercentage = findViewById(R.id.slider_percentage);

        //Get shared preferences object
        String sharedPrefFile = "com.example.n01419541_q2_taxcalculatorapp";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        //Reading the data values from Shared Preferences
        annualIncomeSt = mPreferences.getString("annualIncome", "");
        maxRRSPLimitSt = mPreferences.getString("maxRRSPLimit", "");
        percentSliderSt = mPreferences.getString("percentSlider", "");
        contributedRRSPSt = mPreferences.getString("contributedRRSP", "");
        nextYearRRSPSt = mPreferences.getString("nextYearRRSP", "");
        taxableIncomeSt = mPreferences.getString("taxableIncome", "");
        totalTaxSt = mPreferences.getString("totalTax", "");

        //Setting the values to edit text fields using Shared Preferences
        if(annualIncomeSt!=""){
            annualIncome = Double.parseDouble(annualIncomeSt);
            etAnnualIncome.setText(String.format("%.2f", annualIncome));
        }
        if(maxRRSPLimitSt!=""){
            maxRRSPLimit = Double.parseDouble(maxRRSPLimitSt);
            etMaxRRSPLimit.setText(String.format("%.2f", maxRRSPLimit));
        }
        if(percentSliderSt!="")
            sliderPercentage.setValue(Float.parseFloat(percentSliderSt));
        if(contributedRRSPSt!=""){
            contributedRRSP = Double.parseDouble(contributedRRSPSt);
            etRRSPContribution.setText(String.format("%.2f", contributedRRSP));
        }
        if(nextYearRRSPSt!=""){
            nextYearRRSP = Double.parseDouble(nextYearRRSPSt);
            etRRSPNextYear.setText(String.format("%.2f", nextYearRRSP));
        }
        if(taxableIncomeSt!=""){
            taxableIncome = Double.parseDouble(taxableIncomeSt);
            etTaxableIncome.setText(String.format("%.2f", taxableIncome));
        }
        if(totalTaxSt!=""){
            totalTax = Double.parseDouble(totalTaxSt);
            etTaxCalculated.setText(String.format("%.2f", totalTax));
        }

        //Slider Change Listener Event
        sliderPercentage.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                computeRRSPContribution();
                computeTaxableIncome();
                calculateTotalTax();
            }
        });

        //MaxRRSPLimit Text Change Listener Event
        etMaxRRSPLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                computeRRSPContribution();
                computeTaxableIncome();
                calculateTotalTax();
            }
        });

        //AnnualIncome Text Change Listener Event
        etAnnualIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                annualIncome = etAnnualIncome.length()==0 ? 0.0 : Double.parseDouble(etAnnualIncome.getText().toString());
                maxRRSPLimit = etMaxRRSPLimit.length()==0 ? 0.0 : Double.parseDouble(etMaxRRSPLimit.getText().toString());

                if(annualIncome < maxRRSPLimit){
                    etMaxRRSPLimit.setError("Invalid Max. RRSP Limit");
                }

                computeRRSPContribution();
                computeTaxableIncome();
                calculateTotalTax();
            }
        });
    }

    private void computeRRSPContribution() {
        if(etMaxRRSPLimit.length()==0){
            etMaxRRSPLimit.requestFocus();
            etMaxRRSPLimit.setError("Enter Max. RRSP Limit");
        }
        else {
            maxRRSPLimit = Double.parseDouble(etMaxRRSPLimit.getText().toString());
            percentSlider = sliderPercentage.getValue();

            contributedRRSP = taxCalculation.calculateRRSP(maxRRSPLimit, percentSlider);
            nextYearRRSP = maxRRSPLimit - contributedRRSP;

            etRRSPContribution.setText(String.format("%.2f", contributedRRSP));
            etRRSPNextYear.setText(String.format("%.2f", nextYearRRSP));
            etTaxCalculated.setText("");
        }
    }

    private void computeTaxableIncome(){
        if(etAnnualIncome.length()==0){
            etAnnualIncome.requestFocus();
            etAnnualIncome.setError("Enter Annual Income");
        } else{
            annualIncome = Double.parseDouble(etAnnualIncome.getText().toString());
            maxRRSPLimit = etMaxRRSPLimit.length()==0 ? 0.0 :  Double.parseDouble(etMaxRRSPLimit.getText().toString());
            contributedRRSP = etRRSPContribution.length()==0 ? 0.0 : Double.parseDouble(etRRSPContribution.getText().toString());

            if(annualIncome > maxRRSPLimit) {
                taxableIncome = annualIncome - contributedRRSP;
                etTaxableIncome.setText(String.format("%.2f", taxableIncome));
                etTaxCalculated.setText("");
            } else {
                etMaxRRSPLimit.setError("Invalid Max. RRSP Limit");
                Toast.makeText(MainActivity.this, "Invalid Annual Income or Max RRSP Limit.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void calculateTotalTax() {
        if(etTaxableIncome.length()==0) {
            etAnnualIncome.requestFocus();
            etAnnualIncome.setError("Enter Annual Income");
        }
        else {
            taxableIncome = Double.parseDouble(etTaxableIncome.getText().toString());
            totalTax = taxCalculation.processTaxCalculation(taxableIncome);

            etTaxCalculated.setText(String.format("%.2f", totalTax));

            //Get shared preferences object
            String sharedPrefFile = "com.example.n01419541_q2_taxcalculatorapp";
            SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

            //Save data to shared pref
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("annualIncome", String.valueOf(annualIncome));
            preferencesEditor.putString("maxRRSPLimit", String.valueOf(maxRRSPLimit));
            preferencesEditor.putString("percentSlider", String.valueOf(percentSlider));
            preferencesEditor.putString("contributedRRSP", String.valueOf(contributedRRSP));
            preferencesEditor.putString("nextYearRRSP", String.valueOf(nextYearRRSP));
            preferencesEditor.putString("taxableIncome", String.valueOf(taxableIncome));
            preferencesEditor.putString("totalTax", String.valueOf(totalTax));
            preferencesEditor.apply();
        }
    }
}