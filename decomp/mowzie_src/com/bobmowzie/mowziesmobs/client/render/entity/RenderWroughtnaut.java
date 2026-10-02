package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelWroughtnaut;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ItemLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.WroughtnautEyesLayer;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWroughtnaut extends MobRenderer<EntityWroughtnaut, ModelWroughtnaut<EntityWroughtnaut>> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/wroughtnaut.png");

   public RenderWroughtnaut(Context mgr) {
      super(mgr, new ModelWroughtnaut(), 1.0F);
      this.m_115326_(new WroughtnautEyesLayer(this));
      this.m_115326_(new ItemLayer(this, ((ModelWroughtnaut)this.m_7200_()).sword, Items.f_42388_.m_7968_(), TransformType.GROUND));
   }

   public void render(EntityWroughtnaut p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
      super.m_7392_(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
      if (this.f_114476_.m_114377_() && !p_115455_.m_20145_() && !Minecraft.m_91087_().m_91299_()) {
         Vec3 forward = p_115455_.m_20156_();
         Vec3 bodyFacing = Vec3.m_82498_(0.0F, p_115455_.f_20883_);
         Matrix4f matrix4f = p_115458_.m_85850_().m_85861_();
         Matrix3f matrix3f = p_115458_.m_85850_().m_85864_();
         VertexConsumer consumer = p_115459_.m_6299_(RenderType.m_110504_());
         consumer.m_85982_(matrix4f, 0.0F, p_115455_.m_20192_() + 0.1F, 0.0F)
            .m_6122_(0, 255, 255, 255)
            .m_85977_(matrix3f, (float)forward.f_82479_, (float)forward.f_82480_, (float)forward.f_82481_)
            .m_5752_();
         consumer.m_85982_(
               matrix4f,
               (float)(forward.f_82479_ * 2.0),
               (float)((double)p_115455_.m_20192_() + 0.1F + forward.f_82480_ * 2.0),
               (float)(forward.f_82481_ * 2.0)
            )
            .m_6122_(0, 255, 255, 255)
            .m_85977_(matrix3f, (float)forward.f_82479_, (float)forward.f_82480_, (float)forward.f_82481_)
            .m_5752_();
         consumer.m_85982_(matrix4f, 0.0F, p_115455_.m_20192_() + 0.2F, 0.0F)
            .m_6122_(255, 0, 255, 255)
            .m_85977_(matrix3f, (float)bodyFacing.f_82479_, (float)bodyFacing.f_82480_, (float)bodyFacing.f_82481_)
            .m_5752_();
         consumer.m_85982_(
               matrix4f,
               (float)(bodyFacing.f_82479_ * 2.0),
               (float)((double)p_115455_.m_20192_() + 0.2F + bodyFacing.f_82480_ * 2.0),
               (float)(bodyFacing.f_82481_ * 2.0)
            )
            .m_6122_(255, 0, 255, 255)
            .m_85977_(matrix3f, (float)bodyFacing.f_82479_, (float)bodyFacing.f_82480_, (float)bodyFacing.f_82481_)
            .m_5752_();
      }
   }

   protected float getFlipDegrees(EntityWroughtnaut entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityWroughtnaut entity) {
      return TEXTURE;
   }
}
