package com.example.mewanchathuranga.amca;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mewanchathuranga.amca.Adapters.FoodListAdapter;
import com.example.mewanchathuranga.amca.Model.FoodListModel;
import com.example.mewanchathuranga.amca.listener.MyItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodList extends AppCompatActivity implements MyItemClickListener {
    private String categoryid;
    private Context context;
    private FirebaseDatabase db;
    public List foodList;
    private MyItemClickListener itemClickListener;
    public LayoutManager layoutManager;
    private FoodListAdapter mAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    public RecyclerView recycler_food;
    private TextView txtUserEmail;
    public View view;

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(R.layout.food_list);
        this.mAuth = FirebaseAuth.getInstance();
        this.foodList = new ArrayList();
        this.db = FirebaseDatabase.getInstance();
        this.mRef = this.db.getReference().child("Food");
        this.mAdapter = new FoodListAdapter(this, this.foodList);
        this.mAdapter.setOnItemClickListener(this);
        this.recycler_food = (RecyclerView)this.findViewById(R.id.recyclerFoodList);
        this.recycler_food.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recycler_food.setLayoutManager(this.layoutManager);
        this.recycler_food.setAdapter(this.mAdapter);
        if(this.getIntent() != null) {
            this.categoryid = this.getIntent().getStringExtra("categoryid");
        }

        if(!this.categoryid.isEmpty() && this.categoryid != null) {
            this.retrieveFoodList(this.categoryid);
        }

    }

    public void onItemClick(View var1, int var2) {
        Intent var3 = new Intent(this, FoodInfo.class);
        var3.putExtra("foodid", ((FoodListModel)this.foodList.get(var2)).getFoodid());
        this.startActivity(var3);
    }

    public void retrieveFoodList(String var1) {
        this.db.getReference().child("Food").orderByChild("menuid").equalTo(var1).addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError var1) {
            }

            public void onDataChange(DataSnapshot var1) {
                Iterator var2 = var1.getChildren().iterator();

                while(var2.hasNext()) {
                    FoodListModel var4 = (FoodListModel)((DataSnapshot)var2.next()).getValue(FoodListModel.class);
                    FoodList.this.foodList.add(var4);
                    Log.d("ContentValues", "onComplete: Data retrieved to menuList");
                }

                FoodList.this.mAdapter.notifyDataSetChanged();
                Log.d("ContentValues", "onComplete: Dataset change notified.");
            }
        });
    }
}
