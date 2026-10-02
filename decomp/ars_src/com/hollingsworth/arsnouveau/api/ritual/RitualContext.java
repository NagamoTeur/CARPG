package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class RitualContext {
   public int progress = 0;
   public boolean isDone = false;
   public boolean isStarted = false;
   public boolean needsSourceToRun;
   public List<ItemStack> consumedItems = new ArrayList<>();

   public RitualContext() {
      this.needsSourceToRun = false;
   }

   public void write(CompoundTag tag) {
      tag.m_128405_("progress", this.progress);
      tag.m_128379_("complete", this.isDone);
      tag.m_128379_("started", this.isStarted);
      tag.m_128379_("needsMana", this.needsSourceToRun);
      NBTUtil.writeItems(tag, "item_", this.consumedItems);
   }

   public static RitualContext read(CompoundTag tag) {
      RitualContext context = new RitualContext();
      context.progress = tag.m_128451_("progress");
      context.isDone = tag.m_128471_("complete");
      context.isStarted = tag.m_128471_("started");
      context.consumedItems = NBTUtil.readItems(tag, "item_");
      context.needsSourceToRun = tag.m_128471_("needsMana");
      return context;
   }
}
