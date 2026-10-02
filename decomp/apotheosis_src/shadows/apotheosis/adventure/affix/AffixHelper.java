package shadows.apotheosis.adventure.affix;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.util.CachedObject;
import shadows.placebo.util.StepFunction;
import shadows.placebo.util.CachedObject.CachedObjectSource;

public class AffixHelper {
   public static final ResourceLocation AFFIX_CACHED_OBJECT = Apotheosis.loc("affixes");
   public static final String DISPLAY = "display";
   public static final String LORE = "Lore";
   public static final String AFFIX_DATA = "affix_data";
   public static final String AFFIXES = "affixes";
   public static final String RARITY = "rarity";
   public static final String NAME = "name";
   public static final String CATEGORY = "category";

   public static void applyAffix(ItemStack stack, AffixInstance affix) {
      Map<Affix, AffixInstance> affixes = getAffixes(stack);
      affixes.put(affix.affix(), affix);
      setAffixes(stack, affixes);
   }

   public static void setAffixes(ItemStack stack, Map<Affix, AffixInstance> affixes) {
      CompoundTag afxData = stack.m_41698_("affix_data");
      CompoundTag affixesTag = new CompoundTag();

      for (AffixInstance inst : affixes.values()) {
         affixesTag.m_128350_(inst.affix().getId().toString(), inst.level());
      }

      afxData.m_128365_("affixes", affixesTag);
   }

   public static void setName(ItemStack stack, Component name) {
      CompoundTag afxData = stack.m_41698_("affix_data");
      afxData.m_128359_("name", Serializer.m_130703_(name));
   }

   @Nullable
   public static Component getName(ItemStack stack) {
      if (!stack.m_41782_()) {
         return null;
      } else {
         CompoundTag afxData = stack.m_41737_("affix_data");
         return afxData == null ? null : Serializer.m_130701_(afxData.m_128461_("name"));
      }
   }

   public static Map<Affix, AffixInstance> getAffixes(ItemStack stack) {
      return (Map<Affix, AffixInstance>)CachedObjectSource.getOrCreate(
         stack, AFFIX_CACHED_OBJECT, AffixHelper::getAffixesImpl, CachedObject.hashSubkey("affix_data")
      );
   }

   public static Map<Affix, AffixInstance> getAffixesImpl(ItemStack stack) {
      Map<Affix, AffixInstance> map = new HashMap<>();
      if (!hasAffixes(stack)) {
         return map;
      } else {
         CompoundTag afxData = stack.m_41737_("affix_data");
         if (afxData != null && afxData.m_128441_("affixes")) {
            CompoundTag affixes = afxData.m_128469_("affixes");
            LootRarity rarity = getRarity(afxData);
            if (rarity == null) {
               rarity = LootRarity.COMMON;
            }

            LootCategory cat = LootCategory.forItem(stack);

            for (String key : affixes.m_128431_()) {
               Affix affix = (Affix)AffixManager.INSTANCE.getValue(new ResourceLocation(key));
               if (affix != null && affix.canApplyTo(stack, cat, rarity)) {
                  float lvl = affixes.m_128457_(key);
                  map.put(affix, new AffixInstance(affix, stack, rarity, lvl));
               }
            }
         }

         return map;
      }
   }

   public static Stream<AffixInstance> streamAffixes(ItemStack stack) {
      return getAffixes(stack).values().stream();
   }

   public static boolean hasAffixes(ItemStack stack) {
      return stack.m_41782_() && !stack.m_41783_().m_128469_("affix_data").m_128469_("affixes").m_128456_();
   }

   public static void addLore(ItemStack stack, Component lore) {
      CompoundTag display = stack.m_41698_("display");
      ListTag tag = display.m_128437_("Lore", 8);
      tag.add(StringTag.m_129297_(Serializer.m_130703_(lore)));
      display.m_128365_("Lore", tag);
   }

   public static void setRarity(ItemStack stack, LootRarity rarity) {
      Component comp = Component.m_237110_("%s", new Object[]{Component.m_237113_("")}).m_130948_(Style.f_131099_.m_131148_(rarity.color()));
      CompoundTag afxData = stack.m_41698_("affix_data");
      afxData.m_128359_("name", Serializer.m_130703_(comp));
      afxData.m_128359_("rarity", rarity.id());
   }

   public static void copyFrom(ItemStack stack, Entity entity) {
      if (stack.m_41782_() && stack.m_41737_("affix_data") != null) {
         CompoundTag afxData = stack.m_41737_("affix_data").m_6426_();
         afxData.m_128359_("category", LootCategory.forItem(stack).getName());
         entity.getPersistentData().m_128365_("affix_data", afxData);
      }
   }

   @Nullable
   public static LootCategory getShooterCategory(Entity entity) {
      CompoundTag afxData = entity.getPersistentData().m_128469_("affix_data");
      return afxData != null && afxData.m_128441_("category") ? LootCategory.byId(afxData.m_128461_("category")) : null;
   }

   public static Map<Affix, AffixInstance> getAffixes(AbstractArrow arrow) {
      Map<Affix, AffixInstance> map = new HashMap<>();
      CompoundTag afxData = arrow.getPersistentData().m_128469_("affix_data");
      SocketHelper.loadSocketAffix(arrow, map);
      if (afxData != null && afxData.m_128441_("affixes")) {
         CompoundTag affixes = afxData.m_128469_("affixes");
         LootRarity rarity = getRarity(afxData);
         if (rarity == null) {
            rarity = LootRarity.COMMON;
         }

         for (String key : affixes.m_128431_()) {
            Affix affix = (Affix)AffixManager.INSTANCE.getValue(new ResourceLocation(key));
            if (affix != null) {
               float lvl = affixes.m_128457_(key);
               map.put(affix, new AffixInstance(affix, ItemStack.f_41583_, rarity, lvl));
            }
         }
      }

      return map;
   }

   public static Stream<AffixInstance> streamAffixes(AbstractArrow arrow) {
      return getAffixes(arrow).values().stream();
   }

   @Nullable
   public static LootRarity getRarity(ItemStack stack) {
      if (!stack.m_41782_()) {
         return null;
      } else {
         CompoundTag afxData = stack.m_41737_("affix_data");
         return getRarity(afxData);
      }
   }

   @Nullable
   public static LootRarity getRarity(@Nullable CompoundTag afxData) {
      if (afxData != null) {
         try {
            return LootRarity.byId(afxData.m_128461_("rarity"));
         } catch (IllegalArgumentException var2) {
            afxData.m_128473_("rarity");
            return null;
         }
      } else {
         return null;
      }
   }

   public static Collection<Affix> byType(AffixType type) {
      return AffixManager.INSTANCE.getTypeMap().get(type);
   }

   public static StepFunction step(float min, int steps, float step) {
      return new StepFunction(min, steps, step);
   }

   public static Map<LootRarity, StepFunction> readValues(JsonObject obj) {
      return (Map<LootRarity, StepFunction>)Affix.GSON.fromJson(obj, (new TypeToken<Map<LootRarity, StepFunction>>() {
      }).getType());
   }

   public static Set<LootCategory> readTypes(JsonArray json) {
      return (Set<LootCategory>)Affix.GSON.fromJson(json, (new TypeToken<Set<LootCategory>>() {
      }).getType());
   }
}
