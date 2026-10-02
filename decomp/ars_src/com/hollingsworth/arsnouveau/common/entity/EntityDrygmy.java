package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.client.IVariantColorProvider;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.DrygmyTile;
import com.hollingsworth.arsnouveau.common.compat.PatchouliHandler;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.common.entity.goal.UntamedFindItemGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.drygmy.CollectEssenceGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig.FollowMobGoalBackoff;
import com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig.FollowPlayerGoal;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags.Items;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class EntityDrygmy extends PathfinderMob implements IAnimatable, ITooltipProvider, IDispellable, IVariantColorProvider<EntityDrygmy> {
   public static final EntityDataAccessor<Boolean> CHANNELING = SynchedEntityData.m_135353_(EntityDrygmy.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.m_135353_(EntityDrygmy.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> BEING_TAMED = SynchedEntityData.m_135353_(EntityDrygmy.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> HOLDING_ESSENCE = SynchedEntityData.m_135353_(EntityDrygmy.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Integer> CHANNELING_ENTITY = SynchedEntityData.m_135353_(EntityDrygmy.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<String> COLOR = SynchedEntityData.m_135353_(EntityDrygmy.class, EntityDataSerializers.f_135030_);
   public int channelCooldown;
   private boolean setBehaviors;
   public BlockPos homePos;
   public int tamingTime;
   public static String[] COLORS = new String[]{"brown", "cyan", "orange"};
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public int m_213860_() {
      return 0;
   }

   public EntityDrygmy(EntityType<? extends PathfinderMob> p_i48575_1_, Level p_i48575_2_) {
      super(p_i48575_1_, p_i48575_2_);
      this.addGoalsAfterConstructor();
   }

   public EntityDrygmy(Level world, boolean tamed) {
      super((EntityType)ModEntities.ENTITY_DRYGMY.get(), world);
      this.setTamed(tamed);
      this.addGoalsAfterConstructor();
   }

   @Nullable
   public DrygmyTile getHome() {
      return this.homePos != null && this.f_19853_.m_7702_(this.homePos) instanceof DrygmyTile ? (DrygmyTile)this.f_19853_.m_7702_(this.homePos) : null;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return SummonUtil.canSummonTakeDamage(pSource) && super.m_6469_(pSource, pAmount);
   }

   public void m_6667_(DamageSource source) {
      if (!this.f_19853_.f_46443_ && this.isTamed()) {
         ItemStack stack = new ItemStack(ItemsRegistry.DRYGMY_CHARM);
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
      }

      super.m_6667_(source);
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!this.f_19853_.f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(hand);
         if (player.m_21205_().m_204117_(Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color != null && !((String)this.f_19804_.m_135370_(COLOR)).equals(color.m_41065_()) && Arrays.asList(COLORS).contains(color.m_41065_())) {
               this.f_19804_.m_135381_(COLOR, color.m_41065_());
               player.m_21205_().m_41774_(1);
               return InteractionResult.SUCCESS;
            } else {
               return InteractionResult.SUCCESS;
            }
         } else {
            return super.m_6071_(player, hand);
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public void m_8119_() {
      super.m_8119_();
      SummonUtil.healOverTime(this);
      if (!this.f_19853_.f_46443_ && this.channelCooldown > 0) {
         this.channelCooldown--;
      }

      if (!this.f_19853_.f_46443_
         && this.f_19853_.m_46467_() % 60L == 0L
         && this.isTamed()
         && this.homePos != null
         && !(this.f_19853_.m_7702_(this.homePos) instanceof DrygmyTile)) {
         this.m_6469_(DamageSource.m_19344_(ANFakePlayer.getPlayer((ServerLevel)this.f_19853_)), 99.0F);
      } else {
         if (this.f_19853_.f_46443_ && this.isChanneling() && this.getChannelEntity() != -1) {
            Entity entity = this.f_19853_.m_6815_(this.getChannelEntity());
            if (entity == null || entity.m_213877_()) {
               return;
            }

            Vec3 vec = entity.f_19825_;
            this.f_19853_
               .m_7106_(
                  GlowParticleData.createData(new ParticleColor(50, 255, 20)),
                  (double)((float)vec.f_82479_) - Math.sin((double)ClientInfo.ticksInGame / 8.0),
                  (double)((float)vec.f_82480_) + Math.sin((double)ClientInfo.ticksInGame / 5.0) / 8.0 + 0.5,
                  (double)((float)vec.f_82481_) - Math.cos((double)ClientInfo.ticksInGame / 8.0),
                  0.0,
                  0.0,
                  0.0
               );
         }

         if (!this.isTamed() && !(Boolean)this.f_19804_.m_135370_(BEING_TAMED) && this.f_19853_.m_46467_() % 40L == 0L) {
            for (ItemEntity itementity : this.f_19853_.m_45976_(ItemEntity.class, this.m_20191_().m_82400_(1.0))) {
               this.m_7581_(itementity);
            }
         }

         if (!this.isTamed() && (Boolean)this.f_19804_.m_135370_(BEING_TAMED)) {
            this.tamingTime++;
            if (this.tamingTime % 20 == 0 && !this.f_19853_.m_5776_()) {
               Networking.sendToNearby(this.f_19853_, this, new PacketANEffect(PacketANEffect.EffectType.TIMED_HELIX, this.m_20183_()));
            }

            if (this.tamingTime > 60 && !this.f_19853_.f_46443_) {
               ItemStack stack = new ItemStack(ItemsRegistry.DRYGMY_SHARD, 1 + this.f_19853_.f_46441_.m_188503_(2));
               this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), stack));
               this.m_142687_(RemovalReason.DISCARDED);
               this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12052_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
         }
      }
   }

   public boolean m_6785_(double p_213397_1_) {
      return false;
   }

   private PlayState animationPredicate(AnimationEvent<?> event) {
      if (!this.isChanneling() && !(Boolean)this.f_19804_.m_135370_(BEING_TAMED) && (!this.f_19853_.f_46443_ || !PatchouliHandler.isPatchouliWorld())) {
         if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("channel"));
         return PlayState.CONTINUE;
      }
   }

   protected void addGoalsAfterConstructor() {
      if (!this.f_19853_.m_5776_()) {
         for (WrappedGoal goal : this.getGoals()) {
            this.f_21345_.m_25352_(goal.m_26012_(), goal.m_26015_());
         }
      }
   }

   public List<WrappedGoal> getGoals() {
      return this.f_19804_.m_135370_(TAMED) ? this.getTamedGoals() : this.getUntamedGoals();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(CHANNELING, false);
      this.f_19804_.m_135372_(TAMED, false);
      this.f_19804_.m_135372_(HOLDING_ESSENCE, false);
      this.f_19804_.m_135372_(CHANNELING_ENTITY, -1);
      this.f_19804_.m_135372_(BEING_TAMED, false);
      this.f_19804_.m_135372_(COLOR, "brown");
   }

   public boolean holdingEssence() {
      return (Boolean)this.f_19804_.m_135370_(HOLDING_ESSENCE);
   }

   public void setHoldingEssence(boolean holdingEssence) {
      this.f_19804_.m_135381_(HOLDING_ESSENCE, holdingEssence);
   }

   public boolean isTamed() {
      return (Boolean)this.f_19804_.m_135370_(TAMED);
   }

   public void setTamed(boolean tamed) {
      this.f_19804_.m_135381_(TAMED, tamed);
   }

   public boolean isChanneling() {
      return (Boolean)this.f_19804_.m_135370_(CHANNELING);
   }

   public void setChanneling(boolean channeling) {
      this.f_19804_.m_135381_(CHANNELING, channeling);
   }

   public int getChannelEntity() {
      return (Integer)this.f_19804_.m_135370_(CHANNELING_ENTITY);
   }

   public void setChannelingEntity(int entityID) {
      this.f_19804_.m_135381_(CHANNELING_ENTITY, entityID);
   }

   protected void m_8099_() {
   }

   public List<WrappedGoal> getTamedGoals() {
      List<WrappedGoal> list = new ArrayList<>();
      list.add(new WrappedGoal(3, new RandomLookAroundGoal(this)));
      list.add(new WrappedGoal(2, new CollectEssenceGoal(this)));
      list.add(new WrappedGoal(0, new FloatGoal(this)));
      return list;
   }

   public List<WrappedGoal> getUntamedGoals() {
      List<WrappedGoal> list = new ArrayList<>();
      list.add(new WrappedGoal(3, new FollowMobGoalBackoff(this, 1.0, 3.0F, 7.0F, 0.5F)));
      list.add(new WrappedGoal(5, new FollowPlayerGoal(this, 1.0, 3.0F, 7.0F)));
      list.add(new WrappedGoal(2, new RandomLookAroundGoal(this)));
      list.add(new WrappedGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0)));
      list.add(new WrappedGoal(0, new FloatGoal(this)));
      list.add(
         new WrappedGoal(
            1,
            new UntamedFindItemGoal(
               this,
               () -> !this.isTamed() && !(Boolean)this.f_19804_.m_135370_(BEING_TAMED),
               itemEntity -> !itemEntity.m_32063_() && itemEntity.m_6084_() && itemEntity.m_32055_().m_204117_(ItemTagProvider.WILDEN_DROP_TAG)
            )
         )
      );
      return list;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController<>(this, "walkController", 20.0F, this::animationPredicate));
      animationData.addAnimationController(new AnimationController<>(this, "idleController", 20.0F, this::idlePredicate));
   }

   protected void m_7581_(ItemEntity itemEntity) {
      if (!this.isTamed() && !(Boolean)this.f_19804_.m_135370_(BEING_TAMED) && itemEntity.m_32055_().m_204117_(ItemTagProvider.WILDEN_DROP_TAG)) {
         this.f_19804_.m_135381_(BEING_TAMED, true);
         itemEntity.m_32055_().m_41774_(1);
         this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12019_, this.m_5720_(), 1.0F, 1.0F);
      }
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      if (this.m_213877_()) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_ && this.isTamed()) {
            ItemStack stack = new ItemStack(ItemsRegistry.DRYGMY_CHARM);
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
         }

         return this.isTamed();
      }
   }

   private PlayState idlePredicate(AnimationEvent<?> event) {
      return PlayState.CONTINUE;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      NBTUtil.storeBlockPos(tag, "home", this.homePos);
      tag.m_128379_("tamed", (Boolean)this.f_19804_.m_135370_(TAMED));
      tag.m_128405_("cooldown", this.channelCooldown);
      tag.m_128405_("taming", this.tamingTime);
      tag.m_128379_("beingTamed", (Boolean)this.f_19804_.m_135370_(BEING_TAMED));
      tag.m_128359_("color", (String)this.f_19804_.m_135370_(COLOR));
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (NBTUtil.hasBlockPos(tag, "home")) {
         this.homePos = NBTUtil.getBlockPos(tag, "home");
      }

      this.setTamed(tag.m_128471_("tamed"));
      if (!this.setBehaviors) {
         this.tryResetGoals();
         this.setBehaviors = true;
      }

      this.channelCooldown = tag.m_128451_("cooldown");
      this.tamingTime = tag.m_128451_("taming");
      this.f_19804_.m_135381_(BEING_TAMED, tag.m_128471_("beingTamed"));
      if (tag.m_128441_("color")) {
         this.f_19804_.m_135381_(COLOR, tag.m_128461_("color"));
      }
   }

   public void tryResetGoals() {
      this.f_21345_.f_25345_ = new LinkedHashSet();
      this.addGoalsAfterConstructor();
   }

   public ResourceLocation getTexture(EntityDrygmy entity) {
      String color = this.getColor(entity).toLowerCase();
      if (color.isEmpty()) {
         color = "brown";
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/drygmy_" + color + ".png");
   }

   public String getColor(EntityDrygmy entity) {
      return (String)this.m_20088_().m_135370_(COLOR);
   }

   public void setColor(String color, EntityDrygmy entity) {
      this.m_20088_().m_135381_(COLOR, color);
   }
}
