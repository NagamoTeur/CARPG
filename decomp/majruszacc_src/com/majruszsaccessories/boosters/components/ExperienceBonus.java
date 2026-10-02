package com.majruszsaccessories.boosters.components;

import com.majruszlibrary.events.OnExpOrbPickedUp;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.BoosterItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;

public class ExperienceBonus extends BonusComponent<BoosterItem> {
   RangedFloat bonus = new RangedFloat().id("experience_bonus");

   public static BonusComponent.ISupplier<BoosterItem> create(float bonus) {
      return handler -> new ExperienceBonus(handler, bonus);
   }

   protected ExperienceBonus(BonusHandler<BoosterItem> handler, float bonus) {
      super(handler);
      this.bonus.set(bonus, Range.of(0.0F, 1.0F));
      OnExpOrbPickedUp.listen(this::increaseExperience);
      this.addTooltip(
         "majruszsaccessories.boosters.experience_bonus",
         new ITooltipProvider[]{TooltipHelper.asBooster(() -> this.getItem()), TooltipHelper.asFixedPercent(this.bonus)}
      );
      this.bonus.define(handler.getConfig());
   }

   private void increaseExperience(OnExpOrbPickedUp data) {
      data.experience = data.experience
         + Random.round((double)((float)data.original * this.bonus.get() * (float)AccessoryHolders.get(data.player).getBoostersCount(() -> this.getItem())));
   }
}
