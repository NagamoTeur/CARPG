package com.aizistral.enigmaticlegacy.objects;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class SpecialLootModifier extends LootModifier {
   public static final Supplier<Codec<SpecialLootModifier>> CODEC = Suppliers.memoize(
      () -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, SpecialLootModifier::new))
   );

   protected SpecialLootModifier(LootItemCondition[] conditions) {
      super(conditions);
   }

   @Nonnull
   @NotNull
   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      ServerLevel level = context.m_78952_();
      Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
      Vec3 origin = (Vec3)context.m_78953_(LootContextParams.f_81460_);
      if (entity instanceof ServerPlayer player) {
         if (this.isVanillaChest(context)) {
            if (OmniconfigHandler.isItemEnabled(EnigmaticItems.ENIGMATIC_EYE) && !SuperpositionHandler.hasPersistentTag(player, "LootedArchitectEye")) {
               SuperpositionHandler.setPersistentBoolean(player, "LootedArchitectEye", true);
               generatedLoot.add(new ItemStack(EnigmaticItems.ENIGMATIC_EYE, 1));
            }

            if (SuperpositionHandler.hasPersistentTag(player, "LootedIchorBottle")) {
               generatedLoot.removeIf(stack -> stack.m_150930_(EnigmaticItems.ICHOR_BOTTLE));
            } else if (generatedLoot.stream().anyMatch(stack -> stack.m_150930_(EnigmaticItems.ICHOR_BOTTLE))) {
               SuperpositionHandler.setPersistentBoolean(player, "LootedIchorBottle", true);
            }
         }

         if (OmniconfigHandler.isItemEnabled(EnigmaticItems.ASTRAL_FRUIT)
            && "minecraft:chests/end_city_treasure".equals(String.valueOf(context.getQueriedLootTableId()))
            && !SuperpositionHandler.hasPersistentTag(player, "LootedFirstEndCityChest")) {
            SuperpositionHandler.setPersistentBoolean(player, "LootedFirstEndCityChest", true);
            if (SuperpositionHandler.isTheCursedOne(player)) {
               generatedLoot.add(new ItemStack(EnigmaticItems.ASTRAL_FRUIT, 1));
            }
         }
      }

      return generatedLoot;
   }

   private boolean isVanillaChest(LootContext context) {
      return String.valueOf(context.getQueriedLootTableId()).startsWith("minecraft:chests/");
   }

   public Codec<SpecialLootModifier> codec() {
      return CODEC.get();
   }
}
