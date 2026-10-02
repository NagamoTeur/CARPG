package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RitualWarp extends AbstractRitual {
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
            List<Entity> entities = this.getWorld().m_45976_(Entity.class, new AABB(this.getPos()).m_82400_(5.0));
            ItemStack i = this.getConsumedItems().get(0);
            WarpScroll.WarpScrollData data = new WarpScroll.WarpScrollData(i);
            BlockPos b = data.getPos();

            for (Entity a : entities) {
               if (b != null) {
                  a.m_6021_((double)b.m_123341_(), (double)b.m_123342_(), (double)b.m_123343_());
               }
            }

            if (b != null) {
               world.m_5594_(null, b, SoundEvents.f_12287_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }

            this.setFinished();
         }
      }
   }

   @Override
   public String getLangName() {
      return "Warping";
   }

   @Override
   public String getLangDescription() {
      return "Warps all nearby entities to the location on a warp scroll. Before starting the ritual, you must first augment the ritual with an inscribed Warp Scroll.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return super.getCenterColor();
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      if (stack.m_41720_() instanceof WarpScroll && this.getConsumedItems().isEmpty()) {
         WarpScroll.WarpScrollData data = new WarpScroll.WarpScrollData(stack);
         return data.isValid() && data.canTeleportWithDim(this.tile.m_58904_());
      } else {
         return false;
      }
   }

   @Override
   public boolean canStart() {
      return !this.getConsumedItems().isEmpty();
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.WARP);
   }
}
