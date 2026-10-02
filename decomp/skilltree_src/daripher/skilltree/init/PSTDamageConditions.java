package daripher.skilltree.init;

import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.skill.bonus.condition.damage.DamageCondition;
import daripher.skilltree.skill.bonus.condition.damage.MagicDamageCondition;
import daripher.skilltree.skill.bonus.condition.damage.MeleeDamageCondition;
import daripher.skilltree.skill.bonus.condition.damage.NoneDamageCondition;
import daripher.skilltree.skill.bonus.condition.damage.ProjectileDamageCondition;
import java.util.List;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PSTDamageConditions {
   public static final ResourceLocation REGISTRY_ID = new ResourceLocation("skilltree", "damage_conditions");
   public static final DeferredRegister<DamageCondition.Serializer> REGISTRY = DeferredRegister.create(REGISTRY_ID, "skilltree");
   public static final RegistryObject<DamageCondition.Serializer> NONE = REGISTRY.register("none", NoneDamageCondition.Serializer::new);
   public static final RegistryObject<DamageCondition.Serializer> PROJECTILE = REGISTRY.register("projectile", ProjectileDamageCondition.Serializer::new);
   public static final RegistryObject<DamageCondition.Serializer> MELEE = REGISTRY.register("melee", MeleeDamageCondition.Serializer::new);
   public static final RegistryObject<DamageCondition.Serializer> MAGIC = REGISTRY.register("magic", MagicDamageCondition.Serializer::new);

   public static List<DamageCondition> conditionsList() {
      return PSTRegistries.DAMAGE_CONDITIONS.get().getValues().stream().map(DamageCondition.Serializer::createDefaultInstance).toList();
   }

   public static String getName(DamageCondition condition) {
      ResourceLocation id = PSTRegistries.DAMAGE_CONDITIONS.get().getKey(condition.getSerializer());
      return TooltipHelper.idToName(Objects.requireNonNull(id).m_135815_());
   }
}
