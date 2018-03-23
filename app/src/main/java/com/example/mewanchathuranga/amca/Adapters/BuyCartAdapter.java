package com.example.mewanchathuranga.amca.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.mewanchathuranga.amca.Model.BuyCartModel;
import com.example.mewanchathuranga.amca.R;
import com.example.mewanchathuranga.amca.listener.MyItemClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


/**
 * Created by mewanchathuranga on 20/03/2018.
 */

    public class BuyCartAdapter extends RecyclerView.Adapter<com.example.mewanchathuranga.amca.Adapters.BuyCartAdapter.ViewHolder> implements View.OnClickListener {

        public Context context;
        public List<BuyCartModel> getBuyCart;
        public MyItemClickListener mItemClickListener;
    public TextView itemPriceGlobal;
    public TextView itemNameGlobal;
    public ImageView qtyImgGlobal;


    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private MyItemClickListener mListener;

            public View mView;
            public TextView itemPrice;
            public TextView itemName;
            public ImageView qtyImg;


            public ViewHolder(View itemView, MyItemClickListener listener) {
                super(itemView);
                this.mView = itemView;
                this.mListener = listener;
                itemView.setOnClickListener(this);
                itemName = mView.findViewById(R.id.food_cart_item);
                itemPrice = mView.findViewById(R.id.food_cart_price);
                qtyImg=mView.findViewById(R.id.item_count_image);
                itemNameGlobal = itemName;
                itemPriceGlobal = itemPrice;
                qtyImgGlobal = qtyImg;
            }

            public void onClick(View v) {
                if (this.mListener != null) {
                    this.mListener.onItemClick(this.itemView, getLayoutPosition());
                }
            }
        }

    public BuyCartAdapter(Context context, List<BuyCartModel> getBuyCart) {
        this.context = context;
        this.getBuyCart = getBuyCart;
    }

    public BuyCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_buycart, parent, false);
        this.itemNameGlobal = parent.findViewById(R.id.food_cart_item);
        this.itemPriceGlobal = parent.findViewById(R.id.food_cart_price);
        this.qtyImgGlobal = parent.findViewById(R.id.item_count_image);
        return new ViewHolder(view, this.mItemClickListener);

    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(BuyCartAdapter.ViewHolder holder, int position) {

        holder.itemName.setText(this.getBuyCart.get(position).getFoodname());

        TextDrawable txtDrawable = TextDrawable.builder().buildRound(""+getBuyCart.get(position).getFoodqty(), Color.RED);
        holder.qtyImg.setImageDrawable(txtDrawable);

        Locale locale = new Locale("en", "MY");
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance(locale);
        int foodPrice = (Integer.parseInt(getBuyCart.get(position).getPrice()))*(Integer.parseInt(getBuyCart.get(position).getFoodqty()));
        holder.itemPrice.setText(priceFormat.format(foodPrice));
    }

    @Override
    public int getItemCount() {
        return this.getBuyCart.size();
    }



    public void deleteItem(int position) {
        this.getBuyCart.remove(position);
        notifyItemRemoved(position);
    }
    public void deleteBuyCart(){
        this.getBuyCart.removeAll(getBuyCart);
        notifyItemRemoved(getBuyCart.size());

    }
}
