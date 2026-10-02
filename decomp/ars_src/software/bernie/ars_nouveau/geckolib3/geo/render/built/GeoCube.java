package software.bernie.ars_nouveau.geckolib3.geo.render.built;

import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.Cube;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.FaceUv;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.ModelProperties;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.UvFaces;
import software.bernie.ars_nouveau.geckolib3.geo.raw.pojo.UvUnion;
import software.bernie.ars_nouveau.geckolib3.util.VectorUtils;

public class GeoCube {
   public GeoQuad[] quads = new GeoQuad[6];
   public Vector3f pivot;
   public Vector3f rotation;
   public Vector3f size = new Vector3f();
   public double inflate;
   public Boolean mirror;

   private GeoCube(double[] size) {
      if (size.length >= 3) {
         this.size.m_122245_((float)size[0], (float)size[1], (float)size[2]);
      }
   }

   public static GeoCube createFromPojoCube(Cube cubeIn, ModelProperties properties, Double boneInflate, Boolean mirror) {
      GeoCube cube = new GeoCube(cubeIn.getSize());
      UvUnion uvUnion = cubeIn.getUv();
      UvFaces faces = uvUnion.faceUV;
      boolean isBoxUV = uvUnion.isBoxUV;
      cube.mirror = cubeIn.getMirror();
      cube.inflate = cubeIn.getInflate() == null ? (boneInflate == null ? 0.0 : boneInflate) : cubeIn.getInflate() / 16.0;
      float textureHeight = properties.getTextureHeight().floatValue();
      float textureWidth = properties.getTextureWidth().floatValue();
      Vec3 size = VectorUtils.fromArray(cubeIn.getSize());
      Vec3 origin = VectorUtils.fromArray(cubeIn.getOrigin());
      origin = new Vec3(-(origin.f_82479_ + size.f_82479_) / 16.0, origin.f_82480_ / 16.0, origin.f_82481_ / 16.0);
      size = size.m_82542_(0.0625, 0.0625, 0.0625);
      Vector3f rotation = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(cubeIn.getRotation()));
      rotation.m_122263_(-1.0F, -1.0F, 1.0F);
      rotation.setX((float)Math.toRadians((double)rotation.m_122239_()));
      rotation.setY((float)Math.toRadians((double)rotation.m_122260_()));
      rotation.setZ((float)Math.toRadians((double)rotation.m_122269_()));
      Vector3f pivot = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(cubeIn.getPivot()));
      pivot.m_122263_(-1.0F, 1.0F, 1.0F);
      cube.pivot = pivot;
      cube.rotation = rotation;
      GeoVertex P1 = new GeoVertex(origin.f_82479_ - cube.inflate, origin.f_82480_ - cube.inflate, origin.f_82481_ - cube.inflate);
      GeoVertex P2 = new GeoVertex(origin.f_82479_ - cube.inflate, origin.f_82480_ - cube.inflate, origin.f_82481_ + size.f_82481_ + cube.inflate);
      GeoVertex P3 = new GeoVertex(origin.f_82479_ - cube.inflate, origin.f_82480_ + size.f_82480_ + cube.inflate, origin.f_82481_ - cube.inflate);
      GeoVertex P4 = new GeoVertex(
         origin.f_82479_ - cube.inflate, origin.f_82480_ + size.f_82480_ + cube.inflate, origin.f_82481_ + size.f_82481_ + cube.inflate
      );
      GeoVertex P5 = new GeoVertex(origin.f_82479_ + size.f_82479_ + cube.inflate, origin.f_82480_ - cube.inflate, origin.f_82481_ - cube.inflate);
      GeoVertex P6 = new GeoVertex(
         origin.f_82479_ + size.f_82479_ + cube.inflate, origin.f_82480_ - cube.inflate, origin.f_82481_ + size.f_82481_ + cube.inflate
      );
      GeoVertex P7 = new GeoVertex(
         origin.f_82479_ + size.f_82479_ + cube.inflate, origin.f_82480_ + size.f_82480_ + cube.inflate, origin.f_82481_ - cube.inflate
      );
      GeoVertex P8 = new GeoVertex(
         origin.f_82479_ + size.f_82479_ + cube.inflate, origin.f_82480_ + size.f_82480_ + cube.inflate, origin.f_82481_ + size.f_82481_ + cube.inflate
      );
      GeoQuad quadWest;
      GeoQuad quadEast;
      GeoQuad quadNorth;
      GeoQuad quadSouth;
      GeoQuad quadUp;
      GeoQuad quadDown;
      if (!isBoxUV) {
         FaceUv west = faces.getWest();
         FaceUv east = faces.getEast();
         FaceUv north = faces.getNorth();
         FaceUv south = faces.getSouth();
         FaceUv up = faces.getUp();
         FaceUv down = faces.getDown();
         quadWest = west == null
            ? null
            : new GeoQuad(new GeoVertex[]{P4, P3, P1, P2}, west.getUv(), west.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.WEST);
         quadEast = east == null
            ? null
            : new GeoQuad(new GeoVertex[]{P7, P8, P6, P5}, east.getUv(), east.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.EAST);
         quadNorth = north == null
            ? null
            : new GeoQuad(new GeoVertex[]{P3, P7, P5, P1}, north.getUv(), north.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.NORTH);
         quadSouth = south == null
            ? null
            : new GeoQuad(new GeoVertex[]{P8, P4, P2, P6}, south.getUv(), south.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.SOUTH);
         quadUp = up == null
            ? null
            : new GeoQuad(new GeoVertex[]{P4, P8, P7, P3}, up.getUv(), up.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.UP);
         quadDown = down == null
            ? null
            : new GeoQuad(new GeoVertex[]{P1, P5, P6, P2}, down.getUv(), down.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.DOWN);
         if (cubeIn.getMirror() == Boolean.TRUE || mirror == Boolean.TRUE) {
            quadWest = west == null
               ? null
               : new GeoQuad(new GeoVertex[]{P7, P8, P6, P5}, west.getUv(), west.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.WEST);
            quadEast = east == null
               ? null
               : new GeoQuad(new GeoVertex[]{P4, P3, P1, P2}, east.getUv(), east.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.EAST);
            quadNorth = north == null
               ? null
               : new GeoQuad(
                  new GeoVertex[]{P3, P7, P5, P1}, north.getUv(), north.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.NORTH
               );
            quadSouth = south == null
               ? null
               : new GeoQuad(
                  new GeoVertex[]{P8, P4, P2, P6}, south.getUv(), south.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.SOUTH
               );
            quadUp = up == null
               ? null
               : new GeoQuad(new GeoVertex[]{P1, P5, P6, P2}, up.getUv(), up.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.UP);
            quadDown = down == null
               ? null
               : new GeoQuad(new GeoVertex[]{P4, P8, P7, P3}, down.getUv(), down.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.DOWN);
         }
      } else {
         double[] UV = cubeIn.getUv().boxUVCoords;
         Vec3 UVSize = VectorUtils.fromArray(cubeIn.getSize());
         UVSize = new Vec3(Math.floor(UVSize.f_82479_), Math.floor(UVSize.f_82480_), Math.floor(UVSize.f_82481_));
         quadWest = new GeoQuad(
            new GeoVertex[]{P4, P3, P1, P2},
            new double[]{UV[0] + UVSize.f_82481_ + UVSize.f_82479_, UV[1] + UVSize.f_82481_},
            new double[]{UVSize.f_82481_, UVSize.f_82480_},
            textureWidth,
            textureHeight,
            cubeIn.getMirror(),
            Direction.WEST
         );
         quadEast = new GeoQuad(
            new GeoVertex[]{P7, P8, P6, P5},
            new double[]{UV[0], UV[1] + UVSize.f_82481_},
            new double[]{UVSize.f_82481_, UVSize.f_82480_},
            textureWidth,
            textureHeight,
            cubeIn.getMirror(),
            Direction.EAST
         );
         quadNorth = new GeoQuad(
            new GeoVertex[]{P3, P7, P5, P1},
            new double[]{UV[0] + UVSize.f_82481_, UV[1] + UVSize.f_82481_},
            new double[]{UVSize.f_82479_, UVSize.f_82480_},
            textureWidth,
            textureHeight,
            cubeIn.getMirror(),
            Direction.NORTH
         );
         quadSouth = new GeoQuad(
            new GeoVertex[]{P8, P4, P2, P6},
            new double[]{UV[0] + UVSize.f_82481_ + UVSize.f_82479_ + UVSize.f_82481_, UV[1] + UVSize.f_82481_},
            new double[]{UVSize.f_82479_, UVSize.f_82480_},
            textureWidth,
            textureHeight,
            cubeIn.getMirror(),
            Direction.SOUTH
         );
         quadUp = new GeoQuad(
            new GeoVertex[]{P4, P8, P7, P3},
            new double[]{UV[0] + UVSize.f_82481_, UV[1]},
            new double[]{UVSize.f_82479_, UVSize.f_82481_},
            textureWidth,
            textureHeight,
            cubeIn.getMirror(),
            Direction.UP
         );
         quadDown = new GeoQuad(
            new GeoVertex[]{P1, P5, P6, P2},
            new double[]{UV[0] + UVSize.f_82481_ + UVSize.f_82479_, UV[1] + UVSize.f_82481_},
            new double[]{UVSize.f_82479_, -UVSize.f_82481_},
            textureWidth,
            textureHeight,
            cubeIn.getMirror(),
            Direction.DOWN
         );
         if (cubeIn.getMirror() == Boolean.TRUE || mirror == Boolean.TRUE) {
            quadWest = new GeoQuad(
               new GeoVertex[]{P7, P8, P6, P5},
               new double[]{UV[0] + UVSize.f_82481_ + UVSize.f_82479_, UV[1] + UVSize.f_82481_},
               new double[]{UVSize.f_82481_, UVSize.f_82480_},
               textureWidth,
               textureHeight,
               cubeIn.getMirror(),
               Direction.WEST
            );
            quadEast = new GeoQuad(
               new GeoVertex[]{P4, P3, P1, P2},
               new double[]{UV[0], UV[1] + UVSize.f_82481_},
               new double[]{UVSize.f_82481_, UVSize.f_82480_},
               textureWidth,
               textureHeight,
               cubeIn.getMirror(),
               Direction.EAST
            );
            quadNorth = new GeoQuad(
               new GeoVertex[]{P3, P7, P5, P1},
               new double[]{UV[0] + UVSize.f_82481_, UV[1] + UVSize.f_82481_},
               new double[]{UVSize.f_82479_, UVSize.f_82480_},
               textureWidth,
               textureHeight,
               cubeIn.getMirror(),
               Direction.NORTH
            );
            quadSouth = new GeoQuad(
               new GeoVertex[]{P8, P4, P2, P6},
               new double[]{UV[0] + UVSize.f_82481_ + UVSize.f_82479_ + UVSize.f_82481_, UV[1] + UVSize.f_82481_},
               new double[]{UVSize.f_82479_, UVSize.f_82480_},
               textureWidth,
               textureHeight,
               cubeIn.getMirror(),
               Direction.SOUTH
            );
            quadUp = new GeoQuad(
               new GeoVertex[]{P4, P8, P7, P3},
               new double[]{UV[0] + UVSize.f_82481_, UV[1]},
               new double[]{UVSize.f_82479_, UVSize.f_82481_},
               textureWidth,
               textureHeight,
               cubeIn.getMirror(),
               Direction.UP
            );
            quadDown = new GeoQuad(
               new GeoVertex[]{P1, P5, P6, P2},
               new double[]{UV[0] + UVSize.f_82481_ + UVSize.f_82479_, UV[1] + UVSize.f_82481_},
               new double[]{UVSize.f_82479_, -UVSize.f_82481_},
               textureWidth,
               textureHeight,
               cubeIn.getMirror(),
               Direction.DOWN
            );
         }
      }

      cube.quads[0] = quadWest;
      cube.quads[1] = quadEast;
      cube.quads[2] = quadNorth;
      cube.quads[3] = quadSouth;
      cube.quads[4] = quadUp;
      cube.quads[5] = quadDown;
      return cube;
   }
}
