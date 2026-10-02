package com.hollingsworth.arsnouveau.common.datagen;

import com.google.common.base.Preconditions;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.common.items.FamiliarScript;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.common.util.RegistryWrapper;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
   public ItemModelGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
      super(generator, modid, existingFileHelper);
   }

   protected void registerModels() {
      for (Supplier<Glyph> i : ArsNouveauAPI.getInstance().getGlyphItemMap().values()) {
         try {
            if (i.get().spellPart.getRegistryName().m_135827_().equals("ars_nouveau")) {
               ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(i.get().spellPart.getRegistryName().m_135815_()))
                     .parent(new UncheckedModelFile("item/generated")))
                  .texture("layer0", this.spellTexture(i.get()));
            }
         } catch (Exception var8) {
            var8.printStackTrace();
            System.out.println("No texture for " + i.get());
         }
      }

      for (RitualTablet i : ArsNouveauAPI.getInstance().getRitualItemMap().values()) {
         try {
            if (i.ritual.getRegistryName().m_135827_().equals("ars_nouveau")) {
               ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(i.ritual.getRegistryName().m_135815_())).parent(new UncheckedModelFile("item/generated")))
                  .texture("layer0", this.itemTexture(i));
            }
         } catch (Exception var7) {
            System.out.println("No texture for " + i);
         }
      }

      for (FamiliarScript i : ArsNouveauAPI.getInstance().getFamiliarScriptMap().values()) {
         try {
            if (i.familiar.getRegistryName().m_135827_().equals("ars_nouveau")) {
               ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(i.familiar.getRegistryName().m_135815_()))
                     .parent(new UncheckedModelFile("item/generated")))
                  .texture("layer0", this.itemTexture(i));
            }
         } catch (Exception var6) {
            System.out.println("No texture for " + i);
         }
      }

      for (PerkItem i : ArsNouveauAPI.getInstance().getPerkItemMap().values()) {
         try {
            if (i.perk.getRegistryName().m_135827_().equals("ars_nouveau")) {
               ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(i.perk.getRegistryName().m_135815_())).parent(new UncheckedModelFile("item/generated")))
                  .texture("layer0", this.itemTexture(i));
            }
         } catch (Exception var5) {
            System.out.println("No texture for " + i);
         }
      }

      ((ItemModelBuilder)this.getBuilder("stripped_blue_archwood_log")).parent(BlockStatesDatagen.getUncheckedModel("stripped_blue_archwood_log"));
      ((ItemModelBuilder)this.getBuilder("stripped_blue_archwood_wood")).parent(BlockStatesDatagen.getUncheckedModel("stripped_blue_archwood_wood"));
      ((ItemModelBuilder)this.getBuilder("stripped_green_archwood_log")).parent(BlockStatesDatagen.getUncheckedModel("stripped_green_archwood_log"));
      ((ItemModelBuilder)this.getBuilder("stripped_green_archwood_wood")).parent(BlockStatesDatagen.getUncheckedModel("stripped_green_archwood_wood"));
      ((ItemModelBuilder)this.getBuilder("stripped_red_archwood_log")).parent(BlockStatesDatagen.getUncheckedModel("stripped_red_archwood_log"));
      ((ItemModelBuilder)this.getBuilder("stripped_red_archwood_wood")).parent(BlockStatesDatagen.getUncheckedModel("stripped_red_archwood_wood"));
      ((ItemModelBuilder)this.getBuilder("stripped_purple_archwood_log")).parent(BlockStatesDatagen.getUncheckedModel("stripped_purple_archwood_log"));
      ((ItemModelBuilder)this.getBuilder("stripped_purple_archwood_wood")).parent(BlockStatesDatagen.getUncheckedModel("stripped_purple_archwood_wood"));
      ((ItemModelBuilder)this.getBuilder("source_gem_block")).parent(BlockStatesDatagen.getUncheckedModel("source_gem_block"));
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(ItemsRegistry.EXPERIENCE_GEM.getRegistryName())).parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", this.itemTexture(ItemsRegistry.EXPERIENCE_GEM.get()));
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(ItemsRegistry.GREATER_EXPERIENCE_GEM.getRegistryName()))
            .parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", this.itemTexture(ItemsRegistry.GREATER_EXPERIENCE_GEM.get()));
      ((ItemModelBuilder)this.getBuilder("red_sbed")).parent(BlockStatesDatagen.getUncheckedModel("red_sbed"));
      ((ItemModelBuilder)this.getBuilder("blue_sbed")).parent(BlockStatesDatagen.getUncheckedModel("blue_sbed"));
      ((ItemModelBuilder)this.getBuilder("green_sbed")).parent(BlockStatesDatagen.getUncheckedModel("green_sbed"));
      ((ItemModelBuilder)this.getBuilder("yellow_sbed")).parent(BlockStatesDatagen.getUncheckedModel("yellow_sbed"));
      ((ItemModelBuilder)this.getBuilder("orange_sbed")).parent(BlockStatesDatagen.getUncheckedModel("orange_sbed"));
      ((ItemModelBuilder)this.getBuilder("purple_sbed")).parent(BlockStatesDatagen.getUncheckedModel("purple_sbed"));
      this.blockAsItem("mendosteen_pod");
      this.blockAsItem("bastion_pod");
      this.blockAsItem("frostaya_pod");
      this.blockAsItem("bombegranate_pod");
      this.itemUnchecked(ItemsRegistry.ALCHEMISTS_CROWN);
      this.stateUnchecked("potion_diffuser");

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         ((ItemModelBuilder)this.getBuilder(s)).parent(BlockStatesDatagen.getUncheckedModel(s));
         ((ItemModelBuilder)this.getBuilder(s + "_slab")).parent(BlockStatesDatagen.getUncheckedModel(s + "_slab"));
         ((ItemModelBuilder)this.getBuilder(s + "_stairs")).parent(BlockStatesDatagen.getUncheckedModel(s + "_stairs"));
      }

      ((ItemModelBuilder)this.getBuilder("void_prism")).parent(BlockStatesDatagen.getUncheckedModel("void_prism"));
      ((ItemModelBuilder)this.getBuilder("falseweave")).parent(BlockStatesDatagen.getUncheckedModel("falseweave"));
      ((ItemModelBuilder)this.getBuilder("mirrorweave")).parent(BlockStatesDatagen.getUncheckedModel("mirrorweave"));
      ((ItemModelBuilder)this.getBuilder("ghostweave")).parent(BlockStatesDatagen.getUncheckedModel("ghostweave"));
      ((ItemModelBuilder)this.getBuilder("magebloom_block")).parent(BlockStatesDatagen.getUncheckedModel("magebloom_block"));
      ((ItemModelBuilder)this.getBuilder("ritual_brazier")).parent(BlockStatesDatagen.getUncheckedModel("ritual_brazier"));
      ((ItemModelBuilder)this.getBuilder("item_detector")).parent(BlockStatesDatagen.getUncheckedModel("item_detector"));
      this.itemUnchecked(ItemsRegistry.WILD_HUNT);
      this.itemUnchecked(ItemsRegistry.SOUND_OF_GLASS);
      this.itemUnchecked(ItemsRegistry.JUMP_RING);
      ((ItemModelBuilder)this.getBuilder("spell_sensor")).parent(BlockStatesDatagen.getUncheckedModel("spell_sensor"));
   }

   public void blockAsItem(String s) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder("ars_nouveau:" + s)).parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", this.itemTexture(s));
   }

   public void blockAsItem(RegistryWrapper<? extends Block> block) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(block.getRegistryName())).parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", this.itemTexture(block.get()));
   }

   public void itemUnchecked(RegistryWrapper<? extends Item> item) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(item.getRegistryName())).parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", this.itemTexture(item.get()));
   }

   public void stateUnchecked(String name) {
      ((ItemModelBuilder)this.getBuilder(name)).parent(BlockStatesDatagen.getUncheckedModel(name));
   }

   public String m_6055_() {
      return "Ars Nouveau Item Models";
   }

   private ResourceLocation registryName(Item item) {
      return (ResourceLocation)Preconditions.checkNotNull(RegistryHelper.getRegistryName(item), "Item %s has a null registry name", item);
   }

   private ResourceLocation registryName(Block item) {
      return (ResourceLocation)Preconditions.checkNotNull(RegistryHelper.getRegistryName(item), "Item %s has a null registry name", item);
   }

   private ResourceLocation itemTexture(String item) {
      return new ResourceLocation("ars_nouveau", "items/" + item);
   }

   private ResourceLocation itemTexture(Item item) {
      ResourceLocation name = this.registryName(item);
      return new ResourceLocation(name.m_135827_(), "items/" + name.m_135815_());
   }

   private ResourceLocation itemTexture(Block item) {
      ResourceLocation name = this.registryName(item);
      return new ResourceLocation(name.m_135827_(), "items/" + name.m_135815_());
   }

   private ResourceLocation spellTexture(Item item) {
      ResourceLocation name = this.registryName(item);
      return new ResourceLocation(name.m_135827_(), "items/" + name.m_135815_().replace("glyph_", ""));
   }
}
