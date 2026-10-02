package com.aqutheseal.celestisynth.common.attack.poltergeist;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.api.mixin.LivingMixinSupport;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastPoltergeistWard;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class PoltergeistCosmicSteelAttack extends WeaponAttackInstance {
   public static final String IS_IMPACT_LARGE = "cs.isImpactLarge";
   public static final String SMASH_HEIGHT = "cs.poltergeistSmashHeight";
   public static final String SMASH_COUNT_FOR_PASSIVE = "cs.smashCountForPassive";

   public PoltergeistCosmicSteelAttack(Player player, ItemStack stack) {
      super(player, stack);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_POLTERGEIST_SMASH;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.poltergeistSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 20;
   }

   @Override
   public boolean getCondition() {
      return !this.getPlayer().m_6144_();
   }

   @Override
   public void startUsing() {
      this.useAndDamageItem(this.getStack(), this.getPlayer().f_19853_, this.getPlayer(), 5);
   }

   @Override
   public void tickAttack() {
      boolean isGiantImpact = this.getTagExtras().m_128471_("cs.isImpactLarge");
      if (this.getTimerProgress() == 20) {
         this.getPlayer()
            .m_6027_(
               this.player.m_20185_(),
               (double)(this.calculateNonCollidingPos(this.player.f_19853_, this.getPlayer().m_20183_()).m_123342_() + 1),
               this.getPlayer().m_20189_()
            );
         CSVisualType crack = isGiantImpact
            ? (CSVisualType)CSVisualTypes.POLTERGEIST_IMPACT_CRACK_LARGE.get()
            : (CSVisualType)CSVisualTypes.POLTERGEIST_IMPACT_CRACK.get();
         double range = isGiantImpact ? 6.5 : 4.0;
         double xx = this.calculateXLook(this.player) * 3.0;
         double zz = this.calculateZLook(this.player) * 3.0;
         this.doImpact(isGiantImpact, xx, zz, range);
         this.player.m_5496_(SoundEvents.f_11858_, 1.0F, 1.75F);
         this.player.m_5496_((SoundEvent)CSSoundEvents.CS_LOUD_IMPACT.get(), 1.5F, 1.0F);
         CSEffectEntity.createInstance(this.player, null, crack, xx, isGiantImpact ? -1.3 : -0.5, zz);
         if (isGiantImpact) {
            if (!this.player.f_19853_.m_5776_()) {
               SkillCastPoltergeistWard projectile = (SkillCastPoltergeistWard)((EntityType)CSEntityTypes.POLTERGEIST_WARD.get())
                  .m_20615_(this.player.f_19853_);
               projectile.setOwnerUuid(this.player.m_20148_());
               projectile.m_6027_(this.player.m_20185_() + xx, this.getPlayer().m_20186_(), this.getPlayer().m_20189_() + zz);
               this.getPlayer().f_19853_.m_7967_(projectile);
            }

            this.shakeScreensForNearbyPlayers(this.player, this.getPlayer().f_19853_, 24.0, 60, 30, 0.035F);
         } else {
            CSEffectEntity.createInstance(this.player, null, (CSVisualType)CSVisualTypes.POLTERGEIST_WARD_SUMMON_SMALL.get(), xx, 1.0, zz);
            this.shakeScreensForNearbyPlayers(this.player, this.getPlayer().f_19853_, 12.0, 30, 15, 0.01F);
         }

         this.addComboPoint(this.getStack(), this.player);
         if (this.getTagController().m_128451_("cs.poltergeistSmashHeight") > 1) {
            this.getPlayer().m_36335_().m_41527_(this.getStack().m_41720_());
         }
      }
   }

   public void doImpact(boolean isGiantImpact, double kbX, double kbZ, double range) {
      for (Entity entityBatch : this.iterateEntities(this.player.f_19853_, this.createAABB(this.player.m_20183_().m_7637_(kbX, 1.0, kbZ), range))) {
         if (entityBatch instanceof LivingEntity target && target != this.player && target.m_6084_() && !this.player.m_7307_(target)) {
            this.hurtNoKB(
               this.player,
               target,
               (
                     isGiantImpact
                        ? (float)((Double)CSConfigManager.COMMON.poltergeistSkillDmg.get()).doubleValue() * 1.4F
                        : (float)((Double)CSConfigManager.COMMON.poltergeistSkillDmg.get()).doubleValue()
                  )
                  + this.getSharpnessValue(this.getStack(), 1.2F)
                  + (float)this.getTagController().m_128451_("cs.poltergeistSmashHeight")
            );
            target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 2));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 100, 0));
            target.f_19864_ = true;
            target.m_20334_(
               (target.m_20185_() - (this.player.m_20185_() + kbX)) / 3.0,
               (target.m_20186_() - this.getPlayer().m_20186_()) / 3.0,
               (target.m_20189_() - (this.player.m_20189_() + kbZ)) / 3.0
            );
            CSWeaponUtil.disableRunningWeapon(target);
            if (target instanceof LivingMixinSupport lms) {
               lms.setPhantomTagger(this.player);
            }
         }

         if (entityBatch instanceof Projectile projectile) {
            projectile.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   @Override
   public void stopUsing() {
      this.getTagController().m_128405_("cs.poltergeistSmashHeight", 0);
   }

   public BlockPos calculateNonCollidingPos(Level level, BlockPos pos) {
      MutableBlockPos mutablePos = new MutableBlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());

      do {
         mutablePos.m_122173_(Direction.DOWN);
         if ((Boolean)CSConfigManager.COMMON.enablePoltergeistHeightDmg.get()) {
            this.getTagController().m_128405_("cs.poltergeistSmashHeight", this.getTagController().m_128451_("cs.poltergeistSmashHeight") + 1);
         }
      } while (mutablePos.m_123342_() > level.m_141937_() && level.m_8055_(mutablePos).m_60647_(level, mutablePos, PathComputationType.LAND));

      return new BlockPos(mutablePos.m_123341_(), mutablePos.m_123342_(), mutablePos.m_123343_());
   }

   public void addComboPoint(ItemStack itemStack, Player player) {
      CompoundTag elementAltsTag = itemStack.m_41698_("csExtras");
      boolean isImpactLarge = elementAltsTag.m_128471_("cs.isImpactLarge");
      if (!isImpactLarge && elementAltsTag.m_128451_("cs.smashCountForPassive") < 9) {
         this.getPlayer().m_216990_(SoundEvents.f_11852_);
         elementAltsTag.m_128405_("cs.smashCountForPassive", elementAltsTag.m_128451_("cs.smashCountForPassive") + 1);
      } else if (!isImpactLarge && elementAltsTag.m_128451_("cs.smashCountForPassive") >= 9) {
         this.getPlayer().m_216990_(SoundEvents.f_11860_);
         elementAltsTag.m_128379_("cs.isImpactLarge", true);
      } else if (isImpactLarge) {
         elementAltsTag.m_128379_("cs.isImpactLarge", false);
         elementAltsTag.m_128405_("cs.smashCountForPassive", 0);
      }
   }
}
