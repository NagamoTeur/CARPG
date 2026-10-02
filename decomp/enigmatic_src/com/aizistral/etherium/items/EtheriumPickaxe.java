package com.aizistral.etherium.items;

import com.aizistral.enigmaticlegacy.config.EtheriumConfigHandler;
import com.aizistral.enigmaticlegacy.helpers.AOEMiningHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.etherium.core.EtheriumUtil;
import com.aizistral.etherium.items.generic.ItemEtheriumTool;
import java.util.List;
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

public class EtheriumPickaxe extends ItemEtheriumTool {
   public EtheriumPickaxe() {
      super(
         1.0F,
         -2.8F,
         BlockTags.f_144282_,
         EtheriumUtil.defaultProperties(EtheriumPickaxe.class)
            .m_41499_((int)((double)EtheriumConfigHandler.instance().getToolMaterial().m_6609_() * 1.5))
            .m_41486_()
      );
      this.effectiveMaterials.add(Material.f_76279_);
      this.effectiveMaterials.add(Material.f_76278_);
      this.effectiveMaterials.add(Material.f_76281_);
      this.effectiveMaterials.add(Material.f_76275_);
      this.effectiveMaterials.add(Material.f_76316_);
      this.effectiveMaterials.add(Material.f_76276_);
      this.effectiveMaterials.add(Material.f_76319_);
      this.effectiveMaterials.add(Material.f_164531_);
      this.getConfig().getSorceryMaterial("MARBLE").ifPresent(this.effectiveMaterials::add);
      this.getConfig().getSorceryMaterial("BLACK_MARBLE").ifPresent(this.effectiveMaterials::add);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (this.getConfig().getPickaxeMiningRadius() != -1) {
         if (Screen.m_96638_()) {
            ItemLoreHelper.addLocalizedString(
               list,
               "tooltip.enigmaticlegacy.etheriumPickaxe1",
               ChatFormatting.GOLD,
               this.getConfig().getPickaxeMiningRadius() + this.getConfig().getAOEBoost(Minecraft.m_91087_().f_91074_),
               this.getConfig().getPickaxeMiningDepth()
            );
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            if (!this.getConfig().disableAOEShiftInhibition()) {
               ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumPickaxe2");
            }

            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumPickaxe3");
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
         && this.getConfig().getPickaxeMiningRadius() != -1) {
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
               this.getConfig().getPickaxeMiningRadius() + this.getConfig().getAOEBoost((Player)entityLiving),
               this.getConfig().getPickaxeMiningDepth(),
               true,
               pos,
               stack,
               (objPos, objState) -> stack.m_41622_(1, entityLiving, p -> p.m_21166_(Mob.m_147233_(stack)))
            );
         }
      }

      return super.m_6813_(stack, world, state, pos, entityLiving);
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

   public InteractionResult m_6225_(UseOnContext context) {
      return context.m_43723_().m_6047_() ? this.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_() : super.m_6225_(context);
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction);
   }
}
