package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MycelialSourcelinkTile extends SourcelinkTile {
   public MycelialSourcelinkTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public MycelialSourcelinkTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.MYCELIAL_TILE, pos, state);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.f_58857_.f_46443_) {
         if (this.f_58857_.m_46467_() % 40L == 0L && this.canAcceptSource()) {
            for (ItemEntity i : this.f_58857_.m_45976_(ItemEntity.class, new AABB(this.f_58858_).m_82400_(1.0))) {
               if (i.m_32055_().m_41720_().m_41472_()) {
                  int source = this.getSourceValue(i.m_32055_());
                  this.addSource(source);
                  ItemStack containerItem = i.m_32055_().getCraftingRemainingItem();
                  i.m_32055_().m_41774_(1);
                  if (!containerItem.m_41619_()) {
                     this.f_58857_.m_7967_(new ItemEntity(this.f_58857_, i.m_20185_(), i.m_20186_(), i.m_20189_(), containerItem));
                  }

                  Networking.sendToNearby(
                     this.f_58857_,
                     this.m_58899_(),
                     new PacketANEffect(PacketANEffect.EffectType.BURST, i.m_20183_(), new ParticleColor.IntWrapper(255, 255, 255))
                  );
               }
            }

            for (ArcanePedestalTile ix : this.getSurroundingPedestals()) {
               int sourceValue = this.getSourceValue(ix.m_8020_(0));
               if (sourceValue > 0) {
                  this.addSource(sourceValue);
                  ItemStack containerItem = ix.m_8020_(0).getCraftingRemainingItem();
                  ix.m_7407_(0, 1);
                  ix.m_6836_(0, containerItem);
                  Networking.sendToNearby(
                     this.f_58857_,
                     this.m_58899_(),
                     new PacketANEffect(PacketANEffect.EffectType.BURST, ix.m_58899_().m_7494_(), new ParticleColor.IntWrapper(255, 255, 255))
                  );
               }
            }
         }
      }
   }

   public int getSourceValue(ItemStack i) {
      if (i.m_41720_().m_41472_()) {
         int mana = 0;
         FoodProperties food = i.m_41720_().getFoodProperties(i, null);
         if (food == null) {
            return 0;
         } else {
            mana += 11 * food.m_38744_();
            mana = (int)((float)mana + 30.0F * food.m_38745_());
            this.progress++;
            if (i.m_204117_(ItemTagProvider.MAGIC_FOOD)
               || i.m_41720_() instanceof BlockItem blockItem && blockItem.m_40614_().m_49966_().m_204336_(BlockTagProvider.MAGIC_PLANTS)) {
               this.progress += 4;
               mana += 10;
               mana *= 2;
            }

            return mana;
         }
      } else {
         return 0;
      }
   }

   @Override
   public void doRandomAction() {
      super.doRandomAction();
      if (!this.f_58857_.f_46443_) {
         if (this.progress > 10) {
            for (BlockPos p : BlockPos.m_121925_(this.f_58858_, 1, 0, 1)) {
               if (this.f_58857_.m_8055_(p).m_60795_() && this.f_58857_.m_8055_(p.m_7495_()).m_60734_() == Blocks.f_50195_) {
                  this.f_58857_.m_46597_(p, (double)this.f_58857_.m_213780_().m_188501_() > 0.5 ? Blocks.f_50072_.m_49966_() : Blocks.f_50073_.m_49966_());
                  this.progress -= 10;
                  break;
               }
            }
         }

         BlockPos dirtPos = this.getBlockInArea(Blocks.f_50493_, 1);
         dirtPos = dirtPos == null ? this.getBlockInArea(Blocks.f_50440_, 1) : dirtPos;
         if (dirtPos != null && this.progress >= 25) {
            this.f_58857_.m_46597_(dirtPos, Blocks.f_50195_.m_49966_());
            this.progress -= 25;
         }
      }
   }

   public BlockPos getBlockInArea(Block block, int range) {
      AtomicReference<BlockPos> posFound = new AtomicReference<>();
      BlockPos.m_121990_(this.f_58858_.m_7918_(range, -1, range), this.f_58858_.m_7918_(-range, -1, -range)).forEach(blockPos -> {
         blockPos = blockPos.m_7949_();
         if (posFound.get() == null && this.f_58857_.m_8055_(blockPos).m_60734_() == block) {
            posFound.set(blockPos);
         }
      });
      return posFound.get();
   }
}
