package com.example.mypizza.app;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.app.Activity;
import android.widget.LinearLayout;
import com.example.mypizza.app.R;

import java.util.*;


public class MainActivity extends Activity {

    private ArrayList<Pizza> panier = new ArrayList<Pizza>();
    private ArrayList<Pizza> pizzas = new ArrayList<Pizza>();

    private DBHelper monDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout myLinearLayout = (LinearLayout)findViewById(R.id.shinku);
        monDB = new DBHelper(this);

        if(monDB.numberOfPizzas() == 0) {
            insertPizzas();
        }

        pizzas = monDB.getAllPizzas();
        Log.d("MainActivity onCreate : ",pizzas.toString());


        Button b = (Button)findViewById(R.id.carteButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CarteActivity.class);
                Log.d("MainActivity : ","onCreate inner class " + pizzas);
                i.putParcelableArrayListExtra("pizzasListe", pizzas);
                i.putParcelableArrayListExtra("panier",panier);
                startActivity(i);
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("MainActivity: ","onSaveInstanceState called");
        savedInstanceState.putParcelableArrayList("pizzasListe",pizzas);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertPizzas() {
        monDB.insertPizza("Merguez",26, 9, "Tomate, Fromage, Poivrons, Merguez");
        monDB.insertPizza("Saumon", 26, 10, "Tomate, Fromage, Saumons, Creme Fraiche");
        monDB.insertPizza("Marguerite", 26, 5, "Tomate, Fromage");
    }

}
