package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.client.IVariantColorProvider;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.util.LevelEntityMap;
import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.WhirlisprigTile;
import com.hollingsworth.arsnouveau.common.entity.goal.GoBackHomeGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig.BonemealGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig.FollowMobGoalBackoff;
import com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig.FollowPlayerGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig.InspectPlantGoal;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags.Items;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class Whirlisprig extends AbstractFlyingCreature implements IAnimatable, ITooltipProvider, IDispellable, IVariantColorProvider<Whirlisprig> {
   AnimationFactory manager = GeckoLibUtil.createFactory(this);
   public static LevelEntityMap WHIRLI_MAP = new LevelEntityMap();
   public int timeSinceBonemeal = 0;
   public static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.m_135353_(Whirlisprig.class, EntityDataSerializers.f_135035_);
   public int tamingTime = 0;
   public boolean droppingShards;
   public static final EntityDataAccessor<Integer> MOOD_SCORE = SynchedEntityData.m_135353_(Whirlisprig.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<String> COLOR = SynchedEntityData.m_135353_(Whirlisprig.class, EntityDataSerializers.f_135030_);
   public int diversityScore;
   public BlockPos flowerPos;
   public int timeSinceGen;
   private boolean setBehaviors;

   private PlayState idlePredicate(AnimationEvent<?> event) {
      if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController<>(this, "idleController", 1.0F, this::idlePredicate));
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   public int m_213860_() {
      return 0;
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (player.m_20193_().f_46443_) {
         return super.m_6071_(player, hand);
      } else {
         ItemStack stack = player.m_21120_(hand);
         if (stack.m_41720_() == ItemsRegistry.DENY_ITEM_SCROLL.m_5456_() && this.getTile() != null) {
            ItemScroll.ItemScrollData scrollData = new ItemScroll.ItemScrollData(stack);
            this.getTile().ignoreItems.addAll(scrollData.getItems());
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.whirlisprig.ignore"));
         }

         return super.m_6071_(player, hand);
      }
   }

   public String getColor(Whirlisprig entity) {
      return (String)this.f_19804_.m_135370_(COLOR);
   }

   public void setColor(String color, Whirlisprig entity) {
      this.f_19804_.m_135381_(COLOR, color);
   }

   public static String getColorFromStack(ItemStack stack) {
      if (stack.m_204117_(Items.DYES)) {
         if (stack.m_204117_(Items.DYES_GREEN)) {
            return "summer";
         }

         if (stack.m_204117_(Items.DYES_ORANGE)) {
            return "autumn";
         }

         if (stack.m_204117_(Items.DYES_YELLOW)) {
            return "spring";
         }

         if (stack.m_204117_(Items.DYES_WHITE)) {
            return "winter";
         }
      }

      return null;
   }

   public InteractionResult m_7111_(Player player, Vec3 vec, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && !player.m_20193_().f_46443_ && (Boolean)this.f_19804_.m_135370_(TAMED)) {
         ItemStack stack = player.m_21120_(hand);
         String color = getColorFromStack(stack);
         if (color != null && !this.getColor(this).equals(color)) {
            this.f_19804_.m_135381_(COLOR, color);
            stack.m_41774_(1);
            return InteractionResult.SUCCESS;
         } else if (stack.m_41619_()) {
            int moodScore = (Integer)this.f_19804_.m_135370_(MOOD_SCORE);
            if (moodScore < 250) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.unhappy"));
            } else if (moodScore <= 500) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.content"));
            } else if (moodScore <= 750) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.happy"));
            } else if (moodScore < 1000) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.very_happy"));
            } else {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.extremely_happy"));
            }

            int numDrops = this.diversityScore / 2;
            if (numDrops <= 5) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.okay_diversity"));
            } else if (numDrops <= 10) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.diverse_enough"));
            } else if (numDrops <= 20) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.very_diverse"));
            } else {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.extremely_diverse"));
            }

            WhirlisprigTile tile = this.getTile();
            if (tile.ignoreItems != null && !tile.ignoreItems.isEmpty()) {
               StringBuilder status = new StringBuilder();
               status.append(Component.m_237115_("ars_nouveau.whirlisprig.ignore_list").getString());

               for (ItemStack i : tile.ignoreItems) {
                  status.append(i.m_41786_().getString()).append(" ");
               }

               PortUtil.sendMessage(player, Component.m_237113_(status.toString()));
            }

            return InteractionResult.SUCCESS;
         } else if (!(stack.m_41720_() instanceof BlockItem)) {
            return InteractionResult.PASS;
         } else {
            BlockState state = ((BlockItem)stack.m_41720_()).m_40614_().m_49966_();
            int score = WhirlisprigTile.getScore(state);
            if (score > 0
               && this.getTile() != null
               && this.getTile().scoreMap != null
               && this.getTile().scoreMap.get(state) != null
               && this.getTile().scoreMap.get(state) >= 50) {
               PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.toomuch"));
               return InteractionResult.SUCCESS;
            } else {
               if (score == 0) {
                  PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.notinterested"));
               }

               if (score == 1) {
                  PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.likes"));
               }

               if (score == 2) {
                  PortUtil.sendMessage(player, Component.m_237115_("whirlisprig.excited"));
               }

               return InteractionResult.SUCCESS;
            }
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   public Whirlisprig(EntityType<? extends AbstractFlyingCreature> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
      this.addGoalsAfterConstructor();
   }

   public Whirlisprig(Level world, boolean isTamed, BlockPos pos) {
      super((EntityType<? extends PathfinderMob>)ModEntities.WHIRLISPRIG_TYPE.get(), world);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
      this.f_19804_.m_135381_(TAMED, isTamed);
      this.flowerPos = pos;
      this.addGoalsAfterConstructor();
   }

   public void m_142467_(RemovalReason pRemovalReason) {
      super.m_142467_(pRemovalReason);
      WHIRLI_MAP.removeEntity(this.f_19853_, this.m_20148_());
   }

   public void m_8119_() {
      super.m_8119_();
      SummonUtil.healOverTime(this);
      if (!this.f_19853_.f_46443_) {
         if (!this.m_213877_() && !this.isTamed()) {
            WHIRLI_MAP.addEntity(this.f_19853_, this.m_20148_());
         }

         if (this.f_19853_.m_46467_() % 20L == 0L && this.m_20183_().m_123342_() < this.f_19853_.m_141937_()) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         this.timeSinceBonemeal++;
         this.timeSinceGen++;
         if (this.f_19853_.m_46467_() % 20L == 0L && this.flowerPos != null && this.isTamed() && this.getTile() != null) {
            this.f_19804_.m_135381_(MOOD_SCORE, this.getTile().moodScore);
            this.diversityScore = this.getTile().diversityScore;
         }
      }

      if (!this.f_19853_.f_46443_
         && this.f_19853_.m_46467_() % 60L == 0L
         && this.isTamed()
         && this.flowerPos != null
         && !(this.f_19853_.m_7702_(this.flowerPos) instanceof WhirlisprigTile)) {
         this.m_6469_(DamageSource.m_19344_(ANFakePlayer.getPlayer((ServerLevel)this.f_19853_)), 99.0F);
      } else {
         if (this.droppingShards) {
            this.tamingTime++;
            if (this.tamingTime % 20 == 0 && !this.f_19853_.m_5776_()) {
               Networking.sendToNearby(this.f_19853_, this, new PacketANEffect(PacketANEffect.EffectType.TIMED_HELIX, this.m_20183_()));
            }

            if (this.tamingTime > 60 && !this.f_19853_.f_46443_) {
               ItemStack stack = new ItemStack(ItemsRegistry.WHIRLISPRIG_SHARDS, 1 + this.f_19853_.f_46441_.m_188503_(1));
               this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), stack));
               this.m_142687_(RemovalReason.DISCARDED);
               this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12052_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else if (this.tamingTime > 55 && this.f_19853_.f_46443_) {
               for (int i = 0; i < 10; i++) {
                  double d0 = this.m_20185_();
                  double d1 = this.m_20186_() + 0.1;
                  double d2 = this.m_20189_();
                  this.f_19853_
                     .m_7106_(
                        ParticleTypes.f_123810_,
                        d0,
                        d1,
                        d2,
                        ((double)(this.f_19853_.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0,
                        ((double)(this.f_19853_.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0,
                        ((double)(this.f_19853_.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0
                     );
               }
            }
         }
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

   public boolean isTamed() {
      return (Boolean)this.f_19804_.m_135370_(TAMED);
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return SummonUtil.canSummonTakeDamage(pSource) && super.m_6469_(pSource, pAmount);
   }

   public void m_6667_(DamageSource source) {
      if (!this.f_19853_.f_46443_ && this.isTamed()) {
         ItemStack stack = new ItemStack(ItemsRegistry.WHIRLISPRIG_CHARM);
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
      }

      super.m_6667_(source);
   }

   public List<WrappedGoal> getTamedGoals() {
      List<WrappedGoal> list = new ArrayList<>();
      list.add(new WrappedGoal(3, new RandomLookAroundGoal(this)));
      list.add(new WrappedGoal(2, new BonemealGoal(this, () -> this.flowerPos, 10)));
      list.add(new WrappedGoal(2, new InspectPlantGoal(this, () -> this.flowerPos, 15)));
      list.add(new WrappedGoal(1, new GoBackHomeGoal(this, () -> this.flowerPos, 20)));
      list.add(new WrappedGoal(0, new FloatGoal(this)));
      return list;
   }

   public List<WrappedGoal> getUntamedGoals() {
      List<WrappedGoal> list = new ArrayList<>();
      list.add(new WrappedGoal(3, new FollowMobGoalBackoff(this, 1.0, 3.0F, 7.0F, 0.5F)));
      list.add(new WrappedGoal(5, new FollowPlayerGoal(this, 1.0, 3.0F, 7.0F)));
      list.add(new WrappedGoal(2, new RandomLookAroundGoal(this)));
      list.add(new WrappedGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0)));
      list.add(new WrappedGoal(1, new BonemealGoal(this)));
      list.add(new WrappedGoal(0, new FloatGoal(this)));
      return list;
   }

   public WhirlisprigTile getTile() {
      return this.flowerPos != null && this.f_19853_.m_7702_(this.flowerPos) instanceof WhirlisprigTile
         ? (WhirlisprigTile)this.f_19853_.m_7702_(this.flowerPos)
         : null;
   }

   protected void m_8099_() {
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if ((Boolean)this.f_19804_.m_135370_(TAMED)) {
         int mood = (Integer)this.f_19804_.m_135370_(MOOD_SCORE);
         String moodStr = Component.m_237115_("ars_nouveau.whirlisprig.tooltip_unhappy").getString();
         if (mood >= 1000) {
            moodStr = Component.m_237115_("ars_nouveau.whirlisprig.tooltip_extremely_happy").getString();
         } else if (mood >= 750) {
            moodStr = Component.m_237115_("ars_nouveau.whirlisprig.tooltip_very_happy").getString();
         } else if (mood >= 500) {
            moodStr = Component.m_237115_("ars_nouveau.whirlisprig.tooltip_happy").getString();
         } else if (mood >= 250) {
            moodStr = Component.m_237115_("ars_nouveau.whirlisprig.tooltip_content").getString();
         }

         tooltip.add(Component.m_237113_(Component.m_237115_("ars_nouveau.whirlisprig.tooltip_mood").getString() + moodStr));
      }
   }

   public boolean m_6785_(double p_213397_1_) {
      return false;
   }

   public static Builder attributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22280_, Attributes.f_22280_.m_22082_())
         .m_22268_(Attributes.f_22276_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.2);
   }

   @NotNull
   protected PathNavigation m_6037_(Level world) {
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, world);
      flyingpathnavigator.m_26440_(false);
      flyingpathnavigator.m_7008_(true);
      flyingpathnavigator.m_26443_(true);
      return flyingpathnavigator;
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128441_("summoner_x")) {
         this.flowerPos = new BlockPos(tag.m_128451_("summoner_x"), tag.m_128451_("summoner_y"), tag.m_128451_("summoner_z"));
      }

      this.timeSinceBonemeal = tag.m_128451_("bonemeal");
      this.f_19804_.m_135381_(TAMED, tag.m_128471_("tamed"));
      this.f_19804_.m_135381_(MOOD_SCORE, tag.m_128451_("score"));
      if (!this.setBehaviors) {
         this.tryResetGoals();
         this.setBehaviors = true;
      }

      this.f_19804_.m_135381_(COLOR, tag.m_128461_("color"));
      this.timeSinceGen = tag.m_128451_("genTime");
   }

   public void tryResetGoals() {
      this.f_21345_.f_25345_ = new LinkedHashSet();
      this.addGoalsAfterConstructor();
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      if (this.flowerPos != null) {
         tag.m_128405_("summoner_x", this.flowerPos.m_123341_());
         tag.m_128405_("summoner_y", this.flowerPos.m_123342_());
         tag.m_128405_("summoner_z", this.flowerPos.m_123343_());
      }

      tag.m_128405_("bonemeal", this.timeSinceBonemeal);
      tag.m_128379_("tamed", (Boolean)this.f_19804_.m_135370_(TAMED));
      tag.m_128405_("score", (Integer)this.f_19804_.m_135370_(MOOD_SCORE));
      tag.m_128359_("color", (String)this.f_19804_.m_135370_(COLOR));
      tag.m_128405_("genTime", this.timeSinceGen);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(MOOD_SCORE, 0);
      this.f_19804_.m_135372_(TAMED, false);
      this.f_19804_.m_135372_(COLOR, "summer");
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      if (this.m_213877_()) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_ && this.isTamed()) {
            ItemStack stack = new ItemStack(ItemsRegistry.WHIRLISPRIG_CHARM);
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
         }

         return this.isTamed();
      }
   }

   public ResourceLocation getTexture(Whirlisprig entity) {
      return new ResourceLocation("ars_nouveau", "textures/entity/whirlisprig_" + (this.getColor(entity).isEmpty() ? "summer" : this.getColor(entity)) + ".png");
   }
}
