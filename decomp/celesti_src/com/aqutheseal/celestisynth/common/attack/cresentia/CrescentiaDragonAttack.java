package com.aqutheseal.celestisynth.common.attack.cresentia;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastCrescentiaRanged;
import com.aqutheseal.celestisynth.common.item.weapons.CrescentiaItem;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CrescentiaDragonAttack extends WeaponAttackInstance {
   public CrescentiaDragonAttack(Player player, ItemStack stack) {
      super(player, stack);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_CRESCENTIA_THROW;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.crescentiaShiftSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 30;
   }

   @Override
   public boolean getCondition() {
      return this.getPlayer().m_6144_();
   }

   @Override
   public void startUsing() {
      this.useAndDamageItem(this.getStack(), this.getPlayer().f_19853_, this.getPlayer(), 5);
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() <= 20) {
         this.setDeltaPlayer(this.getPlayer(), 0.0, 0.0, 0.0);
      }

      if (this.getTimerProgress() == 20) {
         if (!this.player.f_19853_.m_5776_()) {
            SkillCastCrescentiaRanged cresentiaSkillCast = (SkillCastCrescentiaRanged)((EntityType)CSEntityTypes.CRESCENTIA_RANGED.get())
               .m_20615_(this.getPlayer().f_19853_);
            cresentiaSkillCast.setOwnerUuid(this.getPlayer().m_20148_());
            cresentiaSkillCast.setAngleX((float)this.calculateXLook(this.getPlayer()));
            cresentiaSkillCast.setAngleY((float)this.calculateYLook(this.getPlayer()));
            cresentiaSkillCast.setAngleZ((float)this.calculateZLook(this.getPlayer()));
            cresentiaSkillCast.setAddAngleX((float)this.calculateXLook(this.getPlayer()) / 2.0F);
            cresentiaSkillCast.setAddAngleY((float)this.calculateYLook(this.getPlayer()) / 2.0F);
            cresentiaSkillCast.setAddAngleZ((float)this.calculateZLook(this.getPlayer()) / 2.0F);
            cresentiaSkillCast.m_6027_(this.getPlayer().m_20185_(), this.getPlayer().m_20186_() + 1.0, this.getPlayer().m_20189_());
            this.getPlayer().f_19853_.m_7967_(cresentiaSkillCast);
         }

         for (int i = 0; i < 10; i++) {
            float offX = this.getPlayer().f_19853_.f_46441_.m_188501_() * 12.0F - 6.0F;
            float offY = this.getPlayer().f_19853_.f_46441_.m_188501_() * 12.0F - 6.0F;
            float offZ = this.getPlayer().f_19853_.f_46441_.m_188501_() * 12.0F - 6.0F;
            CrescentiaItem.createCrescentiaFirework(
               this.getStack(),
               this.getPlayer().f_19853_,
               this.getPlayer(),
               this.getPlayer().m_20185_() + (double)offX,
               this.getPlayer().m_20186_() + (double)offY,
               this.getPlayer().m_20189_() + (double)offZ,
               true
            );
            this.getPlayer().m_5496_(SoundEvents.f_11913_, 1.0F, 1.5F);
         }
      }
   }

   @Override
   public void stopUsing() {
   }
}
