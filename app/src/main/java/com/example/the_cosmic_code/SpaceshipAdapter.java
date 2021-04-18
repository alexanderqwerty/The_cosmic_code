package com.example.the_cosmic_code;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;

import java.util.List;

public class SpaceshipAdapter extends RecyclerView.Adapter<SpaceshipAdapter.ViewHolder> {

    private List<Spaceship> spaceships;
    private final LayoutInflater inflater;
    private final AdapterListener adapterListener;
    private OnDeleteSpaceshipListener deleteListener;
    private AdapterListener mlistener;

    public void setSpaceships(List<Spaceship> spaceships) {
        this.spaceships = spaceships;
    }

    public SpaceshipAdapter(Context context, List<Spaceship> spaceships) {
        this.spaceships = spaceships;
        this.inflater = LayoutInflater.from(context);
        this.adapterListener = (AdapterListener) context;
        this.deleteListener = (OnDeleteSpaceshipListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_item_spaseship, parent, false);
        return new ViewHolder(v, adapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Spaceship spaceship = spaceships.get(position);
        holder.name.setText(spaceship.getName());

        holder.maxMass.setText(String.valueOf(spaceship.getMaxMass()));
    }

    @Override
    public int getItemCount() {
        return spaceships.size();
    }


    public interface AdapterListener {
        void onClickAdapter(int position);
    }

    public interface OnDeleteSpaceshipListener {
        void onDeleteSpaceshipEl(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final EditText name, maxMass;


        public ViewHolder(@NonNull View v, AdapterListener listener) {
            super(v);
            name = v.findViewById(R.id.name_ship);
            maxMass = v.findViewById(R.id.maxMass_ship);
            maxMass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (maxMass.getText().length()!=0)
                        spaceships.get(getAdapterPosition()).setMaxMass(Integer.parseInt(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    spaceships.get(getAdapterPosition()).setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            mlistener = listener;
            v.setOnClickListener(v1 -> {
                if (check())
                    mlistener.onClickAdapter(getAdapterPosition());
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.menu_op);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            deleteListener.onDeleteSpaceshipEl(getAdapterPosition());
                            return true;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
        }

        private boolean check() {
            boolean flag = true;
            if (name.getText().length() == 0) {
                flag = false;
                name.setBackgroundColor(Color.RED);
            } else {
                name.setBackgroundColor(Color.TRANSPARENT);
            }
            if (maxMass.getText().length() == 0 || Integer.parseInt(maxMass.getText().toString()) <= 0) {
                flag = false;
                maxMass.setBackgroundColor(Color.RED);
            } else
                maxMass.setBackgroundColor(Color.TRANSPARENT);
            return flag;
        }
    }
}
