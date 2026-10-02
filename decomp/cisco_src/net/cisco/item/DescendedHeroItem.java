package net.cisco.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DescendedHeroItem extends GeoArmorItem implements IAnimatable {
   private AnimationFactory factory = GeckoLibUtil.createFactory(this);
   public String animationprocedure = "empty";

   public DescendedHeroItem(EquipmentSlot slot, Properties properties) {
      super(new ArmorMaterial() {
         public int m_7366_(EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 1000;
         }

         public int m_7365_(EquipmentSlot slot) {
            return new int[]{30, 30, 30, 30}[slot.m_20749_()];
         }

         public int m_6646_() {
            return 20;
         }

         public SoundEvent m_7344_() {
            return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_netherite"));
         }

         public Ingredient m_6230_() {
            return Ingredient.f_43901_;
         }

         public String m_6082_() {
            return "descended_hero";
         }

         public float m_6651_() {
            return 9.0F;
         }

         public float m_6649_() {
            return 0.25F;
         }
      }, slot, properties);
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      if (itemstack.m_41720_() instanceof DescendedHeroItem armor && armor.f_40377_ == EquipmentSlot.HEAD) {
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Ceromonius Descent: §fDamage reduction increases as hp decreases up to a maximum of 70 percent at 50 % hp."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_("§6[Full Set Bonus]: Avatar of the Dark One: §fIncreases armor by 70 percent and boosts attack by 100 percent of armor value.")
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §4Voidborn Descendant: §fSets hp to 50 % and grants 6 seconds of invulnerability. (default x)"));
      }

      if (itemstack.m_41720_() instanceof DescendedHeroItem armor && armor.f_40377_ == EquipmentSlot.CHEST) {
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Ceromonius Descent: §fDamage reduction increases as hp decreases up to a maximum of 70 percent at 50 % hp."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_("§6[Full Set Bonus]: Avatar of the Dark One: §fIncreases armor by 70 percent and boosts attack by 100 percent of armor value.")
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §4Voidborn Descendant: §fSets hp to 50 % and grants 6 seconds of invulnerability. (default x)"));
      }

      if (itemstack.m_41720_() instanceof DescendedHeroItem armor && armor.f_40377_ == EquipmentSlot.LEGS) {
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Ceromonius Descent: §fDamage reduction increases as hp decreases up to a maximum of 70 percent at 50 % hp."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_("§6[Full Set Bonus]: Avatar of the Dark One: §fIncreases armor by 70 percent and boosts attack by 100 percent of armor value.")
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §4Voidborn Descendant: §fSets hp to 50 % and grants 6 seconds of invulnerability. (default x)"));
      }

      if (itemstack.m_41720_() instanceof DescendedHeroItem armor && armor.f_40377_ == EquipmentSlot.FEET) {
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Ceromonius Descent: §fDamage reduction increases as hp decreases up to a maximum of 70 percent at 50 % hp."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_("§6[Full Set Bonus]: Avatar of the Dark One: §fIncreases armor by 70 percent and boosts attack by 100 percent of armor value.")
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §4Voidborn Descendant: §fSets hp to 50 % and grants 6 seconds of invulnerability. (default x)"));
      }
   }

   public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
   }

   private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
      List<EquipmentSlot> slotData = event.getExtraDataOfType(EquipmentSlot.class);
      List<ItemStack> stackData = event.getExtraDataOfType(ItemStack.class);
      LivingEntity livingEntity = (LivingEntity)event.getExtraDataOfType(LivingEntity.class).get(0);
      if (this.animationprocedure.equals("empty")) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.new", EDefaultLoopTypes.LOOP));
         if (livingEntity instanceof ArmorStand) {
            return PlayState.CONTINUE;
         } else {
            List<Item> armorList = new ArrayList<>(4);

            for (EquipmentSlot slot : EquipmentSlot.values()) {
               if (slot.m_20743_() == Type.ARMOR && livingEntity.m_6844_(slot) != null) {
                  armorList.add(livingEntity.m_6844_(slot).m_41720_());
               }
            }

            boolean isWearingAll = armorList.containsAll(
               Arrays.asList(
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_BOOTS.get(),
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_LEGGINGS.get(),
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_CHESTPLATE.get(),
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_HELMET.get()
               )
            );
            return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
         }
      } else {
         return PlayState.STOP;
      }
   }

   private <P extends IAnimatable> PlayState procedurePredicate(AnimationEvent<P> event) {
      List<EquipmentSlot> slotData = event.getExtraDataOfType(EquipmentSlot.class);
      List<ItemStack> stackData = event.getExtraDataOfType(ItemStack.class);
      LivingEntity livingEntity = (LivingEntity)event.getExtraDataOfType(LivingEntity.class).get(0);
      if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation(this.animationprocedure, EDefaultLoopTypes.PLAY_ONCE));
         if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            this.animationprocedure = "empty";
            event.getController().markNeedsReload();
         }

         if (livingEntity instanceof ArmorStand) {
            return PlayState.CONTINUE;
         } else {
            List<Item> armorList = new ArrayList<>(4);

            for (EquipmentSlot slot : EquipmentSlot.values()) {
               if (slot.m_20743_() == Type.ARMOR && livingEntity.m_6844_(slot) != null) {
                  armorList.add(livingEntity.m_6844_(slot).m_41720_());
               }
            }

            boolean isWearingAll = armorList.containsAll(
               Arrays.asList(
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_BOOTS.get(),
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_LEGGINGS.get(),
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_CHESTPLATE.get(),
                  (DescendedHeroItem)CiscoModModItems.DESCENDED_HERO_HELMET.get()
               )
            );
            return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
         }
      } else {
         return PlayState.CONTINUE;
      }
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, "controller", 5.0F, this::predicate));
      data.addAnimationController(new AnimationController(this, "procedureController", 5.0F, this::procedurePredicate));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }
}
