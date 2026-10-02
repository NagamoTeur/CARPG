package net.cisco.network;

import java.util.function.Supplier;
import net.cisco.CiscoModMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
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

@EventBusSubscriber(
   bus = Bus.MOD
)
public class CiscoModModVariables {
   public static final Capability<CiscoModModVariables.PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(
      new CapabilityToken<CiscoModModVariables.PlayerVariables>() {
      }
   );

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      CiscoModMod.addNetworkMessage(
         CiscoModModVariables.SavedDataSyncMessage.class,
         CiscoModModVariables.SavedDataSyncMessage::buffer,
         CiscoModModVariables.SavedDataSyncMessage::new,
         CiscoModModVariables.SavedDataSyncMessage::handler
      );
      CiscoModMod.addNetworkMessage(
         CiscoModModVariables.PlayerVariablesSyncMessage.class,
         CiscoModModVariables.PlayerVariablesSyncMessage::buffer,
         CiscoModModVariables.PlayerVariablesSyncMessage::new,
         CiscoModModVariables.PlayerVariablesSyncMessage::handler
      );
   }

   @SubscribeEvent
   public static void init(RegisterCapabilitiesEvent event) {
      event.register(CiscoModModVariables.PlayerVariables.class);
   }

   @EventBusSubscriber
   public static class EventBusVariableHandlers {
      @SubscribeEvent
      public static void onPlayerLoggedInSyncPlayerVariables(PlayerLoggedInEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            ((CiscoModModVariables.PlayerVariables)event.getEntity()
                  .getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerRespawnedSyncPlayerVariables(PlayerRespawnEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            ((CiscoModModVariables.PlayerVariables)event.getEntity()
                  .getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerChangedDimensionEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            ((CiscoModModVariables.PlayerVariables)event.getEntity()
                  .getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void clonePlayer(Clone event) {
         event.getOriginal().revive();
         CiscoModModVariables.PlayerVariables original = (CiscoModModVariables.PlayerVariables)event.getOriginal()
            .getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new CiscoModModVariables.PlayerVariables());
         CiscoModModVariables.PlayerVariables clone = (CiscoModModVariables.PlayerVariables)event.getEntity()
            .getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new CiscoModModVariables.PlayerVariables());
         clone.PosX = original.PosX;
         clone.PosY = original.PosY;
         clone.PosZ = original.PosZ;
         clone.attackset = original.attackset;
         clone.founddefender = original.founddefender;
         if (!event.isWasDeath()) {
            clone.Ciscosethp = original.Ciscosethp;
            clone.jumpvariable = original.jumpvariable;
            clone.UnyieldingUsed = original.UnyieldingUsed;
            clone.UnyieldViolet = original.UnyieldViolet;
            clone.UnyieldRouge = original.UnyieldRouge;
            clone.DarkProtectionUsed = original.DarkProtectionUsed;
            clone.DescendedActiveUsed = original.DescendedActiveUsed;
            clone.SovereignActiveUsed = original.SovereignActiveUsed;
            clone.SovereignFlightTimer = original.SovereignFlightTimer;
         }
      }

      @SubscribeEvent
      public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            SavedData mapdata = CiscoModModVariables.MapVariables.get(event.getEntity().f_19853_);
            SavedData worlddata = CiscoModModVariables.WorldVariables.get(event.getEntity().f_19853_);
            if (mapdata != null) {
               CiscoModMod.PACKET_HANDLER
                  .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new CiscoModModVariables.SavedDataSyncMessage(0, mapdata));
            }

            if (worlddata != null) {
               CiscoModMod.PACKET_HANDLER
                  .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new CiscoModModVariables.SavedDataSyncMessage(1, worlddata));
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            SavedData worlddata = CiscoModModVariables.WorldVariables.get(event.getEntity().f_19853_);
            if (worlddata != null) {
               CiscoModMod.PACKET_HANDLER
                  .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new CiscoModModVariables.SavedDataSyncMessage(1, worlddata));
            }
         }
      }
   }

   public static class MapVariables extends SavedData {
      public static final String DATA_NAME = "cisco_mod_mapvars";
      public boolean IsBossAliveAndInBattle = false;
      public boolean CiscoKilled = false;
      public double worldspawnX = 0.0;
      public double worldspawnZ = 0.0;
      public boolean FellKingLives = false;
      public boolean DescendedCiscoLives = false;
      static CiscoModModVariables.MapVariables clientSide = new CiscoModModVariables.MapVariables();

      public static CiscoModModVariables.MapVariables load(CompoundTag tag) {
         CiscoModModVariables.MapVariables data = new CiscoModModVariables.MapVariables();
         data.read(tag);
         return data;
      }

      public void read(CompoundTag nbt) {
         this.IsBossAliveAndInBattle = nbt.m_128471_("IsBossAliveAndInBattle");
         this.CiscoKilled = nbt.m_128471_("CiscoKilled");
         this.worldspawnX = nbt.m_128459_("worldspawnX");
         this.worldspawnZ = nbt.m_128459_("worldspawnZ");
         this.FellKingLives = nbt.m_128471_("FellKingLives");
         this.DescendedCiscoLives = nbt.m_128471_("DescendedCiscoLives");
      }

      public CompoundTag m_7176_(CompoundTag nbt) {
         nbt.m_128379_("IsBossAliveAndInBattle", this.IsBossAliveAndInBattle);
         nbt.m_128379_("CiscoKilled", this.CiscoKilled);
         nbt.m_128347_("worldspawnX", this.worldspawnX);
         nbt.m_128347_("worldspawnZ", this.worldspawnZ);
         nbt.m_128379_("FellKingLives", this.FellKingLives);
         nbt.m_128379_("DescendedCiscoLives", this.DescendedCiscoLives);
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.m_77762_();
         if (world instanceof Level && !world.m_5776_()) {
            CiscoModMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new CiscoModModVariables.SavedDataSyncMessage(0, this));
         }
      }

      public static CiscoModModVariables.MapVariables get(LevelAccessor world) {
         return world instanceof ServerLevelAccessor serverLevelAcc
            ? (CiscoModModVariables.MapVariables)serverLevelAcc.m_6018_()
               .m_7654_()
               .m_129880_(Level.f_46428_)
               .m_8895_()
               .m_164861_(e -> load(e), CiscoModModVariables.MapVariables::new, "cisco_mod_mapvars")
            : clientSide;
      }
   }

   public static class PlayerVariables {
      public boolean Ciscosethp = false;
      public boolean jumpvariable = false;
      public double PosX = 0.0;
      public double PosY = 60.0;
      public double PosZ = 0.0;
      public double attackset = 0.0;
      public boolean UnyieldingUsed = false;
      public boolean UnyieldViolet = false;
      public boolean UnyieldRouge = false;
      public boolean DarkProtectionUsed = false;
      public boolean founddefender = false;
      public boolean DescendedActiveUsed = false;
      public boolean SovereignActiveUsed = false;
      public double SovereignFlightTimer = 20.0;

      public void syncPlayerVariables(Entity entity) {
         if (entity instanceof ServerPlayer serverPlayer) {
            CiscoModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new CiscoModModVariables.PlayerVariablesSyncMessage(this));
         }
      }

      public Tag writeNBT() {
         CompoundTag nbt = new CompoundTag();
         nbt.m_128379_("Ciscosethp", this.Ciscosethp);
         nbt.m_128379_("jumpvariable", this.jumpvariable);
         nbt.m_128347_("PosX", this.PosX);
         nbt.m_128347_("PosY", this.PosY);
         nbt.m_128347_("PosZ", this.PosZ);
         nbt.m_128347_("attackset", this.attackset);
         nbt.m_128379_("UnyieldingUsed", this.UnyieldingUsed);
         nbt.m_128379_("UnyieldViolet", this.UnyieldViolet);
         nbt.m_128379_("UnyieldRouge", this.UnyieldRouge);
         nbt.m_128379_("DarkProtectionUsed", this.DarkProtectionUsed);
         nbt.m_128379_("founddefender", this.founddefender);
         nbt.m_128379_("DescendedActiveUsed", this.DescendedActiveUsed);
         nbt.m_128379_("SovereignActiveUsed", this.SovereignActiveUsed);
         nbt.m_128347_("SovereignFlightTimer", this.SovereignFlightTimer);
         return nbt;
      }

      public void readNBT(Tag Tag) {
         CompoundTag nbt = (CompoundTag)Tag;
         this.Ciscosethp = nbt.m_128471_("Ciscosethp");
         this.jumpvariable = nbt.m_128471_("jumpvariable");
         this.PosX = nbt.m_128459_("PosX");
         this.PosY = nbt.m_128459_("PosY");
         this.PosZ = nbt.m_128459_("PosZ");
         this.attackset = nbt.m_128459_("attackset");
         this.UnyieldingUsed = nbt.m_128471_("UnyieldingUsed");
         this.UnyieldViolet = nbt.m_128471_("UnyieldViolet");
         this.UnyieldRouge = nbt.m_128471_("UnyieldRouge");
         this.DarkProtectionUsed = nbt.m_128471_("DarkProtectionUsed");
         this.founddefender = nbt.m_128471_("founddefender");
         this.DescendedActiveUsed = nbt.m_128471_("DescendedActiveUsed");
         this.SovereignActiveUsed = nbt.m_128471_("SovereignActiveUsed");
         this.SovereignFlightTimer = nbt.m_128459_("SovereignFlightTimer");
      }
   }

   @EventBusSubscriber
   private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
      private final CiscoModModVariables.PlayerVariables playerVariables = new CiscoModModVariables.PlayerVariables();
      private final LazyOptional<CiscoModModVariables.PlayerVariables> instance = LazyOptional.of(() -> this.playerVariables);

      @SubscribeEvent
      public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
         if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation("cisco_mod", "player_variables"), new CiscoModModVariables.PlayerVariablesProvider());
         }
      }

      public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
         return cap == CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY ? this.instance.cast() : LazyOptional.empty();
      }

      public Tag serializeNBT() {
         return this.playerVariables.writeNBT();
      }

      public void deserializeNBT(Tag nbt) {
         this.playerVariables.readNBT(nbt);
      }
   }

   public static class PlayerVariablesSyncMessage {
      public CiscoModModVariables.PlayerVariables data;

      public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
         this.data = new CiscoModModVariables.PlayerVariables();
         this.data.readNBT(buffer.m_130260_());
      }

      public PlayerVariablesSyncMessage(CiscoModModVariables.PlayerVariables data) {
         this.data = data;
      }

      public static void buffer(CiscoModModVariables.PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
         buffer.m_130079_((CompoundTag)message.data.writeNBT());
      }

      public static void handler(CiscoModModVariables.PlayerVariablesSyncMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(
            () -> {
               if (!context.getDirection().getReceptionSide().isServer()) {
                  CiscoModModVariables.PlayerVariables variables = (CiscoModModVariables.PlayerVariables)Minecraft.m_91087_()
                     .f_91074_
                     .getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new CiscoModModVariables.PlayerVariables());
                  variables.Ciscosethp = message.data.Ciscosethp;
                  variables.jumpvariable = message.data.jumpvariable;
                  variables.PosX = message.data.PosX;
                  variables.PosY = message.data.PosY;
                  variables.PosZ = message.data.PosZ;
                  variables.attackset = message.data.attackset;
                  variables.UnyieldingUsed = message.data.UnyieldingUsed;
                  variables.UnyieldViolet = message.data.UnyieldViolet;
                  variables.UnyieldRouge = message.data.UnyieldRouge;
                  variables.DarkProtectionUsed = message.data.DarkProtectionUsed;
                  variables.founddefender = message.data.founddefender;
                  variables.DescendedActiveUsed = message.data.DescendedActiveUsed;
                  variables.SovereignActiveUsed = message.data.SovereignActiveUsed;
                  variables.SovereignFlightTimer = message.data.SovereignFlightTimer;
               }
            }
         );
         context.setPacketHandled(true);
      }
   }

   public static class SavedDataSyncMessage {
      public int type;
      public SavedData data;

      public SavedDataSyncMessage(FriendlyByteBuf buffer) {
         this.type = buffer.readInt();
         this.data = (SavedData)(this.type == 0 ? new CiscoModModVariables.MapVariables() : new CiscoModModVariables.WorldVariables());
         if (this.data instanceof CiscoModModVariables.MapVariables _mapvars) {
            _mapvars.read(buffer.m_130260_());
         } else if (this.data instanceof CiscoModModVariables.WorldVariables _worldvars) {
            _worldvars.read(buffer.m_130260_());
         }
      }

      public SavedDataSyncMessage(int type, SavedData data) {
         this.type = type;
         this.data = data;
      }

      public static void buffer(CiscoModModVariables.SavedDataSyncMessage message, FriendlyByteBuf buffer) {
         buffer.writeInt(message.type);
         buffer.m_130079_(message.data.m_7176_(new CompoundTag()));
      }

      public static void handler(CiscoModModVariables.SavedDataSyncMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer()) {
               if (message.type == 0) {
                  CiscoModModVariables.MapVariables.clientSide = (CiscoModModVariables.MapVariables)message.data;
               } else {
                  CiscoModModVariables.WorldVariables.clientSide = (CiscoModModVariables.WorldVariables)message.data;
               }
            }
         });
         context.setPacketHandled(true);
      }
   }

   public static class WorldVariables extends SavedData {
      public static final String DATA_NAME = "cisco_mod_worldvars";
      static CiscoModModVariables.WorldVariables clientSide = new CiscoModModVariables.WorldVariables();

      public static CiscoModModVariables.WorldVariables load(CompoundTag tag) {
         CiscoModModVariables.WorldVariables data = new CiscoModModVariables.WorldVariables();
         data.read(tag);
         return data;
      }

      public void read(CompoundTag nbt) {
      }

      public CompoundTag m_7176_(CompoundTag nbt) {
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.m_77762_();
         if (world instanceof Level level && !level.m_5776_()) {
            CiscoModMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(level::m_46472_), new CiscoModModVariables.SavedDataSyncMessage(1, this));
         }
      }

      public static CiscoModModVariables.WorldVariables get(LevelAccessor world) {
         return world instanceof ServerLevel level
            ? (CiscoModModVariables.WorldVariables)level.m_8895_().m_164861_(e -> load(e), CiscoModModVariables.WorldVariables::new, "cisco_mod_worldvars")
            : clientSide;
      }
   }
}
