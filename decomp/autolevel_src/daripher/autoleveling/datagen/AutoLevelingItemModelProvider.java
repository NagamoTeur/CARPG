package daripher.autoleveling.datagen;

import daripher.autoleveling.init.AutoLevelingItems;
import java.util.Objects;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class AutoLevelingItemModelProvider extends ItemModelProvider {
   public AutoLevelingItemModelProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
      super(gen, "autoleveling", existingFileHelper);
   }

   protected void registerModels() {
      this.handheld((Item)AutoLevelingItems.BLACKLIST_TOOL.get());
      this.handheld((Item)AutoLevelingItems.WHITELIST_TOOL.get());
   }

   private void handheld(Item item) {
      ResourceLocation itemId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
      ((ItemModelBuilder)this.withExistingParent(itemId.toString(), this.mcLoc("handheld"))).texture("layer0", this.modLoc("item/" + itemId.m_135815_()));
   }
}
