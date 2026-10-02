package com.github.L_Ender.cataclysm.client.event;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.ClientProxy;
import com.github.L_Ender.cataclysm.client.gui.CustomBossBar;
import com.github.L_Ender.cataclysm.client.model.entity.PlayerSandstorm_Model;
import com.github.L_Ender.cataclysm.client.render.CMItemstackRenderer;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.etc.LavaVisionFluidRenderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosItemREnderer.Blazing_Grips_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosItemREnderer.RendererSticky_Gloves;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Tongue_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Aptrgangr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.lionfishapi.client.event.EventGetFluidRenderType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Post;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Pre;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@OnlyIn(Dist.CLIENT)
public class ClientEvent {
   public static final ResourceLocation FLAME_STRIKE = new ResourceLocation("cataclysm", "textures/entity/soul_flame_strike_sigil.png");
   public static final ResourceLocation NORMAL_FLAME_STRIKE = new ResourceLocation("cataclysm", "textures/entity/flame_strike_sigil.png");
   private boolean previousLavaVision = false;
   private LiquidBlockRenderer previousFluidRenderer;
   private static final ResourceLocation SANDSTORM_ICON = new ResourceLocation("cataclysm", "textures/gui/sandstorm_icons.png");
   private static final ResourceLocation EFFECT_HEART = new ResourceLocation("cataclysm", "textures/gui/effect_heart.png");
   private static final ResourceLocation SANDSTORM_TEXTURE = new ResourceLocation("cataclysm", "textures/entity/ancient_remnant/sandstorm.png");
   private static final PlayerSandstorm_Model SANDSTORM_MODEL = new PlayerSandstorm_Model();
   private final Random random = new Random();
   private int lastHealth;
   private int displayHealth;
   private long lastHealthTime;
   private long healthBlinkTime;

   @SubscribeEvent
   public void onCameraSetup(ComputeCameraAngles event) {
      Player player = Minecraft.m_91087_().f_91074_;
      float delta = Minecraft.m_91087_().m_91296_();
      float ticksExistedDelta = (float)player.f_19797_ + delta;
      if (CMConfig.ScreenShake && !Minecraft.m_91087_().m_91104_()) {
         if (player != null) {
            float shakeAmplitude = 0.0F;

            for (ScreenShake_Entity ScreenShake : player.f_19853_.m_45976_(ScreenShake_Entity.class, player.m_20191_().m_82377_(20.0, 20.0, 20.0))) {
               if (ScreenShake.m_20270_(player) < ScreenShake.getRadius()) {
                  shakeAmplitude += ScreenShake.getShakeAmount(player, delta);
               }
            }

            if (shakeAmplitude > 1.0F) {
               shakeAmplitude = 1.0F;
            }

            event.setPitch((float)((double)event.getPitch() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 3.0F + 2.0F)) * 25.0));
            event.setYaw((float)((double)event.getYaw() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 5.0F + 1.0F)) * 25.0));
            event.setRoll((float)((double)event.getRoll() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 4.0F)) * 25.0));
         }

         if (Minecraft.m_91087_().f_91074_.m_21124_((MobEffect)ModEffect.EFFECTSTUN.get()) != null) {
            MobEffectInstance effectinstance1 = Minecraft.m_91087_().f_91074_.m_21124_((MobEffect)ModEffect.EFFECTSTUN.get());
            float shakeAmplitude = (float)((double)(1 + effectinstance1.m_19564_()) * 0.01);
            event.setPitch((float)((double)event.getPitch() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 3.0F + 2.0F)) * 25.0));
            event.setYaw((float)((double)event.getYaw() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 5.0F + 1.0F)) * 25.0));
            event.setRoll((float)((double)event.getRoll() + (double)shakeAmplitude * Math.cos((double)(ticksExistedDelta * 4.0F)) * 25.0));
         }
      }

      Entity cameraEntity = Minecraft.m_91087_().m_91288_();
      if (cameraEntity != null && cameraEntity.m_20159_() && cameraEntity.m_20202_() instanceof Maledictus_Entity && event.getCamera().m_90594_()) {
         event.getCamera().m_90568_(-event.getCamera().m_90566_(6.0), 0.0, 0.0);
      }

      if (cameraEntity != null && cameraEntity.m_20159_() && cameraEntity.m_20202_() instanceof Aptrgangr_Entity && event.getCamera().m_90594_()) {
         event.getCamera().m_90568_(-event.getCamera().m_90566_(3.0), 0.0, 0.0);
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onFogDensity(RenderFog event) {
      FogType fogType = event.getCamera().m_167685_();
      ItemStack itemstack = Minecraft.m_91087_().f_91074_.m_150109_().m_36052_(3);
      if (itemstack.m_150930_((Item)ModItems.IGNITIUM_HELMET.get()) && fogType == FogType.LAVA) {
         RenderSystem.m_157445_(-8.0F);
         RenderSystem.m_157443_(50.0F);
      }
   }

   @SubscribeEvent
   public void MovementInput(MovementInputUpdateEvent event) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null && player.m_21023_((MobEffect)ModEffect.EFFECTCURSE_OF_DESERT.get())) {
         if (Minecraft.m_91087_().f_91066_.f_92087_.m_90857_()) {
            event.getInput().f_108567_ += 2.0F;
         }

         if (Minecraft.m_91087_().f_91066_.f_92086_.m_90857_()) {
            event.getInput().f_108566_ -= 2.0F;
         }

         if (Minecraft.m_91087_().f_91066_.f_92088_.m_90857_()) {
            event.getInput().f_108566_ += 2.0F;
         }

         if (Minecraft.m_91087_().f_91066_.f_92085_.m_90857_()) {
            event.getInput().f_108567_ -= 2.0F;
         }
      }
   }

   @SubscribeEvent
   public void onPreRenderHUD(Pre event) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         Minecraft mc = Minecraft.m_91087_();
         ForgeGui gui = (ForgeGui)mc.f_91065_;
         if (player.m_20159_()
            && (player.m_20202_() instanceof The_Leviathan_Tongue_Entity || player.m_20202_() instanceof IHoldEntity)
            && event.getOverlay().id().equals(VanillaGuiOverlay.HELMET.id())) {
            Minecraft.m_91087_().f_91065_.m_93063_(Component.m_237115_("entity.cataclysm.you_cant_escape"), false);
         }

         if (event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type()
            && !mc.f_91066_.f_92062_
            && gui.shouldDrawSurvivalElements()
            && (player.m_21023_((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get()) || player.m_21023_((MobEffect)ModEffect.EFFECTABYSSAL_CURSE.get()))) {
            this.CustomHealth(event, 25);
         }
      }
   }

   @SubscribeEvent
   public void onPostRenderHUD(Post event) {
      Player player = Minecraft.m_91087_().f_91074_;
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onPreRenderEntity(net.minecraftforge.client.event.RenderLivingEvent.Pre event) {
      LivingEntity player = event.getEntity();
      boolean usingIncinerator = player.m_6117_() && player.m_21211_().m_150930_((Item)ModItems.THE_INCINERATOR.get());
      boolean usingImmolator = player.m_6117_() && player.m_21211_().m_150930_((Item)ModItems.THE_IMMOLATOR.get());
      if (usingIncinerator) {
         int i = player.m_21252_();
         float f2 = (float)player.f_19797_ + event.getPartialTick();
         PoseStack matrixStackIn = event.getPoseStack();
         float f3 = (float)Mth.m_14045_(i, 1, 60);
         matrixStackIn.m_85836_();
         VertexConsumer ivertexbuilder = ItemRenderer.m_115184_(event.getMultiBufferSource(), CMRenderTypes.getGlowingEffect(FLAME_STRIKE), false, true);
         matrixStackIn.m_85837_(0.0, 0.001, 0.0);
         matrixStackIn.m_85841_(f3 * 0.05F, f3 * 0.05F, f3 * 0.05F);
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F + f2));
         Pose lvt_19_1_ = matrixStackIn.m_85850_();
         Matrix4f lvt_20_1_ = lvt_19_1_.m_85861_();
         Matrix3f lvt_21_1_ = lvt_19_1_.m_85864_();
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
         matrixStackIn.m_85849_();
      }

      if (usingImmolator) {
         int i = player.m_21252_();
         float f2 = (float)player.f_19797_ + event.getPartialTick();
         PoseStack matrixStackIn = event.getPoseStack();
         float f3 = (float)Mth.m_14045_(i, 1, 45);
         matrixStackIn.m_85836_();
         VertexConsumer ivertexbuilder = ItemRenderer.m_115184_(event.getMultiBufferSource(), CMRenderTypes.getGlowingEffect(NORMAL_FLAME_STRIKE), false, true);
         matrixStackIn.m_85837_(0.0, 0.001, 0.0);
         matrixStackIn.m_85841_(f3 * 0.05F, f3 * 0.05F, f3 * 0.05F);
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F + f2));
         Pose lvt_19_1_ = matrixStackIn.m_85850_();
         Matrix4f lvt_20_1_ = lvt_19_1_.m_85861_();
         Matrix3f lvt_21_1_ = lvt_19_1_.m_85864_();
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
         matrixStackIn.m_85849_();
      }

      if (ClientProxy.blockedEntityRenders.contains(event.getEntity().m_20148_())) {
         if (!Cataclysm.PROXY.isFirstPersonPlayer(event.getEntity())) {
            MinecraftForge.EVENT_BUS
               .post(
                  new net.minecraftforge.client.event.RenderLivingEvent.Post(
                     event.getEntity(), event.getRenderer(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()
                  )
               );
            event.setCanceled(true);
         }

         ClientProxy.blockedEntityRenders.remove(event.getEntity().m_20148_());
      }
   }

   public void drawVertex(
      Matrix4f p_229039_1_,
      Matrix3f p_229039_2_,
      VertexConsumer p_229039_3_,
      int p_229039_4_,
      int p_229039_5_,
      int p_229039_6_,
      float p_229039_7_,
      float p_229039_8_,
      int p_229039_9_,
      int p_229039_10_,
      int p_229039_11_,
      int p_229039_12_
   ) {
      p_229039_3_.m_85982_(p_229039_1_, (float)p_229039_4_, (float)p_229039_5_, (float)p_229039_6_)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(p_229039_7_, p_229039_8_)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(p_229039_12_)
         .m_85977_(p_229039_2_, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_)
         .m_5752_();
   }

   @SubscribeEvent
   public void clientTick(ClientTickEvent event) {
      if (event.phase == Phase.START) {
         CMItemstackRenderer.incrementTick();
      }
   }

   private void updateAllChunks() {
      if (Minecraft.m_91087_().f_91060_.f_109469_ != null) {
         int length = Minecraft.m_91087_().f_91060_.f_109469_.f_110843_.length;

         for (int i = 0; i < length; i++) {
            Minecraft.m_91087_().f_91060_.f_109469_.f_110843_[i].f_112792_ = true;
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onRenderWorldLastEvent(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_SKY && !CMConfig.shadersCompat) {
         ItemStack itemstack = Minecraft.m_91087_().f_91074_.m_150109_().m_36052_(3);
         if (itemstack.m_150930_((Item)ModItems.IGNITIUM_HELMET.get())) {
            if (!this.previousLavaVision) {
               this.previousFluidRenderer = Minecraft.m_91087_().m_91289_().f_110901_;
               Minecraft.m_91087_().m_91289_().f_110901_ = new LavaVisionFluidRenderer();
               this.updateAllChunks();
            }
         } else if (this.previousLavaVision) {
            if (this.previousFluidRenderer != null) {
               Minecraft.m_91087_().m_91289_().f_110901_ = this.previousFluidRenderer;
            }

            this.updateAllChunks();
         }

         this.previousLavaVision = itemstack.m_150930_((Item)ModItems.IGNITIUM_HELMET.get());
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onGetFluidRenderType(EventGetFluidRenderType event) {
      if (Minecraft.m_91087_().f_91074_.m_150109_().m_36052_(3).m_150930_((Item)ModItems.IGNITIUM_HELMET.get())
         && (event.getFluidState().m_192917_(Fluids.f_76195_) || event.getFluidState().m_192917_(Fluids.f_76194_))) {
         event.setRenderType(RenderType.m_110466_());
         event.setResult(Result.ALLOW);
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onPoseHand(EventPosePlayerHand event) {
      LivingEntity player = (LivingEntity)event.getEntityIn();
      if (player.m_21120_(InteractionHand.OFF_HAND).m_150930_((Item)ModItems.THE_ANNIHILATOR.get())
         && player.m_21120_(InteractionHand.MAIN_HAND).m_150930_((Item)ModItems.THE_ANNIHILATOR.get())
         && player.m_6117_()) {
         if (player.m_5737_() == HumanoidArm.LEFT) {
            event.getModel().f_102811_.f_104203_ = event.getModel().f_102811_.f_104203_ * 0.5F - (float) Math.PI;
            event.getModel().f_102811_.f_104204_ = 0.0F;
         } else {
            event.getModel().f_102812_.f_104203_ = event.getModel().f_102812_.f_104203_ * 0.5F - (float) Math.PI;
            event.getModel().f_102812_.f_104204_ = 0.0F;
         }
      }

      if (player.m_21120_(InteractionHand.OFF_HAND).m_150930_((Item)ModItems.THE_IMMOLATOR.get())
         && player.m_21120_(InteractionHand.MAIN_HAND).m_150930_((Item)ModItems.THE_IMMOLATOR.get())
         && player.m_6117_()) {
         if (player.m_5737_() == HumanoidArm.LEFT) {
            event.getModel().f_102811_.f_104203_ = event.getModel().f_102811_.f_104203_ * 0.5F - (float) Math.PI;
            event.getModel().f_102811_.f_104204_ = 0.0F;
         } else {
            event.getModel().f_102812_.f_104203_ = event.getModel().f_102812_.f_104203_ * 0.5F - (float) Math.PI;
            event.getModel().f_102812_.f_104204_ = 0.0F;
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onRenderArm(RenderArmEvent event) {
      InteractionHand hand = event.getArm() == event.getPlayer().m_5737_() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
      CuriosApi.getCuriosHelper()
         .getCuriosHandler(event.getPlayer())
         .ifPresent(
            handler -> {
               ICurioStacksHandler stacksHandler = (ICurioStacksHandler)handler.getCurios().get(SlotTypePreset.HANDS.getIdentifier());
               if (stacksHandler != null) {
                  IDynamicStackHandler stacks = stacksHandler.getStacks();
                  IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();

                  for (int slot = hand == InteractionHand.MAIN_HAND ? 0 : 1; slot < stacks.getSlots(); slot += 2) {
                     ItemStack stack = cosmeticStacks.getStackInSlot(slot);
                     if (stack.m_41619_() && (Boolean)stacksHandler.getRenders().get(slot)) {
                        stack = stacks.getStackInSlot(slot);
                     }

                     Blazing_Grips_Renderer gripsrenderer = Blazing_Grips_Renderer.getGloveRenderer(stack);
                     if (gripsrenderer != null) {
                        gripsrenderer.renderFirstPersonArm(
                           event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack.m_41790_()
                        );
                     }

                     RendererSticky_Gloves stickyrenderer = RendererSticky_Gloves.getGloveRenderer(stack);
                     if (stickyrenderer != null) {
                        stickyrenderer.renderFirstPersonArm(
                           event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack.m_41790_()
                        );
                     }
                  }
               }
            }
         );
   }

   private void CustomHealth(Pre event, int back) {
      Player player = Minecraft.m_91087_().f_91074_;
      Minecraft mc = Minecraft.m_91087_();
      ForgeGui gui = (ForgeGui)mc.f_91065_;
      PoseStack stack = event.getPoseStack();
      gui.setupOverlayRenderState(true, false);
      int width = event.getWindow().m_85445_();
      int height = event.getWindow().m_85446_();
      event.setCanceled(true);
      RenderSystem.m_157456_(0, EFFECT_HEART);
      RenderSystem.m_69478_();
      int health = Mth.m_14167_(player.m_21223_());
      int tickCount = gui.m_93079_();
      boolean highlight = this.healthBlinkTime > (long)tickCount && (this.healthBlinkTime - (long)tickCount) / 3L % 2L == 1L;
      if (health < this.lastHealth && player.f_19802_ > 0) {
         this.lastHealthTime = Util.m_137550_();
         this.healthBlinkTime = (long)(tickCount + 20);
      } else if (health > this.lastHealth && player.f_19802_ > 0) {
         this.lastHealthTime = Util.m_137550_();
         this.healthBlinkTime = (long)(tickCount + 10);
      }

      if (Util.m_137550_() - this.lastHealthTime > 1000L) {
         this.lastHealth = health;
         this.displayHealth = health;
         this.lastHealthTime = Util.m_137550_();
      }

      this.lastHealth = health;
      int healthLast = this.displayHealth;
      AttributeInstance maxHealth = player.m_21051_(Attributes.f_22276_);
      float healthMax = (float)maxHealth.m_22135_();
      int absorbtion = Mth.m_14167_(player.m_6103_());
      int healthRows = Mth.m_14167_((healthMax + (float)absorbtion) / 2.0F / 10.0F);
      int rowHeight = Math.max(10 - (healthRows - 2), 3);
      this.random.setSeed((long)tickCount * 312871L);
      int left = width / 2 - 91;
      int top = height - gui.leftHeight;
      gui.leftHeight += healthRows * rowHeight;
      if (rowHeight != 10) {
         gui.leftHeight += 10 - rowHeight;
      }

      int regen = -1;
      if (player.m_21023_(MobEffects.f_19605_)) {
         regen = tickCount % Mth.m_14167_(healthMax + 5.0F);
      }

      int TOP = player.f_19853_.m_6106_().m_5466_() ? 9 : 0;
      int BACKGROUND = highlight ? back : 16;
      int margin = 34;
      float absorbtionRemaining = (float)absorbtion;

      for (int i = Mth.m_14167_((healthMax + (float)absorbtion) / 2.0F) - 1; i >= 0; i--) {
         int row = Mth.m_14167_((float)(i + 1) / 10.0F) - 1;
         int x = left + i % 10 * 8;
         int y = top - row * rowHeight;
         if (health <= 4) {
            y += this.random.nextInt(2);
         }

         if (i == regen) {
            y -= 2;
         }

         RenderSystem.m_157456_(0, EFFECT_HEART);
         gui.m_93228_(stack, x, y, BACKGROUND, TOP, 9, 9);
         if (highlight) {
            if (i * 2 + 1 < healthLast) {
               gui.m_93228_(stack, x, y, margin, TOP, 9, 9);
            } else if (i * 2 + 1 == healthLast) {
               gui.m_93228_(stack, x, y, margin + 9, TOP, 9, 9);
            }
         }

         if (absorbtionRemaining > 0.0F) {
            if (absorbtionRemaining == (float)absorbtion && (float)absorbtion % 2.0F == 1.0F) {
               gui.m_93228_(stack, x, y, margin + 9, TOP, 9, 9);
               absorbtionRemaining--;
            } else {
               gui.m_93228_(stack, x, y, margin, TOP, 9, 9);
               absorbtionRemaining -= 2.0F;
            }
         } else if (i * 2 + 1 < health) {
            gui.m_93228_(stack, x, y, margin, TOP, 9, 9);
         } else if (i * 2 + 1 == health) {
            gui.m_93228_(stack, x, y, margin + 9, TOP, 9, 9);
         }
      }

      RenderSystem.m_69461_();
      RenderSystem.m_157456_(0, EFFECT_HEART);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void renderBossOverlay(BossEventProgress event) {
      if (CMConfig.custombossbar && ClientProxy.bossBarRenderTypes.containsKey(event.getBossEvent().m_18860_())) {
         int renderTypeFor = ClientProxy.bossBarRenderTypes.get(event.getBossEvent().m_18860_());
         CustomBossBar customBossBar = CustomBossBar.customBossBars.getOrDefault(renderTypeFor, null);
         if (customBossBar == null) {
            return;
         }

         event.setCanceled(true);
         customBossBar.renderBossBar(event);
      }
   }
}
