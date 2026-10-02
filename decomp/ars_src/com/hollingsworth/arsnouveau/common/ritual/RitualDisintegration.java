package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RitualDisintegration extends AbstractRitual {
   @Override
   public void onStart() {
      super.onStart();
   }

   @Override
   protected void tick() {
      Level world = this.getWorld();
      if (world.f_46443_) {
         BlockPos pos = this.getPos();

         for (int i = 0; i < 10; i++) {
            Vec3 particlePos = new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()).m_82520_(0.5, 0.0, 0.5);
            particlePos = particlePos.m_82549_(ParticleUtil.pointInSphere().m_82542_(5.0, 5.0, 5.0));
            world.m_7106_(
               ParticleLineData.createData(this.getCenterColor()),
               particlePos.m_7096_(),
               particlePos.m_7098_(),
               particlePos.m_7094_(),
               (double)pos.m_123341_() + 0.5,
               (double)(pos.m_123342_() + 1),
               (double)pos.m_123343_() + 0.5
            );
         }
      }

      if (!world.f_46443_ && world.m_46467_() % 60L == 0L) {
         boolean didWorkOnce = false;

         for (LivingEntity m : world.m_6443_(
            LivingEntity.class,
            new AABB(this.getPos()).m_82400_(5.0),
            mx -> (mx.getClassification(false).equals(MobCategory.MONSTER) || mx.m_6095_().m_204039_(EntityTags.DISINTEGRATION_WHITELIST))
                  && !(mx instanceof Player)
         )) {
            if (!m.m_6095_().m_204039_(EntityTags.DISINTEGRATION_BLACKLIST)) {
               m.m_142687_(RemovalReason.DISCARDED);
               if (m.m_213877_()) {
                  ParticleUtil.spawnPoof((ServerLevel)world, m.m_20183_());
                  if (m.m_6149_()) {
                     int exp = m.m_213860_() * 2;
                     if (exp > 0) {
                        int numGreater = exp / 12;
                        exp -= numGreater * 12;
                        int numLesser = exp / 3;
                        if (exp - numLesser * 3 > 0) {
                           numLesser++;
                        }

                        world.m_7967_(
                           new ItemEntity(
                              world,
                              (double)m.m_20183_().m_123341_(),
                              (double)m.m_20183_().m_123342_(),
                              (double)m.m_20183_().m_123343_(),
                              new ItemStack(ItemsRegistry.GREATER_EXPERIENCE_GEM, numGreater)
                           )
                        );
                        world.m_7967_(
                           new ItemEntity(
                              world,
                              (double)m.m_20183_().m_123341_(),
                              (double)m.m_20183_().m_123342_(),
                              (double)m.m_20183_().m_123343_(),
                              new ItemStack(ItemsRegistry.EXPERIENCE_GEM, numLesser)
                           )
                        );
                        didWorkOnce = true;
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

   @Override
   public int getSourceCost() {
      return 300;
   }

   @Override
   public String getLangName() {
      return "Disintegration";
   }

   @Override
   public String getLangDescription() {
      return "Destroys nearby monsters and converts them into Experience Gems worth twice as much experience. Monsters destroyed this way will not drop items. This ritual consumes source each time a monster is destroyed.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return ParticleColor.makeRandomColor(220, 20, 20, this.rand);
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.DISINTEGRATION);
   }
}
