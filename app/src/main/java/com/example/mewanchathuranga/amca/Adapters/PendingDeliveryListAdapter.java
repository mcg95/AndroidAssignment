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
import android.widget.TextView;
import com.example.mewanchathuranga.amca.Model.PendingDelivery;
import com.example.mewanchathuranga.amca.R;
import com.example.mewanchathuranga.amca.listener.MyItemClickListener;
import java.util.List;

public class PendingDeliveryListAdapter extends Adapter<ViewHolder> {
    public String cOrderNo;
    private Context context;
    public List<PendingDelivery> getPendingList;
    public MyItemClickListener mItemClickListener;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        TextView delAddText = ((TextView) this.mView.findViewById(R.id.deliveryAddressText));
        TextView itemsText = ((TextView) this.mView.findViewById(R.id.noOfItemsText));
        private MyItemClickListener mListener;
        View mView;
        TextView restNameText = ((TextView) this.mView.findViewById(R.id.restaurantNameText));

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mView = itemView;
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (this.mListener != null) {
                this.mListener.onItemClick(this.itemView, getLayoutPosition());
            }
        }
    }

    public PendingDeliveryListAdapter(Context context, List<PendingDelivery> pendingDelivery) {
        this.context = context;
        this.getPendingList = pendingDelivery;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_delivery, parent, false), this.mItemClickListener);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.restNameText.setText(((PendingDelivery) this.getPendingList.get(position)).getRestName());
        holder.delAddText.setText(((PendingDelivery) this.getPendingList.get(position)).getDeliAdd());
        holder.itemsText.setText(((PendingDelivery) this.getPendingList.get(position)).getNoOfItems());
    }

    public int getItemCount() {
        return this.getPendingList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void deleteItem(int position) {
        this.getPendingList.remove(position);
        notifyItemRemoved(position);
    }
}
