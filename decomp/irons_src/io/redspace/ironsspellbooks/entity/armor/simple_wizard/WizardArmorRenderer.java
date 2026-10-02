package io.redspace.ironsspellbooks.entity.armor.simple_wizard;

import io.redspace.ironsspellbooks.item.armor.WizardArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.util.GeoUtils;

public class WizardArmorRenderer extends GeoArmorRenderer<WizardArmorItem> {
   public String leggingTorsoLayer = "armorLeggingTorsoLayer";
   private final GeoBone leggingTorsoLayerBone;

   public WizardArmorRenderer() {
      super(new WizardArmorModel());
      this.headBone = "armorHead";
      this.bodyBone = "armorBody";
      this.rightArmBone = "armorRightArm";
      this.leftArmBone = "armorLeftArm";
      this.rightLegBone = "armorRightLeg";
      this.leftLegBone = "armorLeftLeg";
      this.rightBootBone = "armorRightBoot";
      this.leftBootBone = "armorLeftBoot";
      this.leggingTorsoLayerBone = new GeoBone();
      this.leggingTorsoLayerBone.name = "armorLeggingTorsoLayer";
   }

   protected void fitToBiped() {
      super.fitToBiped();
      this.ensureBone();
      if (this.leggingTorsoLayer != null) {
         IBone torsoLayerBone = this.getGeoModelProvider().getBone(this.leggingTorsoLayer);
         GeoUtils.copyRotations(this.f_102810_, torsoLayerBone);
         torsoLayerBone.setPositionX(this.f_102810_.f_104200_);
         torsoLayerBone.setPositionY(-this.f_102810_.f_104201_);
         torsoLayerBone.setPositionZ(this.f_102810_.f_104202_);
      }
   }

   public GeoArmorRenderer applySlot(EquipmentSlot slot) {
      this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource((WizardArmorItem)this.currentArmorItem));
      this.ensureBone();
      this.setBoneVisibility(this.headBone, false);
      this.setBoneVisibility(this.bodyBone, false);
      this.setBoneVisibility(this.rightArmBone, false);
      this.setBoneVisibility(this.leftArmBone, false);
      this.setBoneVisibility(this.rightLegBone, false);
      this.setBoneVisibility(this.leftLegBone, false);
      this.setBoneVisibility(this.rightBootBone, false);
      this.setBoneVisibility(this.rightBootBone, false);
      this.setBoneVisibility(this.leftBootBone, false);
      this.setBoneVisibility(this.leggingTorsoLayer, false);
      switch (slot) {
         case HEAD:
            this.setBoneVisibility(this.headBone, true);
            break;
         case CHEST:
            this.setBoneVisibility(this.bodyBone, true);
            this.setBoneVisibility(this.rightArmBone, true);
            this.setBoneVisibility(this.leftArmBone, true);
            break;
         case LEGS:
            this.setBoneVisibility(this.rightLegBone, true);
            this.setBoneVisibility(this.leftLegBone, true);
            this.setBoneVisibility(this.leggingTorsoLayer, true);
            break;
         case FEET:
            this.setBoneVisibility(this.rightBootBone, true);
            this.setBoneVisibility(this.leftBootBone, true);
      }

      return this;
   }

   private void ensureBone() {
      AnimatedGeoModel<WizardArmorItem> model = this.getGeoModelProvider();
      if (!model.getAnimationProcessor().getModelRendererList().contains(this.leggingTorsoLayerBone)) {
         model.registerBone(this.leggingTorsoLayerBone);
      }
   }
}
