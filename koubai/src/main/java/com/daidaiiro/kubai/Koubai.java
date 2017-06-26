package com.daidaiiro.kubai;

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

public class Koubai {

  private GoogleMap googleMap;
  private String    points;
  private int       startColor;
  private int       endColor;
  private int       startColorR;
  private int       starColorG;
  private int       starColorB;
  private int       endColorR;
  private int       endColorG;
  private int       endColorB;

  public Koubai(KoubaiComposer koubaiComposer) {
    googleMap = koubaiComposer.googleMap;
    points = koubaiComposer.points;
    startColorR = koubaiComposer.startColorR;
    starColorG = koubaiComposer.starColorG;
    starColorB = koubaiComposer.starColorB;
    endColorR = koubaiComposer.endColorR;
    endColorG = koubaiComposer.endColorG;
    endColorB = koubaiComposer.endColorB;

  }

  public static KoubaiComposer with(GoogleMap googleMap) {
    return new KoubaiComposer(googleMap);
  }

  private int getRGB(int size, int position, int startRGB, int endRGB) {
    int color = startRGB;
    float ment = (((float) (startRGB - endRGB) / size) * (float) position);

    if (startRGB > endColorR) {
      color = (int) ((float) startRGB - ment);
    } else if (startRGB < endRGB) {
      ment = (((float) (endRGB - startRGB) / size) * (float) position);
      color = (int) ((float) startRGB + ment);
    }
    return color;
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


  public static final class KoubaiComposer {

    private GoogleMap googleMap;
    private String    points;
    private int       startColorR;
    private int       starColorG;
    private int       starColorB;
    private int       endColorR;
    private int       endColorG;
    private int       endColorB;

    public KoubaiComposer(GoogleMap googleMap) {
      this.googleMap = googleMap;
    }

    public KoubaiComposer points(String points) {
      this.points = points;
      return this;
    }

    public KoubaiComposer startColor(int startColor) {
      this.startColorR = Color.red(startColor);
      this.starColorG = Color.green(startColor);
      this.starColorB = Color.blue(startColor);
      return this;
    }

    public KoubaiComposer endColor(int endColor) {
      this.endColorR = Color.red(endColor);
      this.endColorG = Color.green(endColor);
      this.endColorB = Color.blue(endColor);
      return this;
    }

    public Koubai draw() {
      Koubai koubai = new Koubai(this);
      koubai.drawLines();
      return koubai;
    }

  }


  private void drawLines() {
    Polyline line;
    List<Polyline> polylines = new ArrayList<Polyline>();

    List<LatLng> list = decodePoly(points);
    int size = list.size() - 1;

    for (int i = 0; i < size; i++) {
      LatLng src = list.get(i);
      LatLng dest = list.get(i + 1);

      int red = getRGB(size, i, startColorR, endColorR);
      int green = getRGB(size, i, starColorG, endColorG);
      int blue = getRGB(size, i, starColorB, endColorB);

      line = googleMap.addPolyline(new PolylineOptions()
          .add(new LatLng(src.latitude, src.longitude),
              new LatLng(dest.latitude, dest.longitude))
          .width(9)
          .color(Color.rgb(red, green, blue))
          .geodesic(true));
      polylines.add(line);
    }
  }

}
