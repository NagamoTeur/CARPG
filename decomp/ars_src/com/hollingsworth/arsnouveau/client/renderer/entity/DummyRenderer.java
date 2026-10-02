package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.EntityDummy;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;

public class DummyRenderer extends LivingEntityRenderer<EntityDummy, PlayerModel<EntityDummy>> {
   private final PlayerModel<EntityDummy> playerModel;
   private final PlayerModel<EntityDummy> playerModelSlim;

   public DummyRenderer(Context p_i46102_1_) {
      this(p_i46102_1_, false);
   }

   public ResourceLocation getTextureLocation(EntityDummy p_110775_1_) {
      return p_110775_1_.getSkinTextureLocation();
   }

   public DummyRenderer(Context context, boolean slim) {
      super(context, new PlayerModel(context.m_174023_(slim ? ModelLayers.f_171166_ : ModelLayers.f_171162_), slim), 0.5F);
      this.playerModel = new PlayerModel(context.m_174023_(ModelLayers.f_171162_), false);
      this.playerModelSlim = new PlayerModel(context.m_174023_(ModelLayers.f_171166_), true);
      this.m_115326_(
         new HumanoidArmorLayer(this, new HumanoidModel(context.m_174023_(ModelLayers.f_171164_)), new HumanoidModel(context.m_174023_(ModelLayers.f_171165_)))
      );
      this.m_115326_(new ItemInHandLayer(this, context.m_234598_()));
      this.m_115326_(new ArrowLayer(context, this));
      this.m_115326_(new CustomHeadLayer(this, context.m_174027_(), context.m_234598_()));
      this.m_115326_(new ElytraLayer(this, context.m_174027_()));
   }

   public void render(EntityDummy p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {
      this.setModelProperties(p_225623_1_);
      super.m_7392_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   public Vec3 getRenderOffset(EntityDummy p_225627_1_, float p_225627_2_) {
      return p_225627_1_.m_6047_() ? new Vec3(0.0, -0.125, 0.0) : super.m_7860_(p_225627_1_, p_225627_2_);
   }

   private void setModelProperties(EntityDummy pEntityDummy) {
      if (pEntityDummy.isSlim()) {
         this.f_115290_ = this.playerModelSlim;
      } else {
         this.f_115290_ = this.playerModel;
      }

      PlayerModel<EntityDummy> playermodel = (PlayerModel<EntityDummy>)this.m_7200_();
      if (pEntityDummy.m_5833_()) {
         playermodel.m_8009_(false);
         playermodel.f_102808_.f_104207_ = true;
         playermodel.f_102809_.f_104207_ = true;
      } else {
         playermodel.m_8009_(true);
         playermodel.f_102817_ = pEntityDummy.m_6047_();
         ArmPose bipedmodel$armpose = getArmPose(pEntityDummy, InteractionHand.MAIN_HAND);
         ArmPose bipedmodel$armpose1 = getArmPose(pEntityDummy, InteractionHand.OFF_HAND);
         if (bipedmodel$armpose.m_102897_()) {
            bipedmodel$armpose1 = pEntityDummy.m_21206_().m_41619_() ? ArmPose.EMPTY : ArmPose.ITEM;
         }

         if (pEntityDummy.m_5737_() == HumanoidArm.RIGHT) {
            playermodel.f_102816_ = bipedmodel$armpose;
            playermodel.f_102815_ = bipedmodel$armpose1;
         } else {
            playermodel.f_102816_ = bipedmodel$armpose1;
            playermodel.f_102815_ = bipedmodel$armpose;
         }
      }
   }

   private static ArmPose getArmPose(EntityDummy pEntityDummy, InteractionHand p_241741_1_) {
      ItemStack itemstack = pEntityDummy.m_21120_(p_241741_1_);
      if (itemstack.m_41619_()) {
         return ArmPose.EMPTY;
      } else {
         if (pEntityDummy.m_7655_() == p_241741_1_ && pEntityDummy.m_21212_() > 0) {
            UseAnim useaction = itemstack.m_41780_();
            if (useaction == UseAnim.BLOCK) {
               return ArmPose.BLOCK;
            }

            if (useaction == UseAnim.BOW) {
               return ArmPose.BOW_AND_ARROW;
            }

            if (useaction == UseAnim.SPEAR) {
               return ArmPose.THROW_SPEAR;
            }

            if (useaction == UseAnim.CROSSBOW && p_241741_1_ == pEntityDummy.m_7655_()) {
               return ArmPose.CROSSBOW_CHARGE;
            }
         } else if (!pEntityDummy.f_20911_ && itemstack.m_41720_() == Items.f_42717_ && CrossbowItem.m_40932_(itemstack)) {
            return ArmPose.CROSSBOW_HOLD;
         }

         return ArmPose.ITEM;
      }
   }

   protected void scale(EntityDummy p_225620_1_, PoseStack p_225620_2_, float p_225620_3_) {
      p_225620_2_.m_85841_(0.9375F, 0.9375F, 0.9375F);
   }

   protected void renderNameTag(EntityDummy p_225629_1_, Component p_225629_2_, PoseStack p_225629_3_, MultiBufferSource p_225629_4_, int p_225629_5_) {
      if (p_225629_1_.m_6052_()) {
         p_225629_3_.m_85836_();
         super.m_7649_(p_225629_1_, p_225629_2_, p_225629_3_, p_225629_4_, p_225629_5_);
         p_225629_3_.m_85849_();
      }
   }

   public void renderRightHand(PoseStack p_229144_1_, MultiBufferSource p_229144_2_, int p_229144_3_, EntityDummy p_229144_4_) {
      this.renderHand(p_229144_1_, p_229144_2_, p_229144_3_, p_229144_4_, ((PlayerModel)this.f_115290_).f_102811_, ((PlayerModel)this.f_115290_).f_103375_);
   }

   public void renderLeftHand(PoseStack p_229146_1_, MultiBufferSource p_229146_2_, int p_229146_3_, EntityDummy p_229146_4_) {
      this.renderHand(p_229146_1_, p_229146_2_, p_229146_3_, p_229146_4_, ((PlayerModel)this.f_115290_).f_102812_, ((PlayerModel)this.f_115290_).f_103374_);
   }

   private void renderHand(
      PoseStack p_229145_1_, MultiBufferSource p_229145_2_, int p_229145_3_, EntityDummy p_229145_4_, ModelPart p_229145_5_, ModelPart p_229145_6_
   ) {
      PlayerModel<EntityDummy> playermodel = (PlayerModel<EntityDummy>)this.m_7200_();
      this.setModelProperties(p_229145_4_);
      playermodel.f_102608_ = 0.0F;
      playermodel.f_102817_ = false;
      playermodel.f_102818_ = 0.0F;
      playermodel.m_6973_(p_229145_4_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      p_229145_5_.f_104203_ = 0.0F;
      p_229145_5_.m_104301_(p_229145_1_, p_229145_2_.m_6299_(RenderType.m_110446_(p_229145_4_.getSkinTextureLocation())), p_229145_3_, OverlayTexture.f_118083_);
      p_229145_6_.f_104203_ = 0.0F;
      p_229145_6_.m_104301_(p_229145_1_, p_229145_2_.m_6299_(RenderType.m_110473_(p_229145_4_.getSkinTextureLocation())), p_229145_3_, OverlayTexture.f_118083_);
   }

   protected void setupRotations(EntityDummy p_225621_1_, PoseStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      float f = p_225621_1_.m_20998_(p_225621_5_);
      if (p_225621_1_.m_21255_()) {
         super.m_7523_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
         float f1 = (float)p_225621_1_.m_21256_() + p_225621_5_;
         float f2 = Mth.m_14036_(f1 * f1 / 100.0F, 0.0F, 1.0F);
         if (!p_225621_1_.m_21209_()) {
            p_225621_2_.m_85845_(Vector3f.f_122223_.m_122240_(f2 * (-90.0F - p_225621_1_.m_146909_())));
         }

         Vec3 vector3d = p_225621_1_.m_20252_(p_225621_5_);
         Vec3 vector3d1 = p_225621_1_.m_20184_();
         double d0 = vector3d1.m_165925_();
         double d1 = vector3d.m_165925_();
         if (d0 > 0.0 && d1 > 0.0) {
            double d2 = (vector3d1.f_82479_ * vector3d.f_82479_ + vector3d1.f_82481_ * vector3d.f_82481_) / Math.sqrt(d0 * d1);
            double d3 = vector3d1.f_82479_ * vector3d.f_82481_ - vector3d1.f_82481_ * vector3d.f_82479_;
            p_225621_2_.m_85845_(Vector3f.f_122225_.m_122270_((float)(Math.signum(d3) * Math.acos(d2))));
         }
      } else if (f > 0.0F) {
         super.m_7523_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
         float f3 = p_225621_1_.m_20069_() ? -90.0F - p_225621_1_.m_146909_() : -90.0F;
         float f4 = Mth.m_14179_(f, 0.0F, f3);
         p_225621_2_.m_85845_(Vector3f.f_122223_.m_122240_(f4));
         if (p_225621_1_.m_6067_()) {
            p_225621_2_.m_85837_(0.0, -1.0, 0.3F);
         }
      } else {
         super.m_7523_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      }
   }
}
