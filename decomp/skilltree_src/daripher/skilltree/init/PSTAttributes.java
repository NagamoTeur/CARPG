package daripher.skilltree.init;

import java.util.Collection;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "skilltree",
   bus = Bus.MOD
)
public class PSTAttributes {
   public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "skilltree");
   public static final RegistryObject<Attribute> EXP_PER_MINUTE = create("exp_per_minute", 100.0);
   public static final RegistryObject<Attribute> EVASION = create("evasion", 90.0);
   public static final RegistryObject<Attribute> REGENERATION = create("regeneration", 100.0);
   public static final RegistryObject<Attribute> BLOCKING = create("blocking", 90.0);
   public static final RegistryObject<Attribute> STEALTH = create("stealth", 90.0);

   private static RegistryObject<Attribute> create(String name, double maxValue) {
      String descriptionId = "attribute.name.%s.%s".formatted("skilltree", name);
      return REGISTRY.register(name, () -> new RangedAttribute(descriptionId, 0.0, 0.0, maxValue).m_22084_(true));
   }

   @SubscribeEvent
   public static void attachAttributes(EntityAttributeModificationEvent event) {
      REGISTRY.getEntries().stream().<Attribute>map(RegistryObject::get).forEach(attribute -> event.add(EntityType.f_20532_, attribute));
   }

   public static Collection<Attribute> attributeList() {
      return ForgeRegistries.ATTRIBUTES
         .getValues()
         .stream()
         .filter(((AttributeSupplier)ForgeHooks.getAttributesView().get(EntityType.f_20532_))::m_22258_)
         .toList();
   }

   public static String getName(Attribute attribute) {
      ResourceLocation id = ForgeRegistries.ATTRIBUTES.getKey(attribute);
      Objects.requireNonNull(id);
      return id.toString();
   }
}
