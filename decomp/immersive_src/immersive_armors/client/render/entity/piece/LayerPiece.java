package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public abstract class LayerPiece extends Piece {
   protected abstract HumanoidModel<LivingEntity> getModel();

   protected static HumanoidModel<LivingEntity> buildDilatedModel(float dilation) {
      return new HumanoidModel(LayerDefinition.m_171565_(HumanoidModel.m_170681_(new CubeDeformation(dilation), 0.0F), 64, 32).m_171564_());
   }

   @Override
   public <T extends LivingEntity, A extends HumanoidModel<T>> void render(
      PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel
   ) {
      if (itemStack.m_41720_() instanceof ExtendedArmorItem armorItem) {
         armorModel.m_102872_(this.getModel());
         this.setVisible(this.getModel(), armorSlot);
         if (this.isColored()) {
            int i = ((DyeableLeatherItem)armorItem).m_41121_(itemStack);
            float red = (float)(i >> 16 & 0xFF) / 255.0F;
            float green = (float)(i >> 8 & 0xFF) / 255.0F;
            float blue = (float)(i & 0xFF) / 255.0F;
            this.renderParts(matrices, vertexConsumers, light, itemStack, armorItem, this.getModel(), red, green, blue, false);
            this.renderParts(matrices, vertexConsumers, light, itemStack, armorItem, this.getModel(), 1.0F, 1.0F, 1.0F, true);
         } else {
            this.renderParts(matrices, vertexConsumers, light, itemStack, armorItem, this.getModel(), 1.0F, 1.0F, 1.0F, false);
         }
      }
   }
}
