package com.ernakh.aplicativoaula;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPS_Activity extends AppCompatActivity {

    //private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView latitudeTextView, longitudeTextView, enderecoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gps);

        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);
        enderecoTextView = findViewById(R.id.enderecoTextView);

        try
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        200);
            }

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Latitude e Longitude
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        latitudeTextView.setText("Latitude: " + latitude);
                        longitudeTextView.setText("Longitude: " + longitude);

                        // Obter endereço com Geocoder
                        getAddress(latitude, longitude);
                    } else {
                        Toast.makeText(GPS_Activity.this, "Não foi possível obter a localização", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        catch (Exception e)
        {
            Toast.makeText(GPS_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressDetails = address.getAddressLine(0);
                enderecoTextView.setText("Endereço: " + addressDetails);
            } else {
                enderecoTextView.setText("Endereço não encontrado");
            }
        } catch (IOException e) {
            e.printStackTrace();
            enderecoTextView.setText("Erro ao obter endereço");
        }
    }

    public void voltar(View view)
    {
        finish();
    }
}