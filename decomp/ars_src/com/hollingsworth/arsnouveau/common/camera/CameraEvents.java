package com.hollingsworth.arsnouveau.common.camera;

import com.hollingsworth.arsnouveau.api.camera.ICameraMountable;
import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class CameraEvents {
   @SubscribeEvent
   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      ServerPlayer player = (ServerPlayer)event.getEntity();
      if (player.m_8954_() instanceof ScryerCamera cam) {
         if (player.f_19853_.m_7702_(cam.m_20183_()) instanceof ICameraMountable camBe) {
            camBe.stopViewing();
         }

         cam.m_146870_();
      }
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingHurtEvent event) {
      LivingEntity entity = event.getEntity();
      Level level = entity.f_19853_;
      if (!level.f_46443_ && entity instanceof ServerPlayer player && CameraUtil.isPlayerMountedOnCamera(entity)) {
         ((ScryerCamera)player.m_8954_()).stopViewing(player);
      }
   }

   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      if (CameraUtil.isPlayerMountedOnCamera(event.getEntity())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onLeftClickBlock(LeftClickBlock event) {
      if (CameraUtil.isPlayerMountedOnCamera(event.getEntity())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onRightClickItem(RightClickItem event) {
      if (CameraUtil.isPlayerMountedOnCamera(event.getEntity())) {
         event.setCanceled(true);
      }
   }
}
