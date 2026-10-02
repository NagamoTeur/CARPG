package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class Poison_Dart_Entity extends AbstractArrow {
   public Poison_Dart_Entity(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public Poison_Dart_Entity(EntityType type, double x, double y, double z, Level worldIn) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
   }

   public Poison_Dart_Entity(Level worldIn, LivingEntity shooter) {
      this((EntityType)ModEntities.POISON_DART.get(), shooter.m_20185_(), shooter.m_20188_() - 0.1F, shooter.m_20189_(), worldIn);
      this.m_5602_(shooter);
      if (shooter instanceof Player) {
         this.f_36705_ = Pickup.ALLOWED;
      }
   }

   public Poison_Dart_Entity(SpawnEntity spawnEntity, Level world) {
      this((EntityType)ModEntities.POISON_DART.get(), world);
   }

   protected void m_8060_(BlockHitResult result) {
      super.m_8060_(result);
      this.m_146870_();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_7761_(LivingEntity p_36873_) {
      super.m_7761_(p_36873_);
      Entity entity = this.m_150173_();
      p_36873_.m_147207_(new MobEffectInstance(MobEffects.f_19614_, 100, 1), entity);
   }

   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }
}
