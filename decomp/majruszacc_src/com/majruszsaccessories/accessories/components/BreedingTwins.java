package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.events.OnBabySpawned;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.events.base.Events;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.function.Supplier;
import net.minecraft.world.entity.AgeableMob;

public class BreedingTwins extends BonusComponent<AccessoryItem> {
   static AgeableMob LAST_CHILD = null;
   RangedFloat chance = new RangedFloat().id("chance").maxRange(Range.CHANCE);

   public static BonusComponent.ISupplier<AccessoryItem> create(float chance) {
      return handler -> new BreedingTwins(handler, chance);
   }

   protected BreedingTwins(BonusHandler<AccessoryItem> handler, float chance) {
      super(handler);
      this.chance.set(chance, Range.CHANCE);
      OnBabySpawned.listen(this::spawnTwins)
         .addCondition(Condition.isLogicalServer())
         .addCondition(data -> data.player != null)
         .addCondition(data -> data.child != LAST_CHILD);
      this.addTooltip("majruszsaccessories.bonuses.spawn_twins", new ITooltipProvider[]{TooltipHelper.asPercent(this.chance)});
      handler.getConfig().define("breeding_twins", this.chance::define);
   }

   private void spawnTwins(OnBabySpawned data) {
      AccessoryHolder holder = AccessoryHolders.get(data.player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled() && Random.check(holder.apply(this.chance))) {
         LAST_CHILD = data.parentA.m_142606_(data.getServerLevel(), data.parentB);
         if (LAST_CHILD != null) {
            LAST_CHILD.m_6863_(true);
            LAST_CHILD.m_19890_(data.parentA.m_20185_(), data.parentA.m_20186_(), data.parentA.m_20189_(), 0.0F, 0.0F);
            data.getLevel().m_7967_(LAST_CHILD);
            Events.dispatch(new OnBabySpawned(data.parentA, data.parentB, LAST_CHILD, data.player));
            this.spawnEffects(data, LAST_CHILD, holder);
         }
      }
   }

   private void spawnEffects(OnBabySpawned data, AgeableMob child, AccessoryHolder holder) {
      holder.getParticleEmitter().count(4).sizeBased(child).emit(data.getServerLevel());
   }
}
