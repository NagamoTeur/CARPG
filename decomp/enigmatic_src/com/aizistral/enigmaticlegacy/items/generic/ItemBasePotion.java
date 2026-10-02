package com.aizistral.enigmaticlegacy.items.generic;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ItemBasePotion extends ItemBase {
   public ItemBasePotion() {
      this(getDefaultProperties().m_41487_(1));
   }

   public ItemBasePotion(Properties props) {
      super(props);
   }

   public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity living) {
      if (living instanceof Player player) {
         this.onConsumed(worldIn, player, stack);
         if (player instanceof ServerPlayer) {
            CriteriaTriggers.f_10592_.m_23682_((ServerPlayer)player, stack);
         }

         if (!player.m_150110_().f_35937_) {
            stack.m_41774_(1);
            if (stack.m_41619_()) {
               return new ItemStack(Items.f_42590_);
            }

            player.m_150109_().m_36054_(new ItemStack(Items.f_42590_));
         }
      }

      return stack;
   }

   public int m_8105_(ItemStack stack) {
      return 32;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      if (this.canDrink(worldIn, playerIn, playerIn.m_21120_(handIn))) {
         playerIn.m_6672_(handIn);
         return super.m_7203_(worldIn, playerIn, handIn);
      } else {
         return new InteractionResultHolder(InteractionResult.PASS, playerIn.m_21120_(handIn));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(ItemStack stack) {
      return true;
   }

   public boolean canDrink(Level world, Player player, ItemStack potion) {
      return true;
   }

   public void onConsumed(Level worldIn, Player player, ItemStack potion) {
   }
}
