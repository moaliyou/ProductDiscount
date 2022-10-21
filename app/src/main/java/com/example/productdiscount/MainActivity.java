package com.example.productdiscount;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productdiscount.classes.Product;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    // Creating objects for
    // text input layouts
    // to read the inputted data
    // and display the result
    private TextInputLayout productPriceInputLayout, discountPercentageInputLayout,
            discountAmountInputLayout, amountInputLayout;
    private AutoCompleteTextView productsDropDownMenu;

    // Creating objects for
    // the button to calculate
    // and reset/clear input data
    private Button calculateButton, resetButton;

    private Product productObject;

    private final TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String product_price_value = Objects.requireNonNull(
                    productPriceInputLayout.getEditText()).getText().toString();

            String percentage_discount_value = Objects.requireNonNull(
                    discountPercentageInputLayout.getEditText()).getText().toString();


            if (!product_price_value.isEmpty() && !percentage_discount_value.isEmpty()) {
                productObject.setProductPrice(Double.parseDouble(product_price_value));
                productObject.setPercentageDiscount(Double.parseDouble(percentage_discount_value));

                if (productObject.getDiscountedAmount() >= productObject.getProductPrice()){
                    discountPercentageInputLayout.setHelperTextEnabled(false);
                    discountPercentageInputLayout.setErrorEnabled(true);
                    discountPercentageInputLayout.setError("Error: the discount % is higher");

                    Objects.requireNonNull(discountAmountInputLayout.getEditText())
                            .setText(R.string.discount_amount_hint_string);

                    Objects.requireNonNull(amountInputLayout.getEditText())
                            .setText(R.string.amount_hint_string);
                } else {
                    discountPercentageInputLayout.setErrorEnabled(false);
                    discountPercentageInputLayout.setHelperTextEnabled(true);
                    discountPercentageInputLayout.setHelperText("*Required");
                }

            }

            calculateButton.setEnabled(
                    !product_price_value.isEmpty() && !percentage_discount_value.isEmpty()
                    && !(productObject.getDiscountedAmount() >= productObject.getProductPrice())
            );
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

        fetchListOfProducts();

    }

    private void eventHandling() {

        resetButton.setOnClickListener(this);
        calculateButton.setOnClickListener(this);
        productsDropDownMenu.setOnItemClickListener(this);

        Objects.requireNonNull(productPriceInputLayout.getEditText())
                .addTextChangedListener(inputTextWatcher);

        Objects.requireNonNull(discountPercentageInputLayout.getEditText())
                .addTextChangedListener(inputTextWatcher);
    }

    private void clearInputData() {
        Objects.requireNonNull(productPriceInputLayout.getEditText()).setText("");
        Objects.requireNonNull(discountPercentageInputLayout.getEditText()).setText("");

        Objects.requireNonNull(discountAmountInputLayout.getEditText())
                .setText(R.string.discount_amount_hint_string);

        Objects.requireNonNull(amountInputLayout.getEditText())
                .setText(R.string.amount_hint_string);

        discountPercentageInputLayout.getEditText().clearFocus();
        productPriceInputLayout.getEditText().clearFocus();
        productsDropDownMenu.setText(null);

    }

    private void initializeViews() {
        productPriceInputLayout = findViewById(R.id.product_price_layout);
        discountPercentageInputLayout = findViewById(R.id.percentage_discount_layout);
        discountAmountInputLayout = findViewById(R.id.discount_amount_layout);
        amountInputLayout = findViewById(R.id.amount_layout);
        productsDropDownMenu = findViewById(R.id.products_dropDown_menu);
        calculateButton = findViewById(R.id.calculate_button);
        resetButton = findViewById(R.id.reset_button);
        productObject = new Product();

        productObject.setListOfProducts(
                Arrays.asList(this.getResources().getStringArray(R.array.list_of_products))
        );

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

    private void fetchListOfProducts(){

        ArrayAdapter<String> productListAdapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.list_products,
                productObject.getListOfProducts()
        );

        productsDropDownMenu.setAdapter(productListAdapter);

    }

    private void calculateDiscountOfProduct() {
        // getting input values
        // to calculate discount of product
       productObject.setProductPrice(
               Double.parseDouble(
                       Objects.requireNonNull(productPriceInputLayout.getEditText())
                               .getText()
                               .toString()
               )
       );

       productObject.setPercentageDiscount(
               Double.parseDouble(
                       Objects.requireNonNull(discountPercentageInputLayout.getEditText())
                               .getText()
                               .toString()
               )
       );

       Objects.requireNonNull(discountAmountInputLayout.getEditText())
               .setText(String.valueOf(productObject.getDiscountedAmount()));

       Objects.requireNonNull(amountInputLayout.getEditText())
               .setText(String.valueOf(productObject.getAmountToPay()));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,
                "Selected Item " + parent.getItemAtPosition(position)
                        + " at position " + position,
                Toast.LENGTH_SHORT)
                .show();
    }
}