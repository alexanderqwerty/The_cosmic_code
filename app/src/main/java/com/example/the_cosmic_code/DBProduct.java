package com.example.the_cosmic_code;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DBProduct implements Serializable {
    public static final String DATABASE_NAME = "products.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "spaceships";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_COST = "Cost";
    public static final String COLUMN_MASS = "Mass";
    public static final String COLUMN_NAME_SPACESHIP = "Spaceship";
    public static final String COLUMN_MAX_MASS = "MaxMass";

    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_NAME = 1;
    public static final int NUM_COLUMN_COST = 2;
    public static final int NUM_COLUMN_MASS = 3;
    public static final int NUM_COLUMN_NAME_SPACESHIP = 4;
    public static final int NUM_COLUMN_MAX_MASS = 5;
    private static SQLiteDatabase database;

    public DBProduct(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public void insertAll(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            insert(products.get(i).getName(), products.get(i).getCost(), products.get(i).getMass(), products.get(i).getSpaceship().getMaxMass(), products.get(i).getSpaceship().getName());
        }
    }


    public long insert(String name, int cost, int mass, int maxMass, String nameSpaceship) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COST, cost);
        cv.put(COLUMN_MASS, mass);
        cv.put(COLUMN_MAX_MASS, maxMass);
        cv.put(COLUMN_NAME_SPACESHIP, nameSpaceship);
        return database.insert(TABLE_NAME, null, cv);
    }

    public int update(Product p) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MASS, p.getMass());
        cv.put(COLUMN_COST, p.getCost());
        cv.put(COLUMN_NAME, p.getName());
        cv.put(COLUMN_ID, p.getId());
        cv.put(COLUMN_NAME_SPACESHIP, p.getSpaceship().getName());
        cv.put(COLUMN_MAX_MASS, p.getSpaceship().getMaxMass());
        return database.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(p.getId())});
    }

    public void deleteAll() {
        database.delete(TABLE_NAME, null, null);
    }

    public void deleteAll(Spaceship spaceship) {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            do {
                int maxMass = cursor.getInt(NUM_COLUMN_MAX_MASS);
                String nameSpaceship = cursor.getString(NUM_COLUMN_NAME_SPACESHIP);
                int id = cursor.getInt(NUM_COLUMN_ID);
                if (spaceship.getMaxMass() == maxMass && spaceship.getName().equals(nameSpaceship))
                    delete(id);
            } while (cursor.moveToNext());
        cursor.close();
    }

    public void delete(int id) {
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }



    public Product select(int id) {
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        String name = cursor.getString(NUM_COLUMN_NAME);
        int mass = cursor.getInt(NUM_COLUMN_MASS);
        int cost = cursor.getInt(NUM_COLUMN_COST);
        int maxMass = cursor.getInt(NUM_COLUMN_MAX_MASS);
        String nameSpaceship = cursor.getString(NUM_COLUMN_NAME_SPACESHIP);
        cursor.close();
        return new Product(new Spaceship(maxMass, nameSpaceship), name, cost, mass, id);
    }

    public ArrayList<Spaceship> selectAllSpaceShip() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Spaceship> out = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            do {
                int maxMass = cursor.getInt(NUM_COLUMN_MAX_MASS);
                String nameSpaceship = cursor.getString(NUM_COLUMN_NAME_SPACESHIP);
                boolean contains = false;
                for (int i = 0; i < out.size(); i++) {
                    if (out.get(i).getName().equals(nameSpaceship) && out.get(i).getMaxMass() == maxMass)
                        contains = true;
                }
                if (!contains)
                    out.add(new Spaceship(maxMass, nameSpaceship));
            } while (cursor.moveToNext());
        return out;
    }

    public ArrayList<Product> selectAll() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Product> arr = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            do {
                int id = cursor.getInt(NUM_COLUMN_ID);
                String name = cursor.getString(NUM_COLUMN_NAME);
                int mass = cursor.getInt(NUM_COLUMN_MASS);
                int cost = cursor.getInt(NUM_COLUMN_COST);
                int maxMass = cursor.getInt(NUM_COLUMN_MAX_MASS);
                String nameSpaceship = cursor.getString(NUM_COLUMN_NAME_SPACESHIP);
                arr.add(new Product(new Spaceship(maxMass, nameSpaceship), name, cost, mass, id));
            } while (cursor.moveToNext());
        cursor.close();
        return arr;
    }

    public ArrayList<Product> selectAll(Spaceship spaceship) {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Product> out = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            do {
                int id = cursor.getInt(NUM_COLUMN_ID);
                String name = cursor.getString(NUM_COLUMN_NAME);
                int mass = cursor.getInt(NUM_COLUMN_MASS);
                int cost = cursor.getInt(NUM_COLUMN_COST);
                int maxMass = cursor.getInt(NUM_COLUMN_MAX_MASS);
                String nameSpaceship = cursor.getString(NUM_COLUMN_NAME_SPACESHIP);
                if (spaceship.getMaxMass() == maxMass && spaceship.getName().equals(nameSpaceship))
                    out.add(new Product(new Spaceship(maxMass, nameSpaceship), name, cost, mass, id));
            } while (cursor.moveToNext());
        cursor.close();
        return out;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_COST + " INT, " +
                    COLUMN_MASS + " INT, " +
                    COLUMN_NAME_SPACESHIP + " TEXT, " +
                    COLUMN_MAX_MASS + " INT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
