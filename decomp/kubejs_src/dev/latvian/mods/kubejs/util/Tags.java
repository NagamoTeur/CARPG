package dev.latvian.mods.kubejs.util;

import dev.latvian.mods.kubejs.item.ingredient.TagContext;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class Tags {
   public static TagKey<Item> item(ResourceLocation id) {
      return generic(id, Registry.f_122904_);
   }

   public static TagKey<Block> block(ResourceLocation id) {
      return generic(id, Registry.f_122901_);
   }

   public static TagKey<Fluid> fluid(ResourceLocation id) {
      return generic(id, Registry.f_122899_);
   }

   public static TagKey<EntityType<?>> entityType(ResourceLocation id) {
      return generic(id, Registry.f_122903_);
   }

   public static TagKey<Biome> biome(ResourceLocation id) {
      return generic(id, Registry.f_122885_);
   }

   public static Stream<TagKey<Item>> byItemStack(ItemStack stack) {
      return byItem(stack.m_41720_());
   }

   public static Stream<TagKey<Item>> byItem(Item item) {
      return forHolder(item.m_204114_());
   }

   public static Stream<TagKey<Block>> byBlockState(BlockState state) {
      return byBlock(state.m_60734_());
   }

   public static Stream<TagKey<Block>> byBlock(Block block) {
      return forHolder(block.m_204297_());
   }

   public static Stream<TagKey<Fluid>> byFluid(Fluid fluid) {
      return forHolder(fluid.m_205069_());
   }

   public static Stream<TagKey<EntityType<?>>> byEntity(Entity entity) {
      return byEntityType(entity.m_6095_());
   }

   public static Stream<TagKey<EntityType<?>>> byEntityType(EntityType<?> entityType) {
      return forHolder(entityType.m_204041_());
   }

   public static <T> Stream<TagKey<T>> forType(T object, Registry<T> registry) {
      warnIfUnbound();
      return registry.m_7854_(object).flatMap(registry::m_203636_).stream().flatMap(Holder::m_203616_);
   }

   private static <T> TagKey<T> generic(ResourceLocation id, ResourceKey<Registry<T>> registry) {
      return TagKey.m_203882_(registry, id);
   }

   private static <T> Stream<TagKey<T>> forHolder(Reference<T> registryHolder) {
      warnIfUnbound();
      return registryHolder.m_203616_();
   }

   private static void warnIfUnbound() {
      if (!((TagContext)TagContext.INSTANCE.getValue()).areTagsBound()) {
         ConsoleJS.getCurrent(ConsoleJS.STARTUP)
            .warn("Tags have not been bound to registry yet! The values returned by this method may be outdated!", new Throwable());
      }
   }
}
