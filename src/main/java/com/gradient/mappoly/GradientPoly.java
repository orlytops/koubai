package com.gradient.mappoly;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orly on 6/23/17.
 */

public class GradientPoly {

  private GoogleMap googleMap;
  private String    points;

  public GradientPoly(GoogleMap googleMap, String points) {
    this.googleMap = googleMap;
    this.points = points;
  }

  public void drawGradient() {

    Polyline line;
    List<Polyline> polylines = new ArrayList<Polyline>();

    List<LatLng> list = decodePoly(points);
    int size = list.size() - 1;

    for (int z = 0; z < size; z++) {
      LatLng src = list.get(z);
      LatLng dest = list.get(z + 1);

      int red = (int) ((float) 255 - (((float) (255 - 79) / size) * (float) z));
      int green = (int) ((float) 28 + (((float) (212 - 28) / size) * (float) z));
      int blue = (int) ((float) 93 - ((float) (93 / size) * (float) z));

      line = googleMap.addPolyline(new PolylineOptions()
          .add(new LatLng(src.latitude, src.longitude),
              new LatLng(dest.latitude, dest.longitude))
          .width(9)
          .color(Color.rgb(red, green, blue))
          .geodesic(true));
      polylines.add(line);
    }

  }

  private List<LatLng> decodePoly(String encoded) {

    List<LatLng> poly = new ArrayList<LatLng>();
    int index = 0, len = encoded.length();
    int lat = 0, lng = 0;

    while (index < len) {
      int b, shift = 0, result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lat += dlat;

      shift = 0;
      result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lng += dlng;

      LatLng p = new LatLng((((double) lat / 1E5)),
          (((double) lng / 1E5)));
      poly.add(p);
    }

    return poly;
  }

}
