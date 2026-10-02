package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.LightBlock;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class JarOfLight extends ModItem {
   public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      super.m_6883_(stack, worldIn, entityIn, itemSlot, isSelected);
      if (!worldIn.f_46443_) {
         CompoundTag tag = stack.m_41783_();
         if (tag == null) {
            stack.m_41751_(new CompoundTag());
         }

         tag = stack.m_41783_();
         if (this.lightExists(tag)) {
            if (this.getLightLocation(tag) == null) {
               this.setLightExists(tag, false);
               return;
            }

            BlockPos lightLocation = this.getLightLocation(tag);
            BlockState state = worldIn.m_8055_(lightLocation);
            if (!(state.m_60734_() instanceof LightBlock)) {
               this.setLightExists(tag, false);
               this.setLightLocation(tag, null);
            }

            if (BlockUtil.distanceFrom(lightLocation, entityIn.m_20183_()) > 7.0) {
               Direction opposite = entityIn.m_6350_().m_122424_();
               BlockPos preferredLightPos = entityIn.m_20183_().m_5484_(opposite, 1);
               this.removeLight(worldIn, tag);
               if (!this.placeLight(worldIn, preferredLightPos, tag)) {
                  this.placeLight(worldIn, preferredLightPos.m_6630_(2), tag);
               }
            }
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      if (worldIn.f_46443_) {
         return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
      } else {
         CompoundTag tag = playerIn.m_21120_(handIn).m_41783_();
         if (tag == null) {
            return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
         } else if (this.lightExists(tag)) {
            this.removeLight(worldIn, tag);
            return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
         } else {
            this.placeLight(worldIn, playerIn.m_20183_(), tag);
            return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
         }
      }
   }

   public boolean placeLight(Level world, BlockPos pos, CompoundTag tag) {
      if (world.m_8055_(pos).m_60767_() == Material.f_76296_) {
         world.m_46597_(pos, BlockRegistry.LIGHT_BLOCK.m_49966_());
         this.setLightExists(tag, true);
         this.setLightLocation(tag, pos);
         return true;
      } else {
         return false;
      }
   }

   public void removeLight(Level world, CompoundTag tag) {
      if (this.getLightLocation(tag) != null) {
         if (world.m_8055_(this.getLightLocation(tag)).m_60734_() instanceof LightBlock) {
            world.m_46597_(this.getLightLocation(tag), Blocks.f_50016_.m_49966_());
         }

         this.setLightExists(tag, false);
      }
   }

   public boolean lightExists(CompoundTag tag) {
      return tag.m_128441_("light_exists") && tag.m_128471_("light_exists");
   }

   public void setLightExists(CompoundTag tag, boolean lightExists) {
      tag.m_128379_("light_exists", lightExists);
   }

   public BlockPos getLightLocation(CompoundTag tag) {
      return tag.m_128441_("x") && tag.m_128441_("y") && tag.m_128441_("z") ? new BlockPos(tag.m_128451_("x"), tag.m_128451_("y"), tag.m_128451_("z")) : null;
   }

   public void setLightLocation(CompoundTag tag, BlockPos pos) {
      if (pos == null) {
         tag.m_128473_("x");
         tag.m_128473_("y");
         tag.m_128473_("z");
      } else {
         tag.m_128405_("x", pos.m_123341_());
         tag.m_128405_("y", pos.m_123342_());
         tag.m_128405_("z", pos.m_123343_());
      }
   }
}
