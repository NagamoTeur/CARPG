package immersive_armors.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CapeLayer.class})
public class MixinCapeFeatureRenderer {
   @Inject(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   void immersiveArmors$render(
      PoseStack matrixStack,
      MultiBufferSource vertexConsumerProvider,
      int i,
      AbstractClientPlayer abstractClientPlayerEntity,
      float f,
      float g,
      float h,
      float j,
      float k,
      float l,
      CallbackInfo ci
   ) {
      if (abstractClientPlayerEntity.m_108555_()
         && !abstractClientPlayerEntity.m_20145_()
         && abstractClientPlayerEntity.m_36170_(PlayerModelPart.CAPE)
         && abstractClientPlayerEntity.m_108561_() != null) {
         ItemStack itemStack = abstractClientPlayerEntity.m_6844_(EquipmentSlot.CHEST);
         if (itemStack.m_41720_() instanceof ExtendedArmorItem && ((ExtendedArmorItem)itemStack.m_41720_()).getMaterial().shouldHideCape()) {
            ci.cancel();
         }
      }
   }
}
