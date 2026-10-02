package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class DividerItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "divider")
      .var(10, "s")
      .action(
         (stack, entity, target, context, values) -> {
            if (target == null) {
               return false;
            } else {
               MobEffectInstance EFFECT = target.m_21124_((MobEffect)AquamiraeMobEffects.HEALTH_DECREASE.get());
               if (EFFECT != null) {
                  target.m_7292_(
                     new MobEffectInstance(
                        (MobEffect)AquamiraeMobEffects.HEALTH_DECREASE.get(), 20 * (Integer)values.get(0), Math.min(9, EFFECT.m_19564_() + 1), false, false
                     )
                  );
               } else {
                  target.m_7292_(new MobEffectInstance((MobEffect)AquamiraeMobEffects.HEALTH_DECREASE.get(), 20 * (Integer)values.get(0), 0, false, false));
               }

               return true;
            }
         }
      )
      .build(DividerItem.class);

   public DividerItem() {
      super(AquamiraeTiers.DIVIDER, 3, -2.6F, new Properties().m_41486_().m_41497_(Rarity.EPIC).m_41491_(Aquamirae.TAB));
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
      boolean hurt = super.m_7579_(stack, entity, source);
      if (hurt) {
         this.ABILITY.use(stack, source, entity, null);
      }

      return hurt;
   }
}
