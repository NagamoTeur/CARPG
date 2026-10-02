package dev.latvian.mods.kubejs.client.painter.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import dev.latvian.mods.kubejs.client.painter.PaintEventJS;
import dev.latvian.mods.kubejs.client.painter.Painter;
import dev.latvian.mods.unit.UnitVariables;
import dev.latvian.mods.unit.VariableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class PaintScreenEventJS extends PaintEventJS implements UnitVariables {
   public final int mouseX;
   public final int mouseY;
   public final int width;
   public final int height;
   public final boolean inventory;

   public PaintScreenEventJS(Minecraft m, Screen s, PoseStack ps, int mx, int my, float d) {
      super(m, ps, d, s);
      this.mouseX = mx;
      this.mouseY = my;
      this.width = this.mc.m_91268_().m_85445_();
      this.height = this.mc.m_91268_().m_85446_();
      this.inventory = true;
   }

   public PaintScreenEventJS(Minecraft m, PoseStack ps, float d) {
      super(m, ps, d, null);
      this.mouseX = -1;
      this.mouseY = -1;
      this.width = this.mc.m_91268_().m_85445_();
      this.height = this.mc.m_91268_().m_85446_();
      this.inventory = false;
   }

   public float alignX(float x, float w, int alignX) {
      return switch (alignX) {
         case 0 -> ((float)this.width - w) / 2.0F + x;
         case 1 -> (float)this.width - w + x;
         default -> x;
      };
   }

   public float alignY(float y, float h, int alignY) {
      return switch (alignY) {
         case 0 -> ((float)this.height - h) / 2.0F + y;
         case 1 -> (float)this.height - h + y;
         default -> y;
      };
   }

   public void translate(double x, double y) {
      this.translate(x, y, 0.0);
   }

   public void scale(float x, float y) {
      this.scale(x, y, 1.0F);
   }

   public void scale(float scale) {
      this.scale(scale, scale, 1.0F);
   }

   public void rotateDeg(float angle) {
      this.matrices.m_85845_(Vector3f.f_122227_.m_122240_(angle));
   }

   public void rotateRad(float angle) {
      this.matrices.m_85845_(Vector3f.f_122227_.m_122270_(angle));
   }

   public void rectangle(float x, float y, float z, float w, float h, int color) {
      Matrix4f m = this.getMatrix();
      this.vertex(m, x + w, y, z, color);
      this.vertex(m, x, y, z, color);
      this.vertex(m, x, y + h, z, color);
      this.vertex(m, x + w, y + h, z, color);
   }

   public void rectangle(float x, float y, float z, float w, float h, int color, float u0, float v0, float u1, float v1) {
      Matrix4f m = this.getMatrix();
      this.vertex(m, x + w, y, z, color, u1, v0);
      this.vertex(m, x, y, z, color, u0, v0);
      this.vertex(m, x, y + h, z, color, u0, v1);
      this.vertex(m, x + w, y + h, z, color, u1, v1);
   }

   public void text(Component text, float x, float y, int color, boolean shadow) {
      this.rawText(text.m_7532_(), x, y, color, shadow);
   }

   public void rawText(FormattedCharSequence text, float x, float y, int color, boolean shadow) {
      if (shadow) {
         this.font.m_92744_(this.matrices, text, x, y, color);
      } else {
         this.font.m_92877_(this.matrices, text, x, y, color);
      }
   }

   public VariableSet getVariables() {
      return Painter.INSTANCE.getVariables();
   }
}
