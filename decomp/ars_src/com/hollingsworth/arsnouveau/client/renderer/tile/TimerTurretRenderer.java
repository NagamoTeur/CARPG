package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.tile.BasicSpellTurretTile;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class TimerTurretRenderer extends BasicTurretRenderer {
   public static AnimatedGeoModel model = new GenericModel("spell_turret_timer");

   public TimerTurretRenderer(Context rendererDispatcherIn) {
      super(rendererDispatcherIn, model);
   }

   public TimerTurretRenderer(Context rendererDispatcherIn, AnimatedGeoModel<BasicSpellTurretTile> modelProvider) {
      super(rendererDispatcherIn, modelProvider);
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model);
   }
}
