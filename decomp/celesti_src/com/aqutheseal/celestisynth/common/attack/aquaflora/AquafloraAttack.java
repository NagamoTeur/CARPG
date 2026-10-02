package com.aqutheseal.celestisynth.common.attack.aquaflora;

import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AquafloraAttack extends WeaponAttackInstance {
   public static final String CHECK_PASSIVE = "cs.checkPassiveIfBlooming";

   public AquafloraAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   public AquafloraAttack(Player player, ItemStack stack) {
      super(player, stack);
   }

   public void createHitEffect(ItemStack itemStack, Level level, Player player, LivingEntity target) {
      RandomSource random = level.m_213780_();
      this.sendExpandingParticles(level, ParticleTypes.f_123810_, target.m_20185_(), target.m_20186_(), target.m_20189_(), 25, 0.5F);
      this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_BLING.get(), 0.2F, 1.0F + random.m_188501_());
   }
}
