package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import top.theillusivec4.curios.api.SlotContext;

public class MegaSponge extends ItemBaseCurio implements Vanishable {
   public static Omniconfig.IntParameter radius;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("Megasponge");
      radius = builder.comment("Radius in which Exptrapolated Megaspong absorbs water. Default 4 equals to vanilla sponge").max(128.0).getInt("Radius", 4);
      builder.popPrefix();
   }

   public MegaSponge() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.UNCOMMON));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.megaSponge1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.megaSponge2");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   public BlockPos getCollidedWater(TagKey<Fluid> fluidTag, Player player) {
      AABB axisalignedbb = player.m_20191_().m_82406_(0.001);
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82289_);
      int l = Mth.m_14165_(axisalignedbb.f_82292_);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      BlockPos pos = null;
      if (!player.f_19853_.m_46812_(i, k, i1, j, l, j1)) {
         return null;
      } else {
         try {
            MutableBlockPos blockpos$pooledmutableblockpos = new MutableBlockPos();

            for (int l1 = i; l1 < j; l1++) {
               for (int i2 = k; i2 < l; i2++) {
                  for (int j2 = i1; j2 < j1; j2++) {
                     blockpos$pooledmutableblockpos.m_122178_(l1, i2, j2);
                     FluidState ifluidstate = player.f_19853_.m_6425_(blockpos$pooledmutableblockpos);
                     if (ifluidstate.m_205070_(fluidTag)) {
                        ifluidstate.m_76163_(player.f_19853_, blockpos$pooledmutableblockpos);
                        pos = new BlockPos(l1, i2, j2);
                     }
                  }
               }
            }
         } catch (Exception var16) {
            var16.printStackTrace();
         }

         return pos;
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof Player player && !player.f_19853_.f_46443_ && !player.m_36335_().m_41519_(this)) {
         List<BlockPos> doomedWaterBlocks = new ArrayList<>();
         BlockPos initialPos = this.getCollidedWater(FluidTags.f_13131_, player);
         BlockState initialState = initialPos != null ? player.f_19853_.m_8055_(initialPos) : null;
         if (initialPos != null && initialState != null && initialState.m_60819_() != null && initialState.m_60819_().m_205070_(FluidTags.f_13131_)) {
            doomedWaterBlocks.add(initialPos);
            List<BlockPos> processedBlocks = new ArrayList<>();
            processedBlocks.add(initialPos);

            for (int counter = 0; counter <= radius.getValue(); counter++) {
               List<BlockPos> outputBlocks = new ArrayList<>();

               for (BlockPos checkedPos : processedBlocks) {
                  outputBlocks.addAll(this.getNearbyWater(player.f_19853_, checkedPos));
               }

               processedBlocks.clear();

               for (BlockPos thePos : outputBlocks) {
                  if (!doomedWaterBlocks.contains(thePos)) {
                     processedBlocks.add(thePos);
                     doomedWaterBlocks.add(thePos);
                  }
               }

               outputBlocks.clear();
            }

            processedBlocks.clear();

            for (BlockPos exterminatedBlock : doomedWaterBlocks) {
               this.absorbWaterBlock(exterminatedBlock, player.f_19853_.m_8055_(exterminatedBlock), player.f_19853_);
            }

            doomedWaterBlocks.clear();
            player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_11781_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR.with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 64.0, player.f_19853_.m_46472_())),
                  new PacketPortalParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 40, 1.0, false)
               );
            player.m_36335_().m_41524_(this, 20);
         }
      }
   }

   public void absorbWaterBlock(BlockPos pos, BlockState state, Level world) {
      if (!(state.m_60734_() instanceof BucketPickup) || ((BucketPickup)state.m_60734_()).m_142598_(world, pos, state).m_41619_()) {
         if (state.m_60734_() instanceof LiquidBlock) {
            world.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
         } else if (state.m_60767_() == Material.f_76301_ || state.m_60767_() == Material.f_76304_) {
            BlockEntity tileentity = state.m_155947_() ? world.m_7702_(pos) : null;
            Block.m_49892_(state, world, pos, tileentity);
            world.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
         }
      }
   }

   public List<BlockPos> getNearbyWater(Level world, BlockPos pos) {
      List<BlockPos> nearBlocks = new ArrayList<>();
      List<BlockPos> waterBlocks = new ArrayList<>();
      nearBlocks.add(new BlockPos(pos.m_123341_() + 1, pos.m_123342_(), pos.m_123343_()));
      nearBlocks.add(new BlockPos(pos.m_123341_() - 1, pos.m_123342_(), pos.m_123343_()));
      nearBlocks.add(new BlockPos(pos.m_123341_(), pos.m_123342_() + 1, pos.m_123343_()));
      nearBlocks.add(new BlockPos(pos.m_123341_(), pos.m_123342_() - 1, pos.m_123343_()));
      nearBlocks.add(new BlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_() + 1));
      nearBlocks.add(new BlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_() - 1));

      for (BlockPos checkedPos : nearBlocks) {
         if (world.m_8055_(checkedPos).m_60819_().m_205070_(FluidTags.f_13131_)) {
            waterBlocks.add(checkedPos);
         }
      }

      return waterBlocks;
   }
}
