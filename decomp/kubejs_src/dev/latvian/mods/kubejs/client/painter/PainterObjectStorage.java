package dev.latvian.mods.kubejs.client.painter;

import dev.latvian.mods.kubejs.client.painter.screen.ScreenPainterObject;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.unit.FixedNumberUnit;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.Nullable;

public class PainterObjectStorage {
   private static final ScreenPainterObject[] NO_SCREEN_OBJECTS = new ScreenPainterObject[0];
   public final Painter painter;
   private final Map<String, PainterObject> objects = new LinkedHashMap<>();

   public PainterObjectStorage(Painter p) {
      this.painter = p;
   }

   @Nullable
   public PainterObject getObject(String key) {
      return this.objects.get(key);
   }

   public Collection<PainterObject> getObjects() {
      return (Collection<PainterObject>)(this.objects.isEmpty() ? List.of() : this.objects.values());
   }

   public void handle(CompoundTag root) {
      if (root.m_128441_("bulk")) {
         ListTag bulk = root.m_128437_("bulk", 10);

         for (int i = 0; i < bulk.size(); i++) {
            this.handle(bulk.m_128728_(i));
         }
      } else {
         for (String key : root.m_128431_()) {
            CompoundTag tag = root.m_128469_(key);
            if (key.equals("*")) {
               if (tag.m_128471_("remove")) {
                  this.objects.clear();
               } else {
                  for (PainterObject o : this.objects.values()) {
                     o.update(tag);
                  }
               }
            } else if (key.equals("$")) {
               for (String k : tag.m_128431_()) {
                  if (tag.m_128425_(k, 99)) {
                     this.painter.setVariable(k, FixedNumberUnit.of((double)tag.m_128457_(k)));
                  } else {
                     this.painter.setVariable(k, this.painter.unitOf(ConsoleJS.CLIENT, tag.m_128423_(k)));
                  }
               }
            } else {
               PainterObject o = this.objects.get(key);
               if (o != null) {
                  o.update(tag);
               } else if (key.indexOf(32) != -1) {
                  ConsoleJS.CLIENT.error("Painter id can't contain spaces!");
               } else {
                  String type = tag.m_128461_("type");
                  PainterObject o1 = this.painter.make(type);
                  if (o1 != null) {
                     o1.id = key;
                     o1.parent = this;
                     o1.update(tag);
                     this.objects.put(key, o1);
                  } else {
                     ConsoleJS.CLIENT.error("Unknown Painter type: " + type);
                  }
               }
            }
         }
      }
   }

   public void clear() {
      this.objects.clear();
   }

   public ScreenPainterObject[] createScreenObjects() {
      return this.objects.isEmpty()
         ? NO_SCREEN_OBJECTS
         : this.objects.values().stream().filter(o -> o instanceof ScreenPainterObject).map(o -> (ScreenPainterObject)o).toArray(ScreenPainterObject[]::new);
   }

   public void remove(String id) {
      this.objects.remove(id);
   }
}
