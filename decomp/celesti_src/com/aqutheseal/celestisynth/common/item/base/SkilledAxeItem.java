package com.aqutheseal.celestisynth.common.item.base;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class SkilledAxeItem extends AxeItem implements CSWeapon {
   public static final String ATTACK_INDEX_KEY = "cs.AttackIndex";

   public SkilledAxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
      super(pTier, (float)pAttackDamageModifier, pAttackSpeedModifier, pProperties);
   }

   public abstract ImmutableList<WeaponAttackInstance> getPossibleAttacks(Player var1, ItemStack var2, int var3);

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
      ItemStack heldStack = player.m_21120_(interactionHand);
      CompoundTag data = heldStack.m_41698_("csController");
      if (!player.m_36335_().m_41519_(heldStack.m_41720_()) && !data.m_128471_("cs.hasAnimationBegun")) {
         if (this.m_8105_(heldStack) > 0) {
            if (!player.m_36335_().m_41519_(heldStack.m_41720_()) && !data.m_128471_("cs.hasAnimationBegun")) {
               player.m_6672_(interactionHand);
               return InteractionResultHolder.m_19096_(heldStack);
            }

            return InteractionResultHolder.m_19100_(heldStack);
         }

         int index = 0;

         for (UnmodifiableIterator var7 = this.getPossibleAttacks(player, heldStack, 0).iterator(); var7.hasNext(); index++) {
            WeaponAttackInstance attack = (WeaponAttackInstance)var7.next();
            if (attack.getCondition()) {
               data.m_128379_("cs.hasAnimationBegun", true);
               AnimationManager.playAnimation(level, attack.getAnimation());
               setAttackIndex(heldStack, index);
               attack.startUsing();
               player.m_36335_().m_41524_(heldStack.m_41720_(), attack.getCooldown());
               break;
            }
         }
      }

      return InteractionResultHolder.m_19090_(heldStack);
   }

   public void m_5551_(ItemStack itemstack, @NotNull Level level, @NotNull LivingEntity entity, int i) {
      CompoundTag data = itemstack.m_41698_("csController");
      int dur = this.m_8105_(itemstack) - i;
      if (entity instanceof Player player) {
         int index = 0;

         for (UnmodifiableIterator var9 = this.getPossibleAttacks(player, itemstack, dur).iterator(); var9.hasNext(); index++) {
            WeaponAttackInstance attack = (WeaponAttackInstance)var9.next();
            if (attack.getCondition()) {
               data.m_128379_("cs.hasAnimationBegun", true);
               AnimationManager.playAnimation(level, attack.getAnimation());
               setAttackIndex(itemstack, index);
               attack.startUsing();
               player.m_36335_().m_41524_(itemstack.m_41720_(), attack.getCooldown());
               break;
            }
         }
      }
   }

   @Override
   public void forceTick(ItemStack itemStack, Level level, Entity entity, int itemSlot, boolean isSelected) {
      CompoundTag data = itemStack.m_41698_("csController");
      if (entity instanceof Player player && data.m_128471_("cs.hasAnimationBegun")) {
         int animationTimer = data.m_128451_("cs.animationTimer");
         data.m_128405_("cs.animationTimer", animationTimer + 1);
         int index = 0;

         for (UnmodifiableIterator var10 = this.getPossibleAttacks(player, itemStack, 0).iterator(); var10.hasNext(); index++) {
            WeaponAttackInstance attack = (WeaponAttackInstance)var10.next();
            if (getAttackIndex(itemStack) == index) {
               attack.baseTickSkill();
            }
         }
      }
   }

   public static int getAttackIndex(ItemStack stack) {
      CompoundTag data = stack.m_41698_("csController");
      return data.m_128451_("cs.AttackIndex");
   }

   public static void setAttackIndex(ItemStack stack, int value) {
      CompoundTag data = stack.m_41698_("csController");
      data.m_128405_("cs.AttackIndex", value);
   }
}
