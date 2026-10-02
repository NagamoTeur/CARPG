package com.aqutheseal.celestisynth.common.item.weapons;

import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerDualGalestormAttack;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerGalestormAttack;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerWheelAttack;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerWhirlwindAttack;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerWindRoarAttack;
import com.aqutheseal.celestisynth.common.item.base.SkilledSwordItem;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class BreezebreakerItem extends SkilledSwordItem {
   public static final String BB_COMBO_POINTS = "cs.bbCombo";
   public static final String AT_BUFF_STATE = "cs.bbBuffState";
   public static final String BUFF_STATE_LIMITER = "cs.bbBuffStateLimiter";

   public BreezebreakerItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
      super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
   }

   @Override
   public ImmutableList<WeaponAttackInstance> getPossibleAttacks(Player player, ItemStack stack, int useDuration) {
      return ImmutableList.of(
         new BreezebreakerGalestormAttack(player, stack, useDuration),
         new BreezebreakerDualGalestormAttack(player, stack, useDuration),
         new BreezebreakerWheelAttack(player, stack, useDuration),
         new BreezebreakerWhirlwindAttack(player, stack, useDuration),
         new BreezebreakerWindRoarAttack(player, stack, useDuration)
      );
   }

   @Override
   public int getSkillsAmount() {
      return 5;
   }

   @Override
   public int getPassiveAmount() {
      return 2;
   }

   @Override
   public boolean hasPassive() {
      return true;
   }

   @Override
   public void forceTick(ItemStack itemStack, Level level, Entity owner, int itemSlot, boolean isSelected) {
      super.forceTick(itemStack, level, owner, itemSlot, isSelected);
      CompoundTag extrasData = itemStack.m_41698_("csExtras");
      if (owner instanceof Player playerOwner && (isSelected || playerOwner.m_21206_().m_41720_() instanceof BreezebreakerItem)) {
         this.sendExpandingParticles(level, ParticleTypes.f_123810_, owner.m_20185_(), owner.m_20186_(), owner.m_20189_(), 1, 0.1F);
      }

      if (extrasData.m_128471_("cs.bbBuffState")) {
         if (owner instanceof Player player) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 2, 1));
            double radius = 1.5 + (double)player.m_20205_();
            double speed = 0.5;
            double offX = radius * Math.sin(speed * (double)player.f_19797_);
            double offY = (double)(player.m_20206_() / 2.0F);
            double offZ = radius * Math.cos(speed * (double)player.f_19797_);
            ParticleUtil.sendParticle(
               level, (SimpleParticleType)CSParticleTypes.BREEZEBROKEN.get(), player.m_20185_() + offX, player.m_20186_() + offY, player.m_20189_() + offZ
            );
         }

         extrasData.m_128405_("cs.bbBuffStateLimiter", extrasData.m_128451_("cs.bbBuffStateLimiter") + 1);
         if (extrasData.m_128451_("cs.bbBuffStateLimiter") >= 200) {
            extrasData.m_128379_("cs.bbBuffState", false);
            extrasData.m_128405_("cs.bbCombo", 0);
            extrasData.m_128405_("cs.bbBuffStateLimiter", 0);
         }
      }
   }

   @Override
   public void onPlayerHurt(LivingHurtEvent event, ItemStack mainHandItem, ItemStack offHandItem) {
      if (event.getSource() == DamageSource.f_19315_) {
         event.setCanceled(true);
      } else {
         event.setAmount(event.getAmount() * 2.3F);
      }
   }

   public int m_8105_(@NotNull ItemStack stack) {
      return 72000;
   }

   @NotNull
   public UseAnim m_6164_(@NotNull ItemStack stack) {
      return UseAnim.BOW;
   }
}
