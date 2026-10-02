package software.bernie.ars_nouveau.geckolib3.core.controller;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import software.bernie.ars_nouveau.geckolib3.core.AnimationState;
import software.bernie.ars_nouveau.geckolib3.core.ConstantValue;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatableModel;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.Animation;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.builder.ILoopType;
import software.bernie.ars_nouveau.geckolib3.core.easing.EasingType;
import software.bernie.ars_nouveau.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.ars_nouveau.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.ars_nouveau.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.AnimationPoint;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.BoneAnimation;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.BoneAnimationQueue;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.EventKeyFrame;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.KeyFrame;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.KeyFrameLocation;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.ParticleEventKeyFrame;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.VectorKeyFrameList;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.ars_nouveau.geckolib3.core.util.Axis;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;

public class AnimationController<T extends IAnimatable> {
   static List<AnimationController.ModelFetcher<?>> modelFetchers = new ObjectArrayList();
   protected T animatable;
   protected AnimationController.IAnimationPredicate<T> animationPredicate;
   private final String name;
   protected AnimationState animationState = AnimationState.Stopped;
   public double transitionLengthTicks;
   private AnimationController.ISoundListener<T> soundListener;
   private AnimationController.IParticleListener<T> particleListener;
   private AnimationController.ICustomInstructionListener<T> customInstructionListener;
   public boolean isJustStarting = false;
   private final HashMap<String, BoneAnimationQueue> boneAnimationQueues = new HashMap<>();
   public double tickOffset;
   protected Queue<Animation> animationQueue = new LinkedList<>();
   protected Animation currentAnimation;
   protected AnimationBuilder currentAnimationBuilder = new AnimationBuilder();
   protected boolean shouldResetTick = false;
   private final HashMap<String, BoneSnapshot> boneSnapshots = new HashMap<>();
   private boolean justStopped = false;
   protected boolean justStartedTransition = false;
   public Double2DoubleFunction customEasingMethod;
   protected boolean needsAnimationReload = false;
   public double animationSpeed = 1.0;
   private final Set<EventKeyFrame<?>> executedKeyFrames = new ObjectOpenHashSet();
   public EasingType easingType = EasingType.NONE;

   public static void addModelFetcher(AnimationController.ModelFetcher<?> fetcher) {
      modelFetchers.add(fetcher);
   }

   public static void removeModelFetcher(AnimationController.ModelFetcher<?> fetcher) {
      Objects.requireNonNull(fetcher);
      modelFetchers.remove(fetcher);
   }

   public void setAnimation(AnimationBuilder builder) {
      IAnimatableModel<T> model = this.getModel(this.animatable);
      if (model != null) {
         if (builder != null && builder.getRawAnimationList().size() != 0) {
            if (!builder.getRawAnimationList().equals(this.currentAnimationBuilder.getRawAnimationList()) || this.needsAnimationReload) {
               AtomicBoolean encounteredError = new AtomicBoolean(false);
               LinkedList<Animation> animations = builder.getRawAnimationList().stream().map(rawAnimation -> {
                  Animation animation = model.getAnimation(rawAnimation.animationName, this.animatable);
                  if (animation == null) {
                     System.out.printf("Could not load animation: %s. Is it missing?", rawAnimation.animationName);
                     encounteredError.set(true);
                  }

                  if (animation != null && rawAnimation.loopType != null) {
                     animation.loop = rawAnimation.loopType;
                  }

                  return animation;
               }).collect(Collectors.toCollection(LinkedList::new));
               if (encounteredError.get()) {
                  return;
               }

               this.animationQueue = animations;
               this.currentAnimationBuilder = builder;
               this.shouldResetTick = true;
               this.animationState = AnimationState.Transitioning;
               this.justStartedTransition = true;
               this.needsAnimationReload = false;
            }
         } else {
            this.animationState = AnimationState.Stopped;
         }
      }
   }

   public AnimationController(T animatable, String name, float transitionLengthTicks, AnimationController.IAnimationPredicate<T> animationPredicate) {
      this.animatable = animatable;
      this.name = name;
      this.transitionLengthTicks = (double)transitionLengthTicks;
      this.animationPredicate = animationPredicate;
      this.tickOffset = 0.0;
   }

   public AnimationController(
      T animatable, String name, float transitionLengthTicks, EasingType easingtype, AnimationController.IAnimationPredicate<T> animationPredicate
   ) {
      this.animatable = animatable;
      this.name = name;
      this.transitionLengthTicks = (double)transitionLengthTicks;
      this.easingType = easingtype;
      this.animationPredicate = animationPredicate;
      this.tickOffset = 0.0;
   }

   public AnimationController(
      T animatable,
      String name,
      float transitionLengthTicks,
      Double2DoubleFunction customEasingMethod,
      AnimationController.IAnimationPredicate<T> animationPredicate
   ) {
      this.animatable = animatable;
      this.name = name;
      this.transitionLengthTicks = (double)transitionLengthTicks;
      this.customEasingMethod = customEasingMethod;
      this.easingType = EasingType.CUSTOM;
      this.animationPredicate = animationPredicate;
      this.tickOffset = 0.0;
   }

   public String getName() {
      return this.name;
   }

   public Animation getCurrentAnimation() {
      return this.currentAnimation;
   }

   public AnimationState getAnimationState() {
      return this.animationState;
   }

   public HashMap<String, BoneAnimationQueue> getBoneAnimationQueues() {
      return this.boneAnimationQueues;
   }

   public void registerSoundListener(AnimationController.ISoundListener<T> soundListener) {
      this.soundListener = soundListener;
   }

   public void registerParticleListener(AnimationController.IParticleListener<T> particleListener) {
      this.particleListener = particleListener;
   }

   public void registerCustomInstructionListener(AnimationController.ICustomInstructionListener<T> customInstructionListener) {
      this.customInstructionListener = customInstructionListener;
   }

   public void process(
      double tick,
      AnimationEvent<T> event,
      List<IBone> modelRendererList,
      Map<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection,
      MolangParser parser,
      boolean crashWhenCantFindBone
   ) {
      parser.setValue("query.life_time", () -> tick / 20.0);
      if (this.currentAnimation != null) {
         IAnimatableModel<T> model = this.getModel(this.animatable);
         if (model != null) {
            Animation animation = model.getAnimation(this.currentAnimation.animationName, this.animatable);
            if (animation != null) {
               ILoopType loop = this.currentAnimation.loop;
               this.currentAnimation = animation;
               this.currentAnimation.loop = loop;
            }
         }
      }

      this.createInitialQueues(modelRendererList);
      double adjustedTick = this.adjustTick(tick);
      if (this.animationState == AnimationState.Transitioning && adjustedTick >= this.transitionLengthTicks) {
         this.shouldResetTick = true;
         this.animationState = AnimationState.Running;
         adjustedTick = this.adjustTick(tick);
      }

      assert adjustedTick >= 0.0 : "GeckoLib: Tick was less than zero";

      PlayState playState = this.testAnimationPredicate(event);
      if (playState != PlayState.STOP && (this.currentAnimation != null || this.animationQueue.size() != 0)) {
         if (!this.justStartedTransition || !this.shouldResetTick && !this.justStopped) {
            if (this.currentAnimation == null && this.animationQueue.size() != 0) {
               this.shouldResetTick = true;
               this.animationState = AnimationState.Transitioning;
               this.justStartedTransition = true;
               this.needsAnimationReload = false;
               adjustedTick = this.adjustTick(tick);
            } else if (this.animationState != AnimationState.Transitioning) {
               this.animationState = AnimationState.Running;
            }
         } else {
            this.justStopped = false;
            adjustedTick = this.adjustTick(tick);
         }

         if (this.animationState == AnimationState.Transitioning) {
            if (adjustedTick == 0.0 || this.isJustStarting) {
               this.justStartedTransition = false;
               this.currentAnimation = this.animationQueue.poll();
               this.resetEventKeyFrames();
               this.saveSnapshotsForAnimation(this.currentAnimation, boneSnapshotCollection);
            }

            if (this.currentAnimation != null) {
               this.setAnimTime(parser, 0.0);

               for (BoneAnimation boneAnimation : this.currentAnimation.boneAnimations) {
                  BoneAnimationQueue boneAnimationQueue = this.boneAnimationQueues.get(boneAnimation.boneName);
                  BoneSnapshot boneSnapshot = this.boneSnapshots.get(boneAnimation.boneName);
                  Optional<IBone> first = Optional.empty();

                  for (IBone bone : modelRendererList) {
                     if (bone.getName().equals(boneAnimation.boneName)) {
                        first = Optional.of(bone);
                        break;
                     }
                  }

                  if (first.isEmpty()) {
                     if (crashWhenCantFindBone) {
                        throw new RuntimeException("Could not find bone: " + boneAnimation.boneName);
                     }
                  } else {
                     BoneSnapshot initialSnapshot = first.get().getInitialSnapshot();

                     assert boneSnapshot != null : "Bone snapshot was null";

                     VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames = boneAnimation.rotationKeyFrames;
                     VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames = boneAnimation.positionKeyFrames;
                     VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames = boneAnimation.scaleKeyFrames;
                     if (!rotationKeyFrames.xKeyFrames.isEmpty()) {
                        AnimationPoint xPoint = this.getAnimationPointAtTick(rotationKeyFrames.xKeyFrames, 0.0, true, Axis.X);
                        AnimationPoint yPoint = this.getAnimationPointAtTick(rotationKeyFrames.yKeyFrames, 0.0, true, Axis.Y);
                        AnimationPoint zPoint = this.getAnimationPointAtTick(rotationKeyFrames.zKeyFrames, 0.0, true, Axis.Z);
                        boneAnimationQueue.rotationXQueue()
                           .add(
                              new AnimationPoint(
                                 null,
                                 adjustedTick,
                                 this.transitionLengthTicks,
                                 (double)(boneSnapshot.rotationValueX - initialSnapshot.rotationValueX),
                                 xPoint.animationStartValue
                              )
                           );
                        boneAnimationQueue.rotationYQueue()
                           .add(
                              new AnimationPoint(
                                 null,
                                 adjustedTick,
                                 this.transitionLengthTicks,
                                 (double)(boneSnapshot.rotationValueY - initialSnapshot.rotationValueY),
                                 yPoint.animationStartValue
                              )
                           );
                        boneAnimationQueue.rotationZQueue()
                           .add(
                              new AnimationPoint(
                                 null,
                                 adjustedTick,
                                 this.transitionLengthTicks,
                                 (double)(boneSnapshot.rotationValueZ - initialSnapshot.rotationValueZ),
                                 zPoint.animationStartValue
                              )
                           );
                     }

                     if (!positionKeyFrames.xKeyFrames.isEmpty()) {
                        AnimationPoint xPoint = this.getAnimationPointAtTick(positionKeyFrames.xKeyFrames, 0.0, false, Axis.X);
                        AnimationPoint yPoint = this.getAnimationPointAtTick(positionKeyFrames.yKeyFrames, 0.0, false, Axis.Y);
                        AnimationPoint zPoint = this.getAnimationPointAtTick(positionKeyFrames.zKeyFrames, 0.0, false, Axis.Z);
                        boneAnimationQueue.positionXQueue()
                           .add(
                              new AnimationPoint(
                                 null, adjustedTick, this.transitionLengthTicks, (double)boneSnapshot.positionOffsetX, xPoint.animationStartValue
                              )
                           );
                        boneAnimationQueue.positionYQueue()
                           .add(
                              new AnimationPoint(
                                 null, adjustedTick, this.transitionLengthTicks, (double)boneSnapshot.positionOffsetY, yPoint.animationStartValue
                              )
                           );
                        boneAnimationQueue.positionZQueue()
                           .add(
                              new AnimationPoint(
                                 null, adjustedTick, this.transitionLengthTicks, (double)boneSnapshot.positionOffsetZ, zPoint.animationStartValue
                              )
                           );
                     }

                     if (!scaleKeyFrames.xKeyFrames.isEmpty()) {
                        AnimationPoint xPoint = this.getAnimationPointAtTick(scaleKeyFrames.xKeyFrames, 0.0, false, Axis.X);
                        AnimationPoint yPoint = this.getAnimationPointAtTick(scaleKeyFrames.yKeyFrames, 0.0, false, Axis.Y);
                        AnimationPoint zPoint = this.getAnimationPointAtTick(scaleKeyFrames.zKeyFrames, 0.0, false, Axis.Z);
                        boneAnimationQueue.scaleXQueue()
                           .add(
                              new AnimationPoint(null, adjustedTick, this.transitionLengthTicks, (double)boneSnapshot.scaleValueX, xPoint.animationStartValue)
                           );
                        boneAnimationQueue.scaleYQueue()
                           .add(
                              new AnimationPoint(null, adjustedTick, this.transitionLengthTicks, (double)boneSnapshot.scaleValueY, yPoint.animationStartValue)
                           );
                        boneAnimationQueue.scaleZQueue()
                           .add(
                              new AnimationPoint(null, adjustedTick, this.transitionLengthTicks, (double)boneSnapshot.scaleValueZ, zPoint.animationStartValue)
                           );
                     }
                  }
               }
            }
         } else if (this.getAnimationState() == AnimationState.Running) {
            this.processCurrentAnimation(adjustedTick, tick, parser, crashWhenCantFindBone);
         }
      } else {
         this.animationState = AnimationState.Stopped;
         this.justStopped = true;
      }
   }

   private void setAnimTime(MolangParser parser, double tick) {
      parser.setValue("query.anim_time", () -> tick / 20.0);
   }

   private IAnimatableModel<T> getModel(T animatable) {
      for (AnimationController.ModelFetcher<?> modelFetcher : modelFetchers) {
         IAnimatableModel<T> model = (IAnimatableModel<T>)modelFetcher.apply(animatable);
         if (model != null) {
            return model;
         }
      }

      System.out.printf("Could not find suitable model for animatable of type %s. Did you register a Model Fetcher?%n", animatable.getClass());
      return null;
   }

   protected PlayState testAnimationPredicate(AnimationEvent<T> event) {
      return this.animationPredicate.test(event);
   }

   private void saveSnapshotsForAnimation(Animation animation, Map<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection) {
      for (Pair<IBone, BoneSnapshot> snapshot : boneSnapshotCollection.values()) {
         if (animation != null && animation.boneAnimations != null) {
            for (BoneAnimation boneAnimation : animation.boneAnimations) {
               if (boneAnimation.boneName.equals(((IBone)snapshot.getLeft()).getName())) {
                  this.boneSnapshots.put(boneAnimation.boneName, new BoneSnapshot((BoneSnapshot)snapshot.getRight()));
                  break;
               }
            }
         }
      }
   }

   private void processCurrentAnimation(double tick, double actualTick, MolangParser parser, boolean crashWhenCantFindBone) {
      assert this.currentAnimation != null;

      if (tick >= this.currentAnimation.animationLength) {
         this.resetEventKeyFrames();
         if (!this.currentAnimation.loop.isRepeatingAfterEnd()) {
            Animation peek = this.animationQueue.peek();
            if (peek == null) {
               this.animationState = AnimationState.Stopped;
               return;
            }

            this.animationState = AnimationState.Transitioning;
            this.shouldResetTick = true;
            this.currentAnimation = this.animationQueue.peek();
         } else {
            this.shouldResetTick = true;
            tick = this.adjustTick(actualTick);
         }
      }

      this.setAnimTime(parser, tick);

      for (BoneAnimation boneAnimation : this.currentAnimation.boneAnimations) {
         BoneAnimationQueue boneAnimationQueue = this.boneAnimationQueues.get(boneAnimation.boneName);
         if (boneAnimationQueue == null) {
            if (crashWhenCantFindBone) {
               throw new RuntimeException("Could not find bone: " + boneAnimation.boneName);
            }
         } else {
            VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames = boneAnimation.rotationKeyFrames;
            VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames = boneAnimation.positionKeyFrames;
            VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames = boneAnimation.scaleKeyFrames;
            if (!rotationKeyFrames.xKeyFrames.isEmpty()) {
               boneAnimationQueue.rotationXQueue().add(this.getAnimationPointAtTick(rotationKeyFrames.xKeyFrames, tick, true, Axis.X));
               boneAnimationQueue.rotationYQueue().add(this.getAnimationPointAtTick(rotationKeyFrames.yKeyFrames, tick, true, Axis.Y));
               boneAnimationQueue.rotationZQueue().add(this.getAnimationPointAtTick(rotationKeyFrames.zKeyFrames, tick, true, Axis.Z));
            }

            if (!positionKeyFrames.xKeyFrames.isEmpty()) {
               boneAnimationQueue.positionXQueue().add(this.getAnimationPointAtTick(positionKeyFrames.xKeyFrames, tick, false, Axis.X));
               boneAnimationQueue.positionYQueue().add(this.getAnimationPointAtTick(positionKeyFrames.yKeyFrames, tick, false, Axis.Y));
               boneAnimationQueue.positionZQueue().add(this.getAnimationPointAtTick(positionKeyFrames.zKeyFrames, tick, false, Axis.Z));
            }

            if (!scaleKeyFrames.xKeyFrames.isEmpty()) {
               boneAnimationQueue.scaleXQueue().add(this.getAnimationPointAtTick(scaleKeyFrames.xKeyFrames, tick, false, Axis.X));
               boneAnimationQueue.scaleYQueue().add(this.getAnimationPointAtTick(scaleKeyFrames.yKeyFrames, tick, false, Axis.Y));
               boneAnimationQueue.scaleZQueue().add(this.getAnimationPointAtTick(scaleKeyFrames.zKeyFrames, tick, false, Axis.Z));
            }
         }
      }

      if (this.soundListener != null || this.particleListener != null || this.customInstructionListener != null) {
         for (EventKeyFrame<String> soundKeyFrame : this.currentAnimation.soundKeyFrames) {
            if (!this.executedKeyFrames.contains(soundKeyFrame) && tick >= soundKeyFrame.getStartTick()) {
               SoundKeyframeEvent<T> event = new SoundKeyframeEvent<>(this.animatable, tick, soundKeyFrame.getEventData(), this);
               this.soundListener.playSound(event);
               this.executedKeyFrames.add(soundKeyFrame);
            }
         }

         for (ParticleEventKeyFrame particleEventKeyFrame : this.currentAnimation.particleKeyFrames) {
            if (!this.executedKeyFrames.contains(particleEventKeyFrame) && tick >= particleEventKeyFrame.getStartTick()) {
               ParticleKeyFrameEvent<T> event = new ParticleKeyFrameEvent<>(
                  this.animatable, tick, particleEventKeyFrame.effect, particleEventKeyFrame.locator, particleEventKeyFrame.script, this
               );
               this.particleListener.summonParticle(event);
               this.executedKeyFrames.add(particleEventKeyFrame);
            }
         }

         for (EventKeyFrame<String> customInstructionKeyFrame : this.currentAnimation.customInstructionKeyframes) {
            if (!this.executedKeyFrames.contains(customInstructionKeyFrame) && tick >= customInstructionKeyFrame.getStartTick()) {
               CustomInstructionKeyframeEvent<T> event = new CustomInstructionKeyframeEvent<>(
                  this.animatable, tick, customInstructionKeyFrame.getEventData(), this
               );
               this.customInstructionListener.executeInstruction(event);
               this.executedKeyFrames.add(customInstructionKeyFrame);
            }
         }
      }

      if (this.transitionLengthTicks == 0.0 && this.shouldResetTick && this.animationState == AnimationState.Transitioning) {
         this.currentAnimation = this.animationQueue.poll();
      }
   }

   private void createInitialQueues(List<IBone> modelRendererList) {
      this.boneAnimationQueues.clear();

      for (IBone modelRenderer : modelRendererList) {
         this.boneAnimationQueues.put(modelRenderer.getName(), new BoneAnimationQueue(modelRenderer));
      }
   }

   protected double adjustTick(double tick) {
      if (this.shouldResetTick) {
         if (this.getAnimationState() == AnimationState.Transitioning) {
            this.tickOffset = tick;
         } else if (this.getAnimationState() == AnimationState.Running) {
            this.tickOffset = tick;
         }

         this.shouldResetTick = false;
         return 0.0;
      } else {
         return this.animationSpeed * Math.max(tick - this.tickOffset, 0.0);
      }
   }

   private AnimationPoint getAnimationPointAtTick(List<KeyFrame<IValue>> frames, double tick, boolean isRotation, Axis axis) {
      KeyFrameLocation<KeyFrame<IValue>> location = this.getCurrentKeyFrameLocation(frames, tick);
      KeyFrame<IValue> currentFrame = location.currentFrame;
      double startValue = currentFrame.getStartValue().get();
      double endValue = currentFrame.getEndValue().get();
      if (isRotation) {
         if (!(currentFrame.getStartValue() instanceof ConstantValue)) {
            startValue = Math.toRadians(startValue);
            if (axis == Axis.X || axis == Axis.Y) {
               startValue *= -1.0;
            }
         }

         if (!(currentFrame.getEndValue() instanceof ConstantValue)) {
            endValue = Math.toRadians(endValue);
            if (axis == Axis.X || axis == Axis.Y) {
               endValue *= -1.0;
            }
         }
      }

      return new AnimationPoint(currentFrame, location.currentTick, currentFrame.getLength(), startValue, endValue);
   }

   private KeyFrameLocation<KeyFrame<IValue>> getCurrentKeyFrameLocation(List<KeyFrame<IValue>> frames, double ageInTicks) {
      double totalTimeTracker = 0.0;

      for (KeyFrame<IValue> frame : frames) {
         totalTimeTracker += frame.getLength();
         if (totalTimeTracker > ageInTicks) {
            double tick = ageInTicks - (totalTimeTracker - frame.getLength());
            return new KeyFrameLocation<>(frame, tick);
         }
      }

      return new KeyFrameLocation<>(frames.get(frames.size() - 1), ageInTicks);
   }

   private void resetEventKeyFrames() {
      this.executedKeyFrames.clear();
   }

   public void markNeedsReload() {
      this.needsAnimationReload = true;
   }

   public void clearAnimationCache() {
      this.currentAnimationBuilder = new AnimationBuilder();
   }

   public double getAnimationSpeed() {
      return this.animationSpeed;
   }

   public void setAnimationSpeed(double animationSpeed) {
      this.animationSpeed = animationSpeed;
   }

   @FunctionalInterface
   public interface IAnimationPredicate<P extends IAnimatable> {
      PlayState test(AnimationEvent<P> var1);
   }

   @FunctionalInterface
   public interface ICustomInstructionListener<A extends IAnimatable> {
      void executeInstruction(CustomInstructionKeyframeEvent<A> var1);
   }

   @FunctionalInterface
   public interface IParticleListener<A extends IAnimatable> {
      void summonParticle(ParticleKeyFrameEvent<A> var1);
   }

   @FunctionalInterface
   public interface ISoundListener<A extends IAnimatable> {
      void playSound(SoundKeyframeEvent<A> var1);
   }

   @FunctionalInterface
   public interface ModelFetcher<T> extends Function<IAnimatable, IAnimatableModel<T>> {
   }
}
