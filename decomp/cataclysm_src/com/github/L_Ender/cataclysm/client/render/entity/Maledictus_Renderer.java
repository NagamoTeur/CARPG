package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Maledictus_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.layer.MaledictusRiderLayer;
import com.github.L_Ender.cataclysm.client.render.layer.Maledictus_Circle_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.Maledictus_Layer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.client.event.RenderLivingEvent.Post;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;

@OnlyIn(Dist.CLIENT)
public class Maledictus_Renderer extends MobRenderer<Maledictus_Entity, Maledictus_Model> {
   private static final ResourceLocation MALEDICTUS_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/maledictus/maledictus_ghost.png");

   public Maledictus_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Maledictus_Model(renderManagerIn.m_174023_(CMModelLayers.MALEDICTUS_MODEL)), 0.75F);
      this.m_115326_(new Maledictus_Layer(this));
      this.m_115326_(new Maledictus_Circle_Layer(this, renderManagerIn));
      this.m_115326_(new MaledictusRiderLayer(this));
   }

   public ResourceLocation getTextureLocation(Maledictus_Entity entity) {
      return MALEDICTUS_TEXTURES;
   }

   protected float getFlipDegrees(Maledictus_Entity entity) {
      return 0.0F;
   }

   public void render(Maledictus_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      if (!MinecraftForge.EVENT_BUS.post(new Pre(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) {
         matrixStackIn.m_85836_();
         ((Maledictus_Model)this.f_115290_).f_102608_ = this.m_115342_(entityIn, partialTicks);
         boolean shouldSit = entityIn.m_20159_() && entityIn.m_20202_() != null && entityIn.m_20202_().shouldRiderSit();
         ((Maledictus_Model)this.f_115290_).f_102609_ = shouldSit;
         ((Maledictus_Model)this.f_115290_).f_102610_ = entityIn.m_6162_();
         float f = Mth.m_14189_(partialTicks, entityIn.f_20884_, entityIn.f_20883_);
         float f1 = Mth.m_14189_(partialTicks, entityIn.f_20886_, entityIn.f_20885_);
         float f2 = f1 - f;
         if (shouldSit && entityIn.m_20202_() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entityIn.m_20202_();
            f = Mth.m_14189_(partialTicks, livingentity.f_20884_, livingentity.f_20883_);
            f2 = f1 - f;
            float f3 = Mth.m_14177_(f2);
            if (f3 < -85.0F) {
               f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
               f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
               f += f3 * 0.2F;
            }

            f2 = f1 - f;
         }

         float f6 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
         if (entityIn.m_20089_() == Pose.SLEEPING) {
            Direction direction = entityIn.m_21259_();
            if (direction != null) {
               float f4 = entityIn.m_20236_(Pose.STANDING) - 0.1F;
               matrixStackIn.m_85837_((double)((float)(-direction.m_122429_()) * f4), 0.0, (double)((float)(-direction.m_122431_()) * f4));
            }
         }

         float f7 = this.m_6930_(entityIn, partialTicks);
         this.m_7523_(entityIn, matrixStackIn, f7, f, partialTicks);
         matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
         this.scale(entityIn, matrixStackIn, partialTicks);
         matrixStackIn.m_85837_(0.0, -1.501F, 0.0);
         float f8 = 0.0F;
         float f5 = 0.0F;
         if (!shouldSit && entityIn.m_6084_()) {
            f8 = Mth.m_14179_(partialTicks, entityIn.f_20923_, entityIn.f_20924_);
            f5 = entityIn.f_20925_ - entityIn.f_20924_ * (1.0F - partialTicks);
            if (entityIn.m_6162_()) {
               f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
               f8 = 1.0F;
            }
         }

         ((Maledictus_Model)this.f_115290_).m_6839_(entityIn, f5, f8, partialTicks);
         ((Maledictus_Model)this.f_115290_).setupAnim(entityIn, f5, f8, f7, f2, f6);
         Minecraft minecraft = Minecraft.m_91087_();
         boolean flag = this.m_5933_(entityIn);
         boolean flag1 = !flag && !entityIn.m_20177_(minecraft.f_91074_);
         boolean flag2 = minecraft.m_91314_(entityIn);
         RenderType rendertype = this.getRenderType(entityIn, flag, flag1, flag2);
         if (rendertype != null) {
            float hide = entityIn.m_21223_() / entityIn.m_21233_() - 0.4F;
            float alpha = (1.0F - hide) * 0.6F;
            int i = m_115338_(entityIn, this.m_6931_(entityIn, partialTicks));
            this.renderMaledictusModel(
               matrixStackIn, bufferIn, rendertype, partialTicks, packedLightIn, flag1 ? 0.15F : Mth.m_14036_(alpha, 0.0F, 1.0F), entityIn
            );
         }

         if (!entityIn.m_5833_()) {
            for (RenderLayer layerrenderer : this.f_115291_) {
               layerrenderer.m_6494_(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
            }
         }

         matrixStackIn.m_85849_();
         RenderNameTagEvent renderNameplateEvent = new RenderNameTagEvent(
            entityIn, entityIn.m_5446_(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks
         );
         MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
         if (renderNameplateEvent.getResult() != Result.DENY && (renderNameplateEvent.getResult() == Result.ALLOW || this.m_6512_(entityIn))) {
            this.m_7649_(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
         }

         MinecraftForge.EVENT_BUS.post(new Post(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));
      }
   }

   private void renderMaledictusModel(
      PoseStack matrixStackIn,
      MultiBufferSource source,
      RenderType defRenderType,
      float partialTicks,
      int packedLightIn,
      float alphaIn,
      Maledictus_Entity entityIn
   ) {
      boolean hurt = Math.max(entityIn.f_20916_, entityIn.f_20919_) > 0;
      ((Maledictus_Model)this.f_115290_)
         .m_7695_(
            matrixStackIn,
            source.m_6299_(defRenderType),
            packedLightIn,
            LivingEntityRenderer.m_115338_(entityIn, this.m_6931_(entityIn, partialTicks)),
            hurt ? 0.4F : 1.0F,
            hurt ? 0.8F : 1.0F,
            hurt ? 0.7F : 1.0F,
            alphaIn
         );
   }

   @Nullable
   protected RenderType getRenderType(Maledictus_Entity maledictus, boolean normal, boolean invis, boolean outline) {
      ResourceLocation resourcelocation = this.getTextureLocation(maledictus);
      return outline ? RenderType.m_110491_(resourcelocation) : CMRenderTypes.getGhost(resourcelocation);
   }

   protected void scale(Maledictus_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
   }
}
