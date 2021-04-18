package com.example.the_cosmic_code;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;


public class AddProductFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private InputFragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        ImageButton button = view.findViewById(R.id.open_close_button);


        button.setOnClickListener(v -> {
            if (fragment == null) {
                fragment = new InputFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.input_frame, fragment).commit();
                button.setImageResource(R.drawable.ic_arrowup);
            } else {
                getFragmentManager().beginTransaction().remove(fragment).commit();
                fragment = null;
                button.setImageResource(R.drawable.ic_arrowdown);
            }

        });
        return view;
    }
}