package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.Pet.AI.InternalPetStateGoal;
import com.github.L_Ender.cataclysm.entity.Pet.AI.TameableAIFollowOwner;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.inventory.MinistrostiyMenu;
import com.github.L_Ender.cataclysm.message.MessageMiniinventory;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerContainerEvent.Open;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.PacketDistributor;

public class Netherite_Ministrosity_Entity extends InternalAnimationPet implements Bucketable, ContainerListener, HasCustomInventoryScreen {
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.m_135353_(
      Netherite_Ministrosity_Entity.class, EntityDataSerializers.f_135035_
   );
   private static final EntityDataAccessor<Boolean> IS_AWAKEN = SynchedEntityData.m_135353_(
      Netherite_Ministrosity_Entity.class, EntityDataSerializers.f_135035_
   );
   public SimpleContainer miniInventory;
   public float LayerBrightness;
   public float oLayerBrightness;
   public int LayerTicks;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState sleepAnimationState = new AnimationState();
   public AnimationState operationAnimationState = new AnimationState();
   public AnimationState chestopenAnimationState = new AnimationState();
   public AnimationState chestloopAnimationState = new AnimationState();
   public AnimationState chestcloseAnimationState = new AnimationState();
   private LazyOptional<?> itemHandler = null;

   public Netherite_Ministrosity_Entity(EntityType type, Level world) {
      super(type, world);
      this.createInventory();
      this.f_21364_ = 0;
      setConfigattribute(this, CMConfig.MinistrosityHealthMultiplier, 1.0);
   }

   public float getStepHeight() {
      return 1.0F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(6, new TameableAIFollowOwner(this, 1.3, 6.0F, 2.0F, true));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0, 60));
      this.f_21345_.m_25352_(1, new InternalPetStateGoal(this, 1, 1, 0, 0, 0) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_();
         }

         @Override
         public void m_8037_() {
            this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         }
      });
      this.f_21345_.m_25352_(0, new InternalPetStateGoal(this, 1, 2, 0, 40, 0) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Netherite_Ministrosity_Entity.this.getIsAwaken();
         }
      });
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.MINISTROSITY_HURT.get();
   }

   public boolean isSleep() {
      return this.getAttackState() == 1 || this.getAttackState() == 2;
   }

   public boolean isOpen() {
      return this.getAttackState() == 3 || this.getAttackState() == 4;
   }

   public void setIsAwaken(boolean isAwaken) {
      this.f_19804_.m_135381_(IS_AWAKEN, isAwaken);
      if (!isAwaken) {
         this.setAttackState(1);
      }
   }

   public boolean getIsAwaken() {
      return (Boolean)this.f_19804_.m_135370_(IS_AWAKEN);
   }

   protected int getInventorySize() {
      return 17;
   }

   protected void createInventory() {
      SimpleContainer simplecontainer = this.miniInventory;
      this.miniInventory = new SimpleContainer(this.getInventorySize());
      if (simplecontainer != null) {
         simplecontainer.m_19181_(this);
         int i = Math.min(simplecontainer.m_6643_(), this.miniInventory.m_6643_());

         for (int j = 0; j < i; j++) {
            ItemStack itemstack = simplecontainer.m_8020_(j);
            if (!itemstack.m_41619_()) {
               this.miniInventory.m_6836_(j, itemstack.m_41777_());
            }
         }
      }

      this.miniInventory.m_19164_(this);
      this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.miniInventory));
   }

   public void m_213583_(Player playerEntity) {
      if (playerEntity instanceof ServerPlayer serverplayer && this.m_6084_()) {
         if (serverplayer.f_36096_ != serverplayer.f_36095_) {
            serverplayer.m_6915_();
         }

         this.setAttackState(3);
         serverplayer.m_9217_();
         Cataclysm.NETWORK_WRAPPER
            .send(
               PacketDistributor.PLAYER.with(() -> serverplayer), new MessageMiniinventory(serverplayer.f_8940_, this.miniInventory.m_6643_(), this.m_19879_())
            );
         serverplayer.f_36096_ = new MinistrostiyMenu(serverplayer.f_8940_, serverplayer.m_150109_(), this.miniInventory, this);
         serverplayer.m_143399_(serverplayer.f_36096_);
         MinecraftForge.EVENT_BUS.post(new Open(serverplayer, serverplayer.f_36096_));
      }
   }

   public int getInventoryColumns() {
      return 5;
   }

   public void m_5757_(Container p_30548_) {
   }

   public AnimationState getAnimationState(String input) {
      if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "sleep") {
         return this.sleepAnimationState;
      } else if (input == "operation") {
         return this.operationAnimationState;
      } else if (input == "chest_open") {
         return this.chestopenAnimationState;
      } else if (input == "chest_loop") {
         return this.chestloopAnimationState;
      } else {
         return input == "chest_close" ? this.chestcloseAnimationState : new AnimationState();
      }
   }

   public static Builder ministrosity() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 120.0)
         .m_22268_(Attributes.f_22278_, 0.5)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22277_, 32.0)
         .m_22268_(Attributes.f_22279_, 0.4F);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public void m_7023_(Vec3 vec3d) {
      if (this.isSitting()) {
         if (this.m_21573_().m_26570_() != null) {
            this.m_21573_().m_26573_();
         }

         vec3d = Vec3.f_82478_;
      }

      super.m_7023_(vec3d);
   }

   public boolean m_6673_(DamageSource source) {
      return source == DamageSource.f_19310_ || source == DamageSource.f_19322_ || super.m_6673_(source) || source.m_146707_();
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.miniInventory != null) {
         for (int i = 0; i < this.miniInventory.m_6643_(); i++) {
            ItemStack itemstack = this.miniInventory.m_8020_(i);
            if (!itemstack.m_41619_()) {
               this.m_5552_(itemstack, 0.0F);
            }
         }
      }
   }

   public boolean m_6040_() {
      return true;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(FROM_BUCKET, false);
      this.f_19804_.m_135372_(IS_AWAKEN, false);
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("FromBucket", this.m_27487_());
      compound.m_128379_("is_Awaken", this.getIsAwaken());
      ListTag listtag = new ListTag();

      for (int i = 2; i < this.miniInventory.m_6643_(); i++) {
         ItemStack itemstack = this.miniInventory.m_8020_(i);
         if (!itemstack.m_41619_()) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128344_("Slot", (byte)i);
            itemstack.m_41739_(compoundtag);
            listtag.add(compoundtag);
         }
      }

      compound.m_128365_("Items", listtag);
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.m_27497_(compound.m_128471_("FromBucket"));
      this.setIsAwaken(compound.m_128471_("is_Awaken"));
      this.createInventory();
      ListTag listtag = compound.m_128437_("Items", 10);

      for (int i = 0; i < listtag.size(); i++) {
         CompoundTag compoundtag = listtag.m_128728_(i);
         int j = compoundtag.m_128445_("Slot") & 255;
         if (j >= 2 && j < this.miniInventory.m_6643_()) {
            this.miniInventory.m_6836_(j, ItemStack.m_41712_(compoundtag));
         }
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.sleepAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.operationAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.chestopenAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.chestloopAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.chestcloseAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.sleepAnimationState.m_216973_();
      this.operationAnimationState.m_216973_();
      this.chestopenAnimationState.m_216973_();
      this.chestloopAnimationState.m_216973_();
      this.chestcloseAnimationState.m_216973_();
   }

   public boolean m_27487_() {
      return (Boolean)this.f_19804_.m_135370_(FROM_BUCKET);
   }

   public void m_27497_(boolean sit) {
      this.f_19804_.m_135381_(FROM_BUCKET, sit);
   }

   public void m_6872_(@Nonnull ItemStack bucket) {
      CompoundTag platTag = new CompoundTag();
      CompoundTag compound = bucket.m_41784_();
      this.m_7380_(platTag);
      Bucketable.m_148822_(this, bucket);
      compound.m_128365_("MinistrosityData", platTag);
   }

   public void m_142278_(CompoundTag p_148832_) {
      Bucketable.m_148825_(this, p_148832_);
      if (p_148832_.m_128441_("MinistrosityData")) {
         this.m_7378_(p_148832_.m_128469_("MinistrosityData"));
      }
   }

   @Nonnull
   public ItemStack m_28282_() {
      return new ItemStack((ItemLike)ModItems.NETHERITE_MINISTROSITY_BUCKET.get());
   }

   public SoundEvent m_142623_() {
      return (SoundEvent)ModSounds.MINISTROSITY_FILL_BUCKET.get();
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      boolean owner = this.m_21824_() && this.m_21830_(player);
      InteractionResult type = super.m_6071_(player, hand);
      if (owner) {
         Optional<InteractionResult> result = emptybucketMobPickup(player, hand, this);
         if (result.isPresent()) {
            return result.get();
         }

         if (!player.m_6144_()) {
            this.m_213583_(player);
            this.setCommand(2);
            this.m_21839_(true);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }
      }

      if (!this.m_21824_() && stack.m_150930_((Item)ModItems.LAVA_POWER_CELL.get())) {
         this.m_142075_(player, hand, stack);
         this.m_146850_(GameEvent.f_157806_);
         if (!ForgeEventFactory.onAnimalTame(this, player)) {
            this.m_21828_(player);
            this.setIsAwaken(true);
            this.f_19853_.m_7605_(this, (byte)7);
         } else {
            this.f_19853_.m_7605_(this, (byte)6);
         }

         return InteractionResult.SUCCESS;
      } else if (this.m_21824_() && stack.m_150930_(Items.f_42413_) && this.m_21223_() < this.m_21233_()) {
         this.m_5634_(5.0F);
         if (!player.m_150110_().f_35937_) {
            stack.m_41774_(1);
         }

         this.m_146852_(GameEvent.f_157806_, this);
         return InteractionResult.SUCCESS;
      } else {
         InteractionResult interactionresult = stack.m_41647_(player, this, hand);
         if (interactionresult != InteractionResult.SUCCESS
            && type != InteractionResult.SUCCESS
            && this.m_21824_()
            && this.m_21830_(player)
            && player.m_6144_()) {
            this.setCommand(this.getCommand() + 1);
            if (this.getCommand() == 3) {
               this.setCommand(0);
            }

            player.m_5661_(Component.m_237110_("entity.cataclysm.all.command_" + this.getCommand(), new Object[]{this.m_7755_()}), true);
            boolean sit = this.getCommand() == 2;
            if (sit) {
               this.m_21839_(true);
               return InteractionResult.SUCCESS;
            } else {
               this.m_21839_(false);
               return InteractionResult.SUCCESS;
            }
         } else {
            return type;
         }
      }
   }

   private static <T extends LivingEntity & Bucketable> Optional<InteractionResult> emptybucketMobPickup(
      Player p_148829_, InteractionHand p_148830_, T p_148831_
   ) {
      ItemStack itemstack = p_148829_.m_21120_(p_148830_);
      if (itemstack.m_41720_() == Items.f_42446_ && p_148831_.m_6084_()) {
         p_148831_.m_5496_(p_148831_.m_142623_(), 1.0F, 1.0F);
         ItemStack itemstack1 = p_148831_.m_28282_();
         p_148831_.m_6872_(itemstack1);
         ItemStack itemstack2 = ItemUtils.m_41817_(itemstack, p_148829_, itemstack1, false);
         p_148829_.m_21008_(p_148830_, itemstack2);
         Level level = p_148831_.f_19853_;
         if (!level.f_46443_) {
            CriteriaTriggers.f_10576_.m_38772_((ServerPlayer)p_148829_, itemstack1);
         }

         p_148831_.m_146870_();
         return Optional.of(InteractionResult.m_19078_(level.f_46443_));
      } else {
         return Optional.empty();
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if ((this.isSitting() || this.isOpen()) && this.m_21573_().m_26571_()) {
         this.m_21573_().m_26573_();
      }

      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, this.getAttackState() == 0, this.f_19797_);
      }

      if (this.getAttackState() == 3) {
         if (this.attackTicks == 1) {
            this.m_5496_(SoundEvents.f_12407_, 1.0F, 2.0F);
         }

         if (this.attackTicks >= 9) {
            this.setAttackState(4);
         }
      }

      if (this.getAttackState() == 5) {
         if (this.attackTicks == 1) {
            this.m_5496_(SoundEvents.f_12407_, 1.0F, 2.0F);
         }

         if (this.attackTicks >= 10) {
            this.setAttackState(0);
         }
      }

      if (this.f_19853_.f_46443_) {
         this.LayerTicks++;
         this.LayerBrightness = this.LayerBrightness + (0.0F - this.LayerBrightness) * 0.8F;
      }
   }

   public void animateWhen(AnimationState state, boolean p_252220_, int p_249486_) {
      if (p_252220_) {
         state.m_216982_(p_249486_);
      } else {
         state.m_216973_();
      }
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   public boolean m_7307_(Entity entityIn) {
      if (this.m_21824_()) {
         LivingEntity livingentity = this.m_21826_();
         if (entityIn == livingentity) {
            return true;
         }

         if (entityIn instanceof TamableAnimal) {
            return ((TamableAnimal)entityIn).m_21830_(livingentity);
         }

         if (livingentity != null) {
            return livingentity.m_7307_(entityIn);
         }
      }

      return super.m_7307_(entityIn);
   }

   @Nullable
   @Override
   public AgeableMob m_142606_(ServerLevel serverWorld, AgeableMob ageableEntity) {
      return null;
   }

   @Override
   public boolean shouldFollow() {
      return this.getCommand() == 1;
   }

   public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
      return this.m_6084_() && capability == ForgeCapabilities.ITEM_HANDLER && this.itemHandler != null
         ? this.itemHandler.cast()
         : super.getCapability(capability, facing);
   }

   public void invalidateCaps() {
      super.invalidateCaps();
      if (this.itemHandler != null) {
         LazyOptional<?> oldHandler = this.itemHandler;
         this.itemHandler = null;
         oldHandler.invalidate();
      }
   }

   public boolean hasInventoryChanged(Container p_149512_) {
      return this.miniInventory != p_149512_;
   }
}
