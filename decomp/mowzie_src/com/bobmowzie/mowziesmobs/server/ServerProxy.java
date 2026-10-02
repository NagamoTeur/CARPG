package com.bobmowzie.mowziesmobs.server;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import com.bobmowzie.mowziesmobs.server.message.MessageBlackPinkInYourArea;
import com.bobmowzie.mowziesmobs.server.message.MessageFreezeEffect;
import com.bobmowzie.mowziesmobs.server.message.MessageInterruptAbility;
import com.bobmowzie.mowziesmobs.server.message.MessageJumpToAbilitySection;
import com.bobmowzie.mowziesmobs.server.message.MessageLinkEntities;
import com.bobmowzie.mowziesmobs.server.message.MessagePlayerAttackMob;
import com.bobmowzie.mowziesmobs.server.message.MessagePlayerSolarBeam;
import com.bobmowzie.mowziesmobs.server.message.MessagePlayerSummonSunstrike;
import com.bobmowzie.mowziesmobs.server.message.MessagePlayerUseAbility;
import com.bobmowzie.mowziesmobs.server.message.MessageSculptorTrade;
import com.bobmowzie.mowziesmobs.server.message.MessageSunblockEffect;
import com.bobmowzie.mowziesmobs.server.message.MessageUmvuthiTrade;
import com.bobmowzie.mowziesmobs.server.message.MessageUpdateBossBar;
import com.bobmowzie.mowziesmobs.server.message.MessageUseAbility;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageLeftMouseDown;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageLeftMouseUp;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageRightMouseDown;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageRightMouseUp;
import com.ilexiconn.llibrary.server.network.AnimationMessage;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;

public class ServerProxy {
   private int nextMessageId;
   public static final EntityDataSerializer<Optional<Trade>> OPTIONAL_TRADE = new EntityDataSerializer<Optional<Trade>>() {
      public void write(FriendlyByteBuf buf, Optional<Trade> value) {
         if (value.isPresent()) {
            Trade trade = value.get();
            buf.m_130055_(trade.getInput());
            buf.m_130055_(trade.getOutput());
            buf.writeInt(trade.getWeight());
         } else {
            buf.m_130055_(ItemStack.f_41583_);
         }
      }

      public Optional<Trade> read(FriendlyByteBuf buf) {
         ItemStack input = buf.m_130267_();
         return input == ItemStack.f_41583_ ? Optional.empty() : Optional.of(new Trade(input, buf.m_130267_(), buf.readInt()));
      }

      public EntityDataAccessor<Optional<Trade>> m_135021_(int id) {
         return new EntityDataAccessor(id, this);
      }

      public Optional<Trade> copy(Optional<Trade> value) {
         return value.isPresent() ? Optional.of(new Trade(value.get())) : Optional.empty();
      }
   };

   public void init(IEventBus modbus) {
      ModLoadingContext.get().registerConfig(Type.COMMON, ConfigHandler.COMMON_CONFIG);
      EntityDataSerializers.m_135050_(OPTIONAL_TRADE);
   }

   public void onLateInit(IEventBus modbus) {
   }

   public void playSunstrikeSound(EntitySunstrike strike) {
   }

   public void playIceBreathSound(Entity entity) {
   }

   public void playBoulderChargeSound(LivingEntity player) {
   }

   public void playNagaSwoopSound(EntityNaga naga) {
   }

   public void playBlackPinkSound(AbstractMinecart entity) {
   }

   public void playSunblockSound(LivingEntity entity) {
   }

   public void minecartParticles(ClientLevel world, AbstractMinecart minecart, float scale, double x, double y, double z, BlockState state, BlockPos pos) {
   }

   public void initNetwork() {
      String version = "1";
      MowziesMobs.NETWORK = ChannelBuilder.named(new ResourceLocation("mowziesmobs", "net"))
         .networkProtocolVersion(() -> "1")
         .clientAcceptedVersions("1"::equals)
         .serverAcceptedVersions("1"::equals)
         .simpleChannel();
      this.registerMessage(AnimationMessage.class, AnimationMessage::serialize, AnimationMessage::deserialize, new AnimationMessage.Handler());
      this.registerMessage(MessageLeftMouseDown.class, MessageLeftMouseDown::serialize, MessageLeftMouseDown::deserialize, new MessageLeftMouseDown.Handler());
      this.registerMessage(MessageLeftMouseUp.class, MessageLeftMouseUp::serialize, MessageLeftMouseUp::deserialize, new MessageLeftMouseUp.Handler());
      this.registerMessage(
         MessageRightMouseDown.class, MessageRightMouseDown::serialize, MessageRightMouseDown::deserialize, new MessageRightMouseDown.Handler()
      );
      this.registerMessage(MessageRightMouseUp.class, MessageRightMouseUp::serialize, MessageRightMouseUp::deserialize, new MessageRightMouseUp.Handler());
      this.registerMessage(MessageFreezeEffect.class, MessageFreezeEffect::serialize, MessageFreezeEffect::deserialize, new MessageFreezeEffect.Handler());
      this.registerMessage(MessageUmvuthiTrade.class, MessageUmvuthiTrade::serialize, MessageUmvuthiTrade::deserialize, new MessageUmvuthiTrade.Handler());
      this.registerMessage(
         MessageBlackPinkInYourArea.class,
         MessageBlackPinkInYourArea::serialize,
         MessageBlackPinkInYourArea::deserialize,
         new MessageBlackPinkInYourArea.Handler()
      );
      this.registerMessage(
         MessagePlayerAttackMob.class, MessagePlayerAttackMob::serialize, MessagePlayerAttackMob::deserialize, new MessagePlayerAttackMob.Handler()
      );
      this.registerMessage(
         MessagePlayerSolarBeam.class, MessagePlayerSolarBeam::serialize, MessagePlayerSolarBeam::deserialize, new MessagePlayerSolarBeam.Handler()
      );
      this.registerMessage(
         MessagePlayerSummonSunstrike.class,
         MessagePlayerSummonSunstrike::serialize,
         MessagePlayerSummonSunstrike::deserialize,
         new MessagePlayerSummonSunstrike.Handler()
      );
      this.registerMessage(
         MessageSunblockEffect.class, MessageSunblockEffect::serialize, MessageSunblockEffect::deserialize, new MessageSunblockEffect.Handler()
      );
      this.registerMessage(MessageUseAbility.class, MessageUseAbility::serialize, MessageUseAbility::deserialize, new MessageUseAbility.Handler());
      this.registerMessage(
         MessagePlayerUseAbility.class, MessagePlayerUseAbility::serialize, MessagePlayerUseAbility::deserialize, new MessagePlayerUseAbility.Handler()
      );
      this.registerMessage(
         MessageInterruptAbility.class, MessageInterruptAbility::serialize, MessageInterruptAbility::deserialize, new MessageInterruptAbility.Handler()
      );
      this.registerMessage(
         MessageJumpToAbilitySection.class,
         MessageJumpToAbilitySection::serialize,
         MessageJumpToAbilitySection::deserialize,
         new MessageJumpToAbilitySection.Handler()
      );
      this.registerMessage(MessageSculptorTrade.class, MessageSculptorTrade::serialize, MessageSculptorTrade::deserialize, new MessageSculptorTrade.Handler());
      this.registerMessage(MessageLinkEntities.class, MessageLinkEntities::serialize, MessageLinkEntities::deserialize, new MessageLinkEntities.Handler());
      this.registerMessage(MessageUpdateBossBar.class, MessageUpdateBossBar::serialize, MessageUpdateBossBar::deserialize, new MessageUpdateBossBar.Handler());
   }

   private <MSG> void registerMessage(
      Class<MSG> clazz, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<Context>> consumer
   ) {
      MowziesMobs.NETWORK.registerMessage(this.nextMessageId++, clazz, encoder, decoder, consumer);
   }

   public void setTPS(float tickRate) {
   }

   public Entity getReferencedMob() {
      return null;
   }

   public void setReferencedMob(Entity referencedMob) {
   }
}
