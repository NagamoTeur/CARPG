package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.client.IVariantColorProvider;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.IAnimationListener;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.entity.goal.wixie.CompleteCraftingGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.wixie.FindNextItemGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.wixie.FindPotionGoal;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Items;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class EntityWixie extends AbstractFlyingCreature implements IAnimatable, IAnimationListener, IDispellable, IVariantColorProvider<EntityWixie> {
   AnimationFactory manager = GeckoLibUtil.createFactory(this);
   public static final EntityDataAccessor<String> COLOR = SynchedEntityData.m_135353_(EntityWixie.class, EntityDataSerializers.f_135030_);
   public BlockPos cauldronPos;
   public int inventoryBackoff;
   AnimationController<?> summonController;
   AnimationController<?> castController;
   public static String[] COLORS = new String[]{"white", "green", "blue", "black", "red"};

   private <P extends IAnimatable> PlayState idlePredicate(AnimationEvent<P> event) {
      if (this.m_21573_().m_26572_()) {
         return PlayState.STOP;
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
         return PlayState.CONTINUE;
      }
   }

   private <P extends IAnimatable> PlayState castPredicate(AnimationEvent<P> event) {
      return PlayState.CONTINUE;
   }

   private <P extends IAnimatable> PlayState summonPredicate(AnimationEvent<P> event) {
      return PlayState.CONTINUE;
   }

   public int m_213860_() {
      return 0;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return SummonUtil.canSummonTakeDamage(pSource) && super.m_6469_(pSource, pAmount);
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController<>(this, "idleController", 20.0F, this::idlePredicate));
      this.castController = new AnimationController<>(this, "castController", 1.0F, this::castPredicate);
      this.summonController = new AnimationController<>(this, "summonController", 1.0F, this::summonPredicate);
      animationData.addAnimationController(this.castController);
      animationData.addAnimationController(this.summonController);
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   protected EntityWixie(EntityType<? extends AbstractFlyingCreature> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
   }

   public EntityWixie(Level world, BlockPos pos) {
      this((EntityType<? extends AbstractFlyingCreature>)ModEntities.ENTITY_WIXIE_TYPE.get(), world);
      this.cauldronPos = pos;
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
      if (!this.f_19853_.f_46443_ && (this.cauldronPos == null || !(this.f_19853_.m_7702_(this.cauldronPos) instanceof WixieCauldronTile))) {
         this.m_6469_(DamageSource.m_19344_(ANFakePlayer.getPlayer((ServerLevel)this.f_19853_)), 99.0F);
      }

      if (!this.f_19853_.f_46443_ && this.inventoryBackoff > 0) {
         this.inventoryBackoff--;
      }
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(2, new FindNextItemGoal(this));
      this.f_21345_.m_25352_(2, new FindPotionGoal(this));
      this.f_21345_.m_25352_(1, new CompleteCraftingGoal(this));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(COLOR, "blue");
   }

   public boolean m_6785_(double p_213397_1_) {
      return false;
   }

   protected PathNavigation m_6037_(Level world) {
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, world);
      flyingpathnavigator.m_26440_(false);
      flyingpathnavigator.m_7008_(true);
      flyingpathnavigator.m_26443_(true);
      return flyingpathnavigator;
   }

   public static Builder attributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22280_, Attributes.f_22280_.m_22082_())
         .m_22268_(Attributes.f_22276_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.2);
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128441_("summoner_x")) {
         this.cauldronPos = new BlockPos(tag.m_128451_("summoner_x"), tag.m_128451_("summoner_y"), tag.m_128451_("summoner_z"));
      }

      if (tag.m_128441_("color")) {
         this.f_19804_.m_135381_(COLOR, tag.m_128461_("color"));
      }
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      if (this.cauldronPos != null) {
         tag.m_128405_("summoner_x", this.cauldronPos.m_123341_());
         tag.m_128405_("summoner_y", this.cauldronPos.m_123342_());
         tag.m_128405_("summoner_z", this.cauldronPos.m_123343_());
      }

      tag.m_128359_("color", (String)this.f_19804_.m_135370_(COLOR));
   }

   @Override
   public void startAnimation(int arg) {
      if (arg == EntityWixie.Animations.CAST.ordinal() && this.castController != null) {
         this.castController.markNeedsReload();
         this.castController.setAnimation(new AnimationBuilder().addAnimation("cast"));
      } else if (arg == EntityWixie.Animations.SUMMON_ITEM.ordinal() && this.summonController != null) {
         this.summonController.markNeedsReload();
         this.summonController.setAnimation(new AnimationBuilder().addAnimation("summon_item"));
      }
   }

   public void m_6667_(DamageSource source) {
      if (!this.f_19853_.f_46443_) {
         ItemStack stack = new ItemStack(ItemsRegistry.WIXIE_CHARM);
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
      }

      super.m_6667_(source);
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      if (this.m_213877_()) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_) {
            ItemStack stack = new ItemStack(ItemsRegistry.WIXIE_CHARM);
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack.m_41777_()));
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
         }

         return true;
      }
   }

   public ResourceLocation getTexture(EntityWixie entity) {
      String color = this.getColor(entity).toLowerCase();
      if (color.isEmpty()) {
         color = "blue";
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/wixie_" + color + ".png");
   }

   public String getColor(EntityWixie entityWixie) {
      return (String)this.m_20088_().m_135370_(COLOR);
   }

   public void setColor(String color, EntityWixie entityWixie) {
      this.m_20088_().m_135381_(COLOR, color);
   }

   public static enum Animations {
      CAST,
      SUMMON_ITEM;
   }
}
