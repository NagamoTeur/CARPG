package software.bernie.ars_nouveau.geckolib3.file;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.geo.exception.GeckoLibException;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.Converter;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.FormatVersion;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.RawGeoModel;
import software.bernie.ars_nouveau.geckolib3.geo.raw.tree.RawGeometryTree;
import software.bernie.ars_nouveau.geckolib3.geo.render.GeoBuilder;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;

public class GeoModelLoader {
   public GeoModel loadModel(ResourceManager resourceManager, ResourceLocation location) {
      try {
         RawGeoModel rawModel = Converter.fromJsonString(AnimationFileLoader.getResourceAsString(location, resourceManager));
         if (rawModel.getFormatVersion() != FormatVersion.VERSION_1_12_0) {
            throw new GeckoLibException(location, "Wrong geometry json version, expected 1.12.0");
         } else {
            RawGeometryTree rawGeometryTree = RawGeometryTree.parseHierarchy(rawModel);
            return GeoBuilder.getGeoBuilder(location.m_135827_()).constructGeoModel(rawGeometryTree);
         }
      } catch (Exception var5) {
         GeckoLib.LOGGER.error(String.format("Error parsing %S", location), var5);
         throw new RuntimeException(var5);
      }
   }
}
