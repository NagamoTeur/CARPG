package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.RangeRitual;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.eventbus.api.Event.Result;

public class DenySpawnRitual extends RangeRitual {
   public int radius = 32;
   public boolean deniedSpawn;

   public boolean denySpawn(CheckSpawn checkSpawn) {
      boolean shouldDeny = checkSpawn.getSpawnReason() == MobSpawnType.NATURAL
         && !checkSpawn.isSpawner()
         && checkSpawn.getEntity() instanceof Enemy
         && checkSpawn.getEntity().m_20275_((double)this.getPos().m_123341_(), (double)this.getPos().m_123342_(), (double)this.getPos().m_123343_())
            <= (double)(this.radius * this.radius);
      if (shouldDeny) {
         checkSpawn.setResult(Result.DENY);
         this.deniedSpawn = true;
      }

      return shouldDeny;
   }

   @Override
   public void onStart() {
      super.onStart();
      if (!this.getWorld().f_46443_) {
         for (ItemStack i : this.getConsumedItems()) {
            if (i.m_150930_(Items.f_42583_)) {
               this.radius = this.radius + i.m_41613_();
            }
         }
      }
   }

   @Override
   protected void tick() {
      super.tick();
      if (!this.getWorld().f_46443_) {
         if (this.deniedSpawn && this.getWorld().m_46467_() % 1200L == 0L) {
            this.deniedSpawn = false;
            this.takeSourceNow();
         }
      }
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return stack.m_150930_(Items.f_42583_) && this.itemConsumedCount(i -> i.m_41720_() == Items.f_42583_) < 128;
   }

   @Override
   public int getSourceCost() {
      return 500;
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.SANCTUARY);
   }

   @Override
   public String getLangName() {
      return "Sanctuary";
   }

   @Override
   public String getLangDescription() {
      return "Denies hostile mobs from naturally spawning in a 32 block radius. Augment with rotten flesh to increase the radius by 1 each, up to 128. Costs source once a minute if a spawn is denied.";
   }

   @Override
   public void read(CompoundTag tag) {
      super.read(tag);
      this.radius = tag.m_128451_("radius");
      this.deniedSpawn = tag.m_128471_("deniedSpawn");
   }

   @Override
   public void write(CompoundTag tag) {
      super.write(tag);
      tag.m_128405_("radius", this.radius);
      tag.m_128379_("deniedSpawn", this.deniedSpawn);
   }
}
