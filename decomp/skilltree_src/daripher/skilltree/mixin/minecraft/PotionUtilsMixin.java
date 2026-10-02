package daripher.skilltree.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import daripher.skilltree.potion.PotionHelper;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({PotionUtils.class})
public class PotionUtilsMixin {
   @ModifyReturnValue(
      method = {"getMobEffects"},
      at = {@At("RETURN")}
   )
   private static List<MobEffectInstance> applyEffectsModifiers(List<MobEffectInstance> original, ItemStack stack) {
      float durationMultiplier = PotionHelper.getDurationMultiplier(stack);
      int amplifierBonus = PotionHelper.getAmplifierBonus(stack);
      if (durationMultiplier == 1.0F && amplifierBonus == 0) {
         return original;
      } else {
         original.replaceAll(effect -> {
            int duration = (int)((float)effect.m_19557_() * durationMultiplier);
            int amplifier = effect.m_19564_() + amplifierBonus;
            return new MobEffectInstance(effect.m_19544_(), duration, amplifier);
         });
         return original;
      }
   }
}
