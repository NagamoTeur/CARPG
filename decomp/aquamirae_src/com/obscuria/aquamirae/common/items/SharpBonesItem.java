package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SharpBonesItem extends Item {
   public SharpBonesItem() {
      super(
         new Properties().m_41491_(Aquamirae.TAB).m_41487_(64).m_41497_(Rarity.COMMON).m_41489_(new Builder().m_38760_(1).m_38758_(0.0F).m_38757_().m_38767_())
      );
   }

   public int m_8105_(@NotNull ItemStack itemstack) {
      return 24;
   }

   @NotNull
   public ItemStack m_5922_(@NotNull ItemStack itemstack, @NotNull Level world, LivingEntity entity) {
      entity.m_6469_(DamageSource.f_19313_, 1.0F);
      return super.m_5922_(itemstack, world, entity);
   }
}
