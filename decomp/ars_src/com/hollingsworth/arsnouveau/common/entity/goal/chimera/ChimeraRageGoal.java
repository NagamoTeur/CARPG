package com.hollingsworth.arsnouveau.common.entity.goal.chimera;

import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.common.entity.WildenChimera;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectDelay;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGravity;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLaunch;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPull;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.EnumSet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class ChimeraRageGoal extends Goal {
   WildenChimera chimera;
   boolean finished;
   public int ticks;

   public ChimeraRageGoal(WildenChimera chimera) {
      this.chimera = chimera;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8037_() {
      super.m_8037_();
      this.ticks++;
      if (this.ticks >= 40) {
         this.finished = true;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.chimera.rageTimer = 200;
      this.finished = false;
      this.ticks = 0;
      Networking.sendToNearby(this.chimera.f_19853_, this.chimera, new PacketAnimEntity(this.chimera.m_19879_(), WildenChimera.Animations.HOWL.ordinal()));
      this.chimera.resetCooldowns();
      this.chimera.m_21219_();
      this.chimera.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 100 + 100 * this.chimera.getPhase(), 3));
      this.chimera.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 300 + 300 * this.chimera.getPhase(), this.chimera.getPhase()));
      LivingEntity target = this.chimera.m_5448_();
      if (target != null && !target.m_20096_()) {
         target.m_21195_(MobEffects.f_19591_);
      }

      if (target != null) {
         EntitySpellResolver resolver = new EntitySpellResolver(
            new SpellContext(
               this.chimera.f_19853_,
               new Spell()
                  .add(MethodTouch.INSTANCE)
                  .add(EffectLaunch.INSTANCE)
                  .add(EffectDelay.INSTANCE)
                  .add(EffectPull.INSTANCE)
                  .add(AugmentAmplify.INSTANCE, 2)
                  .add(EffectGravity.INSTANCE)
                  .add(AugmentExtendTime.INSTANCE),
               this.chimera,
               new LivingCaster(this.chimera)
            )
         );
         resolver.onCastOnEntity(target);
         PortUtil.sendMessage(target, Component.m_237115_("ars_nouveau.chimera.rage"));
      }
   }

   public boolean m_8045_() {
      return !this.finished;
   }

   public boolean m_8036_() {
      return this.chimera.m_5448_() != null && this.chimera.rageTimer <= 0 && this.chimera.getPhase() > 0 && this.chimera.canRage();
   }
}
