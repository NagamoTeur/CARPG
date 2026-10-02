package com.aizistral.omniconfig.wrappers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.omniconfig.Configuration;
import com.aizistral.omniconfig.packets.PacketSyncOptions;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.PacketDistributor;

public class OmniconfigWrapper {
   public static final Map<String, OmniconfigWrapper> wrapperRegistry = new HashMap<>();
   public static boolean onRemoteServer = false;
   public final Configuration config;
   private String currentCategory;
   private String currentComment;
   private double currentMin;
   private double currentMax;
   private boolean currentSynchronized;
   public final Map<String, Omniconfig.GenericParameter> invokationMap;
   private boolean deferInvokation;
   private boolean forceSynchronized;
   private boolean currentClientOnly;
   private String optionPrefix;
   private double defaultMin;
   private double defaultMax;

   private OmniconfigWrapper(Configuration config) {
      this.config = config;
      this.defaultMin = 0.0;
      this.defaultMax = 32768.0;
      this.invokationMap = new HashMap<>();
      this.deferInvokation = false;
      this.forceSynchronized = false;
      this.resetCategory();
      this.resetOptionPrefix();
      this.resetOptionStuff();
      wrapperRegistry.put(this.config.getConfigFile().getName(), this);
   }

   public static OmniconfigWrapper setupBuilder(String fileName) {
      return setupBuilder(fileName, null);
   }

   public static OmniconfigWrapper setupBuilder(String fileName, String version) {
      return setupBuilder(fileName, false, version);
   }

   public static OmniconfigWrapper setupBuilder(String fileName, boolean caseSensitive, String version) {
      try {
         return setupBuilder(new File(FMLPaths.CONFIGDIR.get().toFile().getCanonicalFile(), fileName + ".omniconf"), caseSensitive, version);
      } catch (IOException var4) {
         new RuntimeException("Something screwed up when loading config.", var4).printStackTrace();
         return null;
      }
   }

   public static OmniconfigWrapper setupBuilder(File file) {
      return setupBuilder(file, null);
   }

   public static OmniconfigWrapper setupBuilder(File file, String version) {
      return setupBuilder(file, false, version);
   }

   public static OmniconfigWrapper setupBuilder(File file, boolean caseSensitive, String version) {
      return new OmniconfigWrapper(new Configuration(file, version, caseSensitive));
   }

   public static OmniconfigWrapper setupBuilder(Configuration config) {
      return new OmniconfigWrapper(config);
   }

   private void resetOptionStuff() {
      this.resetComment();
      this.resetCurrentMin();
      this.resetCurrentMax();
      this.resetCurrentSynchronized();
      this.resetClientOnly();
   }

   public void setDefaultMin(double defaultMin) {
      this.defaultMin = defaultMin;
      this.resetCurrentMin();
   }

   public void setDefaultMax(double defaultMax) {
      this.defaultMax = defaultMax;
      this.resetCurrentMax();
   }

   private void resetOptionPrefix() {
      this.optionPrefix = "";
   }

   private void resetCurrentSynchronized() {
      this.currentSynchronized = this.forceSynchronized;
   }

   private void resetClientOnly() {
      this.currentClientOnly = false;
   }

   private void resetCurrentMin() {
      this.currentMin = this.defaultMin;
   }

   private void resetCurrentMax() {
      this.currentMax = this.defaultMax;
   }

   private void resetComment() {
      this.currentComment = "Undocumented property";
   }

   private void resetCategory() {
      this.currentCategory = "general";
   }

   public void deferInvocation(boolean whetherOrNot) {
      this.deferInvokation = whetherOrNot;
   }

   public void forceSynchronized(boolean whetherOrNot) {
      this.forceSynchronized = whetherOrNot;
      this.resetCurrentSynchronized();
   }

   public boolean isClientOnly() {
      return this.currentClientOnly;
   }

   public OmniconfigWrapper clientOnly() {
      return this.clientOnly(true);
   }

   public OmniconfigWrapper clientOnly(boolean clientOnly) {
      this.currentClientOnly = clientOnly;
      return this;
   }

   public boolean isForceSynchronized() {
      return this.forceSynchronized;
   }

   public boolean isInvokationDeferred() {
      return this.deferInvokation;
   }

   public OmniconfigWrapper setReloadable() {
      this.pushGenericOverloadingAction();
      this.pushBeholderAttachment();
      return this;
   }

   public String getCurrentCategory() {
      return this.currentCategory;
   }

   public OmniconfigWrapper pushOverloadingAction(Consumer<Configuration> action) {
      this.config.attachOverloadingAction(action);
      return this;
   }

   public OmniconfigWrapper pushGenericOverloadingAction() {
      this.pushOverloadingAction(
         config -> {
            config.load();
            if (this.invokationMap != null) {
               this.invokationMap.values().forEach(param -> {
                  if (!onRemoteServer || !param.isSynchronized()) {
                     param.invoke(config);
                  }
               });
            }

            SuperpositionHandler.executeOnServer(
               server -> {
                  for (ServerPlayer player : server.m_6846_().m_11314_()) {
                     boolean worked = syncWrapperToPlayer(this, player);
                     if (worked) {
                        EnigmaticLegacy.LOGGER
                           .info("Successfully resynchronized file " + config.getConfigFile().getName() + " to " + player.m_36316_().getName());
                     } else {
                        EnigmaticLegacy.LOGGER
                           .info(
                              "File "
                                 + config.getConfigFile().getName()
                                 + " was not resynchronized to "
                                 + player.m_36316_().getName()
                                 + ", since this integrated server is hosted by them."
                           );
                     }
                  }
               }
            );
         }
      );
      return this;
   }

   public OmniconfigWrapper pushBeholderAttachment() {
      this.config.attachBeholder();
      return this;
   }

   public OmniconfigWrapper pushPrefix(String optionPrefix) {
      this.optionPrefix = optionPrefix;
      return this;
   }

   public OmniconfigWrapper popPrefix() {
      this.resetOptionPrefix();
      return this;
   }

   public OmniconfigWrapper pushTerminateNonInvokedKeys(boolean whetherOrNot) {
      this.config.setTerminateNonInvokedKeys(whetherOrNot);
      return this;
   }

   public OmniconfigWrapper pushVersioningPolicy(Configuration.VersioningPolicy policy) {
      this.config.setVersioningPolicy(policy);
      return this;
   }

   public OmniconfigWrapper pushSidedType(Configuration.SidedConfigType type) {
      this.config.setSidedType(type);
      return this;
   }

   public OmniconfigWrapper loadConfigFile() {
      this.config.load();
      return this;
   }

   public OmniconfigWrapper pushCategory(String name) {
      this.currentCategory = name;
      return this;
   }

   public OmniconfigWrapper pushCategory(String name, String comment) {
      this.pushCategory(name);
      this.config.addCustomCategoryComment(name, comment);
      return this;
   }

   public OmniconfigWrapper popCategory() {
      this.currentCategory = "general";
      return this;
   }

   public OmniconfigWrapper comment(String comment) {
      this.currentComment = comment;
      return this;
   }

   public OmniconfigWrapper min(double minValue) {
      this.currentMin = minValue;
      return this;
   }

   public OmniconfigWrapper max(double maxValue) {
      this.currentMax = maxValue;
      return this;
   }

   public OmniconfigWrapper minMax(double value) {
      this.max(value);
      this.min(-value);
      return this;
   }

   public OmniconfigWrapper sync() {
      return this.sync(true);
   }

   public OmniconfigWrapper nosync() {
      return this.sync(false);
   }

   public OmniconfigWrapper sync(boolean whetherOrNot) {
      this.currentSynchronized = whetherOrNot;
      return this;
   }

   public Omniconfig.StringParameter getString(String name, String defaultValue) {
      return this.getString(name, defaultValue, (String[])null);
   }

   public <V extends Enum<V>> Omniconfig.EnumParameter<V> getEnum(String name, V defaultValue) {
      return this.getEnum(name, defaultValue, (V[])((Enum[])null));
   }

   public <V extends Enum<V>> Omniconfig.EnumParameter<V> getEnum(String name, V defaultValue, V... validValues) {
      Omniconfig.EnumParameter<V> returned = new Omniconfig.EnumParameter<>(defaultValue);
      returned.setName(this.optionPrefix + name);
      returned.setCategory(this.currentCategory);
      returned.setComment(this.currentComment);
      returned.setSynchronized(this.currentSynchronized);
      returned.setClientOnly(this.currentClientOnly);
      if (validValues != null && validValues.length > 0) {
         returned.setValidValues(validValues);
      }

      this.resetOptionStuff();
      this.invokationMap.put(returned.getId(), returned);
      if (!this.deferInvokation) {
         returned.invoke(this.config);
      }

      return returned;
   }

   public Omniconfig.StringParameter getString(String name, String defaultValue, String... validValues) {
      Omniconfig.StringParameter returned = new Omniconfig.StringParameter(defaultValue);
      returned.setName(this.optionPrefix + name);
      returned.setCategory(this.currentCategory);
      returned.setComment(this.currentComment);
      returned.setSynchronized(this.currentSynchronized);
      returned.setClientOnly(this.currentClientOnly);
      if (validValues != null && validValues.length > 0) {
         returned.setValidValues(validValues);
      }

      this.resetOptionStuff();
      this.invokationMap.put(returned.getId(), returned);
      if (!this.deferInvokation) {
         returned.invoke(this.config);
      }

      return returned;
   }

   public Omniconfig.BooleanParameter getBoolean(String name, boolean defaultValue) {
      Omniconfig.BooleanParameter returned = new Omniconfig.BooleanParameter(defaultValue);
      returned.setName(this.optionPrefix + name);
      returned.setCategory(this.currentCategory);
      returned.setComment(this.currentComment);
      returned.setSynchronized(this.currentSynchronized);
      returned.setClientOnly(this.currentClientOnly);
      this.resetOptionStuff();
      this.invokationMap.put(returned.getId(), returned);
      if (!this.deferInvokation) {
         returned.invoke(this.config);
      }

      return returned;
   }

   public Omniconfig.IntParameter getInt(String name, int defaultValue) {
      Omniconfig.IntParameter returned = new Omniconfig.IntParameter(defaultValue);
      returned.setName(this.optionPrefix + name);
      returned.setCategory(this.currentCategory);
      returned.setComment(this.currentComment);
      returned.setMinValue((int)this.currentMin);
      returned.setMaxValue((int)this.currentMax);
      returned.setSynchronized(this.currentSynchronized);
      returned.setClientOnly(this.currentClientOnly);
      this.resetOptionStuff();
      this.invokationMap.put(returned.getId(), returned);
      if (!this.deferInvokation) {
         returned.invoke(this.config);
      }

      return returned;
   }

   public Omniconfig.DoubleParameter getDouble(String name, double defaultValue) {
      Omniconfig.DoubleParameter returned = new Omniconfig.DoubleParameter(defaultValue);
      returned.setName(this.optionPrefix + name);
      returned.setCategory(this.currentCategory);
      returned.setComment(this.currentComment);
      returned.setMinValue(this.currentMin);
      returned.setMaxValue(this.currentMax);
      returned.setSynchronized(this.currentSynchronized);
      returned.setClientOnly(this.currentClientOnly);
      this.resetOptionStuff();
      this.invokationMap.put(returned.getId(), returned);
      if (!this.deferInvokation) {
         returned.invoke(this.config);
      }

      return returned;
   }

   public Omniconfig.PerhapsParameter getPerhaps(String name, int defaultPercentage) {
      Omniconfig.PerhapsParameter returned = new Omniconfig.PerhapsParameter(defaultPercentage);
      returned.setName(this.optionPrefix + name);
      returned.setCategory(this.currentCategory);
      returned.setComment(this.currentComment);
      returned.setMinValue((int)this.currentMin);
      returned.setMaxValue((int)this.currentMax);
      returned.setSynchronized(this.currentSynchronized);
      returned.setClientOnly(this.currentClientOnly);
      this.resetOptionStuff();
      this.invokationMap.put(returned.getId(), returned);
      if (!this.deferInvokation) {
         returned.invoke(this.config);
      }

      return returned;
   }

   public Configuration build() {
      if (!this.deferInvokation) {
         this.config.save();
      }

      return this.config;
   }

   public Collection<Omniconfig.GenericParameter> retrieveInvocationList() {
      return this.invokationMap.values();
   }

   public static boolean syncAllToPlayer(ServerPlayer player) {
      if (SuperpositionHandler.areWeRemoteServer(player)) {
         EnigmaticLegacy.LOGGER.info("Synchronizing omniconfig files to " + player.m_36316_().getName() + "...");

         for (OmniconfigWrapper wrapper : wrapperRegistry.values()) {
            if (!wrapper.config.getSidedType().isSided()) {
               syncWrapperToPlayer(wrapper, player);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean syncWrapperToPlayer(OmniconfigWrapper wrapper, ServerPlayer player) {
      if (SuperpositionHandler.areWeRemoteServer(player)) {
         EnigmaticLegacy.LOGGER.info("Sending data for " + wrapper.config.getConfigFile().getName());
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketSyncOptions(wrapper));
         return true;
      } else {
         return false;
      }
   }
}
