package com.aizistral.etherium.items;

import com.aizistral.enigmaticlegacy.config.EtheriumConfigHandler;
import com.aizistral.enigmaticlegacy.helpers.AOEMiningHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.etherium.core.EtheriumUtil;
import com.aizistral.etherium.items.generic.ItemEtheriumTool;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class EtheriumShovel extends ItemEtheriumTool {
   public Set<Material> effectiveMaterials = Sets.newHashSet();

   public EtheriumShovel() {
      super(
         2.5F,
         -3.0F,
         BlockTags.f_144283_,
         EtheriumUtil.defaultProperties(EtheriumShovel.class)
            .m_41499_((int)((double)EtheriumConfigHandler.instance().getToolMaterial().m_6609_() * 1.5))
            .m_41497_(Rarity.RARE)
            .m_41486_()
      );
      this.effectiveMaterials.add(Material.f_76314_);
      this.effectiveMaterials.add(Material.f_76313_);
      this.effectiveMaterials.add(Material.f_76315_);
      this.effectiveMaterials.add(Material.f_76308_);
      this.effectiveMaterials.add(Material.f_76308_);
      this.effectiveMaterials.add(Material.f_76317_);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (this.getConfig().getShovelMiningRadius() != -1) {
         if (Screen.m_96638_()) {
            ItemLoreHelper.addLocalizedString(
               list,
               "tooltip.enigmaticlegacy.etheriumShovel1",
               ChatFormatting.GOLD,
               this.getConfig().getShovelMiningRadius() + this.getConfig().getAOEBoost(Minecraft.m_91087_().f_91074_),
               this.getConfig().getShovelMiningDepth()
            );
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            if (!this.getConfig().disableAOEShiftInhibition()) {
               ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumShovel2");
            }

            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumShovel3");
         } else {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
         }

         if (!this.areaEffectsAllowed(stack)) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.aoeDisabled");
         }
      }
   }

   public boolean m_6813_(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (entityLiving instanceof Player
         && this.areaEffectsEnabled((Player)entityLiving, stack)
         && this.effectiveMaterials.contains(state.m_60767_())
         && !world.f_46443_
         && this.getConfig().getShovelMiningRadius() != -1) {
         HitResult trace = AOEMiningHelper.calcRayTrace(world, (Player)entityLiving, Fluid.ANY);
         if (trace.m_6662_() == Type.BLOCK) {
            BlockHitResult blockTrace = (BlockHitResult)trace;
            Direction face = blockTrace.m_82434_();
            AOEMiningHelper.harvestCube(
               world,
               (Player)entityLiving,
               face,
               pos,
               this.effectiveMaterials,
               this.getConfig().getShovelMiningRadius() + this.getConfig().getAOEBoost((Player)entityLiving),
               this.getConfig().getShovelMiningDepth(),
               false,
               pos,
               stack,
               (objPos, objState) -> stack.m_41622_(1, entityLiving, p -> p.m_21166_(Mob.m_147233_(stack)))
            );
         }
      }

      return super.m_6813_(stack, world, state, pos, entityLiving);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      return context.m_43723_().m_6047_()
         ? this.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_()
         : Items.f_42389_.m_6225_(context);
   }

   public boolean m_8096_(BlockState blockIn) {
      return Items.f_42389_.m_8096_(blockIn);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      player.m_6672_(hand);
      if (player.m_6047_()) {
         this.toggleAreaEffects(player, stack);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return super.m_7203_(world, player, hand);
      }
   }

   @Override
   public float m_8102_(ItemStack stack, BlockState state) {
      Material material = state.m_60767_();
      return !this.effectiveMaterials.contains(material) ? super.m_8102_(stack, state) : this.f_40980_;
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolAction);
   }
}
