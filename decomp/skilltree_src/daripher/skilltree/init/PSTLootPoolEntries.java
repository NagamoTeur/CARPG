package daripher.skilltree.init;

import daripher.skilltree.item.gem.loot.GemLootPoolEntry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraftforge.registries.DeferredRegister;

public class PSTLootPoolEntries {
   public static final DeferredRegister<LootPoolEntryType> REGISTRY = DeferredRegister.create(Registry.f_122815_, "skilltree");

   static {
      REGISTRY.register("gem", () -> GemLootPoolEntry.TYPE);
   }
}
