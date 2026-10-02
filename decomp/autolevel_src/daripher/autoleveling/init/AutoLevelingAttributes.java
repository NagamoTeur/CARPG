package daripher.autoleveling.init;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AutoLevelingAttributes {
   public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "autoleveling");
   public static final RegistryObject<Attribute> PROJECTILE_DAMAGE_BONUS = rangedAttribute("monster", "projectile_damage_bonus", 1.0, 1.0, 1000.0);
   public static final RegistryObject<Attribute> EXPLOSION_DAMAGE_BONUS = rangedAttribute("monster", "explosion_damage_bonus", 1.0, 1.0, 1000.0);

   private static RegistryObject<Attribute> rangedAttribute(String category, String name, double defaultValue, double minValue, double maxValue) {
      return REGISTRY.register(category + "." + name, () -> new RangedAttribute(category + "." + name, defaultValue, minValue, maxValue).m_22084_(true));
   }
}
