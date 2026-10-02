package dev.latvian.mods.kubejs.recipe.component;

import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public record BlockStateComponent(ComponentRole crole) implements RecipeComponent<BlockState> {
   public static final RecipeComponent<BlockState> INPUT = new BlockStateComponent(ComponentRole.INPUT);
   public static final RecipeComponent<BlockState> OUTPUT = new BlockStateComponent(ComponentRole.OUTPUT);
   public static final RecipeComponent<BlockState> BLOCK = new BlockStateComponent(ComponentRole.OTHER);

   @Override
   public ComponentRole role() {
      return this.crole;
   }

   @Override
   public String componentType() {
      return "block_state";
   }

   @Override
   public Class<?> componentClass() {
      return BlockState.class;
   }

   public JsonPrimitive write(RecipeJS recipe, BlockState value) {
      return new JsonPrimitive(BlockStateParser.m_116769_(value));
   }

   public BlockState read(RecipeJS recipe, Object from) {
      if (from instanceof BlockState) {
         return (BlockState)from;
      } else if (from instanceof Block b) {
         return b.m_49966_();
      } else {
         return from instanceof JsonPrimitive json ? UtilsJS.parseBlockState(json.getAsString()) : UtilsJS.parseBlockState(String.valueOf(from));
      }
   }

   public boolean isInput(RecipeJS recipe, BlockState value, ReplacementMatch match) {
      if (this.crole.isInput() && match instanceof BlockStatePredicate m2 && m2.test(value)) {
         return true;
      }

      return false;
   }

   public boolean isOutput(RecipeJS recipe, BlockState value, ReplacementMatch match) {
      if (this.crole.isOutput() && match instanceof BlockStatePredicate m2 && m2.test(value)) {
         return true;
      }

      return false;
   }

   public String checkEmpty(RecipeKey<BlockState> key, BlockState value) {
      return value.m_60734_() == Blocks.f_50016_ ? "Block '" + key.name + "' can't be empty!" : "";
   }

   @Override
   public String toString() {
      return this.componentType();
   }
}
