package com.aizistral.enigmaticlegacy.objects;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.items.BlazingCore;
import com.aizistral.enigmaticlegacy.packets.clients.PacketSyncTransientData;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.lang.ref.WeakReference;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class TransientPlayerData {
   private final WeakReference<Player> player;
   private int fireImmunityTimer;
   private int fireImmunityTimerCap;
   public int spellstoneCooldown;
   private int fireImmunityTimerLast;
   private Boolean consumedForbiddenFruit;
   private Boolean disabledMagnetEffects;
   private Boolean unlockedNarrator;
   private boolean elytraBoosting;
   private boolean hasEyeOfNebulaPower;
   public boolean needsSync = false;

   public static TransientPlayerData get(Player player) {
      boolean clientOnly = player.f_19853_.f_46443_;
      if (EnigmaticLegacy.PROXY.getTransientPlayerData(clientOnly).containsKey(player)) {
         return EnigmaticLegacy.PROXY.getTransientPlayerData(clientOnly).get(player);
      } else {
         TransientPlayerData data = new TransientPlayerData(player);
         EnigmaticLegacy.PROXY.getTransientPlayerData(clientOnly).put(player, data);
         return data;
      }
   }

   public static boolean set(Player player, TransientPlayerData data) {
      boolean clientOnly = player.f_19853_.f_46443_;
      if (data != null) {
         EnigmaticLegacy.PROXY.getTransientPlayerData(clientOnly).put(player, data);
         return true;
      } else {
         return false;
      }
   }

   public void syncToClients(float blockRadius) {
      Player player = this.getPlayer();
      EnigmaticLegacy.packetInstance
         .send(
            PacketDistributor.NEAR
               .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), (double)blockRadius, player.f_19853_.m_46472_())),
            new PacketSyncTransientData(this)
         );
   }

   public void syncToAllClients() {
      EnigmaticLegacy.packetInstance.send(PacketDistributor.ALL.noArg(), new PacketSyncTransientData(this));
   }

   public void syncToPlayer() {
      if (this.getPlayer() instanceof ServerPlayer) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)this.getPlayer()), new PacketSyncTransientData(this));
      }
   }

   public void syncToPlayer(ServerPlayer player) {
      if (this.getPlayer() instanceof ServerPlayer) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketSyncTransientData(this));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void syncToServer() {
   }

   public TransientPlayerData(Player thePlayer) {
      this.player = new WeakReference<>(thePlayer);
      this.fireImmunityTimer = 0;
      this.spellstoneCooldown = 0;
      this.fireImmunityTimerLast = 0;
      this.fireImmunityTimerCap = 30000 + this.getFireDiff();
   }

   public int getFireDiff() {
      return 2000;
   }

   public int getFireImmunityTimerLast() {
      return this.fireImmunityTimerLast;
   }

   public void setFireImmunityTimerLast(int fireImmunityTimerLast) {
      this.fireImmunityTimerLast = fireImmunityTimerLast;
   }

   public int getFireImmunityTimerCap() {
      return this.fireImmunityTimerCap;
   }

   @OnlyIn(Dist.CLIENT)
   public float getFireImmunityFraction(float partialTick) {
      float difference = (float)this.fireImmunityTimer - (float)this.fireImmunityTimerLast;
      difference *= 0.0F;
      double coolFiller;
      if (BlazingCore.traitorBar.getValue()) {
         coolFiller = SuperpositionHandler.parabolicFunction(
            0.0, (double)(this.fireImmunityTimerCap - this.getFireDiff()), (double)this.fireImmunityTimerLast + (double)difference
         );
      } else {
         coolFiller = SuperpositionHandler.flippedParabolicFunction(
            0.0, (double)(this.fireImmunityTimerCap - this.getFireDiff()), (double)this.fireImmunityTimerLast + (double)difference
         );
      }

      return (float)coolFiller;
   }

   public int getFireImmunityTimer() {
      return this.fireImmunityTimer;
   }

   public int getSpellstoneCooldown() {
      return this.spellstoneCooldown;
   }

   public void setFireImmunityTimer(int fireImmunityTimer) {
      int newValue = Math.min(Math.max(fireImmunityTimer, 0), this.fireImmunityTimerCap);
      if (newValue != this.fireImmunityTimer) {
         this.needsSync = true;
      }

      this.fireImmunityTimerLast = fireImmunityTimer;
      this.fireImmunityTimer = newValue;
   }

   public void setSpellstoneCooldown(int spellstoneCooldown) {
      int newValue = Math.max(spellstoneCooldown, 0);
      if (this.spellstoneCooldown != newValue) {
         this.needsSync = true;
         this.spellstoneCooldown = newValue;

         for (Item spellstone : EnigmaticItems.SPELLSTONES) {
            this.player.get().m_36335_().m_41524_(spellstone, this.spellstoneCooldown);
         }
      }
   }

   public Boolean getConsumedForbiddenFruit() {
      return this.consumedForbiddenFruit != null
         ? this.consumedForbiddenFruit
         : (this.consumedForbiddenFruit = SuperpositionHandler.getPersistentBoolean(this.player.get(), "ConsumedForbiddenFruit", false));
   }

   public void setConsumedForbiddenFruit(Boolean consumedForbiddenFruit) {
      this.consumedForbiddenFruit = consumedForbiddenFruit;
      SuperpositionHandler.setPersistentBoolean(this.player.get(), "ConsumedForbiddenFruit", consumedForbiddenFruit);
   }

   public Boolean getDisabledMagnetRingEffects() {
      return this.disabledMagnetEffects != null
         ? this.disabledMagnetEffects
         : (this.disabledMagnetEffects = SuperpositionHandler.getPersistentBoolean(this.player.get(), "DisabledMagnetEffects", false));
   }

   public void setDisabledMagnetRingEffects(Boolean disabledMagnetEffects) {
      this.disabledMagnetEffects = disabledMagnetEffects;
      SuperpositionHandler.setPersistentBoolean(this.player.get(), "DisabledMagnetEffects", disabledMagnetEffects);
   }

   public boolean isElytraBoosting() {
      return this.elytraBoosting;
   }

   public void setElytraBoosting(boolean elytraBoosting) {
      this.elytraBoosting = elytraBoosting;
   }

   public Boolean getUnlockedNarrator() {
      return this.unlockedNarrator != null
         ? this.unlockedNarrator
         : (this.unlockedNarrator = SuperpositionHandler.getPersistentBoolean(this.player.get(), "ELUnlockedNarrator", false));
   }

   public void setUnlockedNarrator(Boolean unlockedNarrator) {
      this.unlockedNarrator = unlockedNarrator;
      SuperpositionHandler.setPersistentBoolean(this.player.get(), "ELUnlockedNarrator", unlockedNarrator);
   }

   public boolean hasEyeOfNebulaPower() {
      return this.hasEyeOfNebulaPower;
   }

   public void setEyeOfNebulaPower(boolean hasOrNo) {
      this.hasEyeOfNebulaPower = hasOrNo;
   }

   public Player getPlayer() {
      return this.player.get();
   }

   public static FriendlyByteBuf encode(TransientPlayerData data, FriendlyByteBuf buf) {
      buf.m_130077_(data.player.get().m_20148_());
      buf.writeInt(data.spellstoneCooldown);
      buf.writeInt(data.fireImmunityTimer);
      buf.writeInt(data.fireImmunityTimerCap);
      buf.writeInt(data.fireImmunityTimerLast);
      buf.writeBoolean(data.getConsumedForbiddenFruit());
      buf.writeBoolean(data.getDisabledMagnetRingEffects());
      buf.writeBoolean(data.getUnlockedNarrator());
      buf.writeBoolean(data.isElytraBoosting());
      return buf;
   }

   public static TransientPlayerData decode(FriendlyByteBuf buf) {
      UUID playerID = buf.m_130259_();
      int spellstoneCooldown = buf.readInt();
      int fireImmunityTimer = buf.readInt();
      int fireImmunityTimerCap = buf.readInt();
      int fireImmunityTimerLast = buf.readInt();
      boolean consumedForbiddenFruit = buf.readBoolean();
      boolean disabledMagnetEffects = buf.readBoolean();
      boolean unlockedNarrator = buf.readBoolean();
      boolean elytraBoosting = buf.readBoolean();
      Player player = EnigmaticLegacy.PROXY.getPlayer(playerID);
      if (player != null) {
         TransientPlayerData data = new TransientPlayerData(player);
         data.spellstoneCooldown = spellstoneCooldown;
         data.fireImmunityTimer = fireImmunityTimer;
         data.fireImmunityTimerCap = fireImmunityTimerCap;
         data.fireImmunityTimerLast = fireImmunityTimerLast;
         data.consumedForbiddenFruit = consumedForbiddenFruit;
         data.disabledMagnetEffects = disabledMagnetEffects;
         data.unlockedNarrator = unlockedNarrator;
         data.elytraBoosting = elytraBoosting;
         return data;
      } else {
         return null;
      }
   }
}
