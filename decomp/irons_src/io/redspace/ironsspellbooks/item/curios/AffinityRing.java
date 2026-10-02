package io.redspace.ironsspellbooks.item.curios;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.render.AffinityRingRenderer;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AffinityRing extends SimpleDescriptiveCurio {
   public AffinityRing(Properties properties) {
      super(properties);
   }

   public void m_7373_(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
      AbstractSpell spell = AffinityData.getAffinityData(pStack).getSpell();
      if (!spell.equals(SpellRegistry.none())) {
         tooltip.add(Component.m_237119_());
         tooltip.add(Component.m_237115_("curios.modifiers.ring").m_130940_(ChatFormatting.GOLD));
         tooltip.add(
            Component.m_237113_(" ")
               .m_7220_(
                  Component.m_237110_(
                        "tooltip.irons_spellbooks.enhance_spell_level",
                        new Object[]{
                           spell.getDisplayName(MinecraftInstanceHelper.instance.player()).m_130948_(spell.getSchoolType().getDisplayName().m_7383_())
                        }
                     )
                     .m_130940_(ChatFormatting.YELLOW)
               )
         );
      } else {
         tooltip.add(
            Component.m_237115_("tooltip.irons_spellbooks.empty_affinity_ring").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC})
         );
      }
   }

   public Component m_7626_(ItemStack pStack) {
      return Component.m_237110_(this.m_5671_(pStack), new Object[]{AffinityData.getAffinityData(pStack).getNameForItem()});
   }

   public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new AffinityRingRenderer(Minecraft.m_91087_().m_91291_(), Minecraft.m_91087_().m_167973_());
         }
      });
   }
}
