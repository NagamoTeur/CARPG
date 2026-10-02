package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.MageBlock;
import com.hollingsworth.arsnouveau.common.entity.AnimBlockSummon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class AnimBlockRenderer<BOBBY extends AnimBlockSummon> extends GeoEntityRenderer<BOBBY> {
   protected static final ResourceLocation TEXTURE = new ResourceLocation("ars_nouveau", "textures/entity/anim_block.png");
   public static final ResourceLocation BASE_MODEL = new ResourceLocation("ars_nouveau", "geo/animated_block.geo.json");
   public static final ResourceLocation ANIMATIONS = new ResourceLocation("ars_nouveau", "animations/animated_block_animations.json");
   private final BlockRenderDispatcher dispatcher;
   protected MultiBufferSource bufferSource;

   public AnimBlockRenderer(Context renderManager) {
      super(renderManager, new AnimatedGeoModel<BOBBY>() {
         public ResourceLocation getModelResource(AnimBlockSummon object) {
            return AnimBlockRenderer.BASE_MODEL;
         }

         public ResourceLocation getTextureResource(AnimBlockSummon object) {
            return AnimBlockRenderer.TEXTURE;
         }

         public ResourceLocation getAnimationResource(AnimBlockSummon animatable) {
            return AnimBlockRenderer.ANIMATIONS;
         }

         public void setCustomAnimations(BOBBY animatable, int instanceId, AnimationEvent customPredicate) {
            super.setCustomAnimations(animatable, instanceId, customPredicate);
            IBone head = this.getAnimationProcessor().getBone("block");
            head.setHidden(!(animatable.getBlockState().m_60734_() instanceof MageBlock));
         }
      });
      this.dispatcher = renderManager.m_234597_();
   }

   public void render(BOBBY animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.m_85836_();
      poseStack.m_85841_(0.8F, 0.8F, 0.8F);
      super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.m_85849_();
   }

   public void renderEarly(
      BOBBY animatable,
      PoseStack poseStack,
      float partialTick,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      super.renderEarly((LivingEntity)animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
      this.bufferSource = bufferSource;
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("block")) {
         AnimBlockSummon animBlock = this.animatable;
         if (animBlock == null) {
            return;
         }

         BlockState blockstate = this.animatable.getBlockState();
         if (!(blockstate.m_60734_() instanceof MageBlock)) {
            try {
               Level level = this.animatable.m_9236_();
               if (blockstate != level.m_8055_(animBlock.m_20183_()) && blockstate.m_60799_() != RenderShape.INVISIBLE) {
                  poseStack.m_85836_();
                  BlockPos blockpos = animBlock.m_20183_().m_7494_();
                  RenderUtils.translateToPivotPoint(poseStack, bone);
                  poseStack.m_85837_(-0.5, -0.5, -0.5);
                  BakedModel model = this.dispatcher.m_110910_(blockstate);

                  for (RenderType renderType : model.getRenderTypes(
                     blockstate, RandomSource.m_216335_(blockstate.m_60726_(animBlock.m_20183_())), ModelData.EMPTY
                  )) {
                     this.dispatcher
                        .m_110937_()
                        .tesselateBlock(
                           level,
                           model,
                           blockstate,
                           blockpos,
                           poseStack,
                           this.bufferSource.m_6299_(renderType),
                           false,
                           RandomSource.m_216327_(),
                           blockstate.m_60726_(animBlock.m_20097_()),
                           OverlayTexture.f_118083_,
                           ModelData.EMPTY,
                           renderType
                        );
                  }

                  poseStack.m_85849_();
                  buffer = this.bufferSource.m_6299_(RenderType.m_110458_(TEXTURE));
               }
            } catch (Exception var17) {
            }
         }
      }

      super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public Color getRenderColor(
      BOBBY animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight
   ) {
      if (animatable != null) {
         ParticleColor color = ParticleColor.fromInt(animatable.getColor());
         return Color.ofRGBA(color.toWrapper().r, color.toWrapper().g, color.toWrapper().b, 200);
      } else {
         return super.getRenderColor(animatable, partialTick, poseStack, bufferSource, buffer, packedLight);
      }
   }
}
