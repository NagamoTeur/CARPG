package net.thirdlife.iterrpg.procedures;

public class SorrowSpireConditionProcedure {
   public static boolean execute(double y) {
      boolean check = false;
      return y <= 70.0 && y >= 34.0;
   }
}
