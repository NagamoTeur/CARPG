package io.redspace.ironsspellbooks.item.weapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.render.SpecialItemRenderer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class KeeperFlambergeItem extends ExtendedSwordItem {
   public KeeperFlambergeItem() {
      super(
         ExtendedWeaponTiers.KEEPER_FLAMBERGE,
         10.0,
         -2.7,
         Map.of(Attributes.f_22284_, new AttributeModifier(UUID.fromString("c552273e-6669-4cd2-80b3-a703b7616336"), "weapon mod", 5.0, Operation.ADDITION)),
         ItemPropertiesHelper.equipment().m_41487_(1).m_41486_().m_41497_(Rarity.UNCOMMON)
      );
   }

   public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new SpecialItemRenderer(Minecraft.m_91087_().m_91291_(), Minecraft.m_91087_().m_167973_(), "keeper_flamberge");
         }
      });
   }
}
