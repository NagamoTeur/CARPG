package com.aqutheseal.celestisynth.manager;

import com.aqutheseal.celestisynth.common.registry.CSBlockEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSFeatures;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSMenuTypes;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.common.registry.CSRecipeTypes;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraftforge.eventbus.api.IEventBus;

public final class CSRegistryManager {
   protected static void registerRegistries(IEventBus modBus) {
      CSEntityTypes.ENTITY_TYPES.register(modBus);
      CSItems.ITEMS.register(modBus);
      CSBlocks.BLOCKS.register(modBus);
      CSParticleTypes.PARTICLE_TYPES.register(modBus);
      CSBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modBus);
      CSSoundEvents.SOUND_EVENTS.register(modBus);
      CSFeatures.FEATURES.register(modBus);
      CSFeatures.CONFIGURED_FEATURES.register(modBus);
      CSFeatures.PLACED_FEATURES.register(modBus);
      CSRecipeTypes.RECIPE_TYPES.register(modBus);
      CSRecipeTypes.RECIPE_SERIALIZERS.register(modBus);
      CSMenuTypes.MENU_TYPES.register(modBus);
      CSVisualTypes.VISUALS.register(modBus);
   }
}
