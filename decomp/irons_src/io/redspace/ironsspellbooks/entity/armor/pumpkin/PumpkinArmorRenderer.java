package io.redspace.ironsspellbooks.entity.armor.pumpkin;

import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.item.armor.PumpkinArmorItem;
import java.util.Objects;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.util.GeoUtils;

public class PumpkinArmorRenderer extends GenericCustomArmorRenderer<PumpkinArmorItem> {
   public static final String bodyHeadLayerBone = "armorBodyHeadLayer";

   public PumpkinArmorRenderer(AnimatedGeoModel model) {
      super(model);
      AnimatedGeoModel<PumpkinArmorItem> m = this.getGeoModelProvider();
      m.registerBone(this.customBone("armorBodyHeadLayer"));
   }

   @Override
   protected void fitToBiped() {
      super.fitToBiped();
      IBone torsoLayerBone = this.getGeoModelProvider().getBone("armorBodyHeadLayer");
      GeoUtils.copyRotations(this.f_102808_, torsoLayerBone);
      torsoLayerBone.setPositionX(this.f_102808_.f_104200_);
      torsoLayerBone.setPositionY(-this.f_102808_.f_104201_);
      torsoLayerBone.setPositionZ(this.f_102808_.f_104202_);
   }

   @Override
   public GeoArmorRenderer applySlot(EquipmentSlot slot) {
      super.applySlot(slot);
      this.setBoneVisibility("armorBodyHeadLayer", false);
      if (Objects.requireNonNull(slot) == EquipmentSlot.CHEST) {
         this.setBoneVisibility("armorBodyHeadLayer", true);
      }

      if (this.entityLiving instanceof IAnimatable) {
         this.setBoneVisibility("armorBodyHeadLayer", false);
      }

      return this;
   }
}
