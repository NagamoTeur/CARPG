package com.hollingsworth.arsnouveau.common.armor;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.client.IVariantColorProvider;
import com.hollingsworth.arsnouveau.api.mana.IManaEquipment;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkProvider;
import com.hollingsworth.arsnouveau.api.perk.ITickablePerk;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.api.perk.PerkInstance;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.crafting.recipes.IDyeable;
import com.hollingsworth.arsnouveau.common.perk.RepairingPerk;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class AnimatedMagicArmor extends ArmorItem implements IManaEquipment, IDyeable, IAnimatable, IVariantColorProvider<ItemStack> {
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public AnimatedMagicArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
      super(materialIn, slot, builder);
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   public void onArmorTick(ItemStack stack, Level world, Player player) {
      if (!world.m_5776_()) {
         RepairingPerk.attemptRepair(stack, player);
         IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(stack);
         if (perkHolder != null) {
            for (PerkInstance instance : perkHolder.getPerkInstances()) {
               if (instance.getPerk() instanceof ITickablePerk tickablePerk) {
                  tickablePerk.tick(stack, world, player, instance);
               }
            }
         }
      }
   }

   protected UUID getModifierForSlot(EquipmentSlot pEquipmentSlot) {
      return f_40380_[pEquipmentSlot.m_20749_()];
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot pEquipmentSlot, ItemStack stack) {
      Builder<Attribute, AttributeModifier> attributes = new Builder();
      attributes.putAll(super.m_7167_(pEquipmentSlot));
      if (this.f_40377_ == pEquipmentSlot) {
         UUID uuid = this.getModifierForSlot(this.f_40377_);
         IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(stack);
         if (perkHolder != null) {
            attributes.put(
               (Attribute)PerkAttributes.FLAT_MANA_BONUS.get(),
               new AttributeModifier(uuid, "max_mana_armor", (double)(30 * (perkHolder.getTier() + 1)), Operation.ADDITION)
            );
            attributes.put(
               (Attribute)PerkAttributes.MANA_REGEN_BONUS.get(),
               new AttributeModifier(uuid, "mana_regen_armor", (double)(perkHolder.getTier() + 1), Operation.ADDITION)
            );

            for (PerkInstance perkInstance : perkHolder.getPerkInstances()) {
               IPerk perk = perkInstance.getPerk();
               attributes.putAll(perk.getModifiers(this.f_40377_, stack, perkInstance.getSlot().value));
            }
         }
      }

      return attributes.build();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
      super.m_7373_(stack, world, tooltip, flag);
      IPerkProvider<ItemStack> perkProvider = ArsNouveauAPI.getInstance().getPerkProvider(stack.m_41720_());
      if (perkProvider != null) {
         if (perkProvider.getPerkHolder(stack) instanceof ArmorPerkHolder armorPerkHolder) {
            tooltip.add(Component.m_237110_("ars_nouveau.tier", new Object[]{armorPerkHolder.getTier() + 1}).m_130940_(ChatFormatting.GOLD));
         }

         perkProvider.getPerkHolder(stack).appendPerkTooltip(tooltip, stack);
      }
   }

   @Override
   public void onDye(ItemStack stack, DyeColor dyeColor) {
      if (PerkUtil.getPerkHolder(stack) instanceof ArmorPerkHolder armorPerkHolder) {
         armorPerkHolder.setColor(dyeColor.m_41065_());
      }
   }

   public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
      return true;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(
         new IClientItemExtensions() {
            @NotNull
            public HumanoidModel<?> getHumanoidArmorModel(
               LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original
            ) {
               return GeoArmorRenderer.getRenderer((Class<? extends ArmorItem>)AnimatedMagicArmor.this.getClass(), livingEntity)
                  .applyEntityStats(original)
                  .applySlot(equipmentSlot)
                  .setCurrentItem(livingEntity, itemStack, equipmentSlot);
            }
         }
      );
   }

   @Nullable
   public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      Class<? extends ArmorItem> clazz = (Class<? extends ArmorItem>)this.getClass();
      GeoArmorRenderer renderer = GeoArmorRenderer.getRenderer(clazz, entity);
      return renderer.getTextureLocation((ArmorItem)stack.m_41720_()).toString();
   }

   public void setColor(String color, ItemStack armor) {
   }

   public String getColor(ItemStack object) {
      if (!(PerkUtil.getPerkHolder(object) instanceof ArmorPerkHolder data)) {
         return "purple";
      } else {
         return data.getColor() != null && !data.getColor().isEmpty() ? data.getColor() : "purple";
      }
   }

   public int getMinTier() {
      return 0;
   }
}
