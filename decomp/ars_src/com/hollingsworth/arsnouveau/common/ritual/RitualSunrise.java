package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.util.MathUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;

public class RitualSunrise extends AbstractRitual {
   @Override
   protected void tick() {
      ParticleUtil.spawnRitualSkyEffect(
         this, this.tile, this.rand, new ParticleColor.IntWrapper(255 + this.rand.m_188503_(1), 255 + this.rand.m_188503_(1), 25 + this.rand.m_188503_(1))
      );
      if (this.getWorld() instanceof ServerLevel world) {
         if (world.m_46468_() % 24000L >= 1000L && world.m_46468_() % 24000L < 12000L) {
            this.incrementProgress();
         } else {
            world.m_8615_(world.m_46468_() + 100L);

            for (ServerPlayer player : world.m_6907_()) {
               player.f_8906_.m_9829_(new ClientboundSetTimePacket(world.m_46467_(), world.m_46468_(), world.m_46469_().m_46207_(GameRules.f_46140_)));
            }
         }

         if (world.m_46467_() % 20L == 0L) {
            this.incrementProgress();
            if (this.getProgress() >= 18) {
               world.m_8615_(MathUtil.getNextDaysTime(world, MathUtil.DAY_TIME));

               for (ServerPlayer player : world.m_6907_()) {
                  player.f_8906_.m_9829_(new ClientboundSetTimePacket(world.m_46467_(), world.m_46468_(), world.m_46469_().m_46207_(GameRules.f_46140_)));
               }

               this.setFinished();
            }
         }
      }
   }

   @Override
   public void onStart() {
      super.onStart();
   }

   @Override
   public String getLangName() {
      return "Sunrise";
   }

   @Override
   public String getLangDescription() {
      return "Sets the time to day.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return new ParticleColor(this.rand.m_188503_(255), this.rand.m_188503_(255), this.rand.m_188503_(25));
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.SUNRISE);
   }
}
