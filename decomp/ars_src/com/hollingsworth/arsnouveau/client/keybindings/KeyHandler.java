package com.hollingsworth.arsnouveau.client.keybindings;

import com.hollingsworth.arsnouveau.api.item.IRadialProvider;
import com.hollingsworth.arsnouveau.api.item.ISpellHotkeyListener;
import com.hollingsworth.arsnouveau.api.util.CuriosUtil;
import com.hollingsworth.arsnouveau.api.util.StackUtil;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.GuiRadialMenu;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketGenericClientMessage;
import com.hollingsworth.arsnouveau.common.network.PacketHotkeyPressed;
import com.hollingsworth.arsnouveau.common.network.PacketQuickCast;
import com.hollingsworth.arsnouveau.common.network.PacketToggleFamiliar;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.client.event.InputEvent.MouseButton.Post;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.IItemHandlerModifiable;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau"
)
public class KeyHandler {
   private static final Minecraft MINECRAFT = Minecraft.m_91087_();
   public static KeyMapping[] CURIO_MAPPINGS = new KeyMapping[]{ModKeyBindings.HEAD_CURIO_HOTKEY};

   public static void checkKeysPressed(int key) {
      checkCurioHotkey(key);
      if (key == ModKeyBindings.FAMILIAR_TOGGLE.getKey().m_84873_() && !ModKeyBindings.FAMILIAR_TOGGLE.m_90862_()) {
         Networking.sendToServer(new PacketToggleFamiliar());
      }

      if (key == ModKeyBindings.OPEN_RADIAL_HUD.getKey().m_84873_()
         && !ModKeyBindings.OPEN_RADIAL_HUD.m_90862_()
         && MINECRAFT.f_91080_ instanceof GuiRadialMenu) {
         MINECRAFT.f_91074_.m_6915_();
      } else {
         checkCasterKeys(key);
      }
   }

   public static void checkCasterKeys(int key) {
      if (key != -1) {
         Player player = MINECRAFT.f_91074_;
         ItemStack radialStack = StackUtil.getHeldRadial(player);
         if (radialStack.m_41720_() instanceof IRadialProvider radialProvider && key == ((IRadialProvider)radialStack.m_41720_()).forKey()) {
            if (MINECRAFT.f_91080_ == null) {
               radialProvider.onRadialKeyPressed(radialStack, player);
               return;
            }

            if (MINECRAFT.f_91080_ instanceof GuiRadialMenu) {
               MINECRAFT.f_91074_.m_6915_();
               return;
            }
         }

         InteractionHand hand = StackUtil.getHeldCasterTool(player);
         if (hand != null) {
            ItemStack stack = player.m_21120_(hand);
            if (!stack.m_41619_()) {
               if (stack.m_41720_() instanceof ISpellHotkeyListener hotkeyListener) {
                  if (key == ModKeyBindings.NEXT_SLOT.getKey().m_84873_()) {
                     sendHotkeyPacket(PacketHotkeyPressed.Key.NEXT);
                  } else if (key == ModKeyBindings.PREVIOUS_SLOT.getKey().m_84873_()) {
                     sendHotkeyPacket(PacketHotkeyPressed.Key.PREVIOUS);
                  } else {
                     if (key == ModKeyBindings.OPEN_BOOK.getKey().m_84873_()) {
                        if (MINECRAFT.f_91080_ instanceof GuiSpellBook && !((GuiSpellBook)MINECRAFT.f_91080_).spell_name.m_93696_()) {
                           MINECRAFT.f_91074_.m_6915_();
                           return;
                        }

                        if (MINECRAFT.f_91080_ == null) {
                           if (stack.m_41720_() instanceof SpellBook) {
                              hotkeyListener.onOpenBookMenuKeyPressed(stack, player);
                           } else {
                              InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
                              ItemStack otherStack = player.m_21120_(otherHand);
                              if (otherStack.m_41720_() instanceof ISpellHotkeyListener offhandListener) {
                                 offhandListener.onOpenBookMenuKeyPressed(otherStack, player);
                              }
                           }
                        }
                     }

                     int slot = ModKeyBindings.usedQuickSlot(key);
                     if (slot != -1) {
                        Networking.INSTANCE.sendToServer(new PacketQuickCast(slot));
                     }
                  }
               }
            }
         }
      }
   }

   public static void checkCurioHotkey(int keyMapping) {
      for (KeyMapping mapping : CURIO_MAPPINGS) {
         if (mapping.getKey().m_84873_() == keyMapping) {
            LazyOptional<IItemHandlerModifiable> stacks = CuriosUtil.getAllWornItems(MINECRAFT.f_91074_);
            if (!stacks.isPresent()) {
               return;
            }

            IItemHandlerModifiable handler = (IItemHandlerModifiable)stacks.orElse(null);

            for (int i = 0; i < handler.getSlots(); i++) {
               ItemStack stack = handler.getStackInSlot(i);
               Item var10 = stack.m_41720_();
               if (var10 instanceof IRadialProvider) {
                  IRadialProvider radialProvider = (IRadialProvider)var10;
                  if (MINECRAFT.f_91080_ instanceof GuiRadialMenu) {
                     MINECRAFT.f_91074_.m_6915_();
                  } else {
                     radialProvider.onRadialKeyPressed(stack, MINECRAFT.f_91074_);
                  }
               }
            }

            return;
         }
      }
   }

   @SubscribeEvent
   public static void mouseEvent(Post event) {
      if (MINECRAFT.f_91074_ != null && event.getAction() == 1) {
         if (MINECRAFT.f_91080_ == null || MINECRAFT.f_91080_ instanceof GuiRadialMenu) {
            checkKeysPressed(event.getButton());
         }
      }
   }

   @SubscribeEvent
   public static void keyEvent(Key event) {
      if (MINECRAFT.f_91074_ != null && event.getAction() == 1) {
         if (MINECRAFT.f_91080_ == null || MINECRAFT.f_91080_ instanceof GuiRadialMenu) {
            checkKeysPressed(event.getKey());
         }

         if (event.getKey() == Minecraft.m_91087_().f_91066_.f_92089_.getKey().m_84873_()
            && Minecraft.m_91087_().f_91074_ != null
            && !Minecraft.m_91087_().f_91074_.m_20096_()
            && CuriosUtil.hasItem(Minecraft.m_91087_().f_91074_, ItemsRegistry.JUMP_RING.get())) {
            Networking.INSTANCE.sendToServer(new PacketGenericClientMessage(PacketGenericClientMessage.Action.JUMP_RING));
         }
      }
   }

   public static void sendHotkeyPacket(PacketHotkeyPressed.Key key) {
      Networking.INSTANCE.sendToServer(new PacketHotkeyPressed(key));
   }
}
