package dev.latvian.mods.kubejs.bindings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.JSObjectType;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.mod.wrapper.ColorWrapper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;

@Info("The hub for all things text components. Format text to your hearts content!")
public class TextWrapper {
   @Info("Returns a Component of the input")
   public static MutableComponent of(@Nullable Object o) {
      o = UtilsJS.wrap(o, JSObjectType.ANY);
      if (o == null) {
         return Component.m_237113_("null");
      } else if (o instanceof MutableComponent) {
         return (MutableComponent)o;
      } else if (o instanceof Component component) {
         return component.m_6881_();
      } else if (o instanceof CharSequence || o instanceof Number || o instanceof Character) {
         return ofString(o.toString());
      } else if (o instanceof Enum<?> e) {
         return ofString(e.name());
      } else if (o instanceof StringTag tag) {
         String s = tag.m_7916_();
         if (s.startsWith("{") && s.endsWith("}")) {
            try {
               return Serializer.m_130701_(s);
            } catch (JsonParseException var13) {
               return Component.m_237113_("Error: " + var13);
            }
         } else {
            return ofString(s);
         }
      } else {
         if (o instanceof Map<?, ?> map && (map.containsKey("text") || map.containsKey("translate"))) {
            MutableComponent text;
            if (map.containsKey("text")) {
               text = ofString(map.get("text").toString());
            } else {
               Object[] with;
               if (map.get("with") instanceof Collection<?> a) {
                  with = new Object[a.size()];
                  int i = 0;

                  for (Object e1 : a) {
                     with[i] = e1;
                     if (with[i] instanceof MapJS || with[i] instanceof ListJS) {
                        with[i] = of(e1);
                     }

                     i++;
                  }
               } else {
                  with = new Object[0];
               }

               text = Component.m_237110_(map.get("translate").toString(), with);
            }

            if (map.containsKey("color")) {
               text.kjs$color(ColorWrapper.of(map.get("color")));
            }

            text.kjs$bold((Boolean)map.getOrDefault("bold", null));
            text.kjs$italic((Boolean)map.getOrDefault("italic", null));
            text.kjs$underlined((Boolean)map.getOrDefault("underlined", null));
            text.kjs$strikethrough((Boolean)map.getOrDefault("strikethrough", null));
            text.kjs$obfuscated((Boolean)map.getOrDefault("obfuscated", null));
            text.kjs$insertion((String)map.getOrDefault("insertion", null));
            text.kjs$font(map.containsKey("font") ? new ResourceLocation(map.get("font").toString()) : null);
            text.kjs$click(map.containsKey("click") ? clickEventOf(map.get("click")) : null);
            text.kjs$hover(map.containsKey("hover") ? of(map.get("hover")) : null);
            Object var20 = map.get("extra");
            if (var20 instanceof Iterable) {
               for (Object e : (Iterable)var20) {
                  text.m_7220_(of(e));
               }
            }

            return text;
         }

         if (!(o instanceof Iterable<?> list)) {
            return ofString(o.toString());
         }

         MutableComponent textx = Component.m_237119_();

         for (Object e1 : list) {
            textx.m_7220_(of(e1));
         }

         return textx;
      }
   }

   @Info("Returns a plain component of the string, or empty if it is an empty string")
   public static MutableComponent ofString(String s) {
      return s.isEmpty() ? Component.m_237119_() : Component.m_237113_(s);
   }

   @Info("Checks if the passed in component, and all its children are empty")
   public static boolean isEmpty(Component component) {
      return component.m_214077_() == ComponentContents.f_237124_ && component.m_7360_().isEmpty();
   }

   @Info("Returns a ClickEvent of the input")
   public static ClickEvent clickEventOf(Object o) {
      if (o == null) {
         return null;
      } else if (o instanceof ClickEvent) {
         return (ClickEvent)o;
      } else {
         JsonObject json = MapJS.json(o);
         if (json != null) {
            String action = GsonHelper.m_13906_(json, "action");
            String value = GsonHelper.m_13906_(json, "value");
            return new ClickEvent(Objects.requireNonNull(Action.m_130645_(action), "Invalid click event action %s!".formatted(action)), value);
         } else {
            String s = o.toString();
            String[] split = s.split(":", 2);
            String var4 = split[0];

            return switch (var4) {
               case "command" -> new ClickEvent(Action.RUN_COMMAND, split[1]);
               case "suggest_command" -> new ClickEvent(Action.SUGGEST_COMMAND, split[1]);
               case "copy" -> new ClickEvent(Action.COPY_TO_CLIPBOARD, split[1]);
               case "file" -> new ClickEvent(Action.OPEN_FILE, split[1]);
               default -> {
                  Action action = Action.m_130645_(split[0]);
                  yield action != null ? new ClickEvent(action, split[1]) : new ClickEvent(Action.OPEN_URL, s);
               }
            };
         }
      }
   }

   @Info("Returns a colorful representation of the input nbt. Useful for displaying NBT to the player")
   public static Component prettyPrintNbt(Tag tag) {
      return NbtUtils.m_178061_(tag);
   }

   @Info("Joins all components in the list with the separator component")
   public static MutableComponent join(MutableComponent separator, Iterable<? extends Component> texts) {
      MutableComponent joined = Component.m_237119_();
      boolean first = true;

      for (Component t : texts) {
         if (first) {
            first = false;
         } else if (!isEmpty(separator)) {
            joined.m_7220_(separator);
         }

         joined.m_7220_(t);
      }

      return joined;
   }

   @Info("Returns an empty component")
   public static MutableComponent empty() {
      return Component.m_237119_();
   }

   @Info("Joins all components")
   public static MutableComponent join(Component... texts) {
      return join(Component.m_237119_(), Arrays.asList(texts));
   }

   @Info("Returns a plain component of the passed in string, even if empty")
   public static MutableComponent string(String text) {
      return Component.m_237113_(text);
   }

   @Info("Returns a plain component of the input")
   public static MutableComponent literal(String text) {
      return Component.m_237113_(text);
   }

   @Info("Returns a translatable component of the input key")
   public static MutableComponent translate(String key) {
      return Component.m_237115_(key);
   }

   @Info("Returns a translatable component of the input key, with args of the objects")
   public static MutableComponent translate(String key, Object... objects) {
      return Component.m_237110_(key, objects);
   }

   @Info("Returns a translatable component of the input key")
   public static MutableComponent translatable(String key) {
      return Component.m_237115_(key);
   }

   @Info("Returns a translatable component of the input key, with args of the objects")
   public static MutableComponent translatable(String key, Object... objects) {
      return Component.m_237110_(key, objects);
   }

   @Info("Returns a keybinding component of the input keybinding descriptor")
   public static MutableComponent keybind(String keybind) {
      return Component.m_237117_(keybind);
   }

   @Info("Returns a score component of the input objective, for the provided selector")
   public static MutableComponent score(String selector, String objective) {
      return MutableComponent.m_237204_(new ScoreContents(selector, objective));
   }

   @Info("Returns a component displaying all entities matching the input selector")
   public static MutableComponent selector(String selector) {
      return MutableComponent.m_237204_(new SelectorContents(selector, Optional.empty()));
   }

   @Info("Returns a component displaying all entities matching the input selector, with a custom separator")
   public static MutableComponent selector(String selector, Component separator) {
      return MutableComponent.m_237204_(new SelectorContents(selector, Optional.of(separator)));
   }

   @Info("Returns a component of the input, colored black")
   public static MutableComponent black(Object text) {
      return of(text).kjs$black();
   }

   @Info("Returns a component of the input, colored dark blue")
   public static MutableComponent darkBlue(Object text) {
      return of(text).kjs$darkBlue();
   }

   @Info("Returns a component of the input, colored dark green")
   public static MutableComponent darkGreen(Object text) {
      return of(text).kjs$darkGreen();
   }

   @Info("Returns a component of the input, colored dark aqua")
   public static MutableComponent darkAqua(Object text) {
      return of(text).kjs$darkAqua();
   }

   @Info("Returns a component of the input, colored dark red")
   public static MutableComponent darkRed(Object text) {
      return of(text).kjs$darkRed();
   }

   @Info("Returns a component of the input, colored dark purple")
   public static MutableComponent darkPurple(Object text) {
      return of(text).kjs$darkPurple();
   }

   @Info("Returns a component of the input, colored gold")
   public static MutableComponent gold(Object text) {
      return of(text).kjs$gold();
   }

   @Info("Returns a component of the input, colored gray")
   public static MutableComponent gray(Object text) {
      return of(text).kjs$gray();
   }

   @Info("Returns a component of the input, colored dark gray")
   public static MutableComponent darkGray(Object text) {
      return of(text).kjs$darkGray();
   }

   @Info("Returns a component of the input, colored blue")
   public static MutableComponent blue(Object text) {
      return of(text).kjs$blue();
   }

   @Info("Returns a component of the input, colored green")
   public static MutableComponent green(Object text) {
      return of(text).kjs$green();
   }

   @Info("Returns a component of the input, colored aqua")
   public static MutableComponent aqua(Object text) {
      return of(text).kjs$aqua();
   }

   @Info("Returns a component of the input, colored red")
   public static MutableComponent red(Object text) {
      return of(text).kjs$red();
   }

   @Info("Returns a component of the input, colored light purple")
   public static MutableComponent lightPurple(Object text) {
      return of(text).kjs$lightPurple();
   }

   @Info("Returns a component of the input, colored yellow")
   public static MutableComponent yellow(Object text) {
      return of(text).kjs$yellow();
   }

   @Info("Returns a component of the input, colored white")
   public static MutableComponent white(Object text) {
      return of(text).kjs$white();
   }
}
