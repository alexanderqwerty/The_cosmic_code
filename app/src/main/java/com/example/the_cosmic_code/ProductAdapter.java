package com.example.the_cosmic_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final List<Product> products;
    private final LayoutInflater inflater;
    private OnDeleteProductListener deleteListener;

    public ProductAdapter(Context context, List<Product> products) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
        try {
            this.deleteListener = (OnDeleteProductListener) context;
        } catch (Exception e) {
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        String mass = "Mass: " + product.getMass();
        holder.mass.setText(mass);
        String cost = "Cost: " + product.getCost();
        holder.cost.setText(cost);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public interface OnDeleteProductListener {
        void onDeleteProductEl(int pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView cost, mass, name;

        public ViewHolder(View view) {
            super(view);
            cost = view.findViewById(R.id.cost_item);
            mass = view.findViewById(R.id.mass_item);
            name = view.findViewById(R.id.name_item);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.menu_op);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            deleteListener.onDeleteProductEl(getAdapterPosition());
                            return true;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
        }
    }
}
