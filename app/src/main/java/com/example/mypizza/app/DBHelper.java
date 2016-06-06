package com.example.mypizza.app;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 24/05/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PizzaRecettes.db";
    public static final String TABLE_NAME = "pizzas";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (id integer primary key, nom text, taille integer, description text, prix decimal(4,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPizza(String nom, int taille, double prix, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("taille", taille);
        contentValues.put("description", description);
        contentValues.put("prix", arrondirPrix(prix));
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean removeAllPizzas() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"", new String[0]);
        return true;
    }

    public Cursor getPizza(String nom, int taille) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where nom like " + nom + " and taille ="+  taille, null);
        return res;
    }


    public Cursor getPizza(String nom) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where nom like " + nom , null);
        return res;
    }

    public int numberOfPizzas() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int)DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public ArrayList<Pizza> getAllPizzas() {
        ArrayList<Pizza> res = new ArrayList<Pizza>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Pizza pizza = new Pizza();
            pizza.setNom(cursor.getString(cursor.getColumnIndex("nom")));
            pizza.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            pizza.setTaille(cursor.getInt(cursor.getColumnIndex("taille")));
            pizza.setPrix(cursor.getDouble(cursor.getColumnIndex("prix")));
            res.add(pizza);
            cursor.moveToNext();
        }
        return res;
    }



    private static double arrondirPrix(double prix) {
        BigDecimal res = new BigDecimal(prix);
        res = res.setScale(2, RoundingMode.HALF_DOWN);
        return res.doubleValue();
    }


}
