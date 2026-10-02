package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;

public class GroundSlamAbility extends PlayerAbility {
   public GroundSlamAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 2),
            new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 21)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      this.playAnimation("ground_pound_loop", true);
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.getUser().m_20334_(0.0, -1.5, 0.0);
         if (this.getUser().m_20096_()) {
            this.nextSection();

            for (LivingEntity livingentity : this.getUser().f_19853_.m_45976_(LivingEntity.class, this.getUser().m_20191_().m_82377_(5.2, 2.0, 5.2))) {
               livingentity.m_6469_(DamageSource.m_19370_(this.getUser()), 10.0F);
            }

            EntityCameraShake.cameraShake(this.getUser().f_19853_, this.getUser().m_20182_(), 45.0F, 0.09F, 20, 20);
            BlockState blockBeneath = this.getUser().f_19853_.m_8055_(this.getUser().m_20183_());
            if (this.getUser().f_19853_.f_46443_) {
               this.getUser().m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F);

               for (int i = 0; i < 50; i++) {
                  this.getUser()
                     .f_19853_
                     .m_7106_(
                        new BlockParticleOption(ParticleTypes.f_123794_, blockBeneath),
                        this.getUser().m_20208_(5.8),
                        (double)((float)this.getUser().m_146904_() + 0.1F),
                        this.getUser().m_20262_(5.8),
                        0.0,
                        0.38,
                        0.0
                     );
                  this.getUser()
                     .f_19853_
                     .m_7106_(ParticleTypes.f_123759_, this.getUser().m_20208_(5.0), this.getUser().m_20186_(), this.getUser().m_20262_(5.0), 0.0, 0.08, 0.0);
               }

               AdvancedParticleBase.spawnParticle(
                  this.getUser().f_19853_,
                  (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
                  (double)((float)this.getUser().m_20185_()),
                  (double)((float)this.getUser().m_20186_() + 0.01F),
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
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.8F, 0.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(0.0F, 136.0F), false
                     )
                  }
               );
            }
         }
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
         this.getUser().m_20334_(0.0, 0.0, 0.0);
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() instanceof Player && !this.getUser().m_150109_().m_36056_().m_41619_()
         ? false
         : EffectGeomancy.canUse(this.getUser()) && this.getUser().f_19789_ > 2.0F && super.canUse();
   }

   @Override
   public void nextSection() {
      super.nextSection();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
         this.playAnimation("ground_pound_land", false);
      }
   }

   @Override
   public void onRightClickEmpty(RightClickEmpty event) {
      super.onRightClickEmpty(event);
      if (!this.getUser().m_20096_() && this.getUser().m_6047_()) {
         AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.GROUND_SLAM_ABILITY);
      }
   }
}
