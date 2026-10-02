package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractRitual {
   public RitualBrazierTile tile;
   private RitualContext context;
   public RandomSource rand = RandomSource.m_216327_();

   public AbstractRitual() {
   }

   @Deprecated(
      forRemoval = true
   )
   public AbstractRitual(RitualBrazierTile tile, RitualContext context) {
      this.tile = tile;
      this.setContext(context);
   }

   @Deprecated(
      forRemoval = true
   )
   public void tryTick() {
      this.tryTick(this.tile);
   }

   public void tryTick(RitualBrazierTile tickingTile) {
      if (tickingTile != null && this.getContext().isStarted && !this.getContext().isDone) {
         this.tile = tickingTile;
         this.tick();
      }
   }

   @Nullable
   public BlockPos getPos() {
      return this.tile != null ? this.tile.m_58899_() : null;
   }

   @Nullable
   public Level getWorld() {
      return this.tile != null ? this.tile.m_58904_() : null;
   }

   public boolean canStart() {
      return true;
   }

   public List<ItemStack> getConsumedItems() {
      return this.getContext().consumedItems;
   }

   public boolean canConsumeItem(ItemStack stack) {
      return false;
   }

   public void onItemConsumed(ItemStack stack) {
      this.getConsumedItems().add(stack.m_41620_(1));
      BlockUtil.safelyUpdateState(this.getWorld(), this.tile.m_58899_());
   }

   public boolean didConsumeItem(ItemLike item) {
      for (ItemStack i : this.getConsumedItems()) {
         if (i.m_41720_() == item.m_5456_()) {
            return true;
         }
      }

      return false;
   }

   public int itemConsumedCount(Predicate<ItemStack> stackPredicate) {
      int total = 0;

      for (ItemStack stack : this.getConsumedItems()) {
         if (stackPredicate.test(stack)) {
            total += stack.m_41613_();
         }
      }

      return total;
   }

   public List<String> getFormattedConsumedItems() {
      Map<String, Integer> map = new HashMap<>();

      for (ItemStack i : this.getConsumedItems()) {
         String name = i.m_41786_().getString();
         if (map.containsKey(name)) {
            map.put(name, map.get(name) + 1);
         } else {
            map.put(name, 1);
         }
      }

      List<String> list = new ArrayList<>();

      for (String s : map.keySet()) {
         list.add(s + " x " + map.get(s));
      }

      return list;
   }

   public void incrementProgress() {
      this.getContext().progress++;
   }

   public int getProgress() {
      return this.getContext().progress;
   }

   public void onStart() {
      this.getContext().isStarted = true;
   }

   public boolean isRunning() {
      return this.getContext().isStarted && !this.getContext().isDone;
   }

   public boolean isDone() {
      return this.getContext().isDone;
   }

   public void setFinished() {
      this.getContext().isDone = true;
   }

   protected abstract void tick();

   public void onEnd() {
      this.getContext().isDone = true;
   }

   public String getName() {
      return Component.m_237110_(
            "ars_nouveau.tablet_of",
            new Object[]{Component.m_237115_("item." + this.getRegistryName().m_135827_() + "." + this.getRegistryName().m_135815_()).getString()}
         )
         .getString();
   }

   public String getDescription() {
      return Component.m_237115_(this.getDescriptionKey()).getString();
   }

   public String getDescriptionKey() {
      return this.getRegistryName().m_135827_() + ".ritual_desc." + this.getRegistryName().m_135815_();
   }

   public int getSourceCost() {
      return 0;
   }

   public boolean consumesSource() {
      return this.getSourceCost() > 0;
   }

   public void setNeedsSource(boolean needMana) {
      this.getContext().needsSourceToRun = needMana;
      BlockUtil.safelyUpdateState(this.getWorld(), this.tile.m_58899_());
   }

   public boolean needsSourceNow() {
      return this.getContext().needsSourceToRun;
   }

   public boolean takeSourceNow() {
      this.setNeedsSource(true);
      return this.tile.takeSource();
   }

   public void write(CompoundTag tag) {
      CompoundTag contextTag = new CompoundTag();
      this.getContext().write(contextTag);
      tag.m_128365_("context", contextTag);
   }

   public void read(CompoundTag tag) {
      this.setContext(RitualContext.read(tag.m_128469_("context")));
   }

   @NotNull
   public RitualContext getContext() {
      if (this.context == null) {
         this.context = new RitualContext();
      }

      return this.context;
   }

   public void setContext(RitualContext context) {
      this.context = context;
   }

   public abstract ResourceLocation getRegistryName();

   public ParticleColor getCenterColor() {
      return new ParticleColor(this.rand.m_188503_(255), this.rand.m_188503_(22), this.rand.m_188503_(255));
   }

   public ParticleColor getOuterColor() {
      return this.getCenterColor();
   }

   public int getParticleIntensity() {
      return 10;
   }

   public String getLangName() {
      return "";
   }

   public String getLangDescription() {
      return "";
   }

   public boolean canBeTraded() {
      return true;
   }
}
