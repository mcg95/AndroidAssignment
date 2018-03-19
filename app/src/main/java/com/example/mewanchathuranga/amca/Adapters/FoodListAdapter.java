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
import com.example.mewanchathuranga.amca.Model.FoodListModel;
import com.example.mewanchathuranga.amca.R;
import com.example.mewanchathuranga.amca.listener.MyItemClickListener;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FoodListAdapter extends Adapter<ViewHolder> implements OnClickListener {
    private Context context;
    private TextView descTxt;
    private ImageView foodImg;
    private List<FoodListModel> foodList;
    private MyItemClickListener itemClickListener;
    private TextView nameTxt;
    private TextView priceTxt;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        public ImageView foodImg = ((ImageView) this.mView.findViewById(R.id.food_image));
        private MyItemClickListener mListener;
        public View mView;
        public TextView nameTxt = ((TextView) this.mView.findViewById(R.id.food_text));

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mView = itemView;
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (this.mListener != null) {
                this.mListener.onItemClick(this.itemView, getAdapterPosition());
            }
        }
    }

    public FoodListAdapter(Context mContext, List<FoodListModel> foodListModel) {
        this.context = mContext;
        this.foodList = foodListModel;
    }

    public void setOnItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list, parent, false);
        this.nameTxt = (TextView) view.findViewById(R.id.food_text);
        this.foodImg = (ImageView) view.findViewById(R.id.food_image);
        return new ViewHolder(view, this.itemClickListener);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        String sURL = ((FoodListModel) this.foodList.get(position)).getImage();
        Picasso.with(this.context).load(((FoodListModel) this.foodList.get(position)).getImage()).fit().into(this.foodImg);
        holder.nameTxt.setText(((FoodListModel) this.foodList.get(position)).getName());
    }

    public int getItemCount() {
        return this.foodList.size();
    }

    public void onClick(View v) {
    }
}
