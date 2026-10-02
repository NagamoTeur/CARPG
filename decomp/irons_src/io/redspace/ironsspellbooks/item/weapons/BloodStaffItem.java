package io.redspace.ironsspellbooks.item.weapons;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.Map;
import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Rarity;

public class BloodStaffItem extends StaffItem {
   public BloodStaffItem() {
      super(
         ItemPropertiesHelper.equipment().m_41487_(1).m_41497_(Rarity.UNCOMMON),
         7.0,
         -3.0,
         Map.of(
            (Attribute)AttributeRegistry.BLOOD_SPELL_POWER.get(),
            new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", 0.15, Operation.MULTIPLY_BASE),
            (Attribute)AttributeRegistry.SPELL_POWER.get(),
            new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", 0.05, Operation.MULTIPLY_BASE),
            (Attribute)AttributeRegistry.SUMMON_DAMAGE.get(),
            new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", 0.1, Operation.MULTIPLY_BASE)
         )
      );
   }
}
