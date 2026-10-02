package com.github.L_Ender.cataclysm.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;

public class BoxOctree {
   private static final int subdivideThreshold = 10;
   private static final int maximumDepth = 3;
   private final AABB boundary;
   private final Vec3i size;
   private final int depth;
   private final List<AABB> innerBoxes = new ArrayList<>();
   private final List<BoxOctree> childrenOctants = new ArrayList<>();

   public BoxOctree(AABB axisAlignedBB) {
      this(axisAlignedBB, 0);
   }

   private BoxOctree(AABB axisAlignedBB, int parentDepth) {
      this.boundary = axisAlignedBB.m_82386_(0.0, 0.0, 0.0);
      this.size = new Vec3i((int)this.boundary.m_82362_(), (int)this.boundary.m_82376_(), (int)this.boundary.m_82385_());
      this.depth = parentDepth + 1;
   }

   private void subdivide() {
      if (!this.childrenOctants.isEmpty()) {
         throw new UnsupportedOperationException("YUNG's API - Tried to subdivide when there are already children octants.");
      } else {
         int halfXSize = this.size.m_123341_() / 2;
         int halfYSize = this.size.m_123342_() / 2;
         int halfZSize = this.size.m_123343_() / 2;
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_,
                     this.boundary.f_82289_,
                     this.boundary.f_82290_,
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82290_ + (double)halfZSize
                  ),
                  this.depth
               )
            );
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82289_,
                     this.boundary.f_82290_,
                     this.boundary.f_82291_,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82290_ + (double)halfZSize
                  ),
                  this.depth
               )
            );
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82290_,
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82292_,
                     this.boundary.f_82290_ + (double)halfZSize
                  ),
                  this.depth
               )
            );
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_,
                     this.boundary.f_82289_,
                     this.boundary.f_82290_ + (double)halfZSize,
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82293_
                  ),
                  this.depth
               )
            );
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82290_,
                     this.boundary.f_82291_,
                     this.boundary.f_82292_,
                     this.boundary.f_82290_ + (double)halfZSize
                  ),
                  this.depth
               )
            );
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82290_ + (double)halfZSize,
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82292_,
                     this.boundary.f_82293_
                  ),
                  this.depth
               )
            );
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82289_,
                     this.boundary.f_82290_ + (double)halfZSize,
                     this.boundary.f_82291_,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82293_
                  ),
                  this.depth
               )
            );
         this.childrenOctants
            .add(
               new BoxOctree(
                  new AABB(
                     this.boundary.f_82288_ + (double)halfXSize,
                     this.boundary.f_82289_ + (double)halfYSize,
                     this.boundary.f_82290_ + (double)halfZSize,
                     this.boundary.f_82291_,
                     this.boundary.f_82292_,
                     this.boundary.f_82293_
                  ),
                  this.depth
               )
            );

         for (AABB parentInnerBox : this.innerBoxes) {
            for (BoxOctree octree : this.childrenOctants) {
               if (octree.boundaryIntersectsFuzzy(parentInnerBox)) {
                  octree.addBox(parentInnerBox);
               }
            }
         }

         this.innerBoxes.clear();
      }
   }

   public void addBox(AABB axisAlignedBB) {
      if (this.depth < 3 && this.innerBoxes.size() > 10) {
         this.subdivide();
      }

      if (!this.childrenOctants.isEmpty()) {
         for (BoxOctree octree : this.childrenOctants) {
            if (octree.boundaryIntersectsFuzzy(axisAlignedBB)) {
               octree.addBox(axisAlignedBB);
            }
         }
      } else {
         for (AABB parentInnerBox : this.innerBoxes) {
            if (parentInnerBox.equals(axisAlignedBB)) {
               return;
            }
         }

         this.innerBoxes.add(axisAlignedBB);
      }
   }

   public void removeBox(AABB axisAlignedBB) {
      if (!this.childrenOctants.isEmpty()) {
         for (BoxOctree octree : this.childrenOctants) {
            if (octree.boundaryIntersectsFuzzy(axisAlignedBB)) {
               octree.removeBox(axisAlignedBB);
            }
         }
      } else {
         for (AABB innerBox : this.innerBoxes) {
            if (innerBox.equals(axisAlignedBB)) {
               this.innerBoxes.remove(innerBox);
               return;
            }
         }
      }
   }

   public boolean boundaryIntersectsFuzzy(AABB axisAlignedBB) {
      return this.boundary.m_82400_(axisAlignedBB.m_82309_() / 2.0).m_82381_(axisAlignedBB);
   }

   public boolean boundaryContains(AABB axisAlignedBB) {
      return this.boundary.m_82393_(axisAlignedBB.f_82288_, axisAlignedBB.f_82289_, axisAlignedBB.f_82290_)
         && this.boundary.m_82393_(axisAlignedBB.f_82291_, axisAlignedBB.f_82292_, axisAlignedBB.f_82293_);
   }

   public boolean intersectsAnyBox(AABB axisAlignedBB) {
      if (!this.childrenOctants.isEmpty()) {
         for (BoxOctree octree : this.childrenOctants) {
            if (octree.intersectsAnyBox(axisAlignedBB)) {
               return true;
            }
         }
      } else {
         for (AABB innerBox : this.innerBoxes) {
            if (innerBox.m_82381_(axisAlignedBB)) {
               return true;
            }
         }
      }

      return false;
   }
}
