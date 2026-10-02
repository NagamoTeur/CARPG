package com.hollingsworth.arsnouveau.common.datagen.advancement;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class ANAdvancements implements Consumer<Consumer<Advancement>> {
   Consumer<Advancement> advCon;

   public void accept(Consumer<Advancement> con) {
      this.advCon = con;
      Advancement root = this.builder("ars_nouveau")
         .display(
            ItemsRegistry.WORN_NOTEBOOK,
            Component.m_237115_("ars_nouveau.advancement.title.root"),
            Component.m_237115_("ars_nouveau.advancement.desc.root"),
            new ResourceLocation("ars_nouveau:textures/gui/advancements/backgrounds/sourcestone.png"),
            FrameType.TASK,
            false,
            false,
            false
         )
         .addCriterion("ars_nouveau:worn_notebook", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .save(con, "ars_nouveau:root");
      Advancement poofMob = this.builder("poof_mob")
         .display(Items.f_42587_, FrameType.TASK)
         .addCriterion(new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.POOF_MOB.m_7295_(), Composite.f_36667_))
         .parent(root)
         .save(con);
      this.saveBasicItem(ItemsRegistry.WIXIE_CHARM, poofMob);
      this.saveBasicItem(ItemsRegistry.WHIRLISPRIG_CHARM, poofMob);
      this.saveBasicItem(ItemsRegistry.DRYGMY_CHARM, poofMob);
      Advancement starbyCharm = this.builder("starby_charm").normalItemRequirement(ItemsRegistry.STARBUNCLE_CHARM).parent(poofMob).save(con);
      this.saveBasicItem(ItemsRegistry.STARBUNCLE_SHADES, starbyCharm);
      this.saveBasicItem(ItemsRegistry.WIXIE_HAT, starbyCharm);
      Advancement novice = this.saveBasicItem(ItemsRegistry.NOVICE_SPELLBOOK, root);
      Advancement mages = this.saveBasicItem(ItemsRegistry.APPRENTICE_SPELLBOOK, novice);
      Advancement tribue = this.saveBasicItem(ItemsRegistry.WILDEN_TRIBUTE, mages);
      this.builder("wilden_explosion")
         .display(ItemsRegistry.WILDEN_TRIBUTE, FrameType.CHALLENGE, true)
         .addCriterion(
            new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.CHIMERA_EXPLOSION.m_7295_(), Composite.f_36667_)
         )
         .parent(tribue)
         .save(con);
      this.saveBasicItem(ItemsRegistry.ARCHMAGE_SPELLBOOK, tribue);
      this.saveBasicItem(ItemsRegistry.SUMMONING_FOCUS, tribue);
      this.saveBasicItem(ItemsRegistry.SHAPERS_FOCUS, novice);
      this.builder("eat_bombegranate")
         .display(BlockRegistry.BOMBEGRANTE_POD, FrameType.TASK, true)
         .addCriterion(net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.m_23703_(BlockRegistry.BOMBEGRANTE_POD))
         .parent(root)
         .save(con);
      Advancement rituals = this.saveBasicItem(BlockRegistry.RITUAL_BLOCK, root);
      this.saveBasicItem(ItemsRegistry.AMETHYST_GOLEM_CHARM, rituals);
      this.builder("familiar")
         .display((ItemLike)ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation("ars_nouveau", RitualLib.BINDING)), FrameType.GOAL)
         .addCriterion(new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.FAMILIAR.m_7295_(), Composite.f_36667_))
         .parent(rituals)
         .save(con);
      Advancement jars = this.saveBasicItem(BlockRegistry.MOB_JAR, rituals);
      this.builder("shrunk_starbuncle")
         .display(ItemsRegistry.STARBUNCLE_CHARM, FrameType.CHALLENGE, true)
         .addCriterion(new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.SHRUNK_STARBY.m_7295_(), Composite.f_36667_))
         .parent(jars)
         .save(con);
      this.builder("catch_lightning")
         .display(Items.f_151041_, FrameType.CHALLENGE, true)
         .addCriterion(
            new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.CAUGHT_LIGHTNING.m_7295_(), Composite.f_36667_)
         )
         .parent(jars)
         .save(con);
      this.builder("time_in_a_bottle")
         .display(Items.f_42524_, FrameType.CHALLENGE, true)
         .addCriterion(new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.TIME_IN_BOTTLE.m_7295_(), Composite.f_36667_))
         .parent(jars)
         .save(con);
      Advancement chamber = this.saveBasicItem(BlockRegistry.IMBUEMENT_BLOCK, root);
      Advancement jar = this.saveBasicItem(BlockRegistry.SOURCE_JAR, chamber);
      Advancement apparatus = this.saveBasicItem(BlockRegistry.ENCHANTING_APP_BLOCK, chamber);
      this.saveBasicItem(BlockRegistry.SCRYERS_OCULUS, apparatus);
      Advancement potionJar = this.saveBasicItem(BlockRegistry.POTION_JAR, apparatus);
      this.saveBasicItem(BlockRegistry.POTION_MELDER, potionJar);
      this.saveBasicItem(BlockRegistry.POTION_DIFFUSER, potionJar);
      this.saveBasicItem(ItemsRegistry.POTION_FLASK, potionJar);
      Advancement turret = this.saveBasicItem(BlockRegistry.BASIC_SPELL_TURRET, apparatus);
      Advancement prism = this.saveBasicItem(BlockRegistry.SPELL_PRISM, turret);
      this.builder("prismatic")
         .display(BlockRegistry.SPELL_PRISM, FrameType.CHALLENGE, true)
         .addCriterion(new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.PRISMATIC.m_7295_(), Composite.f_36667_))
         .parent(prism)
         .save(con);
      Advancement magebloom = this.saveBasicItem(BlockRegistry.MAGE_BLOOM_CROP, apparatus);
      Advancement warpScroll = this.saveBasicItem(ItemsRegistry.WARP_SCROLL, magebloom);
      this.builder("create_portal")
         .display(BlockRegistry.CREATIVE_SOURCE_JAR, FrameType.CHALLENGE, false)
         .addCriterion(new net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance(ANCriteriaTriggers.CREATE_PORTAL.m_7295_(), Composite.f_36667_))
         .parent(warpScroll)
         .save(con);
      Advancement alteration = this.saveBasicItem(BlockRegistry.ALTERATION_TABLE, magebloom);
      this.builder("ritual_gravity")
         .display((ItemLike)ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation("ars_nouveau", RitualLib.GRAVITY)), FrameType.GOAL)
         .addCriterion(
            "gravity_effect",
            net.minecraft.advancements.critereon.EffectsChangedTrigger.TriggerInstance.m_26780_(
               MobEffectsPredicate.m_56552_().m_56553_((MobEffect)ModPotions.GRAVITY_EFFECT.get())
            )
         )
         .parent(rituals)
         .save(con);
   }

   public ANAdvancementBuilder buildBasicItem(ItemLike item, Advancement parent) {
      return this.builder(ForgeRegistries.ITEMS.getKey(item.m_5456_()).m_135815_()).normalItemRequirement(item).parent(parent);
   }

   public Advancement saveBasicItem(ItemLike item, Advancement parent) {
      return this.buildBasicItem(item, parent).save(this.advCon);
   }

   public ANAdvancementBuilder builder(String key) {
      return ANAdvancementBuilder.builder("ars_nouveau", key);
   }
}
