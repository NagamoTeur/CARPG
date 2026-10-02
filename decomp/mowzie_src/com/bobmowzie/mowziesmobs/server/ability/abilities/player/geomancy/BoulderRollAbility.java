package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class BoulderRollAbility extends PlayerAbility {
   private static int START_UP = 15;
   float spinAmount = 0.0F;

   public BoulderRollAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(abilityType, user, new AbilitySection[]{new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE)});
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
   }

   @Override
   public <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> e, GeckoPlayer.Perspective perspective) {
      e.getController().transitionLengthTicks = 0.0;
      if (perspective == GeckoPlayer.Perspective.THIRD_PERSON) {
         e.getController().setAnimation(new AnimationBuilder().addAnimation("boulder_roll_loop_still", true));
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.getUser().m_20256_(this.getUser().m_20252_(1.0F).m_82541_().m_82542_(0.3, 0.0, 0.3));
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.getUser().m_20256_(this.getUser().m_20252_(1.0F).m_82541_().m_82542_(1.0, 0.0, 1.0));
      }
   }

   @Override
   public boolean tryAbility() {
      return super.tryAbility();
   }

   @Override
   public void onRightClickEmpty(RightClickEmpty event) {
      super.onRightClickEmpty(event);
      AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.BOULDER_ROLL_ABILITY);
   }

   @Override
   public void onRightMouseUp(Player player) {
      super.onRightMouseUp(player);
      if (this.isUsing()) {
         this.nextSection();
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() instanceof Player && !this.getUser().m_150109_().m_36056_().m_41619_()
         ? false
         : this.getUser().m_21023_((MobEffect)EffectHandler.GEOMANCY.get()) && this.getUser().m_20142_() && super.canUse();
   }

   @Override
   public void codeAnimations(MowzieAnimatedGeoModel<? extends IAnimatable> model, float partialTick) {
      super.codeAnimations(model, partialTick);
      float spinSpeed = 0.35F;
      this.spinAmount += partialTick * spinSpeed;
      MowzieGeoBone centerOfMass = model.getMowzieBone("CenterOfMass");
      centerOfMass.addRotationX(-this.spinAmount);
   }
}
