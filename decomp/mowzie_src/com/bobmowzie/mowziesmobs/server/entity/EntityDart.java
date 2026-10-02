package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;

public class EntityDart extends Arrow {
   public EntityDart(EntityType<? extends EntityDart> type, Level world) {
      super(type, world);
   }

   public EntityDart(EntityType<? extends EntityDart> type, Level worldIn, double x, double y, double z) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
      this.m_36781_((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.BLOW_GUN.attackDamage.get());
   }

   public EntityDart(EntityType<? extends EntityDart> type, Level world, LivingEntity shooter) {
      this(type, world, shooter.m_20185_(), shooter.m_20186_() + (double)shooter.m_20192_() - 0.1F, shooter.m_20189_());
      this.m_5602_(shooter);
      if (shooter instanceof Player) {
         this.f_36705_ = Pickup.ALLOWED;
      }
   }

   protected ItemStack m_7941_() {
      return new ItemStack(ItemHandler.DART);
   }

   protected void m_7761_(LivingEntity living) {
      super.m_7761_(living);
      if (this.m_37282_() instanceof Player) {
         living.m_7292_(
            new MobEffectInstance(MobEffects.f_19614_, (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.BLOW_GUN.poisonDuration.get(), 3, false, true)
         );
      } else {
         living.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 30, 1, false, true));
      }

      living.m_21317_(living.m_21234_() - 1);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_5790_(EntityHitResult raytraceResultIn) {
      if (raytraceResultIn.m_6662_() == Type.ENTITY) {
         Entity hit = raytraceResultIn.m_82443_();
         Entity shooter = this.m_37282_();
         if (hit instanceof LivingEntity living
            && (
               this.f_19853_.f_46443_
                  || shooter == hit
                  || shooter instanceof EntityUmvuthana
                     && living instanceof EntityUmvuthana
                     && ((EntityUmvuthana)shooter).isUmvuthiDevoted() == ((EntityUmvuthana)living).isUmvuthiDevoted()
                  || shooter instanceof EntityUmvuthanaFollowerToPlayer && living == ((EntityUmvuthanaFollowerToPlayer)shooter).getLeader()
            )) {
            return;
         }
      }

      super.m_5790_(raytraceResultIn);
   }
}
