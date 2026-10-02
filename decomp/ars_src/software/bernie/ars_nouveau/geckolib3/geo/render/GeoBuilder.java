package software.bernie.ars_nouveau.geckolib3.geo.render;

import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.Bone;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.Cube;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.ModelProperties;
import software.bernie.ars_nouveau.geckolib3.geo.raw.tree.RawBoneGroup;
import software.bernie.ars_nouveau.geckolib3.geo.raw.tree.RawGeometryTree;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoCube;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.util.VectorUtils;

public class GeoBuilder implements IGeoBuilder {
   private static final Map<String, IGeoBuilder> moddedGeoBuilders = new Object2ObjectOpenHashMap();
   private static final IGeoBuilder defaultBuilder = new GeoBuilder();

   public static void registerGeoBuilder(String modID, IGeoBuilder builder) {
      moddedGeoBuilders.put(modID, builder);
   }

   public static IGeoBuilder getGeoBuilder(String modID) {
      IGeoBuilder builder = moddedGeoBuilders.get(modID);
      return builder == null ? defaultBuilder : builder;
   }

   @Override
   public GeoModel constructGeoModel(RawGeometryTree geometryTree) {
      GeoModel model = new GeoModel();
      model.properties = geometryTree.properties;

      for (RawBoneGroup rawBone : geometryTree.topLevelBones.values()) {
         model.topLevelBones.add(this.constructBone(rawBone, geometryTree.properties, null));
      }

      return model;
   }

   @Override
   public GeoBone constructBone(RawBoneGroup bone, ModelProperties properties, GeoBone parent) {
      GeoBone geoBone = new GeoBone();
      Bone rawBone = bone.selfBone;
      Vector3f rotation = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(rawBone.getRotation()));
      Vector3f pivot = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(rawBone.getPivot()));
      rotation.m_122263_(-1.0F, -1.0F, 1.0F);
      geoBone.mirror = rawBone.getMirror();
      geoBone.dontRender = rawBone.getNeverRender();
      geoBone.reset = rawBone.getReset();
      geoBone.inflate = rawBone.getInflate();
      geoBone.parent = parent;
      geoBone.setModelRendererName(rawBone.getName());
      geoBone.setRotationX((float)Math.toRadians((double)rotation.m_122239_()));
      geoBone.setRotationY((float)Math.toRadians((double)rotation.m_122260_()));
      geoBone.setRotationZ((float)Math.toRadians((double)rotation.m_122269_()));
      geoBone.rotationPointX = -pivot.m_122239_();
      geoBone.rotationPointY = pivot.m_122260_();
      geoBone.rotationPointZ = pivot.m_122269_();
      if (!ArrayUtils.isEmpty(rawBone.getCubes())) {
         for (Cube cube : rawBone.getCubes()) {
            geoBone.childCubes.add(GeoCube.createFromPojoCube(cube, properties, geoBone.inflate == null ? null : geoBone.inflate / 16.0, geoBone.mirror));
         }
      }

      for (RawBoneGroup child : bone.children.values()) {
         geoBone.childBones.add(this.constructBone(child, properties, geoBone));
      }

      return geoBone;
   }
}
