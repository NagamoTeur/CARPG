package net.mindoth.dreadsteel.entity;

import net.mindoth.dreadsteel.config.DreadsteelCommonConfig;
import net.mindoth.dreadsteel.registries.DreadsteelEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class EntityScytheProjectileBronze extends AbstractArrow {
   public EntityScytheProjectileBronze(EntityType<? extends AbstractArrow> type, Level LevelIn) {
      super(type, LevelIn);
      this.m_36781_((double)((Integer)DreadsteelCommonConfig.SCYTHE_DAMAGE.get() + 1));
   }

   public EntityScytheProjectileBronze(EntityType<? extends AbstractArrow> type, Level LevelIn, double x, double y, double z, float r, float g, float b) {
      this(type, LevelIn);
      this.m_6034_(x, y, z);
      this.m_36781_((double)((Integer)DreadsteelCommonConfig.SCYTHE_DAMAGE.get() + 1));
   }

   public EntityScytheProjectileBronze(EntityType<? extends AbstractArrow> type, Level LevelIn, LivingEntity shooter, double dmg) {
      super(type, shooter, LevelIn);
      this.m_36781_(dmg);
   }

   public EntityScytheProjectileBronze(SpawnEntity spawnEntity, Level LevelIn) {
      this((EntityType<? extends AbstractArrow>)DreadsteelEntities.SCYTHE_PROJECTILE_BRONZE.get(), LevelIn);
   }

   public boolean m_20069_() {
      return false;
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ == 80) {
         this.spawnParticles();
      } else if (this.f_19797_ > 80) {
         this.m_5496_(SoundEvents.f_12052_, 1.0F, 1.0F);
         this.m_146870_();
      }
   }

   public boolean m_20068_() {
      return true;
   }

   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }

   protected void m_5790_(EntityHitResult result) {
      Entity entity = result.m_82443_();
      if (entity instanceof LivingEntity && entity != this.m_37282_()) {
         entity.m_6469_(DamageSource.m_19367_(this, this.m_37282_()).m_19380_().m_19382_(), (float)this.m_36789_());
      }
   }

   protected void m_8060_(BlockHitResult result) {
      if (!this.f_19853_.f_46443_) {
         this.spawnParticles();
         this.m_5496_(SoundEvents.f_12052_, 1.0F, 1.0F);
         this.m_146870_();
      }
   }

   private void spawnParticles() {
      if (!this.f_19853_.f_46443_) {
         Vec3 center = this.m_20191_().m_82399_();
         ServerLevel level = (ServerLevel)this.f_19853_;

         for (int i = 0; i < 8; i++) {
            level.m_8767_(ParticleTypes.f_123744_, center.f_82479_, center.f_82480_, center.f_82481_, 1, 0.0, 0.0, 0.0, 1.0);
         }
      }
   }

   protected float m_6882_() {
      return 0.0F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
