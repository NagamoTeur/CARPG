package com.obscuria.aquamirae.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMaw;
import com.obscuria.aquamirae.common.entities.Maw;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class MawRenderer extends MobRenderer<Maw, ModelMaw> {
   public MawRenderer(Context context) {
      super(context, new ModelMaw(context.m_174023_(AquamiraeLayers.MAW)), 0.9F);
      this.m_115326_(new EyesLayer<Maw, ModelMaw>(this) {
         @NotNull
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("aquamirae", "textures/entity/maw_overlay.png"));
         }
      });
      this.m_115326_(new MawRenderer.MawItemLayer(this));
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull Maw entity) {
      return new ResourceLocation("aquamirae", "textures/entity/maw.png");
   }

   @OnlyIn(Dist.CLIENT)
   public static class MawItemLayer extends RenderLayer<Maw, ModelMaw> {
      public MawItemLayer(RenderLayerParent<Maw, ModelMaw> layer) {
         super(layer);
      }

      public void render(
         @NotNull PoseStack pose, @NotNull MultiBufferSource source, int i1, Maw maw, float f1, float f2, float f3, float f4, float f5, float f6
      ) {
         if (!maw.getItemInMouth().m_41619_()) {
            pose.m_85836_();
            ((ModelMaw)this.m_117386_()).translate(pose);
            pose.m_85845_(Vector3f.f_122223_.m_122240_(100.0F));
            pose.m_85845_(Vector3f.f_122227_.m_122240_(0.0F));
            pose.m_85837_(0.0, -0.8, 0.02);
            pose.m_85841_(0.7F, 0.7F, 0.7F);
            Minecraft.m_91087_().f_91063_.f_109055_.m_109322_(maw, maw.getItemInMouth(), TransformType.FIXED, false, pose, source, i1);
            pose.m_85849_();
         }
      }
   }
}
