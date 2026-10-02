package dev.latvian.mods.kubejs.client.painter.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import dev.latvian.mods.kubejs.client.painter.Painter;
import dev.latvian.mods.kubejs.client.painter.PainterObjectProperties;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.unit.FixedBooleanUnit;
import dev.latvian.mods.unit.FixedNumberUnit;
import dev.latvian.mods.unit.Unit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemObject extends ScreenPainterObject {
   private ItemStack itemStack = ItemStack.f_41583_;
   private Unit overlay = FixedBooleanUnit.TRUE;
   private String customText = "";
   private Unit rotation = FixedNumberUnit.ZERO;

   public ItemObject(Painter painter) {
      this.z = FixedNumberUnit.of(100.0);
   }

   @Override
   protected void load(PainterObjectProperties properties) {
      super.load(properties);
      if (properties.hasAny("item")) {
         this.itemStack = ItemStackJS.of(properties.tag.m_128423_("item"));
      }

      this.overlay = properties.getUnit("overlay", this.overlay);
      this.customText = properties.getString("customText", this.customText);
      this.rotation = properties.getUnit("rotation", this.rotation);
   }

   @Override
   public void draw(PaintScreenEventJS event) {
      if (!this.itemStack.m_41619_()) {
         float aw = this.w.getFloat(event);
         float ah = this.h.getFloat(event);
         float ax = event.alignX(this.x.getFloat(event), aw, this.alignX);
         float ay = event.alignY(this.y.getFloat(event), ah, this.alignY);
         float az = this.z.getFloat(event);
         event.push();
         event.translate((double)ax, (double)ay, (double)az);
         if (this.rotation != FixedNumberUnit.ZERO) {
            event.rotateRad(this.rotation.getFloat(event));
         }

         event.scale(aw / 16.0F, ah / 16.0F, 1.0F);
         drawItem(event.matrices, this.itemStack, 0, this.overlay.getBoolean(event), this.customText.isEmpty() ? null : this.customText);
         event.pop();
      }
   }

   public static void drawItem(PoseStack poseStack, ItemStack stack, int hash, boolean renderOverlay, @Nullable String text) {
      if (!stack.m_41619_()) {
         Minecraft mc = Minecraft.m_91087_();
         ItemRenderer itemRenderer = mc.m_91291_();
         BakedModel bakedModel = itemRenderer.m_174264_(stack, null, mc.f_91074_, hash);
         Minecraft.m_91087_().m_91097_().m_118506_(InventoryMenu.f_39692_).m_117960_(false, false);
         RenderSystem.m_157456_(0, InventoryMenu.f_39692_);
         RenderSystem.m_69478_();
         RenderSystem.m_69408_(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         PoseStack modelViewStack = RenderSystem.m_157191_();
         modelViewStack.m_85836_();
         modelViewStack.m_166854_(poseStack.m_85850_().m_85861_());
         modelViewStack.m_85841_(1.0F, -1.0F, 1.0F);
         modelViewStack.m_85841_(16.0F, 16.0F, 16.0F);
         RenderSystem.m_157182_();
         BufferSource bufferSource = Minecraft.m_91087_().m_91269_().m_110104_();
         boolean flatLight = !bakedModel.m_7547_();
         if (flatLight) {
            Lighting.m_84930_();
         }

         itemRenderer.m_115143_(stack, TransformType.GUI, false, new PoseStack(), bufferSource, 15728880, OverlayTexture.f_118083_, bakedModel);
         bufferSource.m_109911_();
         RenderSystem.m_69482_();
         if (flatLight) {
            Lighting.m_84931_();
         }

         modelViewStack.m_85849_();
         RenderSystem.m_157182_();
         if (renderOverlay) {
            Tesselator t = Tesselator.m_85913_();
            Font font = mc.f_91062_;
            if (stack.m_41613_() != 1 || text != null) {
               String s = text == null ? String.valueOf(stack.m_41613_()) : text;
               poseStack.m_85836_();
               poseStack.m_85837_(9.0 - (double)font.m_92895_(s), 1.0, 20.0);
               font.m_92811_(s, 0.0F, 0.0F, 16777215, true, poseStack.m_85850_().m_85861_(), bufferSource, false, 0, 15728880);
               bufferSource.m_109911_();
               poseStack.m_85849_();
            }

            if (stack.m_150947_()) {
               RenderSystem.m_69465_();
               RenderSystem.m_69472_();
               RenderSystem.m_69461_();
               int barWidth = stack.m_150948_();
               int barColor = stack.m_150949_();
               draw(poseStack, t, -6, 5, 13, 2, 0, 0, 0, 255);
               draw(poseStack, t, -6, 5, barWidth, 1, barColor >> 16 & 0xFF, barColor >> 8 & 0xFF, barColor & 0xFF, 255);
               RenderSystem.m_69478_();
               RenderSystem.m_69493_();
               RenderSystem.m_69482_();
            }

            float cooldown = mc.f_91074_ == null ? 0.0F : mc.f_91074_.m_36335_().m_41521_(stack.m_41720_(), mc.m_91296_());
            if (cooldown > 0.0F) {
               RenderSystem.m_69465_();
               RenderSystem.m_69472_();
               RenderSystem.m_69478_();
               RenderSystem.m_69453_();
               draw(poseStack, t, -8, Mth.m_14143_(16.0F * (1.0F - cooldown)) - 8, 16, Mth.m_14167_(16.0F * cooldown), 255, 255, 255, 127);
               RenderSystem.m_69493_();
               RenderSystem.m_69482_();
            }
         }
      }
   }

   private static void draw(PoseStack matrixStack, Tesselator t, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
      if (width > 0 && height > 0) {
         RenderSystem.m_157427_(GameRenderer::m_172811_);
         Matrix4f m = matrixStack.m_85850_().m_85861_();
         BufferBuilder renderer = t.m_85915_();
         renderer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
         renderer.m_85982_(m, (float)x, (float)y, 0.0F).m_6122_(red, green, blue, alpha).m_5752_();
         renderer.m_85982_(m, (float)x, (float)(y + height), 0.0F).m_6122_(red, green, blue, alpha).m_5752_();
         renderer.m_85982_(m, (float)(x + width), (float)(y + height), 0.0F).m_6122_(red, green, blue, alpha).m_5752_();
         renderer.m_85982_(m, (float)(x + width), (float)y, 0.0F).m_6122_(red, green, blue, alpha).m_5752_();
         t.m_85914_();
      }
   }
}
