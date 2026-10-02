package com.hollingsworth.arsnouveau.client.particle;

import com.hollingsworth.arsnouveau.api.particle.IParticleColor;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class ParticleColor implements IParticleColor, Cloneable {
   public static final ResourceLocation ID = new ResourceLocation("ars_nouveau", "constant");
   public static final ParticleColor WHITE = new ParticleColor(255, 255, 255);
   public static final ParticleColor RED = new ParticleColor(255, 50, 50);
   public static final ParticleColor GREEN = new ParticleColor(50, 255, 50);
   public static final ParticleColor BLUE = new ParticleColor(50, 50, 255);
   public static final ParticleColor YELLOW = new ParticleColor(255, 255, 0);
   public static final ParticleColor PURPLE = new ParticleColor(255, 50, 255);
   public static final ParticleColor CYAN = new ParticleColor(50, 255, 255);
   public static final ParticleColor ORANGE = new ParticleColor(255, 128, 0);
   public static final ParticleColor TO_HIGHLIGHT = RED;
   public static final ParticleColor FROM_HIGHLIGHT = CYAN;
   public static final Random random = new Random();
   private final float r;
   private final float g;
   private float b;
   private final int color;

   public ParticleColor(int r, int g, int b) {
      this.r = (float)Math.max(r, 1) / 255.0F;
      this.g = (float)Math.max(g, 1) / 255.0F;
      this.b = (float)Math.max(b, 1) / 255.0F;
      this.color = r << 16 | g << 8 | b;
   }

   public ParticleColor(double red, double green, double blue) {
      this((int)red, (int)green, (int)blue);
   }

   public static ParticleColor makeRandomColor(int r, int g, int b, RandomSource random) {
      return new ParticleColor(random.m_188503_(r), random.m_188503_(g), random.m_188503_(b));
   }

   public ParticleColor(float r, float g, float b) {
      this((int)r, (int)g, (int)b);
   }

   public ParticleColor(CompoundTag compoundTag) {
      this(compoundTag.m_128451_("r"), compoundTag.m_128451_("g"), compoundTag.m_128451_("b"));
   }

   public static ParticleColor fromInt(int color) {
      int r = color >> 16 & 0xFF;
      int g = color >> 8 & 0xFF;
      int b = color & 0xFF;
      return new ParticleColor(r, g, b);
   }

   public static ParticleColor defaultParticleColor() {
      return new ParticleColor(255, 25, 180);
   }

   @Override
   public float getRed() {
      return this.r;
   }

   @Override
   public float getGreen() {
      return this.g;
   }

   @Override
   public float getBlue() {
      return this.b;
   }

   @Override
   public int getColor() {
      return this.color;
   }

   @Override
   public ResourceLocation getRegistryName() {
      return ID;
   }

   @Override
   public CompoundTag serialize() {
      CompoundTag tag = new CompoundTag();
      ParticleColor.IntWrapper wrapper = this.toWrapper();
      tag.m_128405_("r", wrapper.r);
      tag.m_128405_("g", wrapper.g);
      tag.m_128405_("b", wrapper.b);
      tag.m_128359_("type", this.getRegistryName().toString());
      return tag;
   }

   @Override
   public String toString() {
      return this.r + "," + this.g + "," + this.b;
   }

   public ParticleColor.IntWrapper toWrapper() {
      return new ParticleColor.IntWrapper(this);
   }

   public ParticleColor nextColor(RandomSource random) {
      ParticleColor.IntWrapper wrapper = this.toWrapper();
      return new ParticleColor(random.m_188503_(wrapper.r), random.m_188503_(wrapper.g), random.m_188503_(wrapper.b));
   }

   @Override
   public ParticleColor transition(int ticks) {
      ParticleColor.IntWrapper wrapper = this.toWrapper();
      return new ParticleColor(random.nextInt(wrapper.r), random.nextInt(wrapper.g), random.nextInt(wrapper.b));
   }

   public static ParticleColor fromString(String string) {
      if (string != null && !string.isEmpty()) {
         String[] arr = string.split(",");
         return new ParticleColor(Integer.parseInt(arr[0].trim()), Integer.parseInt(arr[1].trim()), Integer.parseInt(arr[2].trim()));
      } else {
         return defaultParticleColor();
      }
   }

   @Deprecated(
      forRemoval = true
   )
   public static ParticleColor deserialize(CompoundTag tag) {
      return tag != null && !tag.m_128456_() ? new ParticleColor(tag.m_128451_("r"), tag.m_128451_("g"), tag.m_128451_("b")) : defaultParticleColor();
   }

   public ParticleColor clone() {
      try {
         return (ParticleColor)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }

   @Deprecated(
      forRemoval = true
   )
   public static class IntWrapper implements Cloneable {
      public int r;
      public int g;
      public int b;

      public IntWrapper(int r, int g, int b) {
         this.r = Math.max(1, r);
         this.g = Math.max(1, g);
         this.b = Math.max(1, b);
      }

      public IntWrapper(ParticleColor color) {
         this.r = Math.max(1, (int)((double)color.getRed() * 255.0));
         this.g = Math.max(1, (int)((double)color.getGreen() * 255.0));
         this.b = Math.max(1, (int)((double)color.getBlue() * 255.0));
      }

      public ParticleColor toParticleColor() {
         return new ParticleColor(this.r, this.g, this.b);
      }

      public ParticleColor.IntWrapper clone() {
         try {
            return (ParticleColor.IntWrapper)super.clone();
         } catch (CloneNotSupportedException var2) {
            throw new AssertionError();
         }
      }
   }
}
