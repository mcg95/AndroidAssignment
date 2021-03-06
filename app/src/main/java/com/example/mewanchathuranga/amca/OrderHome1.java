package com.example.mewanchathuranga.amca;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mewanchathuranga.amca.Adapters.OrderMenuAdapter;
import com.example.mewanchathuranga.amca.Model.FoodMenuModel;
import com.example.mewanchathuranga.amca.listener.MyItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OrderHome1 extends AppCompatActivity implements OnNavigationItemSelectedListener, MyItemClickListener {
    private ViewGroup container;
    private Context context;
    private FirebaseDatabase db;
    private FirebaseRecyclerAdapter fAdapter;
    private ImageView imgMenu;
    private MyItemClickListener itemClickListener;
    private LayoutInflater layoutInflater;
    public LayoutManager layoutManager;
    private OrderMenuAdapter mAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    public List<FoodMenuModel> menuList;
    private View menuListView;
    public RecyclerView recycler_menu;
    private TextView txtMenuName;
    private TextView txtUserEmail;
    FloatingActionButton buyCart;
    public View view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mewanchathuranga.amca.R.layout.activity_order_home1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        this.mAuth = FirebaseAuth.getInstance();
        this.menuList = new ArrayList();
        this.db = FirebaseDatabase.getInstance();
        this.mRef = this.db.getReference().child("Menu");
        this.mAdapter = new OrderMenuAdapter(this, this.menuList);
        this.mAdapter.setOnItemClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(com.example.mewanchathuranga.amca.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, com.example.mewanchathuranga.amca.R.string.navigation_drawer_open, com.example.mewanchathuranga.amca.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(com.example.mewanchathuranga.amca.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.txtUserEmail = (TextView) navigationView.getHeaderView(0).findViewById(com.example.mewanchathuranga.amca.R.id.currentUserEmail);
        this.txtUserEmail.setText(mAuth.getCurrentUser().getEmail());
        this.recycler_menu = (RecyclerView) findViewById(com.example.mewanchathuranga.amca.R.id.recycler_menu);
        this.recycler_menu.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recycler_menu.setLayoutManager(this.layoutManager);
        this.recycler_menu.setAdapter(this.mAdapter);
        retrieveMenu();
    }

    public void retrieveMenu() {
        this.db.getReference().child("Menu").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot menuSnapshot : dataSnapshot.getChildren()) {
                    OrderHome1.this.menuList.add((FoodMenuModel) menuSnapshot.getValue(FoodMenuModel.class));
                    Log.d("ContentValues", "onComplete: Data retrieved to menuList");
                }
                OrderHome1.this.mAdapter.notifyDataSetChanged();
                Log.d("ContentValues", "onComplete: Dataset change notified.");
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.example.mewanchathuranga.amca.R.id.drawer_layout);
        if (drawer.isDrawerOpen(8388611)) {
            drawer.closeDrawer(8388611);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.example.mewanchathuranga.amca.R.menu.order_home1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == com.example.mewanchathuranga.amca.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (!(id == com.example.mewanchathuranga.amca.R.id.order_menu || id == com.example.mewanchathuranga.amca.R.id.order_menu || id == com.example.mewanchathuranga.amca.R.id.cart || id == com.example.mewanchathuranga.amca.R.id.order_history || id != com.example.mewanchathuranga.amca.R.id.sign_out)) {
        }
        ((DrawerLayout) findViewById(com.example.mewanchathuranga.amca.R.id.drawer_layout)).closeDrawer(8388611);
        return true;
    }

    public void onItemClick(View view, int position) {
        FoodMenuModel MenuList = (FoodMenuModel) this.menuList.get(position);
        Intent foodList = new Intent(this, FoodList.class);
        foodList.putExtra("categoryid", ((FoodMenuModel) this.menuList.get(position)).getCategoryid());
        startActivity(foodList);
        Toast.makeText(this, "Clicked Item: " + MenuList.getName(), Toast.LENGTH_LONG).show();
    }
}
