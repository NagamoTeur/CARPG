package daripher.skilltree.data.generation;

import daripher.skilltree.init.PSTItems;
import daripher.skilltree.init.PSTTags;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class PSTRecipesProvider extends RecipeProvider {
   public PSTRecipesProvider(DataGenerator dataGenerator) {
      super(dataGenerator);
   }

   protected void m_176531_(@NotNull Consumer<FinishedRecipe> consumer) {
      this.ring(PSTItems.GOLDEN_RING, Items.NUGGETS_GOLD, consumer);
      this.ring(PSTItems.COPPER_RING, PSTTags.NUGGETS_COPPER, consumer);
      this.ring(PSTItems.IRON_RING, Items.NUGGETS_IRON, consumer);
      this.packing(net.minecraft.world.item.Items.f_151052_, PSTTags.NUGGETS_COPPER, consumer);
      this.unpacking(PSTItems.COPPER_NUGGET, Items.INGOTS_COPPER, consumer);
      this.necklace(PSTItems.ASSASSIN_NECKLACE, net.minecraft.world.item.Items.f_42500_, consumer);
      this.necklace(PSTItems.TRAVELER_NECKLACE, net.minecraft.world.item.Items.f_42402_, consumer);
      this.necklace(PSTItems.HEALER_NECKLACE, net.minecraft.world.item.Items.f_42586_, consumer);
      this.necklace(PSTItems.SIMPLE_NECKLACE, consumer);
      this.necklace(PSTItems.SCHOLAR_NECKLACE, net.minecraft.world.item.Items.f_42584_, consumer);
      this.necklace(PSTItems.ARSONIST_NECKLACE, net.minecraft.world.item.Items.f_42613_, consumer);
      this.necklace(PSTItems.FISHERMAN_NECKLACE, net.minecraft.world.item.Items.f_42528_, consumer);
      this.quiver(PSTItems.QUIVER, consumer);
      this.quiver(PSTItems.ARMORED_QUIVER, Items.INGOTS_IRON, consumer);
      this.quiver(PSTItems.DIAMOND_QUIVER, Items.GEMS_DIAMOND, consumer);
      this.quiver(PSTItems.FIERY_QUIVER, net.minecraft.world.item.Items.f_42593_, consumer);
      this.quiver(PSTItems.GILDED_QUIVER, Items.INGOTS_GOLD, consumer);
      this.quiver(PSTItems.HEALING_QUIVER, net.minecraft.world.item.Items.f_42586_, consumer);
      this.quiver(PSTItems.TOXIC_QUIVER, net.minecraft.world.item.Items.f_42592_, consumer);
      this.quiver(PSTItems.SILENT_QUIVER, net.minecraft.world.item.Items.f_42402_, consumer);
      this.quiver(PSTItems.BONE_QUIVER, net.minecraft.world.item.Items.f_42500_, consumer);
   }

   protected void quiver(RegistryObject<Item> result, Item material, Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_((ItemLike)result.get())
         .m_126127_('#', material)
         .m_126127_('l', net.minecraft.world.item.Items.f_42454_)
         .m_126127_('s', net.minecraft.world.item.Items.f_42401_)
         .m_126130_("#ls")
         .m_126130_("#ls")
         .m_126130_("#ls")
         .m_126145_(m_176632_((ItemLike)result.get()))
         .m_126132_(m_176602_(material), m_125977_(material))
         .m_126140_(consumer, this.getRecipeId((Item)result.get()));
   }

   protected void quiver(RegistryObject<Item> result, TagKey<Item> material, Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_((ItemLike)result.get())
         .m_206416_('#', material)
         .m_126127_('l', net.minecraft.world.item.Items.f_42454_)
         .m_126127_('s', net.minecraft.world.item.Items.f_42401_)
         .m_126130_("#ls")
         .m_126130_("#ls")
         .m_126130_("#ls")
         .m_126145_(m_176632_((ItemLike)result.get()))
         .m_126132_(this.getHasName(material), m_206406_(material))
         .m_126140_(consumer, this.getRecipeId((Item)result.get()));
   }

   protected void quiver(RegistryObject<Item> result, Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_((ItemLike)result.get())
         .m_126127_('l', net.minecraft.world.item.Items.f_42454_)
         .m_126127_('s', net.minecraft.world.item.Items.f_42401_)
         .m_126130_("ls")
         .m_126130_("ls")
         .m_126130_("ls")
         .m_126145_(m_176632_((ItemLike)result.get()))
         .m_126132_(m_176602_(net.minecraft.world.item.Items.f_42454_), m_125977_(net.minecraft.world.item.Items.f_42454_))
         .m_126140_(consumer, this.getRecipeId((Item)result.get()));
   }

   protected void necklace(RegistryObject<Item> result, Item material, Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_((ItemLike)result.get())
         .m_126127_('#', material)
         .m_206416_('n', Items.NUGGETS_GOLD)
         .m_126130_("nnn")
         .m_126130_("n n")
         .m_126130_("n#n")
         .m_126145_(m_176632_((ItemLike)result.get()))
         .m_126132_(m_176602_(material), m_125977_(material))
         .m_126140_(consumer, this.getRecipeId((Item)result.get()));
   }

   protected void necklace(RegistryObject<Item> result, Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_((ItemLike)result.get())
         .m_206416_('n', Items.NUGGETS_GOLD)
         .m_126130_("nnn")
         .m_126130_("n n")
         .m_126130_("nnn")
         .m_126145_(m_176632_((ItemLike)result.get()))
         .m_126132_(this.getHasName(Items.NUGGETS_GOLD), m_206406_(Items.NUGGETS_GOLD))
         .m_126140_(consumer, this.getRecipeId((Item)result.get()));
   }

   protected void ring(RegistryObject<Item> result, TagKey<Item> material, Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_((ItemLike)result.get())
         .m_206416_('#', material)
         .m_126130_(" # ")
         .m_126130_("# #")
         .m_126130_(" # ")
         .m_126145_(m_176632_((ItemLike)result.get()))
         .m_126132_(this.getHasName(material), m_206406_(material))
         .m_126140_(consumer, this.getRecipeId((Item)result.get()));
   }

   protected void packing(Item result, TagKey<Item> material, Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_(result)
         .m_206416_('#', material)
         .m_126130_("###")
         .m_126130_("###")
         .m_126130_("###")
         .m_126145_(m_176632_(result))
         .m_126132_(this.getHasName(material), m_206406_(material))
         .m_126140_(consumer, this.getRecipeId(result));
   }

   protected void unpacking(RegistryObject<Item> result, TagKey<Item> material, Consumer<FinishedRecipe> consumer) {
      ShapelessRecipeBuilder.m_126191_((ItemLike)result.get(), 9)
         .m_206419_(material)
         .m_126145_(m_176632_((ItemLike)result.get()))
         .m_126132_(this.getHasName(material), m_206406_(material))
         .m_126140_(consumer, this.getRecipeId((Item)result.get()));
   }

   protected String getHasName(TagKey<Item> material) {
      return "has_" + material.f_203868_().m_135815_().replaceAll("/", "_");
   }

   private ResourceLocation getRecipeId(Item item) {
      ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
      return new ResourceLocation("skilltree", Objects.requireNonNull(id).m_135815_());
   }
}
