package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.EntityBounds;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import java.util.Map;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public class NetworkedHitboxManager implements ICompoundHitbox {
   private final BaseEntity entity;
   private final Map<Byte, ICompoundHitbox> hitboxMap;
   public static final EntityDataAccessor<Byte> hitbox = SynchedEntityData.m_135353_(BaseEntity.class, EntityDataSerializers.f_135027_);

   public NetworkedHitboxManager(BaseEntity entity, Map<Byte, ICompoundHitbox> hitboxMap) {
      this.entity = entity;
      this.hitboxMap = hitboxMap;
      entity.m_20088_().m_135372_(hitbox, hitboxMap.keySet().iterator().next());
   }

   @Override
   public void updatePosition() {
      for (ICompoundHitbox hitbox1 : this.hitboxMap.values()) {
         hitbox1.updatePosition();
      }
   }

   @Override
   public EntityBounds getBounds() {
      return this.hitboxMap.get(this.entity.m_20088_().m_135370_(hitbox)).getBounds();
   }

   @Override
   public void setNextDamagedPart(String part) {
      this.hitboxMap.get(this.entity.m_20088_().m_135370_(hitbox)).setNextDamagedPart(part);
   }
}
