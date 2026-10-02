package com.hollingsworth.arsnouveau.api.familiar;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class PersistentFamiliarData<T extends Entity> {
   public Component name;
   public String color;
   public ItemStack cosmetic;

   public PersistentFamiliarData(CompoundTag tag) {
      this.name = tag.m_128441_("name") ? Serializer.m_130701_(tag.m_128461_("name")) : null;
      this.color = tag.m_128441_("color") ? tag.m_128461_("color") : null;
      this.cosmetic = tag.m_128441_("cosmetic") ? ItemStack.m_41712_(tag.m_128469_("cosmetic")) : null;
   }

   public CompoundTag toTag(T entity, CompoundTag tag) {
      if (this.name != null) {
         tag.m_128359_("name", Serializer.m_130703_(this.name));
      }

      if (this.color != null) {
         tag.m_128359_("color", this.color);
      }

      if (this.cosmetic != null) {
         tag.m_128365_("cosmetic", this.cosmetic.m_41739_(new CompoundTag()));
      }

      return tag;
   }
}
