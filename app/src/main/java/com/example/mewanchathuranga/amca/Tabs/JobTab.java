package com.example.mewanchathuranga.amca.Tabs;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mewanchathuranga.amca.R;
import com.firebase.ui.storage.BuildConfig;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class JobTab extends Fragment implements MyItemClickListener {
    private static final String COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final String FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private static final String FIRE_LOG = "Fire_log";
    private static final int PERMISSION_REQUEST_CODE = 1234;
    public double Lat;
    public double Lng;
    private Button completeBtn;
    private Context context;
    private FirebaseFirestore db;
    private EditText delAddress;
    private EditText delTimeFrame;
    public List<PendingDelivery> deliList;
    private DocumentReference documentReference;
    private int gPosition;
    private PendingDeliveryListAdapter mDataAdapter;
    private FusedLocationProviderClient mFLPC;
    private Boolean mPermissionsGranted = Boolean.valueOf(false);
    private EditText noOfItems;
    private EditText orderNo;
    private String orderRef = "Orders";
    private EditText orderTime;
    private RecyclerView pendingJobRV;
    private EditText restName;
    private EditText totalAmount;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_tab, container, false);
        this.db = FirebaseFirestore.getInstance();
        this.documentReference = this.db.collection(this.orderRef).document();
        this.deliList = new ArrayList();
        retrievePendingDelivery();
        this.mDataAdapter = new PendingDeliveryListAdapter(this.context, this.deliList);
        this.orderNo = (EditText) view.findViewById(R.id.orderNumberText);
        this.orderTime = (EditText) view.findViewById(R.id.orderTimeText);
        this.delAddress = (EditText) view.findViewById(R.id.deliveryAddressText);
        this.restName = (EditText) view.findViewById(R.id.restNameText);
        this.noOfItems = (EditText) view.findViewById(R.id.noOfItemsText);
        this.delTimeFrame = (EditText) view.findViewById(R.id.timeFrameText);
        this.totalAmount = (EditText) view.findViewById(R.id.totalAmountText);
        Button completeBtn = (Button) view.findViewById(R.id.completeDeliBtn);
        RecyclerView pendingJobRV = (RecyclerView) view.findViewById(R.id.pendingJobRV);
        pendingJobRV.setHasFixedSize(true);
        pendingJobRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        pendingJobRV.setAdapter(this.mDataAdapter);
        deliAddressDialog();
        this.mDataAdapter.setOnItemClickListener(this);
        completeBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                JobTab.this.endDelivery(v, JobTab.this.gPosition);
            }
        });
        getLocationPermissions();
        getDeviceLocation();
        return view;
    }

    private void getLocationPermissions() {
        Log.d("ContentValues", "Location Permission Requested");
        String[] permissions = new String[]{FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION) != 0) {
            ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_REQUEST_CODE);
        } else if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), COARSE_LOCATION) == 0) {
            this.mPermissionsGranted = Boolean.valueOf(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void retrievePendingDelivery() {
        this.db.collection(this.orderRef).addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
                    if (dc.getType() == Type.ADDED) {
                        Log.d("ContentValues", dc.getDocument().getId() + "=>" + dc.getDocument().getData());
                        JobTab.this.deliList.add((PendingDelivery) dc.getDocument().toObject(PendingDelivery.class));
                        JobTab.this.mDataAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        this.mPermissionsGranted = Boolean.valueOf(false);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE /*1234*/:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[0] == 0) {
                            this.mPermissionsGranted = Boolean.valueOf(false);
                            return;
                        }
                    }
                    this.mPermissionsGranted = Boolean.valueOf(true);
                    Toast.makeText(getActivity(), "Location Permissions Granted", 0).show();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void getDeviceLocation() {
        Log.d("ContentValues", "Obtaining Device Current Location");
        this.mFLPC = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if (this.mPermissionsGranted.booleanValue()) {
                this.mFLPC.getLastLocation().addOnCompleteListener(new OnCompleteListener() {
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            LatLng tempCurrentLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            double tLng = tempCurrentLocation.longitude;
                            double tLat = tempCurrentLocation.latitude;
                            JobTab.this.Lng = tLng;
                            JobTab.this.Lat = tLat;
                            Toast.makeText(JobTab.this.getActivity(), "Current Location - Lat: " + tLat + " Lng: " + tLng, 1).show();
                            return;
                        }
                        Log.d("ContentValues", "onComplete: Location NULL");
                        Toast.makeText(JobTab.this.getActivity(), "Location was not retrievable.", 0).show();
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("ContentValues", "getDeviceLocation: Security Exception" + e.getMessage());
        }
    }

    public void onItemClick(final View view, final int position) {
        Builder builder = new Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Confirm Order Acceptance");
        builder.setMessage("Do you wish to ACCEPT this Order?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                JobTab.this.confirmOrder(view, position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void deliAddressDialog() {
        this.delAddress.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Builder builder = new Builder(JobTab.this.getActivity());
                builder.setCancelable(true);
                builder.setTitle("Delivery Address");
                builder.setMessage(JobTab.this.delAddress.getText());
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Navigate to Location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentWaze = new Intent("android.intent.action.VIEW", Uri.parse("https://waze.com/ul?ll=" + JobTab.this.Lat + "," + JobTab.this.Lng + "&navigate=yes"));
                        Log.d("ContentValues", "setUserVisibleHint: Lat: " + JobTab.this.Lat + "Lng: " + JobTab.this.Lng);
                        JobTab.this.getActivity().startActivity(intentWaze);
                    }
                });
                builder.show();
            }
        });
    }

    private void confirmOrder(View view, int position) {
        PendingDelivery DeliList = (PendingDelivery) this.deliList.get(position);
        this.gPosition = position;
        if (DeliList != null) {
            Toast.makeText(getActivity(), DeliList.getOrderNo(), 0).show();
            Log.d("ContentValues", "DeliList: " + DeliList.getdTimeFrame());
            String textOrderNo = DeliList.getOrderNo();
            String textOrderTime = DeliList.getOrderTime();
            String textRestName = DeliList.getRestName();
            String textDeliAdd = DeliList.getDeliAdd();
            String textNoItems = DeliList.getNoOfItems();
            String textDeliTimeFrame = DeliList.getdTimeFrame();
            String textTotalAmount = DeliList.getTotalAmount();
            this.orderNo.setText(textOrderNo);
            this.restName.setText(textRestName);
            this.delAddress.setText(textDeliAdd);
            this.noOfItems.setText(textNoItems);
            this.delTimeFrame.setText(textDeliTimeFrame);
            this.orderTime.setText(textOrderTime);
            this.totalAmount.setText(textTotalAmount);
        }
    }

    private void endDelivery(View view, final int position) {
        if (((PendingDelivery) this.deliList.get(position)) != null) {
            Builder builder = new Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle("Confirm Delivery Completed");
            builder.setMessage("Do you wish to mark this Delivery as COMPLETED?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    JobTab.this.mDataAdapter.deleteItem(position);
                    JobTab.this.orderNo.setText(BuildConfig.FLAVOR);
                    JobTab.this.restName.setText(BuildConfig.FLAVOR);
                    JobTab.this.delAddress.setText(BuildConfig.FLAVOR);
                    JobTab.this.noOfItems.setText(BuildConfig.FLAVOR);
                    JobTab.this.delTimeFrame.setText(BuildConfig.FLAVOR);
                    JobTab.this.orderTime.setText(BuildConfig.FLAVOR);
                    JobTab.this.totalAmount.setText(BuildConfig.FLAVOR);
                    Toast.makeText(JobTab.this.getActivity(), "Delivery Completed! Thank You!", 0).show();
                    Log.d("ContentValues", "Delivery Completed!");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
}
