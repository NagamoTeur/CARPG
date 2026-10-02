package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.emitter.ParticleEmitter;
import com.majruszlibrary.entity.AttributeHandler;
import com.majruszlibrary.events.OnItemFished;
import com.majruszlibrary.events.OnPlayerTicked;
import com.majruszlibrary.events.base.Condition;
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
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class FishingLuckBonus extends BonusComponent<AccessoryItem> {
   final AttributeHandler attribute;
   RangedFloat luck = new RangedFloat().id("bonus").maxRange(Range.of(0.0F, 100.0F));

   public static BonusComponent.ISupplier<AccessoryItem> create(float luck) {
      return handler -> new FishingLuckBonus(handler, luck);
   }

   protected FishingLuckBonus(BonusHandler<AccessoryItem> handler, float luck) {
      super(handler);
      this.attribute = new AttributeHandler("%s_fishing_luck_bonus".formatted(handler.getId()), () -> Attributes.f_22286_, Operation.ADDITION);
      this.luck.set(luck, Range.of(0.0F, 10.0F));
      OnPlayerTicked.listen(this::updateLuck).addCondition(Condition.isLogicalServer()).addCondition(Condition.cooldown(4.0F));
      OnItemFished.listen(this::spawnEffects).addCondition(Condition.isLogicalServer());
      this.addTooltip("majruszsaccessories.bonuses.fishing_luck", new ITooltipProvider[]{TooltipHelper.asValue(this.luck)});
      handler.getConfig().define("fishing_luck", this.luck::define);
   }

   private void updateLuck(OnPlayerTicked data) {
      this.attribute.setValue((double)this.getLuck(data.player)).apply(data.player);
   }

   private float getLuck(Player player) {
      if (player.f_36083_ == null) {
         return 0.0F;
      } else {
         AccessoryHolder holder = AccessoryHolders.get(player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
         return holder.isValid() && !holder.isBonusDisabled() ? holder.apply(this.luck) : 0.0F;
      }
   }

   private void spawnEffects(OnItemFished data) {
      AccessoryHolder holder = AccessoryHolders.get(data.player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled()) {
         BlockPos position = LevelHelper.getPositionOverFluid(data.getLevel(), data.hook.m_20183_());
         holder.getParticleEmitter()
            .count(4)
            .offset(ParticleEmitter.offset(0.125F))
            .position(AnyPos.from(data.hook.m_20185_(), (double)position.m_123342_() + 0.25, data.hook.m_20189_()).vec3())
            .emit(data.getServerLevel());
      }
   }
}
