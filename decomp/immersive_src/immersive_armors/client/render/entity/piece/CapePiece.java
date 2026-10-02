package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import immersive_armors.client.render.entity.model.CapeModel;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class CapePiece<M extends CapeModel<LivingEntity>> extends Piece {
   private final M model;

   public CapePiece(M model) {
      this.model = model;
   }

   private ResourceLocation getCapeTexture(ExtendedArmorItem item, boolean overlay) {
      return new ResourceLocation("immersive_armors", "textures/models/armor/" + item.getMaterial().m_6082_() + "/cape" + (overlay ? "_overlay" : "") + ".png");
   }

   @Override
   public <T extends LivingEntity, A extends HumanoidModel<T>> void render(
      PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel
   ) {
      if (itemStack.m_41720_() instanceof ExtendedArmorItem armor) {
         CapePiece.CapeAngles angles = new CapePiece.CapeAngles(itemStack);
         angles.updateCapeAngles(entity, tickDelta);
         angles.store(itemStack);
         matrices.m_85836_();
         matrices.m_85837_(0.0, 0.0, 0.125);
         float n = entity.f_20884_ + (entity.f_20883_ - entity.f_20884_);
         double o = (double)Mth.m_14031_(n * (float) (Math.PI / 180.0));
         double p = (double)(-Mth.m_14089_(n * (float) (Math.PI / 180.0)));
         double q = angles.deltaY * 40.0;
         q = Mth.m_14008_(q, -6.0, 32.0);
         double r = (angles.deltaX * o + angles.deltaZ * p) * 100.0;
         r = Mth.m_14008_(r, 0.0, 150.0);
         double s = (angles.deltaX * p - angles.deltaZ * o) * 100.0;
         s = Mth.m_14008_(s, -20.0, 20.0);
         if (r < 0.0) {
            r = 0.0;
         }

         if (entity.m_6047_()) {
            q += 22.5;
            matrices.m_85837_(0.0, 0.25, 0.0);
         }

         matrices.m_85845_(Vector3f.f_122223_.m_122240_((float)(6.0 + r / 2.0 + q)));
         matrices.m_85845_(Vector3f.f_122227_.m_122240_((float)(s / 2.0)));
         matrices.m_85845_(Vector3f.f_122225_.m_122240_((float)(180.0 - s / 2.0)));
         this.model.setAngles(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         VertexConsumer vertexConsumer;
         if (this.isColored()) {
            int i = ((DyeableLeatherItem)armor).m_41121_(itemStack);
            float red = (float)(i >> 16 & 0xFF) / 255.0F;
            float green = (float)(i >> 8 & 0xFF) / 255.0F;
            float blue = (float)(i & 0xFF) / 255.0F;
            vertexConsumer = vertexConsumers.m_6299_(RenderType.m_110431_(this.getCapeTexture(armor, false)));
            this.model.m_7695_(matrices, vertexConsumer, light, OverlayTexture.f_118083_, red, green, blue, 1.0F);
            vertexConsumer = vertexConsumers.m_6299_(RenderType.m_110431_(this.getCapeTexture(armor, true)));
         } else {
            vertexConsumer = vertexConsumers.m_6299_(RenderType.m_110431_(this.getCapeTexture(armor, false)));
         }

         this.model.m_7695_(matrices, vertexConsumer, light, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrices.m_85849_();
      }
   }

   private static class CapeAngles {
      private static final String ANGLE_TAG = "capeAngles";
      private double capeX;
      private double capeY;
      private double capeZ;
      private double deltaX;
      private double deltaY;
      private double deltaZ;
      private float lastTickDelta;

      private Vec3 predictPosition(Entity entity, float tickDelta) {
         return new Vec3(
            Mth.m_14139_((double)tickDelta, entity.f_19854_, entity.m_20185_()),
            Mth.m_14139_((double)tickDelta, entity.f_19855_, entity.m_20186_()),
            Mth.m_14139_((double)tickDelta, entity.f_19856_, entity.m_20189_())
         );
      }

      private void updateCapeAngles(Entity entity, float tickDelta) {
         Vec3 pos = this.predictPosition(entity, tickDelta);
         double dx = pos.m_7096_() - this.capeX;
         double dy = pos.m_7098_() - this.capeY;
         double dz = pos.m_7094_() - this.capeZ;
         if (dx > 10.0 || dx < -10.0) {
            this.capeX = pos.m_7096_();
            dx = 0.0;
         }

         if (dy > 10.0 || dy < -10.0) {
            this.capeY = pos.m_7098_();
            dy = 0.0;
         }

         if (dz > 10.0 || dz < -10.0) {
            this.capeZ = pos.m_7094_();
            dz = 0.0;
         }

         float delta = tickDelta - this.lastTickDelta;
         if ((double)delta < 0.0) {
            delta++;
         }

         delta *= 0.25F;
         this.lastTickDelta = tickDelta;
         this.capeX += dx * (double)delta;
         this.capeZ += dz * (double)delta;
         this.capeY += dy * (double)delta;
         this.deltaX = this.capeX - pos.m_7096_();
         this.deltaY = this.capeY - pos.m_7098_();
         this.deltaZ = this.capeZ - pos.m_7094_();
      }

      public CapeAngles(ItemStack cape) {
         CompoundTag tag = cape.m_41784_();
         if (tag.m_128441_("capeAngles")) {
            CompoundTag angles = tag.m_128469_("capeAngles");
            this.capeX = angles.m_128459_("capeX");
            this.capeY = angles.m_128459_("capeY");
            this.capeZ = angles.m_128459_("capeZ");
            this.lastTickDelta = angles.m_128457_("lastTickDelta");
         }
      }

      public void store(ItemStack cape) {
         CompoundTag tag = cape.m_41784_();
         CompoundTag angles = new CompoundTag();
         angles.m_128347_("capeX", this.capeX);
         angles.m_128347_("capeY", this.capeY);
         angles.m_128347_("capeZ", this.capeZ);
         angles.m_128350_("lastTickDelta", this.lastTickDelta);
         tag.m_128365_("capeAngles", angles);
      }
   }
}
