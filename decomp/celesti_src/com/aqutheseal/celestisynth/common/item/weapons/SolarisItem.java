package com.aqutheseal.celestisynth.common.item.weapons;

import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.attack.solaris.SolarisFullRoundAttack;
import com.aqutheseal.celestisynth.common.attack.solaris.SolarisSoulDashAttack;
import com.aqutheseal.celestisynth.common.item.base.SkilledSwordItem;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class SolarisItem extends SkilledSwordItem {
   public SolarisItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
      super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
   }

   @Override
   public ImmutableList<WeaponAttackInstance> getPossibleAttacks(Player player, ItemStack stack, int dur) {
      return ImmutableList.of(new SolarisFullRoundAttack(player, stack), new SolarisSoulDashAttack(player, stack));
   }

   @Override
   public int getSkillsAmount() {
      return 2;
   }

   @Override
   public boolean hasPassive() {
      return true;
   }

   @Override
   public int getPassiveAmount() {
      return 1;
   }

   public boolean m_7579_(ItemStack itemStack, LivingEntity entity, LivingEntity source) {
      entity.m_20254_(5);
      return super.m_7579_(itemStack, entity, source);
   }

   @Override
   public void forceTick(ItemStack itemStack, Level level, Entity entity, int itemSlot, boolean isSelected) {
      if (entity instanceof Player player && (isSelected || player.m_21206_().m_41720_() instanceof SolarisItem)) {
         player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 2, 0));
      }

      super.forceTick(itemStack, level, entity, itemSlot, isSelected);
   }
}
