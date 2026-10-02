package daripher.skilltree.mixin.minecraft;

import daripher.skilltree.potion.PotionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PotionItem.class})
public class PotionItemMixin extends Item {
   private PotionItemMixin() {
      super(null);
   }

   @NotNull
   public Component m_7626_(@NotNull ItemStack stack) {
      if (PotionHelper.isMixture(stack)) {
         return this.getMixtureName(stack);
      } else {
         return PotionHelper.getDurationMultiplier(stack) == 1.0F && PotionHelper.getAmplifierBonus(stack) == 0
            ? super.m_7626_(stack)
            : this.getSuperiorPotionName(stack);
      }
   }

   protected Component getMixtureName(ItemStack stack) {
      String mixtureId = this.getMixtureId(stack);
      MutableComponent translatedName = Component.m_237115_(mixtureId);
      return translatedName.getString().equals(mixtureId) ? Component.m_237115_(this.m_5524_() + ".mixture") : translatedName;
   }

   protected Component getSuperiorPotionName(ItemStack stack) {
      Component potionName = super.m_7626_(stack);
      return Component.m_237110_("potion.superior", new Object[]{potionName});
   }

   @Inject(
      method = {"appendHoverText"},
      at = {@At("TAIL")}
   )
   public void addAdvancedTooltip(ItemStack itemStack, Level level, List<Component> components, TooltipFlag tooltipFlag, CallbackInfo callbackInfo) {
      if (tooltipFlag == Default.ADVANCED) {
         this.addAdvancedTooltip(itemStack, components);
      }
   }

   private void addAdvancedTooltip(ItemStack itemStack, List<Component> components) {
      if (PotionHelper.isMixture(itemStack)) {
         PotionUtils.m_43547_(itemStack)
            .stream()
            .map(MobEffectInstance::m_19544_)
            .<String>map(MobEffect::m_19481_)
            .map(s -> s.replaceAll("effect.", ""))
            .<MutableComponent>map(Component::m_237113_)
            .map(c -> c.m_130940_(ChatFormatting.DARK_GRAY))
            .forEach(components::add);
      }
   }

   protected String getMixtureId(ItemStack itemStack) {
      StringBuilder name = new StringBuilder(this.m_5524_() + ".mixture");
      PotionUtils.m_43547_(itemStack)
         .stream()
         .map(MobEffectInstance::m_19544_)
         .<String>map(MobEffect::m_19481_)
         .map(id -> id.replaceAll("effect.", ""))
         .forEach(id -> name.append(".").append(id));
      return name.toString();
   }
}
