package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ItemRenderer.class})
public class MixinItemRenderer {
   @Redirect(
      method = {"render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/tags/TagKey;)Z",
         ordinal = 0
      ),
      require = 1
   )
   private boolean redirectCompassCheck(ItemStack stack, TagKey item) {
      return stack.m_150930_(EnigmaticItems.THE_INFINITUM) || stack.m_204117_(item);
   }
}
