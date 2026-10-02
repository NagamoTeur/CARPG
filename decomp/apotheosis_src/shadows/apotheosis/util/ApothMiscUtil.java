package shadows.apotheosis.util;

import shadows.placebo.util.EnchantmentUtils;

public class ApothMiscUtil {
   public static int getExpCostForSlot(int level, int slot) {
      int cost = 0;

      for (int i = 0; i <= slot; i++) {
         cost += EnchantmentUtils.getExperienceForLevel(level - i);
      }

      return cost - 1;
   }

   public static int[] doubleUpGradient(int[] data) {
      int[] out = new int[data.length * 2];
      System.arraycopy(data, 0, out, 0, data.length);

      for (int i = data.length - 1; i >= 0; i--) {
         out[data.length * 2 - 1 - i] = data[i];
      }

      return out;
   }
}
