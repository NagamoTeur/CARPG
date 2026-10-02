package com.obscuria.aquamirae.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelCaptainCornelia;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class CaptainCorneliaRenderer extends MobRenderer<CaptainCornelia, ModelCaptainCornelia> {
   public CaptainCorneliaRenderer(Context context) {
      super(context, new ModelCaptainCornelia(context.m_174023_(AquamiraeLayers.CAPTAIN_CORNELIA)), 0.5F);
      this.m_115326_(new EyesLayer<CaptainCornelia, ModelCaptainCornelia>(this) {
         @NotNull
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("aquamirae", "textures/entity/captain_cornelia_overlay.png"));
         }
      });
      this.m_115326_(new CaptainCorneliaRenderer.CaptainCorneliaItemLayer(this));
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull CaptainCornelia entity) {
      return new ResourceLocation("aquamirae", "textures/entity/captain_cornelia.png");
   }

   @OnlyIn(Dist.CLIENT)
   public static class CaptainCorneliaItemLayer extends RenderLayer<CaptainCornelia, ModelCaptainCornelia> {
      public CaptainCorneliaItemLayer(RenderLayerParent<CaptainCornelia, ModelCaptainCornelia> layer) {
         super(layer);
      }

      public void render(
         @NotNull PoseStack pose, @NotNull MultiBufferSource source, int i1, CaptainCornelia entity, float f1, float f2, float f3, float f4, float f5, float f6
      ) {
         ItemStack right = entity.m_6844_(EquipmentSlot.MAINHAND);
         if (!right.m_41619_()) {
            pose.m_85836_();
            ((ModelCaptainCornelia)this.m_117386_()).translateToHand(HumanoidArm.RIGHT, pose);
            pose.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
            pose.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            pose.m_85837_(0.0, 0.1, 0.0);
            Minecraft.m_91087_().f_91063_.f_109055_.m_109322_(entity, right, TransformType.THIRD_PERSON_RIGHT_HAND, false, pose, source, i1);
            pose.m_85849_();
         }

         ItemStack left = entity.m_6844_(EquipmentSlot.OFFHAND);
         if (!left.m_41619_()) {
            pose.m_85836_();
            ((ModelCaptainCornelia)this.m_117386_()).translateToHand(HumanoidArm.LEFT, pose);
            pose.m_85845_(Vector3f.f_122223_.m_122240_(45.0F));
            pose.m_85837_(0.0, -0.15, -0.65);
            Minecraft.m_91087_().f_91063_.f_109055_.m_109322_(entity, left, TransformType.THIRD_PERSON_LEFT_HAND, false, pose, source, i1);
            pose.m_85849_();
         }
      }
   }
}
