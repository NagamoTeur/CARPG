package shadows.apotheosis.mixin;

import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.ench.table.RealEnchantmentHelper;

@Mixin({EnchantmentHelper.class})
public class EnchantmentHelperMixin {
   @Overwrite
   public static List<EnchantmentInstance> m_44817_(int power, ItemStack stack, boolean allowTreasure) {
      return RealEnchantmentHelper.getAvailableEnchantmentResults(power, stack, allowTreasure);
   }

   @Overwrite
   public static List<EnchantmentInstance> m_220297_(RandomSource pRandom, ItemStack pItemStack, int pLevel, boolean pAllowTreasure) {
      return RealEnchantmentHelper.selectEnchantment(pRandom, pItemStack, pLevel, 15.0F, 0.0F, 0.0F, pAllowTreasure);
   }

   @Inject(
      at = {@At("RETURN")},
      method = {"getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/world/damagesource/DamageSource;)I"},
      cancellable = true
   )
   private static void apoth_getDamageProtection(Iterable<ItemStack> stacks, DamageSource source, CallbackInfoReturnable<Integer> cir) {
      int prot = cir.getReturnValueI();

      for (ItemStack s : stacks) {
         Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(s);

         for (AffixInstance inst : affixes.values()) {
            prot += inst.getDamageProtection(source);
         }
      }

      cir.setReturnValue(prot);
   }

   @Inject(
      at = {@At("RETURN")},
      method = {"getDamageBonus(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/MobType;)F"},
      cancellable = true
   )
   private static void apoth_getDamageBonus(ItemStack stack, MobType type, CallbackInfoReturnable<Float> cir) {
      float dmg = cir.getReturnValueF();
      Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(stack);

      for (AffixInstance inst : affixes.values()) {
         dmg += inst.getDamageBonus(type);
      }

      cir.setReturnValue(dmg);
   }

   @Inject(
      at = {@At("TAIL")},
      method = {"doPostDamageEffects(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)V"}
   )
   private static void apoth_doPostDamageEffects(LivingEntity user, Entity target, CallbackInfo ci) {
      if (user != null) {
         for (ItemStack s : user.m_20158_()) {
            Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(s);

            for (AffixInstance inst : affixes.values()) {
               int old = target.f_19802_;
               target.f_19802_ = 0;
               inst.doPostAttack(user, target);
               target.f_19802_ = old;
            }
         }
      }
   }

   @Inject(
      at = {@At("TAIL")},
      method = {"doPostHurtEffects(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)V"}
   )
   private static void apoth_doPostHurtEffects(LivingEntity user, Entity attacker, CallbackInfo ci) {
      if (user != null) {
         for (ItemStack s : user.m_20158_()) {
            Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(s);

            for (AffixInstance inst : affixes.values()) {
               inst.doPostHurt(user, attacker);
            }
         }
      }
   }

   @Redirect(
      method = {"getTagEnchantmentLevel"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/nbt/ListTag;getCompound(I)Lnet/minecraft/nbt/CompoundTag;",
         remap = true
      ),
      remap = false
   )
   private static CompoundTag apoth_reverseLoopOrder(ListTag tags, int index) {
      return tags.m_128728_(tags.size() - 1 - index);
   }
}
