package dev.latvian.mods.kubejs.client.painter.screen;

import dev.latvian.mods.kubejs.client.painter.Painter;
import dev.latvian.mods.kubejs.client.painter.PainterObject;
import dev.latvian.mods.kubejs.client.painter.PainterObjectProperties;
import dev.latvian.mods.kubejs.client.painter.PainterObjectStorage;
import dev.latvian.mods.unit.FixedNumberUnit;
import dev.latvian.mods.unit.Unit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.nbt.CompoundTag;

public class ScreenGroup extends ScreenPainterObject {
   private final PainterObjectStorage storage;
   private Unit scaleX = FixedNumberUnit.ONE;
   private Unit scaleY = FixedNumberUnit.ONE;
   private Unit paddingW = FixedNumberUnit.ZERO;
   private Unit paddingH = FixedNumberUnit.ZERO;

   public ScreenGroup(Painter painter) {
      this.storage = new PainterObjectStorage(painter);
   }

   @Override
   protected void load(PainterObjectProperties properties) {
      super.load(properties);
      if (properties.tag.m_128423_("children") instanceof CompoundTag tag) {
         this.storage.handle(tag);
      }

      this.paddingW = properties.getUnit("paddingW", this.paddingW);
      this.paddingH = properties.getUnit("paddingH", this.paddingH);
      if (properties.hasAny("scale")) {
         this.scaleX = this.scaleY = properties.getUnit("scale", FixedNumberUnit.ONE);
      } else {
         this.scaleX = properties.getUnit("scaleX", this.scaleX);
         this.scaleY = properties.getUnit("scaleY", this.scaleY);
      }
   }

   @Override
   public void preDraw(PaintScreenEventJS event) {
      this.w = FixedNumberUnit.ZERO;
      this.h = FixedNumberUnit.ZERO;
      Collection<PainterObject> objects = this.storage.getObjects();
      if (!objects.isEmpty()) {
         List<Unit> wunits = new ArrayList<>(objects.size());
         List<Unit> hunits = new ArrayList<>(objects.size());

         for (PainterObject object : objects) {
            if (object instanceof ScreenPainterObject s) {
               s.preDraw(event);
               wunits.add(s.x.add(s.w));
               hunits.add(s.y.add(s.h));
            }
         }

         this.w = new MultiMaxFunc(wunits).add(this.paddingW);
         this.h = new MultiMaxFunc(hunits).add(this.paddingH);
      }
   }

   @Override
   public void draw(PaintScreenEventJS event) {
      float ax = event.alignX(this.x.getFloat(event), this.w.getFloat(event), this.alignX);
      float ay = event.alignY(this.y.getFloat(event), this.h.getFloat(event), this.alignY);
      float az = this.z.getFloat(event);
      event.push();
      event.translate((double)ax, (double)ay, (double)az);
      event.scale(this.scaleX.getFloat(event), this.scaleY.getFloat(event), 1.0F);

      for (PainterObject object : this.storage.getObjects()) {
         if (object instanceof ScreenPainterObject s) {
            s.draw(event);
         }
      }

      event.pop();
   }
}
