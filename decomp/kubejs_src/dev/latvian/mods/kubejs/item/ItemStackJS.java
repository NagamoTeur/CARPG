package dev.latvian.mods.kubejs.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.platform.IngredientPlatformHelper;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.Lazy;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Wrapper;
import dev.latvian.mods.rhino.mod.util.NBTUtils;
import dev.latvian.mods.rhino.regexp.NativeRegExp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public interface ItemStackJS {
   Map<String, ItemStack> PARSE_CACHE = new HashMap<>();
   ItemStack[] EMPTY_ARRAY = new ItemStack[0];
   Lazy<List<String>> CACHED_ITEM_TYPE_LIST = Lazy.of(() -> {
      ArrayList<String> cachedItemTypeList = new ArrayList<>();

      for (ResourceLocation id : KubeJSRegistries.items().getIds()) {
         cachedItemTypeList.add(id.toString());
      }

      return cachedItemTypeList;
   });
   Lazy<Map<ResourceLocation, NonNullList<ItemStack>>> CACHED_ITEM_MAP = Lazy.of(
      () -> {
         HashMap<ResourceLocation, NonNullList<ItemStack>> map = new HashMap<>();
         NonNullList<ItemStack> stackList = NonNullList.m_122779_();

         for (Item item : KubeJSRegistries.items()) {
            try {
               item.m_6787_(CreativeModeTab.f_40754_, stackList);
            } catch (Throwable var5) {
            }
         }

         for (ItemStack stack : stackList) {
            if (!stack.m_41619_()) {
               map.computeIfAbsent(stack.m_41720_().kjs$getIdLocation(), _rl -> NonNullList.m_122779_()).add(stack.kjs$withCount(1));
            }
         }

         for (String itemId : CACHED_ITEM_TYPE_LIST.get()) {
            ResourceLocation itemRl = new ResourceLocation(itemId);
            map.computeIfAbsent(
               itemRl, _rl -> NonNullList.m_122783_(ItemStack.f_41583_, new ItemStack[]{new ItemStack((ItemLike)KubeJSRegistries.items().get(itemRl))})
            );
         }

         return map;
      }
   );
   Lazy<List<ItemStack>> CACHED_ITEM_LIST = Lazy.of(() -> CACHED_ITEM_MAP.get().values().stream().flatMap(Collection::stream).toList());

   static ItemStack of(@Nullable Object o) {
      if (o instanceof Wrapper w) {
         o = w.unwrap();
      }

      if (o == null || o == ItemStack.f_41583_ || o == Items.f_41852_) {
         return ItemStack.f_41583_;
      } else if (o instanceof ItemStack stack) {
         return stack.m_41619_() ? ItemStack.f_41583_ : stack;
      } else if (o instanceof OutputItem out) {
         return out.item;
      } else if (o instanceof Ingredient ingr) {
         return ingr.kjs$getFirst();
      } else if (o instanceof ResourceLocation id) {
         Item item = (Item)KubeJSRegistries.items().get(id);
         if (item != null && item != Items.f_41852_) {
            return item.m_7968_();
         } else if (RecipeJS.itemErrors) {
            throw new RecipeExceptionJS("Item '" + id + "' not found!").error();
         } else {
            return ItemStack.f_41583_;
         }
      } else if (o instanceof ItemLike itemLike) {
         return new ItemStack(itemLike.m_5456_());
      } else if (o instanceof JsonElement json) {
         return resultFromRecipeJson(json);
      } else if (o instanceof StringTag tag) {
         return of(tag.m_7916_());
      } else if (o instanceof Pattern || o instanceof NativeRegExp) {
         Pattern reg = UtilsJS.parseRegex(o);
         return reg != null ? IngredientPlatformHelper.get().regex(reg).kjs$getFirst() : ItemStack.f_41583_;
      } else if (o instanceof CharSequence) {
         String os = o.toString().trim();
         String s = os;
         ItemStack cached = PARSE_CACHE.get(os);
         if (cached != null) {
            return cached.m_41619_() ? ItemStack.f_41583_ : cached.m_41777_();
         } else {
            int count = 1;
            int spaceIndex = os.indexOf(32);
            if (spaceIndex >= 2 && os.indexOf(120) == spaceIndex - 1) {
               count = Integer.parseInt(os.substring(0, spaceIndex - 1));
               s = os.substring(spaceIndex + 1);
            }

            cached = parse(s);
            cached.m_41764_(count);
            PARSE_CACHE.put(os, cached);
            return cached.m_41777_();
         }
      } else {
         Map<?, ?> map = MapJS.of(o);
         if (map != null) {
            if (map.containsKey("item")) {
               ResourceLocation idx = UtilsJS.getMCID(null, map.get("item").toString());
               Item item = (Item)KubeJSRegistries.items().get(idx);
               if (item == Items.f_41852_) {
                  if (RecipeJS.itemErrors) {
                     throw new RecipeExceptionJS("Item '" + idx + "' not found!").error();
                  }

                  return ItemStack.f_41583_;
               }

               ItemStack stack = new ItemStack(item);
               if (map.get("count") instanceof Number number) {
                  stack.m_41764_(number.intValue());
               }

               if (map.containsKey("nbt")) {
                  stack.m_41751_(NBTUtils.toTagCompound(map.get("nbt")));
               }

               return stack;
            }

            if (map.get("tag") instanceof CharSequence s) {
               ItemStack stackx = IngredientPlatformHelper.get().tag(s.toString()).kjs$getFirst();
               if (map.containsKey("count")) {
                  stackx.m_41764_(UtilsJS.parseInt(map.get("count"), 1));
               }

               return stackx;
            }
         }

         return ItemStack.f_41583_;
      }
   }

   static ItemStack parse(String s) {
      if (s.isEmpty() || s.equals("-") || s.equals("air") || s.equals("minecraft:air")) {
         return ItemStack.f_41583_;
      } else if (s.startsWith("#")) {
         return IngredientPlatformHelper.get().tag(s.substring(1)).kjs$getFirst();
      } else if (s.startsWith("@")) {
         return IngredientPlatformHelper.get().mod(s.substring(1)).kjs$getFirst();
      } else if (s.startsWith("%")) {
         CreativeModeTab group = UtilsJS.findCreativeTab(s.substring(1));
         if (group != null) {
            return IngredientPlatformHelper.get().creativeTab(group).kjs$getFirst();
         } else if (RecipeJS.itemErrors) {
            throw new RecipeExceptionJS("Item group '" + s.substring(1) + "' not found!").error();
         } else {
            return ItemStack.f_41583_;
         }
      } else {
         Pattern reg = UtilsJS.parseRegex(s);
         if (reg != null) {
            return IngredientPlatformHelper.get().regex(reg).kjs$getFirst();
         } else {
            int spaceIndex = s.indexOf(32);
            String id = spaceIndex == -1 ? s : s.substring(0, spaceIndex);
            Item item = (Item)KubeJSRegistries.items().get(new ResourceLocation(id));
            if (item != Items.f_41852_) {
               ItemStack stack = new ItemStack(item);
               if (spaceIndex != -1) {
                  String tagStr = s.substring(spaceIndex + 1);
                  if (tagStr.length() >= 2 && tagStr.charAt(0) == '{') {
                     stack.m_41751_(NBTUtils.toTagCompound(tagStr));
                  }
               }

               return stack;
            } else if (RecipeJS.itemErrors) {
               throw new RecipeExceptionJS("Item '" + id + "' not found!").error();
            } else {
               return ItemStack.f_41583_;
            }
         }
      }
   }

   static Item getRawItem(Context cx, @Nullable Object o) {
      if (o == null) {
         return Items.f_41852_;
      } else if (o instanceof ItemLike item) {
         return item.m_5456_();
      } else {
         if (o instanceof CharSequence) {
            String s = o.toString();
            if (s.isEmpty()) {
               return Items.f_41852_;
            }

            if (s.charAt(0) != '#') {
               return (Item)KubeJSRegistries.items().get(UtilsJS.getMCID(cx, s));
            }
         }

         return of(o).m_41720_();
      }
   }

   static ItemStack resultFromRecipeJson(@Nullable JsonElement json) {
      if (json == null || json.isJsonNull()) {
         return ItemStack.f_41583_;
      } else if (json.isJsonPrimitive()) {
         return of(json.getAsString());
      } else {
         if (json instanceof JsonObject jsonObj) {
            ItemStack stack = null;
            if (jsonObj.has("item")) {
               stack = of(jsonObj.get("item").getAsString());
            } else if (jsonObj.has("tag")) {
               stack = IngredientPlatformHelper.get().tag(jsonObj.get("tag").getAsString()).kjs$getFirst();
            }

            if (stack != null) {
               if (jsonObj.has("count")) {
                  stack.m_41764_(jsonObj.get("count").getAsInt());
               } else if (jsonObj.has("amount")) {
                  stack.m_41764_(jsonObj.get("amount").getAsInt());
               }

               if (jsonObj.has("nbt")) {
                  JsonElement element = jsonObj.get("nbt");
                  if (element.isJsonObject()) {
                     stack.m_41751_(NBTUtils.toTagCompound(element));
                  } else {
                     stack.m_41751_(NBTUtils.toTagCompound(element.getAsString()));
                  }
               }

               return stack;
            }
         }

         return ItemStack.f_41583_;
      }
   }

   static String toItemString(Object object) {
      return of(object).kjs$toItemString();
   }

   static List<ItemStack> getList() {
      return CACHED_ITEM_LIST.get();
   }

   static List<String> getTypeList() {
      return CACHED_ITEM_TYPE_LIST.get();
   }

   static Map<ResourceLocation, NonNullList<ItemStack>> getTypeToStacks() {
      return CACHED_ITEM_MAP.get();
   }

   static void clearAllCaches() {
      CACHED_ITEM_LIST.forget();
      CACHED_ITEM_TYPE_LIST.forget();
      PARSE_CACHE.clear();
      InputItem.PARSE_CACHE.clear();
   }
}
