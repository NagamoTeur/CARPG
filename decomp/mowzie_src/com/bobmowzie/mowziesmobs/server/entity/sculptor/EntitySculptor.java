package com.bobmowzie.mowziesmobs.server.entity.sculptor;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBlockSwapper;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderPlatform;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerSculptorTrade;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntitySculptor extends MowzieGeckoEntity {
   public static int TEST_HEIGHT = 50;
   public static int TEST_RADIUS_BOTTOM = 6;
   public static int TEST_RADIUS = 16;
   public static int TEST_MAX_RADIUS_HEIGHT = 10;
   public static double TEST_RADIUS_FALLOFF = 5.0;
   public static final AbilityType<EntitySculptor, EntitySculptor.StartTestAbility> START_TEST = new AbilityType(
      "testStart", EntitySculptor.StartTestAbility::new
   );
   public static final AbilityType<EntitySculptor, EntitySculptor.EndTestAbility> END_TEST = new AbilityType("testEnd", EntitySculptor.EndTestAbility::new);
   public static final AbilityType<EntitySculptor, EntitySculptor.PassTestAbility> PASS_TEST = new AbilityType("testPass", EntitySculptor.PassTestAbility::new);
   private static final EntityDataAccessor<ItemStack> DESIRES = SynchedEntityData.m_135353_(EntitySculptor.class, EntityDataSerializers.f_135033_);
   private static final EntityDataAccessor<Boolean> IS_TRADING = SynchedEntityData.m_135353_(EntitySculptor.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Optional<UUID>> TESTING_PLAYER = SynchedEntityData.m_135353_(EntitySculptor.class, EntityDataSerializers.f_135041_);
   public boolean handLOpen = true;
   public boolean handROpen = true;
   private Player customer;
   private Player testingPlayer;
   private Optional<Double> prevPlayerVelY;
   private Optional<Vec3> prevPlayerPosition;
   private int ticksAcceleratingUpward;
   private boolean testing;
   private EntityPillar pillar;
   public int numLivePaths = 0;

   public EntitySculptor(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
   }

   @Override
   public AbilityType getHurtAbility() {
      return null;
   }

   @Override
   public AbilityType getDeathAbility() {
      return null;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, START_TEST));
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      Item tradeItem = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation((String)ConfigHandler.COMMON.MOBS.UMVUTHI.whichItem.get()));
      this.m_20088_().m_135372_(DESIRES, new ItemStack(tradeItem, (Integer)ConfigHandler.COMMON.MOBS.UMVUTHI.howMany.get()));
      this.m_20088_().m_135372_(IS_TRADING, false);
      this.m_20088_().m_135372_(TESTING_PLAYER, Optional.empty());
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22281_, 10.0).m_22268_(Attributes.f_22276_, 40.0).m_22268_(Attributes.f_22278_, 1.0);
   }

   @Override
   protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      this.getController().transitionLengthTicks = 0.0;
      AbilityCapability.IAbilityCapability abilityCapability = this.getAbilityCapability();
      if (abilityCapability == null) {
         return PlayState.STOP;
      } else if (abilityCapability.getActiveAbility() != null) {
         return abilityCapability.animationPredicate(event, null);
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("test_fail_start", true));
         return PlayState.CONTINUE;
      }
   }

   public boolean m_142535_(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }

   public boolean m_6063_() {
      return false;
   }

   public void m_5997_(double x, double y, double z) {
      super.m_5997_(0.0, y, 0.0);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public void setDesires(ItemStack stack) {
      this.m_20088_().m_135381_(DESIRES, stack);
   }

   public ItemStack getDesires() {
      return (ItemStack)this.m_20088_().m_135370_(DESIRES);
   }

   public boolean fulfillDesire(Slot input) {
      ItemStack desires = this.getDesires();
      if (canPayFor(input.m_7993_(), desires)) {
         input.m_6201_(desires.m_41613_());
         return true;
      } else {
         return false;
      }
   }

   public boolean m_8023_() {
      return true;
   }

   @Override
   public void m_8119_() {
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
      super.m_8119_();
      if (this.testingPlayer == null && this.getTestingPlayerID().isPresent()) {
         this.testingPlayer = this.f_19853_.m_46003_(this.getTestingPlayerID().get());
      }

      if (this.testingPlayer != null) {
         this.m_21563_().m_148051_(this.testingPlayer);
      } else if (this.customer != null) {
         this.m_21563_().m_148051_(this.customer);
      }

      if (this.testing && !this.f_19853_.m_5776_()) {
         if (this.testingPlayer == null) {
            this.sendAbilityMessage(END_TEST);
            this.prevPlayerPosition = Optional.empty();
            this.prevPlayerVelY = Optional.empty();
         } else {
            this.checkIfPlayerCheats();
         }
      }
   }

   private void checkIfPlayerCheats() {
      if (this.isTesting() && this.testingPlayer != null && !this.testingPlayer.m_7500_()) {
         if (this.testingPlayer != null
            && this.testingPlayer.m_20182_().m_82542_(1.0, 0.0, 1.0).m_82554_(this.m_20182_().m_82542_(1.0, 0.0, 1.0)) > (double)(TEST_RADIUS + 3)) {
            this.playerCheated();
         }

         if (this.testingPlayer != null && this.pillar != null && this.testingPlayer.m_20186_() < this.pillar.m_20186_() - 10.0) {
            this.playerCheated();
         }

         if (this.testingPlayer != null && this.testingPlayer.m_150110_().f_35935_) {
            this.playerCheated();
         }

         if (this.testingPlayer != null && !this.testingPlayer.m_20096_()) {
            double playerVelY = this.testingPlayer.m_20184_().m_7098_();
            if (this.prevPlayerVelY != null && this.prevPlayerVelY.isPresent()) {
               double acceleration = playerVelY - this.prevPlayerVelY.get();
               if (acceleration >= 0.0) {
                  this.ticksAcceleratingUpward++;
               } else if (this.ticksAcceleratingUpward > 0) {
                  this.ticksAcceleratingUpward--;
               }

               if (this.ticksAcceleratingUpward > 5) {
                  this.playerCheated();
               }
            }

            this.prevPlayerVelY = Optional.of(playerVelY);
         } else {
            this.ticksAcceleratingUpward = 0;
            this.prevPlayerVelY = Optional.empty();
         }

         if (this.testingPlayer != null) {
            Vec3 currPosition = this.testingPlayer.m_20182_();
            if (this.prevPlayerPosition != null && this.prevPlayerPosition.isPresent() && currPosition.m_82554_(this.prevPlayerPosition.get()) > 3.0) {
               this.playerCheated();
            }

            this.prevPlayerPosition = Optional.of(currPosition);
         }
      }
   }

   public void playerCheated() {
      if (this.isTesting() && this.testingPlayer != null) {
         this.sendAbilityMessage(END_TEST);
      }
   }

   public static double testRadiusAtHeight(double height) {
      return (double)TEST_RADIUS_BOTTOM
         + Math.pow(Math.min(height / (double)TEST_MAX_RADIUS_HEIGHT, 1.0), TEST_RADIUS_FALLOFF) * (double)(TEST_RADIUS - TEST_RADIUS_BOTTOM);
   }

   public void setTrading(boolean trading) {
      this.f_19804_.m_135381_(IS_TRADING, trading);
   }

   public boolean isTrading() {
      return (Boolean)this.f_19804_.m_135370_(IS_TRADING);
   }

   public Player getCustomer() {
      return this.customer;
   }

   public void setCustomer(Player customer) {
      this.setTrading(customer != null);
      this.customer = customer;
   }

   public boolean isTesting() {
      return this.testing;
   }

   public Optional<UUID> getTestingPlayerID() {
      return (Optional<UUID>)this.m_20088_().m_135370_(TESTING_PLAYER);
   }

   public void setTestingPlayerID(UUID playerID) {
      if (playerID == null) {
         this.m_20088_().m_135381_(TESTING_PLAYER, Optional.empty());
      } else {
         this.m_20088_().m_135381_(TESTING_PLAYER, Optional.of(playerID));
      }
   }

   public void setTestingPlayer(Player testingPlayer) {
      this.testingPlayer = testingPlayer;
      this.setTestingPlayerID(testingPlayer == null ? null : testingPlayer.m_20148_());
   }

   public void openGUI(Player playerEntity) {
      this.setCustomer(playerEntity);
      MowziesMobs.PROXY.setReferencedMob(this);
      if (!this.f_19853_.f_46443_ && this.m_5448_() == null && this.m_6084_()) {
         playerEntity.m_5893_(new MenuProvider() {
            public AbstractContainerMenu m_7208_(int id, Inventory playerInventory, Player player) {
               return new ContainerSculptorTrade(id, EntitySculptor.this, playerInventory);
            }

            public Component m_5446_() {
               return EntitySculptor.this.m_5446_();
            }
         });
      }
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (this.isTesting()) {
         if (player == this.testingPlayer) {
            this.sendAbilityMessage(PASS_TEST);
         }
      } else if (this.canTradeWith(player) && this.m_5448_() == null && this.m_6084_()) {
         this.openGUI(player);
         return InteractionResult.SUCCESS;
      }

      return InteractionResult.PASS;
   }

   public boolean canTradeWith(Player player) {
      return !this.isTrading() && !(this.m_21223_() <= 0.0F) && !this.testing;
   }

   public boolean doesItemSatisfyDesire(ItemStack stack) {
      return canPayFor(stack, this.getDesires());
   }

   private static boolean canPayFor(ItemStack stack, ItemStack worth) {
      return stack.m_41720_() == worth.m_41720_() && stack.m_41613_() >= worth.m_41613_();
   }

   public EntityPillar getPillar() {
      return this.pillar;
   }

   public void setPillar(EntityPillar pillar) {
      this.pillar = pillar;
   }

   private <ENTITY extends IAnimatable> void instructionListener(CustomInstructionKeyframeEvent<ENTITY> event) {
      if (event.instructions.contains("closeHandR")) {
         this.handROpen = false;
      }

      if (event.instructions.contains("closeHandL")) {
         this.handLOpen = false;
      }

      if (event.instructions.contains("openHandR")) {
         this.handROpen = true;
      }

      if (event.instructions.contains("openHandL")) {
         this.handLOpen = true;
      }
   }

   @Override
   public void registerControllers(AnimationData data) {
      super.registerControllers(data);
      this.controller.registerCustomInstructionListener(this::instructionListener);
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{START_TEST, END_TEST, PASS_TEST};
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      if (this.testingPlayer != null && this.getTestingPlayerID().isPresent()) {
         compound.m_128362_("TestingPlayer", this.getTestingPlayerID().get());
         compound.m_128405_("NumLivePaths", this.numLivePaths);
      }
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("TestingPlayer")) {
         this.testing = true;
         this.setTestingPlayerID(compound.m_128342_("TestingPlayer"));
         this.numLivePaths = compound.m_128451_("NumLivePaths");
      }
   }

   public static class EndTestAbility extends Ability<EntitySculptor> {
      public EndTestAbility(AbilityType<EntitySculptor, ? extends EntitySculptor.EndTestAbility> abilityType, EntitySculptor user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 20)
            }
         );
      }

      @Override
      public boolean tryAbility() {
         return this.getUser().pillar != null;
      }

      @Override
      public void start() {
         super.start();
         this.playAnimation("test_fail_start", false);
         if (this.getUser().pillar != null) {
            this.getUser().pillar.startFalling();
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            if (!this.getUser().m_9236_().m_5776_() && this.getUser().pillar != null) {
               this.getUser().m_146884_(this.getUser().pillar.m_20182_().m_82520_(0.0, (double)this.getUser().pillar.getHeight(), 0.0));
            }

            if (this.getUser().pillar == null || this.getUser().pillar.m_213877_()) {
               this.nextSection();
            }
         }
      }

      @Override
      public void end() {
         super.end();
         if (this.getUser() != null) {
            this.getUser().pillar = null;
            this.getUser().setTestingPlayer(null);
            this.getUser().testing = false;
            this.getUser().numLivePaths = 0;
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return true;
      }
   }

   public static class PassTestAbility extends EntitySculptor.EndTestAbility {
      public PassTestAbility(AbilityType<EntitySculptor, EntitySculptor.PassTestAbility> abilityType, EntitySculptor user) {
         super(abilityType, user);
      }

      @Override
      public void start() {
         if (this.getUser().testingPlayer != null) {
            List<EntityBoulderPlatform> platforms = this.getUser()
               .m_9236_()
               .m_45976_(EntityBoulderPlatform.class, this.getUser().testingPlayer.m_20191_().m_82363_(0.0, -6.0, 0.0));
            EntityBoulderPlatform platformBelowPlayer = platforms.get(0);
            platformBelowPlayer.descend();
         }

         super.start();
      }

      @Override
      public void end() {
         super.end();
         this.getUser().m_19983_(ItemHandler.EARTHBORE_GAUNTLET.m_7968_());
      }

      @Override
      public boolean canCancelActiveAbility() {
         return false;
      }
   }

   public static class StartTestAbility extends Ability<EntitySculptor> {
      private static int MAX_RANGE_TO_GROUND = 12;
      private BlockPos spawnPillarPos;
      private BlockState spawnPillarBlock;

      public StartTestAbility(AbilityType<EntitySculptor, EntitySculptor.StartTestAbility> abilityType, EntitySculptor user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 18),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, (int)((float)EntitySculptor.TEST_HEIGHT / 0.25F))
            }
         );
      }

      @Override
      public boolean tryAbility() {
         Vec3 from = this.getUser().m_20182_();
         Vec3 to = from.m_82492_(0.0, (double)MAX_RANGE_TO_GROUND, 0.0);
         BlockHitResult result = this.getUser().f_19853_.m_45547_(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
         if (result.m_6662_() == Type.MISS) {
            return false;
         } else {
            this.spawnPillarPos = result.m_82425_();
            this.spawnPillarBlock = this.getUser().f_19853_.m_8055_(this.spawnPillarPos);
            if (result.m_82434_() != Direction.UP) {
               BlockState blockAbove = this.getUser().f_19853_.m_8055_(this.spawnPillarPos.m_7494_());
               if (blockAbove.m_60828_(this.getUser().f_19853_, this.spawnPillarPos.m_7494_()) || blockAbove.m_60795_()) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public void start() {
         super.start();
         this.playAnimation("testStart", false);
         this.getUser().testing = true;
         this.getUser().setTestingPlayer(this.getUser().getCustomer());
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.spawnPillarPos != null) {
            if (!this.getUser().m_9236_().m_5776_()) {
               EntityBlockSwapper.EntityBlockSwapperSculptor swapper = new EntityBlockSwapper.EntityBlockSwapperSculptor(
                  (EntityType<? extends EntityBlockSwapper.EntityBlockSwapperSculptor>)EntityHandler.BLOCK_SWAPPER_SCULPTOR.get(),
                  this.getUser().m_9236_(),
                  this.getUser().m_20183_(),
                  Blocks.f_50016_.m_49966_(),
                  60,
                  false,
                  false
               );
               this.getUser().m_9236_().m_7967_(swapper);
            }

            if (this.spawnPillarBlock == null || !EffectGeomancy.isBlockDiggable(this.spawnPillarBlock)) {
               this.spawnPillarBlock = Blocks.f_50069_.m_49966_();
            }

            this.getUser().pillar = new EntityPillar(
               (EntityType<? extends EntityPillar>)EntityHandler.PILLAR.get(),
               this.getUser().f_19853_,
               this.getUser(),
               Blocks.f_50069_.m_49966_(),
               this.spawnPillarPos
            );
            this.getUser().pillar.setTier(EntityGeomancyBase.GeomancyTier.SMALL);
            this.getUser()
               .pillar
               .m_6034_(
                  (double)((float)this.spawnPillarPos.m_123341_() + 0.5F),
                  (double)(this.spawnPillarPos.m_123342_() + 1),
                  (double)((float)this.spawnPillarPos.m_123343_() + 0.5F)
               );
            this.getUser().pillar.setDoRemoveTimer(false);
            if (this.getUser().pillar.checkCanSpawn()) {
               this.getUser().f_19853_.m_7967_(this.getUser().pillar);
            }

            int numStartBoulders = this.rand.nextInt(2, 5);
            float angleOffset = this.rand.nextFloat((float) (Math.PI * 2));

            for (int i = 0; i < numStartBoulders; i++) {
               float angleInc = (float) (Math.PI * 2) / ((float)numStartBoulders * 2.0F);
               float angle = angleOffset + angleInc * (float)(i * 2) + this.rand.nextFloat(angleInc);
               Vec3 spawnBoulderPos = this.getUser().pillar.m_20182_().m_82549_(new Vec3((double)this.rand.nextFloat(3.0F, 6.0F), 0.0, 0.0).m_82524_(angle));
               EntityBoulderPlatform boulderPlatform = new EntityBoulderPlatform(
                  (EntityType<? extends EntityBoulderBase>)EntityHandler.BOULDER_PLATFORM.get(),
                  this.getUser().m_9236_(),
                  this.getUser(),
                  Blocks.f_50069_.m_49966_(),
                  BlockPos.f_121853_,
                  EntityGeomancyBase.GeomancyTier.MEDIUM
               );
               boulderPlatform.m_146884_(spawnBoulderPos.m_82520_(0.0, 1.0, 0.0));
               if (i == 0) {
                  boulderPlatform.setMainPath();
               }

               this.getUser().m_9236_().m_7967_(boulderPlatform);
            }

            this.getUser().numLivePaths = numStartBoulders;
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            for (Player player : this.getUser().getPlayersNearby(5.0, 5.0, 5.0, 5.0)) {
               Vec3 userPos = this.getUser().m_20182_().m_82542_(1.0, 0.0, 1.0);
               Vec3 playerPos = player.m_20182_().m_82542_(1.0, 0.0, 1.0);
               Vec3 vec = userPos.m_82546_(playerPos).m_82541_().m_82490_(-Math.min(1.0 / userPos.m_82557_(playerPos), 2.0));
               player.m_5997_(vec.f_82479_, vec.f_82480_, vec.f_82481_);
            }

            if (!this.getUser().m_9236_().m_5776_() && this.getUser().pillar != null) {
               this.getUser().m_146884_(this.getUser().pillar.m_20182_().m_82520_(0.0, (double)this.getUser().pillar.getHeight(), 0.0));
            }

            if (this.getUser().pillar != null && this.getUser().pillar.getHeight() > (float)EntitySculptor.TEST_HEIGHT) {
               this.nextSection();
            }
         }
      }

      @Override
      public void end() {
         super.end();
         if (this.getUser().pillar != null) {
            this.getUser().pillar.stopRising();
         }

         this.getUser().numLivePaths = 0;
      }
   }
}
