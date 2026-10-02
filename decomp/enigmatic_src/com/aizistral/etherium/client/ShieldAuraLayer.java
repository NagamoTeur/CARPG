package com.aizistral.etherium.client;

import com.aizistral.etherium.items.EtheriumArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldAuraLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("enigmaticlegacy", "textures/models/misc/ultimate_wither_armor.png");
   private final PlayerModel<AbstractClientPlayer> witherModel;

   public ShieldAuraLayer(LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, EntityModelSet modelSet) {
      super(renderer);
      this.witherModel = new PlayerModel(modelSet.m_171103_(ModelLayers.f_171162_), false);
   }

   public void render(
      PoseStack poseStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractClientPlayer entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (EtheriumArmor.hasShield(entitylivingbaseIn)) {
         if (!Minecraft.m_91087_().m_91104_() && entitylivingbaseIn.m_5446_().equals(Minecraft.m_91087_().f_91074_.m_5446_())) {
            partialTicks = Minecraft.m_91087_().m_91296_();
         }

         float f = (float)entitylivingbaseIn.f_19797_ + partialTicks;
         PlayerModel<AbstractClientPlayer> entitymodel = this.model();
         entitymodel.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         if (entitylivingbaseIn.m_5833_()) {
            this.witherModel.m_8009_(false);
            this.witherModel.f_102808_.f_104207_ = true;
            this.witherModel.f_102809_.f_104207_ = true;
         } else {
            ItemStack itemstack = entitylivingbaseIn.m_21205_();
            ItemStack itemstack1 = entitylivingbaseIn.m_21206_();
            this.witherModel.m_8009_(true);
            this.witherModel.f_102817_ = entitylivingbaseIn.m_6047_();
            ArmPose bipedmodel$armpose = this.func_217766_a(entitylivingbaseIn, itemstack, itemstack1, InteractionHand.MAIN_HAND);
            ArmPose bipedmodel$armpose1 = this.func_217766_a(entitylivingbaseIn, itemstack, itemstack1, InteractionHand.OFF_HAND);
            if (entitylivingbaseIn.m_5737_() == HumanoidArm.RIGHT) {
               this.witherModel.f_102816_ = bipedmodel$armpose;
               this.witherModel.f_102815_ = bipedmodel$armpose1;
            } else {
               this.witherModel.f_102816_ = bipedmodel$armpose1;
               this.witherModel.f_102815_ = bipedmodel$armpose;
            }
         }

         float scale = 1.05F;
         poseStackIn.m_85836_();
         poseStackIn.m_85841_(scale, scale, scale);
         poseStackIn.m_85837_(0.0, (double)(-(scale - 1.0F) / 2.0F), 0.0);
         ((PlayerModel)this.m_117386_()).m_102872_(entitymodel);
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110436_(getTextureLocation(), xOffset(f), f * 0.01F));
         entitymodel.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         entitymodel.m_7695_(poseStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 0.5F, 0.5F, 0.5F, 1.0F);
         poseStackIn.m_85849_();
      }
   }

   private ArmPose func_217766_a(AbstractClientPlayer playerIn, ItemStack itemStackMain, ItemStack itemStackOff, InteractionHand handIn) {
      ArmPose bipedmodel$armpose = ArmPose.EMPTY;
      ItemStack itemstack = handIn == InteractionHand.MAIN_HAND ? itemStackMain : itemStackOff;
      if (!itemstack.m_41619_()) {
         bipedmodel$armpose = ArmPose.ITEM;
         if (playerIn.m_21212_() > 0) {
            UseAnim useaction = itemstack.m_41780_();
            if (useaction == UseAnim.BLOCK) {
               bipedmodel$armpose = ArmPose.BLOCK;
            } else if (useaction == UseAnim.BOW) {
               bipedmodel$armpose = ArmPose.BOW_AND_ARROW;
            } else if (useaction == UseAnim.SPEAR) {
               bipedmodel$armpose = ArmPose.THROW_SPEAR;
            } else if (useaction == UseAnim.CROSSBOW && handIn == playerIn.m_7655_()) {
               bipedmodel$armpose = ArmPose.CROSSBOW_CHARGE;
            }
         } else {
            boolean flag3 = itemStackMain.m_41720_() == Items.f_42717_;
            boolean flag = CrossbowItem.m_40932_(itemStackMain);
            boolean flag1 = itemStackOff.m_41720_() == Items.f_42717_;
            boolean flag2 = CrossbowItem.m_40932_(itemStackOff);
            if (flag3 && flag) {
               bipedmodel$armpose = ArmPose.CROSSBOW_HOLD;
            }

            if (flag1 && flag2 && itemStackMain.m_41720_().m_6164_(itemStackMain) == UseAnim.NONE) {
               bipedmodel$armpose = ArmPose.CROSSBOW_HOLD;
            }
         }
      }

      return bipedmodel$armpose;
   }

   public static float xOffset(float p_225634_1_) {
      return Mth.m_14089_(p_225634_1_ * 0.02F) * 2.0F;
   }

   public static ResourceLocation getTextureLocation() {
      return WITHER_ARMOR;
   }

   protected PlayerModel<AbstractClientPlayer> model() {
      return this.witherModel;
   }
}
