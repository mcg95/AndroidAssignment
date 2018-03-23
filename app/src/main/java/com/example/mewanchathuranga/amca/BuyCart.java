package com.example.mewanchathuranga.amca;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mewanchathuranga.amca.Adapters.BuyCartAdapter;
import com.example.mewanchathuranga.amca.Model.BuyCartModel;
import com.example.mewanchathuranga.amca.Model.PendingDelivery;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BuyCart extends AppCompatActivity {
    private Context context;
    private FirebaseFirestore db;
    public List<BuyCartModel> buyCartList;
    private DocumentReference documentReference;
    private int gPosition;
    private BuyCartAdapter mDataAdapter;
    private FusedLocationProviderClient mFLPC;
    private String cartRef = "BuyCart";
    private RecyclerView recycler_cart;
    public RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth mAuth;
    public static final String TAG = "Mewan";
    private TextView totalPrice;
    public int totPrice = 0;
    public int totQty = 0;
    public int itemNo = 0;
    Date currentTime = Calendar.getInstance().getTime();
    public AppCompatButton placeOrderBtn;
    Place deliAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_cart);


        View mView;
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        this.documentReference = this.db.collection(this.cartRef).document();
        this.buyCartList = new ArrayList();
        this.mDataAdapter = new BuyCartAdapter(this.context, this.buyCartList);
        this.placeOrderBtn = this.findViewById(R.id.place_order_btn);
        this.recycler_cart = this.findViewById(R.id.cart_list);
        this.recycler_cart.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recycler_cart.setLayoutManager(this.layoutManager);
        this.recycler_cart.setAdapter(this.mDataAdapter);
        this.totalPrice = this.findViewById(R.id.total_price_text);
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Alert Dialog activated!");

                showAlertDialog();
            }
        });
        retrieveBuyCart();



    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BuyCart.this);
        alertDialog.setTitle("Address Selection");
        alertDialog.setMessage("Please select your location.");

        LayoutInflater inflater = this.getLayoutInflater();
        View addressEntry = inflater.inflate(R.layout.fragment_autocomplete_places, null);

        final PlaceAutocompleteFragment addText = (PlaceAutocompleteFragment) getFragmentManager().
                findFragmentById(R.id.place_autocomplete_fragment);
        LinearLayout.LayoutParams layoutP = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        alertDialog.setView(addressEntry);

        addText.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
            deliAdd = place;
            }

            @Override
            public void onError(Status status) {
            Log.e("ERROR",status.getStatusMessage());
            }
        });
        Log.d(TAG,"Alert Dialog appeared!");

        alertDialog.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String totQtyStr = String.valueOf(totQty);
                PendingDelivery pendingDelivery = new PendingDelivery(
                        mAuth.getCurrentUser().getEmail(),deliAdd.getAddress().toString(),totQtyStr ,currentTime.toString()
                        ,totalPrice.getText().toString()
                        ,String.format("%s,%s",deliAdd.getLatLng().latitude,deliAdd.getLatLng().longitude));

                db.collection("Orders").document(String.valueOf(System.currentTimeMillis())).set(pendingDelivery);
                Toast.makeText(BuyCart.this,"Order has been Confirmed!",Toast.LENGTH_SHORT).show();
                clearBuyCart();

            }
        });
        alertDialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager()
                        .beginTransaction()
                        .remove(getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment))
                        .commit();
            }
        });
        alertDialog.show();
    };


    private void retrieveBuyCart() {

        this.db.collection("BuyCart")
                .document(mAuth.getCurrentUser().getUid())
                .collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        BuyCartModel buyCartModel = document.toObject(BuyCartModel.class);
                        buyCartList.add(buyCartModel);
                        BuyCart.this.mDataAdapter.notifyDataSetChanged();
                        if (itemNo<buyCartList.size() - 1){
                            itemNo++;}

                        totPrice+=(Integer.parseInt(buyCartList.get(itemNo).getPrice()))
                                *(Integer.parseInt(buyCartList.get(itemNo).getFoodqty()));
                        totQty+=(Integer.parseInt(buyCartList.get(itemNo).getFoodqty()));
                        Locale locale = new Locale("en", "MY");
                        NumberFormat priceFormat = NumberFormat.getCurrencyInstance(locale);
                        Log.d(TAG, "Log 2" + totPrice);
                        Toast.makeText(getApplicationContext(), ""+totPrice, Toast.LENGTH_LONG).show();
                        totalPrice.setText(priceFormat.format(totPrice));
                        Log.d(TAG, "Log 1"+buyCartList.get(itemNo).getPrice().toString() + buyCartList.get(itemNo).getFoodqty().toString() + totPrice);


                        Log.d(TAG, "Log X" + buyCartModel.getFoodname().toString()+"  " + buyCartModel.getFoodqty().toString() +"  "+ buyCartModel.getPrice().toString() );
                    }
                } else {
                }
            }

        });

    }
    private void clearBuyCart(){
        db.collection("BuyCart").document(mAuth.getCurrentUser().getUid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //UID matches document, yet no updates on deletion in the database.
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Log.d(TAG, "Current User UID: " + mAuth.getCurrentUser().getUid());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }


}
