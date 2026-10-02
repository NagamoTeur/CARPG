package io.redspace.ironsspellbooks.capabilities.magic;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.data.IronsDataStorage;
import io.redspace.ironsspellbooks.entity.spells.portal.PortalData;
import io.redspace.ironsspellbooks.entity.spells.portal.PortalEntity;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public class PortalManager implements INBTSerializable<CompoundTag> {
   public static final PortalManager INSTANCE = new PortalManager();
   private final HashMap<UUID, HashMap<UUID, AtomicInteger>> cooldownLookup = new HashMap<>();
   private final HashMap<UUID, PortalData> portalLookup = new HashMap<>();
   private static final int cooldownTicks = 10;

   public PortalData getPortalData(PortalEntity portalEntity) {
      return this.portalLookup.get(portalEntity.m_20148_());
   }

   public void addPortalData(UUID portalEntityUUID, PortalData portalData) {
      this.portalLookup.put(portalEntityUUID, portalData);
      IronsDataStorage.INSTANCE.m_77762_();
   }

   public void addPortalCooldown(Entity entity, UUID portalId) {
      PortalData portalData = this.portalLookup.get(portalId);
      if (portalData != null) {
         HashMap<UUID, AtomicInteger> playerMap = this.cooldownLookup.computeIfAbsent(portalData.getConnectedPortalUUID(portalId), k -> new HashMap<>());
         playerMap.put(entity.m_20148_(), new AtomicInteger(10));
      }
   }

   public boolean isEntityOnCooldown(Entity entity, UUID portalId) {
      HashMap<UUID, AtomicInteger> playerMap = this.cooldownLookup.get(portalId);
      return playerMap != null && playerMap.containsKey(entity.m_20148_());
   }

   public boolean isPortalConnected(UUID portalID) {
      PortalData portalData = this.portalLookup.get(portalID);
      if (portalData != null) {
         UUID connectedPortal = portalData.getConnectedPortalUUID(portalID);
         if (connectedPortal != null) {
            return true;
         }
      }

      return false;
   }

   public boolean canUsePortal(PortalEntity portalEntity, Entity entity) {
      if (portalEntity != null && entity != null) {
         PortalData portalData = this.portalLookup.get(portalEntity.m_20148_());
         return !entity.m_20159_()
            && portalData != null
            && portalData.portalEntityId1 != null
            && portalData.portalEntityId2 != null
            && this.portalLookup.containsKey(portalData.portalEntityId1)
            && this.portalLookup.containsKey(portalData.portalEntityId2)
            && !this.isEntityOnCooldown(entity, portalEntity.m_20148_());
      } else {
         return false;
      }
   }

   public void processCooldownTick(UUID portalUUID, int delta) {
      HashMap<UUID, AtomicInteger> playerCooldownsForPortal = this.cooldownLookup.get(portalUUID);
      if (playerCooldownsForPortal != null) {
         playerCooldownsForPortal.entrySet()
            .stream()
            .filter(item -> item.getValue().addAndGet(delta) <= 0)
            .toList()
            .forEach(itemToRemove -> playerCooldownsForPortal.remove(itemToRemove.getKey()));
      }
   }

   public void processDelayCooldown(UUID portalUUID, UUID playerUUID, int delta) {
      HashMap<UUID, AtomicInteger> playerCooldownsForPortal = this.cooldownLookup.get(portalUUID);
      if (playerCooldownsForPortal != null) {
         AtomicInteger cooldown = playerCooldownsForPortal.get(playerUUID);
         if (cooldown != null) {
            cooldown.addAndGet(delta);
         }
      }
   }

   public void removePortalData(UUID portalUUID) {
      this.portalLookup.remove(portalUUID);
      this.cooldownLookup.remove(portalUUID);
      IronsDataStorage.INSTANCE.m_77762_();
   }

   public void killPortal(UUID portalUUID, UUID ownerUUID) {
      PortalData removedPortalData = this.portalLookup.remove(portalUUID);
      if (removedPortalData != null) {
         if (removedPortalData.portalEntityId2 != null && removedPortalData.globalPos2 != null) {
            UUID connectedPortalUUID = removedPortalData.getConnectedPortalUUID(portalUUID);
            if (connectedPortalUUID != null) {
               removedPortalData = this.portalLookup.remove(connectedPortalUUID);
               removedPortalData.getConnectedPortalPos(portalUUID).ifPresent(globalPos -> {
                  ServerLevel level = IronsSpellbooks.MCS.m_129880_(globalPos.dimension());
                  if (level != null) {
                     Entity connectedPortalToRemove = level.m_8791_(connectedPortalUUID);
                     if (connectedPortalToRemove != null) {
                        connectedPortalToRemove.m_146870_();
                     }
                  }
               });
               this.cooldownLookup.remove(connectedPortalUUID);
            }
         } else {
            this.tryCancelRecast(portalUUID, ownerUUID);
         }
      }

      this.cooldownLookup.remove(portalUUID);
      IronsDataStorage.INSTANCE.m_77762_();
   }

   private void tryCancelRecast(UUID portalUUID, UUID ownerUUID) {
      IronsSpellbooks.MCS.m_129785_().forEach(level -> {
         Player player = level.m_46003_(ownerUUID);
         if (player != null) {
            MagicData magicData = MagicData.getPlayerMagicData(player);
            PlayerRecasts playerRecasts = magicData.getPlayerRecasts();
            String spellId = ((AbstractSpell)SpellRegistry.PORTAL_SPELL.get()).getSpellId();
            RecastInstance recastInstance = playerRecasts.getRecastInstance(spellId);
            if (recastInstance != null && recastInstance.castData instanceof PortalData portalData && portalData.portalEntityId1 == portalUUID) {
               playerRecasts.removeRecast(recastInstance, RecastResult.COUNTERSPELL);
               return;
            }
         }
      });
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      ListTag portalLookupTag = new ListTag();
      if (!this.portalLookup.isEmpty()) {
         portalLookupTag.addAll(this.portalLookup.entrySet().stream().map(entry -> {
            CompoundTag itemTag = new CompoundTag();
            itemTag.m_128362_("key", entry.getKey());
            itemTag.m_128365_("value", entry.getValue().serializeNBT());
            return itemTag;
         }).toList());
      }

      tag.m_128365_("portalLookup", portalLookupTag);
      return tag;
   }

   public void deserializeNBT(CompoundTag compoundTag) {
      if (compoundTag.m_128441_("portalLookup")) {
         ListTag portalLookupTag = (ListTag)compoundTag.m_128423_("portalLookup");
         if (portalLookupTag != null) {
            portalLookupTag.forEach(tag -> {
               CompoundTag portalLookupItem = (CompoundTag)tag;
               PortalData portalData = new PortalData();
               portalData.deserializeNBT(portalLookupItem.m_128469_("value"));
               this.portalLookup.put(portalLookupItem.m_128342_("key"), portalData);
            });
         }
      }
   }
}
