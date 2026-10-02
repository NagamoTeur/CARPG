package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.HistoricalData;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.CompositeGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.FindTargetGoal;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.AnimationHolder;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.CappedHeal;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamagedAttackerNotSeen;
import com.cerbon.bosses_of_mass_destruction.entity.damage.StagedDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityTick;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.AnimationUtils;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;

public class LichEntity extends BaseEntity {
   private final LichConfig mobConfig;
   private final AnimationHolder animationHolder;
   private final TeleportAction teleportAction;
   public final HistoricalData<Vec3> velocityHistory;
   public final boolean shouldSetToNighttime;
   public boolean collides;

   public LichEntity(EntityType<? extends LichEntity> entityType, Level level, LichConfig mobConfig) {
      super(entityType, level);
      this.mobConfig = mobConfig;
      this.f_19811_ = true;
      this.animationHolder = new AnimationHolder(
         this,
         Map.of(
            (byte)10,
            new AnimationHolder.Animation("unteleport", "idle"),
            (byte)12,
            new AnimationHolder.Animation("rage_mode", "idle"),
            (byte)11,
            new AnimationHolder.Animation("rage_mode", "idle"),
            (byte)5,
            new AnimationHolder.Animation("summon_fireball", "idle"),
            (byte)7,
            new AnimationHolder.Animation("summon_minions", "idle"),
            (byte)8,
            new AnimationHolder.Animation("rage_mode", "idle"),
            (byte)9,
            new AnimationHolder.Animation("teleport", "teleporting"),
            (byte)6,
            new AnimationHolder.Animation("summon_missiles", "idle"),
            (byte)3,
            new AnimationHolder.Animation("idle", "idle")
         ),
         (byte)4,
         0
      );
      MinionAction minionAction = new MinionAction(this, this.preTickEvents, this::cancelAttackAction);
      this.teleportAction = new TeleportAction(this, this.preTickEvents, this::cancelAttackAction);
      Map<Byte, IActionWithCooldown> statusRegistry = Map.of(
         (byte)5,
         new CometAction(this, this.preTickEvents, this::cancelAttackAction, mobConfig),
         (byte)6,
         new VolleyAction(this, mobConfig, this.preTickEvents, this::cancelAttackAction),
         (byte)7,
         minionAction,
         (byte)8,
         new MinionRageAction(this, this.preTickEvents, this::cancelAttackAction, minionAction),
         (byte)9,
         this.teleportAction,
         (byte)12,
         new CometRageAction(this, this.preTickEvents, this::cancelAttackAction, mobConfig),
         (byte)11,
         new VolleyRageAction(this, mobConfig, this.preTickEvents, this::cancelAttackAction)
      );
      DamageMemory damageMemory = new DamageMemory(5, this);
      LichMoveLogic moveLogic = new LichMoveLogic(statusRegistry, this, damageMemory);
      LichParticleHandler lichParticles = new LichParticleHandler(this, this.preTickEvents);
      this.shouldSetToNighttime = mobConfig.eternalNighttime;
      this.velocityHistory = new HistoricalData<>(Vec3.f_82478_, 2);
      this.collides = true;
      CappedHeal cappedHeal = new CappedHeal(this, LichUtils.hpPercentRageModes, mobConfig.idleHealingPerTick);
      this.entityEventHandler = new CompositeEntityEventHandler(this.animationHolder, lichParticles);
      this.damageHandler = new CompositeDamageHandler(
         new StagedDamageHandler(LichUtils.hpPercentRageModes, () -> level.m_7605_(this, (byte)13)), new DamagedAttackerNotSeen(this, livingEntity -> {
            if (livingEntity instanceof ServerPlayer) {
               this.teleportAction.performTeleport((ServerPlayer)livingEntity);
            }
         }), moveLogic, damageMemory
      );
      this.bossBar = new ServerBossEvent(this.m_5446_(), BossBarColor.BLUE, BossBarOverlay.PROGRESS);
      this.serverTick = new CompositeEntityTick(cappedHeal, moveLogic);
      this.clientTick = lichParticles;
      if (!level.f_46443_) {
         LichActions attackHelper = new LichActions(this, moveLogic);
         LichMovement moveHelper = new LichMovement(this);
         this.f_21345_.m_25352_(1, new FloatGoal(this));
         this.f_21345_.m_25352_(3, new CompositeGoal(moveHelper.buildAttackMovement(), attackHelper.buildAttackGoal()));
         this.f_21345_.m_25352_(4, moveHelper.buildWanderGoal());
         this.f_21346_.m_25352_(2, new FindTargetGoal(this, Player.class, d -> this.m_20191_().m_82400_(d), 10, true, false, null));
      }
   }

   public void registerControllers(AnimationData data) {
      this.animationHolder.registerControllers(data);
      data.addAnimationController(new AnimationController(this, "skull_float", 0.0F, AnimationUtils.createIdlePredicate("skull_float")));
      data.addAnimationController(new AnimationController(this, "float", 0.0F, AnimationUtils.createIdlePredicate("float")));
      data.addAnimationController(new AnimationController(this, "book_idle", 0.0F, AnimationUtils.createIdlePredicate("book_idle")));
   }

   public boolean inLineOfSight(Entity target) {
      boolean hasDirectLineOfSight = VanillaCopiesServer.hasDirectLineOfSight(this.m_146892_(), MobUtils.eyePos(target), this.f_19853_, this);
      Vec3 directionToLich = MathUtils.unNormedDirection(MobUtils.eyePos(target), this.m_146892_());
      boolean facingSameDirection = MathUtils.facingSameDirection(target.m_20154_(), directionToLich);
      return hasDirectLineOfSight && facingSameDirection;
   }

   @Override
   public void clientTick() {
      this.velocityHistory.set(this.m_20184_());
   }

   @Override
   public void serverTick(ServerLevel serverLevel) {
      if (this.shouldSetToNighttime) {
         serverLevel.m_8615_(LichUtils.timeToNighttime(serverLevel.m_8044_()));
      }
   }

   public boolean m_7337_(@NotNull Entity entity) {
      return this.collides;
   }

   public boolean m_142535_(float fallDistance, float multiplier, @NotNull DamageSource source) {
      return false;
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public void m_6043_() {
      BMDUtils.preventDespawnExceptPeaceful(this, this.f_19853_);
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.LICH_HURT.get();
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return (SoundEvent)BMDSounds.LICH_DEATH.get();
   }

   protected float m_6121_() {
      return 5.0F;
   }

   public boolean m_6147_() {
      return false;
   }

   private boolean cancelAttackAction() {
      return this.m_21224_() || this.m_5448_() == null;
   }

   public void m_6667_(@NotNull DamageSource damageSource) {
      int expTicks = 18;
      int expPerTick = (int)((float)this.mobConfig.experienceDrop / (float)expTicks);
      this.preTickEvents
         .addEvent(new TimedEvent(() -> VanillaCopiesServer.awardExperience(expPerTick, MobUtils.eyePos(this), this.f_19853_), 0, expTicks, () -> false));
      this.f_19853_.m_45976_(Phantom.class, new AABB(this.m_20183_()).m_82377_(100.0, 100.0, 100.0)).forEach(LivingEntity::m_6074_);
      super.m_6667_(damageSource);
   }

   protected void m_7840_(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public void m_7023_(@NotNull Vec3 movementInput) {
      VanillaCopiesServer.travel(movementInput, this, 0.91F);
   }
}
