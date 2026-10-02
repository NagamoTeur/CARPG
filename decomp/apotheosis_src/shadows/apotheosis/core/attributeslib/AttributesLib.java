package shadows.apotheosis.core.attributeslib;

import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.core.attributeslib.api.ALAttributes;
import shadows.apotheosis.core.attributeslib.client.AttributesLibClient;
import shadows.apotheosis.core.attributeslib.compat.CuriosCompat;
import shadows.apotheosis.core.attributeslib.impl.AttributeEvents;
import shadows.apotheosis.core.attributeslib.impl.PercentBasedAttribute;
import shadows.apotheosis.core.attributeslib.packet.CritParticleMessage;
import shadows.placebo.network.MessageHelper;
import shadows.placebo.util.RegObjHelper;
import shadows.placebo.util.RegistryEvent.Register;

public class AttributesLib {
   public static final String MODID = "attributeslib";
   public static final RegObjHelper REG_OBJS = new RegObjHelper("apotheosis");
   public static final RegistryObject<SoundEvent> DODGE_SOUND = REG_OBJS.sound("dodge");
   public static final SimpleChannel CHANNEL = ChannelBuilder.named(new ResourceLocation("attributeslib", "attributeslib"))
      .clientAcceptedVersions(s -> true)
      .serverAcceptedVersions(s -> true)
      .networkProtocolVersion(() -> "1.0.0")
      .simpleChannel();

   public AttributesLib() {
      MinecraftForge.EVENT_BUS.register(new AttributeEvents());
      if (FMLEnvironment.dist.isClient()) {
         MinecraftForge.EVENT_BUS.register(new AttributesLibClient());
         FMLJavaModLoadingContext.get().getModEventBus().register(AttributesLibClient.class);
      }

      MessageHelper.registerMessage(CHANNEL, 0, new CritParticleMessage.Provider());
   }

   @SubscribeEvent
   public void attribs(Register<Attribute> e) {
      e.getRegistry()
         .registerAll(
            new Object[]{
               new PercentBasedAttribute("apotheosis:draw_speed", 1.0, 0.0, 4.0).m_22084_(true),
               "draw_speed",
               new PercentBasedAttribute("apotheosis:crit_chance", 0.05, 0.0, 10.0).m_22084_(true),
               "crit_chance",
               new PercentBasedAttribute("apotheosis:crit_damage", 1.5, 1.0, 100.0).m_22084_(true),
               "crit_damage",
               new RangedAttribute("apotheosis:cold_damage", 0.0, 0.0, 1000.0).m_22084_(true),
               "cold_damage",
               new RangedAttribute("apotheosis:fire_damage", 0.0, 0.0, 1000.0).m_22084_(true),
               "fire_damage",
               new PercentBasedAttribute("apotheosis:life_steal", 0.0, 0.0, 10.0).m_22084_(true),
               "life_steal",
               new PercentBasedAttribute("apotheosis:current_hp_damage", 0.0, 0.0, 1.0).m_22084_(true),
               "current_hp_damage",
               new PercentBasedAttribute("apotheosis:overheal", 0.0, 0.0, 10.0).m_22084_(true),
               "overheal",
               new RangedAttribute("apotheosis:ghost_health", 0.0, 0.0, 1000.0).m_22084_(true),
               "ghost_health",
               new PercentBasedAttribute("apotheosis:mining_speed", 1.0, 0.0, 10.0).m_22084_(true),
               "mining_speed",
               new PercentBasedAttribute("apotheosis:arrow_damage", 1.0, 0.0, 10.0).m_22084_(true),
               "arrow_damage",
               new PercentBasedAttribute("apotheosis:arrow_velocity", 1.0, 0.0, 10.0).m_22084_(true),
               "arrow_velocity",
               new PercentBasedAttribute("apotheosis:experience_gained", 1.0, 0.0, 10.0).m_22084_(true),
               "experience_gained",
               new PercentBasedAttribute("apotheosis:healing_received", 1.0, 0.0, 10.0).m_22084_(true),
               "healing_received",
               new RangedAttribute("apotheosis:armor_pierce", 0.0, 0.0, 1000.0).m_22084_(true),
               "armor_pierce",
               new PercentBasedAttribute("apotheosis:armor_shred", 0.0, 0.0, 2.0).m_22084_(true),
               "armor_shred",
               new RangedAttribute("apotheosis:prot_pierce", 0.0, 0.0, 34.0).m_22084_(true),
               "prot_pierce",
               new PercentBasedAttribute("apotheosis:prot_shred", 0.0, 0.0, 1.0).m_22084_(true),
               "prot_shred",
               new PercentBasedAttribute("apotheosis:dodge_chance", 0.0, 0.0, 1.0).m_22084_(true),
               "dodge_chance"
            }
         );
   }

   @SubscribeEvent
   public void particles(Register<ParticleType<?>> e) {
      e.getRegistry().register(new SimpleParticleType(false), "apoth_crit");
   }

   @SubscribeEvent
   public void sounds(Register<SoundEvent> e) {
      e.getRegistry().register(new SoundEvent(Apotheosis.loc("dodge")), "dodge");
   }

   @SubscribeEvent
   public void applyAttribs(EntityAttributeModificationEvent e) {
      e.getTypes()
         .forEach(
            type -> addAll(
                  type,
                  e::add,
                  ALAttributes.DRAW_SPEED,
                  ALAttributes.CRIT_CHANCE,
                  ALAttributes.CRIT_DAMAGE,
                  ALAttributes.COLD_DAMAGE,
                  ALAttributes.FIRE_DAMAGE,
                  ALAttributes.LIFE_STEAL,
                  ALAttributes.CURRENT_HP_DAMAGE,
                  ALAttributes.OVERHEAL,
                  ALAttributes.GHOST_HEALTH,
                  ALAttributes.MINING_SPEED,
                  ALAttributes.ARROW_DAMAGE,
                  ALAttributes.ARROW_VELOCITY,
                  ALAttributes.EXPERIENCE_GAINED,
                  ALAttributes.HEALING_RECEIVED,
                  ALAttributes.ARMOR_PIERCE,
                  ALAttributes.ARMOR_SHRED,
                  ALAttributes.PROT_PIERCE,
                  ALAttributes.PROT_SHRED,
                  ALAttributes.DODGE_CHANCE
               )
         );
      e.add(EntityType.f_20532_, (Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get(), 0.6);
   }

   @SafeVarargs
   private static void addAll(
      EntityType<? extends LivingEntity> type, BiConsumer<EntityType<? extends LivingEntity>, Attribute> add, RegistryObject<Attribute>... attribs
   ) {
      for (RegistryObject<Attribute> a : attribs) {
         add.accept(type, (Attribute)a.get());
      }
   }

   @SubscribeEvent
   public void setup(FMLCommonSetupEvent e) {
      AttributeSupplier playerAttribs = (AttributeSupplier)ForgeHooks.getAttributesView().get(EntityType.f_20532_);

      for (Attribute attr : ForgeRegistries.ATTRIBUTES.getValues()) {
         if (playerAttribs.m_22258_(attr)) {
            attr.m_22084_(true);
         }
      }

      if (ModList.get().isLoaded("curios")) {
         e.enqueueWork(CuriosCompat::init);
      }
   }

   public static TooltipFlag getTooltipFlag() {
      return (TooltipFlag)(FMLEnvironment.dist.isClient() ? AttributesLib.ClientAccess.getTooltipFlag() : Default.NORMAL);
   }

   static class ClientAccess {
      static TooltipFlag getTooltipFlag() {
         return Minecraft.m_91087_().f_91066_.f_92125_ ? Default.ADVANCED : Default.NORMAL;
      }
   }
}
