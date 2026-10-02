package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class DecoModel extends AgeableListModel {
   public DecoModel() {
      super(true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
   }

   public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
      this.f_102608_ = model.f_102608_;
      this.f_102609_ = model.f_102609_;
      this.f_102610_ = model.f_102610_;
   }

   protected Iterable<ModelPart> m_5607_() {
      return Collections.emptyList();
   }

   protected Iterable<ModelPart> m_5608_() {
      return Collections.emptyList();
   }

   public void m_6973_(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
   }

   public static ModelPart getModelPart(HumanoidModel model, String name) {
      return switch (name) {
         case "head" -> model.f_102808_;
         case "leftArm" -> model.f_102812_;
         case "rightArm" -> model.f_102811_;
         case "leftLeg" -> model.f_102814_;
         case "rightLeg" -> model.f_102813_;
         default -> model.f_102810_;
      };
   }
}
