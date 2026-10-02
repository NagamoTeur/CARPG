package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.core.BlockKJS;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@RemapPrefixForJS("kjs$")
@Mixin({BlockBehaviour.class})
public abstract class BlockBehaviourMixin implements BlockKJS {
   private BlockBuilder kjs$blockBuilder;
   private CompoundTag kjs$typeData;
   private ResourceLocation kjs$id;
   private String kjs$idString;

   @Override
   public ResourceLocation kjs$getIdLocation() {
      if (this.kjs$id == null) {
         if (this instanceof Block block) {
            ResourceLocation id = KubeJSRegistries.blocks().getId(block);
            this.kjs$id = id == null ? UtilsJS.UNKNOWN_ID : id;
         } else {
            this.kjs$id = UtilsJS.UNKNOWN_ID;
         }
      }

      return this.kjs$id;
   }

   @Override
   public String kjs$getId() {
      if (this.kjs$idString == null) {
         this.kjs$idString = this.kjs$getIdLocation().toString();
      }

      return this.kjs$idString;
   }

   @Nullable
   @Override
   public BlockBuilder kjs$getBlockBuilder() {
      return this.kjs$blockBuilder;
   }

   @Override
   public void kjs$setBlockBuilder(BlockBuilder b) {
      this.kjs$blockBuilder = b;
   }

   @Override
   public CompoundTag kjs$getTypeData() {
      if (this.kjs$typeData == null) {
         this.kjs$typeData = new CompoundTag();
      }

      return this.kjs$typeData;
   }

   @Accessor("material")
   @Mutable
   @Override
   public abstract void kjs$setMaterialRaw(Material var1);

   @Accessor("hasCollision")
   @Mutable
   @Override
   public abstract void kjs$setHasCollision(boolean var1);

   @Accessor("explosionResistance")
   @Mutable
   @Override
   public abstract void kjs$setExplosionResistance(float var1);

   @Accessor("isRandomlyTicking")
   @Mutable
   @Override
   public abstract void kjs$setIsRandomlyTicking(boolean var1);

   @Accessor("soundType")
   @Mutable
   @Override
   public abstract void kjs$setSoundType(SoundType var1);

   @Accessor("friction")
   @Mutable
   @Override
   public abstract void kjs$setFriction(float var1);

   @Accessor("speedFactor")
   @Mutable
   @Override
   public abstract void kjs$setSpeedFactor(float var1);

   @Accessor("jumpFactor")
   @Mutable
   @Override
   public abstract void kjs$setJumpFactor(float var1);
}
