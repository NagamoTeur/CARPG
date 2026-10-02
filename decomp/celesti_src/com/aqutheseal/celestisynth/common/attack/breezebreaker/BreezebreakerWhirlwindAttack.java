package com.aqutheseal.celestisynth.common.attack.breezebreaker;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastBreezebreakerTornado;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BreezebreakerWhirlwindAttack extends BreezebreakerAttack {
   public BreezebreakerWhirlwindAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      if (this.player.m_21205_() == this.stack && this.getPlayer().m_21206_() != this.stack) {
         return AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_SHIFT_RIGHT;
      } else if (this.player.m_21206_() == this.stack && this.getPlayer().m_21205_() != this.stack) {
         return AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_SHIFT_LEFT;
      } else if (this.player.m_21206_() == this.stack && this.getPlayer().m_21205_() == this.stack) {
         boolean shouldShiftRight = this.getPlayer().m_217043_().m_188499_();
         return shouldShiftRight
            ? AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_SHIFT_RIGHT
            : AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_SHIFT_LEFT;
      } else {
         return AnimationManager.AnimationsList.CLEAR;
      }
   }

   @Override
   public void startUsing() {
      super.startUsing();
      this.useAndDamageItem(this.stack, this.getPlayer().f_19853_, this.player, 3);
   }

   @Override
   public int getCooldown() {
      return this.buffStateModified((Integer)CSConfigManager.COMMON.breezebreakerShiftSkillCD.get());
   }

   @Override
   public int getAttackStopTime() {
      return 20;
   }

   @Override
   public boolean getCondition() {
      return this.getPlayer().m_6047_();
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() == 10) {
         this.getPlayer().m_216990_((SoundEvent)CSSoundEvents.CS_WIND_STRIKE.get());
         this.getPlayer().m_216990_((SoundEvent)CSSoundEvents.CS_WHIRLWIND.get());
         if (!this.player.f_19853_.m_5776_()) {
            SkillCastBreezebreakerTornado tornadoSkillCast = (SkillCastBreezebreakerTornado)((EntityType)CSEntityTypes.BREEZEBREAKER_TORNADO.get())
               .m_20615_(this.player.f_19853_);
            tornadoSkillCast.setOwnerUuid(this.player.m_20148_());
            tornadoSkillCast.setAngleX((float)this.calculateXLook(this.player));
            tornadoSkillCast.setAngleY((float)this.calculateYLook(this.player));
            tornadoSkillCast.setAngleZ((float)this.calculateZLook(this.player));
            tornadoSkillCast.setAddAngleX((float)this.calculateXLook(this.player));
            tornadoSkillCast.setAddAngleY((float)this.calculateYLook(this.player));
            tornadoSkillCast.setAddAngleZ((float)this.calculateZLook(this.player));
            tornadoSkillCast.m_6027_(this.player.m_20185_(), this.getPlayer().m_20186_() + 1.0, this.getPlayer().m_20189_());
            this.getPlayer().f_19853_.m_7967_(tornadoSkillCast);
         }
      }
   }

   @Override
   public void stopUsing() {
   }
}
