package dev.latvian.mods.kubejs.core;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.MaterialJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

@RemapPrefixForJS("kjs$")
public interface BlockKJS extends BlockBuilderProvider {
   default void kjs$setBlockBuilder(BlockBuilder b) {
      throw new NoMixinException();
   }

   default ResourceLocation kjs$getIdLocation() {
      return UtilsJS.UNKNOWN_ID;
   }

   default String kjs$getId() {
      return this.kjs$getIdLocation().toString();
   }

   default String kjs$getMod() {
      return this.kjs$getIdLocation().m_135827_();
   }

   default CompoundTag kjs$getTypeData() {
      throw new NoMixinException();
   }

   default void kjs$setMaterialRaw(Material v) {
      throw new NoMixinException();
   }

   default void kjs$setHasCollision(boolean v) {
      throw new NoMixinException();
   }

   default void kjs$setExplosionResistance(float v) {
      throw new NoMixinException();
   }

   default void kjs$setIsRandomlyTicking(boolean v) {
      throw new NoMixinException();
   }

   default void kjs$setSoundType(SoundType v) {
      throw new NoMixinException();
   }

   default void kjs$setFriction(float v) {
      throw new NoMixinException();
   }

   default void kjs$setSpeedFactor(float v) {
      throw new NoMixinException();
   }

   default void kjs$setJumpFactor(float v) {
      throw new NoMixinException();
   }

   default void kjs$setMaterial(MaterialJS v) {
      Material m = v.getMinecraftMaterial();
      this.kjs$setMaterialRaw(m);

      for (BlockState state : this.kjs$getBlockStates()) {
         state.kjs$setMaterial(m);
      }
   }

   default void kjs$setDestroySpeed(float v) {
      for (BlockState state : this.kjs$getBlockStates()) {
         state.kjs$setDestroySpeed(v);
      }
   }

   default void kjs$setLightEmission(int v) {
      for (BlockState state : this.kjs$getBlockStates()) {
         state.kjs$setLightEmission(v);
      }
   }

   default void kjs$setRequiresTool(boolean v) {
      for (BlockState state : this.kjs$getBlockStates()) {
         state.kjs$setRequiresTool(v);
      }
   }

   default List<BlockState> kjs$getBlockStates() {
      return (List<BlockState>)(this instanceof Block block ? block.m_49965_().m_61056_() : List.of());
   }
}
