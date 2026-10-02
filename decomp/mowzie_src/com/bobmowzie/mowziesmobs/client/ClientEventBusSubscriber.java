package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.client.gui.GuiSculptorTrade;
import com.bobmowzie.mowziesmobs.client.gui.GuiUmvuthanaTrade;
import com.bobmowzie.mowziesmobs.client.gui.GuiUmvuthiTrade;
import com.bobmowzie.mowziesmobs.client.render.block.GongRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderAxeAttack;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderBoulder;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderDart;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFallingBlock;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFoliaath;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFoliaathBaby;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFrostmaw;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderGrottol;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderIceBall;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderLantern;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderNaga;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderNothing;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderPillar;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderPoisonBall;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderRockSling;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSolarBeam;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSunstrike;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSuperNova;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthana;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderWroughtnaut;
import com.bobmowzie.mowziesmobs.server.block.entity.BlockEntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
   modid = "mowziesmobs",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class ClientEventBusSubscriber {
   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      EntityRenderers.m_174036_((EntityType)EntityHandler.BABY_FOLIAATH.get(), RenderFoliaathBaby::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.FOLIAATH.get(), RenderFoliaath::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.WROUGHTNAUT.get(), RenderWroughtnaut::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.UMVUTHI.get(), RenderUmvuthi::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.UMVUTHANA_RAPTOR.get(), RenderUmvuthana::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), RenderUmvuthana::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.UMVUTHANA_MINION.get(), RenderUmvuthana::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.get(), RenderUmvuthana::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.get(), RenderUmvuthana::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.UMVUTHANA_CRANE.get(), RenderUmvuthana::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.FROSTMAW.get(), RenderFrostmaw::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.GROTTOL.get(), RenderGrottol::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.LANTERN.get(), RenderLantern::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.NAGA.get(), RenderNaga::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.DART.get(), RenderDart::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.SUNSTRIKE.get(), RenderSunstrike::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.SOLAR_BEAM.get(), RenderSolarBeam::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.BOULDER_PROJECTILE.get(), RenderBoulder::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.BOULDER_PLATFORM.get(), RenderBoulder::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.PILLAR.get(), RenderPillar::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.PILLAR_PIECE.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.AXE_ATTACK.get(), RenderAxeAttack::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.POISON_BALL.get(), RenderPoisonBall::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.ICE_BALL.get(), RenderIceBall::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.ICE_BREATH.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.FROZEN_CONTROLLER.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.SUPER_NOVA.get(), RenderSuperNova::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.FALLING_BLOCK.get(), RenderFallingBlock::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.BLOCK_SWAPPER.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.BLOCK_SWAPPER_SCULPTOR.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.CAMERA_SHAKE.get(), RenderNothing::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)BlockEntityHandler.GONG_BLOCK_ENTITY.get(), GongRenderer::new);
      EntityRenderers.m_174036_((EntityType)EntityHandler.ROCK_SLING.get(), RenderRockSling::new);
      MenuScreens.m_96206_((MenuType)ContainerHandler.CONTAINER_UMVUTHANA_TRADE.get(), GuiUmvuthanaTrade::new);
      MenuScreens.m_96206_((MenuType)ContainerHandler.CONTAINER_UMVUTHI_TRADE.get(), GuiUmvuthiTrade::new);
      MenuScreens.m_96206_((MenuType)ContainerHandler.CONTAINER_SCULPTOR_TRADE.get(), GuiSculptorTrade::new);
   }

   @SubscribeEvent
   public static void onRegisterModels(RegisterAdditional modelRegistryEvent) {
      for (String item : MMModels.HAND_MODEL_ITEMS) {
         modelRegistryEvent.register(new ModelResourceLocation("mowziesmobs:" + item + "_in_hand", "inventory"));
      }

      for (MaskType type : MaskType.values()) {
         modelRegistryEvent.register(new ModelResourceLocation("mowziesmobs:umvuthana_mask_" + type.name + "_frame", "inventory"));
      }

      modelRegistryEvent.register(new ModelResourceLocation("mowziesmobs:sol_visage_frame", "inventory"));
   }
}
