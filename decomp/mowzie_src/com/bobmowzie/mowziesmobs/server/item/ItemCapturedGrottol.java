package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class ItemCapturedGrottol extends Item {
   public ItemCapturedGrottol(Properties properties) {
      super(properties);
   }

   public int getMaxStackSize(ItemStack stack) {
      return 1;
   }

   public InteractionResult m_6225_(UseOnContext context) {
      Player player = context.m_43723_();
      BlockPos pos = context.m_8083_();
      Direction facing = context.m_43719_();
      InteractionHand hand = context.m_43724_();
      Level world = context.m_43725_();
      if (context.m_43719_() == Direction.DOWN) {
         return InteractionResult.FAIL;
      } else {
         BlockPos location = pos.m_121945_(facing);
         ItemStack stack = player.m_21120_(hand);
         if (!player.m_36204_(location, facing, stack)) {
            return InteractionResult.FAIL;
         } else {
            if (!world.f_46443_) {
               EntityGrottol grottol = new EntityGrottol((EntityType<? extends EntityGrottol>)EntityHandler.GROTTOL.get(), world);
               CompoundTag compound = stack.m_41737_("EntityTag");
               if (compound != null) {
                  this.setData(grottol, compound);
               }

               grottol.m_20035_(location, 0.0F, 0.0F);
               this.lookAtPlayer(grottol, player);
               grottol.m_6518_((ServerLevelAccessor)world, world.m_6436_(location), MobSpawnType.MOB_SUMMONED, null, null);
               world.m_7967_(grottol);
               if (!player.m_150110_().f_35937_) {
                  stack.m_41774_(1);
               }
            }

            return InteractionResult.SUCCESS;
         }
      }
   }

   private void setData(EntityGrottol grottol, CompoundTag compound) {
      CompoundTag data = grottol.serializeNBT();
      UUID id = grottol.m_20148_();
      data.m_128391_(compound);
      grottol.deserializeNBT(data);
      grottol.m_20084_(id);
   }

   private void lookAtPlayer(EntityGrottol grottol, Player player) {
      LookControl helper = new LookControl(grottol);
      helper.m_24960_(player, 180.0F, 90.0F);
      helper.m_8128_();
   }

   public ItemStack create(EntityGrottol grottol) {
      ItemStack stack = new ItemStack(this);
      stack.m_41700_("EntityTag", grottol.serializeNBT());
      return stack;
   }
}
