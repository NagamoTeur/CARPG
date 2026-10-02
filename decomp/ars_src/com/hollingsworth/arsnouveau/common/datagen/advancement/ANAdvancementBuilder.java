package com.hollingsworth.arsnouveau.common.datagen.advancement;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.extensions.IForgeAdvancementBuilder;
import net.minecraftforge.registries.ForgeRegistries;

public class ANAdvancementBuilder implements IForgeAdvancementBuilder {
   @Nullable
   private ResourceLocation parentId;
   @Nullable
   private Advancement parent;
   @Nullable
   private DisplayInfo display;
   private AdvancementRewards rewards = AdvancementRewards.f_9978_;
   private Map<String, Criterion> criteria = Maps.newLinkedHashMap();
   @Nullable
   private String[][] requirements;
   private RequirementsStrategy requirementsStrategy = RequirementsStrategy.f_15978_;
   private String modid;
   private String fileKey;

   private ANAdvancementBuilder(
      @Nullable ResourceLocation pParentId,
      @Nullable DisplayInfo pDisplay,
      AdvancementRewards pRewards,
      Map<String, Criterion> pCriteria,
      String[][] pRequirements
   ) {
      this.parentId = pParentId;
      this.display = pDisplay;
      this.rewards = pRewards;
      this.criteria = pCriteria;
      this.requirements = pRequirements;
   }

   private ANAdvancementBuilder(String modid, String fileKey) {
      this.modid = modid;
      this.fileKey = fileKey;
   }

   public static ANAdvancementBuilder builder(String modid, String fileKey) {
      return new ANAdvancementBuilder(modid, fileKey);
   }

   public ANAdvancementBuilder parent(Advancement pParent) {
      this.parent = pParent;
      return this;
   }

   public ANAdvancementBuilder parent(ResourceLocation pParentId) {
      this.parentId = pParentId;
      return this;
   }

   public ANAdvancementBuilder display(
      ItemStack pStack,
      Component pTitle,
      Component pDescription,
      @Nullable ResourceLocation pBackground,
      FrameType pFrame,
      boolean pShowToast,
      boolean pAnnounceToChat,
      boolean pHidden
   ) {
      return this.display(new DisplayInfo(pStack, pTitle, pDescription, pBackground, pFrame, pShowToast, pAnnounceToChat, pHidden));
   }

   public ANAdvancementBuilder display(
      ItemLike pItem,
      Component pTitle,
      Component pDescription,
      @Nullable ResourceLocation pBackground,
      FrameType pFrame,
      boolean pShowToast,
      boolean pAnnounceToChat,
      boolean pHidden
   ) {
      return this.display(new DisplayInfo(new ItemStack(pItem.m_5456_()), pTitle, pDescription, pBackground, pFrame, pShowToast, pAnnounceToChat, pHidden));
   }

   public ANAdvancementBuilder display(DisplayInfo pDisplay) {
      this.display = pDisplay;
      return this;
   }

   public ANAdvancementBuilder display(ItemStack pItem, FrameType pFrame) {
      return this.display(new DisplayInfo(pItem, this.getComponent("title"), this.getComponent("desc"), null, pFrame, true, true, false));
   }

   public ANAdvancementBuilder display(ItemLike pItem, FrameType pFrame) {
      return this.display(new ItemStack(pItem), pFrame);
   }

   public ANAdvancementBuilder display(ItemStack pItem, FrameType pFrame, boolean hidden) {
      return this.display(new DisplayInfo(pItem, this.getComponent("title"), this.getComponent("desc"), null, pFrame, true, true, hidden));
   }

   public ANAdvancementBuilder display(ItemLike pItem, FrameType pFrame, boolean hidden) {
      return this.display(new ItemStack(pItem), pFrame, hidden);
   }

   public ANAdvancementBuilder rewards(Builder pRewardsBuilder) {
      return this.rewards(pRewardsBuilder.m_10004_());
   }

   public ANAdvancementBuilder rewards(AdvancementRewards pRewards) {
      this.rewards = pRewards;
      return this;
   }

   public ANAdvancementBuilder addCriterion(String pKey, CriterionTriggerInstance pCriterion) {
      return this.addCriterion(pKey, new Criterion(pCriterion));
   }

   public ANAdvancementBuilder addCriterion(CriterionTriggerInstance pCriterion) {
      return this.addCriterion(this.fileKey, new Criterion(pCriterion));
   }

   public ANAdvancementBuilder addCriterion(String pKey, Criterion pCriterion) {
      if (this.criteria.containsKey(pKey)) {
         throw new IllegalArgumentException("Duplicate criterion " + pKey);
      } else {
         this.criteria.put(pKey, pCriterion);
         return this;
      }
   }

   public ANAdvancementBuilder requirements(RequirementsStrategy pStrategy) {
      this.requirementsStrategy = pStrategy;
      return this;
   }

   public ANAdvancementBuilder requirements(String[][] pRequirements) {
      this.requirements = pRequirements;
      return this;
   }

   public ANAdvancementBuilder normalItemRequirement(ItemLike item) {
      return this.display(item, FrameType.TASK).requireItem(item);
   }

   public ANAdvancementBuilder requireItem(ItemLike item) {
      return this.addCriterion("has_" + ForgeRegistries.ITEMS.getKey(item.m_5456_()).m_135815_(), TriggerInstance.m_43199_(new ItemLike[]{item}));
   }

   public MutableComponent getComponent(String type) {
      return Component.m_237115_(this.modid + ".adv." + type + "." + this.fileKey);
   }

   public boolean canBuild(Function<ResourceLocation, Advancement> pParentLookup) {
      if (this.parentId == null) {
         return true;
      } else {
         if (this.parent == null) {
            this.parent = pParentLookup.apply(this.parentId);
         }

         return this.parent != null;
      }
   }

   public Advancement build(ResourceLocation pId) {
      if (!this.canBuild(p_138407_ -> null)) {
         throw new IllegalStateException("Tried to build incomplete advancement!");
      } else {
         if (this.requirements == null) {
            this.requirements = this.requirementsStrategy.m_15985_(this.criteria.keySet());
         }

         return new Advancement(pId, this.parent, this.display, this.rewards, this.criteria, this.requirements);
      }
   }

   public Advancement save(Consumer<Advancement> pConsumer, String pId) {
      Advancement advancement = this.build(new ResourceLocation(pId));
      pConsumer.accept(advancement);
      return advancement;
   }

   public Advancement save(Consumer<Advancement> pConsumer) {
      return this.save(pConsumer, new ResourceLocation(this.modid, this.fileKey).toString());
   }

   @Override
   public String toString() {
      return "Task Advancement{parentId="
         + this.parentId
         + ", display="
         + this.display
         + ", rewards="
         + this.rewards
         + ", criteria="
         + this.criteria
         + ", requirements="
         + Arrays.deepToString(this.requirements)
         + "}";
   }

   public Map<String, Criterion> getCriteria() {
      return this.criteria;
   }
}
