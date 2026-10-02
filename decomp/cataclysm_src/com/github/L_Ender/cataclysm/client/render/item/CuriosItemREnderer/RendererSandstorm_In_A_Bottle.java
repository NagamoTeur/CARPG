package com.github.L_Ender.cataclysm.client.render.item.CuriosItemREnderer;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.item.CuriosModel.Sandstorm_In_A_BottleModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class RendererSandstorm_In_A_Bottle implements ICurioRenderer {
   private final Sandstorm_In_A_BottleModel model = new Sandstorm_In_A_BottleModel(
      Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.SANDSTORM_IN_A_BOTTLE_MODEL)
   );
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/curiositem/sandstorm_in_a_bottle.png");

   public ResourceLocation getCuriosTexture() {
      return TEXTURE;
   }

   public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack poseStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource buffer,
      int packedLight,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ICurioRenderer.followBodyRotations(slotContext.entity(), new HumanoidModel[]{this.model});
      VertexConsumer consumer = ItemRenderer.m_115184_(buffer, RenderType.m_110431_(this.getCuriosTexture()), false, stack.m_41790_());
      this.model.m_7695_(poseStack, consumer, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
