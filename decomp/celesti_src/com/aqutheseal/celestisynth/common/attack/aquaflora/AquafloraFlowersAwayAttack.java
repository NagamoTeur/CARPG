package com.aqutheseal.celestisynth.common.attack.aquaflora;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AquafloraFlowersAwayAttack extends AquafloraAttack {
   public AquafloraFlowersAwayAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_POLTERGEIST_RETREAT;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.aquafloraBloomShiftSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 0;
   }

   @Override
   public boolean getCondition() {
      return this.getTagController().m_128471_("cs.checkPassiveIfBlooming") && this.getPlayer().m_6047_();
   }

   @Override
   public void startUsing() {
      this.sendExpandingParticles(
         this.getPlayer().f_19853_, ParticleTypes.f_123810_, this.getPlayer().m_20185_(), this.getPlayer().m_20186_(), this.getPlayer().m_20189_(), 55, 1.2F
      );
      CSEffectEntity.createInstance(this.player, null, (CSVisualType)CSVisualTypes.AQUAFLORA_FLOWER.get(), 0.0, -1.0, 0.0);
      List<Entity> entities = this.iterateEntities(this.getPlayer().f_19853_, this.createAABB(this.player.m_20183_(), 12.0));
      this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_BLING.get(), 0.4F, 0.5F);

      for (Entity target : entities) {
         if (target instanceof LivingEntity) {
            LivingEntity lt = (LivingEntity)target;
            if (target != this.player && target.m_6084_() && !this.player.m_7307_(target)) {
               CSEffectEntity.createInstance(this.player, target, (CSVisualType)CSVisualTypes.AQUAFLORA_FLOWER_BIND.get());
               this.hurtNoKB(this.player, lt, (float)((Double)CSConfigManager.COMMON.aquafloraBloomShiftSkillDmg.get()).doubleValue());
               target.m_20334_(
                  (this.player.m_20185_() - target.m_20185_()) * 0.35,
                  (this.player.m_20186_() - target.m_20186_()) * 0.35,
                  (this.player.m_20189_() - target.m_20189_()) * 0.35
               );
            }
         }
      }
   }

   @Override
   public void tickAttack() {
   }

   @Override
   public void stopUsing() {
      this.getTagController().m_128379_("cs.checkPassiveIfBlooming", false);
   }
}
