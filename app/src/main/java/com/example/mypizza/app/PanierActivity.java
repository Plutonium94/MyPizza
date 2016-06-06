package com.example.mypizza.app;

import android.net.Uri;
import android.telephony.SmsManager;
import android.provider.Telephony;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.mypizza.app.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by user on 03/06/16.
 */
public class PanierActivity extends Activity {

    private ArrayList<Pizza> panier = new ArrayList<Pizza>();


    @Override
    protected void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        Log.d("PanierActivity:","PanierAcitvity constructor called");
        Intent i = getIntent();
        this.panier = i.getParcelableArrayListExtra("panier");
        setContentView(R.layout.acitvity_panier);

        TextView prixText = (TextView)findViewById(R.id.prixText);
        double prix = 0.0;
        for(Pizza p : panier) {
            prix += p.getPrix();
        }
        prixText.setText(prix + " €");

        GridView panierGrid = (GridView)findViewById(R.id.panierGrid);

        final Context that = this;

        panierGrid.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                Log.d("panierGrid size: ", (panier.size()*2) + "");
                return panier.size() * 2;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Log.d("panierGrid i : ", i+"");
                Pizza pizza =  panier.get(i/2);
                if((i+1)%2 == 1) {
                    TextView tv = new TextView(that);
                    tv.setText(Html.fromHtml("<strong>"+ pizza.getNom()+"</strong>&nbsp;" + pizza.getPrix()));
                    return tv;
                } else {
                    Button removeButton = new Button(that);
                    /*ViewGroup.LayoutParams layoutParams = removeButton.getLayoutParams();
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                    removeButton.setLayoutParams(layoutParams);*/
                    removeButton.setText("Retirer");
                    //Button removeButton = (Button)findViewById(R.id.removeButton);
                    return removeButton;
                }
            }
        });

        Button commanderButton = (Button)findViewById(R.id.commanderButton);
        commanderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commande = "MyPizzaCommande [" + getPrix()+ "] " + panier.toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("5554", null, commande, null, null);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:5554"));
                sendIntent.putExtra("sms_body", commande);
                if(sendIntent.resolveActivity(getPackageManager()) != null) {
                    panier = new ArrayList<Pizza>();
                    startActivity(sendIntent);

                }

            }
        });

        /*for(Pizza pizza : panier) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv = new TextView(this);
            tv.setText(pizza.getNom() + " " + pizza.getPrix());
            layout.addView(tv);
            Button b = new Button(this);
            b.setText("Retirer");
            layout.addView(b);
            linearLayout.addView(layout);

        }*/


    }

    public double getPrix() {
        double res = 0.0;
        for(Pizza p : panier) {
            res += p.getPrix();
        }
        return res;
    }

}
