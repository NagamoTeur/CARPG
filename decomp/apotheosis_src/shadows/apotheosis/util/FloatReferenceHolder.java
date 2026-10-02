package shadows.apotheosis.util;

import net.minecraft.util.Mth;
import net.minecraft.world.inventory.SimpleContainerData;

public class FloatReferenceHolder {
   boolean updating = false;
   float internal = 0.0F;
   final float min;
   final float max;
   SimpleContainerData array = new SimpleContainerData(3) {
      public void m_8050_(int index, int value) {
         super.m_8050_(index, value);
         if (!FloatReferenceHolder.this.updating) {
            FloatReferenceHolder.this.updateFromArray();
         }
      }
   };

   public FloatReferenceHolder(float def, float min, float max) {
      this.set(def);
      this.min = min;
      this.max = max;
   }

   public SimpleContainerData getArray() {
      return this.array;
   }

   public float get() {
      return this.internal;
   }

   public void set(float f) {
      f = Mth.m_14036_(f, this.min, this.max);
      this.internal = f;
      this.updating = true;
      this.array.m_8050_(0, (int)f);
      this.array.m_8050_(1, (int)(f * 10.0F) % 10);
      this.array.m_8050_(2, (int)(f * 100.0F) % 10);
      this.updating = false;
   }

   private void updateFromArray() {
      this.internal = (float)this.array.m_6413_(0) + (float)this.array.m_6413_(1) / 10.0F + (float)this.array.m_6413_(2) / 100.0F;
   }

   public float getMax() {
      return this.max;
   }

   public float getMin() {
      return this.min;
   }
}
