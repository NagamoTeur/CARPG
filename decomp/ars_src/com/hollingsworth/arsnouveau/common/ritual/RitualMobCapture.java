package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

public class RitualMobCapture extends AbstractRitual {
   @Override
   protected void tick() {
      Level world = this.getWorld();
      int radius = 3;
      if (world.f_46443_) {
         BlockPos pos = this.getPos();
         ParticleUtil.spawnRitualAreaEffect(this.getPos(), this.getWorld(), this.rand, this.getCenterColor(), radius);
      }

      if (!this.getWorld().f_46443_ && world.m_46467_() % 60L == 0L) {
         boolean didWorkOnce = false;
         Level level = this.getWorld();
         BlockPos pos = this.getPos();

         for (BlockPos blockPos : BlockPos.m_121940_(pos.m_7918_(-radius, -radius, -radius), pos.m_7918_(radius, radius, radius))) {
            if (level.m_8055_(blockPos).m_60734_() == BlockRegistry.MOB_JAR) {
               MobJarTile tile = (MobJarTile)level.m_7702_(blockPos);
               if (tile != null && tile.getEntity() == null) {
                  for (Entity e : level.m_6249_((Entity)null, new AABB(tile.m_58899_()).m_82400_(5.0), this::canJar)) {
                     if (e instanceof Mob mob && ((Mob)e).m_21523_() && e.m_142391_() && mob.m_21523_()) {
                        mob.m_21455_(true, true);
                     }

                     if (e instanceof Raider raider && raider.m_37886_()) {
                        raider.m_37885_().m_37740_(raider, false);
                     }

                     if (tile.setEntityData(e)) {
                        e.m_142687_(RemovalReason.UNLOADED_TO_CHUNK);
                        EntityFlyingItem followProjectile = new EntityFlyingItem(level, e.f_19825_, Vec3.m_82512_(tile.m_58899_()), 100, 50, 100);
                        level.m_7967_(followProjectile);
                        ParticleUtil.spawnPoof((ServerLevel)level, e.m_20097_().m_7494_());
                        didWorkOnce = true;
                        if (e instanceof Starbuncle starbuncle) {
                           ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.SHRUNK_STARBY, (ServerLevel)level, starbuncle.m_20183_(), 10);
                        }

                        if (e instanceof LightningBolt bolt) {
                           ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.CAUGHT_LIGHTNING, (ServerLevel)level, bolt.m_20183_(), 10);
                        }

                        if (e instanceof ItemEntity item && item.m_32055_().m_41720_() == Items.f_42524_) {
                           ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.TIME_IN_BOTTLE, (ServerLevel)level, item.m_20183_(), 10);
                        }
                        break;
                     }
                  }
               }
            }
         }

         if (didWorkOnce) {
            this.setNeedsSource(true);
         }
      }
   }

   public boolean canJar(Entity e) {
      if (e.m_6095_().m_204039_(EntityTags.JAR_WHITELIST)) {
         return true;
      } else if (e.m_6095_().m_204039_(EntityTags.JAR_BLACKLIST)) {
         return false;
      } else if (e instanceof PartEntity) {
         return false;
      } else {
         if (e instanceof LivingEntity livingEntity && !(e instanceof Player) && !((LivingEntity)e).m_21224_()) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int getSourceCost() {
      return 500;
   }

   @Override
   public String getLangDescription() {
      return "Captures a nearby entity and places it into any nearby placed Containment Jars. After the first capture, this ritual requires additional source to continue. Mobs and jars must be within 3 blocks of the brazier.";
   }

   @Override
   public String getLangName() {
      return "Containment";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.CONTAINMENT);
   }
}
