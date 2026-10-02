package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

public class RitualPillagerRaid extends AbstractRitual {
   @Override
   protected void tick() {
      ParticleUtil.spawnRitualSkyEffect(this, this.tile, this.rand, this.getCenterColor().toWrapper());
      if (this.getWorld().m_46467_() % 20L == 0L && !this.getWorld().f_46443_) {
         this.incrementProgress();
         if (this.getProgress() >= 18) {
            ServerLevel world = (ServerLevel)this.getWorld();
            List<ServerPlayer> players = world.m_45976_(ServerPlayer.class, new AABB(this.getPos()).m_82400_(5.0));
            if (players.size() > 0) {
               Raid raid = world.m_8905_().m_37963_(players.get(0));
               if (raid != null) {
                  this.setFinished();
                  if (this.didConsumeItem(Items.f_42616_)) {
                     raid.f_37686_ = 7;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return this.getWorld().m_46791_() != Difficulty.HARD
         && this.getContext().consumedItems.isEmpty()
         && stack.m_204117_(net.minecraftforge.common.Tags.Items.GEMS_EMERALD);
   }

   @Override
   public ParticleColor getCenterColor() {
      return ParticleColor.makeRandomColor(20, 250, 20, this.rand);
   }

   @Override
   public ParticleColor getOuterColor() {
      return super.getOuterColor();
   }

   @Override
   public void onItemConsumed(ItemStack stack) {
      super.onItemConsumed(stack);
   }

   @Override
   public String getLangName() {
      return "Challenge";
   }

   @Override
   public String getLangDescription() {
      return "Summons an illager raid when used inside a village. An Emerald may be used to increase the difficulty of the raid to the maximum amount, making Totems of the Undying accessible on easier difficulties. Augmenting has no effect on Hard difficulty.";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.CHALLENGE);
   }
}
