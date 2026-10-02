package net.thirdlife.iterrpg.procedures;

public class PusidonUmriProcedure {
   public static boolean execute(double x, double z) {
      return Math.abs(x) >= 1000.0 && Math.abs(z) >= 1000.0;
   }
}
