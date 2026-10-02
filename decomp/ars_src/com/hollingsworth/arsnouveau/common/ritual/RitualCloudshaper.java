package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RitualCloudshaper extends AbstractRitual {
   public static final IntProvider RAIN_DELAY = UniformInt.m_146622_(12000, 180000);
   public static final IntProvider RAIN_DURATION = UniformInt.m_146622_(12000, 24000);
   public static final IntProvider THUNDER_DURATION = UniformInt.m_146622_(3600, 15600);

   @Override
   protected void tick() {
      ParticleUtil.spawnRitualSkyEffect(this, this.tile, this.rand, this.getCenterColor().toWrapper());
      if (this.getWorld().m_46467_() % 20L == 0L && !this.getWorld().f_46443_) {
         this.incrementProgress();
         if (this.getProgress() >= 18) {
            ServerLevel world = (ServerLevel)this.getWorld();
            if (!this.isStorm() && !this.isRain()) {
               world.m_8606_(RAIN_DELAY.m_214085_(world.m_213780_()), 0, false, false);
               this.setFinished();
            }

            if (this.isStorm()) {
               world.m_8606_(0, THUNDER_DURATION.m_214085_(world.m_213780_()), true, true);
               this.setFinished();
            }

            if (this.isRain()) {
               world.m_8606_(0, RAIN_DURATION.m_214085_(world.m_213780_()), true, false);
               this.setFinished();
            }
         }
      }
   }

   @Override
   public void onStart() {
      super.onStart();
   }

   public boolean isStorm() {
      return this.didConsumeItem(Items.f_41854_);
   }

   public boolean isRain() {
      return this.didConsumeItem(Items.f_42403_);
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return this.getConsumedItems().isEmpty() && (stack.m_41720_() == Items.f_41854_ || stack.m_41720_() == Items.f_42403_);
   }

   @Override
   public String getLangName() {
      return "Cloudshaping";
   }

   @Override
   public String getLangDescription() {
      return "This ritual can change the weather at a moments notice. By default, this ritual will set the weather to clear. Augmenting with Gunpowder will cause it to rain, while a Lapis Block will cause it to storm.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return !this.isRain() && !this.isStorm()
         ? new ParticleColor(this.rand.m_188503_(255), this.rand.m_188503_(255), this.rand.m_188503_(255))
         : new ParticleColor(this.rand.m_188503_(100), this.rand.m_188503_(100), this.rand.m_188503_(255));
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.CLOUDSHAPER);
   }
}
