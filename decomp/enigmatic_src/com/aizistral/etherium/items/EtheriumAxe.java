package com.aizistral.etherium.items;

import com.aizistral.enigmaticlegacy.api.materials.EnigmaticMaterials;
import com.aizistral.enigmaticlegacy.helpers.AOEMiningHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.etherium.core.EtheriumUtil;
import com.aizistral.etherium.core.IEtheriumTool;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EtheriumAxe extends AxeItem implements IEtheriumTool {
   public Set<Material> effectiveMaterials = Sets.newHashSet();

   public EtheriumAxe() {
      super(EnigmaticMaterials.ETHERIUM, 10.0F, -3.2F, EtheriumUtil.defaultProperties(EtheriumAxe.class).m_41486_());
      this.effectiveMaterials.add(Material.f_76320_);
      this.effectiveMaterials.add(Material.f_76274_);
      this.effectiveMaterials.add(Material.f_76277_);
      this.effectiveMaterials.add(Material.f_76271_);
      this.effectiveMaterials.add(Material.f_76321_);
      this.effectiveMaterials.add(Material.f_76270_);
      this.effectiveMaterials.add(Material.f_76285_);
      this.getConfig().getSorceryMaterial("INFUSED_WOOD").ifPresent(this.effectiveMaterials::add);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (this.getConfig().getAxeMiningVolume() != -1) {
         if (Screen.m_96638_()) {
            ItemLoreHelper.addLocalizedString(
               list,
               "tooltip.enigmaticlegacy.etheriumAxe1",
               ChatFormatting.GOLD,
               this.getConfig().getAxeMiningVolume() + this.getConfig().getAOEBoost(Minecraft.m_91087_().f_91074_)
            );
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            if (!this.getConfig().disableAOEShiftInhibition()) {
               ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumAxe2");
            }

            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumAxe3");
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
         && this.getConfig().getAxeMiningVolume() != -1) {
         Direction face = Direction.UP;
         int volume = this.getConfig().getAxeMiningVolume() + this.getConfig().getAOEBoost((Player)entityLiving);
         AOEMiningHelper.harvestCube(
            world,
            (Player)entityLiving,
            face,
            pos.m_7918_(0, (volume - 1) / 2, 0),
            this.effectiveMaterials,
            volume,
            volume,
            false,
            pos,
            stack,
            (objPos, objState) -> stack.m_41622_(1, entityLiving, p -> p.m_21166_(Mob.m_147233_(stack)))
         );
      }

      return super.m_6813_(stack, world, state, pos, entityLiving);
   }

   public float m_8102_(ItemStack stack, BlockState state) {
      Material material = state.m_60767_();
      return !this.effectiveMaterials.contains(material) ? super.m_8102_(stack, state) : this.f_40980_;
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
}
