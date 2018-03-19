package com.example.mewanchathuranga.amca.Adapters;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mewanchathuranga.amca.Model.FoodMenuModel;
import com.example.mewanchathuranga.amca.R;
import com.example.mewanchathuranga.amca.listener.MyItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderMenuAdapter extends Adapter<OrderMenuAdapter.ViewHolder> implements OnClickListener {
    private Context context;
    public ImageView imgMenu;
    private MyItemClickListener itemClickListener;
    public List<FoodMenuModel> menuList;
    public TextView txtMenuName;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        public ImageView imgMenu = ((ImageView) this.mView.findViewById(R.id.menu_image));
        private MyItemClickListener mListener;
        View mView;
        public TextView txtMenuName = ((TextView) this.mView.findViewById(R.id.menu_text));

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mView = itemView;
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (this.mListener != null) {
                this.mListener.onItemClick(this.itemView, getAdapterPosition());
            }
        }
    }

    public OrderMenuAdapter(Context mContext, List<FoodMenuModel> foodMenuModel) {
        this.context = mContext;
        this.menuList = foodMenuModel;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list, parent, false);
        this.txtMenuName = (TextView) view.findViewById(R.id.menu_text);
        this.imgMenu = (ImageView) view.findViewById(R.id.menu_image);
        return new ViewHolder(view, this.itemClickListener);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        String sURL = ((FoodMenuModel) this.menuList.get(position)).getImage();
        Picasso.with(this.context).load(((FoodMenuModel) this.menuList.get(position)).getImage()).fit().into(this.imgMenu);
        holder.txtMenuName.setText(((FoodMenuModel) this.menuList.get(position)).getName());
    }

    public int getItemCount() {
        return this.menuList.size();
    }

    public void onClick(View v) {
    }
}
