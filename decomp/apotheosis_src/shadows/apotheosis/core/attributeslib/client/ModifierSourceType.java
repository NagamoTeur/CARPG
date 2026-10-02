package shadows.apotheosis.core.attributeslib.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.core.attributeslib.api.AttributeHelper;
import shadows.apotheosis.util.Comparators;

public abstract class ModifierSourceType<T> {
   private static final List<ModifierSourceType<?>> SOURCE_TYPES = new ArrayList<>();
   public static final ModifierSourceType<ItemStack> EQUIPMENT = register(new ModifierSourceType<ItemStack>() {
      @Override
      public void extract(LivingEntity entity, BiConsumer<AttributeModifier, ModifierSource<?>> map) {
         for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack item = entity.m_6844_(slot);
            item.m_41638_(slot).values().forEach(modif -> map.accept(modif, new ModifierSource.ItemModifierSource(item)));
         }
      }

      @Override
      public int getPriority() {
         return 0;
      }
   });
   public static final ModifierSourceType<MobEffectInstance> MOB_EFFECT = register(new ModifierSourceType<MobEffectInstance>() {
      @Override
      public void extract(LivingEntity entity, BiConsumer<AttributeModifier, ModifierSource<?>> map) {
         for (MobEffectInstance effectInst : entity.m_21220_()) {
            effectInst.m_19544_().m_19485_().values().forEach(modif -> map.accept(modif, new ModifierSource.EffectModifierSource(effectInst)));
         }
      }

      @Override
      public int getPriority() {
         return 100;
      }
   });

   public static Collection<ModifierSourceType<?>> getTypes() {
      return Collections.unmodifiableCollection(SOURCE_TYPES);
   }

   public static <T extends ModifierSourceType<?>> T register(T type) {
      SOURCE_TYPES.add(type);
      return type;
   }

   public static Comparator<AttributeModifier> compareBySource(Map<UUID, ModifierSource<?>> sources) {
      Comparator<AttributeModifier> comp = Comparators.chained(
         Comparator.comparingInt(a -> sources.get(a.m_22209_()).getType().getPriority()),
         Comparator.comparing(a -> sources.get(a.m_22209_())),
         AttributeHelper.modifierComparator()
      );
      return (a1, a2) -> {
         ModifierSource<?> src1 = sources.get(a1.m_22209_());
         ModifierSource<?> src2 = sources.get(a2.m_22209_());
         if (src1 != null && src2 != null) {
            return comp.compare(a1, a2);
         } else {
            return src1 != null ? -1 : (src2 != null ? 1 : 0);
         }
      };
   }

   public abstract void extract(LivingEntity var1, BiConsumer<AttributeModifier, ModifierSource<?>> var2);

   public abstract int getPriority();
}
