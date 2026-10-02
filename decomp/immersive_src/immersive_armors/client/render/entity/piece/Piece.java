package immersive_armors.client.render.entity.piece;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class Piece {
   private static final Map<String, ResourceLocation> ARMOR_TEXTURE_CACHE = Maps.newHashMap();
   private boolean translucent;
   private boolean glint;
   private boolean colored;
   private boolean glowing;
   private String texture;

   protected void setVisible(HumanoidModel bipedModel, EquipmentSlot slot) {
      bipedModel.m_8009_(false);
      switch (slot) {
         case HEAD:
            bipedModel.f_102808_.f_104207_ = true;
            bipedModel.f_102809_.f_104207_ = true;
            break;
         case CHEST:
            bipedModel.f_102810_.f_104207_ = true;
            bipedModel.f_102811_.f_104207_ = true;
            bipedModel.f_102812_.f_104207_ = true;
            break;
         case LEGS:
            bipedModel.f_102810_.f_104207_ = true;
            bipedModel.f_102813_.f_104207_ = true;
            bipedModel.f_102814_.f_104207_ = true;
            break;
         case FEET:
            bipedModel.f_102813_.f_104207_ = true;
            bipedModel.f_102814_.f_104207_ = true;
      }
   }

   private ResourceLocation getTexture(ExtendedArmorItem item, boolean overlay) {
      String string = "immersive_armors:textures/models/armor/" + item.getMaterial().m_6082_() + "/" + this.getTexture() + (overlay ? "_overlay" : "") + ".png";
      return ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ResourceLocation::new);
   }

   protected void renderParts(
      PoseStack matrices,
      MultiBufferSource vertexConsumers,
      int light,
      ItemStack itemStack,
      ExtendedArmorItem item,
      EntityModel model,
      float red,
      float green,
      float blue,
      boolean overlay
   ) {
      RenderType renderLayer;
      if (this.isTranslucent()) {
         renderLayer = RenderType.m_110473_(this.getTexture(item, overlay));
      } else if (this.isGlowing()) {
         renderLayer = RenderType.m_110460_(this.getTexture(item, overlay), false);
      } else {
         renderLayer = RenderType.m_110431_(this.getTexture(item, overlay));
      }

      VertexConsumer vertexConsumer = ItemRenderer.m_115184_(
         vertexConsumers, renderLayer, false, this.hasGlint() | itemStack.m_41790_() & Config.getInstance().enableEnchantmentGlint
      );
      model.m_7695_(matrices, vertexConsumer, light, OverlayTexture.f_118083_, red, green, blue, 1.0F);
   }

   public abstract <T extends LivingEntity, A extends HumanoidModel<T>> void render(
      PoseStack var1, MultiBufferSource var2, int var3, T var4, ItemStack var5, float var6, EquipmentSlot var7, A var8
   );

   public Piece translucent() {
      this.translucent = true;
      return this;
   }

   public Piece glint() {
      this.glint = true;
      return this;
   }

   public Piece colored() {
      this.colored = true;
      return this;
   }

   public Piece glowing() {
      this.glowing = true;
      return this;
   }

   public Piece texture(String texture) {
      this.texture = texture;
      return this;
   }

   public boolean isTranslucent() {
      return this.translucent;
   }

   public boolean hasGlint() {
      return this.glint;
   }

   public boolean isColored() {
      return this.colored;
   }

   public boolean isGlowing() {
      return this.glowing;
   }

   public String getTexture() {
      return this.texture;
   }
}
