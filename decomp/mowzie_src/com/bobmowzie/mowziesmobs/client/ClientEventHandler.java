package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.client.gui.CustomBossBar;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerFirstPerson;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerThirdPerson;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoFirstPersonRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.item.ItemBlowgun;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Post;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

@OnlyIn(Dist.CLIENT)
public enum ClientEventHandler {
   INSTANCE;

   private static final ResourceLocation FROZEN_BLUR = new ResourceLocation("textures/misc/powder_snow_outline.png");

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onHandRender(RenderHandEvent event) {
      if ((Boolean)ConfigHandler.CLIENT.customPlayerAnims.get()) {
         Player player = Minecraft.m_91087_().f_91074_;
         if (player != null) {
            boolean shouldAnimate = false;
            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
            if (abilityCapability != null) {
               shouldAnimate = abilityCapability.getActiveAbility() != null;
            }

            if (shouldAnimate) {
               PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
               if (playerCapability != null) {
                  GeckoPlayer.GeckoPlayerFirstPerson geckoPlayer = GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
                  if (geckoPlayer != null) {
                     ModelGeckoPlayerFirstPerson geckoFirstPersonModel = (ModelGeckoPlayerFirstPerson)geckoPlayer.getModel();
                     GeckoFirstPersonRenderer firstPersonRenderer = (GeckoFirstPersonRenderer)geckoPlayer.getPlayerRenderer();
                     if (geckoFirstPersonModel != null && firstPersonRenderer != null) {
                        if (!geckoFirstPersonModel.isUsingSmallArms() && ((AbstractClientPlayer)player).m_108564_().equals("slim")) {
                           firstPersonRenderer.setSmallArms();
                        }

                        event.setCanceled(true);
                        if (event.isCanceled()) {
                           float delta = event.getPartialTick();
                           float f1 = Mth.m_14179_(delta, player.f_19860_, player.m_146909_());
                           firstPersonRenderer.renderItemInFirstPerson(
                              (AbstractClientPlayer)player,
                              f1,
                              delta,
                              event.getHand(),
                              event.getSwingProgress(),
                              event.getItemStack(),
                              event.getEquipProgress(),
                              event.getPoseStack(),
                              event.getMultiBufferSource(),
                              event.getPackedLight(),
                              geckoPlayer
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void renderLivingEvent(Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> event) {
      if (event.getEntity() instanceof Player) {
         if (!(Boolean)ConfigHandler.CLIENT.customPlayerAnims.get()) {
            return;
         }

         Player player = (Player)event.getEntity();
         if (player == null) {
            return;
         }

         float delta = event.getPartialTick();
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
         if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               GeckoPlayer.GeckoPlayerThirdPerson geckoPlayer = playerCapability.getGeckoPlayer();
               if (geckoPlayer != null) {
                  ModelGeckoPlayerThirdPerson geckoPlayerModel = (ModelGeckoPlayerThirdPerson)geckoPlayer.getModel();
                  GeckoRenderPlayer animatedPlayerRenderer = (GeckoRenderPlayer)geckoPlayer.getPlayerRenderer();
                  if (geckoPlayerModel != null && animatedPlayerRenderer != null) {
                     event.setCanceled(true);
                     if (event.isCanceled()) {
                        animatedPlayerRenderer.render(
                           (AbstractClientPlayer)event.getEntity(),
                           event.getEntity().m_146908_(),
                           delta,
                           event.getPoseStack(),
                           event.getMultiBufferSource(),
                           event.getPackedLight(),
                           geckoPlayer
                        );
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent event) {
      Player player = Minecraft.m_91087_().f_91074_;
      FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(player, CapabilityHandler.FROZEN_CAPABILITY);
      if (frozenCapability != null && frozenCapability.getFrozen() && frozenCapability.getPrevFrozen()) {
         player.m_146922_(frozenCapability.getFrozenYaw());
         player.m_146926_(frozenCapability.getFrozenPitch());
         player.f_20885_ = frozenCapability.getFrozenYawHead();
         player.f_19859_ = player.m_146908_();
         player.f_19860_ = player.m_146909_();
         player.f_20886_ = player.f_20885_;
      }
   }

   @SubscribeEvent
   public void onRenderLiving(Pre event) {
      LivingEntity entity = event.getEntity();
      FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.FROZEN_CAPABILITY);
      if (frozenCapability != null && frozenCapability.getFrozen() && frozenCapability.getPrevFrozen()) {
         entity.m_146922_(entity.f_19859_ = frozenCapability.getFrozenYaw());
         entity.m_146926_(entity.f_19860_ = frozenCapability.getFrozenPitch());
         entity.f_20885_ = entity.f_20886_ = frozenCapability.getFrozenYawHead();
         entity.f_20883_ = entity.f_20884_ = frozenCapability.getFrozenRenderYawOffset();
         entity.f_20921_ = entity.f_20920_ = frozenCapability.getFrozenSwingProgress();
         entity.f_20924_ = entity.f_20923_ = frozenCapability.getFrozenLimbSwingAmount();
         entity.m_20260_(false);
      }
   }

   @SubscribeEvent
   public void onRenderOverlay(Post e) {
      int startTime = 210;
      int pointStart = 1200;
      int timePerMillis = 22;
      if (e.getOverlay() == VanillaGuiOverlay.FROSTBITE.type() && Minecraft.m_91087_().f_91074_ != null) {
         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(
            Minecraft.m_91087_().f_91074_, CapabilityHandler.FROZEN_CAPABILITY
         );
         if (frozenCapability != null && frozenCapability.getFrozen() && Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON) {
            RenderSystem.m_157456_(0, FROZEN_BLUR);
            Window res = e.getWindow();
            GuiComponent.m_93133_(e.getPoseStack(), 0, 0, 0.0F, 0.0F, res.m_85445_(), res.m_85446_(), res.m_85445_(), res.m_85446_());
         }
      }
   }

   @SubscribeEvent
   public void onRenderHUD(net.minecraftforge.client.event.RenderGuiOverlayEvent.Pre event) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null
         && player.m_20159_()
         && player.m_20202_() instanceof EntityFrozenController
         && event.getOverlay() == VanillaGuiOverlay.MOUNT_HEALTH.type()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void updateFOV(ComputeFovModifierEvent event) {
      Player player = event.getPlayer();
      if (player.m_6117_() && player.m_21211_().m_41720_() instanceof ItemBlowgun) {
         int i = player.m_21252_();
         float f1 = (float)i / 5.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F;
         } else {
            f1 *= f1;
         }

         event.setNewFovModifier(1.0F - f1 * 0.15F);
      }
   }

   @SubscribeEvent
   public void onSetupCamera(ComputeCameraAngles event) {
      Player player = Minecraft.m_91087_().f_91074_;
      float delta = Minecraft.m_91087_().m_91296_();
      float ticksExistedDelta = (float)player.f_19797_ + delta;
      if (player != null && (Boolean)ConfigHandler.CLIENT.doCameraShakes.get() && !Minecraft.m_91087_().m_91104_()) {
         float shakeAmplitude = 0.0F;

         for (EntityCameraShake cameraShake : player.f_19853_.m_45976_(EntityCameraShake.class, player.m_20191_().m_82377_(20.0, 20.0, 20.0))) {
            if (cameraShake.m_20270_(player) < cameraShake.getRadius()) {
               shakeAmplitude += cameraShake.getShakeAmount(player, delta);
            }
         }

         if (shakeAmplitude > 1.0F) {
            shakeAmplitude = 1.0F;
         }

         event.setPitch((float)((double)event.getPitch() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 3.0F + 2.0F)) * 25.0));
         event.setYaw((float)((double)event.getYaw() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 5.0F + 1.0F)) * 25.0));
         event.setRoll((float)((double)event.getRoll() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 4.0F)) * 25.0));
      }
   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      if (event.phase != Phase.START && event.player != null) {
         Player player = event.player;
         PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
         if (playerCapability != null && event.side == LogicalSide.CLIENT) {
            GeckoPlayer geckoPlayer = playerCapability.getGeckoPlayer();
            if (geckoPlayer != null) {
               geckoPlayer.tick();
            }

            if (player == Minecraft.m_91087_().f_91074_) {
               GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON.tick();
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onRenderBossBar(BossEventProgress event) {
      if ((Boolean)ConfigHandler.CLIENT.customBossBars.get()) {
         ResourceLocation bossRegistryName = ClientProxy.bossBarRegistryNames.getOrDefault(event.getBossEvent().m_18860_(), null);
         if (bossRegistryName != null) {
            CustomBossBar customBossBar = CustomBossBar.customBossBars.getOrDefault(bossRegistryName, null);
            if (customBossBar != null) {
               event.setCanceled(true);
               customBossBar.renderBossBar(event);
            }
         }
      }
   }

   private void drawBar(PoseStack stack, int x, int y, BossEvent p_93710_) {
      Minecraft.m_91087_().f_91065_.m_93228_(stack, x, y, 0, p_93710_.m_18862_().ordinal() * 5 * 2, 182, 6);
      int i = (int)(p_93710_.m_142717_() * 183.0F);
      if (i > 0) {
         Minecraft.m_91087_().f_91065_.m_93228_(stack, x, y, 0, p_93710_.m_18862_().ordinal() * 5 * 2 + 5, i, 6);
      }
   }
}
