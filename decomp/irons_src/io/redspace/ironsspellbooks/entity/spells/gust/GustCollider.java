package io.redspace.ironsspellbooks.entity.spells.gust;

import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractConeProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class GustCollider extends AbstractConeProjectile {
   public float strength;
   public float range;
   public int amplifier;

   public GustCollider(Level level, LivingEntity owner) {
      this((EntityType<GustCollider>)EntityRegistry.GUST_COLLIDER.get(), level);
      this.m_5602_(owner);
      this.m_19915_(owner.m_146908_(), owner.m_146909_());
   }

   public GustCollider(EntityType<GustCollider> gustColliderEntityType, Level level) {
      super(gustColliderEntityType, level);
   }

   @Override
   public void spawnParticles() {
      if (this.f_19853_.f_46443_ && this.f_19797_ <= 2) {
         Vec3 rotation = this.m_20154_().m_82541_();
         Vec3 pos = this.m_20182_().m_82549_(rotation.m_82490_(1.6));
         double x = pos.f_82479_;
         double y = pos.f_82480_;
         double z = pos.f_82481_;
         double speed = this.f_19796_.m_188500_() * 0.4 + 0.45;

         for (int i = 0; i < 5; i++) {
            double offset = 0.25;
            double ox = Math.random() * 2.0 * offset - offset;
            double oy = Math.random() * 2.0 * offset - offset;
            double oz = Math.random() * 2.0 * offset - offset;
            double angularness = 0.8;
            Vec3 randomVec = new Vec3(
                  Math.random() * 2.0 * angularness - angularness,
                  Math.random() * 2.0 * angularness - angularness,
                  Math.random() * 2.0 * angularness - angularness
               )
               .m_82541_();
            Vec3 result = rotation.m_82490_(3.0).m_82549_(randomVec).m_82541_().m_82490_(speed);
            this.f_19853_.m_7106_(ParticleTypes.f_123759_, x + ox, y + oy, z + oz, result.f_82479_, result.f_82480_, result.f_82481_);
         }
      }
   }

   @Override
   protected void m_5790_(EntityHitResult entityHitResult) {
      Entity entity = this.m_37282_();
      if (entity != null
         && entityHitResult.m_82443_() instanceof LivingEntity target
         && target.m_20280_(entity) < (double)(this.range * this.range)
         && !DamageSources.isFriendlyFireBetween(entity, target)) {
         knockback(
            target, (double)this.strength, entity.m_20185_() - target.m_20185_(), entity.m_20186_() - target.m_20186_(), entity.m_20189_() - target.m_20189_()
         );
         target.f_19864_ = true;
         target.m_7292_(new MobEffectInstance((MobEffect)MobEffectRegistry.AIRBORNE.get(), 60, this.amplifier));
      }
   }

   private static void knockback(LivingEntity target, double pStrength, double x, double y, double z) {
      LivingKnockBackEvent event = ForgeHooks.onLivingKnockBack(target, (float)pStrength, x, z);
      if (!event.isCanceled()) {
         pStrength = (double)event.getStrength();
         x = event.getRatioX();
         z = event.getRatioZ();
         pStrength *= 1.0 - target.m_21133_(Attributes.f_22278_) * 0.5;
         if (!(pStrength <= 0.0)) {
            target.f_19812_ = true;
            Vec3 vec3 = target.m_20184_();
            Vec3 vec31 = new Vec3(x, y, z).m_82541_().m_82490_(pStrength);
            target.m_20334_(
               vec3.f_82479_ / 2.0 - vec31.f_82479_,
               target.m_20096_() ? Math.min(0.4, vec3.f_82480_ / 2.0 + pStrength) : vec3.f_82480_,
               vec3.f_82481_ / 2.0 - vec31.f_82481_
            );
         }
      }
   }

   @Override
   public void m_8119_() {
      if (this.f_19797_ > 8) {
         this.m_146870_();
      } else {
         super.m_8119_();
      }
   }

   @Nullable
   public Entity m_37282_() {
      return this.f_19797_ >= 1 ? null : super.m_37282_();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
