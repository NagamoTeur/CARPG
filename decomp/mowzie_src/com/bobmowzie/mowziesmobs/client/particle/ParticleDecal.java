package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleDecal extends AdvancedParticleBase {
   protected ParticleDecal(
      ClientLevel worldIn,
      double xCoordIn,
      double yCoordIn,
      double zCoordIn,
      double motionX,
      double motionY,
      double motionZ,
      ParticleRotation rotation,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, motionX, motionY, motionZ, rotation, scale, r, g, b, a, drag, duration, emissive, canCollide, components);
   }

   @Override
   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      this.f_107230_ = this.prevAlpha + (this.f_107230_ - this.prevAlpha) * partialTicks;
      if ((double)this.f_107230_ < 0.01) {
         this.f_107230_ = 0.01F;
      }

      this.f_107227_ = this.prevRed + (this.red - this.prevRed) * partialTicks;
      this.f_107228_ = this.prevGreen + (this.green - this.prevGreen) * partialTicks;
      this.f_107229_ = this.prevBlue + (this.blue - this.prevBlue) * partialTicks;
      this.particleScale = this.prevScale + (this.scale - this.prevScale) * partialTicks;

      for (ParticleComponent component : this.components) {
         component.preRender(this, partialTicks);
      }

      if (this.doRender) {
         float decalRot = 0.0F;
         if (this.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
            float rotY = eulerRot.prevYaw + (eulerRot.yaw - eulerRot.prevYaw) * partialTicks;
            decalRot = rotY;
         }

         float u0 = this.m_5970_();
         float u1 = this.m_5952_();
         float v0 = this.m_5951_();
         float v1 = this.m_5950_();
         int lightColor = this.m_6355_(partialTicks);
         int spriteSize = 8;
         int bufferSize = 32;
         float spriteScale = (float)spriteSize / (float)bufferSize;
         Vec3 minCorner = new Vec3((double)(-this.particleScale), (double)(-this.particleScale), (double)(-this.particleScale))
            .m_82524_(decalRot)
            .m_82520_(this.f_107212_, this.f_107213_, this.f_107214_);
         Vec3 maxCorner = new Vec3((double)this.particleScale, (double)this.particleScale, (double)this.particleScale)
            .m_82524_(decalRot)
            .m_82520_(this.f_107212_, this.f_107213_, this.f_107214_);

         for (BlockPos blockpos : BlockPos.m_121940_(new BlockPos(minCorner), new BlockPos(maxCorner))) {
            renderBlockDecal(
               buffer,
               renderInfo,
               this.f_107208_,
               blockpos,
               this.f_107212_,
               this.f_107213_,
               this.f_107214_,
               u0,
               u1,
               v0,
               v1,
               this.particleScale,
               spriteScale,
               this.f_107230_,
               decalRot,
               this.f_107227_,
               this.f_107228_,
               this.f_107229_,
               lightColor
            );
         }

         for (ParticleComponent component : this.components) {
            component.postRender(this, buffer, renderInfo, partialTicks, lightColor);
         }
      }
   }

   private static Vec2 rotateVec2(Vec2 v, float angle) {
      return new Vec2(
         v.f_82470_ * (float)Math.cos((double)angle) - v.f_82471_ * (float)Math.sin((double)angle),
         v.f_82470_ * (float)Math.sin((double)angle) + v.f_82471_ * (float)Math.cos((double)angle)
      );
   }

   private static void renderBlockDecal(
      VertexConsumer buffer,
      Camera renderInfo,
      LevelReader level,
      BlockPos blockPos,
      double x,
      double y,
      double z,
      float u0,
      float u1,
      float v0,
      float v1,
      float scale,
      float spriteScale,
      float alpha,
      float rotation,
      float r,
      float g,
      float b,
      int lightColor
   ) {
      Vec2 center = new Vec2((float)x, (float)z);
      BlockPos blockpos = blockPos.m_7495_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (blockstate.m_60799_() != RenderShape.INVISIBLE && blockstate.m_60838_(level, blockpos)) {
         VoxelShape voxelshape = blockstate.m_60808_(level, blockPos.m_7495_());
         if (!voxelshape.m_83281_()) {
            float f = alpha;
            if (alpha >= 0.0F) {
               if (alpha > 1.0F) {
                  f = 1.0F;
               }

               double rad2 = Math.sqrt(2.0);
               double minX = x - (double)(scale * spriteScale) * rad2;
               double minZ = z - (double)(scale * spriteScale) * rad2;
               double maxX = x + (double)(scale * spriteScale) * rad2;
               double maxZ = z + (double)(scale * spriteScale) * rad2;
               AABB aabb = voxelshape.m_83215_();
               float d0 = (float)blockPos.m_123341_() + (float)aabb.f_82288_;
               float d1 = (float)blockPos.m_123341_() + (float)aabb.f_82291_;
               float d2 = (float)blockPos.m_123342_() + (float)aabb.f_82289_ + 0.015625F;
               float d3 = (float)blockPos.m_123343_() + (float)aabb.f_82290_;
               float d4 = (float)blockPos.m_123343_() + (float)aabb.f_82293_;
               if ((double)d0 < minX) {
                  d0 = (float)minX;
               }

               if ((double)d1 > maxX) {
                  d1 = (float)maxX;
               }

               if ((double)d3 < minZ) {
                  d3 = (float)minZ;
               }

               if ((double)d4 > maxZ) {
                  d4 = (float)maxZ;
               }

               Vec2[] corners = new Vec2[]{new Vec2(d0, d3), new Vec2(d1, d3), new Vec2(d1, d4), new Vec2(d0, d4)};

               for (Vec2 corner : corners) {
                  Vec2 cornerRelative = rotateVec2(corner.m_165910_(center.m_165913_()), -rotation);
                  Vec2 uv = new Vec2(
                     (cornerRelative.f_82470_ / (2.0F * scale) + 0.5F) * (u1 - u0) + u0, (cornerRelative.f_82471_ / (2.0F * scale) + 0.5F) * (v1 - v0) + v0
                  );
                  decalVertex(buffer, renderInfo, f, corner.f_82470_, d2, corner.f_82471_, uv.f_82470_, uv.f_82471_, r, g, b, lightColor);
               }
            }
         }
      }
   }

   private static void decalVertex(
      VertexConsumer buffer, Camera renderInfo, float alpha, float x, float y, float z, float u, float v, float r, float g, float b, int lightColor
   ) {
      Vec3 Vector3d = renderInfo.m_90583_();
      buffer.m_5483_((double)x - Vector3d.m_7096_(), (double)y - Vector3d.m_7098_(), (double)z - Vector3d.m_7094_())
         .m_7421_(u, v)
         .m_85950_(r, g, b, alpha)
         .m_85969_(lightColor)
         .m_5752_();
   }

   public static void spawnDecal(
      Level world,
      ParticleType<AdvancedParticleData> particle,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      ParticleRotation rotation,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      world.m_7106_(
         new AdvancedParticleData(particle, rotation, scale, r, g, b, a, drag, duration, emissive, canCollide, components), x, y, z, motionX, motionY, motionZ
      );
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<AdvancedParticleData> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         AdvancedParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParticleDecal particle = new ParticleDecal(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            typeIn.getRotation(),
            typeIn.getScale(),
            typeIn.getRed(),
            typeIn.getGreen(),
            typeIn.getBlue(),
            typeIn.getAlpha(),
            typeIn.getAirDrag(),
            typeIn.getDuration(),
            typeIn.isEmissive(),
            typeIn.getCanCollide(),
            typeIn.getComponents()
         );
         particle.m_107253_((float)typeIn.getRed(), (float)typeIn.getGreen(), (float)typeIn.getBlue());
         particle.m_108335_(this.spriteSet);
         return particle;
      }
   }
}
