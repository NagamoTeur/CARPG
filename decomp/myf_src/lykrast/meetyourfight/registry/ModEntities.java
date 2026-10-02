package lykrast.meetyourfight.registry;

import lykrast.meetyourfight.entity.BellringerEntity;
import lykrast.meetyourfight.entity.DameFortunaEntity;
import lykrast.meetyourfight.entity.ProjectileLineEntity;
import lykrast.meetyourfight.entity.RosalyneEntity;
import lykrast.meetyourfight.entity.RoseSpiritEntity;
import lykrast.meetyourfight.entity.SwampMineEntity;
import lykrast.meetyourfight.entity.SwampjawEntity;
import lykrast.meetyourfight.entity.VelaEntity;
import lykrast.meetyourfight.entity.VelaVortexEntity;
import lykrast.meetyourfight.entity.WaterBoulderEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   bus = Bus.MOD,
   modid = "meetyourfight"
)
public class ModEntities {
   public static final DeferredRegister<EntityType<?>> REG = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "meetyourfight");
   public static RegistryObject<EntityType<BellringerEntity>> BELLRINGER = REG.register(
      "bellringer",
      () -> Builder.m_20704_(BellringerEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.95F)
            .setUpdateInterval(2)
            .setTrackingRange(128)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("")
   );
   public static RegistryObject<EntityType<DameFortunaEntity>> DAME_FORTUNA = REG.register(
      "dame_fortuna",
      () -> Builder.m_20704_(DameFortunaEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 2.325F)
            .setUpdateInterval(2)
            .setTrackingRange(128)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("")
   );
   public static RegistryObject<EntityType<SwampjawEntity>> SWAMPJAW = REG.register(
      "swampjaw",
      () -> Builder.m_20704_(SwampjawEntity::new, MobCategory.MONSTER)
            .m_20699_(2.6F, 1.6F)
            .setUpdateInterval(2)
            .setTrackingRange(128)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("")
   );
   public static RegistryObject<EntityType<RosalyneEntity>> ROSALYNE = REG.register(
      "rosalyne",
      () -> Builder.m_20704_(RosalyneEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.95F)
            .setUpdateInterval(1)
            .setTrackingRange(128)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("")
   );
   public static RegistryObject<EntityType<VelaEntity>> VELA;
   public static RegistryObject<EntityType<RoseSpiritEntity>> ROSE_SPIRIT = REG.register(
      "rose_spirit",
      () -> Builder.m_20704_(RoseSpiritEntity::new, MobCategory.MONSTER)
            .m_20699_(0.75F, 1.3125F)
            .setUpdateInterval(2)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("")
   );
   public static RegistryObject<EntityType<ProjectileLineEntity>> PROJECTILE_LINE = REG.register(
      "projectile_line",
      () -> Builder.m_20704_(ProjectileLineEntity::new, MobCategory.MISC)
            .m_20699_(0.3125F, 0.3125F)
            .setUpdateInterval(1)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("")
   );
   public static RegistryObject<EntityType<SwampMineEntity>> SWAMP_MINE = REG.register(
      "swamp_mine",
      () -> Builder.m_20704_(SwampMineEntity::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .setUpdateInterval(1)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("")
   );
   public static RegistryObject<EntityType<WaterBoulderEntity>> WATER_BOULDER;
   public static RegistryObject<EntityType<VelaVortexEntity>> VELA_VORTEX;

   @SubscribeEvent
   public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)BELLRINGER.get(), BellringerEntity.createAttributes().m_22265_());
      event.put((EntityType)DAME_FORTUNA.get(), DameFortunaEntity.createAttributes().m_22265_());
      event.put((EntityType)SWAMPJAW.get(), SwampjawEntity.createAttributes().m_22265_());
      event.put((EntityType)ROSALYNE.get(), RosalyneEntity.createAttributes().m_22265_());
      event.put((EntityType)ROSE_SPIRIT.get(), RoseSpiritEntity.createAttributes().m_22265_());
   }
}
