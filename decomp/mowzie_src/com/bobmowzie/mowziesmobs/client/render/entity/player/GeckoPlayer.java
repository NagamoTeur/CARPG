package com.bobmowzie.mowziesmobs.client.render.entity.player;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerFirstPerson;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerThirdPerson;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.entity.IAnimationTickable;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

@OnlyIn(Dist.CLIENT)
public abstract class GeckoPlayer implements IAnimatable, IAnimationTickable {
   protected IGeoRenderer<GeckoPlayer> renderer;
   protected MowzieAnimatedGeoModel<GeckoPlayer> model;
   private int tickTimer = 0;
   private Player player;
   private AnimationFactory factory = new AnimationFactory(this);
   public static final String THIRD_PERSON_CONTROLLER_NAME = "thirdPersonAnimation";
   public static final String FIRST_PERSON_CONTROLLER_NAME = "firstPersonAnimation";

   public GeckoPlayer(Player player) {
      this.player = player;
      this.setup(player);
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new MowzieAnimationController<>(this, this.getControllerName(), 0.0F, this::predicate, 0.0));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public Player getPlayer() {
      return this.player;
   }

   @Override
   public void tick() {
      this.tickTimer++;
   }

   @Override
   public int tickTimer() {
      return this.tickTimer;
   }

   public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> e) {
      e.getController().transitionLengthTicks = 0.0;
      Player player = this.getPlayer();
      if (player == null) {
         return PlayState.STOP;
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
         if (abilityCapability == null) {
            return PlayState.STOP;
         } else if (abilityCapability.getActiveAbility() != null) {
            return abilityCapability.animationPredicate(e, this.getPerspective());
         } else {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
            return PlayState.CONTINUE;
         }
      }
   }

   @Nullable
   public static GeckoPlayer getGeckoPlayer(Player player, GeckoPlayer.Perspective perspective) {
      if (perspective == GeckoPlayer.Perspective.FIRST_PERSON) {
         return GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
      } else {
         PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
         return playerCapability != null ? playerCapability.getGeckoPlayer() : null;
      }
   }

   public static MowzieAnimationController<GeckoPlayer> getAnimationController(Player player, GeckoPlayer.Perspective perspective) {
      PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
      if (playerCapability != null) {
         GeckoPlayer geckoPlayer;
         if (perspective == GeckoPlayer.Perspective.FIRST_PERSON) {
            geckoPlayer = GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
         } else {
            geckoPlayer = playerCapability.getGeckoPlayer();
         }

         if (geckoPlayer != null) {
            String name = perspective == GeckoPlayer.Perspective.FIRST_PERSON ? "firstPersonAnimation" : "thirdPersonAnimation";
            return (MowzieAnimationController<GeckoPlayer>)GeckoLibUtil.getControllerForID(geckoPlayer.getFactory(), player.m_20148_().hashCode(), name);
         }
      }

      return null;
   }

   public IGeoRenderer<GeckoPlayer> getPlayerRenderer() {
      return this.renderer;
   }

   public MowzieAnimatedGeoModel<GeckoPlayer> getModel() {
      return this.model;
   }

   public abstract String getControllerName();

   public abstract GeckoPlayer.Perspective getPerspective();

   public abstract void setup(Player var1);

   public static class GeckoPlayerFirstPerson extends GeckoPlayer {
      public GeckoPlayerFirstPerson(Player player) {
         super(player);
      }

      @Override
      public String getControllerName() {
         return "firstPersonAnimation";
      }

      @Override
      public GeckoPlayer.Perspective getPerspective() {
         return GeckoPlayer.Perspective.FIRST_PERSON;
      }

      @Override
      public void setup(Player player) {
         ModelGeckoPlayerFirstPerson modelGeckoPlayer = new ModelGeckoPlayerFirstPerson();
         this.model = modelGeckoPlayer;
         GeckoFirstPersonRenderer geckoRenderer = new GeckoFirstPersonRenderer(Minecraft.m_91087_(), modelGeckoPlayer);
         this.renderer = geckoRenderer;
         if (!geckoRenderer.getModelsToLoad().containsKey(this.getClass())) {
            geckoRenderer.getModelsToLoad().put((Class<? extends GeckoPlayer>)this.getClass(), geckoRenderer);
         }
      }
   }

   public static class GeckoPlayerThirdPerson extends GeckoPlayer {
      public static GeckoRenderPlayer GECKO_RENDERER_THIRD_PERSON_NORMAL;
      public static ModelGeckoPlayerThirdPerson GECKO_MODEL_THIRD_PERSON_NORMAL;
      public static GeckoRenderPlayer GECKO_RENDERER_THIRD_PERSON_SLIM;
      public static ModelGeckoPlayerThirdPerson GECKO_MODEL_THIRD_PERSON_SLIM;

      public GeckoPlayerThirdPerson(Player player) {
         super(player);
      }

      @Override
      public String getControllerName() {
         return "thirdPersonAnimation";
      }

      @Override
      public GeckoPlayer.Perspective getPerspective() {
         return GeckoPlayer.Perspective.THIRD_PERSON;
      }

      @Override
      public void setup(Player player) {
         if (((AbstractClientPlayer)player).m_108564_().equals("slim")) {
            this.model = GECKO_MODEL_THIRD_PERSON_SLIM;
            this.renderer = GECKO_RENDERER_THIRD_PERSON_SLIM;
         } else {
            this.model = GECKO_MODEL_THIRD_PERSON_NORMAL;
            this.renderer = GECKO_RENDERER_THIRD_PERSON_NORMAL;
         }
      }

      public static void initRenderer() {
         GECKO_MODEL_THIRD_PERSON_NORMAL = new ModelGeckoPlayerThirdPerson();
         GECKO_MODEL_THIRD_PERSON_SLIM = new ModelGeckoPlayerThirdPerson();
         GECKO_MODEL_THIRD_PERSON_SLIM.setUseSmallArms(true);
         Minecraft minecraft = Minecraft.m_91087_();
         EntityRenderDispatcher dispatcher = minecraft.m_91290_();
         ItemRenderer itemRenderer = minecraft.m_91291_();
         ResourceManager resourceManager = minecraft.m_91098_();
         EntityModelSet entityModelSet = minecraft.m_167973_();
         Font font = minecraft.f_91062_;
         Context context = new Context(dispatcher, itemRenderer, minecraft.m_91289_(), dispatcher.m_234586_(), resourceManager, entityModelSet, font);
         GeckoRenderPlayer geckoRenderer = new GeckoRenderPlayer(context, false, GECKO_MODEL_THIRD_PERSON_NORMAL);
         if (!geckoRenderer.getModelsToLoad().containsKey(GeckoPlayer.GeckoPlayerThirdPerson.class)) {
            geckoRenderer.getModelsToLoad().put(GeckoPlayer.GeckoPlayerThirdPerson.class, geckoRenderer);
         }

         GECKO_RENDERER_THIRD_PERSON_NORMAL = geckoRenderer;
         GeckoRenderPlayer geckoRendererSlim = new GeckoRenderPlayer(context, true, GECKO_MODEL_THIRD_PERSON_SLIM);
         if (!geckoRendererSlim.getModelsToLoad().containsKey(GeckoPlayer.GeckoPlayerThirdPerson.class)) {
            geckoRendererSlim.getModelsToLoad().put(GeckoPlayer.GeckoPlayerThirdPerson.class, geckoRendererSlim);
         }

         GECKO_RENDERER_THIRD_PERSON_SLIM = geckoRendererSlim;
      }
   }

   public static enum Perspective {
      FIRST_PERSON,
      THIRD_PERSON;
   }
}
