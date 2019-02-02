package com.example.collegeloanpayoffcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private EditText editTextYears = null;
    private TextView textViewMonthlyPayment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextYears = (EditText)findViewById(R.id.editTextYears);
        textViewMonthlyPayment = (TextView)findViewById(R.id.textViewMonthlyPayment);
    }

    public void buttonCalculate_onClick(View v){
        System.out.println("Calculating...");

        String yearsStr = editTextYears.getText().toString();
        int yearsInt = Integer.parseInt(yearsStr);
        System.out.println(yearsInt);



        double monthlyPayment = yearsInt * 2.5;

        System.out.println(monthlyPayment);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String monthlyPaymentCurrentString = formatter.format(monthlyPayment);
        String monthlyPaymentStr = String.format("Your monthly payment will be %s", monthlyPaymentCurrentString);
        textViewMonthlyPayment.setText(monthlyPaymentStr);
     }
}
