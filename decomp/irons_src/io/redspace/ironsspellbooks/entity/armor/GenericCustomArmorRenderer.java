package io.redspace.ironsspellbooks.entity.armor;

import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.util.GeoUtils;

public class GenericCustomArmorRenderer<T extends GeoArmorItem & IAnimatable> extends GeoArmorRenderer<T> {
   public static final String leggingTorsoLayerBone = "armorLeggingTorsoLayer";

   public GenericCustomArmorRenderer(AnimatedGeoModel model) {
      super(model);
      this.headBone = "armorHead";
      this.bodyBone = "armorBody";
      this.rightArmBone = "armorRightArm";
      this.leftArmBone = "armorLeftArm";
      this.rightLegBone = "armorRightLeg";
      this.leftLegBone = "armorLeftLeg";
      this.rightBootBone = "armorRightBoot";
      this.leftBootBone = "armorLeftBoot";
      AnimatedGeoModel<T> m = this.getGeoModelProvider();
      m.registerBone(this.customBone("armorLeggingTorsoLayer"));
   }

   protected void fitToBiped() {
      super.fitToBiped();
      IBone torsoLayerBone = this.getGeoModelProvider().getBone("armorLeggingTorsoLayer");
      GeoUtils.copyRotations(this.f_102810_, torsoLayerBone);
      torsoLayerBone.setPositionX(this.f_102810_.f_104200_);
      torsoLayerBone.setPositionY(-this.f_102810_.f_104201_);
      torsoLayerBone.setPositionZ(this.f_102810_.f_104202_);
   }

   public GeoArmorRenderer applySlot(EquipmentSlot slot) {
      this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource((GeoArmorItem)this.currentArmorItem));
      this.setBoneVisibility(this.headBone, false);
      this.setBoneVisibility(this.bodyBone, false);
      this.setBoneVisibility(this.rightArmBone, false);
      this.setBoneVisibility(this.leftArmBone, false);
      this.setBoneVisibility(this.rightLegBone, false);
      this.setBoneVisibility(this.leftLegBone, false);
      this.setBoneVisibility(this.rightBootBone, false);
      this.setBoneVisibility(this.rightBootBone, false);
      this.setBoneVisibility(this.leftBootBone, false);
      this.setBoneVisibility("armorLeggingTorsoLayer", false);
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
            this.setBoneVisibility("armorLeggingTorsoLayer", true);
            break;
         case FEET:
            this.setBoneVisibility(this.rightBootBone, true);
            this.setBoneVisibility(this.leftBootBone, true);
      }

      return this;
   }

   protected GeoBone customBone(String name) {
      GeoBone bone = new GeoBone();
      bone.name = name;
      return bone;
   }
}
