package com.example.orly.koubai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daidaiiro.koubai.Koubai;
import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {
  private GoogleMap googleMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Koubai.with(googleMap).points("");
  }
}
