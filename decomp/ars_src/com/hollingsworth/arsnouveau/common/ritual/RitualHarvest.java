package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RitualHarvest extends AbstractRitual {
   @Override
   protected void tick() {
      Level world = this.getWorld();
      BlockPos pos = this.getPos();
      if (world != null && world.f_46443_) {
         ParticleUtil.spawnRitualAreaEffect(this.getPos(), this.getWorld(), this.rand, this.getCenterColor(), 4);
      } else if (world != null && pos != null && world.m_46467_() % 200L == 0L) {
         int range = 4;
         boolean hasPlayedSound = false;

         for (BlockPos blockpos : BlockPos.m_121940_(pos.m_7918_(range, -1, range), pos.m_7918_(-range, 1, -range))) {
            BlockState state = world.m_8055_(blockpos);
            if (state.m_60734_() instanceof FarmBlock
               || world.m_8055_(blockpos.m_7494_()).m_60734_() instanceof CropBlock
               || world.m_8055_(blockpos.m_7494_()).m_60734_() instanceof NetherWartBlock
               || world.m_8055_(blockpos.m_7494_()).m_204336_(BlockTagProvider.HARVEST_STEMS)) {
               blockpos = blockpos.m_7494_();
               state = world.m_8055_(blockpos);
            }

            if (state.m_60734_() instanceof NetherWartBlock) {
               if (this.harvestNetherwart(blockpos, state, world) && !hasPlayedSound) {
                  world.m_5594_(null, this.getPos(), SoundEvents.f_11838_, SoundSource.BLOCKS, 1.0F, 1.0F);
                  hasPlayedSound = true;
               }
            } else if (state.m_60734_() instanceof CocoaBlock) {
               if (this.harvestPods(blockpos, state, world) && !hasPlayedSound) {
                  world.m_5594_(null, this.getPos(), SoundEvents.f_11838_, SoundSource.BLOCKS, 1.0F, 1.0F);
                  hasPlayedSound = true;
               }
            } else if (!(state.m_60734_() instanceof StemGrownBlock)
               && (!state.m_204336_(BlockTagProvider.HARVEST_STEMS) || state.m_60734_() != world.m_8055_(blockpos.m_7495_()).m_60734_())) {
               if (state.m_60734_() instanceof CropBlock) {
                  CropBlock cropsBlock = (CropBlock)world.m_8055_(blockpos).m_60734_();
                  if (cropsBlock.m_52307_(state) && world instanceof ServerLevel) {
                     if (this.processAndSpawnDrops(blockpos, state, world, true) && !hasPlayedSound) {
                        world.m_5594_(null, this.getPos(), SoundEvents.f_11838_, SoundSource.BLOCKS, 1.0F, 1.0F);
                        hasPlayedSound = true;
                     }

                     world.m_46597_(blockpos, cropsBlock.m_52289_(1));
                  }
               }
            } else {
               this.processAndSpawnDrops(blockpos, state, world, false);
               BlockUtil.destroyBlockSafely(world, blockpos, false, null);
            }
         }
      }
   }

   public boolean harvestNetherwart(BlockPos pos, BlockState state, Level world) {
      if ((Integer)state.m_61143_(NetherWartBlock.f_54967_) != 3) {
         return false;
      } else {
         this.processAndSpawnDrops(pos, state, world, true);
         world.m_46597_(pos, (BlockState)state.m_61124_(NetherWartBlock.f_54967_, 0));
         this.setNeedsSource(true);
         return true;
      }
   }

   public boolean harvestPods(BlockPos pos, BlockState state, Level world) {
      if ((Integer)state.m_61143_(CocoaBlock.f_51736_) != 2) {
         return false;
      } else {
         this.processAndSpawnDrops(pos, state, world, true);
         world.m_46597_(pos, (BlockState)state.m_61124_(CocoaBlock.f_51736_, 0));
         this.setNeedsSource(true);
         return true;
      }
   }

   public boolean processAndSpawnDrops(BlockPos pos, BlockState state, Level world, boolean takeSeedToReplant) {
      List<ItemStack> cropDrops = Block.m_49869_(state, (ServerLevel)world, pos, world.m_7702_(pos));
      if (takeSeedToReplant) {
         for (ItemStack i : cropDrops) {
            if (i.m_41720_() instanceof BlockItem blockItem && blockItem.m_40614_() == state.m_60734_()) {
               i.m_41774_(1);
               break;
            }
         }
      }

      InventoryManager manager = this.tile.getInventoryManager();
      cropDrops.forEach(d -> {
         if (!d.m_41619_() && d.m_41720_() != BlockRegistry.MAGE_BLOOM_CROP.m_5456_()) {
            d = manager.insertStack(d);
            if (!d.m_41619_()) {
               world.m_7967_(new ItemEntity(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), d));
            }
         }
      });
      this.setNeedsSource(true);
      return true;
   }

   @Override
   public String getLangDescription() {
      return "Casts the Harvest effect on nearby crops. Consumes source each time a set of crops are harvested. If an inventory is adjacent to the brazier, the items will be deposited in them before dropping on the ground.";
   }

   @Override
   public String getLangName() {
      return "Harvest";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.HARVEST);
   }

   @Override
   public int getSourceCost() {
      return 100;
   }

   @Override
   public ParticleColor getCenterColor() {
      return new ParticleColor(50, 180, 50);
   }
}
