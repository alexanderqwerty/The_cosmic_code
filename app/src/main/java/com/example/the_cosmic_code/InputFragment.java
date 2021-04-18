package com.example.the_cosmic_code;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;


public class InputFragment extends Fragment {
    private FragmentInputListener listener;
    private ImageButton button;
    private EditText name, cost, mass;
    private Spaceship spaceship;
    private ArrayList<Product> products;
    private ArrayList<String> list;

    public interface FragmentInputListener {
        void onInputInputFrag(Product p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AddProductActivity a = (AddProductActivity) getActivity();
        View v = inflater.inflate(R.layout.fragment_input, container, false);
        name = v.findViewById(R.id.name_input);
        cost = v.findViewById(R.id.cost_input);
        mass = v.findViewById(R.id.mass_input);
        button = v.findViewById(R.id.add_prod_button);
        list = new ArrayList<>();
        products = a.getProducts();
        for (int i = 0; i < products.size(); i++) {
            list.add(products.get(i).getName());
        }
        AddProductActivity activity = (AddProductActivity) getActivity();
        spaceship = activity.getSpaceship();
        button.setOnClickListener(v1 -> {

            if (checkForFill(name, cost, mass)) {
                Product p = new Product(spaceship, name.getText().toString(), Integer.parseInt(cost.getText().toString()), Integer.parseInt(mass.getText().toString()));
                listener.onInputInputFrag(p);
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInputListener)
            listener = (FragmentInputListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement FragmentInputListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private boolean checkForFill(EditText name, EditText cost, EditText mass) {
        boolean flag = true;
        if (name.getText().length() == 0 || list.contains(name.getText().toString())) {
            flag = false;
            name.setBackgroundColor(Color.RED);
        } else {
            name.setBackgroundColor(Color.TRANSPARENT);
        }
        if (cost.getText().length() == 0 || Integer.parseInt(cost.getText().toString()) <= 0) {
            flag = false;
            cost.setBackgroundColor(Color.RED);
        } else {
            cost.setBackgroundColor(Color.TRANSPARENT);
        }
        if (mass.getText().length() == 0 || Integer.parseInt(mass.getText().toString()) <= 0) {
            flag = false;
            mass.setBackgroundColor(Color.RED);
        } else {
            mass.setBackgroundColor(Color.TRANSPARENT);
        }
        return flag;
    }
}