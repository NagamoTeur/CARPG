package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class RitualAwakening extends AbstractRitual {
   EntityType<? extends LivingEntity> entity = null;
   BlockPos foundPos;

   public void destroyTree(Level world, Set<BlockPos> set) {
      for (BlockPos p : set) {
         BlockUtil.destroyBlockSafelyWithoutSound(world, p, false);
      }
   }

   public void findTargets(Level world) {
      for (BlockPos p : BlockPos.m_121925_(this.getPos(), 3, 1, 3)) {
         Set<BlockPos> blazing = SpellUtil.DFSBlockstates(
            world, p, 350, b -> b.m_60734_() == BlockRegistry.BLAZING_LOG || b.m_60734_() == BlockRegistry.BLAZING_LEAVES
         );
         if (blazing.size() >= 50) {
            this.entity = (EntityType<? extends LivingEntity>)ModEntities.ENTITY_BLAZING_WEALD.get();
            this.foundPos = p;
            this.destroyTree(world, blazing);
            return;
         }

         Set<BlockPos> flourishing = SpellUtil.DFSBlockstates(
            world, p, 350, b -> b.m_60734_() == BlockRegistry.FLOURISHING_LOG || b.m_60734_() == BlockRegistry.FLOURISHING_LEAVES
         );
         if (flourishing.size() >= 50) {
            this.entity = (EntityType<? extends LivingEntity>)ModEntities.ENTITY_FLOURISHING_WEALD.get();
            this.foundPos = p;
            this.destroyTree(world, flourishing);
            return;
         }

         Set<BlockPos> vexing = SpellUtil.DFSBlockstates(
            world, p, 350, b -> b.m_60734_() == BlockRegistry.VEXING_LOG || b.m_60734_() == BlockRegistry.VEXING_LEAVES
         );
         if (vexing.size() >= 50) {
            this.entity = (EntityType<? extends LivingEntity>)ModEntities.ENTITY_VEXING_WEALD.get();
            this.foundPos = p;
            this.destroyTree(world, vexing);
            return;
         }

         Set<BlockPos> cascading = SpellUtil.DFSBlockstates(
            world, p, 350, b -> b.m_60734_() == BlockRegistry.CASCADING_LOG || b.m_60734_() == BlockRegistry.CASCADING_LEAVE
         );
         if (cascading.size() >= 50) {
            this.entity = (EntityType<? extends LivingEntity>)ModEntities.ENTITY_CASCADING_WEALD.get();
            this.foundPos = p;
            this.destroyTree(world, cascading);
            return;
         }

         if (world.m_8055_(p).m_60734_() == Blocks.f_152491_) {
            world.m_7731_(p, Blocks.f_50016_.m_49966_(), 3);
            this.entity = (EntityType<? extends LivingEntity>)ModEntities.AMETHYST_GOLEM.get();
            this.foundPos = p;
            return;
         }
      }
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

      if (!world.f_46443_ && world.m_46467_() % 20L == 0L) {
         if (this.isBookwyrms()) {
            int progress = this.getProgress();
            int numBookwyrms = this.getConsumedItems().stream().filter(i -> i.m_41720_() instanceof WritableBookItem).mapToInt(ItemStack::m_41613_).sum();
            if (progress < numBookwyrms) {
               ItemStack charm = new ItemStack(ItemsRegistry.BOOKWYRM_CHARM);
               ItemEntity itemEntity = new ItemEntity(
                  world, (double)this.getPos().m_123341_() + 0.5, (double)(this.getPos().m_123342_() + 1), (double)this.getPos().m_123343_() + 0.5, charm
               );
               float range = 0.1F;
               itemEntity.m_20334_(
                  ParticleUtil.inRange((double)(-range), (double)range), ParticleUtil.inRange(0.4, 0.6), ParticleUtil.inRange((double)(-range), (double)range)
               );
               this.getWorld().m_5594_(null, this.getPos(), SoundEvents.f_11713_, SoundSource.BLOCKS, 1.0F, 1.0F);
               world.m_7967_(itemEntity);
            } else {
               this.setFinished();
            }
         } else if (this.getProgress() > 5) {
            this.findTargets(world);
            if (this.entity != null) {
               ParticleUtil.spawnPoof((ServerLevel)world, this.foundPos);
               LivingEntity walker = (LivingEntity)this.entity.m_20615_(world);
               walker.m_6034_((double)this.foundPos.m_123341_() + 0.5, (double)this.foundPos.m_123342_(), (double)this.foundPos.m_123343_() + 0.5);
               world.m_7967_(walker);
               this.setFinished();
            }
         }

         this.incrementProgress();
      }
   }

   public boolean isBookwyrms() {
      return this.getConsumedItems().stream().anyMatch(i -> i.m_41720_() instanceof WritableBookItem);
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return super.canConsumeItem(stack) || stack.m_41720_() instanceof WritableBookItem;
   }

   @Override
   public ParticleColor getCenterColor() {
      return new ParticleColor(50, 200, 50);
   }

   @Override
   public String getLangName() {
      return "Awakening";
   }

   @Override
   public String getLangDescription() {
      return "Awakens nearby Archwood trees into Weald Walkers and Budding Amethyst into Amethyst Golems. Weald Walkers can be given a position in the world to guard against hostile mobs. They will heal over time, and turn into Weald Waddlers if they die. To create a Weald Walker, perform this ritual near the base of an Archwood Tree. Augmenting with Book and Quills will create Bookwyrm Charms.";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.AWAKENING);
   }
}
