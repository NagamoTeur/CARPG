package dev.latvian.mods.kubejs.item.custom;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class SwordItemBuilder extends HandheldItemBuilder {
   public SwordItemBuilder(ResourceLocation i) {
      super(i, 3.0F, -2.4F);
   }

   public Item createObject() {
      return new SwordItem(this.toolTier, (int)this.attackDamageBaseline, this.speedBaseline, this.createItemProperties()) {
         private boolean modified = false;

         {
            this.f_43267_ = ArrayListMultimap.create(this.f_43267_);
         }

         public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
            if (!this.modified) {
               this.modified = true;
               SwordItemBuilder.this.attributes.forEach((r, m) -> this.f_43267_.put((Attribute)KubeJSRegistries.attributes().get(r), m));
            }

            return super.m_7167_(equipmentSlot);
         }
      };
   }
}
