package com.aizistral.enigmaticlegacy.client.fx;

import com.aizistral.enigmaticlegacy.entities.PermanentItemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PermanentItemPickupParticle extends Particle {
   private final RenderBuffers renderTypeBuffers;
   private final Entity item;
   private final Entity target;
   private int particleAge;
   private final EntityRenderDispatcher renderManager;

   public PermanentItemPickupParticle(EntityRenderDispatcher entityRenderManager, RenderBuffers buffers, ClientLevel world, Entity item, Entity target) {
      this(entityRenderManager, buffers, world, item, target, item.m_20184_());
   }

   private PermanentItemPickupParticle(
      EntityRenderDispatcher entityRenderManager, RenderBuffers buffers, ClientLevel world, Entity item, Entity target, Vec3 motionVector
   ) {
      super(world, item.m_20185_(), item.m_20186_(), item.m_20189_(), motionVector.f_82479_, motionVector.f_82480_, motionVector.f_82481_);
      this.renderTypeBuffers = buffers;
      this.item = this.getSafeCopy(item);
      this.target = target;
      this.renderManager = entityRenderManager;
   }

   private Entity getSafeCopy(Entity entity) {
      return (Entity)(!(entity instanceof PermanentItemEntity) ? entity : ((PermanentItemEntity)entity).copy());
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      try {
         float f = ((float)this.particleAge + partialTicks) / 3.0F;
         f *= f;
         double d0 = Mth.m_14139_((double)partialTicks, this.target.f_19790_, this.target.m_20185_());
         double d1 = Mth.m_14139_((double)partialTicks, this.target.f_19791_, this.target.m_20186_()) + 0.5;
         double d2 = Mth.m_14139_((double)partialTicks, this.target.f_19792_, this.target.m_20189_());
         double d3 = Mth.m_14139_((double)f, this.item.m_20185_(), d0);
         double d4 = Mth.m_14139_((double)f, this.item.m_20186_(), d1);
         double d5 = Mth.m_14139_((double)f, this.item.m_20189_(), d2);
         BufferSource ibuffer = this.renderTypeBuffers.m_110104_();
         Vec3 vector3d = renderInfo.m_90583_();
         this.renderManager
            .m_114384_(
               this.item,
               d3 - vector3d.m_7096_(),
               d4 - vector3d.m_7098_(),
               d5 - vector3d.m_7094_(),
               this.item.m_146908_(),
               partialTicks,
               new PoseStack(),
               ibuffer,
               this.renderManager.m_114394_(this.item, partialTicks)
            );
         ibuffer.m_109911_();
      } catch (Throwable var19) {
      }
   }

   public void m_5989_() {
      this.particleAge++;
      if (this.particleAge == 3) {
         this.m_107274_();
      }
   }
}
