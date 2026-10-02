package com.cerbon.bosses_of_mass_destruction.mixin;

import com.cerbon.bosses_of_mass_destruction.block.custom.MonolithBlock;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({ServerLevel.class})
public class ExplosionMixin {
   @ModifyVariable(
      at = @At("HEAD"),
      method = {"explode"},
      argsOnly = true
   )
   private float Explosion(
      float g,
      @Nullable Entity source,
      @Nullable DamageSource damageSource,
      @Nullable ExplosionDamageCalculator damageCalculator,
      double x,
      double y,
      double z,
      float radius,
      boolean fire,
      BlockInteraction explosionInteraction
   ) {
      Level level = (Level)this;
      return !level.f_46443_ ? MonolithBlock.getExplosionPower((ServerLevel)level, new BlockPos(x, y, z), g) : g;
   }
}
