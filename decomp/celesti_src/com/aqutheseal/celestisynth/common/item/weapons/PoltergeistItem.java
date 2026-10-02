package com.aqutheseal.celestisynth.common.item.weapons;

import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.attack.poltergeist.PoltergeistBarrierCallAttack;
import com.aqutheseal.celestisynth.common.attack.poltergeist.PoltergeistCosmicSteelAttack;
import com.aqutheseal.celestisynth.common.item.base.SkilledAxeItem;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PoltergeistItem extends SkilledAxeItem implements CSWeapon {
   public PoltergeistItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
      super(tier, attackDamage, attackSpeed, properties);
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

   @Override
   public ImmutableList<WeaponAttackInstance> getPossibleAttacks(Player player, ItemStack stack, int useDuration) {
      return ImmutableList.of(new PoltergeistCosmicSteelAttack(player, stack), new PoltergeistBarrierCallAttack(player, stack));
   }

   public boolean m_7579_(ItemStack itemStack, LivingEntity entity, LivingEntity source) {
      entity.m_7292_(new MobEffectInstance(MobEffects.f_216964_, 60, 0));
      return super.m_7579_(itemStack, entity, source);
   }

   @Override
   public void forceTick(ItemStack itemStack, Level level, Entity entity, int itemSlot, boolean isSelected) {
      super.forceTick(itemStack, level, entity, itemSlot, isSelected);
      CompoundTag data = itemStack.m_41698_("csController");
      if (data.m_128471_("cs.hasAnimationBegun") && entity instanceof Player player) {
         player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 2, 2));
      }
   }

   @Override
   public void onPlayerHurt(LivingHurtEvent event, ItemStack mainHandItem, ItemStack offHandItem) {
      LivingEntity entity = event.getEntity();
      CompoundTag tagR = mainHandItem.m_41720_().getShareTag(entity.m_21205_());
      CompoundTag tagL = offHandItem.m_41720_().getShareTag(entity.m_21206_());
      if (tagR != null && tagR.m_128471_("cs.hasAnimationBegun") || tagL != null && tagL.m_128471_("cs.hasAnimationBegun")) {
         event.setAmount(event.getAmount() * 0.5F);
      }
   }
}
