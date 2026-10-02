package dev.latvian.mods.kubejs.server.tag;

import dev.latvian.mods.kubejs.DevProperties;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader.EntryWithSource;
import net.minecraft.util.ExtraCodecs.TagOrElementLocation;

public interface TagEventFilter {
   static TagEventFilter of(TagEventJS event, Object o) {
      if (o instanceof TagEventFilter) {
         return (TagEventFilter)o;
      } else if (o instanceof Collection<?> list) {
         List<TagEventFilter> filters = list.stream()
            .map(o1 -> of(event, o1))
            .flatMap(TagEventFilter::unwrap)
            .filter(f -> f != TagEventFilter.Empty.INSTANCE)
            .toList();
         return (TagEventFilter)(filters.isEmpty() ? TagEventFilter.Empty.INSTANCE : (filters.size() == 1 ? filters.get(0) : new TagEventFilter.Or(filters)));
      } else {
         Pattern regex = UtilsJS.parseRegex(o);
         if (regex != null) {
            return new TagEventFilter.RegEx(regex);
         } else {
            String s = o.toString().trim();
            if (!s.isEmpty()) {
               return (TagEventFilter)(switch (s.charAt(0)) {
                  case '#' -> new TagEventFilter.Tag(event.get(new ResourceLocation(s.substring(1))));
                  case '@' -> new TagEventFilter.Namespace(s.substring(1));
                  default -> new TagEventFilter.ID(new ResourceLocation(s));
               });
            } else {
               return TagEventFilter.Empty.INSTANCE;
            }
         }
      }
   }

   static TagEventFilter unwrap(TagEventJS event, Object[] array) {
      return array.length == 1 ? of(event, array[0]) : of(event, Arrays.asList(array));
   }

   boolean testElementId(ResourceLocation var1);

   default boolean testTagOrElementLocation(TagOrElementLocation element) {
      return !element.f_216196_() && this.testElementId(element.f_216195_());
   }

   default Stream<TagEventFilter> unwrap() {
      return Stream.of(this);
   }

   default int add(TagWrapper wrapper) {
      int count = 0;

      for (ResourceLocation id : wrapper.event.getElementIds()) {
         if (this.testElementId(id)) {
            wrapper.entries.add(new EntryWithSource(TagEntry.m_215925_(id), "KubeJS Custom Tags"));
            count++;
         }
      }

      return count;
   }

   default int remove(TagWrapper wrapper) {
      int count = 0;
      Iterator<EntryWithSource> itr = wrapper.entries.iterator();

      while (itr.hasNext()) {
         EntryWithSource it = itr.next();
         if (!it.f_216042_().f_215914_ && this.testElementId(it.f_216042_().f_215913_)) {
            itr.remove();
            count++;
         }
      }

      return count;
   }

   public static class Empty implements TagEventFilter {
      public static final TagEventFilter.Empty INSTANCE = new TagEventFilter.Empty();

      @Override
      public boolean testElementId(ResourceLocation resourceLocation) {
         return false;
      }

      @Override
      public boolean testTagOrElementLocation(TagOrElementLocation element) {
         return false;
      }

      @Override
      public int add(TagWrapper wrapper) {
         return 0;
      }

      @Override
      public int remove(TagWrapper wrapper) {
         return 0;
      }
   }

   public static record ID(ResourceLocation id) implements TagEventFilter {
      @Override
      public boolean testElementId(ResourceLocation id) {
         return this.id.equals(id);
      }

      @Override
      public int add(TagWrapper wrapper) {
         if (wrapper.event.getElementIds().contains(this.id)) {
            wrapper.entries.add(new EntryWithSource(TagEntry.m_215925_(this.id), "KubeJS Custom Tags"));
            return 1;
         } else {
            String msg = "No such element %s in registry %s".formatted(this.id, wrapper.event.registry);
            if (DevProperties.get().strictTags) {
               throw new EmptyTagTargetException(msg);
            } else {
               if (DevProperties.get().logSkippedTags) {
                  ConsoleJS.SERVER.warn(msg);
               }

               return 0;
            }
         }
      }
   }

   public static record Namespace(String namespace) implements TagEventFilter {
      @Override
      public boolean testElementId(ResourceLocation id) {
         return id.m_135827_().equals(this.namespace);
      }
   }

   public static record Or(List<TagEventFilter> filters) implements TagEventFilter {
      @Override
      public boolean testElementId(ResourceLocation resourceLocation) {
         for (TagEventFilter filter : this.filters) {
            if (filter.testElementId(resourceLocation)) {
               return true;
            }
         }

         return false;
      }

      @Override
      public boolean testTagOrElementLocation(TagOrElementLocation element) {
         for (TagEventFilter filter : this.filters) {
            if (filter.testTagOrElementLocation(element)) {
               return true;
            }
         }

         return false;
      }

      @Override
      public Stream<TagEventFilter> unwrap() {
         return this.filters.stream();
      }

      @Override
      public int add(TagWrapper wrapper) {
         int count = 0;

         for (TagEventFilter filter : this.filters) {
            count += filter.add(wrapper);
         }

         return count;
      }

      @Override
      public int remove(TagWrapper wrapper) {
         int count = 0;

         for (TagEventFilter filter : this.filters) {
            count += filter.remove(wrapper);
         }

         return count;
      }
   }

   public static record RegEx(Pattern pattern) implements TagEventFilter {
      @Override
      public boolean testElementId(ResourceLocation id) {
         return this.pattern.matcher(id.toString()).find();
      }
   }

   public static record Tag(TagWrapper tag) implements TagEventFilter {
      @Override
      public boolean testElementId(ResourceLocation id) {
         return false;
      }

      @Override
      public boolean testTagOrElementLocation(TagOrElementLocation element) {
         return element.f_216196_() && this.tag.id.equals(element.f_216195_());
      }

      @Override
      public int add(TagWrapper wrapper) {
         wrapper.entries.add(new EntryWithSource(TagEntry.m_215949_(this.tag.id), "KubeJS Custom Tags"));
         return 1;
      }

      @Override
      public int remove(TagWrapper wrapper) {
         int count = 0;
         Iterator<EntryWithSource> itr = wrapper.entries.iterator();

         while (itr.hasNext()) {
            EntryWithSource it = itr.next();
            if (it.f_216042_().f_215914_ && it.f_216042_().f_215913_.equals(this.tag.id)) {
               itr.remove();
               count++;
            }
         }

         return count;
      }
   }
}
