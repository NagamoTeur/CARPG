package com.aqutheseal.celestisynth.common.entity.skill;

import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.base.EffectControllerEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.item.weapons.CrescentiaItem;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.AABB;

public class SkillCastCrescentiaRanged extends EffectControllerEntity {
   public SkillCastCrescentiaRanged(EntityType<?> type, Level level) {
      super(type, level);
   }

   @Override
   public Item getCorrespondingItem() {
      return (Item)CSItems.CRESCENTIA.get();
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      UUID ownerUuid = this.getOwnerUuid();
      Player ownerPlayer = ownerUuid == null ? null : this.m_9236_().m_46003_(ownerUuid);
      this.setAngleX(this.getAngleX() + this.getAddAngleX());
      this.setAngleY(this.getAngleY() + this.getAddAngleY());
      this.setAngleZ(this.getAngleZ() + this.getAddAngleZ());
      double newX = this.m_20185_() + (double)this.getAngleX();
      double newY = this.m_20186_() + (double)this.getAngleY();
      double newZ = this.m_20189_() + (double)this.getAngleZ();
      BlockPos newPos = new BlockPos((int)newX, (int)newY, (int)newZ);
      double range = 7.0;
      List<Entity> entities = this.f_19853_
         .m_45976_(Entity.class, new AABB(newX + range, newY + range, newZ + range, newX - range, newY - range, newZ - range));
      ItemStack fireworkStack = new ItemStack(Items.f_42688_);

      for (Entity entityBatch : entities) {
         if (entityBatch instanceof LivingEntity target && target != ownerPlayer && target.m_6084_()) {
            this.fromInterfaceWeapon().hurtNoKB(ownerPlayer, target, (float)((Double)CSConfigManager.COMMON.crescentiaShiftSkillDmg.get()).doubleValue());
            target.m_6469_(DamageSource.m_19344_(ownerPlayer), (float)((Double)CSConfigManager.COMMON.crescentiaShiftSkillDmg.get()).doubleValue());
            target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 2));
         }

         if (entityBatch instanceof Projectile projectile) {
            CrescentiaItem.createCrescentiaFirework(
               fireworkStack, this.f_19853_, ownerPlayer, projectile.m_20185_(), projectile.m_20186_(), projectile.m_20189_(), true
            );
            projectile.m_5496_(SoundEvents.f_11932_, 1.0F, 1.0F);
            projectile.m_142687_(RemovalReason.DISCARDED);
         }
      }

      if (this.f_19797_ % 3 == 0) {
         float offX = this.f_19796_.m_188501_() * 20.0F - 10.0F;
         float offY = this.f_19796_.m_188501_() * 20.0F - 10.0F;
         float offZ = this.f_19796_.m_188501_() * 20.0F - 10.0F;
         CrescentiaItem.createCrescentiaFirework(
            fireworkStack,
            this.f_19853_,
            ownerPlayer,
            this.m_20185_() + (double)this.getAngleX() + (double)offX,
            this.m_20186_() + (double)this.getAngleY() + (double)offY,
            this.m_20189_() + (double)this.getAngleZ() + (double)offZ,
            false
         );
         CrescentiaItem.createCrescentiaFirework(
            fireworkStack,
            this.f_19853_,
            ownerPlayer,
            (double)(this.getAngleX() + offZ),
            (double)this.getAngleY() - 1.5 + (double)offX,
            (double)(this.getAngleZ() + offY),
            false
         );
      }

      if (this.f_19796_.m_188499_()) {
         CSEffectEntity.createInstance(
            ownerPlayer,
            this,
            (CSVisualType)CSVisualTypes.CRESCENTIA_THROW.get(),
            (double)this.getAngleX(),
            (double)this.getAngleY() - 1.5,
            (double)this.getAngleZ()
         );
      } else {
         CSEffectEntity.createInstance(
            ownerPlayer,
            this,
            (CSVisualType)CSVisualTypes.CRESCENTIA_THROW_INVERTED.get(),
            (double)this.getAngleX(),
            (double)this.getAngleY() - 1.5,
            (double)this.getAngleZ()
         );
      }

      CSEffectEntity.createInstance(
         ownerPlayer, this, (CSVisualType)CSVisualTypes.SOLARIS_AIR.get(), (double)this.getAngleX(), (double)this.getAngleY(), (double)this.getAngleZ()
      );
      this.playRandomBladeSound(BASE_WEAPON_EFFECTS.length, newX, newY, newZ);
      int radius = 2;

      for (int sx = -radius; sx <= radius; sx++) {
         for (int sy = -radius; sy <= radius; sy++) {
            for (int sz = -radius; sz <= radius; sz++) {
               if (this.m_9236_().m_8055_(newPos.m_7918_(sx, sy, sz)).m_204336_(BlockTags.f_198158_)) {
                  this.m_9236_().m_46953_(newPos.m_7918_(sx, sy, sz), false, ownerPlayer);
               }
            }
         }
      }

      if (this.f_19797_ == 100 || !this.m_9236_().m_8055_(newPos).m_60795_()) {
         this.f_19853_.m_46511_(ownerPlayer, newX, newY, newZ, 3.0F, BlockInteraction.DESTROY);
         CrescentiaItem.createCrescentiaFirework(fireworkStack, this.f_19853_, ownerPlayer, newX, newY, newZ, true);
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public void playRandomBladeSound(int length, double x, double y, double z) {
      SoundEvent randomSound = BASE_WEAPON_EFFECTS[this.f_19796_.m_188503_(length)];
      this.f_19853_.m_6263_(this.f_19853_.m_46003_(this.getOwnerUuid()), x, y, z, randomSound, SoundSource.HOSTILE, 0.1F, 0.5F + new Random().nextFloat());
   }
}
