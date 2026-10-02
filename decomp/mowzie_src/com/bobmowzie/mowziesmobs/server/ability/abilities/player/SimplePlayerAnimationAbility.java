package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

public class SimplePlayerAnimationAbility extends PlayerAbility {
   private String animationName;
   private boolean separateLeftAndRight;
   private boolean lockHeldItemMainHand;

   public SimplePlayerAnimationAbility(AbilityType<Player, SimplePlayerAnimationAbility> abilityType, Player user, String animationName, int duration) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, duration)
         }
      );
      this.animationName = animationName;
   }

   public SimplePlayerAnimationAbility(
      AbilityType<Player, SimplePlayerAnimationAbility> abilityType,
      Player user,
      String animationName,
      int duration,
      boolean separateLeftAndRight,
      boolean lockHeldItemMainHand
   ) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, duration)
         }
      );
      this.animationName = animationName;
      this.separateLeftAndRight = separateLeftAndRight;
      this.lockHeldItemMainHand = lockHeldItemMainHand;
   }

   @Override
   public void start() {
      super.start();
      if (this.separateLeftAndRight) {
         boolean handSide = this.getUser().m_5737_() == HumanoidArm.RIGHT;
         this.playAnimation(this.animationName + "_" + (handSide ? "right" : "left"), GeckoPlayer.Perspective.THIRD_PERSON, false);
         this.playAnimation(this.animationName, GeckoPlayer.Perspective.FIRST_PERSON, false);
      } else {
         this.playAnimation(this.animationName, false);
      }

      if (this.lockHeldItemMainHand) {
         this.heldItemMainHandVisualOverride = this.getUser().m_21205_();
      }
   }
}
