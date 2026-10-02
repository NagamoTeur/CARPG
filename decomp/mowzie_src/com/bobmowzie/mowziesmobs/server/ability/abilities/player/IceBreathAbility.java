package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBreath;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class IceBreathAbility extends PlayerAbility {
   protected EntityIceBreath iceBreath;

   public IceBreathAbility(AbilityType<Player, IceBreathAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
            new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 6)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      LivingEntity user = this.getUser();
      if (!this.getUser().f_19853_.m_5776_()) {
         EntityIceBreath iceBreath = new EntityIceBreath((EntityType<? extends EntityIceBreath>)EntityHandler.ICE_BREATH.get(), user.f_19853_, user);
         iceBreath.m_19890_(user.m_20185_(), user.m_20186_() + (double)user.m_20192_() - 0.5, user.m_20189_(), user.m_146908_(), user.m_146909_());
         user.f_19853_.m_7967_(iceBreath);
         this.iceBreath = iceBreath;
      }

      this.playAnimation("ice_breath_start", false);
      if (this.getUser().m_7655_() == InteractionHand.MAIN_HAND) {
         this.heldItemMainHandVisualOverride = this.getUser().m_21205_();
         this.heldItemOffHandVisualOverride = ItemStack.f_41583_;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.DONT_RENDER;
      } else {
         this.heldItemOffHandVisualOverride = this.getUser().m_21206_();
         this.heldItemMainHandVisualOverride = ItemStack.f_41583_;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.DONT_RENDER;
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getCurrentSection().sectionType != AbilitySection.AbilitySectionType.RECOVERY && !this.checkIceCrystal()) {
         this.jumpToSection(2);
      }
   }

   @Override
   public void end() {
      super.end();
      if (this.iceBreath != null) {
         this.iceBreath.m_146870_();
      }
   }

   private boolean checkIceCrystal() {
      ItemStack stack = this.getUser().m_21211_();
      if (this.getTicksInUse() <= 1) {
         return true;
      } else {
         return stack.m_41720_() != ItemHandler.ICE_CRYSTAL
            ? false
            : stack.m_41773_() + 5 < stack.m_41776_() || (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get();
      }
   }

   @Override
   public boolean canUse() {
      return this.checkIceCrystal() && super.canUse();
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.playAnimation("ice_breath_start", false);
      } else if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.playAnimation("ice_breath_loop", true);
      } else if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
         this.playAnimation("ice_breath_end", false);
      }
   }
}
