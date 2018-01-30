package th.co.gosoft.storemobile.transporttracking.Dialogs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import th.co.gosoft.storemobile.transporttracking.Controller.DirectionFinder;
import th.co.gosoft.storemobile.transporttracking.Utility.DirectionFinderListener;
import th.co.gosoft.storemobile.transporttracking.Controller.GPSTracker;
import th.co.gosoft.storemobile.transporttracking.Controller.DB.DBHelper;
import th.co.gosoft.storemobile.transporttracking.Models.Map.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
 * Created by Jubjang on 9/18/2017.
 */

public class DialogMap extends DialogFragment implements DirectionFinderListener, OnMapReadyCallback {
    GoogleMap mGoogleMap;
    List<String> Lati = new ArrayList<>();
    List<String> Longi = new ArrayList<>();
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private static List<Polyline> polylinePaths = new ArrayList<>();
    private GPSTracker gpsTracker;
    private Location mLocation;
    Double yourLat, yourLong;
    Button btn_close;
    String storeid;
    SupportMapFragment mapFragment;
    DBHelper helper;
    SQLiteDatabase db;

    public DialogMap() {
    }

    public DialogMap(String storeid){
        this.storeid = storeid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(th.co.gosoft.storemobile.transporttracking.R.layout.dialog_map, container, false);
        mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(th.co.gosoft.storemobile.transporttracking.R.id.map_dialog);
        mapFragment.getMapAsync(this);
        btn_close = (Button) v.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_close_dialog);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //--------------- my current location ---------------------------
        System.out.println("dialog map position : "+storeid);
        gpsTracker = new GPSTracker(getContext());
        mLocation = gpsTracker.getLocation();
        yourLat = mLocation.getLatitude();
        yourLong = mLocation.getLongitude();
        Lati.add(yourLat.toString());
        Longi.add(yourLong.toString());
        //---------------------------------------------------------------

        //find Lat Lng for each store
        String qr = "SELECT * FROM "+ Constants.TABLE_NAME_STORE+" WHERE "+ Constants.STORE_ID_STORE+" = '"+storeid+"'";
        helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(qr,null);
        if (cursor.moveToFirst()){
            do {
                String Lat = cursor.getString(5);
                String Lng = cursor.getString(6);
                Lati.add(Lat);
                Longi.add(Lng);
            }while (cursor.moveToNext());
        }

        sendRequest();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getFragmentManager().beginTransaction().remove(mapFragment).commit();
            }
        });
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
            mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(th.co.gosoft.storemobile.transporttracking.R.drawable.ic_mark_grey))
                    .position(new LatLng(markLati, markLongi)));
        }
    }

    @Override
    public void onDirectionFinderStart() {
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
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        markLocation();
        double markLati = Double.parseDouble(Lati.get(0));
        double markLongi = Double.parseDouble(Longi.get(0));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(markLati, markLongi), 15));
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

}
