package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class DragonBehavior extends JarBehavior<EnderDragon> {
   @Override
   public void use(BlockState state, Level world, BlockPos pos, Player pPlayer, InteractionHand pHand, BlockHitResult hit, MobJarTile tile) {
      ItemStack itemstack = pPlayer.m_21120_(pHand);
      if (itemstack.m_150930_(Items.f_42590_)) {
         pPlayer.m_5496_(SoundEvents.f_11771_, 1.0F, 1.0F);
         pPlayer.f_19853_.m_220400_(pPlayer, GameEvent.f_157816_, pPlayer.m_20182_());
         pPlayer.m_36246_(Stats.f_12982_.m_12902_(itemstack.m_41720_()));
         ItemUtils.m_41813_(itemstack, pPlayer, new ItemStack(Items.f_42735_));
      }
   }
}
