package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.api.common.classes.Ability.Cost.Type;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class PoisonedBladeItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "poisoned_blade").cost(Type.COOLDOWN, 10).action((stack, entity, target, context, values) -> {
      if (target == null) {
         return false;
      } else {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 20 * (Integer)values.get(0), 1));
         return true;
      }
   }).var(5, "s").build(PoisonedBladeItem.class);

   public PoisonedBladeItem() {
      super(AquamiraeTiers.POISONED_BLADE, 3, -1.0F, new Properties().m_41491_(Aquamirae.TAB));
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
      boolean hurt = super.m_7579_(stack, entity, source);
      if (hurt) {
         this.ABILITY.use(stack, source, entity, null);
      }

      return hurt;
   }
}
