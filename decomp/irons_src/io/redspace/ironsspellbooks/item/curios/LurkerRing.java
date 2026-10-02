package io.redspace.ironsspellbooks.item.curios;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.compat.Curios;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;

public class LurkerRing extends SimpleDescriptiveCurio {
   public static final int COOLDOWN_IN_TICKS = 300;
   public static final float MULTIPLIER = 1.5F;

   public LurkerRing() {
      super(new Properties().m_41487_(1), Curios.RING_SLOT);
   }

   @Override
   public List<Component> getDescriptionLines(ItemStack stack) {
      double playerCooldownModifier = Minecraft.m_91087_().f_91074_ == null
         ? 1.0
         : Minecraft.m_91087_().f_91074_.m_21133_((Attribute)AttributeRegistry.COOLDOWN_REDUCTION.get());
      return List.of(
         Component.m_237110_(
               "tooltip.irons_spellbooks.passive_ability",
               new Object[]{Utils.timeFromTicks((float)(300.0 * (2.0 - Utils.softCapFormula(playerCooldownModifier))), 1)}
            )
            .m_130940_(ChatFormatting.GREEN),
         this.getDescription(stack)
      );
   }

   @Override
   public Component getDescription(ItemStack stack) {
      return Component.m_237113_(" ").m_7220_(Component.m_237110_(this.m_5524_() + ".desc", new Object[]{50})).m_130948_(this.descriptionStyle);
   }
}
