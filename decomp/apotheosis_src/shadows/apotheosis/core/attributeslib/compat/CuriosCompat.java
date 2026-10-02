package shadows.apotheosis.core.attributeslib.compat;

import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import shadows.apotheosis.core.attributeslib.client.ModifierSource;
import shadows.apotheosis.core.attributeslib.client.ModifierSourceType;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosCompat {
   public static void init() {
      ModifierSourceType.register(new ModifierSourceType<Object>() {
         @Override
         public void extract(LivingEntity entity, BiConsumer<AttributeModifier, ModifierSource<?>> map) {
            CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(handler -> {
               Map<String, ICurioStacksHandler> curios = handler.getCurios();

               for (Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                  ICurioStacksHandler stacksHandler = entry.getValue();
                  String identifier = entry.getKey();
                  IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                  for (int i = 0; i < stacksHandler.getSlots(); i++) {
                     SlotContext slotContext = new SlotContext(identifier, entity, i, false, true);
                     ItemStack stack = stackHandler.getStackInSlot(i);
                     if (!stack.m_41619_()) {
                        UUID uuid = UUID.nameUUIDFromBytes((identifier + i).getBytes());
                        Multimap<Attribute, AttributeModifier> modifiers = CuriosApi.getCuriosHelper().getAttributeModifiers(slotContext, uuid, stack);
                        ModifierSource<?> src = new ModifierSource.ItemModifierSource(stack);
                        modifiers.values().forEach(m -> map.accept(m, src));
                     }
                  }
               }
            });
         }

         @Override
         public int getPriority() {
            return 20;
         }
      });
   }

   static {
      if (!ModList.get().isLoaded("curios")) {
         throw new UnsupportedOperationException("This optional compat class requires Curios to be loaded.");
      }
   }
}
