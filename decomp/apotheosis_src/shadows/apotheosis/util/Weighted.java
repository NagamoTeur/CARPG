package shadows.apotheosis.util;

import com.google.gson.annotations.Expose;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public class Weighted implements WeightedEntry {
   @Expose(
      deserialize = false
   )
   private Weight _weight;
   public final int weight;

   public Weighted(int pWeight) {
      this.weight = pWeight;
   }

   public Weight m_142631_() {
      if (this._weight == null) {
         this._weight = Weight.m_146282_(this.weight);
      }

      return this._weight;
   }
}
