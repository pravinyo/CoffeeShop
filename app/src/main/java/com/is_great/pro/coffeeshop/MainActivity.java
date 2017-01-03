/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
 package com.is_great.pro.coffeeshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.StringCharacterIterator;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private static int coffeeUpdates=0;
    CheckBox mWhippedCream,mExtaSugar,mCookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /**
     * This function calculates topping(if any)
    */
    int getToppings(){
        int price=0;
        mWhippedCream=(CheckBox) findViewById(R.id.topping_type_1_check_box);
        mExtaSugar= (CheckBox) findViewById(R.id.topping_type_2_check_box);
        mCookies = (CheckBox) findViewById(R.id.topping_type_3_check_box);
        boolean hasWhippedCream = mWhippedCream.isChecked();
        boolean hasExtraSugar = mExtaSugar.isChecked();
        boolean hasCookies = mCookies.isChecked();
        if(hasExtraSugar){
            price+=2;
        }
        if(hasCookies){
            price+=4;
        }
        if(hasWhippedCream){
            price+=6;
        }
     return price*coffeeUpdates;
    }
    /**
     * This function will fetch the name of the user
     */
    String getName(){
        EditText name=(EditText) findViewById(R.id.name);
        return name.getText().toString();
    }
    double getDicount(int price){
        return 0.03*price;
    }
    public void composeEmail(String subject,String order) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_EMAIL,"pravinyo12@gmail.com");
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String message="",Subject="Coffee Order Summary for "+getName();
        int totalPrice=0;
        if(coffeeUpdates>0){
            int priceTopping=getToppings();
            //TextView summary=(TextView) findViewById(R.id.price_text_view);
            totalPrice=priceTopping+coffeeUpdates*5;
            double discount=getDicount(totalPrice);
            String order="Name : "+getName()+
                    "\nQuantity : "+coffeeUpdates+
                    "\nDiscount : "+discount+
                    "\nYour Total Due is : "+((double)totalPrice-discount);
            //summary.setText(order);
            composeEmail(Subject,order);
            message="Thank you, Your order will be served soon";
        }
        else {
            message="You haven't selected coffee";
        }
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * This method increment the quantity value
     */
    public void increment(View view){
        if(coffeeUpdates<20) {
            coffeeUpdates++;
            display(coffeeUpdates);
            displayPrice(coffeeUpdates * 5);
        }
    }
    /**
     * This method decrement the quantity value
     */
    public void decrement(View view){

        if(coffeeUpdates>0){
            coffeeUpdates--;
            display(coffeeUpdates);
            displayPrice(coffeeUpdates*5);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.count_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}