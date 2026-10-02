package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.BooleanFlag;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.EntityBounds;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.bosses_of_mass_destruction.config.mob.VoidBlossomConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.ActionGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.CompositeGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.FindTargetGoal;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.AnimationHolder;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.NetworkedHitboxManager;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.VoidBlossomHitboxes;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.damage.StagedDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityTick;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationData;

public class VoidBlossomEntity extends BaseEntity implements MultipartAwareEntity {
   private final AnimationHolder animationHolder;
   private final NetworkedHitboxManager hitboxHelper;
   public final VoidBlossomClientSpikeHandler clientSpikeHandler;
   public static final List<Float> hpMilestones = List.of(0.0F, 0.25F, 0.5F, 0.75F, 1.0F);

   public VoidBlossomEntity(EntityType<? extends PathfinderMob> entityType, Level level, VoidBlossomConfig mobConfig) {
      super(entityType, level);
      this.f_19811_ = true;
      this.animationHolder = new AnimationHolder(
         this,
         Map.of(
            (byte)4,
            new AnimationHolder.Animation("spike", "idle"),
            (byte)5,
            new AnimationHolder.Animation("spike_wave", "idle"),
            (byte)7,
            new AnimationHolder.Animation("spore", "idle"),
            (byte)8,
            new AnimationHolder.Animation("leaf_blade", "idle"),
            (byte)9,
            new AnimationHolder.Animation("blossom", "idle"),
            (byte)10,
            new AnimationHolder.Animation("spawn", "idle"),
            (byte)3,
            new AnimationHolder.Animation("death", "idle")
         ),
         (byte)6,
         0
      );
      VoidBlossomHitboxes hitboxes = new VoidBlossomHitboxes(this);
      this.hitboxHelper = new NetworkedHitboxManager(this, hitboxes.getMap());
      this.entityEventHandler = new CompositeEntityEventHandler(
         this.animationHolder, new ClientSporeEffectHandler(this, this.preTickEvents), new ClientDeathEffectHandler(this, this.preTickEvents)
      );
      DamageMemory damageMemory = new DamageMemory(10, this);
      TargetSwitcher targetSwitcher = new TargetSwitcher(this, damageMemory);
      BooleanFlag shouldSpawnBlossoms = new BooleanFlag();
      StagedDamageHandler hpDetector = new StagedDamageHandler(hpMilestones, shouldSpawnBlossoms::flag);
      this.bossBar = new ServerBossEvent(this.m_5446_(), BossBarColor.GREEN, BossBarOverlay.NOTCHED_12);
      this.clientSpikeHandler = new VoidBlossomClientSpikeHandler();
      this.clientTick = this.clientSpikeHandler;
      this.serverTick = new CompositeEntityTick(
         new LightBlockPlacer(this), new VoidBlossomSpikeTick(this), hitboxes.getTickers(), new CappedHeal(this, hpMilestones, mobConfig.idleHealingPerTick)
      );
      this.deathServerTick = new CompositeEntityTick(
         new LightBlockRemover(this), new VoidBlossomDropExpDeathTick(this, this.preTickEvents, mobConfig.experienceDrop)
      );
      this.damageHandler = new CompositeDamageHandler(hpDetector, hitboxes.getDamageHandlers(), damageMemory);
      if (!level.m_5776_() && level instanceof ServerLevel) {
         VoidBlossomAttacks attackHandler = new VoidBlossomAttacks(this, this.preTickEvents, shouldSpawnBlossoms::getAndReset, targetSwitcher);
         this.f_21345_.m_25352_(2, new CompositeGoal());
         this.f_21345_
            .m_25352_(1, new CompositeGoal(attackHandler.buildAttackGoal(), new ActionGoal(this::canContinueAttack, null, this::lookAtTarget, null, null)));
         this.f_21346_.m_25352_(2, new FindTargetGoal(this, Player.class, d -> this.m_20191_().m_82400_(d), 10, true, false, null));
         this.preTickEvents.addEvent(new TimedEvent(() -> this.m_5496_((SoundEvent)BMDSounds.SPIKE_WAVE_INDICATOR.get(), 2.0F, 0.7F), 1));
      } else if (level.m_5776_()) {
         this.animationHolder.handleEntityEvent((byte)10);
      }
   }

   private void lookAtTarget() {
      LivingEntity target = this.m_5448_();
      if (target != null) {
         this.f_21365_.m_24964_(MobUtils.eyePos(target));
         this.m_21391_(target, (float)this.m_8085_(), (float)this.m_8132_());
      }
   }

   public void registerControllers(AnimationData data) {
      this.animationHolder.registerControllers(data);
   }

   @Override
   public void m_6478_(@NotNull MoverType type, @NotNull Vec3 movement) {
      super.m_6478_(type, new Vec3(0.0, movement.m_7098_(), 0.0));
   }

   private boolean canContinueAttack() {
      return this.m_6084_() && this.m_5448_() != null;
   }

   public boolean m_6060_() {
      return false;
   }

   @Override
   public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) {
      return this.hitboxHelper.getBounds().getBox(bounds);
   }

   @Override
   public EntityBounds getBounds() {
      return this.hitboxHelper.getBounds();
   }

   public boolean m_5830_() {
      return false;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.VOID_BLOSSOM_HURT.get();
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return (SoundEvent)BMDSounds.VOID_BLOSSOM_HURT.get();
   }

   protected float m_6121_() {
      return 1.5F;
   }

   public void m_6043_() {
      BMDUtils.preventDespawnExceptPeaceful(this, this.f_19853_);
   }

   public int m_21230_() {
      return this.m_5448_() != null ? super.m_21230_() : 20;
   }

   @Override
   public void onSetPos(double x, double y, double z) {
      if (this.hitboxHelper != null) {
         this.hitboxHelper.updatePosition();
      }
   }

   @Override
   public void setNextDamagedPart(@Nullable String part) {
      this.hitboxHelper.setNextDamagedPart(part);
   }
}
