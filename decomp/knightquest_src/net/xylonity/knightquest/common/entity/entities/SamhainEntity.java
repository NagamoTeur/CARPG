package net.xylonity.knightquest.common.entity.entities;

import dev.xylonity.knightlib.compat.registry.KnightLibItems;
import java.util.Arrays;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import net.xylonity.knightquest.common.entity.entities.ai.MoveToPumpkinGoal;
import net.xylonity.knightquest.common.entity.entities.ai.RangedAttackGoal;
import net.xylonity.knightquest.registry.KnightQuestItems;
import net.xylonity.knightquest.registry.KnightQuestWeapons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SamhainEntity extends TamableAnimal implements IAnimatable, RangedAttackMob {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.m_135353_(SamhainEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> SIT_VARIATION = SynchedEntityData.m_135353_(SamhainEntity.class, EntityDataSerializers.f_135028_);

   public SamhainEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.m_21553_(true);
   }

   public static AttributeSupplier setAttributes() {
      return TamableAnimal.m_21552_()
         .m_22268_(Attributes.f_22276_, 12.0)
         .m_22268_(Attributes.f_22281_, 0.5)
         .m_22268_(Attributes.f_22283_, 1.0)
         .m_22268_(Attributes.f_22279_, 0.6F)
         .m_22265_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(2, new RangedAttackGoal(this, 0.7, 10, 15.0F));
      this.f_21345_.m_25352_(3, new MeleeAttackGoal(this, 0.6, true));
      this.f_21345_.m_25352_(4, new FollowOwnerGoal(this, 0.6, 6.0F, 2.0F, false));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(7, new MoveToPumpkinGoal(this, 0.68F));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
      animationData.addAnimationController(new AnimationController(this, "attackcontroller", 0.0F, this::attackPredicate));
   }

   private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
      if (this.f_20911_ && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
         event.getController().markNeedsReload();
         event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", EDefaultLoopTypes.PLAY_ONCE));
         this.f_20911_ = false;
      }

      return PlayState.CONTINUE;
   }

   @NotNull
   public HumanoidArm m_5737_() {
      return HumanoidArm.RIGHT;
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      if (this.isSitting()) {
         String sitVariation = this.getSitVariation() == 0 ? "sit" : (this.getSitVariation() == 1 ? "sit3" : "sit2");
         event.getController().setAnimation(new AnimationBuilder().addAnimation(sitVariation, EDefaultLoopTypes.LOOP));
      } else if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      Item itemForTaming = (Item)KnightLibItems.GREAT_ESSENCE.get();
      Item item = itemstack.m_41720_();
      if (item == itemForTaming && !this.m_21824_()) {
         if (this.f_19853_.f_46443_) {
            return InteractionResult.CONSUME;
         } else {
            if (!player.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            if (!ForgeEventFactory.onAnimalTame(this, player) && !this.f_19853_.f_46443_) {
               super.m_21828_(player);
               this.f_21344_.m_26569_();
               this.m_6710_(null);
               this.f_19853_.m_7605_(this, (byte)7);
               this.setSitting(true);
            }

            this.setSitVariation(this.m_217043_().m_216339_(0, 3));
            return InteractionResult.SUCCESS;
         }
      } else if (this.m_21824_() && !this.f_19853_.f_46443_ && hand == InteractionHand.MAIN_HAND && this.m_21826_() == player) {
         if ((itemstack.m_41720_().equals(KnightLibItems.GREAT_ESSENCE.get()) || itemstack.m_41720_().equals(KnightLibItems.SMALL_ESSENCE.get()))
            && this.m_21223_() < this.m_21233_()) {
            if (itemstack.m_41720_().equals(KnightLibItems.GREAT_ESSENCE.get())) {
               this.m_5634_(16.0F);
            } else if (itemstack.m_41720_().equals(KnightLibItems.SMALL_ESSENCE.get())) {
               this.m_5634_(4.0F);
            }

            if (!player.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }
         } else if (player.m_6144_()) {
            this.removeArmor(player);
            this.removeItem(player);
         } else {
            this.setSitting(!this.isSitting());
            this.setSitVariation(this.m_217043_().m_216339_(0, 3));
         }

         return InteractionResult.SUCCESS;
      } else {
         return itemstack.m_41720_() == itemForTaming ? InteractionResult.PASS : super.m_6071_(player, hand);
      }
   }

   protected void m_7581_(ItemEntity itemEntity) {
      ItemStack itemStack = itemEntity.m_32055_();
      Item item = itemStack.m_41720_();
      Item[] COMPATIBLE_WEAPONS = new Item[]{
         Items.f_42383_,
         Items.f_42425_,
         Items.f_42430_,
         Items.f_42420_,
         Items.f_42393_,
         Items.f_42388_,
         Items.f_42411_,
         (Item)KnightQuestWeapons.PALADIN_SWORD.get(),
         (Item)KnightQuestWeapons.NAIL.get(),
         (Item)KnightQuestWeapons.UCHIGATANA.get(),
         (Item)KnightQuestWeapons.KUKRI.get(),
         (Item)KnightQuestWeapons.KHOPESH.get(),
         (Item)KnightQuestWeapons.CLEAVER.get(),
         (Item)KnightQuestItems.WATER_SWORD.get(),
         (Item)KnightQuestItems.STEEL_SWORD.get(),
         (Item)KnightQuestItems.WATER_AXE.get(),
         (Item)KnightQuestItems.STEEL_AXE.get()
      };
      if (Arrays.stream(COMPATIBLE_WEAPONS).toList().contains(item) && this.m_21824_()) {
         EquipmentSlot slot = LivingEntity.m_147233_(itemStack);
         ItemStack currentItem = this.m_21120_(InteractionHand.MAIN_HAND);
         if (currentItem.m_41619_()) {
            this.m_8061_(slot, itemStack.m_41620_(1));
            if (itemStack.m_41619_()) {
               itemEntity.m_146870_();
            }
         }
      }
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      if (this.m_21805_() != null) {
         tag.m_128362_("ownerUUID", this.m_21805_());
      }

      this.setSitting(tag.m_128471_("isSitting"));

      for (EquipmentSlot slot : EquipmentSlot.values()) {
         if (slot.m_20743_() == Type.HAND) {
            CompoundTag stackNbt = tag.m_128469_(slot.m_20751_());
            ItemStack stack = ItemStack.m_41712_(stackNbt);
            if (!stack.m_41619_()) {
               this.m_8061_(slot, stack);
            }
         }
      }
   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      if (tag.m_128403_("ownerUUID")) {
         this.m_21816_(tag.m_128342_("ownerUUID"));
      }

      tag.m_128379_("isSitting", this.isSitting());

      for (EquipmentSlot slot : EquipmentSlot.values()) {
         if (slot.m_20743_() == Type.HAND) {
            ItemStack stack = this.m_6844_(slot);
            if (!stack.m_41619_()) {
               CompoundTag stackNbt = new CompoundTag();
               stack.m_41739_(stackNbt);
               tag.m_128365_(slot.m_20751_(), stackNbt);
            }
         }
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SITTING, false);
      this.f_19804_.m_135372_(SIT_VARIATION, 0);
   }

   private void setSitVariation(int sitVariation) {
      this.f_19804_.m_135381_(SIT_VARIATION, sitVariation);
   }

   private int getSitVariation() {
      return (Integer)this.f_19804_.m_135370_(SIT_VARIATION);
   }

   private void removeArmor(Player pPlayer) {
      for (EquipmentSlot slot : EquipmentSlot.values()) {
         if (slot.m_20743_() == Type.ARMOR) {
            ItemStack armorStack = this.m_6844_(slot);
            if (!armorStack.m_41619_()) {
               boolean addedToInventory = pPlayer.m_150109_().m_36054_(armorStack);
               if (!addedToInventory) {
                  pPlayer.m_19983_(armorStack);
               }

               this.m_8061_(slot, ItemStack.f_41583_);
            }
         }
      }
   }

   private void removeItem(Player pPlayer) {
      ItemStack itemStack = this.m_21120_(InteractionHand.MAIN_HAND);
      if (!itemStack.m_41619_()) {
         boolean addedToInventory = pPlayer.m_150109_().m_36054_(itemStack);
         if (!addedToInventory) {
            pPlayer.m_19983_(itemStack);
         }

         this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
      }
   }

   public void setSitting(boolean sitting) {
      this.f_19804_.m_135381_(SITTING, sitting);
      this.m_21839_(sitting);
   }

   public boolean isSitting() {
      return (Boolean)this.f_19804_.m_135370_(SITTING);
   }

   public boolean m_6573_(@NotNull Player player) {
      return false;
   }

   public void m_7105_(boolean tamed) {
      super.m_7105_(tamed);
      if (tamed) {
         Objects.requireNonNull(this.m_21051_(Attributes.f_22276_)).m_22100_(20.0);
         Objects.requireNonNull(this.m_21051_(Attributes.f_22281_)).m_22100_(0.5);
         Objects.requireNonNull(this.m_21051_(Attributes.f_22279_)).m_22100_(0.5);
         this.m_21153_(20.0F);
      } else {
         Objects.requireNonNull(this.m_21051_(Attributes.f_22276_)).m_22100_(8.0);
         Objects.requireNonNull(this.m_21051_(Attributes.f_22281_)).m_22100_(0.5);
         Objects.requireNonNull(this.m_21051_(Attributes.f_22279_)).m_22100_(0.5);
      }
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
      return null;
   }

   @NotNull
   protected SoundEvent m_5501_() {
      return SoundEvents.f_144067_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_215672_;
   }

   protected SoundEvent m_7975_(@NotNull DamageSource pDamageSource) {
      return SoundEvents.f_215675_;
   }

   protected void m_7355_(@NotNull BlockPos pPos, @NotNull BlockState pState) {
      this.m_5496_(SoundEvents.f_12624_, 0.15F, 1.0F);
   }

   public void m_6504_(@NotNull LivingEntity pTarget, float pVelocity) {
      ItemStack itemstack = this.m_6298_(this.m_21120_(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem)));
      AbstractArrow abstractarrow = this.getArrow(itemstack, pVelocity);
      if (this.m_21205_().m_41720_() instanceof BowItem) {
         abstractarrow = ((BowItem)this.m_21205_().m_41720_()).customArrow(abstractarrow);
      }

      double d0 = pTarget.m_20185_() - this.m_20185_();
      double d1 = pTarget.m_20227_(0.34) - abstractarrow.m_20186_();
      double d2 = pTarget.m_20189_() - this.m_20189_();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      abstractarrow.m_6686_(d0, d1 + d3 * 0.2F, d2, 1.6F, (float)(14 - this.f_19853_.m_46791_().m_19028_() * 4));
      this.m_5496_(SoundEvents.f_12382_, 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
      this.f_19853_.m_7967_(abstractarrow);
   }

   protected AbstractArrow getArrow(ItemStack pArrowStack, float pVelocity) {
      return ProjectileUtil.m_37300_(this, pArrowStack, pVelocity);
   }
}
