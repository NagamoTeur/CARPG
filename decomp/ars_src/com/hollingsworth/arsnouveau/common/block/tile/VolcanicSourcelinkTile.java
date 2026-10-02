package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;

public class VolcanicSourcelinkTile extends SourcelinkTile implements IAnimatable {
   public VolcanicSourcelinkTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.VOLCANIC_TILE, pos, state);
   }

   @Override
   public int getTransferRate() {
      return 1000;
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.f_58857_.f_46443_) {
         if (this.f_58857_.m_46467_() % 20L == 0L && this.canAcceptSource()) {
            for (ItemEntity i : this.f_58857_.m_45976_(ItemEntity.class, new AABB(this.f_58858_).m_82400_(1.0))) {
               int source = this.getSourceValue(i.m_32055_());
               if (source > 0 && (this.canConsumeEntireItem(i.m_32055_()) || this.getSource() <= 0)) {
                  this.addSource(source);
                  ItemStack containerItem = i.m_32055_().getCraftingRemainingItem();
                  i.m_32055_().m_41774_(1);
                  if (!containerItem.m_41619_()) {
                     this.f_58857_.m_7967_(new ItemEntity(this.f_58857_, i.m_20185_(), i.m_20186_(), i.m_20189_(), containerItem));
                  }

                  Networking.sendToNearby(
                     this.f_58857_, this.m_58899_(), new PacketANEffect(PacketANEffect.EffectType.BURST, i.m_20183_(), new ParticleColor.IntWrapper(255, 0, 0))
                  );
                  return;
               }
            }

            for (ArcanePedestalTile ix : this.getSurroundingPedestals()) {
               int sourceValue = this.getSourceValue(ix.m_8020_(0));
               if (sourceValue > 0 && (this.canConsumeEntireItem(ix.m_8020_(0)) || this.getSource() <= 0)) {
                  this.addSource(sourceValue);
                  ItemStack containerItem = ix.m_8020_(0).getCraftingRemainingItem();
                  ix.m_7407_(0, 1);
                  ix.m_6836_(0, containerItem);
                  Networking.sendToNearby(
                     this.f_58857_,
                     this.m_58899_(),
                     new PacketANEffect(PacketANEffect.EffectType.BURST, ix.m_58899_().m_7494_(), new ParticleColor.IntWrapper(255, 0, 0))
                  );
               }
            }
         }
      }
   }

   public boolean canConsumeEntireItem(ItemStack i) {
      int sourceValue = this.getSourceValue(i);
      return this.getSource() + sourceValue <= this.getMaxSource();
   }

   public int getSourceValue(ItemStack i) {
      int source = 0;
      int progress = 0;
      int burnTime = ForgeHooks.getBurnTime(i, null);
      if (burnTime > 0) {
         source = burnTime / 12;
         progress = 1;
      }

      if (i.m_41720_() == BlockRegistry.BLAZING_LOG.m_5456_()) {
         source += 100;
         progress += 5;
      } else if (i.m_204117_(ItemTagProvider.ARCHWOOD_LOG_TAG)) {
         source += 50;
         progress += 3;
      }

      if (i.m_41720_() == ItemsRegistry.FIRE_ESSENCE.get()) {
         source = 2000;
      }

      this.progress += progress;
      this.m_6596_();
      return source;
   }

   @Override
   public void doRandomAction() {
      if (!this.f_58857_.f_46443_) {
         AtomicBoolean set = new AtomicBoolean(false);
         BlockPos.m_121985_(this.f_58858_, 1, 0, 1)
            .forEach(
               p -> {
                  if (!set.get()
                     && this.f_58857_.m_8055_(p).m_60795_()
                     && (this.f_58857_.m_6425_(p.m_7495_()).m_76152_() == Fluids.f_76195_ || this.f_58857_.m_6425_(p.m_7495_()).m_76152_() == Fluids.f_76194_)) {
                     this.f_58857_.m_46597_(p, BlockRegistry.LAVA_LILY.getState(this.f_58857_, p));
                     set.set(true);
                  }
               }
            );
         BlockPos magmaPos = this.getBlockInArea(Blocks.f_50450_, 1);
         if (magmaPos != null && this.progress >= 200) {
            this.f_58857_.m_46597_(magmaPos, Blocks.f_49991_.m_49966_());
            this.progress -= 200;
         } else {
            BlockPos stonePos = this.getBlockInArea(Blocks.f_50069_, 1);
            if (stonePos != null && this.progress >= 150) {
               this.f_58857_.m_46597_(stonePos, Blocks.f_50450_.m_49966_());
               this.progress -= 150;
            } else {
               stonePos = this.getTagInArea(net.minecraftforge.common.Tags.Blocks.STONE, 1);
               if (stonePos != null && this.progress >= 150) {
                  this.f_58857_.m_46597_(stonePos, Blocks.f_50450_.m_49966_());
                  this.progress -= 150;
               }
            }
         }
      }
   }

   public BlockPos getTagInArea(TagKey<Block> block, int range) {
      AtomicReference<BlockPos> posFound = new AtomicReference<>();
      BlockPos.m_121990_(this.f_58858_.m_7918_(range, -1, range), this.f_58858_.m_7918_(-range, -1, -range)).forEach(blockPos -> {
         blockPos = blockPos.m_7949_();
         if (posFound.get() == null && this.f_58857_.m_8055_(blockPos).m_204336_(block)) {
            posFound.set(blockPos);
         }
      });
      return posFound.get();
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

   @Override
   public int getMaxSource() {
      return 5000;
   }
}
