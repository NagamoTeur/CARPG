package shadows.apotheosis.core.attributeslib.api;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import java.util.Comparator;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import shadows.apotheosis.util.Comparators;
import shadows.apotheosis.util.ItemAccess;

public class AttributeHelper {
   public static final UUID BASE_ATTACK_DAMAGE = ItemAccess.getBaseAD();
   public static final UUID BASE_ATTACK_SPEED = ItemAccess.getBaseAS();
   public static final UUID BASE_ATTACK_RANGE = UUID.fromString("89689aa7-c577-4d97-a03e-791fde1798d4");

   public static void modify(LivingEntity entity, Attribute attribute, String name, double value, Operation operation) {
      AttributeInstance inst = entity.m_21051_(attribute);
      if (inst != null) {
         inst.m_22125_(new AttributeModifier("placebo:" + name, value, operation));
      }
   }

   public static void addToBase(LivingEntity entity, Attribute attribute, String name, double modifier) {
      modify(entity, attribute, name, modifier, Operation.ADDITION);
   }

   public static void addXTimesNewBase(LivingEntity entity, Attribute attribute, String name, double modifier) {
      modify(entity, attribute, name, modifier, Operation.MULTIPLY_BASE);
   }

   public static void multiplyFinal(LivingEntity entity, Attribute attribute, String name, double modifier) {
      modify(entity, attribute, name, modifier, Operation.MULTIPLY_TOTAL);
   }

   public static Multimap<Attribute, AttributeModifier> sortedMap() {
      return TreeMultimap.create(Comparators.idComparator(Registry.f_122866_), modifierComparator());
   }

   public static Comparator<AttributeModifier> modifierComparator() {
      return Comparators.chained(
         Comparator.comparing(AttributeModifier::m_22217_),
         Comparator.comparing(AttributeModifier::m_22218_),
         Comparator.comparing(AttributeModifier::m_22209_)
      );
   }

   public static MutableComponent list() {
      return Component.m_237113_(" ┇ ").m_130940_(ChatFormatting.GRAY);
   }
}
