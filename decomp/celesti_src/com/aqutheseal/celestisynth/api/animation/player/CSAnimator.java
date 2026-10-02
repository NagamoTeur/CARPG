package com.aqutheseal.celestisynth.api.animation.player;

import com.aqutheseal.celestisynth.common.item.weapons.RainfallSerenityItem;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier.PartModifier;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CSAnimator {
   public static final Map<AbstractClientPlayer, ModifierLayer<IAnimation>> animationData = new IdentityHashMap<>();
   public static final Map<AbstractClientPlayer, ModifierLayer<IAnimation>> otherAnimationData = new IdentityHashMap<>();

   public static void registerAnimationLayer(FMLClientSetupEvent event) {
      PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(CSAnimator::registerPlayerAnimation);
   }

   public static void registerPlayerAnimation(AbstractClientPlayer player, AnimationStack stack) {
      ModifierLayer<IAnimation> layer = new ModifierLayer();
      layer.addModifier(new AdjustmentModifier(partName -> {
         float xRotMod = 0.0F;
         float yRotMod = 0.0F;
         float zRotMod = 0.0F;
         float xMod = 0.0F;
         float yMod = 0.0F;
         float zMod = 0.0F;
         if (player.m_21211_().m_41720_() instanceof RainfallSerenityItem) {
            boolean checkHand = player.m_7655_() == InteractionHand.MAIN_HAND;
            float f0 = (float)Math.toRadians((double)player.m_146909_()) * 1.75F;
            float f1 = (float)Math.toRadians((double)player.m_146909_()) * 9.0F;
            float rSplit0 = rotationSplit(player.m_146909_(), f0, f1);
            if (checkHand) {
               switch (partName) {
                  case "leftArm":
                     xRotMod = (float)Math.toRadians((double)player.m_146909_()) / 6.0F;
                     if (FirstPersonMode.isFirstPersonPass()) {
                        xMod = rSplit0;
                     }
                     break;
                  case "rightArm":
                     zRotMod = (float)(-Math.toRadians((double)player.m_146909_()));
                     if (FirstPersonMode.isFirstPersonPass()) {
                        xMod = rSplit0;
                     }
                     break;
                  case "head":
                     xRotMod = (float)Math.toRadians((double)player.m_146909_());
                     break;
                  default:
                     return Optional.empty();
               }
            } else {
               switch (partName) {
                  case "rightArm":
                     xRotMod = (float)(-Math.toRadians((double)player.m_146909_())) / 6.0F;
                     if (FirstPersonMode.isFirstPersonPass()) {
                        xMod = -rSplit0;
                     }
                     break;
                  case "leftArm":
                     zRotMod = (float)Math.toRadians((double)player.m_146909_());
                     if (FirstPersonMode.isFirstPersonPass()) {
                        xMod = -rSplit0;
                     }
                     break;
                  case "head":
                     xRotMod = (float)Math.toRadians((double)player.m_146909_());
                     break;
                  default:
                     return Optional.empty();
               }
            }
         }

         if (player.m_6047_()) {
            if (partName.equals("rightArm") || partName.equals("leftArm")) {
               yMod += 3.0F;
            }

            if (partName.equals("head")) {
               yMod += 3.0F;
            }
         }

         return Optional.of(new PartModifier(new Vec3f(xRotMod, yRotMod, zRotMod), new Vec3f(xMod, yMod, zMod)));
      }), 0);
      stack.addAnimLayer(6900, layer);
      animationData.put(player, layer);
      ModifierLayer<IAnimation> layerOther = new ModifierLayer();
      stack.addAnimLayer(0, layerOther);
      otherAnimationData.put(player, layerOther);
   }

   public static float rotationSplit(float faceRotation, float faceUpValue, float faceDownValue) {
      if (faceRotation < 0.0F) {
         return faceUpValue;
      } else {
         return faceRotation > 0.0F ? faceDownValue : 0.0F;
      }
   }
}
