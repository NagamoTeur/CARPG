package software.bernie.ars_nouveau.geckolib3.model;

import com.mojang.blaze3d.Blaze3D;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatableModel;
import software.bernie.ars_nouveau.geckolib3.core.builder.Animation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.geckolib3.core.processor.AnimationProcessor;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.file.AnimationFile;
import software.bernie.ars_nouveau.geckolib3.geo.exception.GeckoLibException;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.GeoModelProvider;
import software.bernie.ars_nouveau.geckolib3.model.provider.IAnimatableModelProvider;
import software.bernie.ars_nouveau.geckolib3.resource.GeckoLibCache;
import software.bernie.ars_nouveau.geckolib3.util.MolangUtils;

public abstract class AnimatedGeoModel<T extends IAnimatable> extends GeoModelProvider<T> implements IAnimatableModel<T>, IAnimatableModelProvider<T> {
   private final AnimationProcessor animationProcessor = new AnimationProcessor(this);
   private GeoModel currentModel;

   protected AnimatedGeoModel() {
   }

   public void registerBone(GeoBone bone) {
      this.registerModelRenderer(bone);

      for (GeoBone childBone : bone.childBones) {
         this.registerBone(childBone);
      }
   }

   @Deprecated(
      forRemoval = true
   )
   public void setLivingAnimations(T animatable, Integer instanceId, AnimationEvent animationEvent) {
      this.setCustomAnimations(animatable, instanceId, animationEvent);
   }

   public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
      Minecraft mc = Minecraft.m_91087_();
      AnimationData manager = animatable.getFactory().getOrCreateAnimationData(instanceId);
      double currentTick = animatable instanceof Entity livingEntity ? (double)livingEntity.f_19797_ : this.getCurrentTick();
      if (manager.startTick == -1.0) {
         manager.startTick = currentTick + (double)mc.m_91296_();
      }

      if (!mc.m_91104_() || manager.shouldPlayWhilePaused) {
         if (animatable instanceof LivingEntity) {
            manager.tick = currentTick + (double)mc.m_91296_();
            double gameTick = manager.tick;
            double deltaTicks = gameTick - this.lastGameTickTime;
            this.seekTime += deltaTicks;
            this.lastGameTickTime = gameTick;
            this.codeAnimations(animatable, instanceId, animationEvent);
         } else {
            manager.tick = currentTick - manager.startTick;
            double gameTick = manager.tick;
            double deltaTicks = gameTick - this.lastGameTickTime;
            this.seekTime += deltaTicks;
            this.lastGameTickTime = gameTick;
         }
      }

      AnimationEvent<T> predicate = animationEvent == null
         ? new AnimationEvent<>(animatable, 0.0F, 0.0F, (float)(manager.tick - this.lastGameTickTime), false, Collections.emptyList())
         : animationEvent;
      predicate.animationTick = this.seekTime;
      this.getAnimationProcessor().preAnimationSetup(predicate.getAnimatable(), this.seekTime);
      if (!this.getAnimationProcessor().getModelRendererList().isEmpty()) {
         this.getAnimationProcessor()
            .tickAnimation(animatable, instanceId, this.seekTime, predicate, GeckoLibCache.getInstance().parser, this.shouldCrashOnMissing);
      }
   }

   public void codeAnimations(T entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
   }

   @Override
   public AnimationProcessor getAnimationProcessor() {
      return this.animationProcessor;
   }

   public void registerModelRenderer(IBone modelRenderer) {
      this.animationProcessor.registerModelRenderer(modelRenderer);
   }

   @Override
   public Animation getAnimation(String name, IAnimatable animatable) {
      AnimationFile animation = GeckoLibCache.getInstance().getAnimations().get(this.getAnimationResource((T)animatable));
      if (animation == null) {
         throw new GeckoLibException(this.getAnimationResource((T)animatable), "Could not find animation file. Please double check name.");
      } else {
         return animation.getAnimation(name);
      }
   }

   @Override
   public GeoModel getModel(ResourceLocation location) {
      GeoModel model = super.getModel(location);
      if (model == null) {
         throw new GeckoLibException(location, "Could not find model. If you are getting this with a built mod, please just restart your game.");
      } else {
         if (model != this.currentModel) {
            this.animationProcessor.clearModelRendererList();
            this.currentModel = model;

            for (GeoBone bone : model.topLevelBones) {
               this.registerBone(bone);
            }
         }

         return model;
      }
   }

   @Override
   public void setMolangQueries(IAnimatable animatable, double seekTime) {
      MolangParser parser = GeckoLibCache.getInstance().parser;
      Minecraft mc = Minecraft.m_91087_();
      parser.setValue("query.actor_count", mc.f_91073_::m_104813_);
      parser.setValue("query.time_of_day", () -> (double)MolangUtils.normalizeTime(mc.f_91073_.m_46468_()));
      parser.setValue("query.moon_phase", mc.f_91073_::m_46941_);
      if (animatable instanceof Entity entity) {
         parser.setValue("query.distance_from_camera", () -> mc.f_91063_.m_109153_().m_90583_().m_82554_(entity.m_20182_()));
         parser.setValue("query.is_on_ground", () -> (double)MolangUtils.booleanToFloat(entity.m_20096_()));
         parser.setValue("query.is_in_water", () -> (double)MolangUtils.booleanToFloat(entity.m_20069_()));
         parser.setValue("query.is_in_water_or_rain", () -> (double)MolangUtils.booleanToFloat(entity.m_20071_()));
         if (entity instanceof LivingEntity livingEntity) {
            parser.setValue("query.health", livingEntity::m_21223_);
            parser.setValue("query.max_health", livingEntity::m_21233_);
            parser.setValue("query.is_on_fire", () -> (double)MolangUtils.booleanToFloat(livingEntity.m_6060_()));
            parser.setValue("query.ground_speed", () -> {
               Vec3 velocity = livingEntity.m_20184_();
               return (double)Mth.m_14116_((float)(velocity.f_82479_ * velocity.f_82479_ + velocity.f_82481_ * velocity.f_82481_));
            });
            parser.setValue("query.yaw_speed", () -> (double)livingEntity.m_5675_((float)seekTime - livingEntity.m_5675_((float)seekTime - 0.1F)));
         }
      }
   }

   @Override
   public double getCurrentTick() {
      return Blaze3D.m_83640_() * 20.0;
   }
}
