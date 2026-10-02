package dev.latvian.mods.kubejs.client.painter.screen;

import dev.latvian.mods.kubejs.bindings.TextWrapper;
import dev.latvian.mods.kubejs.client.painter.Painter;
import dev.latvian.mods.kubejs.client.painter.PainterObjectProperties;
import dev.latvian.mods.unit.FixedBooleanUnit;
import dev.latvian.mods.unit.FixedColorUnit;
import dev.latvian.mods.unit.FixedNumberUnit;
import dev.latvian.mods.unit.Unit;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.FormattedCharSequence;

public class TextObject extends ScreenPainterObject {
   private FormattedCharSequence[] text = new FormattedCharSequence[0];
   private Unit shadow = FixedBooleanUnit.FALSE;
   private Unit scale = FixedNumberUnit.ONE;
   private Unit color = FixedColorUnit.WHITE;
   private Unit centered = FixedBooleanUnit.FALSE;
   private Unit lineSpacing = FixedNumberUnit.TEN;
   private float[] textWidth = new float[0];

   public TextObject(Painter painter) {
   }

   @Override
   protected void load(PainterObjectProperties properties) {
      super.load(properties);
      if (properties.tag.m_128423_("textLines") instanceof ListTag list) {
         this.text = new FormattedCharSequence[list.size()];

         for (int i = 0; i < list.size(); i++) {
            this.text[i] = TextWrapper.of(list.get(i)).m_7532_();
         }
      } else {
         this.text = new FormattedCharSequence[]{TextWrapper.of(properties.tag.m_128423_("text")).m_7532_()};
      }

      this.shadow = properties.getUnit("shadow", this.shadow);
      this.scale = properties.getUnit("scale", this.scale);
      this.color = properties.getColor("color", this.color);
      this.centered = properties.getUnit("centered", this.centered);
      this.lineSpacing = properties.getUnit("lineSpacing", this.lineSpacing);
      this.textWidth = new float[this.text.length];
   }

   @Override
   public void preDraw(PaintScreenEventJS event) {
      float maxTextWidth = 0.0F;

      for (int i = 0; i < this.text.length; i++) {
         this.textWidth[i] = event.font.m_92865_().m_92336_(this.text[i]);
         maxTextWidth = Math.max(maxTextWidth, this.textWidth[i]);
      }

      this.w = this.scale.mul(FixedNumberUnit.of((double)maxTextWidth));
      this.h = this.scale.mul(this.lineSpacing).mul(FixedNumberUnit.of((double)this.text.length));
   }

   @Override
   public void draw(PaintScreenEventJS event) {
      float ls = this.lineSpacing.getFloat(event);
      float ax = event.alignX(this.x.getFloat(event), this.w.getFloat(event), this.alignX);
      float ay = event.alignY(this.y.getFloat(event), this.h.getFloat(event), this.alignY);
      float az = this.z.getFloat(event);
      boolean c = this.centered.getBoolean(event);
      boolean s = this.shadow.getBoolean(event);
      event.push();
      event.translate((double)ax, (double)ay, (double)az);
      event.scale(this.scale.getFloat(event));

      for (int i = 0; i < this.text.length; i++) {
         event.rawText(this.text[i], c ? -(this.textWidth[i] / 2.0F) : 0.0F, (float)i * ls, this.color.getInt(event), s);
      }

      event.pop();
   }
}
