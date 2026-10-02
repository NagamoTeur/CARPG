package net.xylonity.knightquest.common.event;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.xylonity.knightquest.common.entity.boss.NethermanCloneEntity;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;
import net.xylonity.knightquest.common.entity.entities.BadPatchEntity;
import net.xylonity.knightquest.common.entity.entities.EldBombEntity;
import net.xylonity.knightquest.common.entity.entities.EldKnightEntity;
import net.xylonity.knightquest.common.entity.entities.GhastlingEntity;
import net.xylonity.knightquest.common.entity.entities.GhostyEntity;
import net.xylonity.knightquest.common.entity.entities.GremlinEntity;
import net.xylonity.knightquest.common.entity.entities.LizzyEntity;
import net.xylonity.knightquest.common.entity.entities.RatmanEntity;
import net.xylonity.knightquest.common.entity.entities.SamhainEntity;
import net.xylonity.knightquest.common.entity.entities.SwampmanEntity;
import net.xylonity.knightquest.datagen.KQGlobalLootModifiersProvider;
import net.xylonity.knightquest.registry.KnightQuestEntities;

@EventBusSubscriber(
   modid = "knightquest",
   bus = Bus.MOD
)
public class KQEventRegisters {
   @SubscribeEvent
   public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)KnightQuestEntities.GREMLIN.get(), GremlinEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.ELDBOMB.get(), EldBombEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.ELDKINGHT.get(), EldKnightEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.SWAMPMAN.get(), SwampmanEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.RATMAN.get(), RatmanEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.SAMHAIN.get(), SamhainEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.LIZZY.get(), LizzyEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.BADPATCH.get(), BadPatchEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.SHIELD.get(), GhastlingEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.GHOSTY.get(), GhostyEntity.setAttributes());
      event.put((EntityType)KnightQuestEntities.NETHERMAN.get(), NethermanEntity.setAttributes().m_22265_());
      event.put((EntityType)KnightQuestEntities.NETHERMAN_CLONE.get(), NethermanCloneEntity.setAttributes().m_22265_());
   }

   @SubscribeEvent
   public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
      event.register((EntityType)KnightQuestEntities.BADPATCH.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_, Operation.OR);
      event.register((EntityType)KnightQuestEntities.ELDBOMB.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_, Operation.OR);
      event.register((EntityType)KnightQuestEntities.ELDKINGHT.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_, Operation.OR);
      event.register((EntityType)KnightQuestEntities.GHOSTY.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_, Operation.OR);
      event.register((EntityType)KnightQuestEntities.GREMLIN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_, Operation.OR);
      event.register((EntityType)KnightQuestEntities.LIZZY.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_, Operation.OR);
      event.register((EntityType)KnightQuestEntities.RATMAN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_, Operation.OR);
      event.register((EntityType)KnightQuestEntities.SWAMPMAN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_, Operation.OR);
   }

   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      generator.m_236039_(event.includeServer(), new KQGlobalLootModifiersProvider(generator, "knightquest"));
   }
}
