package dev.latvian.mods.kubejs.block.custom;

import dev.latvian.mods.kubejs.client.ModelGenerator;
import dev.latvian.mods.kubejs.client.VariantBlockStateGenerator;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;

public class WoodenPressurePlateBlockBuilder extends ShapedBlockBuilder {
   public WoodenPressurePlateBlockBuilder(ResourceLocation i) {
      super(i, "_wooden_pressure_plate", "_pressure_plate");
      this.noCollision();
      this.tagBoth(BlockTags.f_13099_.f_203868_());
      this.tagBoth(BlockTags.f_13100_.f_203868_());
   }

   public Block createObject() {
      return new PressurePlateBlock(Sensitivity.EVERYTHING, this.createProperties());
   }

   @Override
   protected void generateBlockStateJson(VariantBlockStateGenerator bs) {
      bs.variant("powered=true", v -> v.model(this.newID("block/", "_down").toString()));
      bs.variant("powered=false", v -> v.model(this.newID("block/", "_up").toString()));
   }

   @Override
   protected void generateBlockModelJsons(AssetJsonGenerator generator) {
      String texture = this.textures.get("texture").getAsString();
      generator.blockModel(this.newID("", "_down"), m -> {
         m.parent("minecraft:block/pressure_plate_down");
         m.texture("texture", texture);
      });
      generator.blockModel(this.newID("", "_up"), m -> {
         m.parent("minecraft:block/pressure_plate_up");
         m.texture("texture", texture);
      });
   }

   @Override
   protected void generateItemModelJson(ModelGenerator m) {
      m.parent(this.newID("block/", "_up").toString());
   }
}
