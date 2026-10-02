package com.hollingsworth.arsnouveau.common.camera;

import com.hollingsworth.arsnouveau.common.block.ScryerCrystal;
import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketDismountCamera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientChunkCache.Storage;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau",
   value = {Dist.CLIENT}
)
public class CameraController {
   public static CameraType previousCameraType;
   public static boolean resetOverlaysAfterDismount = false;
   private static Storage cameraStorage;
   private static boolean wasUpPressed;
   private static boolean wasDownPressed;
   private static boolean wasLeftPressed;
   private static boolean wasRightPressed;

   @SubscribeEvent
   public static void onClientTick(ClientTickEvent event) {
      if (Minecraft.m_91087_().f_91075_ instanceof ScryerCamera cam) {
         Options options = Minecraft.m_91087_().f_91066_;
         if (event.phase == Phase.START) {
            if (wasUpPressed = options.f_92085_.m_90857_()) {
               options.f_92085_.m_7249_(false);
            }

            if (wasDownPressed = options.f_92087_.m_90857_()) {
               options.f_92087_.m_7249_(false);
            }

            if (wasLeftPressed = options.f_92086_.m_90857_()) {
               options.f_92086_.m_7249_(false);
            }

            if (wasRightPressed = options.f_92088_.m_90857_()) {
               options.f_92088_.m_7249_(false);
            }

            if (options.f_92090_.m_90857_()) {
               dismount();
               options.f_92090_.m_7249_(false);
            }
         } else if (event.phase == Phase.END) {
            if (wasUpPressed) {
               moveViewUp(cam);
               options.f_92085_.m_7249_(true);
            }

            if (wasDownPressed) {
               moveViewDown(cam);
               options.f_92087_.m_7249_(true);
            }

            if (wasLeftPressed) {
               moveViewHorizontally(cam, cam.m_146908_(), cam.m_146908_() - (float)cam.cameraSpeed * cam.zoomAmount);
               options.f_92086_.m_7249_(true);
            }

            if (wasRightPressed) {
               moveViewHorizontally(cam, cam.m_146908_(), cam.m_146908_() + (float)cam.cameraSpeed * cam.zoomAmount);
               options.f_92088_.m_7249_(true);
            }

            LocalPlayer player = Minecraft.m_91087_().f_91074_;
            double yRotChange = (double)(player.m_146908_() - player.f_108598_);
            double xRotChange = (double)(player.m_146909_() - player.f_108599_);
            if (yRotChange != 0.0 || xRotChange != 0.0) {
               player.f_108617_.m_104955_(new Rot(player.m_146908_(), player.m_146909_(), player.m_20096_()));
            }
         }
      }
   }

   private static void dismount() {
      Networking.INSTANCE.sendToServer(new PacketDismountCamera());
   }

   public static void moveViewUp(ScryerCamera cam) {
      float next = cam.m_146909_() - (float)cam.cameraSpeed * cam.zoomAmount;
      if (cam.isCameraDown()) {
         if (next > 40.0F) {
            cam.setRotation(cam.m_146908_(), next);
         }
      } else if (next > -25.0F) {
         cam.setRotation(cam.m_146908_(), next);
      }
   }

   public static void moveViewDown(ScryerCamera cam) {
      float next = cam.m_146909_() + (float)cam.cameraSpeed * cam.zoomAmount;
      if (cam.isCameraDown()) {
         if (next < 90.0F) {
            cam.setRotation(cam.m_146908_(), next);
         }
      } else if (next < 60.0F) {
         cam.setRotation(cam.m_146908_(), next);
      }
   }

   public static void moveViewHorizontally(ScryerCamera cam, float yRot, float next) {
      BlockState state = cam.f_19853_.m_8055_(cam.m_20183_());
      if (state.m_61138_(ScryerCrystal.FACING)) {
         float checkNext = next;
         if (next < 0.0F) {
            checkNext = next + 360.0F;
         }
         boolean shouldSetRotation = switch ((Direction)state.m_61143_(ScryerCrystal.FACING)) {
            case NORTH -> {
               if (checkNext > 90.0F && checkNext < 270.0F) {
                  yield true;
               }

               yield false;
            }
            case SOUTH -> {
               if (!(checkNext > 270.0F) && !(checkNext < 90.0F)) {
                  yield false;
               }

               yield true;
            }
            case EAST -> {
               if (checkNext > 180.0F && checkNext < 360.0F) {
                  yield true;
               }

               yield false;
            }
            case WEST -> {
               if (checkNext > 0.0F && checkNext < 180.0F) {
                  yield true;
               }

               yield false;
            }
            case DOWN -> true;
            default -> false;
         };
         if (shouldSetRotation) {
            cam.m_146922_(next);
         }
      }
   }

   public static void zoomIn(ScryerCamera cam) {
      cam.zooming = true;
      cam.zoomAmount = Math.max(cam.zoomAmount - 0.1F, 0.1F);
   }

   public static void zoomOut(ScryerCamera cam) {
      cam.zooming = true;
      cam.zoomAmount = Math.min(cam.zoomAmount + 0.1F, 1.4F);
   }

   public static Storage getCameraStorage() {
      return cameraStorage;
   }

   public static void setCameraStorage(Storage cameraStorage) {
      if (cameraStorage != null) {
         CameraController.cameraStorage = cameraStorage;
      }
   }

   public static void setRenderPosition(Entity entity) {
      if (entity instanceof ScryerCamera) {
         SectionPos cameraPos = SectionPos.m_235861_(entity);
         cameraStorage.f_104469_ = cameraPos.m_123170_();
         cameraStorage.f_104470_ = cameraPos.m_123222_();
      }
   }
}
