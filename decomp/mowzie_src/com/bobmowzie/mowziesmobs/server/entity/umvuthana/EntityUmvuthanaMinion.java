package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.ServerProxy;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIUmvuthanaTrade;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIUmvuthanaTradeLook;
import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.TradeStore;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthanaTrade;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;

public class EntityUmvuthanaMinion extends EntityUmvuthana implements LeaderSunstrikeImmune, Enemy {
   private static final TradeStore DEFAULT = new TradeStore.Builder()
      .addTrade(Items.f_42587_, 4, ((Block)BlockHandler.CLAWED_LOG.get()).m_5456_(), 1, 9)
      .addTrade(Items.f_42587_, 7, ((Block)BlockHandler.CLAWED_LOG.get()).m_5456_(), 2, 9)
      .addTrade(Items.f_42587_, 5, Items.f_42582_, 2, 2)
      .addTrade(Items.f_42587_, 4, Items.f_42582_, 1, 2)
      .addTrade(Items.f_42587_, 7, Items.f_42486_, 2, 2)
      .addTrade(Items.f_42587_, 4, Items.f_42486_, 1, 2)
      .addTrade(Items.f_42587_, 1, Items.f_42402_, 4, 2)
      .addTrade(Items.f_42587_, 1, Items.f_42398_, 12, 2)
      .addTrade(Items.f_42587_, 3, Items.f_42781_, 1, 2)
      .addTrade(Items.f_42575_, 3, Items.f_42587_, 5, 2)
      .addTrade(Items.f_42581_, 1, Items.f_42587_, 3, 2)
      .addTrade(Items.f_42581_, 1, Items.f_42587_, 4, 2)
      .addTrade(Items.f_42485_, 1, Items.f_42587_, 6, 2)
      .addTrade(Items.f_42732_, 3, Items.f_42587_, 6, 2)
      .addTrade(Items.f_42527_, 2, Items.f_42587_, 8, 1)
      .addTrade(Items.f_42526_, 2, Items.f_42587_, 7, 1)
      .addTrade(Items.f_42484_, 2, Items.f_42402_, 5, 2)
      .addTrade(Items.f_42402_, 5, Items.f_42484_, 2, 2)
      .addTrade(Items.f_41827_, 2, Items.f_42587_, 4, 2)
      .addTrade(Items.f_42500_, 3, Items.f_42587_, 4, 1)
      .addTrade(Items.f_42500_, 2, Items.f_42587_, 2, 1)
      .build();
   private static final EntityDataAccessor<Optional<Trade>> TRADE = SynchedEntityData.m_135353_(EntityUmvuthanaMinion.class, ServerProxy.OPTIONAL_TRADE);
   private static final EntityDataAccessor<Optional<UUID>> MISBEHAVED_PLAYER = SynchedEntityData.m_135353_(
      EntityUmvuthanaMinion.class, EntityDataSerializers.f_135041_
   );
   private static final EntityDataAccessor<Boolean> IS_TRADING = SynchedEntityData.m_135353_(EntityUmvuthanaMinion.class, EntityDataSerializers.f_135035_);
   private static final int MIN_OFFER_TIME = 6000;
   private static final int MAX_OFFER_TIME = 24000;
   private TradeStore tradeStore = TradeStore.EMPTY;
   private int timeOffering;
   private Player customer;

   public EntityUmvuthanaMinion(EntityType<? extends EntityUmvuthanaMinion> type, Level world) {
      super(type, world);
      this.setWeapon(0);
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new EntityAIUmvuthanaTrade(this));
      this.f_21345_.m_25352_(1, new EntityAIUmvuthanaTradeLook(this));
      this.f_21345_.m_25352_(7, new MoveTowardsRestrictionGoal(this, 0.4));
   }

   @Override
   protected void registerTargetGoals() {
      this.f_21346_.m_25352_(3, new UmvuthanaHurtByTargetAI(this));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal<Player>(this, Player.class, 0, true, true, target -> {
         if (target instanceof Player) {
            if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
               return false;
            } else {
               ItemStack headArmorStack = (ItemStack)((Player)target).m_150109_().f_35975_.get(3);
               return !(headArmorStack.m_41720_() instanceof UmvuthanaMask) || target == this.getMisbehavedPlayer();
            }
         } else {
            return true;
         }
      }) {
         public void m_8041_() {
            super.m_8041_();
            EntityUmvuthanaMinion.this.setMisbehavedPlayerId(null);
         }
      });
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Zombie.class, 0, true, true, null));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, AbstractSkeleton.class, 0, true, false, null));
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(TRADE, Optional.empty());
      this.f_19804_.m_135372_(MISBEHAVED_PLAYER, Optional.empty());
      this.f_19804_.m_135372_(IS_TRADING, false);
   }

   public void setOfferingTrade(Trade trade) {
      this.m_20088_().m_135381_(TRADE, Optional.ofNullable(trade));
   }

   public Trade getOfferingTrade() {
      return (Trade)((Optional)this.m_20088_().m_135370_(TRADE)).orElse(null);
   }

   public boolean isOfferingTrade() {
      return this.m_20088_().m_135370_(TRADE) instanceof Optional ? ((Optional)this.m_20088_().m_135370_(TRADE)).isPresent() : false;
   }

   public void setCustomer(Player customer) {
      this.setTrading(customer != null);
      this.customer = customer;
   }

   public Player getCustomer() {
      return this.customer;
   }

   public void setTrading(boolean trading) {
      this.f_19804_.m_135381_(IS_TRADING, trading);
   }

   public boolean isTrading() {
      return (Boolean)this.f_19804_.m_135370_(IS_TRADING);
   }

   @Override
   protected boolean canHoldVaryingWeapons() {
      return false;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.m_5448_() instanceof Player && (((Player)this.m_5448_()).m_7500_() || this.m_5448_().m_5833_())) {
         this.m_6710_(null);
      }

      if ((!this.isOfferingTrade() || this.timeOffering <= 0) && this.tradeStore.hasStock()) {
         this.setOfferingTrade(this.tradeStore.get(this.f_19796_));
         this.timeOffering = this.f_19796_.m_188503_(18001) + 6000;
      }
   }

   public void openGUI(Player playerEntity) {
      this.setCustomer(playerEntity);
      MowziesMobs.PROXY.setReferencedMob(this);
      if (!this.f_19853_.f_46443_ && this.m_5448_() == null && this.m_6084_()) {
         playerEntity.m_5893_(new MenuProvider() {
            public AbstractContainerMenu m_7208_(int id, Inventory playerInventory, Player player) {
               return new ContainerUmvuthanaTrade(id, EntityUmvuthanaMinion.this, playerInventory);
            }

            public Component m_5446_() {
               return EntityUmvuthanaMinion.this.m_5446_();
            }
         });
      }
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (this.canTradeWith(player) && this.m_5448_() == null && this.m_6084_()) {
         this.openGUI(player);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean canTradeWith(Player player) {
      if (this.isTrading()) {
         return false;
      } else {
         ItemStack headStack = (ItemStack)player.m_150109_().f_35975_.get(3);
         return headStack.m_41720_() instanceof UmvuthanaMask && this.isOfferingTrade();
      }
   }

   @Nullable
   @Override
   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData, @Nullable CompoundTag compound
   ) {
      this.tradeStore = DEFAULT;
      if (reason == MobSpawnType.COMMAND) {
         this.m_21446_(this.m_20183_(), 25);
      }

      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }

   public boolean m_8023_() {
      return true;
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128365_("tradeStore", this.tradeStore.serialize());
      if (this.isOfferingTrade()) {
         compound.m_128365_("offeringTrade", this.getOfferingTrade().serialize());
      }

      compound.m_128405_("timeOffering", this.timeOffering);
      compound.m_128405_("HomePosX", this.m_21534_().m_123341_());
      compound.m_128405_("HomePosY", this.m_21534_().m_123342_());
      compound.m_128405_("HomePosZ", this.m_21534_().m_123343_());
      compound.m_128405_("HomeDist", (int)this.m_21535_());
      if (this.getMisbehavedPlayerId() != null) {
         compound.m_128362_("MisbehavedPlayer", this.getMisbehavedPlayerId());
      }
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.tradeStore = TradeStore.deserialize(compound.m_128469_("tradeStore"));
      this.setOfferingTrade(Trade.deserialize(compound.m_128469_("offeringTrade")));
      this.timeOffering = compound.m_128451_("timeOffering");
      int i = compound.m_128451_("HomePosX");
      int j = compound.m_128451_("HomePosY");
      int k = compound.m_128451_("HomePosZ");
      int dist = compound.m_128451_("HomeDist");
      this.m_21446_(new BlockPos(i, j, k), dist);
      UUID uuid;
      if (compound.m_128403_("MisbehavedPlayer")) {
         uuid = compound.m_128342_("MisbehavedPlayer");
      } else {
         String s = compound.m_128461_("MisbehavedPlayer");
         uuid = OldUsersConverter.m_11083_(this.m_20194_(), s);
      }

      if (uuid != null) {
         try {
            this.setMisbehavedPlayerId(uuid);
         } catch (Throwable var8) {
         }
      }
   }

   @Nullable
   public UUID getMisbehavedPlayerId() {
      return (UUID)((Optional)this.f_19804_.m_135370_(MISBEHAVED_PLAYER)).orElse((UUID)null);
   }

   public void setMisbehavedPlayerId(@Nullable UUID p_184754_1_) {
      this.f_19804_.m_135381_(MISBEHAVED_PLAYER, Optional.ofNullable(p_184754_1_));
   }

   @Nullable
   public LivingEntity getMisbehavedPlayer() {
      try {
         UUID uuid = this.getMisbehavedPlayerId();
         return uuid == null ? null : this.f_19853_.m_46003_(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }
}
