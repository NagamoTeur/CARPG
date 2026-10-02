package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class void_core extends Item {
   public void_core(Properties group) {
      super(group);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      int standingOnY = Mth.m_14107_(player.m_20186_()) - 1;
      double headY = player.m_20186_() + 1.0;
      float yawRadians = (float)Math.toRadians((double)(90.0F + player.m_146908_()));
      boolean hasSucceeded = false;
      if (player.m_146909_() > 70.0F) {
         for (int i = 0; i < 5; i++) {
            float mulPosedYaw = yawRadians + (float)i * (float) Math.PI * 0.4F;
            if (this.spawnFangs(
               player.m_20185_() + (double)Mth.m_14089_(mulPosedYaw) * 1.5,
               headY,
               player.m_20189_() + (double)Mth.m_14031_(mulPosedYaw) * 1.5,
               standingOnY,
               mulPosedYaw,
               0,
               world,
               player
            )) {
               hasSucceeded = true;
            }
         }

         for (int k = 0; k < 8; k++) {
            float mulPosedYaw = yawRadians + (float)k * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
            if (this.spawnFangs(
               player.m_20185_() + (double)Mth.m_14089_(mulPosedYaw) * 2.5,
               headY,
               player.m_20189_() + (double)Mth.m_14031_(mulPosedYaw) * 2.5,
               standingOnY,
               mulPosedYaw,
               3,
               world,
               player
            )) {
               hasSucceeded = true;
            }
         }
      } else {
         for (int l = 0; l < 10; l++) {
            double d2 = 1.25 * (double)(l + 1);
            if (this.spawnFangs(
               player.m_20185_() + (double)Mth.m_14089_(yawRadians) * d2,
               headY,
               player.m_20189_() + (double)Mth.m_14031_(yawRadians) * d2,
               standingOnY,
               yawRadians,
               l,
               world,
               player
            )) {
               hasSucceeded = true;
            }
         }
      }

      ItemStack stack = player.m_21120_(hand);
      if (hasSucceeded) {
         player.m_36335_().m_41524_(this, CMConfig.VoidCoreCooldown);
         return InteractionResultHolder.m_19090_(stack);
      } else {
         return InteractionResultHolder.m_19098_(stack);
      }
   }

   private boolean spawnFangs(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, Player player) {
      BlockPos blockpos = new BlockPos(x, y, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = world.m_8055_(blockpos1);
         if (blockstate.m_60783_(world, blockpos1, Direction.UP)) {
            if (!world.m_46859_(blockpos)) {
               BlockState blockstate1 = world.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(world, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= lowestYCheck);

      if (flag) {
         world.m_7967_(new Void_Rune_Entity(world, x, (double)blockpos.m_123342_() + d0, z, yRot, warmupDelayTicks, (float)CMConfig.Voidrunedamage, player));
         return true;
      } else {
         return false;
      }
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.void_core.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
