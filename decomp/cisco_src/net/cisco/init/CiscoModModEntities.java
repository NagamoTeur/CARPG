package net.cisco.init;

import net.cisco.entity.AfterImageEntity;
import net.cisco.entity.CiscoEntity;
import net.cisco.entity.DescendedCiscoEntity;
import net.cisco.entity.DragonSeekerMissileEntity;
import net.cisco.entity.FellShieldEntity;
import net.cisco.entity.FellkingbossEntity;
import net.cisco.entity.LegionnaireJotunnEntity;
import net.cisco.entity.LegionnaireKingsguardEntity;
import net.cisco.entity.SupremeNightfallAegisModeEntity;
import net.cisco.entity.VengefulAfterImageEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class CiscoModModEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "cisco_mod");
   public static final RegistryObject<EntityType<CiscoEntity>> CISCO = register(
      "cisco",
      Builder.m_20704_(CiscoEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(CiscoEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<AfterImageEntity>> AFTER_IMAGE = register(
      "after_image",
      Builder.m_20704_(AfterImageEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(AfterImageEntity::new)
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<FellkingbossEntity>> FELLKINGBOSS = register(
      "fellkingboss",
      Builder.m_20704_(FellkingbossEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(FellkingbossEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<LegionnaireKingsguardEntity>> LEGIONNAIRE_KINGSGUARD = register(
      "legionnaire_kingsguard",
      Builder.m_20704_(LegionnaireKingsguardEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(LegionnaireKingsguardEntity::new)
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<FellShieldEntity>> FELL_SHIELD = register(
      "fell_shield",
      Builder.m_20704_(FellShieldEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(FellShieldEntity::new)
         .m_20719_()
         .m_20699_(0.05F, 0.05F)
   );
   public static final RegistryObject<EntityType<LegionnaireJotunnEntity>> LEGIONNAIRE_JOTUNN = register(
      "legionnaire_jotunn",
      Builder.m_20704_(LegionnaireJotunnEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(LegionnaireJotunnEntity::new)
         .m_20699_(1.0F, 2.5F)
   );
   public static final RegistryObject<EntityType<DragonSeekerMissileEntity>> DRAGON_SEEKER_MISSILE = register(
      "dragon_seeker_missile",
      Builder.m_20704_(DragonSeekerMissileEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(DragonSeekerMissileEntity::new)
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<DescendedCiscoEntity>> DESCENDED_CISCO = register(
      "descended_cisco",
      Builder.m_20704_(DescendedCiscoEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(DescendedCiscoEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<VengefulAfterImageEntity>> VENGEFUL_AFTER_IMAGE = register(
      "vengeful_after_image",
      Builder.m_20704_(VengefulAfterImageEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(VengefulAfterImageEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<SupremeNightfallAegisModeEntity>> SUPREME_NIGHTFALL_AEGIS_MODE = register(
      "supreme_nightfall_aegis_mode",
      Builder.m_20704_(SupremeNightfallAegisModeEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(SupremeNightfallAegisModeEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 1.8F)
   );

   private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, Builder<T> entityTypeBuilder) {
      return REGISTRY.register(registryname, () -> entityTypeBuilder.m_20712_(registryname));
   }

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         CiscoEntity.init();
         AfterImageEntity.init();
         FellkingbossEntity.init();
         LegionnaireKingsguardEntity.init();
         FellShieldEntity.init();
         LegionnaireJotunnEntity.init();
         DragonSeekerMissileEntity.init();
         DescendedCiscoEntity.init();
         VengefulAfterImageEntity.init();
         SupremeNightfallAegisModeEntity.init();
      });
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)CISCO.get(), CiscoEntity.createAttributes().m_22265_());
      event.put((EntityType)AFTER_IMAGE.get(), AfterImageEntity.createAttributes().m_22265_());
      event.put((EntityType)FELLKINGBOSS.get(), FellkingbossEntity.createAttributes().m_22265_());
      event.put((EntityType)LEGIONNAIRE_KINGSGUARD.get(), LegionnaireKingsguardEntity.createAttributes().m_22265_());
      event.put((EntityType)FELL_SHIELD.get(), FellShieldEntity.createAttributes().m_22265_());
      event.put((EntityType)LEGIONNAIRE_JOTUNN.get(), LegionnaireJotunnEntity.createAttributes().m_22265_());
      event.put((EntityType)DRAGON_SEEKER_MISSILE.get(), DragonSeekerMissileEntity.createAttributes().m_22265_());
      event.put((EntityType)DESCENDED_CISCO.get(), DescendedCiscoEntity.createAttributes().m_22265_());
      event.put((EntityType)VENGEFUL_AFTER_IMAGE.get(), VengefulAfterImageEntity.createAttributes().m_22265_());
      event.put((EntityType)SUPREME_NIGHTFALL_AEGIS_MODE.get(), SupremeNightfallAegisModeEntity.createAttributes().m_22265_());
   }
}
