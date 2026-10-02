package io.redspace.ironsspellbooks.api.spells;

import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class SchoolType {
   final ResourceLocation id;
   final TagKey<Item> focus;
   final Component displayName;
   final Style displayStyle;
   final LazyOptional<Attribute> powerAttribute;
   final LazyOptional<Attribute> resistanceAttribute;
   final LazyOptional<SoundEvent> defaultCastSound;

   public SchoolType(
      ResourceLocation id,
      TagKey<Item> focus,
      Component displayName,
      LazyOptional<Attribute> powerAttribute,
      LazyOptional<Attribute> resistanceAttribute,
      LazyOptional<SoundEvent> defaultCastSound
   ) {
      this.id = id;
      this.focus = focus;
      this.displayName = displayName;
      this.displayStyle = displayName.m_7383_();
      this.powerAttribute = powerAttribute;
      this.resistanceAttribute = resistanceAttribute;
      this.defaultCastSound = defaultCastSound;
   }

   public double getResistanceFor(LivingEntity livingEntity) {
      Attribute resistanceAttribute = (Attribute)this.resistanceAttribute.orElse(null);
      return resistanceAttribute != null ? livingEntity.m_21133_(resistanceAttribute) : 1.0;
   }

   public double getPowerFor(LivingEntity livingEntity) {
      Attribute powerAttribute = (Attribute)this.powerAttribute.orElse(null);
      return powerAttribute != null ? livingEntity.m_21133_(powerAttribute) : 1.0;
   }

   public SoundEvent getCastSound() {
      return (SoundEvent)this.defaultCastSound.resolve().get();
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public Component getDisplayName() {
      return this.displayName;
   }

   public boolean isFocus(ItemStack itemStack) {
      return itemStack.m_204117_(this.focus);
   }

   public Vector3f getTargetingColor() {
      return Utils.deconstructRGB(this.displayStyle.m_131135_().m_131265_());
   }
}
