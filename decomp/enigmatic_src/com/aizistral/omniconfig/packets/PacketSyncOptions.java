package com.aizistral.omniconfig.packets;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSyncOptions {
   private Map<String, String> synchronizedParameters = new HashMap<>();
   private String fileName = "";
   private String configVersion = "";

   private PacketSyncOptions(String fileName, String configVersion, Map<String, String> params) {
      this.fileName = fileName;
      this.configVersion = configVersion;
      this.synchronizedParameters = params;
   }

   public PacketSyncOptions(OmniconfigWrapper wrapper) {
      this.fileName = wrapper.config.getConfigFile().getName();
      this.configVersion = wrapper.config.getLoadedConfigVersion();

      for (Omniconfig.GenericParameter param : wrapper.retrieveInvocationList()) {
         if (param.isSynchronized()) {
            this.synchronizedParameters.put(param.getId(), param.valueToString());
         }
      }
   }

   public static void encode(PacketSyncOptions msg, FriendlyByteBuf buf) {
      buf.m_130072_(msg.fileName, 512);
      buf.m_130072_(String.valueOf(msg.configVersion), 512);
      buf.writeLong((long)msg.synchronizedParameters.size());

      for (String paramName : msg.synchronizedParameters.keySet()) {
         buf.m_130072_(paramName, 512);
         buf.m_130072_(msg.synchronizedParameters.get(paramName), 32768);
      }
   }

   public static PacketSyncOptions decode(FriendlyByteBuf buf) {
      String fileName = buf.m_130136_(512);
      String configVersion = buf.m_130136_(512);
      long entryAmount = buf.readLong();
      Map<String, String> params = new HashMap<>();

      for (int counter = 0; (long)counter < entryAmount; counter++) {
         String identifier = buf.m_130136_(512);
         String value = buf.m_130136_(32768);
         params.put(identifier, value);
      }

      return new PacketSyncOptions(fileName, configVersion, params);
   }

   public static void handle(PacketSyncOptions msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         OmniconfigWrapper.onRemoteServer = true;
         OmniconfigWrapper wrapper = OmniconfigWrapper.wrapperRegistry.get(msg.fileName);
         if (wrapper != null) {
            EnigmaticLegacy.LOGGER.info("Synchronizing values of " + msg.fileName + " with ones dispatched by server...");

            for (String id : msg.synchronizedParameters.keySet()) {
               Omniconfig.GenericParameter parameter = wrapper.invokationMap.get(id);
               if (parameter != null) {
                  String oldValue = parameter.valueToString();
                  parameter.parseFromString(msg.synchronizedParameters.get(id));
                  EnigmaticLegacy.LOGGER.info("Value of '" + parameter.getId() + "' was set to '" + parameter.valueToString() + "'; old value: " + oldValue);
               } else {
                  EnigmaticLegacy.LOGGER.error("Value '" + id + "' does not exist in " + msg.fileName + "! Skipping.");
               }
            }
         } else {
            EnigmaticLegacy.LOGGER.fatal("Received synchronization packet for non-existent config file: " + msg.fileName);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
