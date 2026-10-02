package dev.latvian.mods.kubejs.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class MaterialJS {
   private final String id;
   private final Material minecraftMaterial;
   private final SoundType sound;

   public MaterialJS(String i, Material m, SoundType s) {
      this.id = i;
      this.minecraftMaterial = m;
      this.sound = s;
   }

   public String getId() {
      return this.id;
   }

   public Material getMinecraftMaterial() {
      return this.minecraftMaterial;
   }

   public SoundType getSound() {
      return this.sound;
   }
}
