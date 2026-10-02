package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RitualOvergrowth extends AbstractRitual {
   @Override
   protected void tick() {
      Level world = this.getWorld();
      BlockPos pos = this.getPos();
      if (this.getWorld().f_46443_) {
         ParticleUtil.spawnRitualAreaEffect(this.getPos(), this.getWorld(), this.rand, this.getCenterColor(), 5);
      } else {
         if (this.getWorld().m_46467_() % 200L != 0L) {
            return;
         }

         if (this.isAnimalGrowth()) {
            List<AgeableMob> animals = this.getWorld().m_45976_(AgeableMob.class, new AABB(this.getPos()).m_82400_(5.0));
            boolean didWorkOnce = false;

            for (AgeableMob a : animals) {
               if (a.m_6162_()) {
                  a.m_146740_(500, true);
                  didWorkOnce = true;
               }
            }

            if (didWorkOnce) {
               this.setNeedsSource(true);
            }
         } else {
            int range = 5;
            boolean didWorkOnce = false;

            for (BlockPos b : BlockPos.m_121940_(pos.m_7918_(range, -1, range), pos.m_7918_(-range, 1, -range))) {
               if (this.rand.m_188503_(25) == 0
                  && BoneMealItem.applyBonemeal(new ItemStack(Items.f_42499_), world, b, ANFakePlayer.getPlayer((ServerLevel)world))) {
                  didWorkOnce = true;
               }
            }

            if (didWorkOnce) {
               this.setNeedsSource(true);
            }
         }
      }
   }

   public boolean isAnimalGrowth() {
      return this.didConsumeItem(Items.f_42262_);
   }

   @Override
   public int getSourceCost() {
      return 500;
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return this.getConsumedItems().isEmpty() && stack.m_41720_() == Items.f_42262_;
   }

   @Override
   public String getLangName() {
      return "Overgrowth";
   }

   @Override
   public String getLangDescription() {
      return "Occasionally bone meals blocks in the area around it. This ritual requires source to operate. If augmented with a Bone Block, this ritual will instead force baby animals to grow faster.";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.OVERGROWTH);
   }

   @Override
   public ParticleColor getCenterColor() {
      return ParticleColor.makeRandomColor(20, 255, 20, this.rand);
   }
}
