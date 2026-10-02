package com.aizistral.etherium.items;

import com.aizistral.enigmaticlegacy.api.materials.EnigmaticMaterials;
import com.aizistral.enigmaticlegacy.helpers.AOEMiningHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.etherium.core.EtheriumUtil;
import com.aizistral.etherium.core.IEtheriumTool;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class EtheriumScythe extends SwordItem implements IEtheriumTool {
   protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(
      ImmutableMap.of(
         Blocks.f_50440_,
         Blocks.f_50093_.m_49966_(),
         Blocks.f_152481_,
         Blocks.f_50093_.m_49966_(),
         Blocks.f_50493_,
         Blocks.f_50093_.m_49966_(),
         Blocks.f_50546_,
         Blocks.f_50493_.m_49966_()
      )
   );
   public Set<Material> effectiveMaterials = Sets.newHashSet();

   public EtheriumScythe() {
      super(EnigmaticMaterials.ETHERIUM, 3, -2.0F, EtheriumUtil.defaultProperties(EtheriumScythe.class).m_41486_());
      this.effectiveMaterials.add(Material.f_76274_);
      this.effectiveMaterials.add(Material.f_76271_);
      this.effectiveMaterials.add(Material.f_76270_);
      this.effectiveMaterials.add(Material.f_76304_);
      this.effectiveMaterials.add(Material.f_76300_);
      this.effectiveMaterials.add(Material.f_76301_);
      this.effectiveMaterials.add(Material.f_76302_);
      this.effectiveMaterials.add(Material.f_76277_);
      this.effectiveMaterials.add(Material.f_76285_);
      this.effectiveMaterials.add(Material.f_164530_);
      this.effectiveMaterials.add(Material.f_76303_);
      this.effectiveMaterials.add(Material.f_164533_);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (this.getConfig().getScytheMiningVolume() != -1) {
         if (Screen.m_96638_()) {
            ItemLoreHelper.addLocalizedString(
               list,
               "tooltip.enigmaticlegacy.etheriumScythe1",
               ChatFormatting.GOLD,
               this.getConfig().getScytheMiningVolume() + this.getConfig().getAOEBoost(Minecraft.m_91087_().f_91074_)
            );
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumScythe2", ChatFormatting.GOLD, this.getConfig().getScytheMiningVolume());
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            if (!this.getConfig().disableAOEShiftInhibition()) {
               ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumScythe3");
            }

            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumScythe4");
         } else {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
         }

         if (!this.areaEffectsAllowed(stack)) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.aoeDisabled");
         }
      }
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
      if (context.m_43723_().m_6047_()) {
         return this.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_();
      } else {
         InteractionResult type = Items.f_42392_.m_6225_(context);
         if (!this.areaEffectsEnabled(context.m_43723_(), context.m_43722_())) {
            return type;
         } else {
            int supRad = (this.getConfig().getScytheMiningVolume() - 1) / 2;
            if (type == InteractionResult.CONSUME) {
               for (int x = -supRad; x <= supRad; x++) {
                  for (int z = -supRad; z <= supRad; z++) {
                     if (!(x == 0 & z == 0)) {
                        Items.f_42392_
                           .m_6225_(
                              new UseOnContext(
                                 context.m_43723_(),
                                 context.m_43724_(),
                                 new BlockHitResult(
                                    context.m_43720_().m_82520_((double)x, 0.0, (double)z),
                                    Direction.UP,
                                    context.m_8083_().m_7918_(x, 0, z),
                                    context.m_43721_()
                                 )
                              )
                           );
                     }
                  }
               }
            }

            return type;
         }
      }
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return toolAction == ToolActions.HOE_TILL || toolAction == ToolActions.HOE_DIG || super.canPerformAction(stack, toolAction);
   }

   public boolean m_6813_(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (entityLiving instanceof Player
         && this.areaEffectsEnabled((Player)entityLiving, stack)
         && this.effectiveMaterials.contains(state.m_60767_())
         && !world.f_46443_
         && this.getConfig().getScytheMiningVolume() != -1) {
         Direction face = Direction.UP;
         int volume = this.getConfig().getScytheMiningVolume() + this.getConfig().getAOEBoost((Player)entityLiving);
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
      return !this.effectiveMaterials.contains(material) ? super.m_8102_(stack, state) : this.m_43314_().m_6624_();
   }
}
