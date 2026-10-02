package com.aqutheseal.celestisynth.datagen.providers;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class CSAdvancementProvider extends AdvancementProvider {
   public CSAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
      super(generatorIn, fileHelperIn);
   }

   protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
      Advancement rootObtainedCore = Builder.m_138353_()
         .m_138371_(
            (ItemLike)CSItems.CELESTIAL_CORE.get(),
            Component.m_237115_("advancements.celestisynth.obtained_core.title"),
            Component.m_237115_("advancements.celestisynth.obtained_core.description"),
            Celestisynth.prefix("textures/block/zephyr_deposit.png"),
            FrameType.TASK,
            false,
            true,
            false
         )
         .m_138386_("obtained_core", TriggerInstance.m_43199_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE.get()}))
         .m_138389_(consumer, "celestisynth:root");
      Advancement getSupernalIngot = this.obtainItem(consumer, rootObtainedCore, CSItems.SUPERNAL_NETHERITE_INGOT, FrameType.TASK, false);
      Advancement obtainedCelestialIngot = this.obtainItem(consumer, getSupernalIngot, CSItems.CELESTIAL_NETHERITE_INGOT, FrameType.TASK, false);
      Advancement placeCelestialTable = this.createAdvancement(
         consumer,
         obtainedCelestialIngot,
         "place_celestial_table",
         FrameType.CHALLENGE,
         true,
         CSBlocks.CELESTIAL_CRAFTING_TABLE,
         net.minecraft.advancements.critereon.PlacedBlockTrigger.TriggerInstance::m_59505_
      );
      CSItems.ITEMS
         .getEntries()
         .stream()
         .filter(weapon -> weapon.get() instanceof CSWeapon)
         .forEach(item -> this.obtainItem(consumer, placeCelestialTable, (RegistryObject<? extends Item>)item, FrameType.GOAL, true));
   }

   private Advancement obtainItem(
      Consumer<Advancement> dataSaver, Advancement parent, RegistryObject<? extends Item> require, FrameType frameType, boolean toChat
   ) {
      return this.createAdvancement(
         dataSaver, parent, "obtain_" + require.getId().m_135815_(), frameType, toChat, require, xva$0 -> TriggerInstance.m_43199_(new ItemLike[]{xva$0})
      );
   }

   private <ToCheck extends ItemLike, Trigger extends CriterionTriggerInstance> Advancement createAdvancement(
      Consumer<Advancement> consumer,
      Advancement parent,
      String saveName,
      FrameType frame,
      boolean toChat,
      RegistryObject<ToCheck> toCheck,
      Function<ToCheck, Trigger> triggerFactory
   ) {
      return this.createAdvancement(consumer, parent, saveName, (ItemLike)toCheck.get(), frame, toChat, (ItemLike)toCheck.get(), triggerFactory);
   }

   private <ToCheck, Trigger extends CriterionTriggerInstance> Advancement createAdvancement(
      Consumer<Advancement> consumer,
      Advancement parent,
      String saveName,
      ItemLike displayIcon,
      FrameType frame,
      boolean toChat,
      ToCheck toCheck,
      Function<ToCheck, Trigger> triggerFactory
   ) {
      ResourceLocation fileName = Celestisynth.prefix(saveName);
      String languageKey = fileName.m_214296_("advancement");
      return Builder.m_138353_()
         .m_138398_(parent)
         .m_138371_(
            displayIcon, Component.m_237115_(languageKey + ".title"), Component.m_237115_(languageKey + ".description"), null, frame, true, toChat, false
         )
         .m_138386_("criteria", triggerFactory.apply(toCheck))
         .save(consumer, fileName, super.fileHelper);
   }
}
