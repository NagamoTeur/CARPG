package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.data.Reader;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.events.OnItemBrewed;
import com.majruszlibrary.level.LevelHelper;
import com.majruszlibrary.math.AnyPos;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class StrongerPotions extends BonusComponent<AccessoryItem> {
   RangedFloat durationPenalty = new RangedFloat().id("duration_penalty").maxRange(Range.of(0.0F, 1.0F));
   RangedFloat amplifier = new RangedFloat().id("amplifier").maxRange(Range.of(1.0F, 20.0F));

   public static BonusComponent.ISupplier<AccessoryItem> create(float durationPenalty, float amplifier) {
      return handler -> new StrongerPotions(handler, durationPenalty, amplifier);
   }

   protected StrongerPotions(BonusHandler<AccessoryItem> handler, float durationPenalty, float amplifier) {
      super(handler);
      this.durationPenalty.set(durationPenalty, Range.of(0.0F, 1.0F));
      this.amplifier.set(amplifier, Range.of(1.0F, 10.0F));
      OnItemBrewed.listen(this::boostPotions)
         .addCondition(data -> data.items.subList(0, 3).stream().anyMatch(itemStack -> !PotionUtils.m_43547_(itemStack).isEmpty()));
      this.addTooltip("majruszsaccessories.bonuses.potion_amplifier", new ITooltipProvider[]{TooltipHelper.asValue(this.amplifier).scaleOnlyOnDetailed()});
      this.addTooltip(
         "majruszsaccessories.bonuses.potion_duration", new ITooltipProvider[]{TooltipHelper.asPercent(this.durationPenalty).bonusMultiplier(-1.0F)}
      );
      handler.getConfig().define("stronger_potion", subconfig -> {
         this.durationPenalty.define(subconfig);
         this.amplifier.define(subconfig);
      });
   }

   private void boostPotions(OnItemBrewed data) {
      Player player = LevelHelper.getNearestPlayer(data.level, data.blockPos, 10.0F);
      AccessoryHolder holder = AccessoryHolders.get(player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled()) {
         data.mapPotions(
            potions -> {
               float durationMultiplier = 1.0F - holder.apply(this.durationPenalty, -1.0F);
               int extraAmplifier = Math.round(holder.apply(this.amplifier));
               return potions.stream()
                  .map(
                     itemStack -> {
                        List<MobEffectInstance> effects = PotionUtils.m_43547_(itemStack);
                        if (effects.isEmpty()) {
                           return (ItemStack)itemStack;
                        } else {
                           ItemStack potion = new ItemStack(itemStack.m_41720_());
                           Serializables.write(new StrongerPotions.Data(), potion.m_41784_());
                           return PotionUtils.m_43552_(
                              potion,
                              PotionUtils.m_43547_(itemStack)
                                 .stream()
                                 .map(
                                    effect -> new MobEffectInstance(
                                          effect.m_19544_(),
                                          Math.max(40, (int)((float)effect.m_19557_() * durationMultiplier)),
                                          effect.m_19564_() + extraAmplifier
                                       )
                                 )
                                 .toList()
                           );
                        }
                     }
                  )
                  .toList();
            }
         );
         this.spawnEffects(data, holder);
      }
   }

   private void spawnEffects(OnItemBrewed data, AccessoryHolder holder) {
      holder.getParticleEmitter().count(6).position(AnyPos.from(data.blockPos).center().vec3()).emit(data.getServerLevel());
   }

   private static class Data {
      private String name = "{\"translate\":\"majruszsaccessories.bonuses.potion_name\",\"italic\":false}";

      static {
         Serializables.get(StrongerPotions.Data.class).define("display", config -> config.define("Name", Reader.string(), s -> s.name, (s, v) -> s.name = v));
      }
   }
}
