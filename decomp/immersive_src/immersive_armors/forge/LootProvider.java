package immersive_armors.forge;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import immersive_armors.Items;
import immersive_armors.config.Config;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import org.jetbrains.annotations.NotNull;

public class LootProvider {
   private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(
      Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "immersive_armors"
   );
   private static final RegistryObject<Codec<LootProvider.ImmersiveArmorsLootModifier>> ARMOR_MODIFIER_SERIALIZER = GLM.register(
      "armor_modifier_serializer", LootProvider.ImmersiveArmorsLootModifier.CODEC
   );

   public static void initialize() {
      if (Config.getInstance().lootChance > 0.0F) {
         GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
      }
   }

   private static class DataProvider extends GlobalLootModifierProvider {
      public DataProvider(DataGenerator gen, String modId) {
         super(gen, modId);
      }

      protected void start() {
         for (String s : Items.lootLookup.keySet()) {
            this.add(
               "armor_modifier_serializer_" + s,
               new LootProvider.ImmersiveArmorsLootModifier(new LootItemCondition[]{LootTableIdCondition.builder(new ResourceLocation(s)).m_6409_()})
            );
         }
      }
   }

   @EventBusSubscriber(
      modid = "immersive_armors",
      bus = Bus.MOD
   )
   public static class EventHandlers {
      @SubscribeEvent
      public static void runData(GatherDataEvent event) {
         if (Config.getInstance().lootChance > 0.0F) {
            event.getGenerator().m_236039_(event.includeServer(), new LootProvider.DataProvider(event.getGenerator(), "immersive_armors"));
         }
      }
   }

   private static class ImmersiveArmorsLootModifier extends LootModifier {
      public static final Supplier<Codec<LootProvider.ImmersiveArmorsLootModifier>> CODEC = Suppliers.memoize(
         () -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, LootProvider.ImmersiveArmorsLootModifier::new))
      );

      public ImmersiveArmorsLootModifier(LootItemCondition[] conditionsIn) {
         super(conditionsIn);
      }

      @NotNull
      protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
         ResourceLocation id = context.getQueriedLootTableId();
         if (Items.lootLookup.containsKey(id.toString())) {
            for (Entry<Supplier<Item>, Float> entry : Items.lootLookup.get(id.toString()).entrySet()) {
               if (context.m_78952_().m_213780_().m_188501_() < entry.getValue() * Config.getInstance().lootChance) {
                  generatedLoot.add(new ItemStack((ItemLike)entry.getKey().get()));
               }
            }
         }

         return generatedLoot;
      }

      public Codec<? extends IGlobalLootModifier> codec() {
         return CODEC.get();
      }
   }
}
