package com.aizistral.enigmaticlegacy.items.generic;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.materials.EnigmaticMaterials;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolAction;

public abstract class ItemBaseTool extends DiggerItem {
   public Set<Material> effectiveMaterials = Sets.newHashSet();
   public Set<ToolAction> toolActions = Sets.newHashSet();

   public ItemBaseTool(float attackDamageIn, float attackSpeedIn, Tier tier, TagKey<Block> effectiveBlocksIn, Properties builder) {
      super(attackDamageIn, attackSpeedIn, tier, effectiveBlocksIn, builder);
   }

   public ItemBaseTool(Tier tier) {
      this(4.0F, -2.8F, tier, BlockTags.f_144282_, getDefaultProperties());
   }

   public ItemBaseTool() {
      this(4.0F, -2.8F, EnigmaticMaterials.ETHERIUM, BlockTags.f_144282_, getDefaultProperties());
   }

   public boolean isCorrectToolForDrops(ItemStack stack, BlockState blockIn) {
      return super.isCorrectToolForDrops(stack, blockIn) || this.effectiveMaterials.contains(blockIn.m_60767_());
   }

   public float m_8102_(ItemStack stack, BlockState state) {
      Material material = state.m_60767_();
      return !this.effectiveMaterials.contains(material) ? super.m_8102_(stack, state) : this.f_40980_;
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return super.canPerformAction(stack, toolAction) || this.toolActions.contains(toolAction);
   }

   public static Properties getDefaultProperties() {
      Properties props = new Properties();
      props.m_41491_(EnigmaticLegacy.MAIN_TAB);
      props.m_41487_(1);
      props.m_41497_(Rarity.COMMON);
      return props;
   }
}
