package dev.latvian.mods.kubejs.client.painter;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.mods.kubejs.bindings.event.ClientEvents;
import dev.latvian.mods.kubejs.client.ClientEventJS;
import dev.latvian.mods.kubejs.client.painter.screen.AtlasTextureObject;
import dev.latvian.mods.kubejs.client.painter.screen.GradientObject;
import dev.latvian.mods.kubejs.client.painter.screen.ItemObject;
import dev.latvian.mods.kubejs.client.painter.screen.PaintScreenEventJS;
import dev.latvian.mods.kubejs.client.painter.screen.RectangleObject;
import dev.latvian.mods.kubejs.client.painter.screen.ScreenGroup;
import dev.latvian.mods.kubejs.client.painter.screen.ScreenPainterObject;
import dev.latvian.mods.kubejs.client.painter.screen.TextObject;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.unit.FixedNumberUnit;
import dev.latvian.mods.unit.MutableNumberUnit;
import dev.latvian.mods.unit.Unit;
import dev.latvian.mods.unit.UnitContext;
import dev.latvian.mods.unit.UnitVariables;
import dev.latvian.mods.unit.VariableSet;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import org.jetbrains.annotations.Nullable;

public class Painter implements UnitVariables {
   public static final Painter INSTANCE = new Painter("global");
   public static final int DRAW_ALWAYS = 0;
   public static final int DRAW_INGAME = 1;
   public static final int DRAW_GUI = 2;
   public static final int CENTER = 0;
   public static final int LEFT = -1;
   public static final int RIGHT = 1;
   public static final int TOP = -1;
   public static final int BOTTOM = 1;
   public final String id;
   private final Object lock;
   private final Map<String, PainterFactory> objectRegistry;
   private final PainterObjectStorage storage;
   private ScreenPainterObject[] screenObjects;
   public final UnitContext unitContext;
   private final VariableSet variables;
   public final MutableNumberUnit deltaUnit;
   public final MutableNumberUnit screenWidthUnit;
   public final MutableNumberUnit screenHeightUnit;
   public final MutableNumberUnit mouseXUnit;
   public final MutableNumberUnit mouseYUnit;

   public Painter(String id) {
      this.id = id;
      this.lock = new Object();
      this.objectRegistry = new HashMap<>();
      this.storage = new PainterObjectStorage(this);
      this.screenObjects = null;
      this.unitContext = UnitContext.DEFAULT.sub();
      this.variables = new VariableSet();
      this.deltaUnit = this.variables.setMutable("$D", 1.0);
      this.variables.set("$SX", 0.0);
      this.variables.set("$SY", 0.0);
      this.screenWidthUnit = this.variables.setMutable("$SW", 1.0);
      this.screenHeightUnit = this.variables.setMutable("$SH", 1.0);
      this.mouseXUnit = this.variables.setMutable("$MX", 0.0);
      this.mouseYUnit = this.variables.setMutable("$MY", 0.0);
      this.variables.set("$delta", this.deltaUnit);
      this.variables.set("$screenW", this.screenWidthUnit);
      this.variables.set("$screenH", this.screenHeightUnit);
      this.variables.set("$mouseX", this.mouseXUnit);
      this.variables.set("$mouseY", this.mouseYUnit);
   }

   public Unit unitOf(Context cx, Object o) {
      return this.unitOf(ConsoleJS.getCurrent(cx), o);
   }

   public Unit unitOf(ConsoleJS console, Object o) {
      if (o instanceof Unit) {
         return (Unit)o;
      } else if (o instanceof Number number) {
         return FixedNumberUnit.of((double)number.floatValue());
      } else {
         try {
            if (o instanceof String) {
               return this.unitContext.parse(o.toString());
            }

            if (o instanceof StringTag tag) {
               return this.unitContext.parse(tag.m_7916_());
            }
         } catch (Exception var5) {
            console.error("Failed to parse Unit: " + var5);
         }

         return FixedNumberUnit.ZERO;
      }
   }

   @HideFromJS
   public void registerObject(String name, PainterFactory supplier) {
      this.objectRegistry.put(name, supplier);
   }

   public void registerBuiltinObjects() {
      this.registerObject("screen_group", ScreenGroup::new);
      this.registerObject("rectangle", RectangleObject::new);
      this.registerObject("text", TextObject::new);
      this.registerObject("atlas_texture", AtlasTextureObject::new);
      this.registerObject("gradient", GradientObject::new);
      this.registerObject("item", ItemObject::new);
   }

   @Nullable
   public PainterObject make(String type) {
      PainterFactory supplier = this.objectRegistry.get(type);
      return supplier == null ? null : supplier.create(this);
   }

   @Nullable
   public PainterObject getObject(String key) {
      synchronized (this.lock) {
         return this.storage.getObject(key);
      }
   }

   public void paint(CompoundTag root) {
      Minecraft.m_91087_().execute(() -> {
         synchronized (this.lock) {
            this.storage.handle(root);
            this.screenObjects = null;
            if (ClientEvents.PAINTER_UPDATED.hasListeners()) {
               ClientEvents.PAINTER_UPDATED.post(ScriptType.CLIENT, new ClientEventJS());
            }
         }
      });
   }

   public void clear() {
      Minecraft.m_91087_().execute(() -> {
         synchronized (this.lock) {
            this.storage.clear();
            this.screenObjects = null;
            if (ClientEvents.PAINTER_UPDATED.hasListeners()) {
               ClientEvents.PAINTER_UPDATED.post(ScriptType.CLIENT, new ClientEventJS());
            }
         }
      });
   }

   @HideFromJS
   public ScreenPainterObject[] getScreenObjects() {
      if (this.screenObjects == null) {
         synchronized (this.lock) {
            this.screenObjects = this.storage.createScreenObjects();
         }
      }

      return this.screenObjects;
   }

   public void setVariable(String key, Unit variable) {
      this.variables.set(key, variable);
   }

   public VariableSet getVariables() {
      return this.variables;
   }

   public void inGameScreenDraw(PoseStack matrices, float delta) {
      Minecraft mc = Minecraft.m_91087_();
      if (mc.f_91074_ != null && !mc.f_91066_.f_92063_ && mc.f_91080_ == null) {
         if (ClientEvents.PAINT_SCREEN.hasListeners() || this.getScreenObjects().length != 0) {
            RenderSystem.m_69478_();
            RenderSystem.m_69482_();
            PaintScreenEventJS event = new PaintScreenEventJS(mc, matrices, delta);
            this.deltaUnit.set((double)delta);
            this.screenWidthUnit.set((double)event.width);
            this.screenHeightUnit.set((double)event.height);
            this.mouseXUnit.set((double)event.width / 2.0);
            this.mouseYUnit.set((double)event.height / 2.0);
            ClientEvents.PAINT_SCREEN.post(ScriptType.CLIENT, event);

            for (ScreenPainterObject object : this.getScreenObjects()) {
               if (object.visible.getBoolean(event) && (object.draw == 0 || object.draw == 1)) {
                  object.preDraw(event);
               }
            }

            for (ScreenPainterObject objectx : this.getScreenObjects()) {
               if (objectx.visible.getBoolean(event) && (objectx.draw == 0 || objectx.draw == 1)) {
                  objectx.draw(event);
               }
            }
         }
      }
   }

   public void guiScreenDraw(Screen screen, PoseStack matrices, int mouseX, int mouseY, float delta) {
      Minecraft mc = Minecraft.m_91087_();
      if (mc.f_91074_ != null) {
         if (ClientEvents.PAINT_SCREEN.hasListeners() || this.getScreenObjects().length != 0) {
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            PaintScreenEventJS event = new PaintScreenEventJS(mc, screen, matrices, mouseX, mouseY, delta);
            this.deltaUnit.set((double)delta);
            this.screenWidthUnit.set((double)event.width);
            this.screenHeightUnit.set((double)event.height);
            this.mouseXUnit.set((double)mouseX);
            this.mouseYUnit.set((double)mouseY);
            event.resetShaderColor();
            ClientEvents.PAINT_SCREEN.post(ScriptType.CLIENT, event);

            for (ScreenPainterObject object : this.getScreenObjects()) {
               if (object.visible.getBoolean(event) && (object.draw == 0 || object.draw == 2)) {
                  object.preDraw(event);
               }
            }

            for (ScreenPainterObject objectx : this.getScreenObjects()) {
               if (objectx.visible.getBoolean(event) && (objectx.draw == 0 || objectx.draw == 2)) {
                  objectx.draw(event);
               }
            }
         }
      }
   }
}
