package com.hollingsworth.arsnouveau.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapeUtils {
   private static final Vec3 fromOrigin = new Vec3(-0.5, -0.5, -0.5);

   public static VoxelShape rotateY(VoxelShape shape, int rotation) {
      Set<VoxelShape> rotatedShapes = new HashSet<>();
      shape.m_83286_((x1, y1, z1, x2, y2, z2) -> {
         x1 = x1 * 16.0 - 8.0;
         x2 = x2 * 16.0 - 8.0;
         z1 = z1 * 16.0 - 8.0;
         z2 = z2 * 16.0 - 8.0;
         switch (rotation) {
            case 90:
               rotatedShapes.add(boxSafe(8.0 - z1, y1 * 16.0, 8.0 + x1, 8.0 - z2, y2 * 16.0, 8.0 + x2));
               break;
            case 180:
               rotatedShapes.add(boxSafe(8.0 - x1, y1 * 16.0, 8.0 - z1, 8.0 - x2, y2 * 16.0, 8.0 - z2));
               break;
            case 270:
               rotatedShapes.add(boxSafe(8.0 + z1, y1 * 16.0, 8.0 - x1, 8.0 + z2, y2 * 16.0, 8.0 - x2));
               break;
            default:
               throw new IllegalArgumentException("invalid rotation " + rotation + " (must be 90,180 or 270)");
         }
      });
      return rotatedShapes.stream().reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_)).orElse(shape);
   }

   public static VoxelShape rotateX(VoxelShape shape, int rotation) {
      Set<VoxelShape> rotatedShapes = new HashSet<>();
      shape.m_83286_((x1, y1, z1, x2, y2, z2) -> {
         y1 = y1 * 16.0 - 8.0;
         y2 = y2 * 16.0 - 8.0;
         z1 = z1 * 16.0 - 8.0;
         z2 = z2 * 16.0 - 8.0;
         switch (rotation) {
            case 90:
               rotatedShapes.add(boxSafe(x1 * 16.0, 8.0 - z1, 8.0 + y1, x2 * 16.0, 8.0 - z2, 8.0 + y2));
               break;
            case 180:
               rotatedShapes.add(boxSafe(x1 * 16.0, 8.0 - z1, 8.0 - y1, x2 * 16.0, 8.0 - z2, 8.0 - y2));
               break;
            case 270:
               rotatedShapes.add(boxSafe(x1 * 16.0, 8.0 + z1, 8.0 - y1, x2 * 16.0, 8.0 + z2, 8.0 - y2));
               break;
            default:
               throw new IllegalArgumentException("invalid rotation " + rotation + " (must be 90,180 or 270)");
         }
      });
      return rotatedShapes.stream().reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_)).orElse(shape);
   }

   public static VoxelShape combine(BooleanOp func, VoxelShape... shapes) {
      VoxelShape result = Shapes.m_83040_();

      for (VoxelShape shape : shapes) {
         result = Shapes.m_83148_(result, shape, func);
      }

      return result.m_83296_();
   }

   private static VoxelShape boxSafe(double pMinX, double pMinY, double pMinZ, double pMaxX, double pMaxY, double pMaxZ) {
      double x1 = Math.min(pMinX, pMaxX);
      double x2 = Math.max(pMinX, pMaxX);
      double y1 = Math.min(pMinY, pMaxY);
      double y2 = Math.max(pMinY, pMaxY);
      double z1 = Math.min(pMinZ, pMaxZ);
      double z2 = Math.max(pMinZ, pMaxZ);
      return Block.m_49796_(x1, y1, z1, x2, y2, z2);
   }

   public static AABB rotate(AABB box, Direction side) {
      return switch (side) {
         case DOWN -> box;
         case UP -> new AABB(box.f_82288_, -box.f_82289_, -box.f_82290_, box.f_82291_, -box.f_82292_, -box.f_82293_);
         case NORTH -> new AABB(box.f_82288_, -box.f_82290_, box.f_82289_, box.f_82291_, -box.f_82293_, box.f_82292_);
         case SOUTH -> new AABB(-box.f_82288_, -box.f_82290_, -box.f_82289_, -box.f_82291_, -box.f_82293_, -box.f_82292_);
         case WEST -> new AABB(box.f_82289_, -box.f_82290_, -box.f_82288_, box.f_82292_, -box.f_82293_, -box.f_82291_);
         case EAST -> new AABB(-box.f_82289_, -box.f_82290_, box.f_82288_, -box.f_82292_, -box.f_82293_, box.f_82291_);
         default -> throw new IncompatibleClassChangeError();
      };
   }

   public static AABB rotate(AABB box, Rotation rotation) {
      return switch (rotation) {
         case NONE -> box;
         case CLOCKWISE_90 -> new AABB(-box.f_82290_, box.f_82289_, box.f_82288_, -box.f_82293_, box.f_82292_, box.f_82291_);
         case CLOCKWISE_180 -> new AABB(-box.f_82288_, box.f_82289_, -box.f_82290_, -box.f_82291_, box.f_82292_, -box.f_82293_);
         case COUNTERCLOCKWISE_90 -> new AABB(box.f_82290_, box.f_82289_, -box.f_82288_, box.f_82293_, box.f_82292_, -box.f_82291_);
         default -> throw new IncompatibleClassChangeError();
      };
   }

   public static AABB rotateHorizontal(AABB box, Direction side) {
      return switch (side) {
         case NORTH -> rotate(box, Rotation.NONE);
         case SOUTH -> rotate(box, Rotation.CLOCKWISE_180);
         case WEST -> rotate(box, Rotation.COUNTERCLOCKWISE_90);
         case EAST -> rotate(box, Rotation.CLOCKWISE_90);
         default -> box;
      };
   }

   public static VoxelShape rotate(VoxelShape shape, Direction side) {
      return rotate(shape, (UnaryOperator<AABB>)(box -> rotate(box, side)));
   }

   public static VoxelShape rotate(VoxelShape shape, Rotation rotation) {
      return rotate(shape, (UnaryOperator<AABB>)(box -> rotate(box, rotation)));
   }

   public static VoxelShape rotateHorizontal(VoxelShape shape, Direction side) {
      return rotate(shape, (UnaryOperator<AABB>)(box -> rotateHorizontal(box, side)));
   }

   public static VoxelShape rotate(VoxelShape shape, UnaryOperator<AABB> rotateFunction) {
      List<VoxelShape> rotatedPieces = new ArrayList<>();

      for (AABB sourceBoundingBox : shape.m_83299_()) {
         rotatedPieces.add(
            Shapes.m_83064_(
               rotateFunction.apply(sourceBoundingBox.m_82386_(fromOrigin.f_82479_, fromOrigin.f_82480_, fromOrigin.f_82481_))
                  .m_82386_(-fromOrigin.f_82479_, -fromOrigin.f_82481_, -fromOrigin.f_82481_)
            )
         );
      }

      return combine(rotatedPieces);
   }

   public static VoxelShape combine(VoxelShape... shapes) {
      return batchCombine(Shapes.m_83040_(), BooleanOp.f_82695_, true, shapes);
   }

   public static VoxelShape combine(Collection<VoxelShape> shapes) {
      return batchCombine(Shapes.m_83040_(), BooleanOp.f_82695_, true, shapes);
   }

   public static VoxelShape exclude(VoxelShape... shapes) {
      return batchCombine(Shapes.m_83144_(), BooleanOp.f_82685_, true, shapes);
   }

   public static VoxelShape batchCombine(VoxelShape initial, BooleanOp function, boolean simplify, Collection<VoxelShape> shapes) {
      VoxelShape combinedShape = initial;

      for (VoxelShape shape : shapes) {
         combinedShape = Shapes.m_83148_(combinedShape, shape, function);
      }

      return simplify ? combinedShape.m_83296_() : combinedShape;
   }

   public static VoxelShape batchCombine(VoxelShape initial, BooleanOp function, boolean simplify, VoxelShape... shapes) {
      VoxelShape combinedShape = initial;

      for (VoxelShape shape : shapes) {
         combinedShape = Shapes.m_83148_(combinedShape, shape, function);
      }

      return simplify ? combinedShape.m_83296_() : combinedShape;
   }

   public static void setShape(VoxelShape shape, VoxelShape[] dest, boolean verticalAxis) {
      setShape(shape, dest, verticalAxis, false);
   }

   public static void setShape(VoxelShape shape, VoxelShape[] dest, boolean verticalAxis, boolean invert) {
      Direction[] dirs = verticalAxis ? Direction.values() : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

      for (Direction side : dirs) {
         dest[verticalAxis ? side.ordinal() : side.ordinal() - 2] = verticalAxis
            ? rotate(shape, invert ? side.m_122424_() : side)
            : rotateHorizontal(shape, side);
      }
   }

   public static void setShape(VoxelShape shape, VoxelShape[] dest) {
      setShape(shape, dest, false, false);
   }
}
