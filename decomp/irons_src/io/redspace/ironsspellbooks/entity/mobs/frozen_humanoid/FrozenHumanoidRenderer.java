package io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.client.event.RenderLivingEvent.Post;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.common.MinecraftForge;

public class FrozenHumanoidRenderer extends LivingEntityRenderer<FrozenHumanoid, HumanoidModel<FrozenHumanoid>> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/frozen_humanoid.png");

   public FrozenHumanoidRenderer(Context context) {
      super(context, new HumanoidModel(context.m_174023_(ModelLayers.f_171162_)), 0.36F);
   }

   public ResourceLocation getTextureLocation(FrozenHumanoid pEntity) {
      return TEXTURE;
   }

   protected float getBob(FrozenHumanoid pLivingBase, float pPartialTick) {
      return 0.0F;
   }

   public void render(FrozenHumanoid iceMan, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      if (!MinecraftForge.EVENT_BUS.post(new Pre(iceMan, this, pPartialTicks, pMatrixStack, pBuffer, pPackedLight))) {
         pMatrixStack.m_85836_();
         ((HumanoidModel)this.f_115290_).f_102608_ = this.getAttackAnim(iceMan, pPartialTicks);
         boolean shouldSit = iceMan.isSitting();
         ((HumanoidModel)this.f_115290_).f_102609_ = shouldSit;
         ((HumanoidModel)this.f_115290_).f_102610_ = iceMan.m_6162_();
         float bodyYRot = iceMan.f_20883_;
         float yHeadRot = iceMan.f_20885_;
         float f2 = yHeadRot - bodyYRot;
         if (shouldSit) {
            f2 = yHeadRot - bodyYRot;
            float f3 = Mth.m_14177_(f2);
            if (f3 < -85.0F) {
               f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
               f3 = 85.0F;
            }

            bodyYRot = yHeadRot - f3;
            if (f3 * f3 > 2500.0F) {
               bodyYRot += f3 * 0.2F;
            }

            f2 = yHeadRot - bodyYRot;
         }

         float f6 = Mth.m_14179_(pPartialTicks, iceMan.f_19860_, iceMan.m_146909_());
         if (m_194453_(iceMan)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
         }

         if (iceMan.m_217003_(Pose.SLEEPING)) {
            Direction direction = iceMan.m_21259_();
            if (direction != null) {
               float f4 = iceMan.m_20236_(Pose.STANDING) - 0.1F;
               pMatrixStack.m_85837_((double)((float)(-direction.m_122429_()) * f4), 0.0, (double)((float)(-direction.m_122431_()) * f4));
            }
         }

         float bob = 0.0F;
         this.m_7523_(iceMan, pMatrixStack, bob, bodyYRot, pPartialTicks);
         pMatrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         this.m_7546_(iceMan, pMatrixStack, pPartialTicks);
         pMatrixStack.m_85837_(0.0, -1.501F, 0.0);
         float limbSwingAmount = iceMan.getLimbSwingAmount();
         float limbSwing = iceMan.getLimbSwing();
         ((HumanoidModel)this.f_115290_).m_6839_(iceMan, limbSwing, limbSwingAmount, pPartialTicks);
         ((HumanoidModel)this.f_115290_).m_6973_(iceMan, limbSwing, limbSwingAmount, bob, f2, f6);
         Minecraft minecraft = Minecraft.m_91087_();
         boolean flag = this.m_5933_(iceMan);
         boolean flag1 = !flag && !iceMan.m_20177_(minecraft.f_91074_);
         boolean flag2 = minecraft.m_91314_(iceMan);
         RenderType rendertype = this.m_7225_(iceMan, flag, flag1, flag2);
         if (rendertype != null) {
            VertexConsumer vertexconsumer = pBuffer.m_6299_(rendertype);
            int i = m_115338_(iceMan, this.m_6931_(iceMan, pPartialTicks));
            ((HumanoidModel)this.f_115290_).m_7695_(pMatrixStack, vertexconsumer, pPackedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
         }

         pMatrixStack.m_85849_();
         MinecraftForge.EVENT_BUS.post(new Post(iceMan, this, pPartialTicks, pMatrixStack, pBuffer, pPackedLight));
      }
   }

   protected float getAttackAnim(FrozenHumanoid pLivingBase, float pPartialTickTime) {
      return pLivingBase.getAttacktime();
   }
}
