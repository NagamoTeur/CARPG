package com.cerbon.bosses_of_mass_destruction.api.multipart_entities.util;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MutableBox;
import com.cerbon.bosses_of_mass_destruction.mixin.multipart_entities.InvokerArrayVoxelShape;
import com.google.common.collect.Iterators;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CompoundOrientedBox extends AABB implements Iterable<OrientedBox> {
   private final Collection<OrientedBox> boxes;
   private VoxelShape cached;
   @Nullable
   private final MutableBox overrideBox;

   public CompoundOrientedBox(AABB bounds, Collection<OrientedBox> boxes, MutableBox overrideBox) {
      this(bounds.f_82288_, bounds.f_82289_, bounds.f_82290_, bounds.f_82291_, bounds.f_82292_, bounds.f_82293_, boxes, overrideBox);
   }

   public CompoundOrientedBox(
      double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Collection<OrientedBox> boxes, @Nullable MutableBox overrideBox
   ) {
      super(minX, minY, minZ, maxX, maxY, maxZ);
      this.boxes = boxes;
      this.overrideBox = overrideBox;
   }

   private CompoundOrientedBox(
      double minX,
      double minY,
      double minZ,
      double maxX,
      double maxY,
      double maxZ,
      Collection<OrientedBox> boxes,
      VoxelShape cached,
      @Nullable MutableBox overrideBox
   ) {
      super(minX, minY, minZ, maxX, maxY, maxZ);
      this.boxes = boxes;
      this.cached = cached;
      this.overrideBox = overrideBox;
   }

   @NotNull
   public AABB m_82377_(double x, double y, double z) {
      List<OrientedBox> orientedBoxes = new ObjectArrayList(this.boxes.size());

      for (OrientedBox box : this.boxes) {
         orientedBoxes.add(box.expand(x, y, z));
      }

      MutableBox overrideBox = null;
      if (this.overrideBox != null) {
         overrideBox = new MutableBox(this.overrideBox.getBox().m_82377_(x, y, z));
      }

      return this.cached != null
         ? new CompoundOrientedBox(
            this.f_82288_ - x,
            this.f_82289_ - y,
            this.f_82290_ - z,
            this.f_82291_ + x,
            this.f_82292_ + y,
            this.f_82293_ + z,
            orientedBoxes,
            this.cached.m_83216_(x, y, z),
            overrideBox
         )
         : new CompoundOrientedBox(
            this.f_82288_ - x, this.f_82289_ - y, this.f_82290_ - z, this.f_82291_ + x, this.f_82292_ + y, this.f_82293_ + z, orientedBoxes, overrideBox
         );
   }

   @NotNull
   public AABB m_82386_(double x, double y, double z) {
      List<OrientedBox> orientedBoxes = new ObjectArrayList(this.boxes.size());

      for (OrientedBox box : this.boxes) {
         orientedBoxes.add(box.offset(x, y, z));
      }

      MutableBox overrideBox = null;
      if (this.overrideBox != null) {
         overrideBox = new MutableBox(this.overrideBox.getBox().m_82386_(x, y, z));
      }

      return this.cached != null
         ? new CompoundOrientedBox(
            this.f_82288_ + x,
            this.f_82289_ + y,
            this.f_82290_ + z,
            this.f_82291_ + x,
            this.f_82292_ + y,
            this.f_82293_ + z,
            orientedBoxes,
            this.cached.m_83216_(x, y, z),
            overrideBox
         )
         : new CompoundOrientedBox(
            this.f_82288_ + x, this.f_82289_ + y, this.f_82290_ + z, this.f_82291_ + x, this.f_82292_ + y, this.f_82293_ + z, orientedBoxes, overrideBox
         );
   }

   @NotNull
   public AABB m_82338_(BlockPos blockPos) {
      return this.m_82386_((double)blockPos.m_123341_(), (double)blockPos.m_123342_(), (double)blockPos.m_123343_());
   }

   @NotNull
   public Optional<Vec3> m_82371_(@NotNull Vec3 min, @NotNull Vec3 max) {
      double t = Double.MAX_VALUE;

      for (OrientedBox box : this.boxes) {
         double tmp = box.raycast(min, max);
         if (tmp != -1.0) {
            t = Math.min(t, tmp);
         }
      }

      if (t != Double.MAX_VALUE) {
         double d = max.f_82479_ - min.f_82479_;
         double e = max.f_82480_ - min.f_82480_;
         double f = max.f_82481_ - min.f_82481_;
         return Optional.of(min.m_82520_(t * d, t * e, t * f));
      } else {
         return Optional.empty();
      }
   }

   @NotNull
   @Override
   public Iterator<OrientedBox> iterator() {
      return Iterators.unmodifiableIterator(this.boxes.iterator());
   }

   public boolean m_82314_(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return this.m_82381_(new AABB(minX, minY, minZ, maxX, maxY, maxZ));
   }

   public boolean m_82381_(@NotNull AABB box) {
      Vec3[] vertices = OrientedBox.getVertices(box);

      for (OrientedBox orientedBox : this.boxes) {
         if (orientedBox.intersects(vertices)) {
            return true;
         }
      }

      return false;
   }

   public VoxelShape toVoxelShape() {
      if (this.cached != null) {
         return this.cached;
      } else if (this.overrideBox != null) {
         this.cached = Shapes.m_83064_(this.overrideBox.getBox());
         return this.cached;
      } else {
         double minX = this.m_82340_(Axis.X) + 1.0E-4;
         double minY = this.m_82340_(Axis.Y) + 1.0E-4;
         double minZ = this.m_82340_(Axis.Z) + 1.0E-4;
         double deltaX = this.m_82374_(Axis.X) - minX;
         double deltaY = this.m_82374_(Axis.Y) - minY;
         double deltaZ = this.m_82374_(Axis.Z) - minZ;
         double resolution = 4.0;
         int xResolution = (int)Math.ceil(deltaX * resolution + 1.0E-4);
         int yResolution = (int)Math.ceil(deltaY * resolution + 1.0E-4);
         int zResolution = (int)Math.ceil(deltaZ * resolution + 1.0E-4);
         BitSetDiscreteVoxelShape bitSet = new BitSetDiscreteVoxelShape(xResolution, yResolution, zResolution);

         for (int i = 0; i < xResolution; i++) {
            double x = minX + (double)i / resolution;

            for (int j = 0; j < zResolution; j++) {
               double z = minZ + (double)j / resolution;

               for (int k = 0; k < yResolution; k++) {
                  double y = minY + (double)k / resolution;
                  AABB box = new AABB(x, y, z, x + 0.9999 / (double)xResolution, y + 0.9999 / (double)yResolution, z + 0.9999 / (double)zResolution);
                  if (this.m_82381_(box)) {
                     bitSet.m_142703_(i, k, j);
                  }
               }
            }
         }

         DoubleList xPoints = new DoubleArrayList(xResolution + 1);

         for (int i = 0; i < xResolution + 1; i++) {
            xPoints.add(minX + (double)i / resolution);
         }

         DoubleList yPoints = new DoubleArrayList(yResolution + 1);

         for (int i = 0; i < yResolution + 1; i++) {
            yPoints.add(minY + (double)i / resolution);
         }

         DoubleList zPoints = new DoubleArrayList(zResolution + 1);

         for (int i = 0; i < zResolution + 1; i++) {
            zPoints.add(minZ + (double)i / resolution);
         }

         return this.cached = InvokerArrayVoxelShape.init(bitSet, xPoints, yPoints, zPoints);
      }
   }

   public boolean m_82393_(double x, double y, double z) {
      for (OrientedBox box : this.boxes) {
         if (box.contains(x, y, z)) {
            return true;
         }
      }

      return false;
   }

   public CompoundOrientedBox withBounds(AABB bounds) {
      return new CompoundOrientedBox(bounds, new ObjectArrayList(this.boxes), this.overrideBox);
   }

   public double calculateMaxDistance(Axis axis, VoxelShape voxelShape, double maxDist) {
      for (AABB boundingBox : this.toVoxelShape().m_83299_()) {
         maxDist = voxelShape.m_83259_(axis, boundingBox, maxDist);
         if (Math.abs(maxDist) < 1.0E-4) {
            return 0.0;
         }
      }

      return maxDist;
   }
}
