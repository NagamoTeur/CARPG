package daripher.skilltree.data.generation;

import daripher.skilltree.init.PSTItems;
import daripher.skilltree.init.PSTTags;
import daripher.skilltree.item.gem.GemItem;
import daripher.skilltree.item.necklace.NecklaceItem;
import daripher.skilltree.item.quiver.QuiverItem;
import daripher.skilltree.item.ring.RingItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class PSTItemTagsProvider extends ItemTagsProvider {
   public static final ResourceLocation KNIVES = new ResourceLocation("forge", "tools/knives");

   public PSTItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(dataGenerator, blockTagsProvider, "skilltree", existingFileHelper);
   }

   protected void m_6577_() {
      this.add(PSTTags.GEMS, GemItem.class);
      this.add(PSTTags.RINGS, RingItem.class);
      this.add(PSTTags.NECKLACES, NecklaceItem.class);
      this.add(PSTTags.QUIVERS, QuiverItem.class);
      this.add(PSTTags.NUGGETS_COPPER, (Item)PSTItems.COPPER_NUGGET.get());
      this.m_206424_(PSTTags.JEWELRY).addTags(new TagKey[]{PSTTags.RINGS, PSTTags.NECKLACES});
      this.m_206424_(Items.TOOLS).m_176841_(KNIVES);
      this.m_206424_(PSTTags.MELEE_WEAPON).addTags(new TagKey[]{Items.TOOLS_SWORDS, Items.TOOLS_AXES, Items.TOOLS_TRIDENTS});
      this.m_206424_(PSTTags.RANGED_WEAPON).addTags(new TagKey[]{Items.TOOLS_BOWS, Items.TOOLS_CROSSBOWS});
   }

   private void add(TagKey<Item> itemTag, Class<? extends Item> itemClass) {
      ForgeRegistries.ITEMS.getValues().stream().filter(itemClass::isInstance).forEach(this.m_206424_(itemTag)::m_126582_);
   }

   private void add(TagKey<Item> itemTag, Item item) {
      this.m_206424_(itemTag).m_126582_(item);
   }
}
