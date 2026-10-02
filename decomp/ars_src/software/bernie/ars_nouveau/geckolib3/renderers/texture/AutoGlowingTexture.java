package software.bernie.ars_nouveau.geckolib3.renderers.texture;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import software.bernie.ars_nouveau.geckolib3.resource.data.GlowingMetadataSection;

public class AutoGlowingTexture extends GeoAbstractTexture {
   public AutoGlowingTexture(ResourceLocation originalLocation, ResourceLocation location) {
      super(originalLocation, location);
   }

   public static ResourceLocation get(ResourceLocation originalLocation) {
      return get(originalLocation, "_glowing", AutoGlowingTexture::new);
   }

   @Override
   protected boolean onLoadTexture(Resource resource, NativeImage originalImage, NativeImage newImage) {
      Optional<GlowingMetadataSection> glowingMetadata = null;

      try {
         glowingMetadata = resource.m_215509_().m_214059_(GlowingMetadataSection.SERIALIZER);
      } catch (IOException var6) {
         LOGGER.warn("Failed reading glowing metadata of: {}", this.location, var6);
      }

      if (glowingMetadata != null && !glowingMetadata.isEmpty()) {
         glowingMetadata.get().getGlowingSections().forEach(section -> section.forEach((x, y) -> {
               newImage.m_84988_(x, y, originalImage.m_84985_(x, y));
               originalImage.m_84988_(x, y, 0);
            }));
         return true;
      } else {
         return false;
      }
   }
}
