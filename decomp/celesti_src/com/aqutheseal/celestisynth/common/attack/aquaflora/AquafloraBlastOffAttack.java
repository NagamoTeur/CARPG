package com.aqutheseal.celestisynth.common.attack.aquaflora;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import java.util.List;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AquafloraBlastOffAttack extends AquafloraAttack {
   public AquafloraBlastOffAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_AQUAFLORA_BASH;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.aquafloraShiftSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 0;
   }

   @Override
   public boolean getCondition() {
      return !this.getTagController().m_128471_("cs.checkPassiveIfBlooming") && this.getPlayer().m_6047_();
   }

   @Override
   public void startUsing() {
      List<Entity> surroundingEntities = this.iterateEntities(
         this.getPlayer().f_19853_,
         this.createAABB(
            this.player
               .m_20183_()
               .m_7637_(this.calculateXLook(this.player) * 4.0, 2.0 + this.calculateYLook(this.player) * 3.0, this.calculateZLook(this.player) * 4.0),
            3.0
         )
      );
      this.getPlayer().m_5496_(SoundEvents.f_12555_, 0.7F, 1.5F);
      CSEffectEntity.createInstance(
         this.player,
         null,
         (CSVisualType)CSVisualTypes.AQUAFLORA_BASH.get(),
         this.calculateXLook(this.player) * 2.0,
         1.5,
         this.calculateZLook(this.player) * 2.0
      );

      for (Entity entityBatch : surroundingEntities) {
         if (entityBatch instanceof LivingEntity) {
            LivingEntity target = (LivingEntity)entityBatch;
            if (target != this.player && target.m_6084_() && !this.player.m_7307_(target)) {
               target.m_20334_((target.m_20185_() - this.getPlayer().m_20185_()) * 0.4, 1.0, (target.m_20189_() - this.getPlayer().m_20189_()) * 0.4);
               this.hurtNoKB(
                  this.player,
                  target,
                  (float)((Double)CSConfigManager.COMMON.aquafloraShiftSkillDmg.get()).doubleValue() + this.getSharpnessValue(this.getStack(), 1.0F)
               );
               this.createHitEffect(this.getStack(), this.getPlayer().f_19853_, this.player, target);
               CSWeaponUtil.disableRunningWeapon(target);
            }
         }
      }

      double check = this.getPlayer().m_20096_() ? 0.3 : 0.14;
      if (this.getPlayer().f_19853_.m_5776_()) {
         this.shakeScreens(this.player, 3, 2, 0.015F);
      }

      this.getPlayer().m_20256_(this.player.m_20184_().m_82520_(this.calculateXLook(this.player) * check, 0.0, this.calculateZLook(this.player) * check));
   }

   @Override
   public void tickAttack() {
   }

   @Override
   public void stopUsing() {
      this.getTagController().m_128379_("cs.checkPassiveIfBlooming", true);
   }
}
