package com.hollingsworth.arsnouveau.api.util;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class ANExplosion extends Explosion {
   public double amps;
   public double baseDamage;
   public double ampDamageScalar;

   public ANExplosion(
      Level p_i45752_1_,
      @Nullable Entity p_i45752_2_,
      double p_i45752_3_,
      double p_i45752_5_,
      double p_i45752_7_,
      float p_i45752_9_,
      List<BlockPos> p_i45752_10_
   ) {
      super(p_i45752_1_, p_i45752_2_, p_i45752_3_, p_i45752_5_, p_i45752_7_, p_i45752_9_, p_i45752_10_);
   }

   public ANExplosion(
      Level p_i50006_1_,
      @Nullable Entity p_i50006_2_,
      double p_i50006_3_,
      double p_i50006_5_,
      double p_i50006_7_,
      float p_i50006_9_,
      boolean p_i50006_10_,
      BlockInteraction p_i50006_11_,
      List<BlockPos> p_i50006_12_
   ) {
      super(p_i50006_1_, p_i50006_2_, p_i50006_3_, p_i50006_5_, p_i50006_7_, p_i50006_9_, p_i50006_10_, p_i50006_11_, p_i50006_12_);
   }

   public ANExplosion(
      Level p_i50007_1_,
      @Nullable Entity p_i50007_2_,
      double p_i50007_3_,
      double p_i50007_5_,
      double p_i50007_7_,
      float p_i50007_9_,
      boolean p_i50007_10_,
      BlockInteraction p_i50007_11_
   ) {
      super(p_i50007_1_, p_i50007_2_, p_i50007_3_, p_i50007_5_, p_i50007_7_, p_i50007_9_, p_i50007_10_, p_i50007_11_);
   }

   public ANExplosion(
      Level p_i231610_1_,
      @Nullable Entity p_i231610_2_,
      @Nullable DamageSource p_i231610_3_,
      @Nullable ExplosionDamageCalculator p_i231610_4_,
      double p_i231610_5_,
      double p_i231610_7_,
      double p_i231610_9_,
      float p_i231610_11_,
      boolean p_i231610_12_,
      BlockInteraction p_i231610_13_,
      double numAmps
   ) {
      super(p_i231610_1_, p_i231610_2_, p_i231610_3_, p_i231610_4_, p_i231610_5_, p_i231610_7_, p_i231610_9_, p_i231610_11_, p_i231610_12_, p_i231610_13_);
      this.amps = numAmps;
   }

   public void m_46061_() {
      Set<BlockPos> set = Sets.newHashSet();
      int i = 16;

      for (int j = 0; j < 16; j++) {
         for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
               if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                  double d0 = (double)((float)j / 15.0F * 2.0F - 1.0F);
                  double d1 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                  double d2 = (double)((float)l / 15.0F * 2.0F - 1.0F);
                  double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                  d0 /= d3;
                  d1 /= d3;
                  d2 /= d3;
                  float f = this.f_46017_ * (0.7F + this.f_46012_.f_46441_.m_188501_() * 0.6F);
                  double d4 = this.f_46013_;
                  double d6 = this.f_46014_;
                  double d8 = this.f_46015_;

                  for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                     BlockPos blockpos = new BlockPos(d4, d6, d8);
                     BlockState blockstate = this.f_46012_.m_8055_(blockpos);
                     FluidState fluidstate = this.f_46012_.m_6425_(blockpos);
                     Optional<Float> optional = this.f_46019_.m_6617_(this, this.f_46012_, blockpos, blockstate, fluidstate);
                     if (optional.isPresent()) {
                        f -= (optional.get() + 0.3F) * 0.3F;
                     }

                     if (f > 0.0F && this.f_46019_.m_6714_(this, this.f_46012_, blockpos, blockstate, f)) {
                        set.add(blockpos);
                     }

                     d4 += d0 * 0.3F;
                     d6 += d1 * 0.3F;
                     d8 += d2 * 0.3F;
                  }
               }
            }
         }
      }

      this.f_46020_.addAll(set);
      float f2 = this.f_46017_ * 2.0F;
      int k1 = Mth.m_14107_(this.f_46013_ - (double)f2 - 1.0);
      int l1 = Mth.m_14107_(this.f_46013_ + (double)f2 + 1.0);
      int i2 = Mth.m_14107_(this.f_46014_ - (double)f2 - 1.0);
      int i1 = Mth.m_14107_(this.f_46014_ + (double)f2 + 1.0);
      int j2 = Mth.m_14107_(this.f_46015_ - (double)f2 - 1.0);
      int j1 = Mth.m_14107_(this.f_46015_ + (double)f2 + 1.0);
      List<Entity> list = this.f_46012_.m_45933_(this.f_46016_, new AABB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
      ForgeEventFactory.onExplosionDetonate(this.f_46012_, this, list, (double)f2);
      Vec3 vector3d = new Vec3(this.f_46013_, this.f_46014_, this.f_46015_);

      for (Entity entity : list) {
         if (!entity.m_6128_()) {
            double d12 = (double)(Mth.m_14116_((float)entity.m_20238_(vector3d)) / f2);
            if (d12 <= 1.0) {
               double d5 = entity.m_20185_() - this.f_46013_;
               double d7 = (entity instanceof PrimedTnt ? entity.m_20186_() : entity.m_20188_()) - this.f_46014_;
               double d9 = entity.m_20189_() - this.f_46015_;
               double d13 = (double)Mth.m_14116_((float)(d5 * d5 + d7 * d7 + d9 * d9));
               if (d13 != 0.0) {
                  d5 /= d13;
                  d7 /= d13;
                  d9 /= d13;
                  double d14 = (double)m_46064_(vector3d, entity);
                  double d10 = (1.0 - d12) * d14;
                  float damage = (float)Math.min(
                     (double)Math.max(0.0F, (float)((int)((d10 * d10 + d10) / 2.0 * 7.0 * (double)f2 + 1.0))),
                     this.baseDamage + this.amps * this.ampDamageScalar
                  );
                  entity.m_6469_(this.m_46077_(), damage);
                  double d11 = d10;
                  if (entity instanceof LivingEntity) {
                     d11 = ProtectionEnchantment.m_45135_((LivingEntity)entity, d10);
                  }

                  entity.m_20256_(entity.m_20184_().m_82520_(d5 * d11, d7 * d11, d9 * d11));
                  if (entity instanceof Player) {
                     Player playerentity = (Player)entity;
                     if (!playerentity.m_5833_() && (!playerentity.m_7500_() || !playerentity.f_36077_.f_35935_)) {
                        this.f_46021_.put(playerentity, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                     }
                  }
               }
            }
         }
      }
   }
}
