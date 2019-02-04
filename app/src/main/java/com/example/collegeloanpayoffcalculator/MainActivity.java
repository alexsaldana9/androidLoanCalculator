package com.example.collegeloanpayoffcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private EditText editTextYears = null;
    private TextView textViewMonthlyPayment = null;
    private EditText editTextInterestRate = null;
    private EditText editTextLoanAmount = null;
   // private Button  buttonClear = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextYears = (EditText)findViewById(R.id.editTextYears);
        textViewMonthlyPayment = (TextView)findViewById(R.id.textViewMonthlyPayment);
        editTextInterestRate = (EditText)findViewById(R.id.editTextInterestRate);
        editTextLoanAmount = (EditText)findViewById(R.id.editTextLoanAmount);
        Button buttonClear = (Button)findViewById(R.id.buttonClear);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextYears.setText("");
                editTextInterestRate.setText("");
                editTextLoanAmount.setText("");
                textViewMonthlyPayment.setText("");

            }
        });

    }



    public void buttonCalculate_onClick(View v){
        System.out.println("Calculating...");

        int numMonths = readNumMonths();
        double interestDecimal = readInterestDecimal();
        double loanAmount = readLoanAmount();

        double discountFactor = readDiscountFactor(numMonths, interestDecimal);
        double loanPayment = readLoanPayment(loanAmount, discountFactor);

        displayLoanPayment(loanPayment);
    }

    private void displayLoanPayment(double loanPayment) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String monthlyPaymentCurrentString = formatter.format(loanPayment);
        String monthlyPaymentStr = String.format("Your monthly payment will be %s", monthlyPaymentCurrentString);
        textViewMonthlyPayment.setText(monthlyPaymentStr);
    }

    private double readLoanPayment(double loanAmount, double discountFactor) {
        double loanPayment = loanAmount / discountFactor;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String loanPaymentCurrencyString = formatter.format(loanPayment);
        System.out.println(String.format("Loan Payment: %s", loanPaymentCurrencyString));

        return loanPayment;
    }

    private double readDiscountFactor(int numMonths, double interestDecimal) {
        double discountFactorNumerator = readDiscountFactorNumerator(numMonths, interestDecimal);
        double discountFactorDenominator =  readDiscountFactorDenominator(numMonths, interestDecimal);

        double discountFactor = discountFactorNumerator / discountFactorDenominator;
        System.out.println(String.format("Discount Factor: %.8f", discountFactor));
        return discountFactor;
    }

    private double readDiscountFactorDenominator(int numMonths, double interestDecimal) {
        double discountFactorDenP = (interestDecimal + 1);
        double discountFactorDen = Math.pow(discountFactorDenP, numMonths);
        double discountFactorDenominator = discountFactorDen * interestDecimal;
        System.out.println(String.format("discountFactorDenominator :  %.8f", discountFactorDenominator));
        return discountFactorDenominator;
    }

    private double readDiscountFactorNumerator(int numMonths, double interestDecimal) {
        double discountFactorNum = (1 + interestDecimal);
        double discountFactorNumeratorExp = Math.pow(discountFactorNum, numMonths);

        System.out.println(String.format("discountFactorNumeratorExp - missing the subtraction of 1:  %.8f", discountFactorNumeratorExp));
        double discountFactorNumerator = discountFactorNumeratorExp - 1.00;
        System.out.println(String.format("Numerator: %.8f", discountFactorNumerator));
        return discountFactorNumerator;
    }

    private double readLoanAmount() {
        String loanAmountStr = editTextLoanAmount.getText().toString();
        double loanAmount = Double.parseDouble(loanAmountStr);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String loanAmountCurrencyString = formatter.format(loanAmount);
        System.out.println(String.format("Loan Amount: %s", loanAmountCurrencyString));

        return loanAmount;
    }

    private double readInterestDecimal() {
        //Interest Rate
        String interestRateStr = editTextInterestRate.getText().toString();
        double interestRate = Double.parseDouble(interestRateStr);
        System.out.println(String.format("Interest rate: %.4f", interestRate));

        //convert interest rate to decimal
        double interestDecimal = (interestRate / 100.0) / 12.0;
        System.out.println(String.format("Interest in decimals: %.4f", interestDecimal));

        return interestDecimal;
    }

    private int readNumMonths() {
        //Years
        String yearsStr = editTextYears.getText().toString();
        int yearsInt = Integer.parseInt(yearsStr);
        System.out.println(String.format("Years: %d", yearsInt));

        //Calculations
        //convert years to month
        int numMonths = yearsInt * 12;
        System.out.println(String.format("Number of Months: %d", numMonths));

        return numMonths;
    }
}
