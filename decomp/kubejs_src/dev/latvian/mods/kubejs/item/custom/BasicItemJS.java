package dev.latvian.mods.kubejs.item.custom;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class BasicItemJS extends Item {
   private final ItemBuilder itemBuilder;
   private final Multimap<Attribute, AttributeModifier> attributes;
   private boolean modified = false;

   public BasicItemJS(ItemBuilder p) {
      super(p.createItemProperties());
      this.itemBuilder = p;
      if (p.burnTime > 0) {
         FuelRegistry.register(p.burnTime, new ItemLike[]{this});
      }

      this.attributes = ArrayListMultimap.create();
   }

   public ItemBuilder kjs$getItemBuilder() {
      return this.itemBuilder;
   }

   public Component m_7626_(ItemStack itemStack) {
      return this.itemBuilder.displayName != null && this.itemBuilder.formattedDisplayName ? this.itemBuilder.displayName : super.m_7626_(itemStack);
   }

   public void m_6787_(CreativeModeTab category, NonNullList<ItemStack> stacks) {
      if (this.itemBuilder.subtypes != null && category.equals(this.itemBuilder.group)) {
         stacks.addAll(this.itemBuilder.subtypes.apply(new ItemStack(this)));
      } else {
         super.m_6787_(category, stacks);
      }
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot slot) {
      if (!this.modified) {
         this.itemBuilder.attributes.forEach((r, m) -> this.attributes.put((Attribute)KubeJSRegistries.attributes().get(r), m));
         this.modified = true;
      }

      return slot == EquipmentSlot.MAINHAND ? this.attributes : super.m_7167_(slot);
   }

   public static class Builder extends ItemBuilder {
      public Builder(ResourceLocation i) {
         super(i);
      }

      public Item createObject() {
         return new BasicItemJS(this);
      }
   }
}
