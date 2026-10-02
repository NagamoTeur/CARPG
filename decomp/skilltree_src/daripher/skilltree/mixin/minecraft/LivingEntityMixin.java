package daripher.skilltree.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import daripher.skilltree.entity.EquippedEntity;
import daripher.skilltree.skill.bonus.SkillBonusHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin implements EquippedEntity {
   private final List<ItemStack> equippedItems = new ArrayList<>();

   @Inject(
      method = {"dropAllDeathLoot"},
      at = {@At("HEAD")}
   )
   private void storeEquipmentBeforeDeath(DamageSource damageSource, CallbackInfo callbackInfo) {
      for (EquipmentSlot slot : EquipmentSlot.values()) {
         ItemStack itemInSlot = this.m_6844_(slot);
         if (!itemInSlot.m_41619_()) {
            this.equippedItems.add(itemInSlot);
         }
      }
   }

   @ModifyReturnValue(
      method = {"getJumpPower"},
      at = {@At("RETURN")}
   )
   private float applyJumpHeightBonus(float original) {
      return this instanceof Player player ? original * SkillBonusHandler.getJumpHeightMultiplier(player) : original;
   }

   @Override
   public boolean hasItemEquipped(ItemStack stack) {
      return this.equippedItems.stream().anyMatch(equipped -> ItemStack.m_41728_(stack, equipped));
   }

   @Shadow
   public abstract ItemStack m_6844_(EquipmentSlot var1);
}
