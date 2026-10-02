package dev.latvian.mods.kubejs.registry;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.Registries;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.material.Fluid;

public class KubeJSRegistries {
   @Info("Platform-agnostic wrapper of minecraft registries, can be used to register content or get objects from the registry")
   public static final Registries REGISTRIES = Registries.get("kubejs");

   @Info("Gets the registry associated with the id")
   public static <T> Registrar<T> byId(ResourceLocation id) {
      return genericRegistry(ResourceKey.m_135788_(id));
   }

   @HideFromJS
   public static <T> Registrar<T> genericRegistry(ResourceKey<Registry<T>> key) {
      return REGISTRIES.get(key);
   }

   @Info("Gets the block registrar")
   public static Registrar<Block> blocks() {
      return genericRegistry(Registry.f_122901_);
   }

   @Info("Gets the attribute registrar")
   public static Registrar<Attribute> attributes() {
      return genericRegistry(Registry.f_122916_);
   }

   @Info("Gets the block entity registrar")
   public static Registrar<BlockEntityType<?>> blockEntities() {
      return genericRegistry(Registry.f_122907_);
   }

   @Info("Gets the item registrar")
   public static Registrar<Item> items() {
      return genericRegistry(Registry.f_122904_);
   }

   @Info("Gets the fluid registrar")
   public static Registrar<Fluid> fluids() {
      return genericRegistry(Registry.f_122899_);
   }

   @Info("Gets the entity type registrar")
   public static Registrar<EntityType<?>> entityTypes() {
      return genericRegistry(Registry.f_122903_);
   }

   @Info("Gets the menu type registrar")
   public static Registrar<MenuType<?>> menuTypes() {
      return genericRegistry(Registry.f_122913_);
   }

   @Info("Gets the sound event registrar")
   public static Registrar<SoundEvent> soundEvents() {
      return genericRegistry(Registry.f_122898_);
   }

   @Info("Gets the recipe serializer registrar")
   public static Registrar<RecipeSerializer<?>> recipeSerializers() {
      return genericRegistry(Registry.f_122915_);
   }

   @Info("Gets the biome registrar")
   public static Registrar<Biome> biomes() {
      return genericRegistry(Registry.f_122885_);
   }

   @Info("Gets the chunk generator registrar")
   public static Registrar<Codec<? extends ChunkGenerator>> chunkGenerators() {
      return genericRegistry(Registry.f_122853_);
   }

   @Info("Gets the block entity registrar")
   public static Registrar<BlockEntityType<?>> blockEntityTypes() {
      return genericRegistry(Registry.f_122907_);
   }

   @Info("Gets the potion registrar")
   public static Registrar<Potion> potions() {
      return genericRegistry(Registry.f_122905_);
   }

   @Info("Gets the enchantment registrar")
   public static Registrar<Enchantment> enchantments() {
      return genericRegistry(Registry.f_122902_);
   }

   @Info("Gets the mob effect registrar")
   public static Registrar<MobEffect> mobEffects() {
      return genericRegistry(Registry.f_122900_);
   }

   @Info("Gets the particle type registrar")
   public static Registrar<ParticleType<?>> particleTypes() {
      return genericRegistry(Registry.f_122906_);
   }
}
