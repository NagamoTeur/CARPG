package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.IMultiblockMiningTool;
import com.aizistral.enigmaticlegacy.api.materials.EnigmaticMaterials;
import com.aizistral.enigmaticlegacy.config.EtheriumConfigHandler;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.AOEMiningHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseTool;
import com.aizistral.enigmaticlegacy.packets.clients.PacketFlameParticles;
import com.aizistral.etherium.items.EtheriumAxe;
import com.aizistral.etherium.items.EtheriumPickaxe;
import com.aizistral.etherium.items.EtheriumShovel;
import com.aizistral.omniconfig.Configuration;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class AstralBreaker extends ItemBaseTool implements IMultiblockMiningTool {
   public static Omniconfig.IntParameter miningRadius;
   public static Omniconfig.IntParameter miningDepth;
   public static Omniconfig.BooleanParameter flameParticlesToggle;

   @SubscribeConfig(
      receiveClient = true
   )
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("AstralBreaker");
      if (builder.config.getSidedType() != Configuration.SidedConfigType.CLIENT) {
         miningRadius = builder.comment("The radius of Astral Breaker AOE mining. Set to -1 to disable the feature.")
            .min(-1.0)
            .max(127.0)
            .getInt("MiningRadius", 3);
         miningDepth = builder.comment("The depth of Astral Breaker AOE mining.").max(127.0).getInt("MiningDepth", 1);
      } else {
         flameParticlesToggle = builder.comment("Whether or not flame particles should appear when the Astral Breaker breaks a block")
            .getBoolean("FlameParticlesToggle", true);
      }

      builder.popPrefix();
   }

   public AstralBreaker() {
      super(4.0F, -2.8F, EnigmaticMaterials.ETHERIUM, BlockTags.f_144282_, ItemBaseTool.getDefaultProperties().m_41499_(4000).m_41497_(Rarity.EPIC).m_41486_());
      this.effectiveMaterials.addAll(((EtheriumPickaxe)this.findTool("etherium_pickaxe")).effectiveMaterials);
      this.effectiveMaterials.addAll(((EtheriumAxe)this.findTool("etherium_axe")).effectiveMaterials);
      this.effectiveMaterials.addAll(((EtheriumShovel)this.findTool("etherium_shovel")).effectiveMaterials);
   }

   private Item findTool(String name) {
      return SuperpositionHandler.findItem("enigmaticlegacy", name);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(
            list,
            "tooltip.enigmaticlegacy.astralBreaker1",
            ChatFormatting.GOLD,
            miningRadius.getValue() + EtheriumConfigHandler.instance().getAOEBoost(Minecraft.m_91087_().f_91074_),
            miningDepth.getValue()
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.astralBreaker2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.astralBreaker3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.astralBreaker4");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (!this.areaEffectsAllowed(stack)) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.aoeDisabled");
      }
   }

   public void spawnFlameParticles(Level world, BlockPos pos) {
      EnigmaticLegacy.packetInstance
         .send(
            PacketDistributor.NEAR
               .with(() -> new TargetPoint((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 128.0, world.m_46472_())),
            new PacketFlameParticles((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 18, true)
         );
   }

   public boolean m_6813_(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (!world.f_46443_) {
         this.spawnFlameParticles(world, pos);
      }

      if (entityLiving instanceof Player player
         && this.areaEffectsEnabled(player, stack)
         && this.effectiveMaterials.contains(state.m_60767_())
         && !world.f_46443_
         && miningRadius.getValue() != -1) {
         HitResult trace = AOEMiningHelper.calcRayTrace(world, player, Fluid.ANY);
         if (trace.m_6662_() == Type.BLOCK) {
            BlockHitResult blockTrace = (BlockHitResult)trace;
            Direction face = blockTrace.m_82434_();
            AOEMiningHelper.harvestCube(
               world,
               player,
               face,
               pos,
               this.effectiveMaterials,
               miningRadius.getValue() + EtheriumConfigHandler.instance().getAOEBoost(player),
               miningDepth.getValue(),
               true,
               pos,
               stack,
               (objPos, objState) -> {
                  stack.m_41622_(1, entityLiving, p -> p.m_21166_(Mob.m_147233_(stack)));
                  this.spawnFlameParticles(world, objPos);
               }
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

   @Override
   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return toolAction == ToolActions.AXE_DIG
         || toolAction == ToolActions.PICKAXE_DIG
         || toolAction == ToolActions.SHOVEL_DIG
         || toolAction == ToolActions.HOE_DIG
         || toolAction == ToolActions.SWORD_DIG
         || toolAction == ToolActions.SHEARS_DIG;
   }
}
