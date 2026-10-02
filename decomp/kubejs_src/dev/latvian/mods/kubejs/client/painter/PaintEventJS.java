package dev.latvian.mods.kubejs.client.painter;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import dev.latvian.mods.kubejs.client.ClientEventJS;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PaintEventJS extends ClientEventJS {
   public final Minecraft mc;
   public final Font font;
   public final PoseStack matrices;
   public final Tesselator tesselator;
   public final BufferBuilder buffer;
   public final float delta;
   public final Screen screen;

   public PaintEventJS(Minecraft m, PoseStack p, float d, @Nullable Screen s) {
      this.mc = m;
      this.font = this.mc.f_91062_;
      this.matrices = p;
      this.tesselator = Tesselator.m_85913_();
      this.buffer = this.tesselator.m_85915_();
      this.delta = d;
      this.screen = s;
   }

   public void push() {
      this.matrices.m_85836_();
   }

   public void pop() {
      this.matrices.m_85849_();
   }

   public void translate(double x, double y, double z) {
      this.matrices.m_85837_(x, y, z);
   }

   public void scale(float x, float y, float z) {
      this.matrices.m_85841_(x, y, z);
   }

   public void multiply(Quaternion q) {
      this.matrices.m_85845_(q);
   }

   public void multiplyWithMatrix(Matrix4f m) {
      this.matrices.m_166854_(m);
   }

   public Matrix4f getMatrix() {
      return this.matrices.m_85850_().m_85861_();
   }

   public void bindTextureForSetup(ResourceLocation tex) {
      this.mc.m_91097_().m_174784_(tex);
   }

   public void setShaderColor(float r, float g, float b, float a) {
      RenderSystem.m_157429_(r, g, b, a);
   }

   public void resetShaderColor() {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void setShaderTexture(ResourceLocation tex) {
      RenderSystem.m_157456_(0, tex);
   }

   public void begin(Mode type, VertexFormat format) {
      this.buffer.m_166779_(type, format);
   }

   public void beginQuads(VertexFormat format) {
      this.begin(Mode.QUADS, format);
   }

   public void beginQuads(boolean texture) {
      this.beginQuads(texture ? DefaultVertexFormat.f_85818_ : DefaultVertexFormat.f_85815_);
   }

   public void vertex(Matrix4f m, float x, float y, float z, int col) {
      this.buffer.m_85982_(m, x, y, z).m_6122_(col >> 16 & 0xFF, col >> 8 & 0xFF, col & 0xFF, col >> 24 & 0xFF).m_5752_();
   }

   public void vertex(Matrix4f m, float x, float y, float z, int col, float u, float v) {
      this.buffer.m_85982_(m, x, y, z).m_6122_(col >> 16 & 0xFF, col >> 8 & 0xFF, col & 0xFF, col >> 24 & 0xFF).m_7421_(u, v).m_5752_();
   }

   public void end() {
      this.tesselator.m_85914_();
   }

   public void setShaderInstance(Supplier<ShaderInstance> shader) {
      RenderSystem.m_157427_(shader);
   }

   public void setPositionColorShader() {
      RenderSystem.m_157427_(GameRenderer::m_172811_);
   }

   public void setPositionColorTextureShader() {
      RenderSystem.m_157427_(GameRenderer::m_172814_);
   }
}
