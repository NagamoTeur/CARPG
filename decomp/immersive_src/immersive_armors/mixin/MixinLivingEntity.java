package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class MixinLivingEntity extends Entity {
   DamageSource source;

   protected MixinLivingEntity(EntityType<? extends LivingEntity> entityType, Level world) {
      super(entityType, world);
   }

   @Shadow
   public abstract ItemStack m_6844_(EquipmentSlot var1);

   private float apply(EquipmentSlot slot, DamageSource source, float amount) {
      ItemStack stack = this.m_6844_(slot);
      if (stack != null && stack.m_41720_() instanceof ExtendedArmorItem armor && this instanceof LivingEntity livingEntity) {
         amount = armor.applyArmorToDamage(livingEntity, source, amount, stack);
      }

      return amount;
   }

   private float applyToAttacker(LivingEntity attacker, EquipmentSlot slot, DamageSource source, float amount) {
      ItemStack stack = attacker.m_6844_(slot);
      if (stack != null && stack.m_41720_() instanceof ExtendedArmorItem armor && this instanceof LivingEntity livingEntity) {
         amount = armor.applyArmorToAttack(livingEntity, source, amount, stack);
      }

      return amount;
   }

   @Inject(
      method = {"damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"},
      at = {@At("HEAD")}
   )
   public void immersiveArmors$injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
      this.source = source;
   }

   @ModifyArg(
      method = {"damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"
      ),
      index = 1
   )
   public float immersiveArmors$modifyArgs(float amount) {
      amount = this.apply(EquipmentSlot.HEAD, this.source, amount);
      amount = this.apply(EquipmentSlot.CHEST, this.source, amount);
      amount = this.apply(EquipmentSlot.LEGS, this.source, amount);
      amount = this.apply(EquipmentSlot.FEET, this.source, amount);
      if (this.source.m_7639_() instanceof LivingEntity livingAttacker) {
         amount = this.applyToAttacker(livingAttacker, EquipmentSlot.HEAD, this.source, amount);
         amount = this.applyToAttacker(livingAttacker, EquipmentSlot.CHEST, this.source, amount);
         amount = this.applyToAttacker(livingAttacker, EquipmentSlot.LEGS, this.source, amount);
         amount = this.applyToAttacker(livingAttacker, EquipmentSlot.FEET, this.source, amount);
      }

      return amount;
   }
}
