package com.example.mewanchathuranga.amca;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mewanchathuranga.amca.Adapters.FoodListAdapter;
import com.example.mewanchathuranga.amca.Model.FoodListModel;
import com.example.mewanchathuranga.amca.listener.MyItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodInfo extends AppCompatActivity implements MyItemClickListener {
    private FloatingActionButton cartBtn;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context context;
    private FirebaseDatabase db;
    private ElegantNumberButton eNoBtn;
    private TextView foodDesc;
    private ImageView foodImg;
    public List<FoodListModel> foodList;
    private TextView foodName;
    private TextView foodPrice;
    private String foodid;
    private MyItemClickListener itemClickListener;
    private FoodListAdapter mAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    public View view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseDatabase.getInstance();
        this.mRef = this.db.getReference().child("Food");
        this.eNoBtn = (ElegantNumberButton) findViewById(R.id.e_number_button);
        this.cartBtn = (FloatingActionButton) findViewById(R.id.food_info_cartbtn);
        this.foodDesc = (TextView) findViewById(R.id.food_info_desc);
        this.foodPrice = (TextView) findViewById(R.id.food_info_price);
        this.foodName = (TextView) findViewById(R.id.food_info_name);
        this.foodImg = (ImageView) findViewById(R.id.food_info_image);
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingLayout);
        this.collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        this.collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        if (getIntent() != null) {
            this.foodid = getIntent().getStringExtra("foodid");
        }
        if (!this.foodid.isEmpty() && this.foodid != null) {
            retrieveFoodInfo(this.foodid);
        }
    }

    public void retrieveFoodInfo(String foodid) {
        this.db.getReference().child("Food").child(foodid).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                FoodListModel foodListModel = (FoodListModel) dataSnapshot.getValue(FoodListModel.class);
                Log.d("ContentValues", "onComplete: Data retrieved to foodList");
                Picasso.get().load(foodListModel.getImage()).fit().into(FoodInfo.this.foodImg);
                FoodInfo.this.collapsingToolbarLayout.setTitle(foodListModel.getName());
                FoodInfo.this.foodName.setText(foodListModel.getName());
                FoodInfo.this.foodDesc.setText(foodListModel.getDescription());
                FoodInfo.this.foodPrice.setText(foodListModel.getPrice());
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onItemClick(View view, int position) {
        FoodListModel FoodList = (FoodListModel) this.foodList.get(position);
    }
}