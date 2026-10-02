package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public abstract class RangeEffectRitual extends RangeRitual {
   public abstract MobEffect getEffect();

   public abstract int getRange();

   public abstract int getDuration();

   public boolean shouldApply(ServerPlayer player) {
      return !player.f_19853_.f_46443_ && !this.needsSourceNow() && BlockUtil.distanceFrom(this.getPos(), player.m_20183_()) <= (double)this.getRange();
   }

   public boolean attemptRefresh(ServerPlayer player) {
      if (!this.shouldApply(player)) {
         return false;
      } else if (this.applyEffect(player)) {
         this.setNeedsSource(true);
         return true;
      } else {
         return false;
      }
   }

   public boolean applyEffect(ServerPlayer player) {
      player.m_7292_(new MobEffectInstance(this.getEffect(), this.getDuration()));
      return true;
   }
}
