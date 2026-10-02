package immersive_armors.client.render.entity.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;

public class PrismarineModel extends DecoModel {
   private final List<ModelPart> parts = new LinkedList<>();
   private static final float[][] SPIKE_PITCHES = new float[][]{
      {45.0F, 45.0F, 45.0F, 45.0F}, {45.0F, 45.0F, 135.0F}, {45.0F, 45.0F, 135.0F}, {135.0F}, {135.0F}
   };
   private static final float[][] SPIKE_YAWS = new float[][]{
      {225.0F, 135.0F, 45.0F, 315.0F}, {135.0F, 45.0F, 90.0F}, {225.0F, 315.0F, 270.0F}, {90.0F}, {270.0F}
   };
   private static final float[][] SPIKE_ROLLS = new float[][]{
      {0.0F, 0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 0.0F}
   };
   private static final float[][] SPIKE_PIVOTS_X = new float[][]{{5.0F, -5.0F, -5.0F, 5.0F}, {-5.5F, -5.5F, -6.0F}, {5.5F, 5.5F, 6.0F}, {-5.0F}, {5.0F}};
   private static final float[][] SPIKE_PIVOTS_Y = new float[][]{{-10.0F, -10.0F, -10.0F, -10.0F}, {-5.0F, -5.0F, 6.0F}, {-5.0F, -5.0F, 6.0F}, {6.0F}, {6.0F}};
   private static final float[][] SPIKE_PIVOTS_Z = new float[][]{{5.0F, 5.0F, -5.0F, -5.0F}, {4.5F, -4.5F, 0.0F}, {4.5F, -4.5F, 0.0F}, {0.0F}, {0.0F}};

   public PrismarineModel() {
      MeshDefinition modelData = new MeshDefinition();

      for (int t = 0; t < SPIKE_PIVOTS_X.length; t++) {
         PartDefinition data = modelData.m_171576_().m_171599_("part_" + t, CubeListBuilder.m_171558_(), PartPose.f_171404_);

         for (int i = 0; i < SPIKE_PIVOTS_X[t].length; i++) {
            data.m_171599_(
               "spike_" + i,
               CubeListBuilder.m_171558_().m_171481_(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F),
               PartPose.m_171423_(
                  SPIKE_PIVOTS_X[t][i],
                  SPIKE_PIVOTS_Y[t][i],
                  SPIKE_PIVOTS_Z[t][i],
                  (float)((double)(SPIKE_PITCHES[t][i] / 180.0F) * Math.PI),
                  (float)((double)(SPIKE_YAWS[t][i] / 180.0F) * Math.PI),
                  (float)((double)(SPIKE_ROLLS[t][i] / 180.0F) * Math.PI)
               )
            );
         }

         this.parts.add(data.m_171583_(8, 8));
      }
   }

   @Override
   protected Iterable<ModelPart> m_5607_() {
      return Collections.singletonList(this.parts.get(0));
   }

   @Override
   protected Iterable<ModelPart> m_5608_() {
      return this.parts.subList(1, this.parts.size());
   }

   @Override
   public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
      this.parts.forEach(p -> p.f_104207_ = false);
      switch (slot) {
         case HEAD:
            this.parts.get(0).m_104315_(model.f_102808_);
            this.parts.get(0).f_104207_ = true;
            break;
         case CHEST:
            this.parts.get(1).m_104315_(model.f_102811_);
            this.parts.get(2).m_104315_(model.f_102812_);
            this.parts.get(1).f_104207_ = true;
            this.parts.get(2).f_104207_ = true;
            break;
         case LEGS:
            this.parts.get(3).m_104315_(model.f_102813_);
            this.parts.get(4).m_104315_(model.f_102814_);
            this.parts.get(3).f_104207_ = true;
            this.parts.get(4).f_104207_ = true;
      }

      super.copyFromModel(model, slot);
   }
}
