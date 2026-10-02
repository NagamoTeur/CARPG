package com.aizistral.enigmaticlegacy.items.generic;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public abstract class ItemBaseFood extends ItemBase {
   public ItemBaseFood() {
      this(getDefaultProperties(), buildDefaultFood());
   }

   public ItemBaseFood(Properties props, FoodProperties food) {
      super(props.m_41489_(food));
   }

   public boolean canEat(Level world, Player player, ItemStack food) {
      return true;
   }

   public void onConsumed(Level worldIn, Player player, ItemStack food) {
   }

   public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
      if (entityLiving instanceof Player) {
         this.onConsumed(worldIn, (Player)entityLiving, stack);
      }

      return super.m_5922_(stack, worldIn, entityLiving);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      return this.canEat(worldIn, playerIn, playerIn.m_21120_(handIn))
         ? super.m_7203_(worldIn, playerIn, handIn)
         : new InteractionResultHolder(InteractionResult.PASS, playerIn.m_21120_(handIn));
   }

   protected static FoodProperties buildDefaultFood() {
      return new Builder().m_38760_(0).m_38758_(0.0F).m_38765_().m_38767_();
   }
}
