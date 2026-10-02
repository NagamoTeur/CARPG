package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.api.source.SourcelinkEventQueue;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class VitalicSourcelinkTile extends SourcelinkTile {
   private static final String TAG_POISONED = "quark:poison_potato_applied";

   public VitalicSourcelinkTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public VitalicSourcelinkTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.VITALIC_TILE, pos, state);
   }

   @Override
   public int getMaxSource() {
      return 2500;
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.f_58857_.f_46443_ && this.f_58857_.m_46467_() % 60L == 0L) {
         for (Animal entity : this.f_58857_.m_45976_(Animal.class, new AABB(this.f_58858_).m_82400_(6.0))) {
            if (entity.m_6162_() && entity.m_146764_() < 0) {
               if (ModList.get().isLoaded("quark") && entity.getPersistentData().m_128441_("quark:poison_potato_applied")) {
                  return;
               }

               entity.m_146762_(Math.min(0, entity.m_146764_() + 500));
               this.addSource(10);
               ParticleUtil.spawnFollowProjectile(this.f_58857_, entity.m_20183_(), this.f_58858_);
            }
         }
      }
   }

   @SubscribeEvent
   public static void babySpawnEvent(BabyEntitySpawnEvent e) {
      int mana = 600;
      SourcelinkEventQueue.addManaEvent(e.getParentA().f_19853_, VitalicSourcelinkTile.class, mana, e, e.getParentA().m_20183_());
   }

   @SubscribeEvent
   public static void livingDeath(LivingDeathEvent e) {
      if (!e.getEntity().f_19853_.f_46443_ && !(e.getEntity() instanceof IDispellable) && !(e.getEntity() instanceof ISummon)) {
         int mana = 200;
         SourcelinkEventQueue.addManaEvent(e.getEntity().f_19853_, VitalicSourcelinkTile.class, mana, e, e.getEntity().m_20183_());
      }
   }

   @Override
   public boolean usesEventQueue() {
      return true;
   }
}
