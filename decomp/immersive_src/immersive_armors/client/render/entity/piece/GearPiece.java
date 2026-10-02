package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import immersive_armors.client.render.entity.model.DecoModel;
import immersive_armors.client.render.entity.model.GearModel;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GearPiece<M extends GearModel> extends Piece {
   private final M model;
   private final String texture;
   private final float x;
   private final float y;
   private final float z;
   private final float speed;
   private final Quaternion rotation;

   private ResourceLocation getTexture(ExtendedArmorItem item) {
      return new ResourceLocation("immersive_armors", "textures/models/armor/" + item.getMaterial().m_6082_() + "/" + this.texture + ".png");
   }

   public GearPiece(M model, String texture, float x, float y, float z, float speed) {
      this(model, texture, x, y, z, speed, null);
   }

   public GearPiece(M model, String texture, float x, float y, float z, float speed, @Nullable Quaternion rotation) {
      this.model = model;
      this.texture = texture;
      this.x = x;
      this.y = y;
      this.z = z;
      this.speed = speed;
      this.rotation = rotation;
   }

   @Override
   public <T extends LivingEntity, A extends HumanoidModel<T>> void render(
      PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel
   ) {
      matrices.m_85836_();
      DecoModel.getModelPart(armorModel, this.model.getAttachTo()).m_104299_(matrices);
      matrices.m_85837_((double)this.x, (double)this.y, (double)this.z);
      if (this.rotation != null) {
         matrices.m_85845_(this.rotation);
      }

      matrices.m_85845_(new Quaternion(new Vector3f(0.0F, 0.0F, 1.0F), ((float)entity.f_19797_ + tickDelta) * this.speed, false));
      VertexConsumer vertexConsumer = vertexConsumers.m_6299_(RenderType.m_110431_(this.getTexture((ExtendedArmorItem)itemStack.m_41720_())));
      this.model.getPart().m_104306_(matrices, vertexConsumer, light, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrices.m_85849_();
   }
}
