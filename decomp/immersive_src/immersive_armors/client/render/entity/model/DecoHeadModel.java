package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class DecoHeadModel extends DecoModel {
   abstract ModelPart getPart();

   @Override
   public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
      this.getPart().m_104315_(model.f_102808_);
      super.copyFromModel(model, slot);
   }

   @Override
   protected Iterable<ModelPart> m_5607_() {
      return Collections.singletonList(this.getPart());
   }
}
