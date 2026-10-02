package dev.latvian.mods.kubejs.client.painter.screen;

import dev.latvian.mods.kubejs.client.painter.Painter;
import dev.latvian.mods.kubejs.client.painter.PainterObjectProperties;
import dev.latvian.mods.unit.FixedColorUnit;
import dev.latvian.mods.unit.Unit;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class AtlasTextureObject extends ScreenPainterObject {
   private Unit color = FixedColorUnit.WHITE;
   private ResourceLocation atlas = InventoryMenu.f_39692_;
   private ResourceLocation texture = null;
   private TextureAtlas textureAtlas;

   public AtlasTextureObject(Painter painter) {
   }

   @Override
   protected void load(PainterObjectProperties properties) {
      super.load(properties);
      this.color = properties.getColor("color", this.color);
      this.atlas = properties.getResourceLocation("atlas", this.atlas);
      this.texture = properties.getResourceLocation("texture", this.texture);
      this.textureAtlas = null;
   }

   @Override
   public void draw(PaintScreenEventJS event) {
      if (this.texture != null) {
         if (this.textureAtlas == null) {
            this.textureAtlas = event.mc.m_91304_().m_119428_(this.atlas);
         }

         if (this.textureAtlas != null) {
            float aw = this.w.getFloat(event);
            float ah = this.h.getFloat(event);
            float ax = event.alignX(this.x.getFloat(event), aw, this.alignX);
            float ay = event.alignY(this.y.getFloat(event), ah, this.alignY);
            float az = this.z.getFloat(event);
            TextureAtlasSprite sprite = this.textureAtlas.m_118316_(this.texture);
            float u0 = sprite.m_118409_();
            float v0 = sprite.m_118411_();
            float u1 = sprite.m_118410_();
            float v1 = sprite.m_118412_();
            event.resetShaderColor();
            event.setPositionColorTextureShader();
            event.setShaderTexture(this.atlas);
            event.beginQuads(true);
            event.rectangle(ax, ay, az, aw, ah, this.color.getInt(event), u0, v0, u1, v1);
            event.end();
         }
      }
   }
}
