package dev.latvian.mods.kubejs.client.painter.screen;

import dev.latvian.mods.kubejs.client.painter.PainterObject;
import dev.latvian.mods.kubejs.client.painter.PainterObjectProperties;
import dev.latvian.mods.unit.FixedNumberUnit;
import dev.latvian.mods.unit.Unit;

public abstract class ScreenPainterObject extends PainterObject {
   private static final Unit DEFAULT_SIZE = FixedNumberUnit.SIXTEEN;
   public Unit x = FixedNumberUnit.ZERO;
   public Unit y = FixedNumberUnit.ZERO;
   public Unit z = FixedNumberUnit.ZERO;
   public Unit w = DEFAULT_SIZE;
   public Unit h = DEFAULT_SIZE;
   public int alignX = -1;
   public int alignY = -1;
   public int draw = 1;

   public void preDraw(PaintScreenEventJS event) {
   }

   public abstract void draw(PaintScreenEventJS var1);

   @Override
   protected void load(PainterObjectProperties properties) {
      super.load(properties);
      this.x = properties.getUnit("x", this.x).add(properties.getUnit("moveX", FixedNumberUnit.ZERO));
      this.y = properties.getUnit("y", this.y).add(properties.getUnit("moveY", FixedNumberUnit.ZERO));
      this.z = properties.getUnit("z", this.z);
      this.w = properties.getUnit("w", this.w).add(properties.getUnit("expandW", FixedNumberUnit.ZERO));
      this.h = properties.getUnit("h", this.h).add(properties.getUnit("expandH", FixedNumberUnit.ZERO));
      if (properties.hasString("draw")) {
         String var2 = properties.getString("draw", "ingame");
         switch (var2) {
            case "always":
               this.draw = 0;
               break;
            case "gui":
               this.draw = 2;
               break;
            default:
               this.draw = 1;
         }
      }

      if (properties.hasString("alignX")) {
         String var4 = properties.getString("alignX", "left");
         switch (var4) {
            case "right":
               this.alignX = 1;
               break;
            case "center":
               this.alignX = 0;
               break;
            default:
               this.alignX = -1;
         }
      }

      if (properties.hasString("alignY")) {
         String var5 = properties.getString("alignY", "top");
         switch (var5) {
            case "bottom":
               this.alignY = 1;
               break;
            case "center":
               this.alignY = 0;
               break;
            default:
               this.alignY = -1;
         }
      }
   }
}
