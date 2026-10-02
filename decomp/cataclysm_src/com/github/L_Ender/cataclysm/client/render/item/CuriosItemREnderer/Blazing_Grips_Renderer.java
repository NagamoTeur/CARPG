package com.github.L_Ender.cataclysm.client.render.item.CuriosItemREnderer;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.item.CuriosModel.Blazing_Grips_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class Blazing_Grips_Renderer implements ICurioRenderer {
   private final Blazing_Grips_Model model = new Blazing_Grips_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.BLAZING_GRIPS_MODEL));
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/curiositem/blazing_grips.png");
   private static final ResourceLocation TEXTURE_LAYER = new ResourceLocation("cataclysm", "textures/curiositem/blazing_grips_layer.png");
   private final Blazing_Grips_Model slimModel = new Blazing_Grips_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.BLAZING_GRIPS_SLIM_MODEL));

   @Nullable
   public static Blazing_Grips_Renderer getGloveRenderer(ItemStack stack) {
      return !stack.m_41619_()
         ? CuriosRendererRegistry.getRenderer(stack.m_41720_())
            .filter(Blazing_Grips_Renderer.class::isInstance)
            .map(Blazing_Grips_Renderer.class::cast)
            .orElse(null)
         : null;
   }

   protected Blazing_Grips_Model getModel(boolean hasSlimArms) {
      return hasSlimArms ? this.slimModel : this.model;
   }

   protected static boolean hasSlimArms(Entity entity) {
      if (entity instanceof AbstractClientPlayer player && player.m_108564_().equals("slim")) {
         return true;
      }

      return false;
   }

   public ResourceLocation getCuriosTexture() {
      return TEXTURE;
   }

   public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack poseStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource multiBufferSource,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      boolean hasSlimArms = hasSlimArms(slotContext.entity());
      Blazing_Grips_Model model = this.getModel(hasSlimArms);
      InteractionHand hand = slotContext.index() % 2 == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
      HumanoidArm handSide = hand == InteractionHand.MAIN_HAND ? slotContext.entity().m_5737_() : slotContext.entity().m_5737_().m_20828_();
      model.m_6973_(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      model.m_6839_(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
      ICurioRenderer.followBodyRotations(slotContext.entity(), new HumanoidModel[]{model});
      this.renderArm(model, poseStack, multiBufferSource, handSide, light, stack.m_41790_());
   }

   protected void renderArm(Blazing_Grips_Model model, PoseStack matrixStack, MultiBufferSource buffer, HumanoidArm handSide, int light, boolean hasFoil) {
      RenderType renderType = model.m_103119_(this.getCuriosTexture());
      VertexConsumer vertexBuilder = ItemRenderer.m_115211_(buffer, renderType, false, hasFoil);
      model.renderArm(handSide, matrixStack, vertexBuilder, light, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      VertexConsumer builder = ItemRenderer.m_115211_(buffer, CMRenderTypes.CMEyes(TEXTURE_LAYER), false, hasFoil);
      model.renderArm(handSide, matrixStack, builder, LightTexture.m_109885_(15, 15), OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   public final void renderFirstPersonArm(
      PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm side, boolean hasFoil
   ) {
      if (!player.m_5833_()) {
         boolean hasSlimArms = hasSlimArms(player);
         Blazing_Grips_Model model = this.getModel(hasSlimArms);
         ModelPart arm = side == HumanoidArm.LEFT ? model.f_102812_ : model.f_102811_;
         model.m_8009_(false);
         arm.f_104207_ = true;
         model.f_102817_ = false;
         model.f_102608_ = model.f_102818_ = 0.0F;
         model.m_6973_(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         arm.f_104203_ = 0.0F;
         this.renderFirstPersonArm(model, arm, matrixStack, buffer, light, hasFoil);
      }
   }

   protected void renderFirstPersonArm(Blazing_Grips_Model model, ModelPart arm, PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasFoil) {
      RenderType renderType = model.m_103119_(this.getCuriosTexture());
      VertexConsumer builder = ItemRenderer.m_115211_(buffer, renderType, false, hasFoil);
      arm.m_104301_(matrixStack, builder, light, OverlayTexture.f_118083_);
      VertexConsumer builder2 = ItemRenderer.m_115211_(buffer, CMRenderTypes.CMEyes(TEXTURE_LAYER), false, hasFoil);
      arm.m_104301_(matrixStack, builder2, LightTexture.m_109885_(15, 15), OverlayTexture.f_118083_);
   }
}
