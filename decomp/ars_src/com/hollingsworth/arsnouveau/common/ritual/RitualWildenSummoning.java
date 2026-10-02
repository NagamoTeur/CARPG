package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.common.entity.WildenChimera;
import com.hollingsworth.arsnouveau.common.entity.WildenGuardian;
import com.hollingsworth.arsnouveau.common.entity.WildenHunter;
import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;

public class RitualWildenSummoning extends AbstractRitual {
   @Override
   protected void tick() {
      WildenChimera.spawnPhaseParticles(this.getPos().m_7494_(), this.getWorld(), 1);
      if (this.getWorld().m_46467_() % 20L == 0L) {
         this.incrementProgress();
      }

      if (this.getWorld().m_46467_() % 60L == 0L && !this.getWorld().f_46443_) {
         if (!this.isBossSpawn()) {
            int wild = this.rand.m_188503_(3);
            BlockPos summonPos = this.getPos()
               .m_7494_()
               .m_122030_(this.rand.m_188503_(3) - this.rand.m_188503_(6))
               .m_122013_(this.rand.m_188503_(3) - this.rand.m_188503_(6));

            Mob mobEntity = (Mob)(switch (wild) {
               case 0 -> new WildenStalker(this.getWorld());
               case 1 -> new WildenGuardian(this.getWorld());
               default -> new WildenHunter(this.getWorld());
            });
            this.summon(mobEntity, summonPos);
            if (this.getProgress() >= 15) {
               this.setFinished();
            }
         } else if (this.getProgress() >= 8) {
            WildenChimera chimera = new WildenChimera(this.getWorld());
            this.summon(chimera, this.getPos().m_7494_());

            for (BlockPos b : BlockPos.m_121940_(this.getPos().m_122030_(5).m_122013_(5).m_7494_(), this.getPos().m_122025_(5).m_122020_(5).m_6630_(5))) {
               if (ForgeEventFactory.getMobGriefingEvent(this.getWorld(), chimera) && SpellUtil.isCorrectHarvestLevel(4, this.getWorld().m_8055_(b))) {
                  BlockUtil.destroyBlockSafelyWithoutSound(this.getWorld(), b, true);
               }
            }

            this.setFinished();
         }
      }
   }

   public boolean isBossSpawn() {
      return this.didConsumeItem(ItemsRegistry.WILDEN_HORN)
         && this.didConsumeItem(ItemsRegistry.WILDEN_WING)
         && this.didConsumeItem(ItemsRegistry.WILDEN_SPIKE);
   }

   public void summon(Mob mob, BlockPos pos) {
      mob.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      mob.f_19853_.m_7967_(mob);
   }

   @Override
   public String getLangName() {
      return "Summon Wilden";
   }

   @Override
   public String getLangDescription() {
      return "Without augments, this ritual will summon a random variety of Wilden monsters for a short duration. When augmented with a Wilden Spike, Wilden Horn, and a Wilden Wing, this ritual will summon the Wilden Chimera, a challenging and destructive monster. Note: If summoning the chimera, this ritual will destroy blocks around the brazier.";
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return stack.m_204117_(ItemTagProvider.WILDEN_DROP_TAG);
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.WILDEN_SUMMON);
   }
}
