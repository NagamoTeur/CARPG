package immersive_armors.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.Main;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {HumanoidArmorLayer.class},
   priority = 700
)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
   public MixinArmorFeatureRenderer(RenderLayerParent<T, M> context) {
      super(context);
   }

   @Inject(
      method = {"renderArmor"},
      at = {@At("HEAD")},
      cancellable = true
   )
   void immersiveArmors$injectRenderArmor(
      PoseStack matrices, MultiBufferSource vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci
   ) {
      if (Main.FORGE) {
         ItemStack itemStack = entity.m_6844_(armorSlot);
         if (itemStack.m_41720_() instanceof ExtendedArmorItem) {
            ci.cancel();
         }
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V"},
      at = {@At("HEAD")}
   )
   public void immersiveArmors$render(
      PoseStack matrixStack,
      MultiBufferSource vertexConsumerProvider,
      int i,
      T entity,
      float f,
      float g,
      float tickDelta,
      float j,
      float k,
      float l,
      CallbackInfo ci
   ) {
      if (Main.FORGE) {
         this.renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.HEAD);
         this.renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.CHEST);
         this.renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.LEGS);
         this.renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.FEET);
      }
   }

   private void renderPieces(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float tickDelta, EquipmentSlot armorSlot) {
      if (entity != null) {
         ItemStack equippedStack = entity.m_6844_(armorSlot);
         if (equippedStack.m_41720_() instanceof ExtendedArmorItem item) {
            item.getMaterial()
               .getPieces(item.m_40402_())
               .forEach(piece -> piece.render(matrices, vertexConsumers, light, entity, equippedStack, tickDelta, item.m_40402_(), (A)this.m_117386_()));
         }
      }
   }
}
