package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Infinimeal extends ItemBase implements Vanishable {
   public Infinimeal() {
      super(getDefaultProperties().m_41487_(1).m_41497_(Rarity.UNCOMMON));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.infinimeal1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.infinimeal2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.infinimeal3");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   public InteractionResult m_6225_(UseOnContext context) {
      ItemStack stack = context.m_43722_();
      int savedCount = stack.m_41613_();
      InteractionResult result = Items.f_42499_.m_6225_(context);
      stack.m_41764_(savedCount);
      if (result == InteractionResult.PASS) {
         BlockPos pos = context.m_8083_();
         Level world = context.m_43725_();
         BlockState state = world.m_8055_(pos);
         Block block = state.m_60734_();
         if (!(block instanceof CactusBlock) && !(block instanceof SugarCaneBlock)) {
            if (block instanceof VineBlock) {
               if (!block.m_6724_(state)) {
                  return result;
               }

               if (world.f_46443_) {
                  EnigmaticLegacy.PROXY.spawnBonemealParticles(world, pos, 0);
               }

               int cycles = 7 + world.f_46441_.m_188503_(7);
               if (world instanceof ServerLevel) {
                  for (int i = 0; i <= cycles; i++) {
                     state.m_222972_((ServerLevel)world, pos, world.f_46441_);
                  }

                  state.m_60701_(world, pos, 4);
               }

               return InteractionResult.m_19078_(world.f_46443_);
            }

            if (block instanceof NetherWartBlock) {
               if (!block.m_6724_(state)) {
                  return result;
               }

               if (!world.f_46443_) {
                  world.m_46796_(2005, pos, 0);
               }

               int cycles = 1 + world.f_46441_.m_188503_(1);
               cycles *= 11;
               if (world instanceof ServerLevel) {
                  for (int i = 0; i <= cycles; i++) {
                     state.m_222972_((ServerLevel)world, pos, world.f_46441_);
                  }
               }

               return InteractionResult.m_19078_(world.f_46443_);
            }

            if (block instanceof ChorusPlantBlock || block instanceof ChorusFlowerBlock) {
               if (!world.f_46443_) {
                  world.m_46796_(2005, pos, 0);
               }

               if (world instanceof ServerLevel serverWorld) {
                  List<BlockPos> flowers = this.findChorusFlowers(world, pos);
                  flowers.forEach(flowerPos -> {
                     int cycles = 1 + world.f_46441_.m_188503_(2);
                     cycles *= 11;

                     for (int i = 0; i <= cycles; i++) {
                        BlockState flowerState = world.m_8055_(flowerPos);
                        flowerState.m_222972_(serverWorld, flowerPos, world.f_46441_);
                     }
                  });
               }

               return InteractionResult.m_19078_(world.f_46443_);
            }
         } else {
            BlockPos topMostPos = this.findTopmostGrowable(world, pos, block, true);
            BlockState topMostState = world.m_8055_(topMostPos);
            if (topMostState.m_61138_(BlockStateProperties.f_61410_) && world.m_46859_(topMostPos.m_7494_())) {
               int age = (Integer)topMostState.m_61143_(BlockStateProperties.f_61410_);
               int plantHeight = 1;

               while (world.m_8055_(topMostPos.m_6625_(plantHeight)).m_60713_(block)) {
                  plantHeight++;
               }

               if (plantHeight >= 3) {
                  return result;
               }

               if (!world.f_46443_) {
                  world.m_46796_(2005, pos, 0);
               }

               age += world.f_46441_.m_188503_(20);
               world.m_7731_(topMostPos, (BlockState)topMostState.m_61124_(BlockStateProperties.f_61410_, Math.min(age, 15)), 4);
               if (world instanceof ServerLevel) {
                  world.m_8055_(topMostPos).m_222972_((ServerLevel)world, topMostPos, world.f_46441_);
               }

               return InteractionResult.m_19078_(world.f_46443_);
            }
         }
      }

      return result;
   }

   private List<BlockPos> findChorusFlowers(Level level, BlockPos pos) {
      List<BlockPos> chorusTree = new ArrayList<>();
      chorusTree.add(pos);

      int formerSize;
      do {
         formerSize = chorusTree.size();

         for (BlockPos treePos : new ArrayList<>(chorusTree)) {
            chorusTree.addAll(this.getNeighboringBlocks(level, treePos, chorusTree, ChorusFlowerBlock.class, ChorusPlantBlock.class));
         }
      } while (formerSize != chorusTree.size());

      return chorusTree.stream().filter(p -> level.m_8055_(p).m_60734_() instanceof ChorusFlowerBlock).collect(Collectors.toList());
   }

   @SafeVarargs
   private List<BlockPos> getNeighboringBlocks(Level level, BlockPos pos, List<BlockPos> exclude, Class<? extends Block>... classes) {
      BlockPos[] neighbors = new BlockPos[]{pos.m_7494_(), pos.m_7495_(), pos.m_122029_(), pos.m_122012_(), pos.m_122019_(), pos.m_122024_()};
      return Arrays.stream(neighbors)
         .filter(
            neighbor -> !exclude.contains(neighbor) && Arrays.stream(classes).anyMatch(theClass -> theClass.isInstance(level.m_8055_(neighbor).m_60734_()))
         )
         .collect(Collectors.toList());
   }

   private BlockPos findTopmostGrowable(Level world, BlockPos pos, Block block, boolean goUp) {
      BlockPos top = pos;

      while (world.m_8055_(top) != null && world.m_8055_(top).m_60734_() == block) {
         BlockPos nextUp = goUp ? top.m_7494_() : top.m_7495_();
         if (world.m_8055_(nextUp) == null || world.m_8055_(nextUp).m_60734_() != block) {
            return top;
         }

         top = nextUp;
      }

      return pos;
   }
}
