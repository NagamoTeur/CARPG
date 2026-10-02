package net.thirdlife.iterrpg.network;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;
import net.thirdlife.iterrpg.IterRpgMod;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class IterRpgModVariables {
   public static final Capability<IterRpgModVariables.PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(
      new CapabilityToken<IterRpgModVariables.PlayerVariables>() {
      }
   );

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      IterRpgMod.addNetworkMessage(
         IterRpgModVariables.PlayerVariablesSyncMessage.class,
         IterRpgModVariables.PlayerVariablesSyncMessage::buffer,
         IterRpgModVariables.PlayerVariablesSyncMessage::new,
         IterRpgModVariables.PlayerVariablesSyncMessage::handler
      );
   }

   @SubscribeEvent
   public static void init(RegisterCapabilitiesEvent event) {
      event.register(IterRpgModVariables.PlayerVariables.class);
   }

   @EventBusSubscriber
   public static class EventBusVariableHandlers {
      @SubscribeEvent
      public static void onPlayerLoggedInSyncPlayerVariables(PlayerLoggedInEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            ((IterRpgModVariables.PlayerVariables)event.getEntity()
                  .getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerRespawnedSyncPlayerVariables(PlayerRespawnEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            ((IterRpgModVariables.PlayerVariables)event.getEntity()
                  .getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerChangedDimensionEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            ((IterRpgModVariables.PlayerVariables)event.getEntity()
                  .getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void clonePlayer(Clone event) {
         event.getOriginal().revive();
         IterRpgModVariables.PlayerVariables original = (IterRpgModVariables.PlayerVariables)event.getOriginal()
            .getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new IterRpgModVariables.PlayerVariables());
         IterRpgModVariables.PlayerVariables clone = (IterRpgModVariables.PlayerVariables)event.getEntity()
            .getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new IterRpgModVariables.PlayerVariables());
         clone.Mana = original.Mana;
         clone.MaxMana = original.MaxMana;
         clone.ManaRegen = original.ManaRegen;
         clone.Catalyst = original.Catalyst;
         clone.ManaRounded = original.ManaRounded;
         clone.ManaOverload = original.ManaOverload;
         clone.MageStatus = original.MageStatus;
         clone.ManaRegenMultiplier = original.ManaRegenMultiplier;
         clone.BaseManaRegen = original.BaseManaRegen;
         clone.BaseManaCapacity = original.BaseManaCapacity;
         clone.ManaCapacityMultiplier = original.ManaCapacityMultiplier;
         clone.ItemManaRegen = original.ItemManaRegen;
         clone.ItemManaCapacity = original.ItemManaCapacity;
         clone.MagicCooldown = original.MagicCooldown;
         clone.ManaAcceleration = original.ManaAcceleration;
         clone.ManabarStyle = original.ManabarStyle;
         clone.ElementalArmorCooldown = original.ElementalArmorCooldown;
         clone.MeleeAttackCooldown = original.MeleeAttackCooldown;
         clone.SetBonusToggle = original.SetBonusToggle;
         clone.ElementalArmorPassiveCooldown = original.ElementalArmorPassiveCooldown;
         if (!event.isWasDeath()) {
         }
      }
   }

   public static class PlayerVariables {
      public double Mana = 0.0;
      public double MaxMana = 0.0;
      public double ManaRegen = 0.0;
      public boolean Catalyst = false;
      public double ManaRounded = 0.0;
      public boolean ManaOverload = false;
      public boolean MageStatus = false;
      public double ManaRegenMultiplier = 0.0;
      public double BaseManaRegen = 0.0;
      public double BaseManaCapacity = 0.0;
      public double ManaCapacityMultiplier = 0.0;
      public double ItemManaRegen = 0.0;
      public double ItemManaCapacity = 0.0;
      public double MagicCooldown = 0.0;
      public double ManaAcceleration = 0.0;
      public double ManabarStyle = 0.0;
      public double ElementalArmorCooldown = 0.0;
      public double MeleeAttackCooldown = 0.0;
      public boolean SetBonusToggle = true;
      public double ElementalArmorPassiveCooldown = 0.0;

      public void syncPlayerVariables(Entity entity) {
         if (entity instanceof ServerPlayer serverPlayer) {
            IterRpgMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new IterRpgModVariables.PlayerVariablesSyncMessage(this));
         }
      }

      public Tag writeNBT() {
         CompoundTag nbt = new CompoundTag();
         nbt.m_128347_("Mana", this.Mana);
         nbt.m_128347_("MaxMana", this.MaxMana);
         nbt.m_128347_("ManaRegen", this.ManaRegen);
         nbt.m_128379_("Catalyst", this.Catalyst);
         nbt.m_128347_("ManaRounded", this.ManaRounded);
         nbt.m_128379_("ManaOverload", this.ManaOverload);
         nbt.m_128379_("MageStatus", this.MageStatus);
         nbt.m_128347_("ManaRegenMultiplier", this.ManaRegenMultiplier);
         nbt.m_128347_("BaseManaRegen", this.BaseManaRegen);
         nbt.m_128347_("BaseManaCapacity", this.BaseManaCapacity);
         nbt.m_128347_("ManaCapacityMultiplier", this.ManaCapacityMultiplier);
         nbt.m_128347_("ItemManaRegen", this.ItemManaRegen);
         nbt.m_128347_("ItemManaCapacity", this.ItemManaCapacity);
         nbt.m_128347_("MagicCooldown", this.MagicCooldown);
         nbt.m_128347_("ManaAcceleration", this.ManaAcceleration);
         nbt.m_128347_("ManabarStyle", this.ManabarStyle);
         nbt.m_128347_("ElementalArmorCooldown", this.ElementalArmorCooldown);
         nbt.m_128347_("MeleeAttackCooldown", this.MeleeAttackCooldown);
         nbt.m_128379_("SetBonusToggle", this.SetBonusToggle);
         nbt.m_128347_("ElementalArmorPassiveCooldown", this.ElementalArmorPassiveCooldown);
         return nbt;
      }

      public void readNBT(Tag Tag) {
         CompoundTag nbt = (CompoundTag)Tag;
         this.Mana = nbt.m_128459_("Mana");
         this.MaxMana = nbt.m_128459_("MaxMana");
         this.ManaRegen = nbt.m_128459_("ManaRegen");
         this.Catalyst = nbt.m_128471_("Catalyst");
         this.ManaRounded = nbt.m_128459_("ManaRounded");
         this.ManaOverload = nbt.m_128471_("ManaOverload");
         this.MageStatus = nbt.m_128471_("MageStatus");
         this.ManaRegenMultiplier = nbt.m_128459_("ManaRegenMultiplier");
         this.BaseManaRegen = nbt.m_128459_("BaseManaRegen");
         this.BaseManaCapacity = nbt.m_128459_("BaseManaCapacity");
         this.ManaCapacityMultiplier = nbt.m_128459_("ManaCapacityMultiplier");
         this.ItemManaRegen = nbt.m_128459_("ItemManaRegen");
         this.ItemManaCapacity = nbt.m_128459_("ItemManaCapacity");
         this.MagicCooldown = nbt.m_128459_("MagicCooldown");
         this.ManaAcceleration = nbt.m_128459_("ManaAcceleration");
         this.ManabarStyle = nbt.m_128459_("ManabarStyle");
         this.ElementalArmorCooldown = nbt.m_128459_("ElementalArmorCooldown");
         this.MeleeAttackCooldown = nbt.m_128459_("MeleeAttackCooldown");
         this.SetBonusToggle = nbt.m_128471_("SetBonusToggle");
         this.ElementalArmorPassiveCooldown = nbt.m_128459_("ElementalArmorPassiveCooldown");
      }
   }

   @EventBusSubscriber
   private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
      private final IterRpgModVariables.PlayerVariables playerVariables = new IterRpgModVariables.PlayerVariables();
      private final LazyOptional<IterRpgModVariables.PlayerVariables> instance = LazyOptional.of(() -> this.playerVariables);

      @SubscribeEvent
      public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
         if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation("iter_rpg", "player_variables"), new IterRpgModVariables.PlayerVariablesProvider());
         }
      }

      public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
         return cap == IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY ? this.instance.cast() : LazyOptional.empty();
      }

      public Tag serializeNBT() {
         return this.playerVariables.writeNBT();
      }

      public void deserializeNBT(Tag nbt) {
         this.playerVariables.readNBT(nbt);
      }
   }

   public static class PlayerVariablesSyncMessage {
      public IterRpgModVariables.PlayerVariables data;

      public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
         this.data = new IterRpgModVariables.PlayerVariables();
         this.data.readNBT(buffer.m_130260_());
      }

      public PlayerVariablesSyncMessage(IterRpgModVariables.PlayerVariables data) {
         this.data = data;
      }

      public static void buffer(IterRpgModVariables.PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
         buffer.m_130079_((CompoundTag)message.data.writeNBT());
      }

      public static void handler(IterRpgModVariables.PlayerVariablesSyncMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(
            () -> {
               if (!context.getDirection().getReceptionSide().isServer()) {
                  IterRpgModVariables.PlayerVariables variables = (IterRpgModVariables.PlayerVariables)Minecraft.m_91087_()
                     .f_91074_
                     .getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables());
                  variables.Mana = message.data.Mana;
                  variables.MaxMana = message.data.MaxMana;
                  variables.ManaRegen = message.data.ManaRegen;
                  variables.Catalyst = message.data.Catalyst;
                  variables.ManaRounded = message.data.ManaRounded;
                  variables.ManaOverload = message.data.ManaOverload;
                  variables.MageStatus = message.data.MageStatus;
                  variables.ManaRegenMultiplier = message.data.ManaRegenMultiplier;
                  variables.BaseManaRegen = message.data.BaseManaRegen;
                  variables.BaseManaCapacity = message.data.BaseManaCapacity;
                  variables.ManaCapacityMultiplier = message.data.ManaCapacityMultiplier;
                  variables.ItemManaRegen = message.data.ItemManaRegen;
                  variables.ItemManaCapacity = message.data.ItemManaCapacity;
                  variables.MagicCooldown = message.data.MagicCooldown;
                  variables.ManaAcceleration = message.data.ManaAcceleration;
                  variables.ManabarStyle = message.data.ManabarStyle;
                  variables.ElementalArmorCooldown = message.data.ElementalArmorCooldown;
                  variables.MeleeAttackCooldown = message.data.MeleeAttackCooldown;
                  variables.SetBonusToggle = message.data.SetBonusToggle;
                  variables.ElementalArmorPassiveCooldown = message.data.ElementalArmorPassiveCooldown;
               }
            }
         );
         context.setPacketHandled(true);
      }
   }
}
