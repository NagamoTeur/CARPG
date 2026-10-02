package software.bernie.ars_nouveau.geckolib3.item;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoArmorRenderer;

public abstract class GeoArmorItem extends ArmorItem {
   public GeoArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
      super(materialIn, slot, builder);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(
         new IClientItemExtensions() {
            @NotNull
            public HumanoidModel<?> getHumanoidArmorModel(
               LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original
            ) {
               return GeoArmorRenderer.getRenderer((Class<? extends ArmorItem>)GeoArmorItem.this.getClass(), livingEntity)
                  .applyEntityStats(original)
                  .setCurrentItem(livingEntity, itemStack, equipmentSlot)
                  .applySlot(equipmentSlot);
            }
         }
      );
   }

   @Nullable
   public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      Class<? extends ArmorItem> clazz = (Class<? extends ArmorItem>)this.getClass();
      GeoArmorRenderer renderer = GeoArmorRenderer.getRenderer(clazz, entity);
      return renderer.getTextureLocation((ArmorItem)stack.m_41720_()).toString();
   }
}
