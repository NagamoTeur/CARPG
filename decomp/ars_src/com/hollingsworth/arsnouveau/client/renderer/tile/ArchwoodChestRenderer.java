package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Calendar;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner.Combiner;
import net.minecraft.world.level.block.DoubleBlockCombiner.NeighborCombineResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ArchwoodChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
   private final ModelPart lid;
   private final ModelPart bottom;
   private final ModelPart lock;
   private final ModelPart doubleLeftLid;
   private final ModelPart doubleLeftBottom;
   private final ModelPart doubleLeftLock;
   private final ModelPart doubleRightLid;
   private final ModelPart doubleRightBottom;
   private final ModelPart doubleRightLock;
   private boolean xmasTextures;
   public static Block invBlock = null;

   public ArchwoodChestRenderer(Context context) {
      Calendar calendar = Calendar.getInstance();
      if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
         this.xmasTextures = true;
      }

      ModelPart modelpart = context.m_173582_(ModelLayers.f_171275_);
      this.bottom = modelpart.m_171324_("bottom");
      this.lid = modelpart.m_171324_("lid");
      this.lock = modelpart.m_171324_("lock");
      ModelPart modelpart1 = context.m_173582_(ModelLayers.f_171133_);
      this.doubleLeftBottom = modelpart1.m_171324_("bottom");
      this.doubleLeftLid = modelpart1.m_171324_("lid");
      this.doubleLeftLock = modelpart1.m_171324_("lock");
      ModelPart modelpart2 = context.m_173582_(ModelLayers.f_171134_);
      this.doubleRightBottom = modelpart2.m_171324_("bottom");
      this.doubleRightLid = modelpart2.m_171324_("lid");
      this.doubleRightLock = modelpart2.m_171324_("lock");
   }

   public void m_6922_(T tileEntity, float p_225616_2_, PoseStack ms, MultiBufferSource buffer, int p_225616_5_, int p_225616_6_) {
      Level world = tileEntity.m_58904_();
      boolean flag = world != null;
      BlockState blockstate = flag ? tileEntity.m_58900_() : (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, Direction.SOUTH);
      ChestType chesttype = blockstate.m_61138_(ChestBlock.f_51479_) ? (ChestType)blockstate.m_61143_(ChestBlock.f_51479_) : ChestType.SINGLE;
      if (blockstate.m_60734_() instanceof AbstractChestBlock<?> abstractchestblock) {
         boolean flag1 = chesttype != ChestType.SINGLE;
         ms.m_85836_();
         float f = ((Direction)blockstate.m_61143_(ChestBlock.f_51478_)).m_122435_();
         ms.m_85837_(0.5, 0.5, 0.5);
         ms.m_85845_(Vector3f.f_122225_.m_122240_(-f));
         ms.m_85837_(-0.5, -0.5, -0.5);
         NeighborCombineResult<? extends ChestBlockEntity> icallbackwrapper;
         if (flag) {
            icallbackwrapper = abstractchestblock.m_5641_(blockstate, world, tileEntity.m_58899_(), true);
         } else {
            icallbackwrapper = Combiner::m_6502_;
         }

         float f1 = ((Float2FloatFunction)icallbackwrapper.m_5649_(ChestBlock.m_51517_(tileEntity))).get(p_225616_2_);
         f1 = 1.0F - f1;
         f1 = 1.0F - f1 * f1 * f1;
         int i = ((Int2IntFunction)icallbackwrapper.m_5649_(new BrightnessCombiner())).applyAsInt(p_225616_5_);
         Material rendermaterial = this.getMaterial(tileEntity, chesttype);
         VertexConsumer ivertexbuilder = rendermaterial.m_119194_(buffer, RenderType::m_110452_);
         if (flag1) {
            if (chesttype == ChestType.RIGHT) {
               this.render(ms, ivertexbuilder, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, f1, i, p_225616_6_);
            } else {
               this.render(ms, ivertexbuilder, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, f1, i, p_225616_6_);
            }
         } else {
            this.render(ms, ivertexbuilder, this.lid, this.lock, this.bottom, f1, i, p_225616_6_);
         }

         ms.m_85849_();
      }
   }

   private void render(
      PoseStack p_228871_1_,
      VertexConsumer p_228871_2_,
      ModelPart p_228871_3_,
      ModelPart p_228871_4_,
      ModelPart p_228871_5_,
      float p_228871_6_,
      int p_228871_7_,
      int p_228871_8_
   ) {
      p_228871_3_.f_104203_ = -(p_228871_6_ * (float) (Math.PI / 2));
      p_228871_4_.f_104203_ = p_228871_3_.f_104203_;
      p_228871_3_.m_104301_(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
      p_228871_4_.m_104301_(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
      p_228871_5_.m_104301_(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
   }

   protected Material getMaterial(T tileEntity, ChestType chestType) {
      switch (chestType) {
         case LEFT:
            return new Material(Sheets.f_110740_, new ResourceLocation("ars_nouveau", "entity/archwood_chest_left"));
         case RIGHT:
            return new Material(Sheets.f_110740_, new ResourceLocation("ars_nouveau", "entity/archwood_chest_right"));
         case SINGLE:
         default:
            return new Material(Sheets.f_110740_, new ResourceLocation("ars_nouveau", "entity/archwood_chest"));
      }
   }
}
