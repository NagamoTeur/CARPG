package com.aizistral.enigmaticlegacy.client.renderers;

import com.aizistral.enigmaticlegacy.api.items.IPermanentCrystal;
import com.aizistral.enigmaticlegacy.entities.PermanentItemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PermanentItemRenderer extends EntityRenderer<PermanentItemEntity> {
   private final ItemRenderer itemRenderer;
   private final Random random = new Random();

   public PermanentItemRenderer(Context renderManagerIn, ItemRenderer itemRendererIn) {
      super(renderManagerIn);
      this.itemRenderer = itemRendererIn;
      this.f_114477_ = 0.15F;
      this.f_114478_ = 0.75F;
   }

   protected int getModelCount(ItemStack stack) {
      int i = 1;
      if (stack.m_41613_() > 48) {
         i = 5;
      } else if (stack.m_41613_() > 32) {
         i = 4;
      } else if (stack.m_41613_() > 16) {
         i = 3;
      } else if (stack.m_41613_() > 1) {
         i = 2;
      }

      return i;
   }

   public void render(PermanentItemEntity entityIn, float entityYaw, float partialTicks, PoseStack PoseStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      if (Minecraft.m_91087_().f_91074_.m_6084_()
         || !(
            Math.sqrt(
                  entityIn.m_20275_(
                     Minecraft.m_91087_().f_91074_.m_20185_(), Minecraft.m_91087_().f_91074_.m_20188_(), Minecraft.m_91087_().f_91074_.m_20189_()
                  )
               )
               <= 1.0
         )) {
         PoseStackIn.m_85836_();
         ItemStack itemstack = entityIn.getItem();
         if (itemstack.m_41720_() instanceof IPermanentCrystal) {
            PoseStackIn.m_85841_(1.25F, 1.25F, 1.25F);
            PoseStackIn.m_85837_(0.0, -0.1125, 0.0);
         }

         int i = itemstack.m_41619_() ? 187 : Item.m_41393_(itemstack.m_41720_()) + itemstack.m_41773_();
         this.random.setSeed((long)i);
         BakedModel ibakedmodel = this.itemRenderer.m_174264_(itemstack, entityIn.f_19853_, null, entityIn.m_19879_());
         boolean flag = ibakedmodel.m_7539_();
         int j = this.getModelCount(itemstack);
         float f = 0.25F;
         float f1 = Mth.m_14031_(((float)entityIn.getAge() + partialTicks) / 10.0F + entityIn.hoverStart) * 0.1F + 0.1F;
         float f2 = this.shouldBob() ? ibakedmodel.m_7442_().m_111808_(TransformType.GROUND).f_111757_.m_122260_() : 0.0F;
         PoseStackIn.m_85837_(0.0, (double)(f1 + 0.25F * f2), 0.0);
         float f3 = entityIn.getItemHover(partialTicks);
         PoseStackIn.m_85845_(Vector3f.f_122225_.m_122270_(f3));
         if (!flag) {
            float f7 = -0.0F * (float)(j - 1) * 0.5F;
            float f8 = -0.0F * (float)(j - 1) * 0.5F;
            float f9 = -0.09375F * (float)(j - 1) * 0.5F;
            PoseStackIn.m_85837_((double)f7, (double)f8, (double)f9);
         }

         for (int k = 0; k < j; k++) {
            PoseStackIn.m_85836_();
            if (k > 0) {
               if (flag) {
                  float f11 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  float f13 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  PoseStackIn.m_85837_(
                     this.shouldSpreadItems() ? (double)f11 : 0.0, this.shouldSpreadItems() ? (double)f13 : 0.0, this.shouldSpreadItems() ? (double)f10 : 0.0
                  );
               } else {
                  float f12 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                  float f14 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                  PoseStackIn.m_85837_(this.shouldSpreadItems() ? (double)f12 : 0.0, this.shouldSpreadItems() ? (double)f14 : 0.0, 0.0);
               }
            }

            this.itemRenderer.m_115143_(itemstack, TransformType.GROUND, false, PoseStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_, ibakedmodel);
            PoseStackIn.m_85849_();
            if (!flag) {
               PoseStackIn.m_85837_(0.0, 0.0, 0.09375);
            }
         }

         PoseStackIn.m_85849_();
         super.m_7392_(entityIn, entityYaw, partialTicks, PoseStackIn, bufferIn, packedLightIn);
      }
   }

   public ResourceLocation getTextureLocation(PermanentItemEntity entity) {
      return TextureAtlas.f_118259_;
   }

   public boolean shouldSpreadItems() {
      return true;
   }

   public boolean shouldBob() {
      return true;
   }
}
