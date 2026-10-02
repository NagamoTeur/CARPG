package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.common.block.tile.ScryersOculusTile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class ScryersEyeModel extends AnimatedGeoModel<ScryersOculusTile> {
   public static ResourceLocation SQUINTING = new ResourceLocation("ars_nouveau", "textures/blocks/scryers_eye_squinting.png");
   public static ResourceLocation ALERT = new ResourceLocation("ars_nouveau", "textures/blocks/scryers_eye_alert.png");
   public static ResourceLocation IDLE = new ResourceLocation("ars_nouveau", "textures/blocks/scryers_eye_idle.png");
   public static ResourceLocation SLEEPING = new ResourceLocation("ars_nouveau", "textures/blocks/scryers_eye_sleeping.png");
   public static final ResourceLocation anim = new ResourceLocation("ars_nouveau", "animations/scryers_eye_animations.json");
   public static final ResourceLocation model = new ResourceLocation("ars_nouveau", "geo/scryers_eye.geo.json");

   public ResourceLocation getModelResource(ScryersOculusTile object) {
      return model;
   }

   public ResourceLocation getTextureResource(ScryersOculusTile object) {
      return object != null && object.playerNear ? SQUINTING : IDLE;
   }

   public ResourceLocation getAnimationResource(ScryersOculusTile animatable) {
      return anim;
   }

   public void setCustomAnimations(ScryersOculusTile pBlockEntity, int uniqueID) {
      super.setCustomAnimations(pBlockEntity, uniqueID);
      IBone eye = this.getAnimationProcessor().getBone("eye");
      if (eye != null) {
         float f1 = pBlockEntity.rot - pBlockEntity.oRot;

         while (f1 >= (float) Math.PI) {
            f1 -= (float) (Math.PI * 2);
         }

         while (f1 < (float) -Math.PI) {
            f1 += (float) (Math.PI * 2);
         }

         float f2 = pBlockEntity.oRot + f1 * ClientInfo.partialTicks - 4.7F;
         eye.setRotationY(-f2);
         eye.setPositionY(Mth.m_14031_(((float)ClientInfo.ticksInGame + ClientInfo.partialTicks) / 10.0F) / 2.0F);
      }
   }
}
