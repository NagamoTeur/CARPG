package dev.latvian.mods.kubejs.core;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.platform.IngredientPlatformHelper;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.Tags;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.mod.util.JsonSerializable;
import dev.latvian.mods.rhino.mod.util.NBTSerializable;
import dev.latvian.mods.rhino.mod.util.NBTUtils;
import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import dev.latvian.mods.rhino.util.SpecialEquality;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface ItemStackKJS extends SpecialEquality, NBTSerializable, JsonSerializable, IngredientSupplierKJS {
   default ItemStack kjs$self() {
      return (ItemStack)this;
   }

   default boolean specialEquals(Object o, boolean shallow) {
      if (o instanceof CharSequence) {
         return this.kjs$getId().equals(UtilsJS.getID(o.toString()));
      } else {
         return o instanceof ItemStack s ? this.kjs$equalsIgnoringCount(s) : this.kjs$equalsIgnoringCount(ItemStackJS.of(o));
      }
   }

   default boolean kjs$equalsIgnoringCount(ItemStack stack) {
      ItemStack self = this.kjs$self();
      if (self == stack) {
         return true;
      } else {
         return self.m_41619_() ? stack.m_41619_() : self.m_41720_() == stack.m_41720_() && ItemStack.m_41658_(self, stack);
      }
   }

   default ResourceLocation kjs$getIdLocation() {
      return this.kjs$self().m_41720_().kjs$getIdLocation();
   }

   default String kjs$getId() {
      return this.kjs$self().m_41720_().kjs$getId();
   }

   default Collection<ResourceLocation> kjs$getTags() {
      return Tags.byItem(this.kjs$self().m_41720_()).<ResourceLocation>map(TagKey::f_203868_).collect(Collectors.toSet());
   }

   default boolean kjs$hasTag(ResourceLocation tag) {
      return this.kjs$self().m_204117_(Tags.item(tag));
   }

   default boolean kjs$isBlock() {
      return this.kjs$self().m_41720_() instanceof BlockItem;
   }

   default ItemStack kjs$withCount(int c) {
      if (c > 0 && !this.kjs$self().m_41619_()) {
         ItemStack is = this.kjs$self().m_41777_();
         is.m_41764_(c);
         return is;
      } else {
         return ItemStack.f_41583_;
      }
   }

   default void kjs$removeTag() {
      this.kjs$self().m_41751_(null);
   }

   default String kjs$getNbtString() {
      return String.valueOf(this.kjs$self().m_41783_());
   }

   default ItemStack kjs$withNBT(CompoundTag nbt) {
      ItemStack is = this.kjs$self().m_41777_();
      CompoundTag tag0 = is.m_41783_();
      if (tag0 == null) {
         is.m_41751_(nbt);
      } else {
         is.m_41751_(tag0.m_128391_(nbt));
      }

      return is;
   }

   default ItemStack kjs$withName(@Nullable Component displayName) {
      ItemStack is = this.kjs$self().m_41777_();
      if (displayName != null) {
         is.m_41714_(displayName);
      } else {
         is.m_41787_();
      }

      return is;
   }

   default Map<String, Integer> kjs$getEnchantments() {
      HashMap<String, Integer> map = new HashMap<>();

      for (Entry<Enchantment, Integer> entry : EnchantmentHelper.m_44831_(this.kjs$self()).entrySet()) {
         ResourceLocation id = KubeJSRegistries.enchantments().getId(entry.getKey());
         if (id != null) {
            map.put(id.toString(), entry.getValue());
         }
      }

      return map;
   }

   default boolean kjs$hasEnchantment(Enchantment enchantment, int level) {
      return EnchantmentHelper.m_44843_(enchantment, this.kjs$self()) >= level;
   }

   @RemapForJS("enchant")
   default ItemStack kjs$enchantCopy(Map<?, ?> enchantments) {
      ItemStack is = this.kjs$self();

      for (Entry<?, ?> entry : enchantments.entrySet()) {
         Enchantment enchantment = (Enchantment)KubeJSRegistries.enchantments().get(UtilsJS.getMCID(null, entry.getKey()));
         if (enchantment != null && entry.getValue() instanceof Number number) {
            is = is.kjs$enchantCopy(enchantment, number.intValue());
         }
      }

      return is;
   }

   @RemapForJS("enchant")
   default ItemStack kjs$enchantCopy(Enchantment enchantment, int level) {
      ItemStack is = this.kjs$self().m_41777_();
      if (is.m_41720_() == Items.f_42690_) {
         EnchantedBookItem.m_41153_(is, new EnchantmentInstance(enchantment, level));
      } else {
         is.m_41663_(enchantment, level);
      }

      return is;
   }

   default String kjs$getMod() {
      return this.kjs$self().m_41720_().kjs$getMod();
   }

   @Deprecated
   default Ingredient kjs$ignoreNBT() {
      ConsoleJS console = ConsoleJS.getCurrent(ConsoleJS.SERVER);
      console.warn("You don't need to call .ignoreNBT() anymore, all item ingredients ignore NBT by default!");
      return this.kjs$self().m_41720_().kjs$asIngredient();
   }

   default Ingredient kjs$weakNBT() {
      return IngredientPlatformHelper.get().weakNBT(this.kjs$self());
   }

   default Ingredient kjs$strongNBT() {
      return IngredientPlatformHelper.get().strongNBT(this.kjs$self());
   }

   default boolean kjs$areItemsEqual(ItemStack other) {
      return this.kjs$self().m_41720_() == other.m_41720_();
   }

   default boolean kjs$isNBTEqual(ItemStack other) {
      if (this.kjs$self().m_41782_() == other.m_41782_()) {
         CompoundTag nbt = this.kjs$self().m_41783_();
         CompoundTag nbt2 = other.m_41783_();
         return Objects.equals(nbt, nbt2);
      } else {
         return false;
      }
   }

   default float kjs$getHarvestSpeed(@Nullable BlockContainerJS block) {
      return this.kjs$self().m_41691_(block == null ? Blocks.f_50016_.m_49966_() : block.getBlockState());
   }

   default float kjs$getHarvestSpeed() {
      return this.kjs$getHarvestSpeed(null);
   }

   @RemapForJS("toNBT")
   default CompoundTag toNBTJS() {
      return this.kjs$self().m_41739_(new CompoundTag());
   }

   default String kjs$getCreativeTab() {
      CreativeModeTab cat = this.kjs$self().m_41720_().m_41471_();
      return cat == null ? "" : cat.m_40783_();
   }

   default CompoundTag kjs$getTypeData() {
      return this.kjs$self().m_41720_().kjs$getTypeData();
   }

   default String kjs$toItemString() {
      ItemStack is = this.kjs$self();
      StringBuilder builder = new StringBuilder();
      int count = is.m_41613_();
      boolean hasNbt = is.m_41782_();
      if (count > 1 && !hasNbt) {
         builder.append('\'');
         builder.append(count);
         builder.append("x ");
         builder.append(this.kjs$getId());
         builder.append('\'');
      } else if (hasNbt) {
         builder.append("Item.of('");
         builder.append(is.kjs$getId());
         builder.append('\'');
         List<Pair<String, Integer>> enchants = null;
         if (count > 1) {
            builder.append(", ");
            builder.append(count);
         }

         CompoundTag t = is.m_41783_();
         if (t != null && !t.m_128456_()) {
            String key = is.m_41720_() == Items.f_42690_ ? "StoredEnchantments" : "Enchantments";
            if (t.m_128425_(key, 9)) {
               ListTag l = t.m_128437_(key, 10);
               enchants = new ArrayList<>(l.size());

               for (int i = 0; i < l.size(); i++) {
                  CompoundTag t1 = l.m_128728_(i);
                  enchants.add(Pair.of(t1.m_128461_("id"), t1.m_128451_("lvl")));
               }

               t = t.m_6426_();
               t.m_128473_(key);
               if (t.m_128456_()) {
                  t = null;
               }
            }
         }

         if (t != null) {
            builder.append(", ");
            NBTUtils.quoteAndEscapeForJS(builder, t.toString());
         }

         builder.append(')');
         if (enchants != null) {
            for (Pair<String, Integer> e : enchants) {
               builder.append(".enchant('");
               builder.append((String)e.getKey());
               builder.append("', ");
               builder.append(e.getValue());
               builder.append(')');
            }
         }
      } else {
         builder.append('\'');
         builder.append(this.kjs$getId());
         builder.append('\'');
      }

      return builder.toString();
   }

   @Override
   default Ingredient kjs$asIngredient() {
      return this.kjs$self().m_41720_().kjs$asIngredient();
   }

   default JsonObject toJsonJS() {
      JsonObject json = new JsonObject();
      json.addProperty("item", this.kjs$getId());
      json.addProperty("count", this.kjs$self().m_41613_());
      CompoundTag tag = this.kjs$self().m_41783_();
      if (tag != null) {
         json.addProperty("nbt", tag.toString());
      }

      return json;
   }

   default OutputItem kjs$withChance(double chance) {
      return OutputItem.of(this.kjs$self(), chance);
   }
}
