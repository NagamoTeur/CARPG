package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.IAnimationListener;
import com.hollingsworth.arsnouveau.common.entity.goal.GoBackHomeGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.wealdwalker.CastSpellGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.wealdwalker.SmashGoal;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class WealdWalker extends AgeableMob implements IAnimatable, IAnimationListener, RangedAttackMob, IWandable, ITooltipProvider {
   public static final EntityDataAccessor<Boolean> SMASHING = SynchedEntityData.m_135353_(WealdWalker.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> CASTING = SynchedEntityData.m_135353_(WealdWalker.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.m_135353_(WealdWalker.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Optional<BlockPos>> HOME = SynchedEntityData.m_135353_(WealdWalker.class, EntityDataSerializers.f_135039_);
   public int smashCooldown;
   public int castCooldown;
   public Spell spell = new Spell();
   public ParticleColor color = ParticleColor.defaultParticleColor();
   AnimationController attackController;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public WealdWalker(EntityType<? extends AgeableMob> type, Level world) {
      super(type, world);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.smashCooldown > 0) {
         this.smashCooldown--;
      }

      if (this.castCooldown > 0) {
         this.castCooldown--;
      }

      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46467_() % 20L == 0L && !this.m_21224_()) {
         this.m_5634_(1.0F);
      }
   }

   public InteractionResult m_6071_(Player p_230254_1_, InteractionHand p_230254_2_) {
      ItemStack itemstack = p_230254_1_.m_21120_(p_230254_2_);
      if (itemstack.m_41720_() instanceof BoneMealItem && this.m_6162_()) {
         int i = this.m_146764_();
         if (this.m_6162_()) {
            this.usePlayerItem(p_230254_1_, itemstack);
            this.m_146740_((int)((float)(-i / 20) * 0.1F), true);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }

         if (this.f_19853_.f_46443_) {
            return InteractionResult.CONSUME;
         }
      }

      return super.m_6071_(p_230254_1_, p_230254_2_);
   }

   protected void usePlayerItem(Player p_175505_1_, ItemStack p_175505_2_) {
      if (!p_175505_1_.m_150110_().f_35937_) {
         p_175505_2_.m_41774_(1);
      }
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         this.setHome(storedPos);
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.home_set"));
      }
   }

   public EntityDimensions m_6972_(Pose p_213305_1_) {
      return this.m_6162_() ? new EntityDimensions(1.0F, 1.0F, true) : super.m_6972_(p_213305_1_);
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
      return null;
   }

   public void m_6667_(DamageSource source) {
      if (!this.m_6162_() && !this.f_19853_.f_46443_) {
         this.m_6863_(true);
         this.m_6210_();
         this.m_21153_(60.0F);
         ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_().m_7494_());
         if (source.m_7639_() != null && source.m_7639_() instanceof Mob) {
            ((Mob)source.m_7639_()).m_6710_(null);
         }
      } else {
         super.m_6667_(source);
      }
   }

   public void m_6863_(boolean baby) {
      super.m_6863_(baby);
      this.f_19804_.m_135381_(BABY, baby);
   }

   public void m_146762_(int age) {
      this.f_146733_ = age;
      if (this.f_146733_ >= 0 && !this.f_19853_.f_46443_) {
         this.m_30232_();
      }
   }

   public void setHome(BlockPos home) {
      this.f_19804_.m_135381_(HOME, Optional.of(home));
   }

   @Nullable
   public BlockPos getHome() {
      return (BlockPos)((Optional)this.f_19804_.m_135370_(HOME)).orElse(null);
   }

   protected void m_30232_() {
      super.m_30232_();
      if (!this.f_19853_.f_46443_) {
         this.f_19804_.m_135381_(BABY, false);
      }
   }

   public boolean m_6162_() {
      return (Boolean)this.f_19804_.m_135370_(BABY);
   }

   public void m_7350_(EntityDataAccessor<?> p_184206_1_) {
      if (BABY.equals(p_184206_1_)) {
         this.m_6210_();
      }

      super.m_7350_(p_184206_1_);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new GoBackHomeGoal(this, this::getHome, 10, () -> this.m_5448_() == null || this.m_6162_()));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Mob.class, false, entity -> {
         if (entity instanceof TamableAnimal tamableAnimal && tamableAnimal.m_21824_()) {
            return false;
         }

         return entity instanceof Enemy;
      }));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_
         .m_25352_(
            2,
            new SmashGoal(this, true, () -> this.smashCooldown <= 0 && !(Boolean)this.f_19804_.m_135370_(BABY), WealdWalker.Animations.SMASH.ordinal(), 25, 5)
         );
      this.f_21345_
         .m_25352_(
            2,
            new CastSpellGoal(
               this, 1.2, 20, 15.0F, () -> this.castCooldown <= 0 && !(Boolean)this.f_19804_.m_135370_(BABY), WealdWalker.Animations.CAST.ordinal(), 20
            )
         );
   }

   public boolean m_7307_(Entity pEntity) {
      return !(pEntity instanceof Enemy) || pEntity instanceof TamableAnimal tamableAnimal && tamableAnimal.m_21824_() || super.m_7307_(pEntity);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SMASHING, false);
      this.f_19804_.m_135372_(CASTING, false);
      this.f_19804_.m_135372_(BABY, false);
      this.f_19804_.m_135372_(HOME, Optional.empty());
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128379_("isBaby", (Boolean)this.f_19804_.m_135370_(BABY));
      NBTUtil.storeBlockPos(tag, "home", this.getHome());
      tag.m_128405_("smash", this.smashCooldown);
      tag.m_128405_("cast", this.castCooldown);
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return source != DamageSource.f_19314_ && source != DamageSource.f_19325_ && source != DamageSource.f_19312_ ? super.m_6469_(source, amount) : false;
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.f_19804_.m_135381_(BABY, tag.m_128471_("isBaby"));
      if (NBTUtil.hasBlockPos(tag, "home")) {
         this.setHome(NBTUtil.getBlockPos(tag, "home"));
      }

      this.smashCooldown = tag.m_128451_("smash");
      this.castCooldown = tag.m_128451_("cast");
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "run_controller", 1.0F, this::runController));
      this.attackController = new AnimationController<>(this, "attack_controller", 5.0F, this::attackController);
      data.addAnimationController(this.attackController);
   }

   private PlayState attackController(AnimationEvent<?> animationEvent) {
      return PlayState.CONTINUE;
   }

   private PlayState runController(AnimationEvent<?> animationEvent) {
      if ((Boolean)this.f_19804_.m_135370_(SMASHING) || (Boolean)this.f_19804_.m_135370_(CASTING)) {
         return PlayState.STOP;
      } else if (animationEvent.getController().getCurrentAnimation() != null
         && !animationEvent.getController().getCurrentAnimation().animationName.equals("run_master")) {
         return PlayState.STOP;
      } else if (animationEvent.isMoving()) {
         animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("run_master"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void startAnimation(int arg) {
      try {
         if (arg == WealdWalker.Animations.SMASH.ordinal()) {
            if (this.attackController.getCurrentAnimation() != null && this.attackController.getCurrentAnimation().animationName.equals("smash")) {
               return;
            }

            this.attackController.markNeedsReload();
            this.attackController.setAnimation(new AnimationBuilder().addAnimation("smash").addAnimation("idle"));
         }

         if (arg == WealdWalker.Animations.CAST.ordinal()) {
            if (this.attackController.getCurrentAnimation() != null && this.attackController.getCurrentAnimation().animationName.equals("cast")) {
               return;
            }

            this.attackController.markNeedsReload();
            this.attackController.setAnimation(new AnimationBuilder().addAnimation("cast").addAnimation("idle"));
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public static Builder attributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 60.0)
         .m_22268_(Attributes.f_22279_, 0.2)
         .m_22268_(Attributes.f_22277_, 16.0)
         .m_22268_(Attributes.f_22281_, 10.5);
   }

   public boolean m_6785_(double p_213397_1_) {
      return false;
   }

   public int m_213860_() {
      return 0;
   }

   public void m_6504_(LivingEntity entity, float p_82196_2_) {
      EntitySpellResolver resolver = new EntitySpellResolver(new SpellContext(this.f_19853_, this.spell, this, new LivingCaster(this)).withColors(this.color));
      EntityProjectileSpell projectileSpell = new EntityProjectileSpell(this.f_19853_, resolver);
      projectileSpell.setColor(this.color);
      projectileSpell.shoot(this, this.m_146909_(), this.m_146908_(), 0.0F, 1.0F, 0.8F);
      this.f_19853_.m_7967_(projectileSpell);
      this.castCooldown = 40;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.getHome() != null) {
         String home = this.getHome().m_123341_() + ", " + this.getHome().m_123342_() + ", " + this.getHome().m_123343_();
         tooltip.add(Component.m_237110_("ars_nouveau.weald_walker.home", new Object[]{home}));
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.weald_walker.home", new Object[]{Component.m_237115_("ars_nouveau.nothing").getString()}));
      }
   }

   public static enum Animations {
      CAST,
      SMASH;
   }
}
