package dev.latvian.mods.kubejs.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.bindings.ItemWrapper;
import dev.latvian.mods.kubejs.item.FoodBuilder;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemStackKey;
import dev.latvian.mods.kubejs.item.MutableToolTier;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface ItemKJS extends IngredientSupplierKJS {
   @Nullable
   default ItemBuilder kjs$getItemBuilder() {
      throw new NoMixinException();
   }

   default Item kjs$self() {
      throw new NoMixinException();
   }

   default ResourceLocation kjs$getIdLocation() {
      return UtilsJS.UNKNOWN_ID;
   }

   default String kjs$getId() {
      return this.kjs$getIdLocation().toString();
   }

   default String kjs$getMod() {
      return this.kjs$getIdLocation().m_135827_();
   }

   default String kjs$getCreativeTab() {
      ResourceLocation id = KubeJSRegistries.items().getId((Item)this);
      return id == null ? "unknown" : id.m_135827_();
   }

   default void kjs$setItemBuilder(ItemBuilder b) {
      throw new NoMixinException();
   }

   default CompoundTag kjs$getTypeData() {
      throw new NoMixinException();
   }

   default void kjs$setMaxStackSize(int i) {
      throw new NoMixinException();
   }

   default void kjs$setMaxDamage(int i) {
      throw new NoMixinException();
   }

   default void kjs$setCraftingRemainder(Item i) {
      throw new NoMixinException();
   }

   default void kjs$setFireResistant(boolean b) {
      throw new NoMixinException();
   }

   default void kjs$setRarity(Rarity r) {
      throw new NoMixinException();
   }

   default void kjs$setBurnTime(int i) {
      throw new NoMixinException();
   }

   default void kjs$setFoodProperties(FoodProperties properties) {
      throw new NoMixinException();
   }

   default void kjs$setDigSpeed(float speed) {
      if (this instanceof DiggerItem diggerItem) {
         diggerItem.f_40980_ = speed;
      } else {
         throw new IllegalArgumentException("Item is not a digger item (axe, shovel, etc.)!");
      }
   }

   default float kjs$getDigSpeed() {
      if (this instanceof DiggerItem diggerItem) {
         return diggerItem.f_40980_;
      } else {
         throw new IllegalArgumentException("Item is not a digger item (axe, shovel, etc.)!");
      }
   }

   default void kjs$setTier(Consumer<MutableToolTier> c) {
      if (this instanceof TieredItem tiered) {
         tiered.f_43306_ = (Tier)Util.m_137469_(new MutableToolTier(tiered.f_43306_), c);
      } else {
         throw new IllegalArgumentException("Item is not a tool/tiered item!");
      }
   }

   default void kjs$setFoodProperties(Consumer<FoodBuilder> consumer) {
      FoodProperties fp = this.kjs$self().m_41473_();
      FoodBuilder builder = fp == null ? new FoodBuilder() : new FoodBuilder(fp);
      consumer.accept(builder);
      this.kjs$setFoodProperties(builder.build());
   }

   default void kjs$setAttackDamage(double attackDamage) {
      if (this instanceof ArmorItem) {
         throw new UnsupportedOperationException("Modifying attack damage of unsupported item: " + this);
      } else {
         this.kjs$removeAttribute(Attributes.f_22281_, ItemWrapper.KJS_BASE_ATTACK_DAMAGE_UUID);
         this.kjs$addAttribute(Attributes.f_22281_, ItemWrapper.KJS_BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamage, Operation.ADDITION);
      }
   }

   default void kjs$setAttackSpeed(double attackSpeed) {
      if (this instanceof ArmorItem) {
         throw new UnsupportedOperationException("Modifying attack speed of unsupported item: " + this);
      } else {
         this.kjs$removeAttribute(Attributes.f_22283_, ItemWrapper.KJS_BASE_ATTACK_SPEED_UUID);
         this.kjs$addAttribute(Attributes.f_22283_, ItemWrapper.KJS_BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeed, Operation.ADDITION);
      }
   }

   default void kjs$setArmorProtection(double armorProtection) {
      if (this instanceof ArmorItem armor) {
         UUID uuid = ItemWrapper.KJS_ARMOR_MODIFIER_UUID_PER_SLOT[armor.m_40402_().m_20749_()];
         this.kjs$removeAttribute(Attributes.f_22284_, uuid);
         this.kjs$addAttribute(Attributes.f_22284_, uuid, "Armor modifier", armorProtection, Operation.ADDITION);
      } else {
         throw new UnsupportedOperationException("Modifying armor value of unsupported item: " + this);
      }
   }

   default void kjs$setArmorToughness(double armorToughness) {
      if (this instanceof ArmorItem armor) {
         UUID uuid = ItemWrapper.KJS_ARMOR_MODIFIER_UUID_PER_SLOT[armor.m_40402_().m_20749_()];
         this.kjs$removeAttribute(Attributes.f_22285_, uuid);
         this.kjs$addAttribute(Attributes.f_22285_, uuid, "Armor modifier", armorToughness, Operation.ADDITION);
      } else {
         throw new UnsupportedOperationException("Modifying protection of unsupported item: " + this);
      }
   }

   default void kjs$setArmorKnockbackResistance(double knockbackResistance) {
      if (this instanceof ArmorItem armor) {
         UUID uuid = ItemWrapper.KJS_ARMOR_MODIFIER_UUID_PER_SLOT[armor.m_40402_().m_20749_()];
         this.kjs$removeAttribute(Attributes.f_22278_, uuid);
         this.kjs$addAttribute(Attributes.f_22278_, uuid, "Armor modifier", knockbackResistance, Operation.ADDITION);
      } else {
         throw new UnsupportedOperationException("Modifying protection of unsupported item: " + this);
      }
   }

   default void kjs$addAttribute(Attribute attribute, UUID uuid, String name, double d, Operation operation) {
      if (this instanceof ModifiableItemKJS modifiableItemKJS) {
         Multimap<Attribute, AttributeModifier> attributes = modifiableItemKJS.kjs$getMutableAttributeMap();
         attributes.put(attribute, new AttributeModifier(uuid, name, d, operation));
      } else {
         throw new UnsupportedOperationException("Adding attribute in unsupported item: " + this);
      }
   }

   default void kjs$removeAttribute(Attribute attribute, UUID uuid) {
      if (this instanceof ModifiableItemKJS modifiableItem) {
         Multimap<Attribute, AttributeModifier> attributes = modifiableItem.kjs$getMutableAttributeMap();
         Collection<AttributeModifier> modifiers = attributes.get(attribute);
         Optional<AttributeModifier> value = modifiers.stream().filter(modifier -> uuid.equals(modifier.m_22209_())).findFirst();
         value.ifPresent(modifier -> attributes.remove(attribute, modifier));
      } else {
         throw new UnsupportedOperationException("Removing attribute in unsupported item: " + this);
      }
   }

   default List<AttributeModifier> kjs$getAttributes(Attribute attribute) {
      if (this instanceof ModifiableItemKJS modifiableItem) {
         Multimap<Attribute, AttributeModifier> attributes = modifiableItem.kjs$getAttributeMap();
         return ImmutableList.copyOf(attributes.get(attribute));
      } else {
         throw new UnsupportedOperationException("Getting attribute in unsupported item: " + this);
      }
   }

   default ItemStackKey kjs$getTypeItemStackKey() {
      throw new NoMixinException();
   }
}
