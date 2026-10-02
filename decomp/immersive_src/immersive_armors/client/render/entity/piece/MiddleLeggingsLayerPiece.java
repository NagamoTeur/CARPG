package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class MiddleLeggingsLayerPiece extends LayerPiece {
   private static final HumanoidModel<LivingEntity> model = buildDilatedModel(0.5F);

   public MiddleLeggingsLayerPiece() {
      this.texture("leggings_middle");
   }

   @Override
   protected HumanoidModel<LivingEntity> getModel() {
      return model;
   }
}
