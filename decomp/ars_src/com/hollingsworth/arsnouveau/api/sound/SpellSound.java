package com.hollingsworth.arsnouveau.api.sound;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SpellSound {
   private ResourceLocation id;
   private SoundEvent soundEvent;
   private Component soundName;

   public SpellSound(ResourceLocation id, SoundEvent soundEvent, Component soundName) {
      this.id = id;
      this.soundEvent = soundEvent;
      this.soundName = soundName;
   }

   public SpellSound(SoundEvent soundEvent, Component soundName) {
      this(soundEvent.m_11660_(), soundEvent, soundName);
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public void setId(ResourceLocation id) {
      this.id = id;
   }

   public SoundEvent getSoundEvent() {
      return this.soundEvent;
   }

   public void setSoundEvent(SoundEvent soundEvent) {
      this.soundEvent = soundEvent;
   }

   public Component getSoundName() {
      return this.soundName;
   }

   public void setSoundName(Component soundName) {
      this.soundName = soundName;
   }

   public CompoundTag serialize() {
      CompoundTag tag = new CompoundTag();
      tag.m_128359_("id", this.id.toString());
      return tag;
   }

   @Nullable
   public static SpellSound fromTag(CompoundTag tag) {
      if (!tag.m_128441_("id")) {
         return null;
      } else {
         ResourceLocation id = new ResourceLocation(tag.m_128461_("id"));
         return ArsNouveauAPI.getInstance().getSpellSoundsRegistry().get(id);
      }
   }

   public ResourceLocation getTexturePath() {
      return new ResourceLocation(this.id.m_135827_(), "textures/sounds/" + this.id.m_135815_() + ".png");
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SpellSound that = (SpellSound)o;
         return Objects.equals(this.id, that.id) && Objects.equals(this.soundEvent, that.soundEvent) && Objects.equals(this.soundName, that.soundName);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.id, this.soundEvent, this.soundName);
   }

   @Override
   public String toString() {
      return "SpellSound{id=" + this.id + ", soundEvent=" + this.soundEvent + ", soundName=" + this.soundName + "}";
   }
}
