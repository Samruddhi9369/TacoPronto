package edu.csulb.android.tacopronto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlaceOrder;
    private RadioGroup radioGroupSize, radioGroupTortilla;
    private CheckBox checkBeef,checkRice,checkChicken,checkBeans,checkWhiteFish,checkGallo,checkCheese,checkGuacamole,checkSeafood,checkLbt;
    private CheckBox checkSoda,checkMargarita,checkCerveza,checkTequila;
    private float price=0;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        btnPlaceOrder.setOnClickListener(this);
    }

    public void initialize(){
        btnPlaceOrder = (Button) findViewById(R.id.buttonPlaceOrder);
        //size and tortilla
        radioGroupSize = (RadioGroup) findViewById(R.id.radioGroupSize);
        radioGroupTortilla = (RadioGroup) findViewById(R.id.radioGroupTortilla);
        //checkboxes
        checkBeef = (CheckBox)findViewById(R.id.checkBeef);
        checkRice = (CheckBox)findViewById(R.id.checkRice);
        checkChicken = (CheckBox)findViewById(R.id.checkChicken);
        checkBeans = (CheckBox)findViewById(R.id.checkBeans);
        checkWhiteFish = (CheckBox)findViewById(R.id.checkWhiteFish);
        checkGallo = (CheckBox)findViewById(R.id.checkGallo);
        checkCheese = (CheckBox)findViewById(R.id.checkCheese);
        checkGuacamole = (CheckBox)findViewById(R.id.checkGuacamole);
        checkSeafood = (CheckBox)findViewById(R.id.checkSeafood);
        checkLbt = (CheckBox)findViewById(R.id.checkLbt);
        //Beverages
        checkSoda = (CheckBox)findViewById(R.id.checkSoda);
        checkMargarita = (CheckBox)findViewById(R.id.checkMargarita);
        checkCerveza = (CheckBox)findViewById(R.id.checkCerveza);
        checkTequila = (CheckBox)findViewById(R.id.checkTequila);
    }

    public String getSizeAndTortilla(){
        String order="";
        //Size
        RadioButton buttonSize = (RadioButton)  findViewById(radioGroupSize.getCheckedRadioButtonId());
        String size = buttonSize.getText().toString();
        if(size.matches(getResources().getString(R.string.large)))
            price+=5;
        else
            price+=3;
        order+= size+" size Taco";
        //Tortilla
        RadioButton buttonTortilla = (RadioButton)  findViewById(radioGroupTortilla.getCheckedRadioButtonId());
        String tortilla = buttonTortilla.getText().toString();
        if(tortilla.matches(getResources().getString(R.string.corn)))
            price+=4;
        else
            price+=2;
        order+=" of Tortilla "+tortilla;
        return order;
    }

    public String convertToString(ArrayList<String> order){
        String orderList="";
        StringBuilder orderBuilder = new StringBuilder();

        for (String str : order) {
            orderBuilder.append(str).append(", ");
        }

        orderBuilder.deleteCharAt(orderBuilder.length() - 2);

        return orderBuilder.toString();
    }

    public String getFillings(){
        String order="";
        order+=" with Fillings: ";
        ArrayList<String> fillings = new ArrayList<String>();

        if (checkBeef.isChecked())
            fillings.add(getResources().getString(R.string.beef));
        if (checkRice.isChecked())
            fillings.add(getResources().getString(R.string.rice));

        if (checkChicken.isChecked())
            fillings.add(getResources().getString(R.string.chicken));
        if (checkBeans.isChecked())
            fillings.add(getResources().getString(R.string.beans));

        if (checkWhiteFish.isChecked())
            fillings.add(getResources().getString(R.string.whitefish));
        if (checkGallo.isChecked())
            fillings.add(getResources().getString(R.string.gallo));

        if (checkCheese.isChecked())
            fillings.add(getResources().getString(R.string.cheese));
        if (checkGuacamole.isChecked())
            fillings.add(getResources().getString(R.string.guacamole));

        if (checkSeafood.isChecked())
            fillings.add(getResources().getString(R.string.seafood));
        if (checkLbt.isChecked())
            fillings.add(getResources().getString(R.string.lbt));

        if(fillings.isEmpty())
            order+=" with no Fillings";
        else {
            price = 2 * fillings.size();
            order += convertToString(fillings);
        }
        return order;
    }

    public String getBeverages(){
        String order="";
        order+=" and Beverages: ";
        ArrayList<String> beverages = new ArrayList<String>();
        if (checkSoda.isChecked()) {
            beverages.add(getResources().getString(R.string.soda));
            price+=2;
        }
        if (checkMargarita.isChecked()) {
            beverages.add(getResources().getString(R.string.margarita));
            price+=3;
        }

        if (checkCerveza.isChecked()) {
            beverages.add(getResources().getString(R.string.cerveza));
            price+=3;
        }
        if (checkTequila.isChecked()) {
            beverages.add(getResources().getString(R.string.tequila));
            price+=4;
        }
        if(beverages.isEmpty())
            order+=" and no Beverages.";
        else {
            order += convertToString(beverages);
        }
        return order;
    }

    public String getPrice(){
        return "\nTotal price is $"+price;
    }

    public void setToDefault(){
        price=0;

        radioGroupSize.check(R.id.radioLarge);
        radioGroupTortilla.check(R.id.radioCorn);

        checkBeef.setChecked(false);
        checkRice.setChecked(false);
        checkChicken.setChecked(false);
        checkBeans.setChecked(false);
        checkWhiteFish.setChecked(false);
        checkGallo.setChecked(false);
        checkCheese.setChecked(false);
        checkGuacamole.setChecked(false);
        checkSeafood.setChecked(false);
        checkLbt.setChecked(false);


        checkSoda.setChecked(false);
        checkMargarita.setChecked(false);
        checkCerveza.setChecked(false);
        checkTequila.setChecked(false);

    }

    @Override
    public void onClick(View v) {
        String order="Your order:\n";
        //Size
        order+=getSizeAndTortilla();
        //Fillings
        order+=getFillings();
        //Beverages
        order+=getBeverages();
        //Price
        order+=getPrice();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+15628507084", null, "Hello from Taco Pronto. \n"+order, null, null);
            Toast.makeText(MainActivity.this,"Your order has been placed. Thank you!",Toast.LENGTH_SHORT).show();

            setToDefault();

        }

    }

}
