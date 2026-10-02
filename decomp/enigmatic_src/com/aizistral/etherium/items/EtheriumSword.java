package com.aizistral.etherium.items;

import com.aizistral.enigmaticlegacy.api.materials.EnigmaticMaterials;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.objects.CooldownMap;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.etherium.core.EtheriumUtil;
import com.aizistral.etherium.core.IEtheriumTool;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EtheriumSword extends SwordItem implements IEtheriumTool {
   public CooldownMap etheriumSwordCooldowns = new CooldownMap();

   public EtheriumSword() {
      super(EnigmaticMaterials.ETHERIUM, 6, -2.6F, EtheriumUtil.defaultProperties(EtheriumSword.class).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumSword1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumSword2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumSword3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.etheriumSword4", ChatFormatting.GOLD, (float)this.getConfig().getSwordCooldown() / 20.0F
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumSword5");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (!this.areaEffectsAllowed(stack)) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.abilityDisabled");
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (hand == InteractionHand.OFF_HAND || player.m_21206_().m_41780_() == UseAnim.BLOCK) {
         return new InteractionResultHolder(InteractionResult.PASS, player.m_21120_(hand));
      } else if (player.m_6047_()) {
         this.toggleAreaEffects(player, stack);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else if (!player.f_19853_.f_46443_ && !player.m_36335_().m_41519_(this) && this.areaEffectsEnabled(player, stack)) {
         Vector3 look = new Vector3(player.m_20154_());
         Vector3 dir = look.multiply(1.0);
         this.getConfig().knockBack(player, 1.0F, dir.x, dir.z);
         world.m_5594_(null, player.m_20183_(), SoundEvents.f_12382_, SoundSource.PLAYERS, 1.0F, (float)(0.6F + Math.random() * 0.1));
         player.m_36335_().m_41524_(this, this.getConfig().getSwordCooldown());
         player.m_6672_(hand);
         return new InteractionResultHolder(InteractionResult.SUCCESS, player.m_21120_(hand));
      } else {
         player.m_6672_(hand);
         return new InteractionResultHolder(InteractionResult.PASS, player.m_21120_(hand));
      }
   }

   public InteractionResult m_6225_(UseOnContext context) {
      return context.m_43723_().m_6047_() ? this.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_() : super.m_6225_(context);
   }
}
