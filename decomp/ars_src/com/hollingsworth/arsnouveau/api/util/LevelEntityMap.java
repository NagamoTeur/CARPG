package com.hollingsworth.arsnouveau.api.util;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.level.Level;

public class LevelEntityMap {
   public Map<String, Set<UUID>> entityMap = new ConcurrentHashMap<>();

   public void addEntity(Level level, UUID uuid) {
      this.addEntity(level.m_46472_().m_135782_().toString(), uuid);
   }

   public void addEntity(String key, UUID uuid) {
      if (!this.entityMap.containsKey(key)) {
         this.entityMap.put(key, ConcurrentHashMap.newKeySet());
      }

      this.entityMap.get(key).add(uuid);
   }

   public boolean containsEntity(Level level, UUID uuid) {
      return this.containsEntity(level.m_46472_().m_135782_().toString(), uuid);
   }

   public boolean containsEntity(String key, UUID uuid) {
      return !this.entityMap.containsKey(key) ? false : this.entityMap.get(key).contains(uuid);
   }

   public void removeEntity(Level level, UUID uuid) {
      this.removeEntity(level.m_46472_().m_135782_().toString(), uuid);
   }

   public void removeEntity(String key, UUID uuid) {
      if (this.entityMap.containsKey(key)) {
         this.entityMap.get(key).remove(uuid);
      }
   }

   public Set<UUID> getEntities(Level level) {
      return this.getEntities(level.m_46472_().m_135782_().toString());
   }

   public Set<UUID> getEntities(String key) {
      return (Set<UUID>)(!this.entityMap.containsKey(key) ? ConcurrentHashMap.newKeySet() : this.entityMap.get(key));
   }
}
