package com.aizistral.enigmaticlegacy.mixin;

import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderTooltipEvent.GatherComponents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {ForgeHooksClient.class},
   remap = false
)
public class MixinForgeHooksClient {
   @Inject(
      method = {"gatherTooltipComponents(Lnet/minecraft/world/item/ItemStack;Ljava/util/List;Ljava/util/Optional;IIILnet/minecraft/client/gui/Font;Lnet/minecraft/client/gui/Font;)Ljava/util/List;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void onGatherTooltipComponents(
      ItemStack stack,
      List<? extends FormattedText> textElements,
      Optional<TooltipComponent> itemComponent,
      int mouseX,
      int screenWidth,
      int screenHeight,
      Font forcedFont,
      Font fallbackFont,
      CallbackInfoReturnable<List<ClientTooltipComponent>> info
   ) {
      if (stack != null && ForgeRegistries.ITEMS.getKey(stack.m_41720_()).m_135827_().equals("enigmaticlegacy")) {
         Font font = ForgeHooksClient.getTooltipFont(forcedFont, stack, fallbackFont);
         List<Either<FormattedText, TooltipComponent>> elements = textElements.stream()
            .<Either<FormattedText, TooltipComponent>>map(Either::left)
            .collect(Collectors.toCollection(ArrayList::new));
         itemComponent.ifPresent(c -> elements.add(1, Either.right(c)));
         GatherComponents event = new GatherComponents(stack, screenWidth, screenHeight, elements, -1);
         MinecraftForge.EVENT_BUS.post(event);
         if (event.isCanceled()) {
            info.setReturnValue(List.of());
         } else {
            int tooltipTextWidth = event.getTooltipElements().stream().mapToInt(either -> (Integer)either.map(font::m_92852_, component -> 0)).max().orElse(0);
            boolean needsWrap = false;
            int tooltipX = mouseX + 12;
            if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
               tooltipX = mouseX - 16 - tooltipTextWidth;
               if (tooltipX < 4) {
                  if (mouseX > screenWidth / 2) {
                     tooltipTextWidth = mouseX - 12 - 8;
                  } else {
                     tooltipTextWidth = screenWidth - 16 - mouseX;
                  }

                  needsWrap = true;
               }
            }

            if (event.getMaxWidth() > 0 && tooltipTextWidth > event.getMaxWidth()) {
               tooltipTextWidth = event.getMaxWidth();
               needsWrap = true;
            }

            info.setReturnValue(
               event.getTooltipElements()
                  .stream()
                  .map(
                     either -> (ClientTooltipComponent)either.map(
                           text -> ClientTooltipComponent.m_169948_(
                                 text instanceof Component ? ((Component)text).m_7532_() : Language.m_128107_().m_5536_(text)
                              ),
                           ClientTooltipComponent::m_169950_
                        )
                  )
                  .toList()
            );
         }
      }
   }
}
