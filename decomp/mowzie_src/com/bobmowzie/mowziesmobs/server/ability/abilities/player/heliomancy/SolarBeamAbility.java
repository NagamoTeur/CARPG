package com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SolarBeamAbility extends PlayerAbility {
   protected EntitySolarBeam solarBeam;

   public SolarBeamAbility(AbilityType<Player, SolarBeamAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 20),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 55),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 20)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      LivingEntity user = this.getUser();
      if (!this.getUser().f_19853_.m_5776_()) {
         EntitySolarBeam solarBeam = new EntitySolarBeam(
            (EntityType<? extends EntitySolarBeam>)EntityHandler.SOLAR_BEAM.get(),
            user.f_19853_,
            user,
            user.m_20185_(),
            user.m_20186_() + 1.2F,
            user.m_20189_(),
            (float)((double)(user.f_20885_ + 90.0F) * Math.PI / 180.0),
            (float)((double)(-user.m_146909_()) * Math.PI / 180.0),
            55
         );
         solarBeam.setHasPlayer(true);
         user.f_19853_.m_7967_(solarBeam);
         user.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 80, 2, false, false));
         this.solarBeam = solarBeam;
      } else {
         this.heldItemMainHandVisualOverride = ItemStack.f_41583_;
         this.heldItemOffHandVisualOverride = ItemStack.f_41583_;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
      }

      this.playAnimation("solar_beam_charge", false);
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getLevel().m_5776_()) {
         MobEffectInstance sunsBlessingInstance = this.getUser().m_21124_((MobEffect)EffectHandler.SUNS_BLESSING.get());
         if (sunsBlessingInstance != null) {
            int duration = sunsBlessingInstance.m_19557_();
            this.getUser().m_21195_((MobEffect)EffectHandler.SUNS_BLESSING.get());
            int solarBeamCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.solarBeamCost.get() * 60 * 20;
            if (duration - solarBeamCost > 0) {
               this.getUser().m_7292_(new MobEffectInstance((MobEffect)EffectHandler.SUNS_BLESSING.get(), duration - solarBeamCost, 0, false, false));
            }
         }
      }
   }

   @Override
   public void end() {
      super.end();
      if (this.solarBeam != null) {
         this.solarBeam.m_146870_();
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() instanceof Player && !this.getUser().m_150109_().m_36056_().m_41619_()
         ? false
         : this.getUser().m_21023_((MobEffect)EffectHandler.SUNS_BLESSING.get()) && super.canUse();
   }
}
