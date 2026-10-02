package io.redspace.ironsspellbooks.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.api.util.Utils;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ShockwaveParticle extends TextureSheetParticle {
   private static final Vector3f ROTATION_VECTOR = (Vector3f)Util.m_137469_(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::m_122278_);
   private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);
   private static final float DEGREES_90 = (float) (Math.PI / 2);
   private final float targetSize;
   private final boolean isFullbright;
   private final Optional<ParticleOptions> trailParticle;
   static final int MAX_PARTICLES = 30;

   ShockwaveParticle(ClientLevel pLevel, double pX, double pY, double pZ, double xd, double yd, double zd, ShockwaveParticleOptions options) {
      super(pLevel, pX, pY, pZ, 0.0, 0.0, 0.0);
      this.f_107215_ = xd;
      this.f_107216_ = yd;
      this.f_107217_ = zd;
      this.targetSize = options.m_175813_();
      this.f_107663_ = 0.0F;
      this.f_107225_ = this.targetSize < 0.0F ? (int)(this.targetSize * -20.0F) : (int)((double)Math.abs(this.targetSize) * 2.5);
      this.f_107226_ = 0.1F;
      float f = this.f_107223_.m_188501_() * 0.14F + 0.85F;
      this.f_107227_ = options.color().m_122239_() * f;
      this.f_107228_ = options.color().m_122260_() * f;
      this.f_107229_ = options.color().m_122269_() * f;
      this.f_172258_ = 1.0F;
      this.isFullbright = options.isFullbright();
      this.trailParticle = options.trailParticle();
   }

   public float m_5902_(float partialTick) {
      float f = (partialTick + (float)this.f_107224_) / (float)this.f_107225_;
      return this.targetSize < 0.0F
         ? Mth.m_14179_((1.0F - f) * (1.0F - f), 0.0F, -this.targetSize)
         : Mth.m_14179_(1.0F - (1.0F - f) * (1.0F - f), 0.0F, this.targetSize);
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107216_ *= 0.85F;
         this.f_107215_ *= 0.94F;
         this.f_107217_ *= 0.94F;
         if (this.trailParticle.isPresent()) {
            float radius = this.m_5902_(1.0F);
            float circumference = radius * 2.0F * (float) Math.PI;
            int particles = (int)Mth.m_14036_(circumference / 5.0F, 5.0F, 30.0F);
            float degreesPerParticle = 360.0F / (float)particles;

            for (int i = 0; i < particles; i++) {
               float f = degreesPerParticle * (float)i + (float)Utils.random.m_188503_((int)degreesPerParticle);
               float x = Mth.m_14089_(f * (float) (Math.PI / 180.0)) * radius;
               float z = Mth.m_14031_(f * (float) (Math.PI / 180.0)) * radius;
               this.f_107208_.m_7106_(this.trailParticle.get(), this.f_107212_ + (double)x, this.f_107213_, this.f_107214_ + (double)z, 0.0, 0.05, 0.0);
            }
         }
      }
   }

   public boolean shouldCull() {
      return false;
   }

   public void m_5744_(VertexConsumer buffer, Camera camera, float partialticks) {
      this.f_107230_ = 1.0F - Mth.m_14036_(((float)this.f_107224_ + partialticks) / (float)this.f_107225_, 0.0F, 1.0F);
      this.renderRotatedParticle(buffer, camera, partialticks, p_234005_ -> {
         p_234005_.m_80148_(Vector3f.f_122225_.m_122270_(0.0F));
         p_234005_.m_80148_(Vector3f.f_122223_.m_122270_((float) (-Math.PI / 2)));
      });
      this.renderRotatedParticle(buffer, camera, partialticks, p_234000_ -> {
         p_234000_.m_80148_(Vector3f.f_122225_.m_122270_((float) -Math.PI));
         p_234000_.m_80148_(Vector3f.f_122223_.m_122270_((float) (Math.PI / 2)));
      });
   }

   private void renderRotatedParticle(VertexConsumer pConsumer, Camera camera, float partialTick, Consumer<Quaternion> pQuaternion) {
      Vec3 vec3 = camera.m_90583_();
      float f = (float)(Mth.m_14139_((double)partialTick, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTick, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTick, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Quaternion quaternion = new Quaternion(ROTATION_VECTOR, 0.0F, true);
      pQuaternion.accept(quaternion);
      TRANSFORM_VECTOR.m_122251_(quaternion);
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f3 = this.m_5902_(partialTick);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         vector3f.m_122251_(quaternion);
         vector3f.m_122261_(f3);
         vector3f.m_122272_(f, f1, f2);
      }

      int j = this.m_6355_(partialTick);
      this.makeCornerVertex(pConsumer, avector3f[0], this.m_5952_(), this.m_5950_(), j);
      this.makeCornerVertex(pConsumer, avector3f[1], this.m_5952_(), this.m_5951_(), j);
      this.makeCornerVertex(pConsumer, avector3f[2], this.m_5970_(), this.m_5951_(), j);
      this.makeCornerVertex(pConsumer, avector3f[3], this.m_5970_(), this.m_5950_(), j);
   }

   private void makeCornerVertex(VertexConsumer pConsumer, Vector3f pVec3f, float p_233996_, float p_233997_, int p_233998_) {
      pConsumer.m_5483_((double)pVec3f.m_122239_(), (double)(pVec3f.m_122260_() + 0.08F), (double)pVec3f.m_122269_())
         .m_7421_(p_233996_, p_233997_)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(p_233998_)
         .m_5752_();
   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   protected int m_6355_(float pPartialTick) {
      if (this.isFullbright) {
         return 15728880;
      } else {
         BlockPos blockpos = new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_).m_7494_();
         return this.f_107208_.m_46805_(blockpos) ? LevelRenderer.m_109541_(this.f_107208_, blockpos) : 0;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<ShockwaveParticleOptions> {
      private final SpriteSet sprite;

      public Provider(SpriteSet pSprite) {
         this.sprite = pSprite;
      }

      public Particle createParticle(
         @NotNull ShockwaveParticleOptions options,
         @NotNull ClientLevel pLevel,
         double pX,
         double pY,
         double pZ,
         double pXSpeed,
         double pYSpeed,
         double pZSpeed
      ) {
         ShockwaveParticle shriekparticle = new ShockwaveParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, options);
         shriekparticle.m_108335_(this.sprite);
         shriekparticle.m_107271_(1.0F);
         return shriekparticle;
      }
   }
}
