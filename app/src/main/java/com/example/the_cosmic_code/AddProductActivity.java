package com.example.the_cosmic_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;


public class AddProductActivity extends AppCompatActivity implements InputFragment.FragmentInputListener, ProductAdapter.OnDeleteProductListener {

    private DBProduct database;
    private RecyclerView recyclerView;
    private ArrayList<Product> products;
    private Button button;
    private Spaceship spaceship;
    private ArrayList<Product> list;
    private int lastPrice = 0;

    public Spaceship getSpaceship() {
        return spaceship;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        spaceship = (Spaceship) getIntent().getSerializableExtra("SHIP");
        recyclerView = findViewById(R.id.list_products);
        database = (DBProduct) getIntent().getSerializableExtra("DATABASE");
        list = new ArrayList<>();
        button = findViewById(R.id.imageButton_toOutActivity);
        products = new ArrayList<>();
        database = new DBProduct(this);
        products = database.selectAll(spaceship);
        ProductAdapter adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter);
        list = check(spaceship.getMaxMass(), products, new Comparator1(), new Comparator2(), list, lastPrice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OutActivity.class);

                i.putExtra("BEST_PRICE", lastPrice);
                i.putExtra("SPACESHIP", spaceship);
                i.putExtra("DATABASE", getIntent().getExtras().getSerializable("DATABASE"));
                startActivity(i);
            }
        });
    }

    @Override
    public void onDeleteProductEl(int pos) {
        products.remove(products.get(pos));
        ProductAdapter adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter);
        lastPrice = 0;
        list = check(spaceship.getMaxMass(), products, new Comparator1(), new Comparator2(), list, lastPrice);
        database.deleteAll(spaceship);
        database.insertAll(list);
    }

    static class Comparator1 implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {

            if (o2.getRat() - o1.getRat() == 0)
                if (o2.getMass() - o1.getMass() == 0) {
                    if (!o2.getName().equals(o2.getName()))
                        return o2.getName().length() - o1.getName().length();
                } else
                    return o2.getMass() - o1.getMass();
            return (int) ((o2.getRat() - o1.getRat()) * 10);
        }
    }

    static class Comparator2 implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            return o2.getCost() - o1.getCost();
        }
    }

    @Override
    public void onInputInputFrag(Product p) {
        products.add(p);
        ProductAdapter adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter);
        list = check(spaceship.getMaxMass(), products, new Comparator1(), new Comparator2(), list, lastPrice);
        database.deleteAll(spaceship);
        database.insertAll(list);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> check(int maxMass, List<Product> list,
                                    Comparator<Product> comparator1,
                                    Comparator<Product> comparator2, ArrayList<Product> lastOut,
                                    int lastPrice) {
        TreeSet<Product> treeSet1 = new TreeSet<>(comparator1);
        TreeSet<Product> treeSet2 = new TreeSet<>(comparator2);
        treeSet1.addAll(list);
        treeSet2.addAll(list);
        ArrayList<Product> arrayList1 = new ArrayList<>();
        ArrayList<Product> arrayList2 = new ArrayList<>();
        int massFirst = 0;
        int massSecond = 0;
        int sumFirst = 0;
        int sumSecond = 0;

        while (massFirst < maxMass) {
            if (!treeSet1.isEmpty())
                if (treeSet1.first().getMass() + massFirst == maxMass) {
                    sumFirst += treeSet1.first().getCost();
                    massFirst += treeSet1.first().getMass();
                    arrayList1.add(treeSet1.first());
                    treeSet1.remove(treeSet1.first());
                } else if (treeSet1.first().getMass() + massFirst > maxMass)
                    treeSet1.remove(treeSet1.first());
                else {
                    sumFirst += treeSet1.first().getCost();
                    massFirst += treeSet1.first().getMass();
                    arrayList1.add(treeSet1.first());
                    treeSet1.remove(treeSet1.first());
                }
            if (!treeSet2.isEmpty())
                if (treeSet2.first().getMass() + massSecond == maxMass) {
                    sumSecond += treeSet2.first().getCost();
                    massSecond += treeSet2.first().getMass();
                    arrayList2.add(treeSet2.first());
                    treeSet2.remove(treeSet2.first());
                } else if (treeSet2.first().getMass() + massSecond > maxMass)
                    treeSet2.remove(treeSet2.first());
                else {
                    sumSecond += treeSet2.first().getCost();
                    massSecond += treeSet2.first().getMass();
                    arrayList2.add(treeSet2.first());
                    treeSet2.remove(treeSet2.first());
                }
            if (treeSet1.isEmpty() && treeSet2.isEmpty())
                break;
        }
        this.lastPrice = sumFirst > sumSecond ? Math.max(lastPrice, sumFirst) : Math.max(lastPrice, sumSecond);
        return sumFirst > sumSecond ? lastPrice > sumFirst ? lastOut : arrayList1 : lastPrice > sumSecond ? lastOut : arrayList2;
    }
}
