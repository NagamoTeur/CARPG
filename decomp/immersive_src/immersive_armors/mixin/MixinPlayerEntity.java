package immersive_armors.mixin;

import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Player.class})
public abstract class MixinPlayerEntity {
   @Shadow
   public abstract ItemStack m_6844_(EquipmentSlot var1);

   @Shadow
   public abstract boolean m_7578_();

   @Inject(
      method = {"isPartVisible"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void immersiveArmors$injectIsPartVisible(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
      if (Config.getInstance().hideSecondLayerUnderArmor) {
         if (!this.m_7578_() || Minecraft.m_91087_().f_91063_.m_109153_().m_90594_()) {
            int flag = modelPart.m_150114_();
            EquipmentSlot slot = null;
            int index = -1;
            switch (flag) {
               case 1:
               case 2:
               case 3:
                  slot = EquipmentSlot.CHEST;
                  index = 1;
                  break;
               case 4:
               case 5:
                  slot = EquipmentSlot.LEGS;
                  index = 2;
                  break;
               case 6:
                  slot = EquipmentSlot.HEAD;
                  index = 0;
            }

            if (index >= 0) {
               ItemStack stack = this.m_6844_(slot);
               if (stack.m_41720_() instanceof ExtendedArmorItem armorItem && armorItem.getMaterial().shouldHideSecondLayer()[index]) {
                  cir.setReturnValue(false);
               }
            }
         }
      }
   }
}
