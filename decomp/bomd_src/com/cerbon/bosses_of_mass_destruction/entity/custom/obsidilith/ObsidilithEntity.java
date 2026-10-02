package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.config.mob.ObsidilithConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.CooldownAction;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.ActionGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.FindTargetGoal;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.CappedHeal;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.EffectsImmunity;
import com.cerbon.bosses_of_mass_destruction.entity.util.animation.AnimationPredicate;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;

public class ObsidilithEntity extends BaseEntity {
   private final ObsidilithConfig mobConfig;
   private final Map<Byte, IActionWithCooldown> statusRegistry;
   private final ObsidilithMoveLogic moveLogic;
   private final ObsidilithEffectHandler effectHandler;
   private final List<BlockPos> activePillars = new ArrayList<>();
   public byte currentAttack = 0;

   public ObsidilithEntity(EntityType<? extends PathfinderMob> entityType, Level level, ObsidilithConfig mobConfig) {
      super(entityType, level);
      this.mobConfig = mobConfig;
      this.f_19811_ = true;
      this.statusRegistry = Map.of(
         (byte)5,
         new BurstAction(this),
         (byte)6,
         new WaveAction(this),
         (byte)7,
         new SpikeAction(this),
         (byte)8,
         new AnvilAction(this, mobConfig.anvilAttackExplosionStrength),
         (byte)9,
         new PillarAction(this)
      );
      DamageMemory damageMemory = new DamageMemory(10, this);
      this.moveLogic = new ObsidilithMoveLogic(this.statusRegistry, this, damageMemory);
      this.effectHandler = new ObsidilithEffectHandler(this, BMDCapabilities.getLevelEventScheduler(level));
      this.bossBar = new ServerBossEvent(this.m_5446_(), BossBarColor.PURPLE, BossBarOverlay.NOTCHED_12);
      this.damageHandler = new CompositeDamageHandler(this.moveLogic, new ShieldDamageHandler(this::isShielded), damageMemory);
      this.mobEffectHandler = new EffectsImmunity(MobEffects.f_19615_, MobEffects.f_19614_);
      this.serverTick = new CappedHeal(this, ObsidilithUtils.hpPillarShieldMilestones, mobConfig.idleHealingPerTick);
      if (!level.m_5776_()) {
         this.f_21345_.m_25352_(1, this.buildAttackGoal());
         this.f_21346_.m_25352_(2, new FindTargetGoal(this, Player.class, d -> this.m_20191_().m_82400_(d), 10, true, false, null));
         this.preTickEvents
            .addEvent(
               new TimedEvent(
                  () -> BMDUtils.playSound(
                        (ServerLevel)level, this.m_20182_(), (SoundEvent)BMDSounds.WAVE_INDICATOR.get(), SoundSource.HOSTILE, 1.5F, 0.7F, 24.0, null
                     ),
                  1
               )
            );
      }

      this.f_19804_.m_135372_(ObsidilithUtils.isShielded, false);
   }

   private ActionGoal buildAttackGoal() {
      CooldownAction attackAction = new CooldownAction(this.moveLogic, 80);
      return new ActionGoal(this::canContinueAttack, null, attackAction, null, attackAction);
   }

   @Override
   public void serverTick(ServerLevel serverLevel) {
      super.serverTick(serverLevel);
      this.activePillars.removeIf(posx -> this.f_19853_.m_8055_(posx).m_60734_() != BMDBlocks.OBSIDILITH_RUNE.get() || !posx.m_123314_(this.m_20183_(), 64.0));
      this.m_20088_().m_135381_(ObsidilithUtils.isShielded, !this.activePillars.isEmpty());
      if (this.f_19797_ % 40 == 0 && !this.activePillars.isEmpty()) {
         BlockPos pos = this.activePillars.get(new Random().nextInt(this.activePillars.size()));
         MathUtils.lineCallback(
            VecUtils.asVec3(pos).m_82520_(0.5, 0.5, 0.5),
            MobUtils.eyePos(this),
            15,
            (vec3, i) -> this.preTickEvents
                  .addEvent(
                     new TimedEvent(() -> BMDUtils.spawnParticle(serverLevel, (ParticleOptions)BMDParticles.PILLAR_RUNE.get(), vec3, Vec3.f_82478_, 0, 0.0), i)
                  )
         );
      }
   }

   public boolean m_5829_() {
      return true;
   }

   public void m_7334_(@NotNull Entity entity) {
   }

   public void m_6667_(@NotNull DamageSource damageSource) {
      if (this.mobConfig.spawnPillarOnDeath) {
         ObsidilithUtils.onDeath(this, this.mobConfig.experienceDrop);
         if (this.f_19853_.f_46443_) {
            this.effectHandler.handleStatus((byte)3);
         }
      }

      super.m_6667_(damageSource);
   }

   public boolean canContinueAttack() {
      return this.m_6084_() && this.m_5448_() != null;
   }

   @Override
   public void m_7822_(byte status) {
      IActionWithCooldown attackStatus = this.statusRegistry.get(status);
      if (attackStatus != null) {
         this.effectHandler.handleStatus(status);
         this.currentAttack = status;
         this.preTickEvents.addEvent(new TimedEvent(() -> this.currentAttack = 0, 40));
      }

      super.m_7822_(status);
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.OBSIDILITH_HURT.get();
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return (SoundEvent)BMDSounds.OBSIDILITH_DEATH.get();
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, "summon", 0.0F, new AnimationPredicate(animationState -> {
         animationState.getController().setAnimation(new AnimationBuilder().addAnimation("summon", false));
         return PlayState.CONTINUE;
      })));
   }

   @Override
   public void m_6478_(@NotNull MoverType type, @NotNull Vec3 movement) {
      super.m_6478_(type, new Vec3(0.0, movement.f_82480_, 0.0));
   }

   public boolean m_6060_() {
      return false;
   }

   public int m_21230_() {
      return this.m_5448_() != null ? super.m_21230_() : 24;
   }

   public void m_6043_() {
      BMDUtils.preventDespawnExceptPeaceful(this, this.f_19853_);
   }

   public boolean m_142535_(float fallDistance, float multiplier, @NotNull DamageSource source) {
      return false;
   }

   public boolean isShielded() {
      return (Boolean)this.m_20088_().m_135370_(ObsidilithUtils.isShielded);
   }

   public void addActivePillar(BlockPos pos) {
      this.activePillars.add(pos);
   }

   @NotNull
   @Override
   public CompoundTag m_20240_(@NotNull CompoundTag compound) {
      int[] activePillarsArray = this.activePillars.stream().flatMapToInt(p -> IntStream.of(p.m_123341_(), p.m_123342_(), p.m_123343_())).toArray();
      compound.m_128385_("activePillars", activePillarsArray);
      return super.m_20240_(compound);
   }

   @Override
   public void m_20258_(@NotNull CompoundTag compound) {
      super.m_20258_(compound);
      if (compound.m_128441_("activePillars")) {
         int[] activePillarsArray = compound.m_128465_("activePillars");

         for (int i = 0; i < activePillarsArray.length; i += 3) {
            BlockPos pos = new BlockPos(activePillarsArray[i], activePillarsArray[i + 1], activePillarsArray[i + 2]);
            this.activePillars.add(pos);
         }
      }
   }
}
