package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class PetGuidebook extends ItemBase implements Vanishable {
   public static final List<ResourceLocation> animalExclusionList = new ArrayList<>();

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      animalExclusionList.clear();
      String[] blacklist = builder.config
         .getStringList(
            "AnimalGuideAnimalExclusionList",
            builder.getCurrentCategory(),
            new String[0],
            "List of entities that should count towards the curse-altering effect of Guide to Animal Companionship, and thus remain neutral to players bearing Ring of the Seven Curses if they posses such guide. Examples: minecraft:iron_golem, minecraft:zombified_piglin"
         );
      Arrays.stream(blacklist).forEach(entry -> animalExclusionList.add(new ResourceLocation(entry)));
   }

   public PetGuidebook() {
      super(getDefaultProperties().m_41487_(1).m_41497_(Rarity.UNCOMMON));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.animalGuidebook1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.animalGuidebook2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.animalGuidebook3");
         if (Minecraft.m_91087_().f_91074_ != null && SuperpositionHandler.isTheCursedOne(Minecraft.m_91087_().f_91074_)) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.animalGuidebook4");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.animalGuidebook5");
         }
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (Minecraft.m_91087_().f_91074_ != null && Minecraft.m_91087_().f_91074_.m_7500_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.animalGuidebookCreative1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.animalGuidebookCreative2");
      }
   }

   public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof Player && !worldIn.f_46443_) {
         Player player = (Player)entityIn;
      }
   }

   public InteractionResult m_6880_(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
      if (player.m_7500_()) {
         if (!player.f_19853_.f_46443_) {
            boolean tamable = this.isTamableAnimal(target);
            MutableComponent reply = Component.m_237113_(tamable ? "Yep, this animal seems tamable." : "Nope, this animal is not considered tamable.");
            player.m_213846_(reply.m_130940_(tamable ? ChatFormatting.GREEN : ChatFormatting.RED));
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean isProtectedAnimal(LivingEntity animal) {
      return !(animal instanceof NeutralMob) || animal instanceof Hoglin || animal instanceof Bee || animal instanceof Wolf;
   }

   public boolean isTamableAnimal(LivingEntity entity) {
      return !(entity instanceof TamableAnimal) && !(entity instanceof Bee)
         ? animalExclusionList.contains(ForgeRegistries.ENTITY_TYPES.getKey(entity.m_6095_()))
         : true;
   }
}
