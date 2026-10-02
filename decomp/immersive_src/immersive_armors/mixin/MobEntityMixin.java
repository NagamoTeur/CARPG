package immersive_armors.mixin;

import immersive_armors.Items;
import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Mob.class})
public class MobEntityMixin {
   private static final Random equipmentRandom = new Random();

   @Inject(
      method = {"getEquipmentForSlot(Lnet/minecraft/entity/EquipmentSlot;I)Lnet/minecraft/item/Item;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void immersiveArmors$injectGetEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel, CallbackInfoReturnable<Item> cir) {
      Map<Integer, ExtendedArmorMaterial> items = new HashMap<Integer, ExtendedArmorMaterial>() {
         {
            this.put(Integer.valueOf(0), Items.WOODEN_ARMOR);
            this.put(Integer.valueOf(1), Items.WARRIOR_ARMOR);
            this.put(Integer.valueOf(2), Items.HEAVY_ARMOR);
            this.put(Integer.valueOf(3), Items.DIVINE_ARMOR);
            this.put(Integer.valueOf(4), Items.PRISMARINE_ARMOR);
         }
      };
      if (items.containsKey(equipmentLevel) && equipmentRandom.nextFloat() < Config.getInstance().mobEntityUseImmersiveArmorChance) {
         String name = items.get(equipmentLevel).m_6082_();

         Supplier<Item> item = switch (equipmentSlot) {
            case HEAD -> (Supplier)Items.items.get(name + "_helmet");
            case CHEST -> (Supplier)Items.items.get(name + "_chestplate");
            case LEGS -> (Supplier)Items.items.get(name + "_leggings");
            case FEET -> (Supplier)Items.items.get(name + "_boots");
            default -> null;
         };
         if (item != null) {
            cir.setReturnValue(item.get());
         }
      }
   }
}
