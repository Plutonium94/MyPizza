package com.example.mypizza.app;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcel;

public class Pizza implements Parcelable {

    private String nom;
    private String description;
    private int taille;
    private double prix;

    public Pizza() {

    }

    public Pizza(String nom, String description, int taille, double prix) {
        this.nom = nom;
        this.description = description;
        this.taille = taille;
        this.prix = prix;
    }

    public Pizza(String nom, double prix) {
        this(nom,"",26, prix);
    }

    @Override
    public String toString() {
        return " " + nom +"[" + prix + "]";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getTaille() {
        return taille;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getPrix() {
        return prix;
    }

    // implementer Parcelable

    private Pizza(Parcel in) {
        this.nom = in.readString();
        this.description = in.readString();
        this.taille = in.readInt();
        this.prix = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nom);
        out.writeString(description);
        out.writeInt(taille);
        out.writeDouble(prix);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Pizza> CREATOR = new Parcelable.Creator<Pizza>() {

        public Pizza createFromParcel(Parcel in) {
            return new Pizza(in);
        }

        public Pizza[] newArray(int size) {
            return new Pizza[size];
        }


    };
}