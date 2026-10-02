package software.bernie.ars_nouveau.geckolib3.resource.data;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlowingMetadataSection {
   public static final GlowingMetadataSectionSerializer SERIALIZER = new GlowingMetadataSectionSerializer();
   private final Collection<GlowingMetadataSection.Section> glowingSections;

   public GlowingMetadataSection(Stream<GlowingMetadataSection.Section> sections) {
      this.glowingSections = sections.map(GlowingMetadataSection.Section::copy).collect(Collectors.toList());
   }

   public Collection<GlowingMetadataSection.Section> getGlowingSections() {
      return Collections.unmodifiableCollection(this.glowingSections);
   }

   public boolean isEmpty() {
      return this.glowingSections.isEmpty();
   }

   @FunctionalInterface
   public interface BiIntConsumer {
      void accept(int var1, int var2);
   }

   public static record Section(int x1, int y1, int x2, int y2) {
      public GlowingMetadataSection.Section copy() {
         return new GlowingMetadataSection.Section(this.x1, this.y1, this.x2, this.y2);
      }

      public void forEach(GlowingMetadataSection.BiIntConsumer action) {
         for (int x = this.x1; x < this.x2; x++) {
            for (int y = this.y1; y < this.y2; y++) {
               action.accept(x, y);
            }
         }
      }
   }
}
