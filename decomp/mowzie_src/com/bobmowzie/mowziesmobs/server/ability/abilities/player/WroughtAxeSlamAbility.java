package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityAxeAttack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class WroughtAxeSlamAbility extends PlayerAbility {
   private EntityAxeAttack axeAttack;

   public WroughtAxeSlamAbility(AbilityType<Player, WroughtAxeSlamAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, EntityAxeAttack.SWING_DURATION_VER / 2 - 2),
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, EntityAxeAttack.SWING_DURATION_VER / 2 + 2)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      if (!this.getUser().f_19853_.m_5776_()) {
         EntityAxeAttack axeAttack = new EntityAxeAttack(
            (EntityType<? extends EntityAxeAttack>)EntityHandler.AXE_ATTACK.get(), this.getUser().f_19853_, this.getUser(), true
         );
         axeAttack.m_19890_(
            this.getUser().m_20185_(), this.getUser().m_20186_(), this.getUser().m_20189_(), this.getUser().m_146908_(), this.getUser().m_146909_()
         );
         this.getUser().f_19853_.m_7967_(axeAttack);
         this.axeAttack = axeAttack;
      } else {
         this.playAnimation("axe_swing_vertical", false);
         this.heldItemMainHandVisualOverride = this.getUser().m_21205_();
      }
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getTicksInUse() == EntityAxeAttack.SWING_DURATION_VER && this.getUser() instanceof Player) {
         Player player = this.getUser();
         player.m_36334_();
      }
   }

   @Override
   public void end() {
      super.end();
      if (this.axeAttack != null) {
         this.axeAttack.m_146870_();
      }
   }

   @Override
   public boolean preventsAttacking() {
      return false;
   }
}
