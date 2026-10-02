package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.client.IVariantColorProvider;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.familiar.PersistentFamiliarData;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.StorageLecternTile;
import com.hollingsworth.arsnouveau.common.entity.goal.bookwyrm.RandomStorageVisitGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.bookwyrm.TransferGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.bookwyrm.TransferTask;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.Arrays;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
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

public class EntityBookwyrm extends FlyingMob implements IDispellable, ITooltipProvider, IWandable, IAnimatable, IVariantColorProvider<EntityBookwyrm> {
   public static final EntityDataAccessor<ItemStack> HELD_ITEM = SynchedEntityData.m_135353_(EntityBookwyrm.class, EntityDataSerializers.f_135033_);
   public static final EntityDataAccessor<String> COLOR = SynchedEntityData.m_135353_(EntityBookwyrm.class, EntityDataSerializers.f_135030_);
   public BlockPos lecternPos;
   public int backoffTicks;
   public boolean playerTooFar;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);
   public static String[] COLORS = new String[]{"purple", "green", "blue", "black", "red", "white"};

   protected EntityBookwyrm(EntityType<? extends FlyingMob> p_i48568_1_, Level p_i48568_2_) {
      super(p_i48568_1_, p_i48568_2_);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
   }

   public EntityBookwyrm(Level p_i50190_2_) {
      this((EntityType<? extends FlyingMob>)ModEntities.ENTITY_BOOKWYRM_TYPE.get(), p_i50190_2_);
   }

   public EntityBookwyrm(Level world, BlockPos lecternPos) {
      this(world);
      this.lecternPos = lecternPos;
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && !player.m_20193_().f_46443_) {
         ItemStack stack = player.m_21120_(hand);
         if (player.m_21205_().m_204117_(Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color != null && !((String)this.f_19804_.m_135370_(COLOR)).equals(color.m_41065_()) && Arrays.asList(COLORS).contains(color.m_41065_())) {
               this.setColor(color.m_41065_(), this);
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
      if (this.f_19853_ != null && !this.f_20890_ && this.lecternPos != null) {
         SummonUtil.healOverTime(this);
         if (!this.f_19853_.f_46443_ && this.backoffTicks >= 0) {
            this.backoffTicks--;
         }

         if (this.f_19853_.m_46467_() % 20L == 0L && !(this.f_19853_.m_7702_(this.lecternPos) instanceof StorageLecternTile) && !this.f_19853_.f_46443_) {
            this.m_6469_(DamageSource.m_19344_(ANFakePlayer.getPlayer((ServerLevel)this.f_19853_)), 99.0F);
         }

         if (!this.f_19853_.f_46443_ && this.f_19853_.m_46467_() % 100L == 0L) {
            this.playerTooFar = true;
            ServerLevel serverLevel = (ServerLevel)this.f_19853_;

            for (ServerPlayer serverPlayer : serverLevel.m_6907_()) {
               if (BlockUtil.distanceFrom(serverPlayer.m_20182_(), this.m_20182_()) < 40.0) {
                  this.playerTooFar = false;
                  break;
               }
            }
         }
      }
   }

   public boolean m_7337_(Entity pEntity) {
      return pEntity instanceof Player ? false : super.m_7337_(pEntity);
   }

   public boolean m_6094_() {
      return false;
   }

   protected void m_6138_() {
   }

   public boolean m_6469_(@NotNull DamageSource source, float p_70097_2_) {
      return !SummonUtil.canSummonTakeDamage(source) ? false : super.m_6469_(source, p_70097_2_);
   }

   protected PathNavigation m_6037_(Level world) {
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, world);
      flyingpathnavigator.m_26440_(false);
      flyingpathnavigator.m_7008_(true);
      flyingpathnavigator.m_26443_(true);
      return flyingpathnavigator;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new TransferGoal(this));
      this.f_21345_.m_25352_(4, new RandomStorageVisitGoal(this, () -> {
         StorageLecternTile tile = this.getTile();
         if (tile != null && !this.playerTooFar) {
            List<BlockPos> targets = new ArrayList<>(tile.connectedInventories);
            targets.add(tile.m_58899_());
            return targets.get(this.f_19853_.f_46441_.m_188503_(targets.size())).m_7494_();
         } else {
            return null;
         }
      }));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
   }

   public TransferTask getTransferTask() {
      StorageLecternTile tile = this.getTile();
      return tile != null ? tile.getTransferTask() : null;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      if (this.m_213877_()) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_) {
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.toCharm()));
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
         }

         return true;
      }
   }

   public void m_142687_(RemovalReason pReason) {
      super.m_142687_(pReason);
      StorageLecternTile tile = this.getTile();
      if (tile != null) {
         tile.removeBookwyrm(this);
      }
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_BOOKWYRM_TYPE.get();
   }

   public ItemStack toCharm() {
      ItemStack stack = new ItemStack((ItemLike)ItemsRegistry.BOOKWYRM_CHARM.get());
      PersistentFamiliarData<EntityBookwyrm> data = new PersistentFamiliarData(new CompoundTag());
      data.color = this.getColor(this);
      data.name = this.m_7770_();
      stack.m_41751_(data.toTag(this, new CompoundTag()));
      return stack;
   }

   public void readCharm(ItemStack stack) {
      if (stack.m_41782_()) {
         PersistentFamiliarData<EntityBookwyrm> data = new PersistentFamiliarData(stack.m_41784_());
         this.setColor(data.color, this);
         this.m_6593_(data.name);
      }
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      if (this.lecternPos != null) {
         tag.m_128356_("lectern", this.lecternPos.m_121878_());
      }

      if (!this.getHeldStack().m_41619_()) {
         CompoundTag itemTag = new CompoundTag();
         this.getHeldStack().m_41739_(itemTag);
         tag.m_128365_("held", itemTag);
      }

      tag.m_128405_("backoff", this.backoffTicks);
      tag.m_128359_("color", (String)this.f_19804_.m_135370_(COLOR));
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128441_("lectern")) {
         this.lecternPos = BlockPos.m_122022_(tag.m_128454_("lectern"));
      }

      if (tag.m_128441_("held")) {
         this.setHeldStack(ItemStack.m_41712_((CompoundTag)tag.m_128423_("held")));
      }

      this.backoffTicks = tag.m_128451_("backoff");
      if (tag.m_128441_("color")) {
         this.f_19804_.m_135381_(COLOR, tag.m_128461_("color"));
      }
   }

   @Nullable
   public StorageLecternTile getTile() {
      return this.lecternPos != null && this.f_19853_.m_7702_(this.lecternPos) instanceof StorageLecternTile
         ? (StorageLecternTile)this.f_19853_.m_7702_(this.lecternPos)
         : null;
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "walkController", 1.0F, this::idle));
   }

   public PlayState idle(AnimationEvent<?> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
      return PlayState.CONTINUE;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public int m_213860_() {
      return 0;
   }

   public boolean m_6785_(double p_213397_1_) {
      return false;
   }

   @NotNull
   public ItemStack getHeldStack() {
      return (ItemStack)this.f_19804_.m_135370_(HELD_ITEM);
   }

   public void setHeldStack(ItemStack stack) {
      this.f_19804_.m_135381_(HELD_ITEM, stack);
   }

   public void m_6667_(DamageSource source) {
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.toCharm()));
      }

      super.m_6667_(source);
   }

   public static Builder attributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22280_, Attributes.f_22280_.m_22082_()).m_22268_(Attributes.f_22276_, 6.0).m_22268_(Attributes.f_22279_, 0.3);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HELD_ITEM, ItemStack.f_41583_);
      this.f_19804_.m_135372_(COLOR, "blue");
   }

   public ResourceLocation getTexture(EntityBookwyrm entity) {
      String color = this.getColor(entity).toLowerCase();
      if (color.isEmpty()) {
         color = "blue";
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/book_wyrm_" + color + ".png");
   }

   public String getColor(EntityBookwyrm entityBookwyrm) {
      return (String)this.m_20088_().m_135370_(COLOR);
   }

   public void setColor(String color, EntityBookwyrm entityBookwyrm) {
      this.m_20088_().m_135381_(COLOR, color);
   }
}
