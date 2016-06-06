    package com.example.mypizza.app;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.Html;

import java.util.*;
import com.example.mypizza.app.R;
import android.view.ViewGroup.LayoutParams;

public class CarteActivity extends ActionBarActivity {

    ArrayList<Pizza> panier = new ArrayList<Pizza>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();


        ArrayList<Pizza> pizzas = i.getParcelableArrayListExtra("pizzasListe");
        this.panier = i.getParcelableArrayListExtra("panier");
        Log.d("Android: ", pizzas.toString());
        setContentView(R.layout.activity_carte);

        Button accueilLink = (Button)findViewById(R.id.accueilLink);
        Button panierLink = (Button)findViewById(R.id.panierLink);

        accueilLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putParcelableArrayListExtra("panier",panier);
                startActivity(i);
            }
        });

        panierLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), PanierActivity.class );
                i.putParcelableArrayListExtra("panier",panier);

                startActivity(i);
            }
        });

        GridView gridView = (GridView)findViewById(R.id.gridCarte);
        gridView.setAdapter(new CarteAdapter(this,pizzas));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.carte, menu);
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

    class CarteAdapter extends BaseAdapter {

        private ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
        private Context context;

        public CarteAdapter(Context c, ArrayList<Pizza> pizzas) {
            context = c;
            this.pizzas = pizzas;
        }

        @Override
        public int getCount() {
            return pizzas.size() * 3;
        }

        @Override
        public Object getItem(int id) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Pizza p = pizzas.get(position/3);
            if((position + 1)%3 == 1) {
                TextView tv = new TextView(context);

                tv.setText(Html.fromHtml("<b>" + p.getNom() + "</b><br />" + p.getTaille() + " cm<br /> "));

                return tv;
            } else if((position+1)%3 == 2) {
                TextView tv = new TextView(context);
                tv.setText(p.getPrix() + " €");
                return tv;
            } else {
                Button b = new Button(context);
                b.setText("Rajouter au  panier");
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        panier.add(p);
                    }
                });
                return b;


            }
        }
    }

}
