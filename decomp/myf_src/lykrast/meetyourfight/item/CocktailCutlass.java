package lykrast.meetyourfight.item;

import java.util.ArrayList;
import java.util.List;
import lykrast.meetyourfight.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

public class CocktailCutlass extends SwordItem {
   private static final Tier TIER = new CustomTier(3, 3168, 8.0F, 3.0F, 14, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.fortunesFavor.get()}));
   private static final List<Triple<MobEffect, Integer, Boolean>> EFFECTS = new ArrayList<>();

   public static void initEffects() {
      EFFECTS.add(Triple.of(MobEffects.f_19596_, 1200, false));
      EFFECTS.add(Triple.of(MobEffects.f_19598_, 1200, false));
      EFFECTS.add(Triple.of(MobEffects.f_19600_, 1200, false));
      EFFECTS.add(Triple.of(MobEffects.f_19605_, 200, false));
      EFFECTS.add(Triple.of(MobEffects.f_19606_, 1200, false));
      EFFECTS.add(Triple.of(MobEffects.f_19607_, 1200, true));
      EFFECTS.add(Triple.of(MobEffects.f_19608_, 1200, true));
      EFFECTS.add(Triple.of(MobEffects.f_19609_, 1200, true));
      EFFECTS.add(Triple.of(MobEffects.f_19617_, 1200, false));
      EFFECTS.add(Triple.of(MobEffects.f_19621_, 1200, false));
   }

   public CocktailCutlass(Properties builderIn) {
      super(TIER, 3, -2.4F, builderIn);
   }

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (target != null && attacker instanceof Player) {
         float luck = ((Player)attacker).m_36336_();
         double chance = 0.2;
         if (luck >= 0.0F) {
            chance = (2.0 + (double)luck) / (10.0 + (double)luck);
         } else {
            chance = 1.0 / (5.0 - (double)luck);
         }

         int effectLevel = -1;
         if (attacker.m_9236_().m_213780_().m_188500_() <= chance) {
            effectLevel = 0;

            for (int i = 0; i < 2; i++) {
               chance *= 0.5;
               if (!(attacker.m_9236_().m_213780_().m_188500_() <= chance)) {
                  break;
               }

               effectLevel++;
            }
         }

         if (effectLevel >= 0) {
            Triple<MobEffect, Integer, Boolean> triple = EFFECTS.get(attacker.m_9236_().m_213780_().m_188503_(EFFECTS.size()));
            int duration = triple.getRight() ? (Integer)triple.getMiddle() * (1 + effectLevel) : (Integer)triple.getMiddle();
            int potency = triple.getRight() ? 0 : effectLevel;
            attacker.m_7292_(new MobEffectInstance((MobEffect)triple.getLeft(), duration, potency, false, false, true));
            attacker.f_19853_.m_5594_(null, attacker.m_20183_(), SoundEvents.f_11911_, SoundSource.PLAYERS, 1.0F, 1.0F);
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_(this.m_5524_() + ".desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.meetyourfight.desc.luck").m_130940_(ChatFormatting.GRAY));
   }
}
