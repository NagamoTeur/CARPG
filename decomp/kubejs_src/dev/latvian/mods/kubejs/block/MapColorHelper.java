package dev.latvian.mods.kubejs.block;

import com.mojang.math.Vector3f;
import dev.latvian.mods.rhino.Undefined;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

public record MapColorHelper(int id, String name, MaterialColor color, Vector3f rgb) implements Function<BlockState, MaterialColor> {
   public static final Map<String, MapColorHelper> NAME_MAP = new HashMap<>(64);
   public static final Map<Integer, MapColorHelper> ID_MAP = new HashMap<>(64);
   public static final MapColorHelper NONE = add("none", MaterialColor.f_76398_);

   private static MapColorHelper add(String id, MaterialColor color) {
      float r = (float)(color.f_76396_ >> 16 & 0xFF) / 255.0F;
      float g = (float)(color.f_76396_ >> 8 & 0xFF) / 255.0F;
      float b = (float)(color.f_76396_ & 0xFF) / 255.0F;
      MapColorHelper helper = new MapColorHelper(color.f_76397_, id, color, new Vector3f(r, g, b));
      NAME_MAP.put(id, helper);
      ID_MAP.put(color.f_76397_, helper);
      return helper;
   }

   public static MaterialColor of(Object o) {
      if (o == null || Undefined.isUndefined(o)) {
         return MaterialColor.f_76398_;
      } else if (o instanceof MaterialColor) {
         return (MaterialColor)o;
      } else if (o instanceof CharSequence s) {
         if (s.isEmpty()) {
            return MaterialColor.f_76398_;
         } else {
            return s.charAt(0) == '#' ? findClosest(Integer.decode(s.toString())).color : NAME_MAP.getOrDefault(s.toString().toLowerCase(), NONE).color;
         }
      } else if (o instanceof Number n) {
         return findClosest(n.intValue()).color;
      } else {
         return o instanceof DyeColor c ? c.m_41069_() : MaterialColor.f_76398_;
      }
   }

   public static MapColorHelper reverse(MaterialColor c) {
      return ID_MAP.getOrDefault(c.f_76397_, NONE);
   }

   public static MapColorHelper findClosest(int rgbi) {
      Vector3f rgb = new Vector3f((float)(rgbi >> 16 & 0xFF) / 255.0F, (float)(rgbi >> 8 & 0xFF) / 255.0F, (float)(rgbi & 0xFF) / 255.0F);
      MapColorHelper closest = null;
      float lastDist = Float.MAX_VALUE;

      for (MapColorHelper helper : NAME_MAP.values()) {
         if (helper.color != MaterialColor.f_76398_) {
            float dist = distSq(helper.rgb, rgb);
            if (dist < lastDist) {
               closest = helper;
               lastDist = dist;
            }
         }
      }

      return closest == null ? NONE : closest;
   }

   private static float distSq(Vector3f a, Vector3f b) {
      return (a.m_122239_() - b.m_122239_()) * (a.m_122239_() - b.m_122239_())
         + (a.m_122260_() - b.m_122260_()) * (a.m_122260_() - b.m_122260_())
         + (a.m_122269_() - b.m_122269_()) * (a.m_122269_() - b.m_122269_());
   }

   public MaterialColor apply(BlockState blockState) {
      return this.color;
   }

   static {
      add("grass", MaterialColor.f_76399_);
      add("sand", MaterialColor.f_76400_);
      add("wool", MaterialColor.f_76401_);
      add("fire", MaterialColor.f_76402_);
      add("ice", MaterialColor.f_76403_);
      add("metal", MaterialColor.f_76404_);
      add("plant", MaterialColor.f_76405_);
      add("snow", MaterialColor.f_76406_);
      add("clay", MaterialColor.f_76407_);
      add("dirt", MaterialColor.f_76408_);
      add("stone", MaterialColor.f_76409_);
      add("water", MaterialColor.f_76410_);
      add("wood", MaterialColor.f_76411_);
      add("quartz", MaterialColor.f_76412_);
      add("color_orange", MaterialColor.f_76413_);
      add("color_magenta", MaterialColor.f_76414_);
      add("color_light_blue", MaterialColor.f_76415_);
      add("color_yellow", MaterialColor.f_76416_);
      add("color_light_green", MaterialColor.f_76417_);
      add("color_pink", MaterialColor.f_76418_);
      add("color_gray", MaterialColor.f_76419_);
      add("color_light_gray", MaterialColor.f_76420_);
      add("color_cyan", MaterialColor.f_76421_);
      add("color_purple", MaterialColor.f_76422_);
      add("color_blue", MaterialColor.f_76361_);
      add("color_brown", MaterialColor.f_76362_);
      add("color_green", MaterialColor.f_76363_);
      add("color_red", MaterialColor.f_76364_);
      add("color_black", MaterialColor.f_76365_);
      add("gold", MaterialColor.f_76366_);
      add("diamond", MaterialColor.f_76367_);
      add("lapis", MaterialColor.f_76368_);
      add("emerald", MaterialColor.f_76369_);
      add("podzol", MaterialColor.f_76370_);
      add("nether", MaterialColor.f_76371_);
      add("terracotta_white", MaterialColor.f_76372_);
      add("terracotta_orange", MaterialColor.f_76373_);
      add("terracotta_magenta", MaterialColor.f_76374_);
      add("terracotta_light_blue", MaterialColor.f_76375_);
      add("terracotta_yellow", MaterialColor.f_76376_);
      add("terracotta_light_green", MaterialColor.f_76377_);
      add("terracotta_pink", MaterialColor.f_76378_);
      add("terracotta_gray", MaterialColor.f_76379_);
      add("terracotta_light_gray", MaterialColor.f_76380_);
      add("terracotta_cyan", MaterialColor.f_76381_);
      add("terracotta_purple", MaterialColor.f_76382_);
      add("terracotta_blue", MaterialColor.f_76383_);
      add("terracotta_brown", MaterialColor.f_76384_);
      add("terracotta_green", MaterialColor.f_76385_);
      add("terracotta_red", MaterialColor.f_76386_);
      add("terracotta_black", MaterialColor.f_76388_);
      add("crimson_nylium", MaterialColor.f_76389_);
      add("crimson_stem", MaterialColor.f_76390_);
      add("crimson_hyphae", MaterialColor.f_76391_);
      add("warped_nylium", MaterialColor.f_76392_);
      add("warped_stem", MaterialColor.f_76393_);
      add("warped_hyphae", MaterialColor.f_76394_);
      add("warped_wart_block", MaterialColor.f_76395_);
      add("deepslate", MaterialColor.f_164534_);
      add("raw_iron", MaterialColor.f_164535_);
      add("glow_lichen", MaterialColor.f_164536_);
   }
}
