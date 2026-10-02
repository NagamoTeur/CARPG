package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBlockSwapper;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.base.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class TunnelingAbility extends PlayerAbility {
   private int doubleTapTimer = 0;
   public boolean prevUnderground;
   public BlockState justDug = Blocks.f_50493_.m_49966_();
   boolean underground = false;
   private float spinAmount = 0.0F;
   private float pitch = 0.0F;
   private int timeUnderground = 0;
   private int timeAboveGround = 0;
   private InteractionHand whichHand;
   private ItemStack gauntletStack;

   public TunnelingAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(abilityType, user, new AbilitySection[]{new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE)});
   }

   @Override
   public void tickNotUsing() {
      super.tickNotUsing();
      if (this.doubleTapTimer > 0) {
         this.doubleTapTimer--;
      }
   }

   public void playGauntletAnimation() {
      if (this.getUser() != null && this.gauntletStack != null && this.gauntletStack.m_41720_() == ItemHandler.EARTHBORE_GAUNTLET) {
         Player player = this.getUser();
         ItemHandler.EARTHBORE_GAUNTLET.playAnimation(player, this.gauntletStack, 1);
      }
   }

   public void stopGauntletAnimation() {
      if (this.getUser() != null && this.gauntletStack != null && this.gauntletStack.m_41720_() == ItemHandler.EARTHBORE_GAUNTLET) {
         Player player = this.getUser();
         ItemHandler.EARTHBORE_GAUNTLET.playAnimation(player, this.gauntletStack, 0);
      }
   }

   @Override
   public void start() {
      super.start();
      this.underground = false;
      this.prevUnderground = false;
      if (this.getUser().m_20096_()) {
         this.getUser().m_5997_(0.0, 0.8F, 0.0);
      }

      this.whichHand = this.getUser().m_7655_();
      this.gauntletStack = this.getUser().m_21211_();
      if (this.getUser().f_19853_.m_5776_()) {
         this.spinAmount = 0.0F;
         this.pitch = 0.0F;
      }
   }

   public boolean damageGauntlet() {
      ItemStack stack = this.getUser().m_21211_();
      if (stack.m_41720_() == ItemHandler.EARTHBORE_GAUNTLET) {
         InteractionHand handIn = this.getUser().m_7655_();
         if (stack.m_41773_() + 5 < stack.m_41776_()) {
            stack.m_41622_(5, this.getUser(), p -> p.m_21190_(handIn));
            return true;
         } else {
            if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.breakable.get()) {
               stack.m_41622_(5, this.getUser(), p -> p.m_21190_(handIn));
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public void restoreGauntlet(ItemStack stack) {
      if (stack.m_41720_() == ItemHandler.EARTHBORE_GAUNTLET && !(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.breakable.get()) {
         stack.m_41721_(Math.max(stack.m_41773_() - 1, 0));
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.isUsing() && this.getUser() instanceof Player) {
         Player player = this.getUser();

         for (ItemStack stack : player.m_150109_().f_35974_) {
            this.restoreGauntlet(stack);
         }

         for (ItemStack stack : player.m_150109_().f_35976_) {
            this.restoreGauntlet(stack);
         }
      }
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      this.getUser().f_19789_ = 0.0F;
      if (this.getUser() instanceof Player) {
         this.getUser().m_150110_().f_35935_ = false;
      }

      this.underground = !this.getUser().f_19853_.m_45976_(EntityBlockSwapper.class, this.getUser().m_20191_().m_82400_(0.3)).isEmpty();
      Vec3 lookVec = this.getUser().m_20154_();
      float tunnelSpeed = 0.3F;
      ItemStack stack = this.getUser().m_21211_();
      boolean usingGauntlet = stack.m_41720_() == ItemHandler.EARTHBORE_GAUNTLET;
      if (this.underground) {
         this.timeUnderground++;
         if (usingGauntlet && this.damageGauntlet()) {
            this.getUser().m_20256_(lookVec.m_82541_().m_82490_((double)tunnelSpeed));
         } else {
            this.getUser().m_20256_(lookVec.m_82542_(0.3, 0.0, 0.3).m_82520_(0.0, 1.0, 0.0).m_82541_().m_82490_((double)tunnelSpeed));
         }

         for (LivingEntity entityHit : this.getEntityLivingBaseNearby(this.getUser(), 2.0, 2.0, 2.0, 2.0)) {
            DamageSource damageSource = DamageSource.m_19370_(this.getUser());
            if (this.getUser() instanceof Player) {
               damageSource = DamageSource.m_19344_(this.getUser());
            }

            entityHit.m_6469_(damageSource, 6.0F * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.geomancyAttackMultiplier.get()).floatValue());
         }
      } else {
         this.timeAboveGround++;
         this.getUser().m_20256_(this.getUser().m_20184_().m_82492_(0.0, 0.07, 0.0));
         if (this.getUser().m_20184_().m_7098_() < -1.3) {
            this.getUser().m_20334_(this.getUser().m_20184_().m_7096_(), -1.3, this.getUser().m_20184_().m_7094_());
         }
      }

      if (this.underground && (this.prevUnderground || lookVec.f_82480_ < 0.0) && this.timeAboveGround > 5
         || this.getTicksInUse() > 1 && usingGauntlet && lookVec.f_82480_ < 0.0 && stack.m_41773_() + 5 < stack.m_41776_()) {
         if (this.getUser().f_19797_ % 16 == 0) {
            this.getUser()
               .m_5496_((SoundEvent)((Supplier)MMSounds.EFFECT_GEOMANCY_RUMBLE.get(this.rand.nextInt(3))).get(), 0.6F, 0.5F + this.rand.nextFloat() * 0.2F);
         }

         Vec3 userCenter = this.getUser().m_20182_().m_82520_(0.0, (double)(this.getUser().m_20206_() / 2.0F), 0.0);
         float radius = 2.0F;
         AABB aabb = new AABB((double)(-radius), (double)(-radius), (double)(-radius), (double)radius, (double)radius, (double)radius);
         aabb = aabb.m_82383_(userCenter);

         for (int i = 0; (double)i < this.getUser().m_20184_().m_82553_() * 4.0; i++) {
            for (int x = (int)Math.floor(aabb.f_82288_); (double)x <= Math.floor(aabb.f_82291_); x++) {
               for (int y = (int)Math.floor(aabb.f_82289_); (double)y <= Math.floor(aabb.f_82292_); y++) {
                  for (int z = (int)Math.floor(aabb.f_82290_); (double)z <= Math.floor(aabb.f_82293_); z++) {
                     Vec3 posVec = new Vec3((double)x, (double)y, (double)z);
                     if (!(posVec.m_82520_(0.5, 0.5, 0.5).m_82546_(userCenter).m_82556_() > (double)(radius * radius))) {
                        Vec3 motionScaled = this.getUser().m_20184_().m_82541_().m_82490_((double)i);
                        posVec = posVec.m_82549_(motionScaled);
                        BlockPos pos = new BlockPos(posVec);
                        BlockState blockState = this.getUser().f_19853_.m_8055_(pos);
                        if (EffectGeomancy.isBlockDiggable(blockState) && blockState.m_60734_() != Blocks.f_50752_) {
                           this.justDug = blockState;
                           EntityBlockSwapper.swapBlock(this.getUser().f_19853_, pos, Blocks.f_50016_.m_49966_(), 15, false, false);
                        }
                     }
                  }
               }
            }
         }
      }

      if (!this.prevUnderground && this.underground) {
         this.timeUnderground = 0;
         this.getUser()
            .m_5496_((SoundEvent)((Supplier)MMSounds.EFFECT_GEOMANCY_BREAK_MEDIUM.get(this.rand.nextInt(3))).get(), 1.0F, 0.9F + this.rand.nextFloat() * 0.1F);
         if (this.getUser().f_19853_.f_46443_) {
            AdvancedParticleBase.spawnParticle(
               this.getUser().f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
               (double)((float)this.getUser().m_20185_()),
               (double)((float)this.getUser().m_20186_() + 0.02F),
               (double)((float)this.getUser().m_20189_()),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               10.0,
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(10.0F, 30.0F), false
                  )
               }
            );
         }

         this.playGauntletAnimation();
      }

      if (this.prevUnderground && !this.underground) {
         this.timeAboveGround = 0;
         this.getUser().m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK.get(), 1.0F, 0.9F + this.rand.nextFloat() * 0.1F);
         if (this.getUser().f_19853_.f_46443_) {
            AdvancedParticleBase.spawnParticle(
               this.getUser().f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
               (double)((float)this.getUser().m_20185_()),
               (double)((float)this.getUser().m_20186_() + 0.02F),
               (double)((float)this.getUser().m_20189_()),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               10.0,
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(10.0F, 30.0F), false
                  )
               }
            );
         }

         if (this.timeUnderground > 10) {
            this.getUser().m_20256_(this.getUser().m_20184_().m_82490_(10.0));
         } else {
            this.getUser().m_20256_(this.getUser().m_20184_().m_82542_(3.0, 7.0, 3.0));
         }

         for (int i = 0; i < 6; i++) {
            if (this.justDug == null) {
               this.justDug = Blocks.f_50493_.m_49966_();
            }

            EntityFallingBlock fallingBlock = new EntityFallingBlock(
               (EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.getUser().f_19853_, 80, this.justDug
            );
            fallingBlock.m_6034_(this.getUser().m_20185_(), this.getUser().m_20186_() + 1.0, this.getUser().m_20189_());
            fallingBlock.m_20334_(
               (double)(this.getUser().m_217043_().m_188501_() * 0.8F - 0.4F),
               (double)(0.4F + this.getUser().m_217043_().m_188501_() * 0.8F),
               (double)(this.getUser().m_217043_().m_188501_() * 0.8F - 0.4F)
            );
            this.getUser().f_19853_.m_7967_(fallingBlock);
         }

         this.stopGauntletAnimation();
      }

      this.prevUnderground = this.underground;
   }

   @Override
   public void end() {
      super.end();
      this.stopGauntletAnimation();
   }

   @Override
   public boolean canUse() {
      return super.canUse();
   }

   @Override
   protected boolean canContinueUsing() {
      ItemStack stack = this.getUser().m_21211_();
      boolean usingGauntlet = stack.m_41720_() == ItemHandler.EARTHBORE_GAUNTLET;
      return this.whichHand == null
         ? false
         : (this.getTicksInUse() <= 1 || !this.getUser().m_20096_() && (!this.getUser().m_20069_() || usingGauntlet) || this.underground)
            && this.getUser().m_21120_(this.whichHand).m_41720_() == ItemHandler.EARTHBORE_GAUNTLET
            && super.canContinueUsing();
   }

   @Override
   public boolean preventsItemUse(ItemStack stack) {
      return stack.m_41720_() == ItemHandler.EARTHBORE_GAUNTLET ? false : super.preventsItemUse(stack);
   }

   @Override
   public <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> e, GeckoPlayer.Perspective perspective) {
      e.getController().transitionLengthTicks = 4.0;
      if (perspective == GeckoPlayer.Perspective.THIRD_PERSON) {
         float yMotionThreshold = this.getUser() == Minecraft.m_91087_().f_91074_ ? 1.0F : 2.0F;
         if (!this.underground
            && this.getUser().m_21211_().m_41720_() != ItemHandler.EARTHBORE_GAUNTLET
            && this.getUser().m_20184_().m_7098_() < (double)yMotionThreshold) {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("tunneling_fall", false));
         } else {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("tunneling_drill", true));
         }
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void codeAnimations(MowzieAnimatedGeoModel<? extends IAnimatable> model, float partialTick) {
      super.codeAnimations(model, partialTick);
      float faceMotionController = 1.0F - model.getControllerValueInverted("FaceVelocityController");
      Vec3 moveVec = this.getUser().m_20184_().m_82541_();
      this.pitch = (float)Mth.m_14139_(0.3 * (double)partialTick, (double)this.pitch, moveVec.m_7098_());
      MowzieGeoBone com = model.getMowzieBone("CenterOfMass");
      com.setRotationX((float)((-Math.PI / 2) + (Math.PI / 2) * (double)this.pitch) * faceMotionController);
      float spinSpeed = 0.35F;
      if (faceMotionController < 1.0F && (double)this.spinAmount < 6.2731853071795864 && (double)this.spinAmount > 0.01) {
         float f = (float)(((Math.PI * 2) - (double)this.spinAmount) / (Math.PI * 2));
         f = (float)Math.pow((double)f, 0.5);
         this.spinAmount += partialTick * spinSpeed * f;
         if ((double)this.spinAmount > Math.PI * 2) {
            this.spinAmount = 0.0F;
         }
      } else {
         this.spinAmount += faceMotionController * partialTick * spinSpeed;
         this.spinAmount = (float)((double)this.spinAmount % (Math.PI * 2));
      }

      MowzieGeoBone waist = model.getMowzieBone("Waist");
      waist.addRotationY(-this.spinAmount);
   }

   @Override
   public CompoundTag writeNBT() {
      CompoundTag compound = super.writeNBT();
      if (this.isUsing() && this.whichHand != null) {
         compound.m_128405_("whichHand", this.whichHand.ordinal());
      }

      return compound;
   }

   @Override
   public void readNBT(Tag nbt) {
      super.readNBT(nbt);
      if (this.isUsing()) {
         CompoundTag compound = (CompoundTag)nbt;
         this.whichHand = InteractionHand.values()[compound.m_128451_("whichHand")];
      }
   }
}
