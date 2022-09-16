package com.example.productdiscount;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final DecimalFormat df = new DecimalFormat("0.00");

    // Creating objects for
    // text input layouts
    // to read the inputted data
    // and display the result
    TextInputLayout productPriceInputLayout, discountPercentageInputLayout, discountAmountInputLayout, amountInputLayout;

    // Creating objects for
    // the button to calculate
    // and reset/clear input data
    Button calculateButton, resetButton;

    private final TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String product_price = Objects.requireNonNull(productPriceInputLayout.getEditText()).getText().toString();
            String discount_percentage = Objects.requireNonNull(discountPercentageInputLayout.getEditText()).getText().toString();



            if (!product_price.isEmpty() && !discount_percentage.isEmpty()){
                double discountAmount = Double.parseDouble(product_price) * (Double.parseDouble(discount_percentage) / 100);
                boolean isDiscountHigh = (discountAmount >= Double.parseDouble(product_price));
                if (isDiscountHigh) {
                    discountPercentageInputLayout.setHelperTextEnabled(false);
                    discountPercentageInputLayout.setErrorEnabled(true);
                    discountPercentageInputLayout.setError("Error: high discount");
                    Objects.requireNonNull(discountAmountInputLayout.getEditText()).setText(df.format(0));
                    Objects.requireNonNull(amountInputLayout.getEditText()).setText(df.format(0));
                } else {
                    discountPercentageInputLayout.setHelperText("*Required");
                }
            }

            calculateButton.setEnabled(!product_price.isEmpty() && !discount_percentage.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing views
        // to get the input data
        initializeViews();

        // handling button events
        // to calculate the discount amount
        // and clear input data
        // when ever user clicks
        eventHandling();

        clearInputData();
    }

    private void eventHandling() {
        resetButton.setOnClickListener(this);
        calculateButton.setOnClickListener(this);
        Objects.requireNonNull(productPriceInputLayout.getEditText()).addTextChangedListener(inputTextWatcher);
        Objects.requireNonNull(discountPercentageInputLayout.getEditText()).addTextChangedListener(inputTextWatcher);
    }

    private void clearInputData() {
        Objects.requireNonNull(productPriceInputLayout.getEditText()).setText("");
        Objects.requireNonNull(discountPercentageInputLayout.getEditText()).setText("");
        Objects.requireNonNull(discountAmountInputLayout.getEditText()).setText(R.string.discount_amount_string);
        Objects.requireNonNull(amountInputLayout.getEditText()).setText(R.string.amount_string);
        discountPercentageInputLayout.getEditText().clearFocus();
        productPriceInputLayout.getEditText().clearFocus();
    }

    private void initializeViews() {
        productPriceInputLayout = findViewById(R.id.product_price_textInputLayout);
        discountPercentageInputLayout = findViewById(R.id.percentage_discount_textInputLayout);
        discountAmountInputLayout = findViewById(R.id.discount_amount_textInputLayout);
        amountInputLayout = findViewById(R.id.amount_textInputLayout);
        calculateButton = findViewById(R.id.calculate_button);
        resetButton = findViewById(R.id.reset_button);
    }

    @Override
    public void onClick(View v) {

        // checking which button is clicked
        // to call different methods
        // for every button
        if (v.getId() == R.id.reset_button) {
            clearInputData();
            return;
        }
        if (v.getId() == R.id.calculate_button) {
            calculateDiscountOfProduct();
        }

    }

    private void calculateDiscountOfProduct() {
        // getting input values
        // to calculate discount of product
        double product_price = Double.parseDouble(Objects.requireNonNull(productPriceInputLayout.getEditText()).getText().toString());
        double discount_percentage = Double.parseDouble(Objects.requireNonNull(discountPercentageInputLayout.getEditText()).getText().toString());

        // calculating the discount amount
        // and the amount/price after the discount
        double discount_amount = product_price * (discount_percentage / 100);
        double amount_to_pay = product_price - discount_amount;

        if (discount_amount >= product_price){
            discountPercentageInputLayout.setHelperTextEnabled(false);
            discountPercentageInputLayout.setErrorEnabled(true);
            discountPercentageInputLayout.setError("Error: high discount");
            Objects.requireNonNull(discountAmountInputLayout.getEditText()).setText(df.format(0));
            Objects.requireNonNull(amountInputLayout.getEditText()).setText(df.format(0));
        } else {
            discountPercentageInputLayout.setHelperText("*Required");
            // displaying the discounted amount
            // and the amount to pay
            // after the discount to the user
            Objects.requireNonNull(discountAmountInputLayout.getEditText()).setText(df.format(discount_amount));
            Objects.requireNonNull(amountInputLayout.getEditText()).setText(df.format(amount_to_pay));
        }

    }

}