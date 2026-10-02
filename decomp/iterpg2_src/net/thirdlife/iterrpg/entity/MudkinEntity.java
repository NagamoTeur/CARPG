package net.thirdlife.iterrpg.entity;

import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.MudkinAiProcedure;

public class MudkinEntity extends TamableAnimal {
   public MudkinEntity(SpawnEntity packet, Level world) {
      this((EntityType<MudkinEntity>)IterRpgModEntities.MUDKIN.get(), world);
   }

   public MudkinEntity(EntityType<MudkinEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 1;
      this.m_21557_(false);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new PanicGoal(this, 1.2));
      this.f_21345_.m_25352_(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(3, new RandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(5, new FloatGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:mudkin_ambient"));
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:mudkin_hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:mudkin_death"));
   }

   public InteractionResult m_6071_(Player sourceentity, InteractionHand hand) {
      ItemStack itemstack = sourceentity.m_21120_(hand);
      InteractionResult retval = InteractionResult.m_19078_(this.f_19853_.m_5776_());
      Item item = itemstack.m_41720_();
      if (itemstack.m_41720_() instanceof SpawnEggItem) {
         retval = super.m_6071_(sourceentity, hand);
      } else if (this.f_19853_.m_5776_()) {
         retval = (!this.m_21824_() || !this.m_21830_(sourceentity)) && !this.m_6898_(itemstack)
            ? InteractionResult.PASS
            : InteractionResult.m_19078_(this.f_19853_.m_5776_());
      } else if (this.m_21824_()) {
         if (this.m_21830_(sourceentity)) {
            if (item.m_41472_() && this.m_6898_(itemstack) && this.m_21223_() < this.m_21233_()) {
               this.m_142075_(sourceentity, hand, itemstack);
               this.m_5634_((float)item.m_41473_().m_38744_());
               retval = InteractionResult.m_19078_(this.f_19853_.m_5776_());
            } else if (this.m_6898_(itemstack) && this.m_21223_() < this.m_21233_()) {
               this.m_142075_(sourceentity, hand, itemstack);
               this.m_5634_(4.0F);
               retval = InteractionResult.m_19078_(this.f_19853_.m_5776_());
            } else {
               retval = super.m_6071_(sourceentity, hand);
            }
         }
      } else if (this.m_6898_(itemstack)) {
         this.m_142075_(sourceentity, hand, itemstack);
         if (this.f_19796_.m_188503_(3) == 0 && !ForgeEventFactory.onAnimalTame(this, sourceentity)) {
            this.m_21828_(sourceentity);
            this.f_19853_.m_7605_(this, (byte)7);
         } else {
            this.f_19853_.m_7605_(this, (byte)6);
         }

         this.m_21530_();
         retval = InteractionResult.m_19078_(this.f_19853_.m_5776_());
      } else {
         retval = super.m_6071_(sourceentity, hand);
         if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME) {
            this.m_21530_();
         }
      }

      return retval;
   }

   public void m_6075_() {
      super.m_6075_();
      MudkinAiProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public AgeableMob m_142606_(ServerLevel serverWorld, AgeableMob ageable) {
      MudkinEntity retval = (MudkinEntity)((EntityType)IterRpgModEntities.MUDKIN.get()).m_20615_(serverWorld);
      retval.m_6518_(serverWorld, serverWorld.m_6436_(retval.m_20183_()), MobSpawnType.BREEDING, null, null);
      return retval;
   }

   public boolean m_6898_(ItemStack stack) {
      return List.of().contains(stack.m_41720_());
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.25);
      builder = builder.m_22268_(Attributes.f_22276_, 16.0);
      builder = builder.m_22268_(Attributes.f_22284_, 4.0);
      builder = builder.m_22268_(Attributes.f_22281_, 1.0);
      return builder.m_22268_(Attributes.f_22277_, 16.0);
   }
}
