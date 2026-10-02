package com.hollingsworth.arsnouveau.api.sound;

import com.hollingsworth.arsnouveau.setup.SoundRegistry;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;

public class ConfiguredSpellSound {
   public static ConfiguredSpellSound EMPTY = new ConfiguredSpellSound(SoundRegistry.EMPTY_SPELL_SOUND, 1.0F, 1.0F);
   public static ConfiguredSpellSound DEFAULT = new ConfiguredSpellSound(SoundRegistry.DEFAULT_SPELL_SOUND, 1.0F, 1.0F);
   @Nullable
   public SpellSound sound;
   public float volume;
   public float pitch;

   public ConfiguredSpellSound(@Nullable SpellSound sound) {
      this(sound, 1.0F, 1.0F);
   }

   public ConfiguredSpellSound(@Nullable SpellSound sound, float volume, float pitch) {
      this.sound = sound;
      this.volume = volume;
      this.pitch = pitch;
   }

   public CompoundTag serialize() {
      CompoundTag tag = new CompoundTag();
      tag.m_128365_("soundTag", this.sound == null ? new CompoundTag() : this.sound.serialize());
      tag.m_128350_("volume", this.volume);
      tag.m_128350_("pitch", this.pitch);
      return tag;
   }

   public static ConfiguredSpellSound fromTag(CompoundTag tag) {
      SpellSound sound = SpellSound.fromTag(tag.m_128469_("soundTag"));
      float volume = tag.m_128457_("volume");
      float pitch = tag.m_128457_("pitch");
      return new ConfiguredSpellSound(sound, volume, pitch);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ConfiguredSpellSound that = (ConfiguredSpellSound)o;
         return Float.compare(that.volume, this.volume) == 0 && Float.compare(that.pitch, this.pitch) == 0 && Objects.equals(this.sound, that.sound);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.sound, this.volume, this.pitch);
   }

   @Override
   public String toString() {
      return "ConfiguredSpellSound{sound=" + this.sound + ", volume=" + this.volume + ", pitch=" + this.pitch + "}";
   }
}
