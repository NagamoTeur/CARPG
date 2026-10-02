package com.bobmowzie.mowziesmobs.client.model.tools;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.mojang.math.Vector3f;
import org.apache.commons.lang3.ArrayUtils;
import software.bernie.geckolib3.geo.raw.pojo.Bone;
import software.bernie.geckolib3.geo.raw.pojo.Cube;
import software.bernie.geckolib3.geo.raw.pojo.ModelProperties;
import software.bernie.geckolib3.geo.raw.tree.RawBoneGroup;
import software.bernie.geckolib3.geo.render.GeoBuilder;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.util.VectorUtils;

public class MowzieGeoBuilder extends GeoBuilder {
   public GeoBone constructBone(RawBoneGroup bone, ModelProperties properties, GeoBone parent) {
      MowzieGeoBone geoBone = new MowzieGeoBone();
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
