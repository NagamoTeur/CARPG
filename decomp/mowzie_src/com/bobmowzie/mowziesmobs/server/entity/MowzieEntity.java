package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.IntermittentAnimation;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.spawn.SpawnHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class MowzieEntity extends PathfinderMob implements IEntityAdditionalSpawnData, IntermittentAnimatableEntity {
   private static final byte START_IA_HEALTH_UPDATE_ID = 4;
   private static final byte MUSIC_PLAY_ID = 67;
   private static final byte MUSIC_STOP_ID = 68;
   public int frame;
   public float targetDistance = -1.0F;
   public float targetAngle = -1.0F;
   public boolean active;
   public LivingEntity blockingEntity = null;
   public boolean playsHurtAnimation = true;
   protected boolean dropAfterDeathAnim = true;
   public boolean hurtInterruptsAnimation = false;
   private final List<IntermittentAnimation<?>> intermittentAnimations = new ArrayList<>();
   @OnlyIn(Dist.CLIENT)
   public Vec3[] socketPosArray;
   protected boolean prevOnGround;
   protected boolean prevPrevOnGround;
   protected boolean willLandSoon;
   private int killDataRecentlyHit;
   private DamageSource killDataCause;
   private Player killDataAttackingPlayer;
   private final MMBossInfoServer bossInfo = new MMBossInfoServer(this);
   private static final UUID HEALTH_CONFIG_MODIFIER_UUID = UUID.fromString("eff1c400-910c-11ec-b909-0242ac120002");
   private static final UUID ATTACK_CONFIG_MODIFIER_UUID = UUID.fromString("f76a7c90-910c-11ec-b909-0242ac120002");
   private static final EntityDataAccessor<Boolean> STRAFING = SynchedEntityData.m_135353_(MowzieEntity.class, EntityDataSerializers.f_135035_);
   public boolean renderingInGUI = false;

   public MowzieEntity(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
      if (world.f_46443_) {
         this.socketPosArray = new Vec3[0];
      }

      ConfigHandler.CombatConfig combatConfig = this.getCombatConfig();
      if (combatConfig != null) {
         AttributeInstance maxHealthAttr = this.m_21051_(Attributes.f_22276_);
         if (maxHealthAttr != null) {
            double difference = maxHealthAttr.m_22115_() * (Double)this.getCombatConfig().healthMultiplier.get() - maxHealthAttr.m_22115_();
            maxHealthAttr.m_22118_(new AttributeModifier(HEALTH_CONFIG_MODIFIER_UUID, "Health config multiplier", difference, Operation.ADDITION));
            this.m_21153_(this.m_21233_());
         }

         AttributeInstance attackDamageAttr = this.m_21051_(Attributes.f_22281_);
         if (attackDamageAttr != null) {
            double difference = attackDamageAttr.m_22115_() * (Double)this.getCombatConfig().attackMultiplier.get() - attackDamageAttr.m_22115_();
            attackDamageAttr.m_22118_(new AttributeModifier(ATTACK_CONFIG_MODIFIER_UUID, "Attack config multiplier", difference, Operation.ADDITION));
         }
      }
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22266_(Attributes.f_22281_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(STRAFING, false);
   }

   public void setStrafing(boolean strafing) {
      this.f_19804_.m_135381_(STRAFING, strafing);
   }

   public boolean isStrafing() {
      return (Boolean)this.f_19804_.m_135370_(STRAFING);
   }

   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return null;
   }

   protected ConfigHandler.CombatConfig getCombatConfig() {
      return null;
   }

   public static boolean spawnPredicate(EntityType type, LevelAccessor world, MobSpawnType reason, BlockPos spawnPos, RandomSource rand) {
      ConfigHandler.SpawnConfig spawnConfig = SpawnHandler.spawnConfigs.get(type);
      if (spawnConfig != null) {
         if (rand.m_188500_() > (Double)spawnConfig.extraRarity.get()) {
            return false;
         }

         List<? extends String> dimensionNames = (List<? extends String>)spawnConfig.dimensions.get();
         ResourceLocation currDimensionName = ((ServerLevel)world).m_46472_().m_135782_();
         if (!dimensionNames.contains(currDimensionName.toString())) {
            return false;
         }

         float heightMax = (float)((Integer)spawnConfig.heightMax.get()).intValue();
         float heightMin = (float)((Integer)spawnConfig.heightMin.get()).intValue();
         if ((float)spawnPos.m_123342_() > heightMax && heightMax >= -64.0F) {
            return false;
         }

         if ((float)spawnPos.m_123342_() < heightMin && heightMin >= -64.0F) {
            return false;
         }

         if ((Boolean)spawnConfig.needsDarkness.get() && !Monster.m_219009_((ServerLevelAccessor)world, spawnPos, rand)) {
            return false;
         }

         BlockState block = world.m_8055_(spawnPos.m_7495_());
         ResourceLocation blockName = ForgeRegistries.BLOCKS.getKey(block.m_60734_());
         List<? extends String> allowedBlocks = (List<? extends String>)spawnConfig.allowedBlocks.get();
         List<? extends String> allowedBlockTags = (List<? extends String>)spawnConfig.allowedBlockTags.get();
         if (blockName == null) {
            return false;
         }

         if (!allowedBlocks.isEmpty() && !allowedBlocks.contains(blockName.toString()) && !allowedBlocks.contains(blockName.m_135815_())) {
            return false;
         }

         if (!allowedBlockTags.isEmpty() && !isBlockTagAllowed(allowedBlockTags, block)) {
            return false;
         }

         if ((Boolean)spawnConfig.needsSeeSky.get() && !world.m_46861_(spawnPos)) {
            return false;
         }

         if ((Boolean)spawnConfig.needsCantSeeSky.get() && world.m_46861_(spawnPos)) {
            return false;
         }

         List<? extends String> avoidStructures = (List<? extends String>)spawnConfig.avoidStructures.get();
         Registry<StructureSet> structureSetRegistry = world.m_5962_().m_175515_(Registry.f_211073_);
         ServerLevel serverLevel = (ServerLevel)world;
         ChunkGenerator generator = serverLevel.m_7726_().m_8481_();
         long seed = serverLevel.m_7328_();
         ChunkPos chunkPos = new ChunkPos(spawnPos);

         for (String structureName : avoidStructures) {
            Optional<StructureSet> structureSetOptional = structureSetRegistry.m_6612_(new ResourceLocation(structureName));
            if (!structureSetOptional.isEmpty()) {
               Optional<ResourceKey<StructureSet>> resourceKeyOptional = structureSetRegistry.m_7854_(structureSetOptional.get());
               if (!resourceKeyOptional.isEmpty()
                  && generator.m_223141_(
                     BuiltinRegistries.f_211084_.m_206081_(resourceKeyOptional.get()),
                     serverLevel.m_7726_().m_214994_(),
                     seed,
                     chunkPos.f_45578_,
                     chunkPos.f_45579_,
                     3
                  )) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private static boolean isBlockTagAllowed(List<? extends String> allowedBlockTags, BlockState block) {
      for (String allowedBlockTag : allowedBlockTags) {
         TagKey<Block> tagKey = TagKey.m_203882_(Registry.f_122901_, new ResourceLocation(allowedBlockTag));
         if (block.m_204336_(tagKey)) {
            return true;
         }
      }

      return false;
   }

   protected boolean isWithinDistance(BlockPos pos, int distance) {
      return pos.m_123314_(this.m_20183_(), (double)distance);
   }

   public void m_8119_() {
      this.prevPrevOnGround = this.prevOnGround;
      this.prevOnGround = this.f_19861_;
      super.m_8119_();
      this.frame++;
      if (this.f_19797_ % 4 == 0) {
         this.bossInfo.update();
      }

      if (this.m_5448_() != null) {
         this.targetDistance = this.m_20270_(this.m_5448_()) - this.m_5448_().m_20205_() / 2.0F;
         this.targetAngle = (float)this.getAngleBetweenEntities(this, this.m_5448_());
      }

      this.willLandSoon = !this.f_19861_ && this.f_19853_.m_45772_(this.m_20191_().m_82383_(this.m_20184_()));
      if (!this.f_19853_.f_46443_ && this.getBossMusic() != null) {
         if (this.canPlayMusic()) {
            this.f_19853_.m_7605_(this, (byte)67);
         } else {
            this.f_19853_.m_7605_(this, (byte)68);
         }
      }
   }

   protected boolean canPlayMusic() {
      return !this.m_20067_() && this.m_5448_() instanceof Player;
   }

   public boolean canPlayerHearMusic(Player player) {
      return player != null && this.m_6779_(player) && this.m_20270_(player) < 2500.0F;
   }

   protected void m_8024_() {
      super.m_8024_();
   }

   public void readSpawnData(FriendlyByteBuf buf) {
      this.f_19859_ = this.m_146908_();
      this.f_20884_ = this.f_20883_ = this.f_20886_ = this.f_20885_;
   }

   public boolean m_7327_(Entity entityIn) {
      return this.doHurtTarget(entityIn, 1.0F, 1.0F);
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag
   ) {
      return super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   public boolean doHurtTarget(Entity entityIn, float damageMultiplier, float applyKnockbackMultiplier) {
      return this.doHurtTarget(entityIn, damageMultiplier, applyKnockbackMultiplier, false);
   }

   public boolean doHurtTarget(Entity entityIn, float damageMultiplier, float applyKnockbackMultiplier, boolean canDisableShield) {
      float f = (float)this.m_21051_(Attributes.f_22281_).m_22135_() * damageMultiplier;
      float f1 = (float)this.m_21051_(Attributes.f_22282_).m_22135_();
      if (entityIn instanceof LivingEntity) {
         f += EnchantmentHelper.m_44833_(this.m_21205_(), ((LivingEntity)entityIn).m_6336_());
         f1 += (float)EnchantmentHelper.m_44894_(this);
      }

      int i = EnchantmentHelper.m_44914_(this);
      if (i > 0) {
         entityIn.m_20254_(i * 4);
      }

      boolean flag = entityIn.m_6469_(DamageSource.m_19370_(this), f);
      if (flag) {
         entityIn.m_20256_(entityIn.m_20184_().m_82542_((double)applyKnockbackMultiplier, 1.0, (double)applyKnockbackMultiplier));
         if (f1 > 0.0F && entityIn instanceof LivingEntity) {
            ((LivingEntity)entityIn)
               .m_147240_(
                  (double)(f1 * 0.5F),
                  (double)Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0)),
                  (double)(-Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0)))
               );
            this.m_20256_(this.m_20184_().m_82542_(0.6, 1.0, 0.6));
         }

         if (entityIn instanceof Player player && canDisableShield) {
            this.maybeDisableShield(player, player.m_6117_() ? player.m_21211_() : ItemStack.f_41583_);
         }

         this.m_19970_(this, entityIn);
         this.m_21335_(entityIn);
      }

      return flag;
   }

   private void maybeDisableShield(Player player, ItemStack itemStackBlock) {
      if (!itemStackBlock.m_41619_() && itemStackBlock.m_150930_(Items.f_42740_)) {
         float f = 0.25F + (float)EnchantmentHelper.m_44926_(this) * 0.05F;
         if (this.f_19796_.m_188501_() < f) {
            player.m_36335_().m_41524_(Items.f_42740_, 100);
            this.f_19853_.m_7605_(player, (byte)30);
         }
      }
   }

   public float getHealthRatio() {
      return this.m_21223_() / this.m_21233_();
   }

   public double getAngleBetweenEntities(Entity first, Entity second) {
      return Math.atan2(second.m_20189_() - first.m_20189_(), second.m_20185_() - first.m_20185_()) * (180.0 / Math.PI) + 90.0;
   }

   public double getDotProductBodyFacingEntity(Entity second) {
      Vec3 vecBetween = second.m_20182_().m_82546_(this.m_20182_());
      vecBetween = vecBetween.m_82541_();
      return vecBetween.m_82526_(Vec3.m_82498_(0.0F, this.f_20883_).m_82541_());
   }

   public List<Player> getPlayersNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> nearbyEntities = this.f_19853_.m_45933_(this, this.m_20191_().m_82377_(distanceX, distanceY, distanceZ));
      return nearbyEntities.stream()
         .filter(
            entityNeighbor -> entityNeighbor instanceof Player && (double)this.m_20270_(entityNeighbor) <= radius + (double)(entityNeighbor.m_20205_() / 2.0F)
         )
         .map(entityNeighbor -> (Player)entityNeighbor)
         .collect(Collectors.toList());
   }

   public List<LivingEntity> getAttackableEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> nearbyEntities = this.f_19853_.m_45933_(this, this.m_20191_().m_82377_(distanceX, distanceY, distanceZ));
      return nearbyEntities.stream()
         .filter(
            entityNeighbor -> entityNeighbor instanceof LivingEntity
                  && ((LivingEntity)entityNeighbor).m_5789_()
                  && (!(entityNeighbor instanceof Player) || !((Player)entityNeighbor).m_7500_())
                  && (double)this.m_20270_(entityNeighbor) <= radius + (double)(entityNeighbor.m_20205_() / 2.0F)
         )
         .map(entityNeighbor -> (LivingEntity)entityNeighbor)
         .collect(Collectors.toList());
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double r) {
      return this.f_19853_
         .m_6443_(entityClass, this.m_20191_().m_82377_(r, r, r), e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F));
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.f_19853_
         .m_6443_(
            entityClass,
            this.m_20191_().m_82377_(dX, dY, dZ),
            e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F) && e.m_20186_() <= this.m_20186_() + dY
         );
   }

   protected void m_6153_() {
      this.f_20919_++;
      int deathDuration = this.getDeathDuration();
      if (this.f_20919_ >= deathDuration && !this.f_19853_.m_5776_()) {
         this.f_20888_ = this.killDataAttackingPlayer;
         this.f_20889_ = this.killDataRecentlyHit;
         if (this.dropAfterDeathAnim && this.killDataCause != null) {
            this.m_6668_(this.killDataCause);
         }

         this.f_19853_.m_7605_(this, (byte)60);
         this.m_142687_(RemovalReason.KILLED);
      }
   }

   protected abstract int getDeathDuration();

   protected void m_6668_(DamageSource source) {
      if (!this.dropAfterDeathAnim || this.f_20919_ > 0) {
         super.m_6668_(source);
      }
   }

   public void m_6667_(DamageSource cause) {
      if (!this.f_20890_) {
         this.killDataCause = cause;
         this.killDataRecentlyHit = this.f_20889_;
         this.killDataAttackingPlayer = this.f_20888_;
      }

      super.m_6667_(cause);
      if (!this.m_213877_()) {
         this.bossInfo.update();
      }
   }

   protected void addIntermittentAnimation(IntermittentAnimation animation) {
      animation.setID((byte)this.intermittentAnimations.size());
      this.intermittentAnimations.add(animation);
   }

   public void m_7822_(byte id) {
      if (id >= 4 && id - 4 < this.intermittentAnimations.size()) {
         this.intermittentAnimations.get(id - 4).start();
      } else if (id == 67) {
         BossMusicPlayer.playBossMusic(this);
      } else if (id == 68) {
         BossMusicPlayer.stopBossMusic(this);
      } else {
         super.m_7822_(id);
      }
   }

   @Override
   public byte getOffsetEntityState() {
      return 4;
   }

   public Vec3 circleEntityPosition(Entity target, float radius, float speed, boolean direction, int circleFrame, float offset) {
      int directionInt = direction ? 1 : -1;
      double t = (double)(directionInt * circleFrame) * 0.5 * (double)speed / (double)radius + (double)offset;
      return target.m_20182_().m_82520_((double)radius * Math.cos(t), 0.0, (double)radius * Math.sin(t));
   }

   protected void repelEntities(float x, float y, float z, float radius) {
      for (Entity entity : this.getEntityLivingBaseNearby((double)x, (double)y, (double)z, (double)radius)) {
         if (entity.m_6087_() && !entity.f_19794_) {
            double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
            entity.m_20334_(-0.1 * Math.cos(angle), entity.m_20184_().f_82480_, -0.1 * Math.sin(angle));
         }
      }
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   public void m_6593_(Component name) {
      super.m_6593_(name);
      this.bossInfo.m_6456_(this.m_5446_());
   }

   public boolean hasBossBar() {
      return false;
   }

   public boolean resetHealthOnPlayerRespawn() {
      return false;
   }

   protected BossBarColor bossBarColor() {
      return BossBarColor.PURPLE;
   }

   @OnlyIn(Dist.CLIENT)
   public void setSocketPosArray(int index, Vec3 pos) {
      if (this.socketPosArray != null && this.socketPosArray.length > index) {
         this.socketPosArray[index] = pos;
      }
   }

   public boolean canBePushedByEntity(Entity entity) {
      return true;
   }

   public void m_7334_(Entity entityIn) {
      if (!this.m_5803_() && !this.m_20365_(entityIn) && !entityIn.f_19794_ && !this.f_19794_) {
         double d0 = entityIn.m_20185_() - this.m_20185_();
         double d1 = entityIn.m_20189_() - this.m_20189_();
         double d2 = Mth.m_14005_(d0, d1);
         if (d2 >= 0.01F) {
            d2 = Math.sqrt(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
               d3 = 1.0;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05F;
            d1 *= 0.05F;
            if (!this.m_20160_() && this.canBePushedByEntity(entityIn)) {
               this.m_5997_(-d0, 0.0, -d1);
            }

            if (!entityIn.m_20160_()) {
               entityIn.m_5997_(d0, 0.0, d1);
            }
         }
      }
   }

   public SoundEvent getBossMusic() {
      return null;
   }
}
