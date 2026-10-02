package dev.latvian.mods.kubejs.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import dev.latvian.mods.kubejs.bindings.TextWrapper;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.NotificationBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.Toast.Visibility;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

public class NotificationToast implements Toast {
   public static final Map<Integer, BiFunction<Minecraft, String, NotificationToast.ToastIcon>> ICONS = new HashMap<>(
      Map.of(1, NotificationToast.TextureIcon::new, 2, NotificationToast.ItemIcon::new, 3, NotificationToast.AtlasIcon::of)
   );
   private final NotificationBuilder notification;
   private final long duration;
   private final NotificationToast.ToastIcon icon;
   private final List<FormattedCharSequence> text;
   private int width;
   private int height;
   private long lastChanged;
   private boolean changed;

   public NotificationToast(Minecraft mc, NotificationBuilder notification) {
      this.notification = notification;
      this.duration = notification.duration.toMillis();
      this.icon = ICONS.containsKey(this.notification.iconType) ? ICONS.get(this.notification.iconType).apply(mc, this.notification.icon) : null;
      this.text = new ArrayList<>(2);
      this.width = 0;
      this.height = 0;
      if (!TextWrapper.isEmpty(notification.text)) {
         this.text.addAll(mc.f_91062_.m_92923_(notification.text, 240));
      }

      for (FormattedCharSequence l : this.text) {
         this.width = Math.max(this.width, mc.f_91062_.m_92724_(l));
      }

      this.width += 12;
      if (this.icon != null) {
         this.width += 24;
      }

      this.height = Math.max(this.text.size() * 10 + 12, 28);
      if (this.text.isEmpty() && this.icon != null) {
         this.width = 28;
         this.height = 28;
      }
   }

   public int m_7828_() {
      return this.width;
   }

   public int m_94899_() {
      return this.height;
   }

   private void drawRectangle(Matrix4f m, int x0, int y0, int x1, int y1, int r, int g, int b) {
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      BufferBuilder buf = Tesselator.m_85913_().m_85915_();
      buf.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
      buf.m_85982_(m, (float)x0, (float)y1, 0.0F).m_6122_(r, g, b, 255).m_5752_();
      buf.m_85982_(m, (float)x1, (float)y1, 0.0F).m_6122_(r, g, b, 255).m_5752_();
      buf.m_85982_(m, (float)x1, (float)y0, 0.0F).m_6122_(r, g, b, 255).m_5752_();
      buf.m_85982_(m, (float)x0, (float)y0, 0.0F).m_6122_(r, g, b, 255).m_5752_();
      BufferUploader.m_231202_(buf.m_231175_());
   }

   public Visibility m_7172_(PoseStack poseStack, ToastComponent toastComponent, long l) {
      if (this.changed) {
         this.lastChanged = l;
         this.changed = false;
      }

      Minecraft mc = toastComponent.m_94929_();
      poseStack.m_85836_();
      poseStack.m_85837_(-2.0, 2.0, 0.0);
      Matrix4f m = poseStack.m_85850_().m_85861_();
      int w = this.m_7828_();
      int h = this.m_94899_();
      int oc = this.notification.outlineColor.getRgbJS();
      int ocr = ARGB32.m_13665_(oc);
      int ocg = ARGB32.m_13667_(oc);
      int ocb = ARGB32.m_13669_(oc);
      int bc = this.notification.borderColor.getRgbJS();
      int bcr = ARGB32.m_13665_(bc);
      int bcg = ARGB32.m_13667_(bc);
      int bcb = ARGB32.m_13669_(bc);
      int bgc = this.notification.backgroundColor.getRgbJS();
      int bgcr = ARGB32.m_13665_(bgc);
      int bgcg = ARGB32.m_13667_(bgc);
      int bgcb = ARGB32.m_13669_(bgc);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69472_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      this.drawRectangle(m, 2, 0, w - 2, h, ocr, ocg, ocb);
      this.drawRectangle(m, 0, 2, w, h - 2, ocr, ocg, ocb);
      this.drawRectangle(m, 1, 1, w - 1, h - 1, ocr, ocg, ocb);
      this.drawRectangle(m, 2, 1, w - 2, h - 1, bcr, bcg, bcb);
      this.drawRectangle(m, 1, 2, w - 1, h - 2, bcr, bcg, bcb);
      this.drawRectangle(m, 2, 2, w - 2, h - 2, bgcr, bgcg, bgcb);
      RenderSystem.m_69493_();
      if (this.icon != null) {
         this.icon.draw(mc, poseStack, 14, h / 2, this.notification.iconSize);
      }

      int th = this.icon == null ? 6 : 26;
      int tv = (h - this.text.size() * 10) / 2 + 1;

      for (int i = 0; i < this.text.size(); i++) {
         FormattedCharSequence line = this.text.get(i);
         if (this.notification.textShadow) {
            mc.f_91062_.m_92744_(poseStack, line, (float)th, (float)(tv + i * 10), 16777215);
         } else {
            mc.f_91062_.m_92877_(poseStack, line, (float)th, (float)(tv + i * 10), 16777215);
         }
      }

      poseStack.m_85849_();
      return l - this.lastChanged < this.duration ? Visibility.SHOW : Visibility.HIDE;
   }

   public static record AtlasIcon(TextureAtlasSprite sprite) implements NotificationToast.ToastIcon {
      public static NotificationToast.AtlasIcon of(Minecraft mc, String icon) {
         String[] s = icon.split("\\|");
         return s.length == 2
            ? new NotificationToast.AtlasIcon((TextureAtlasSprite)mc.m_91258_(new ResourceLocation(s[0])).apply(new ResourceLocation(s[1])))
            : new NotificationToast.AtlasIcon((TextureAtlasSprite)mc.m_91258_(InventoryMenu.f_39692_).apply(new ResourceLocation(icon)));
      }

      @Override
      public void draw(Minecraft mc, PoseStack pose, int x, int y, int size) {
         RenderSystem.m_157456_(0, this.sprite.m_118414_().m_118330_());
         RenderSystem.m_157427_(GameRenderer::m_172820_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         Matrix4f m = pose.m_85850_().m_85861_();
         int p0 = -size / 2;
         int p1 = p0 + size;
         float u0 = this.sprite.m_118409_();
         float v0 = this.sprite.m_118411_();
         float u1 = this.sprite.m_118410_();
         float v1 = this.sprite.m_118412_();
         BufferBuilder buf = Tesselator.m_85913_().m_85915_();
         buf.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85819_);
         buf.m_85982_(m, (float)(x + p0), (float)(y + p1), 0.0F).m_7421_(u0, v1).m_6122_(255, 255, 255, 255).m_5752_();
         buf.m_85982_(m, (float)(x + p1), (float)(y + p1), 0.0F).m_7421_(u1, v1).m_6122_(255, 255, 255, 255).m_5752_();
         buf.m_85982_(m, (float)(x + p1), (float)(y + p0), 0.0F).m_7421_(u1, v0).m_6122_(255, 255, 255, 255).m_5752_();
         buf.m_85982_(m, (float)(x + p0), (float)(y + p0), 0.0F).m_7421_(u0, v0).m_6122_(255, 255, 255, 255).m_5752_();
         BufferUploader.m_231202_(buf.m_231175_());
      }
   }

   public static record ItemIcon(ItemStack stack) implements NotificationToast.ToastIcon {
      public ItemIcon(Minecraft ignored, String icon) {
         this(ItemStackJS.of(icon));
      }

      @Override
      public void draw(Minecraft mc, PoseStack pose, int x, int y, int size) {
         PoseStack m = RenderSystem.m_157191_();
         m.m_85836_();
         m.m_85837_((double)x - 2.0, (double)y + 2.0, 0.0);
         float s = (float)size / 16.0F;
         m.m_85841_(s, s, s);
         RenderSystem.m_157182_();
         mc.m_91291_().m_115218_(this.stack, -8, -8);
         m.m_85849_();
         RenderSystem.m_157182_();
      }
   }

   public static record TextureIcon(ResourceLocation texture) implements NotificationToast.ToastIcon {
      public TextureIcon(Minecraft ignored, String icon) {
         this(new ResourceLocation(icon));
      }

      @Override
      public void draw(Minecraft mc, PoseStack pose, int x, int y, int size) {
         RenderSystem.m_157456_(0, this.texture);
         RenderSystem.m_157427_(GameRenderer::m_172820_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         Matrix4f m = pose.m_85850_().m_85861_();
         int p0 = -size / 2;
         int p1 = p0 + size;
         BufferBuilder buf = Tesselator.m_85913_().m_85915_();
         buf.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85819_);
         buf.m_85982_(m, (float)(x + p0), (float)(y + p1), 0.0F).m_7421_(0.0F, 1.0F).m_6122_(255, 255, 255, 255).m_5752_();
         buf.m_85982_(m, (float)(x + p1), (float)(y + p1), 0.0F).m_7421_(1.0F, 1.0F).m_6122_(255, 255, 255, 255).m_5752_();
         buf.m_85982_(m, (float)(x + p1), (float)(y + p0), 0.0F).m_7421_(1.0F, 0.0F).m_6122_(255, 255, 255, 255).m_5752_();
         buf.m_85982_(m, (float)(x + p0), (float)(y + p0), 0.0F).m_7421_(0.0F, 0.0F).m_6122_(255, 255, 255, 255).m_5752_();
         BufferUploader.m_231202_(buf.m_231175_());
      }
   }

   public interface ToastIcon {
      void draw(Minecraft var1, PoseStack var2, int var3, int var4, int var5);
   }
}
