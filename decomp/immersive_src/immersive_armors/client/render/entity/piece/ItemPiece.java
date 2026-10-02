package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import immersive_armors.client.render.entity.model.DecoModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemPiece extends Piece {
   private final String attachTo;
   private final float x;
   private final float y;
   private final float z;
   private final float size;
   private final ItemStack stack;
   private final Quaternion rotation;

   public ItemPiece(String to, float x, float y, float z, float size, ItemStack stack) {
      this(to, x, y, z, size, stack, null);
   }

   public ItemPiece(String to, float x, float y, float z, float size, ItemStack stack, @Nullable Quaternion rotation) {
      this.attachTo = to;
      this.x = x;
      this.y = y;
      this.z = z;
      this.size = size;
      this.stack = stack;
      this.rotation = rotation;
   }

   @Override
   public <T extends LivingEntity, A extends HumanoidModel<T>> void render(
      PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel
   ) {
      matrices.m_85836_();
      DecoModel.getModelPart(armorModel, this.attachTo).m_104299_(matrices);
      matrices.m_85837_((double)this.x, (double)this.y, (double)this.z);
      matrices.m_85841_(this.size, -this.size, -this.size);
      if (this.rotation != null) {
         matrices.m_85845_(this.rotation);
      }

      Minecraft.m_91087_()
         .m_91291_()
         .m_174242_(entity, this.stack, TransformType.GROUND, false, matrices, vertexConsumers, entity.f_19853_, light, OverlayTexture.f_118083_, 0);
      matrices.m_85849_();
   }
}
