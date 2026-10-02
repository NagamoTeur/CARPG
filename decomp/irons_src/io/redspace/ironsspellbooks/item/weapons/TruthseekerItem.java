package io.redspace.ironsspellbooks.item.weapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.render.SpecialItemRenderer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class TruthseekerItem extends ExtendedSwordItem {
   public TruthseekerItem() {
      super(ExtendedWeaponTiers.TRUTHSEEKER, 11.0, -3.0, Map.of(), ItemPropertiesHelper.equipment().m_41487_(1).m_41497_(Rarity.UNCOMMON));
   }

   public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new SpecialItemRenderer(Minecraft.m_91087_().m_91291_(), Minecraft.m_91087_().m_167973_(), "truthseeker");
         }
      });
   }
}
