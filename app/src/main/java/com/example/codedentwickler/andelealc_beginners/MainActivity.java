package com.example.codedentwickler.andelealc_beginners;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    double priceOfMilk = 10;
    double priceOfSugar = 5;
    boolean withMilk = false;
    boolean withSugar = false;
    private String totalPriceForOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);

        String name = nameEditText.getText().toString().trim();
        String[] email = new String[] {emailEditText.getText().toString().trim()};

        int priceOfOneCoffeeCup = 25;

        String sugar = withSugar ? "Yes" : "No";
        String milk = withMilk ? "Yes" : "No";

        displayPrice(quantity * priceOfOneCoffeeCup);

        String message = "Name: "+name + "\nWanted Sugar: "+sugar
                + "\nWanted Milk: "+milk + "\nQuantity: " +quantity
                + "\nTotal: " + totalPriceForOrder + "\nThanks for the Patronage";

        if (!email[0].isEmpty() || !name.isEmpty()) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, email);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject) + name);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, R.string.enter_name_email_toast_message,Toast.LENGTH_LONG).show();
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    private void displayPrice(int price){
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        double totalPrice = price;
        if (withMilk){
            totalPrice = totalPrice + priceOfMilk;
        }
        if (withSugar){
            totalPrice = totalPrice + priceOfSugar;
        }

        totalPriceForOrder = String.valueOf(totalPrice);
        priceTextView.setText(totalPriceForOrder);
    }

    public void decrement(View view) {
        quantity--;
        display(quantity);
    }

    public void increment(View view) {
        quantity++;
        display(quantity);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();

        int idOfCheckedCheckBox = view.getId();

        switch (idOfCheckedCheckBox) {

            case R.id.milk:
                if (checked){
                    withMilk = true;
                    priceOfMilk = 10;
                } else {
                    withMilk = false;
                    priceOfMilk = 0;
                }

                break;

            case R.id.sugar:
                if (checked) {
                    withSugar = true;
                    priceOfSugar = 5;
                } else {
                    withSugar = false;
                    priceOfSugar = 0;
                }

                break;
        }
    }
}
