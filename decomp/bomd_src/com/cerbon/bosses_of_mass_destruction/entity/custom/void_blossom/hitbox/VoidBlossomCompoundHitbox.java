package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.EntityBounds;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.EntityPart;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MutableBox;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;

public class VoidBlossomCompoundHitbox implements ICompoundHitbox, IDamageHandler, IEntityTick<ServerLevel> {
   private final VoidBlossomEntity entity;
   private final EntityBounds hitboxes;
   private final String root;
   private final AABB collisionHitbox;
   private final List<String> spikedBoxes;
   private String nextDamagedPart;

   public VoidBlossomCompoundHitbox(VoidBlossomEntity entity, EntityBounds hitboxes, String root, AABB collisionHitbox, List<String> spikedBoxes) {
      this.entity = entity;
      this.hitboxes = hitboxes;
      this.root = root;
      this.collisionHitbox = collisionHitbox;
      this.spikedBoxes = spikedBoxes;
   }

   @Override
   public void updatePosition() {
      EntityPart rootYaw = this.hitboxes.getPart(this.root);
      rootYaw.setRotation(0.0, (double)(-this.entity.m_146908_()), 0.0, true);
      rootYaw.setX(this.entity.m_20185_());
      rootYaw.setY(this.entity.m_20186_());
      rootYaw.setZ(this.entity.m_20189_());
      MutableBox overrideBox = this.hitboxes.getOverrideBox();
      if (overrideBox != null) {
         overrideBox.setBox(this.collisionHitbox.m_82383_(this.entity.m_20182_()).m_82386_(-1.0, 0.0, -1.0));
      }
   }

   @Override
   public EntityBounds getBounds() {
      return this.hitboxes;
   }

   @Override
   public void setNextDamagedPart(String part) {
      this.nextDamagedPart = part;
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
      String part = this.nextDamagedPart;
      this.nextDamagedPart = null;
      if (result && this.spikedBoxes.contains(part) && !damageSource.m_19360_()) {
         float damage = (float)this.entity.m_21133_(Attributes.f_22281_);
         if (damageSource.m_7639_() != null) {
            damageSource.m_7639_().m_6469_(DamageSource.m_19335_(this.entity), damage);
         }
      }
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      return true;
   }

   public void tick(ServerLevel level) {
      EntityPart rootYaw = this.hitboxes.getPart(this.root);
      rootYaw.setRotation(0.0, (double)(-this.entity.m_146908_()), 0.0, true);
   }
}
