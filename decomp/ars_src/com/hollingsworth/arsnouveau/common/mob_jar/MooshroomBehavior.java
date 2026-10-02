package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MooshroomBehavior extends JarBehavior<MushroomCow> {
   @Override
   public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, MobJarTile tile) {
      ItemStack itemstack = player.m_21120_(handIn);
      MushroomCow mooshroom = this.entityFromJar(tile);
      if (itemstack.m_41720_() == Items.f_42574_ && mooshroom.m_6220_()) {
         Cow cow = (Cow)EntityType.f_20557_.m_20615_(world);
         cow.m_7678_(mooshroom.m_20185_(), mooshroom.m_20186_(), mooshroom.m_20189_(), mooshroom.m_146908_(), mooshroom.m_146909_());
         cow.m_21153_(mooshroom.m_21223_());
         cow.f_20883_ = mooshroom.f_20883_;
         if (mooshroom.m_8077_()) {
            cow.m_6593_(mooshroom.m_7770_());
            cow.m_20340_(mooshroom.m_20151_());
         }

         if (mooshroom.m_21532_()) {
            cow.m_21530_();
         }

         cow.m_20331_(mooshroom.m_20147_());
         tile.setEntityData(cow);
         Block mushroomType = mooshroom.m_28955_().m_28969_().m_60734_();

         for (int i = 0; i < 5; i++) {
            world.m_7967_(new ItemEntity(world, mooshroom.m_20185_(), mooshroom.m_20227_(1.0), mooshroom.m_20189_(), new ItemStack(mushroomType)));
         }
      } else {
         super.use(state, world, pos, player, handIn, hit, tile);
      }
   }
}
