package th.co.gosoft.storemobile.transporttracking.Controller;


import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import th.co.gosoft.storemobile.transporttracking.Controller.DB.DBHelper;
import th.co.gosoft.storemobile.transporttracking.Models.Map.Route;
import th.co.gosoft.storemobile.transporttracking.Models.model_mapdetail;
import th.co.gosoft.storemobile.transporttracking.Utility.DirectionFinderListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.storemobile.transporttracking.Models.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapListener extends Fragment implements OnMapReadyCallback, DirectionFinderListener, GoogleMap.OnMarkerClickListener {
    GoogleMap mGoogleMap;
    List<String> markerDescription = new ArrayList<>();
    List<String> Lati = new ArrayList<>();
    List<String> Longi = new ArrayList<>();
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private static List<Polyline> polylinePaths = new ArrayList<>();
    private GPSTracker gpsTracker;
    private Location mLocation;
    String yourLat, yourLong;
    DBHelper helper;
    SQLiteDatabase db;
    int checkpoint_num = 1;

    public MapListener() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(th.co.gosoft.storemobile.transporttracking.R.layout.fragment_map_listener, container, false);
        MapsInitializer.initialize(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(th.co.gosoft.storemobile.transporttracking.R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //add your device location
        gpsTracker = new GPSTracker(getContext());
        mLocation = gpsTracker.getLocation();
        System.out.println(mLocation);
        if (mLocation != null) {
            yourLat = String.valueOf(mLocation.getLatitude());
            yourLong = String.valueOf(mLocation.getLongitude());
            Lati.add(yourLat);
            Longi.add(yourLong);
        } else {
            Toast.makeText(getContext(), "GPS Fail.", Toast.LENGTH_SHORT).show();
        }

        helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        String qr = "SELECT * FROM " + Constants.TABLE_NAME_STORE;
        Cursor cursor = db.rawQuery(qr, null);
        if (cursor.moveToFirst()) {
            do {
//                String work_id = cursor.getString(1);
                String store_id = cursor.getString(2);
//                String store_name = cursor.getString(3);
//                String store_phone = cursor.getString(4);
                String store_lat = cursor.getString(5);
                String store_lng = cursor.getString(6);
//                String store_type = cursor.getString(7);

                Lati.add(store_lat);
                Longi.add(store_lng);
                markerDescription.add(store_id);

            } while (cursor.moveToNext());
        }
        sendRequest();
//        checkPoint();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        double markLati = Double.parseDouble(Lati.get(0));
        double markLongi = Double.parseDouble(Longi.get(0));
        markLocation();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(markLati, markLongi), 15));
        mGoogleMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void sendRequest() {
        for (int i = 0; i < Lati.size(); i++) {
            if (i < Lati.size() - 1) {
                String origin = Lati.get(i) + "," + Longi.get(i);
                String destination = Lati.get(i + 1) + "," + Longi.get(i + 1);
                try {
                    new DirectionFinder(this, origin, destination).execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void markLocation() {
        for (int i = 1; i < Lati.size(); i++) {
            double markLati = Double.parseDouble(Lati.get(i));
            double markLongi = Double.parseDouble(Longi.get(i));
            String qrStatus = "SELECT " + Constants.STORE_TYPE + " FROM " + Constants.TABLE_NAME_STORE + " WHERE " + Constants.STORE_LAT + " = " + "'" + markLati + "'";
            helper = new DBHelper(getContext());
            db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery(qrStatus, null);
            cursor.moveToFirst();
            if (cursor.getString(0).equals("1")) {
                mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(th.co.gosoft.storemobile.transporttracking.R.drawable.ic_mark_green))
                        .position(new LatLng(markLati, markLongi)));
            } else if (cursor.getString(0).equals("2")) {
                mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(th.co.gosoft.storemobile.transporttracking.R.drawable.ic_mark_grey))
                        .position(new LatLng(markLati, markLongi)));
            } else if (cursor.getString(0).equals("3")) {
                mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(th.co.gosoft.storemobile.transporttracking.R.drawable.ic_mark_yellow))
                        .position(new LatLng(markLati, markLongi)));
            } else if (cursor.getString(0).equals("4")) {
                mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(th.co.gosoft.storemobile.transporttracking.R.drawable.ic_mark_red))
                        .position(new LatLng(markLati, markLongi)));
            }

//            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
//                    .title(route.endAddress)
//                    .position(route.endLocation)));
        }

    }

    private void checkPoint() {
        if (Lati.get(0) == null) {
            System.out.println("Don't have data.");
        } else {
            double LatiDes = Double.parseDouble(Lati.get(checkpoint_num));
            double LongiDes = Double.parseDouble(Longi.get(checkpoint_num));
            // if distance < 0.1
            if (distance(mLocation.getLatitude(), mLocation.getLongitude(), LatiDes, LongiDes) < 0.1) {
                ContentValues values = new ContentValues();
                values.put(Constants.STORE_STATUS, "1");
                db.update(Constants.TABLE_NAME_STORE, values, BaseColumns._ID + " = " + checkpoint_num,null);
                checkpoint_num++;
            } else {
                System.out.println("NOOOOOOOOOOO");
            }
        }
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371; // in miles, change to 6371 for kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;

        return dist;
    }

    @Override
    public void onDirectionFinderStart() {
//        System.out.println("onDirectionFinderStart");
//        progressDialog = ProgressDialog.show(getContext(), "Please wait.",
//                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
//        System.out.println("onDirectionFinderSuccess");
//        progressDialog.dismiss();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        for (Route route : routes) {
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final Dialog descriptDialog = new Dialog(getContext());
        descriptDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),15));
        System.out.println("position : " + marker.getId());
        String a = marker.getId();
        int b = Integer.parseInt(a.substring(1, a.length()));
        System.out.println("markerDescription.get(b); : " + markerDescription.get(b));

        String qr = "SELECT * FROM " + Constants.TABLE_NAME_STORE + " WHERE " +
                Constants.STORE_ID_STORE + " = '" + markerDescription.get(b) + "'";
        helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        Cursor mcursor = db.rawQuery(qr, null);
        model_mapdetail store = new model_mapdetail();
        //set data to dialog
        if (mcursor.moveToFirst()) {
            do {
                store.setStore_id(mcursor.getString(2));
                store.setStore_name(mcursor.getString(3));
                store.setStore_phone(mcursor.getString(4));
                System.out.println(mcursor.getString(0) + " " + mcursor.getString(1) + " " +
                        mcursor.getString(2) + " " + mcursor.getString(3) + " " + mcursor.getString(4) + " " +
                        mcursor.getString(5) + " " + mcursor.getString(6) + " " + mcursor.getString(7));
            } while (mcursor.moveToNext());
        }

        descriptDialog.setContentView(th.co.gosoft.storemobile.transporttracking.R.layout.dialog_mapdetail);
        TextView textView1 = (TextView) descriptDialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textset1);
        textView1.setText("รหัสร้านค้า " + store.getStore_id());
        TextView textView2 = (TextView) descriptDialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textset2);
        textView2.setText("สาขา " + store.getStore_name());
        TextView textView3 = (TextView) descriptDialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textset3);
        textView3.setText(store.getStore_phone());
        TextView textView4 = (TextView) descriptDialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textset4);

        String qr2 = "SELECT count(*) FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = " + "'" + markerDescription.get(b) + "'"
                + " and " + Constants.PRODUCT_TYPE + " = " + "'2'";
        Cursor mcursor2 = db.rawQuery(qr2, null);
        mcursor2.moveToFirst();
        int incout = mcursor2.getInt(0);
        System.out.println("incount : " + incout);
        textView4.setText("ส่งของ " + incout + " ชิ้น");

        String qr3 = "SELECT count(*) FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = " + "'" + markerDescription.get(b) + "'"
                + " and " + Constants.PRODUCT_TYPE + " = " + "'1'";
        Cursor mcursor3 = db.rawQuery(qr3, null);
        mcursor3.moveToFirst();
        int outcount = mcursor3.getInt(0);
        System.out.println("outcount : " + outcount);
        TextView textView5 = (TextView) descriptDialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textset5);
        textView5.setText("รับของ " + outcount + " ชิ้น");

        Button cancelButton = (Button) descriptDialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_close_dialog);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descriptDialog.dismiss();
            }
        });
        descriptDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        descriptDialog.setCancelable(false);
        descriptDialog.setCanceledOnTouchOutside(false);
        descriptDialog.show();

        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("MapListener onStop");
        onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("MapListener Destroy");
        Fragment fragment = (getFragmentManager().findFragmentById(th.co.gosoft.storemobile.transporttracking.R.id.map));
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

}
