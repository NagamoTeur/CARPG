package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ItemIceCrystal extends Item {
   public ItemIceCrystal(Properties properties) {
      super(properties);
   }

   public boolean m_41465_() {
      return true;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.m_21120_(handIn);
      AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(playerIn);
      if (abilityCapability != null) {
         playerIn.m_6672_(handIn);
         if (stack.m_41773_() + 5 < stack.m_41776_() || (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
            if (!worldIn.m_5776_()) {
               AbilityHandler.INSTANCE.sendAbilityMessage(playerIn, AbilityHandler.ICE_BREATH_ABILITY);
            }

            stack.m_41622_(5, playerIn, p -> p.m_21190_(handIn));
            playerIn.m_6672_(handIn);
            return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
         }

         abilityCapability.getAbilityMap().get(AbilityHandler.ICE_BREATH_ABILITY).end();
      }

      return super.m_7203_(worldIn, playerIn, handIn);
   }

   public void m_5551_(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof Player) {
         Ability iceBreathAbility = AbilityHandler.INSTANCE.getAbility(entityLiving, AbilityHandler.ICE_BREATH_ABILITY);
         if (iceBreathAbility != null && iceBreathAbility.isUsing()) {
            iceBreathAbility.end();
         }
      }

      super.m_5551_(stack, worldIn, entityLiving, timeLeft);
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public int getMaxDamage(ItemStack stack) {
      return (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.durability.get();
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      if (!(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
         tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
      }
   }
}
