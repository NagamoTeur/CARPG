package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelSeal;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerSealItem;
import com.github.alexthe666.alexsmobs.entity.EntitySeal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderSeal extends MobRenderer<EntitySeal, ModelSeal> {
   private static final ResourceLocation TEXTURE_BROWN_0 = new ResourceLocation("alexsmobs:textures/entity/seal/seal_brown_0.png");
   private static final ResourceLocation TEXTURE_BROWN_1 = new ResourceLocation("alexsmobs:textures/entity/seal/seal_brown_1.png");
   private static final ResourceLocation TEXTURE_ARCTIC_0 = new ResourceLocation("alexsmobs:textures/entity/seal/seal_arctic_0.png");
   private static final ResourceLocation TEXTURE_ARCTIC_1 = new ResourceLocation("alexsmobs:textures/entity/seal/seal_arctic_1.png");
   private static final ResourceLocation TEXTURE_ARCTIC_BABY = new ResourceLocation("alexsmobs:textures/entity/seal/seal_arctic_baby.png");
   private static final ResourceLocation TEXTURE_TEARS = new ResourceLocation("alexsmobs:textures/entity/seal/seal_crying.png");
   private static final ResourceLocation TEXTURE_TONGUE = new ResourceLocation("alexsmobs:textures/entity/seal/seal_tongue.png");

   public RenderSeal(Context renderManagerIn) {
      super(renderManagerIn, new ModelSeal(), 0.45F);
      this.m_115326_(new LayerSealItem(this));
      this.m_115326_(new RenderSeal.SealTearsLayer(this));
   }

   protected boolean shouldShowName(EntitySeal seal) {
      return super.m_6512_(seal) || seal.isTearsEasterEgg();
   }

   protected void scale(EntitySeal entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.3F, 1.3F, 1.3F);
   }

   public ResourceLocation getTextureLocation(EntitySeal entity) {
      if (entity.isArctic()) {
         return entity.m_6162_() ? TEXTURE_ARCTIC_BABY : (entity.getVariant() == 1 ? TEXTURE_ARCTIC_1 : TEXTURE_ARCTIC_0);
      } else {
         return entity.getVariant() == 1 ? TEXTURE_BROWN_1 : TEXTURE_BROWN_0;
      }
   }

   protected void renderNameTag(EntitySeal seal, Component text, PoseStack poseStack, MultiBufferSource bufferSrc, int numberIn) {
      if (seal.isTearsEasterEgg()) {
         double d0 = this.f_114476_.m_114471_(seal);
         if (ForgeHooksClient.isNameplateInRenderDistance(seal, d0)) {
            boolean flag = !seal.m_20163_();
            float f = seal.m_20206_() + 0.5F;
            String[] split = text.m_130668_(512).split(" ");
            StringBuilder recombined = new StringBuilder();
            List<String> strings = new ArrayList<>();

            for (int wordIndex = 0; wordIndex < split.length; wordIndex++) {
               recombined.append(split[wordIndex]).append(" ");
               if (recombined.length() > 15 || wordIndex == split.length - 1) {
                  strings.add(recombined.toString());
                  recombined = new StringBuilder();
               }
            }

            int i = 10 - 10 * strings.size();
            poseStack.m_85836_();
            poseStack.m_85837_(0.0, (double)f, 0.0);
            poseStack.m_85845_(this.f_114476_.m_114470_());
            poseStack.m_85841_(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = poseStack.m_85850_().m_85861_();
            float f1 = 1.0F;
            int j = -1;
            Font font = this.m_114481_();
            String widest = "";

            for (String print : strings) {
               if (font.m_92895_(widest) < font.m_92895_(print)) {
                  widest = print;
               }
            }

            float widestCenter = (float)(-font.m_92895_(widest) / 2);

            for (String printx : strings) {
               float f2 = (float)(-font.m_92895_(printx) / 2);
               poseStack.m_85837_(0.0, 0.0, 0.1);
               font.m_92811_(widest, widestCenter, (float)i, j, false, matrix4f, bufferSrc, false, j, 240);
               poseStack.m_85837_(0.0, 0.0, -0.1);
               font.m_92811_(printx, f2, (float)i, 1, false, matrix4f, bufferSrc, false, j, 240);
               font.m_92811_(printx, f2, (float)i, 0, false, matrix4f, bufferSrc, false, 0, numberIn);
               i += 10;
            }

            poseStack.m_85849_();
         }
      } else {
         super.m_7649_(seal, text, poseStack, bufferSrc, numberIn);
      }
   }

   class SealTearsLayer extends RenderLayer<EntitySeal, ModelSeal> {
      public SealTearsLayer(RenderSeal p_i50928_1_) {
         super(p_i50928_1_);
      }

      public void render(
         PoseStack matrixStackIn,
         MultiBufferSource bufferIn,
         int packedLightIn,
         EntitySeal entitylivingbaseIn,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (entitylivingbaseIn.isTearsEasterEgg()) {
            VertexConsumer lead = bufferIn.m_6299_(AMRenderTypes.m_110458_(RenderSeal.TEXTURE_TEARS));
            ((ModelSeal)this.m_117386_())
               .m_7695_(matrixStackIn, lead, packedLightIn, LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }
}
