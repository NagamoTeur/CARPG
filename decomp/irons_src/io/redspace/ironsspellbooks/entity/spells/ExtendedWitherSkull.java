package io.redspace.ironsspellbooks.entity.spells;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class ExtendedWitherSkull extends WitherSkull implements AntiMagicSusceptible {
   protected float damage;

   public ExtendedWitherSkull(EntityType<? extends WitherSkull> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public ExtendedWitherSkull(LivingEntity shooter, Level level, float speed, float damage) {
      super((EntityType)EntityRegistry.WITHER_SKULL_PROJECTILE.get(), level);
      this.m_5602_(shooter);
      Vec3 power = shooter.m_20154_().m_82541_().m_82490_((double)speed);
      this.f_36813_ = power.f_82479_;
      this.f_36814_ = power.f_82480_;
      this.f_36815_ = power.f_82481_;
      this.damage = damage;
   }

   protected void m_6532_(HitResult hitResult) {
      if (!this.f_19853_.f_46443_) {
         float explosionRadius = 2.0F;

         for (Entity entity : this.f_19853_.m_45933_(this, this.m_20191_().m_82400_((double)explosionRadius))) {
            double distance = entity.m_20238_(hitResult.m_82450_());
            if (distance < (double)(explosionRadius * explosionRadius) && this.m_5603_(entity)) {
               float damage = (float)((double)this.damage * (1.0 - distance / (double)(explosionRadius * explosionRadius)));
               AbstractSpell spell = (AbstractSpell)SpellRegistry.WITHER_SKULL_SPELL.get();
               DamageSources.applyDamage(entity, damage, spell.getDamageSource(this, this.m_37282_()));
            }
         }

         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0F, false, BlockInteraction.NONE);
         this.m_146870_();
      }
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @Override
   public void onAntiMagic(MagicData playerMagicData) {
      this.m_146870_();
   }
}
