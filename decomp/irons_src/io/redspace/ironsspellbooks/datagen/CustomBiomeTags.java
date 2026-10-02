package io.redspace.ironsspellbooks.datagen;

import io.redspace.ironsspellbooks.registries.ResigterBiomeTags;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class CustomBiomeTags extends TagsProvider<Biome> {
   public CustomBiomeTags(DataGenerator generator, ExistingFileHelper helper) {
      super(generator, BuiltinRegistries.f_123865_, "irons_spellbooks", helper);
   }

   protected void m_6577_() {
      ForgeRegistries.BIOMES.getValues().forEach(biome -> this.m_206424_(ResigterBiomeTags.HAS_TOWER).m_126582_(biome));
   }

   public String m_6055_() {
      return "irons_spellbooks Tags";
   }
}
