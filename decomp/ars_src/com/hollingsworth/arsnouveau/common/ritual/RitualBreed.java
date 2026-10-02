package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.phys.AABB;

public class RitualBreed extends AbstractRitual {
   @Override
   protected void tick() {
      if (this.getWorld().f_46443_) {
         ParticleUtil.spawnRitualAreaEffect(this.getPos(), this.getWorld(), this.rand, this.getCenterColor(), 5);
      } else if (this.getWorld().m_46467_() % 200L == 0L) {
         List<Animal> animals = this.getWorld().m_45976_(Animal.class, new AABB(this.getPos()).m_82400_(5.0));
         if (animals.size() >= 20) {
            return;
         }

         boolean didWorkOnce = false;

         for (Animal a : animals) {
            if (a.m_146764_() == 0 && a.m_5957_()) {
               didWorkOnce = true;
               a.m_27595_(null);
            }
         }

         if (didWorkOnce) {
            this.setNeedsSource(true);
         }
      }
   }

   @Override
   public String getLangDescription() {
      return "Periodically causes nearby animals to breed if possible. This ritual requires source to operate, and will have no effect if there are twenty or more animals nearby.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return new ParticleColor(100, 255, 100);
   }

   @Override
   public String getLangName() {
      return "Fertility";
   }

   @Override
   public int getSourceCost() {
      return 500;
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.FERTILITY);
   }
}
