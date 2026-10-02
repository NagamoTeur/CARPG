package com.hollingsworth.arsnouveau.common.event.timed;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;

public class EruptionEvent implements ITimedEvent {
   int delay;
   BlockPos origin;
   Level world;
   int start;
   int particleDelay;
   public static final String ID = "eruption";

   public EruptionEvent(Level world, BlockPos origin, int delay, int particleDelay) {
      this.world = world;
      this.origin = origin;
      this.delay = delay;
      this.start = delay;
      this.particleDelay = particleDelay;
   }

   @Override
   public void tick(boolean serverSide) {
      this.delay--;
      if (!serverSide && this.start - this.delay >= this.particleDelay && this.world.f_46441_.m_188503_(5) == 0) {
         int intensity = 50;
         ParticleColor centerColor = new ParticleColor(255, 50, 50);
         double xzOffset = 0.25;
         BlockPos pos = this.origin;

         for (int i = 0; i < intensity; i++) {
            this.world
               .m_7106_(
                  GlowParticleData.createData(centerColor),
                  (double)pos.m_123341_() + 0.5 + ParticleUtil.inRange(-xzOffset / 2.0, xzOffset / 2.0),
                  (double)(pos.m_123342_() + 1) + ParticleUtil.inRange(-0.05, 0.2),
                  (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-xzOffset / 2.0, xzOffset / 2.0),
                  0.0,
                  ParticleUtil.inRange(0.0, 0.05F),
                  0.0
               );
         }

         for (int i = 0; i < intensity; i++) {
            this.world
               .m_7106_(
                  GlowParticleData.createData(centerColor),
                  (double)pos.m_123341_() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
                  (double)(pos.m_123342_() + 1) + ParticleUtil.inRange(0.0, 0.7),
                  (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
                  0.0,
                  ParticleUtil.inRange(0.0, 0.05F),
                  0.0
               );
         }
      }

      if (serverSide && this.delay <= 0) {
         this.world
            .m_46511_(null, (double)this.origin.m_123341_(), (double)this.origin.m_123342_(), (double)this.origin.m_123343_(), 5.0F, BlockInteraction.NONE);
      }
   }

   @Override
   public boolean isExpired() {
      return this.delay <= 0;
   }

   @Override
   public CompoundTag serialize(CompoundTag tag) {
      ITimedEvent.super.serialize(tag);
      NBTUtil.storeBlockPos(tag, "pos", this.origin);
      tag.m_128405_("delay", this.delay);
      tag.m_128405_("particleDelay", this.particleDelay);
      return tag;
   }

   public static EruptionEvent get(CompoundTag tag) {
      return new EruptionEvent(ArsNouveau.proxy.getClientWorld(), NBTUtil.getBlockPos(tag, "pos"), tag.m_128451_("delay"), tag.m_128451_("particleDelay"));
   }

   @Override
   public String getID() {
      return "eruption";
   }
}
