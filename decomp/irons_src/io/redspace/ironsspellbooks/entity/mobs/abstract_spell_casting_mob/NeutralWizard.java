package io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob;

import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NeutralWizard extends AbstractSpellCastingMob implements NeutralMob {
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.m_145020_(20, 39);
   private int remainingPersistentAngerTime;
   @Nullable
   private UUID persistentAngerTarget;
   private int lastAngerLevelUpdate;
   private int angerLevel;

   protected NeutralWizard(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public void m_6825_() {
      this.m_7870_(PERSISTENT_ANGER_TIME.m_214085_(this.f_19796_));
   }

   public void m_7870_(int pTime) {
      this.remainingPersistentAngerTime = pTime;
   }

   public int m_6784_() {
      return this.remainingPersistentAngerTime;
   }

   public void m_6925_(@Nullable UUID pTarget) {
      this.persistentAngerTarget = pTarget;
   }

   @Nullable
   public UUID m_6120_() {
      return this.persistentAngerTarget;
   }

   @Override
   public void m_7380_(CompoundTag pCompound) {
      this.m_21678_(pCompound);
      pCompound.m_128405_("AngerLevel", this.angerLevel);
      super.m_7380_(pCompound);
   }

   @Override
   public void m_7378_(CompoundTag pCompound) {
      this.m_147285_(this.f_19853_, pCompound);
      this.angerLevel = pCompound.m_128451_("AngerLevel");
      super.m_7378_(pCompound);
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.f_19853_.f_46443_) {
         this.m_21666_((ServerLevel)this.f_19853_, true);
      }

      if (this.angerLevel > 0 && this.lastAngerLevelUpdate + 400 < this.f_19797_) {
         this.angerLevel--;
         this.lastAngerLevelUpdate = this.f_19797_;
      }
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      if (pSource.m_7639_() instanceof Player player && !player.m_7500_()) {
         this.increaseAngerLevel((int)Math.ceil((double)pAmount));
      }

      return super.m_6469_(pSource, pAmount);
   }

   public void increaseAngerLevel(int levels) {
      if (!this.f_19853_.f_46443_ && this.angerLevel < this.getAngerThreshold()) {
         MagicManager.spawnParticles(
            this.f_19853_, ParticleTypes.f_123792_, this.m_20185_(), this.m_20186_() + 1.25, this.m_20189_(), 15, 0.3, 0.2, 0.3, 0.0, false
         );
         this.getAngerSound().ifPresent(sound -> this.m_5496_(sound, this.m_6121_(), this.m_6100_()));
      }

      this.angerLevel = Math.min(this.angerLevel + levels, 10);
      this.lastAngerLevelUpdate = this.f_19797_;
   }

   public Optional<SoundEvent> getAngerSound() {
      return Optional.empty();
   }

   public int getAngerThreshold() {
      return 2;
   }

   public boolean isHostileTowards(LivingEntity entity) {
      return this.m_21674_(entity) && this.angerLevel >= this.getAngerThreshold();
   }

   public boolean m_21674_(LivingEntity pTarget) {
      return this.angerLevel > 0 && super.m_21674_(pTarget);
   }

   public boolean guardsBlocks() {
      return true;
   }
}
