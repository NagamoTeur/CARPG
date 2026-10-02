package dev.latvian.mods.kubejs.entity.forge;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.jetbrains.annotations.Nullable;

public class LivingEntityDropsEventJS extends LivingEntityEventJS {
   private final LivingDropsEvent event;
   public List<ItemEntity> eventDrops;

   public LivingEntityDropsEventJS(LivingDropsEvent e) {
      this.event = e;
   }

   @Override
   public LivingEntity getEntity() {
      return this.event.getEntity();
   }

   public DamageSource getSource() {
      return this.event.getSource();
   }

   public int getLootingLevel() {
      return this.event.getLootingLevel();
   }

   public boolean isRecentlyHit() {
      return this.event.isRecentlyHit();
   }

   public List<ItemEntity> getDrops() {
      if (this.eventDrops == null) {
         this.eventDrops = new ArrayList<>(this.event.getDrops());
      }

      return this.eventDrops;
   }

   @Nullable
   public ItemEntity addDrop(ItemStack stack) {
      if (!stack.m_41619_()) {
         LivingEntity e = this.event.getEntity();
         ItemEntity ei = new ItemEntity(e.f_19853_, e.m_20185_(), e.m_20186_(), e.m_20189_(), stack);
         ei.m_32010_(10);
         this.getDrops().add(ei);
         return ei;
      } else {
         return null;
      }
   }

   @Nullable
   public ItemEntity addDrop(ItemStack stack, float chance) {
      return !(chance >= 1.0F) && !(this.event.getEntity().f_19853_.f_46441_.m_188501_() <= chance) ? null : this.addDrop(stack);
   }
}
