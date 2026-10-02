package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.ScribesBlock;
import com.hollingsworth.arsnouveau.common.block.ThreePartBlock;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class ScribesRenderer extends ArsGeoBlockRenderer<ScribesTile> {
   public static AnimatedGeoModel model = new GenericModel("scribes_table");

   public ScribesRenderer(Context rendererDispatcherIn) {
      super(rendererDispatcherIn, model);
   }

   public void renderEarly(
      ScribesTile tile,
      PoseStack matrixStack,
      float ticks,
      MultiBufferSource iRenderTypeBuffer,
      VertexConsumer bufferIn,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      try {
         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_60734_() != BlockRegistry.SCRIBES_BLOCK) {
            return;
         }

         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_61143_(ScribesBlock.PART) != ThreePartBlock.HEAD) {
            return;
         }

         if (tile.getStack() == null) {
            return;
         }

         double x = (double)tile.m_58899_().m_123341_();
         double y = (double)tile.m_58899_().m_123342_();
         double z = (double)tile.m_58899_().m_123343_();
         this.renderPressedItem(
            tile,
            tile.crafting ? (tile.craftingTicks < 40 ? tile.recipe.output.m_41720_().m_7968_() : ItemsRegistry.BLANK_GLYPH.get().m_7968_()) : tile.getStack(),
            matrixStack,
            iRenderTypeBuffer,
            packedLightIn,
            packedOverlayIn,
            ticks + partialTicks
         );
      } catch (Throwable var18) {
         var18.printStackTrace();
      }
   }

   public void render(ScribesTile tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      try {
         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_60734_() != BlockRegistry.SCRIBES_BLOCK) {
            return;
         }

         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_61143_(ScribesBlock.PART) != ThreePartBlock.HEAD) {
            return;
         }

         Direction direction = (Direction)tile.m_58904_().m_8055_(tile.m_58899_()).m_61143_(ScribesBlock.FACING);
         stack.m_85836_();
         if (direction == Direction.NORTH) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(-90.0F));
            stack.m_85837_(1.0, 0.0, -1.0);
         }

         if (direction == Direction.SOUTH) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
            stack.m_85837_(-1.0, 0.0, -1.0);
         }

         if (direction == Direction.WEST) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
            stack.m_85837_(0.0, 0.0, -2.0);
         }

         if (direction == Direction.EAST) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(-90.0F));
            stack.m_85837_(0.0, 0.0, 0.0);
         }

         super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
         stack.m_85849_();
      } catch (Throwable var7) {
         var7.printStackTrace();
      }
   }

   public void renderPressedItem(
      ScribesTile tile,
      ItemStack itemToRender,
      PoseStack matrixStack,
      MultiBufferSource iRenderTypeBuffer,
      int packedLight,
      int packedOverlay,
      float partialTicks
   ) {
      Direction direction = (Direction)tile.m_58904_().m_8055_(tile.m_58899_()).m_61143_(ScribesBlock.FACING);
      matrixStack.m_85836_();
      matrixStack.m_85837_(0.0, 1.0, 0.0);
      BlockState state = tile.m_58904_().m_8055_(tile.m_58899_());
      if (state.m_60734_() instanceof ScribesBlock) {
         float y = ((Direction)state.m_61143_(ScribesBlock.FACING)).m_122427_().m_122435_();
         matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(-y + 90.0F));
         matrixStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         matrixStack.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         if (direction == Direction.WEST) {
            matrixStack.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
         }

         if (direction == Direction.EAST) {
            matrixStack.m_85845_(Vector3f.f_122227_.m_122240_(-90.0F));
         }

         if (direction == Direction.SOUTH) {
            matrixStack.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         }

         matrixStack.m_85837_(-0.7, 0.0, 0.0);
         matrixStack.m_85841_(0.6F, 0.6F, 0.6F);
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(itemToRender, TransformType.FIXED, packedLight, packedOverlay, matrixStack, iRenderTypeBuffer, (int)tile.m_58899_().m_121878_());
         matrixStack.m_85849_();
         if (tile.recipe != null && !tile.crafting) {
            List<Ingredient> inputs = tile.getRemainingRequired();
            float ticks = partialTicks + (float)ClientInfo.ticksInGame;
            float angleBetweenEach = 360.0F / (float)inputs.size();
            Vec3 distanceVec = new Vec3(1.0, -0.5, 1.0);

            for (int i = 0; i < inputs.size(); i++) {
               Ingredient ingredient = inputs.get(i);
               ItemStack stack = ingredient.m_43908_()[ClientInfo.ticksInGame / 20 % ingredient.m_43908_().length];
               matrixStack.m_85836_();
               matrixStack.m_85837_(-0.5, 2.0, 0.0);
               matrixStack.m_85841_(0.25F, 0.25F, 0.25F);
               matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(ticks + (float)i * angleBetweenEach));
               matrixStack.m_85837_(
                  distanceVec.m_7096_(),
                  distanceVec.m_7098_() + (double)((float)(i % 2 == 0 ? -i : i) * Mth.m_14031_(ticks / 60.0F)) * 0.0625,
                  distanceVec.m_7094_()
               );
               matrixStack.m_85845_((i % 2 == 0 ? Vector3f.f_122227_ : Vector3f.f_122223_).m_122240_(ticks));
               RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 0.5F);
               Minecraft.m_91087_()
                  .m_91291_()
                  .m_174269_(stack, TransformType.FIXED, packedLight, packedOverlay, matrixStack, iRenderTypeBuffer, (int)tile.m_58899_().m_121878_());
               matrixStack.m_85849_();
            }
         }
      }
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model);
   }

   public RenderType getRenderType(
      ScribesTile animatable,
      float partialTicks,
      PoseStack stack,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110473_(textureLocation);
   }

   public boolean m_5932_(BlockEntity p_188185_1_) {
      return false;
   }
}
