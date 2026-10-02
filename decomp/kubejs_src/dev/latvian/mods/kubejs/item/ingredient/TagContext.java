package dev.latvian.mods.kubejs.item.ingredient;

import com.google.common.collect.Iterables;
import dev.architectury.extensions.injected.InjectedRegistryEntryExtension;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager.LoadResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.NotNull;

public interface TagContext {
   TagContext EMPTY = new TagContext() {
      @Override
      public <T> boolean isEmpty(TagKey<T> tag) {
         return true;
      }

      @Override
      public <T> Iterable<Holder<T>> getTag(TagKey<T> tag) {
         KubeJS.LOGGER.warn("Tried to get tag {} from an empty tag context!", tag.f_203868_());
         return List.of();
      }
   };
   MutableObject<TagContext> INSTANCE = new MutableObject(EMPTY);

   static TagContext usingRegistry(RegistryAccess registryAccess) {
      return new TagContext() {
         @NotNull
         private <T> Registry<T> registry(TagKey<T> tag) {
            return registryAccess.m_175515_(tag.f_203867_());
         }

         @Override
         public boolean areTagsBound() {
            return true;
         }

         @Override
         public <T> Iterable<Holder<T>> getTag(TagKey<T> tag) {
            return this.registry(tag).m_206058_(tag);
         }

         @Override
         public <T> boolean contains(TagKey<T> tag, T value) {
            if (value instanceof InjectedRegistryEntryExtension<?> ext) {
               Holder<T> holder = UtilsJS.cast(ext.arch$holder());
               return holder.m_203656_(tag);
            } else {
               Registry<T> reg = this.registry(tag);
               return reg.m_7854_(value)
                  .<Holder>flatMap(reg::m_203636_)
                  .map(holderx -> holderx.m_203656_(tag))
                  .orElseGet(() -> TagContext.super.contains(tag, value));
            }
         }
      };
   }

   static TagContext fromLoadResult(List<LoadResult<?>> results) {
      final Map<ResourceKey<? extends Registry<?>>, Map<ResourceLocation, Collection<Holder<?>>>> tags = results.stream()
         .collect(Collectors.toMap(result -> UtilsJS.cast(result.f_203928_()), result -> UtilsJS.cast(result.f_203929_())));
      if (!tags.containsKey(Registry.f_122904_)) {
         ConsoleJS.getCurrent(ConsoleJS.SERVER).warn("Failed to load item tags during recipe event! Using replaceInput etc. will not work!");
         return EMPTY;
      } else {
         return new TagContext() {
            @Override
            public <T> Iterable<Holder<T>> getTag(TagKey<T> tag) {
               return UtilsJS.cast(tags.get(tag.f_203867_()).getOrDefault(tag.f_203868_(), Set.of()));
            }
         };
      }
   }

   default <T> boolean isEmpty(TagKey<T> tag) {
      return Iterables.isEmpty(this.getTag(tag));
   }

   default <T> boolean contains(TagKey<T> tag, T value) {
      if (this.isEmpty(tag)) {
         return false;
      } else {
         for (Holder<T> holder : this.getTag(tag)) {
            if (holder.m_203334_().equals(value)) {
               return true;
            }
         }

         return false;
      }
   }

   default boolean areTagsBound() {
      return false;
   }

   <T> Iterable<Holder<T>> getTag(TagKey<T> var1);

   default Collection<ItemStack> patchIngredientTags(TagKey<Item> tag) {
      Iterable<Holder<Item>> c = this.getTag(tag);
      ArrayList<ItemStack> stacks = new ArrayList<>(c instanceof Collection<?> cl ? cl.size() : 3);

      for (Holder<Item> holder : c) {
         stacks.add(new ItemStack((ItemLike)holder.m_203334_()));
      }

      return (Collection<ItemStack>)(stacks.isEmpty() ? List.of() : stacks);
   }
}
