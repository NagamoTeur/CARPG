package com.bobmowzie.mowziesmobs.client.model.tools;

public final class MathUtils {
   public static final float TAU = (float) (Math.PI * 2);
   public static final float PI = (float) Math.PI;

   public static double linearTransformd(double x, double domainMin, double domainMax, double rangeMin, double rangeMax) {
      x = x < domainMin ? domainMin : (x > domainMax ? domainMax : x);
      return (rangeMax - rangeMin) * (x - domainMin) / (domainMax - domainMin) + rangeMin;
   }

   public static double fit(double pct, double lbound, double hbound, double start, double end) {
      double npct = (pct - lbound) / (hbound - lbound);
      npct = Math.max(Math.min(1.0, npct), 0.0);
      return start + npct * (end - start);
   }
}
