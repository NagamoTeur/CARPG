package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.IRadialProvider;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.GuiRadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenuSlot;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.hollingsworth.arsnouveau.client.keybindings.ModKeyBindings;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketConsumePotion;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class AlchemistsCrown extends ModItem implements IRadialProvider {
   public AlchemistsCrown(Properties properties) {
      super(properties);
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      tooltip2.add(
         Component.m_237110_("ars_nouveau.tooltip.alchemists_crown", new Object[]{KeyMapping.m_90842_(ModKeyBindings.HEAD_CURIO_HOTKEY.m_90860_()).get()})
      );
   }

   @Override
   public int forKey() {
      return ModKeyBindings.HEAD_CURIO_HOTKEY.getKey().m_84873_();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void onRadialKeyPressed(ItemStack stack, Player player) {
      List<RadialMenuSlot<AlchemistsCrown.SlotData>> slots = new ArrayList<>();

      for (int i = 0; i < player.f_36093_.m_6643_() && slots.size() < 9; i++) {
         ItemStack item = player.f_36093_.m_8020_(i);
         PotionData potionData = new PotionData(item);
         if (!potionData.isEmpty() && !(item.m_41720_() instanceof ArrowItem)) {
            slots.add(new RadialMenuSlot<>(item.m_41786_().getString(), new AlchemistsCrown.SlotData(i, item)));
         }
      }

      if (slots.isEmpty()) {
         PortUtil.sendMessage(Minecraft.m_91087_().f_91074_, Component.m_237115_("ars_nouveau.alchemists_crown.no_flasks"));
      } else {
         Minecraft.m_91087_()
            .m_91152_(
               new GuiRadialMenu<>(
                  new RadialMenu<>(
                     index -> Networking.INSTANCE.sendToServer(new PacketConsumePotion(slots.get(index).primarySlotIcon().slot)),
                     slots,
                     (slotData, posestack, positionx, posy, size, transparent) -> RenderUtils.drawItemAsIcon(
                           slotData.stack, posestack, positionx, posy, size, transparent
                        ),
                     3
                  )
               )
            );
      }
   }

   public static record SlotData(int slot, ItemStack stack) {
      public int getSlot() {
         return this.slot;
      }

      public ItemStack getStack() {
         return this.stack;
      }
   }
}
