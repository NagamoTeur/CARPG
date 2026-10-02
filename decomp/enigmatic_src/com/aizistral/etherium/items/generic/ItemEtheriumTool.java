package com.aizistral.etherium.items.generic;

import com.aizistral.enigmaticlegacy.api.materials.EnigmaticMaterials;
import com.aizistral.etherium.core.IEtheriumTool;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public abstract class ItemEtheriumTool extends DiggerItem implements IEtheriumTool {
   public Set<Material> effectiveMaterials = Sets.newHashSet();
   public Set<TagKey<Block>> effectiveTags = Sets.newHashSet();

   public ItemEtheriumTool(float attackDamageIn, float attackSpeedIn, TagKey<Block> blocks, Properties builder) {
      super(attackDamageIn, attackSpeedIn, EnigmaticMaterials.ETHERIUM, blocks, builder);
   }

   public boolean isCorrectToolForDrops(ItemStack stack, BlockState blockIn) {
      return super.isCorrectToolForDrops(stack, blockIn) || this.hasAnyTag(blockIn, this.effectiveTags) || this.effectiveMaterials.contains(blockIn.m_60767_());
   }

   protected boolean hasAnyTag(BlockState blockstate, Set<TagKey<Block>> tags) {
      return tags.stream().anyMatch(tag -> this.hasTag(blockstate, (TagKey<Block>)tag));
   }

   protected boolean hasTag(BlockState blockstate, TagKey<Block> tag) {
      return blockstate.m_204336_(tag);
   }

   public float m_8102_(ItemStack stack, BlockState state) {
      Material material = state.m_60767_();
      return !this.effectiveMaterials.contains(material) ? super.m_8102_(stack, state) : this.f_40980_;
   }
}
