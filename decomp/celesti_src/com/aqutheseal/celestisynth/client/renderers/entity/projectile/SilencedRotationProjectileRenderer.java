package com.aqutheseal.celestisynth.client.renderers.entity.projectile;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;

public class SilencedRotationProjectileRenderer<T extends Entity & IAnimatable> extends GeoProjectilesRenderer<T> {
   public SilencedRotationProjectileRenderer(Context renderManager, AnimatedGeoModel<T> modelProvider) {
      super(renderManager, modelProvider);
   }

   public void m_7392_(T animatable, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(animatable));
      this.dispatchedMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
      poseStack.m_85836_();
      AnimationEvent<T> predicate = new AnimationEvent(animatable, 0.0F, 0.0F, partialTick, false, Collections.singletonList(new EntityModelData()));
      this.modelProvider.setCustomAnimations(animatable, this.getInstanceId(animatable), predicate);
      RenderSystem.m_157456_(0, this.m_5478_(animatable));
      Color renderColor = this.getRenderColor(animatable, partialTick, poseStack, bufferSource, null, packedLight);
      RenderType renderType = this.getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLight, this.m_5478_(animatable));
      if (!animatable.m_20177_(Minecraft.m_91087_().f_91074_)) {
         this.render(
            model,
            animatable,
            partialTick,
            renderType,
            poseStack,
            bufferSource,
            null,
            packedLight,
            getPackedOverlay(animatable, 0.0F),
            (float)renderColor.getRed() / 255.0F,
            (float)renderColor.getGreen() / 255.0F,
            (float)renderColor.getBlue() / 255.0F,
            (float)renderColor.getAlpha() / 255.0F
         );
      }

      poseStack.m_85849_();
      RenderNameTagEvent renderNameTagEvent = new RenderNameTagEvent(animatable, animatable.m_5446_(), this, poseStack, bufferSource, packedLight, partialTick);
      MinecraftForge.EVENT_BUS.post(renderNameTagEvent);
      if (renderNameTagEvent.getResult() != Result.DENY && (renderNameTagEvent.getResult() == Result.ALLOW || this.m_6512_(animatable))) {
         this.m_7649_(animatable, renderNameTagEvent.getContent(), poseStack, bufferSource, packedLight);
      }
   }
}
