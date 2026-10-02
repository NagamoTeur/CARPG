package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RitualBinding extends AbstractRitual {
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

      if (!world.f_46443_ && world.m_46467_() % 20L == 0L) {
         this.incrementProgress();
         if (this.getProgress() >= 3) {
            for (Entity entity : this.getWorld().m_45976_(Entity.class, new AABB(this.getPos()).m_82400_(5.0))) {
               for (AbstractFamiliarHolder familiarHolder : ArsNouveauAPI.getInstance().getFamiliarHolderMap().values()) {
                  if (familiarHolder.isEntity.test(entity)) {
                     entity.m_142687_(RemovalReason.DISCARDED);
                     ParticleUtil.spawnPoof((ServerLevel)world, entity.m_20183_());
                     world.m_7967_(
                        new ItemEntity(
                           world,
                           (double)entity.m_20183_().m_123341_(),
                           (double)entity.m_20183_().m_123342_(),
                           (double)entity.m_20183_().m_123343_(),
                           familiarHolder.getOutputItem()
                        )
                     );
                     world.m_5594_(null, entity.m_20183_(), SoundEvents.f_11714_, SoundSource.NEUTRAL, 1.0F, 1.0F);
                     ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.FAMILIAR, (ServerLevel)world, entity.m_20183_(), 8);
                  }
               }
            }

            this.setFinished();
         }
      }
   }

   @Override
   public String getLangName() {
      return "Binding";
   }

   @Override
   public String getLangDescription() {
      return "The Ritual of Binding converts nearby eligible entities into Bound Scripts, used for summoning a Familiar. For more information, see the section on Familiars.";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.BINDING);
   }
}
