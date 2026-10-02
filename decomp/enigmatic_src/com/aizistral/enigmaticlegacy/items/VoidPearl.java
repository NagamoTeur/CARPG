package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;

public class VoidPearl extends ItemSpellstoneCurio implements ISpellstone {
   public static Omniconfig.IntParameter spellstoneCooldown;
   public static Omniconfig.DoubleParameter baseDarknessDamage;
   public static Omniconfig.DoubleParameter regenerationDemodifier;
   public static Omniconfig.DoubleParameter shadowRange;
   public static Omniconfig.PerhapsParameter undeadProbability;
   public static Omniconfig.IntParameter witheringTime;
   public static Omniconfig.IntParameter witheringLevel;
   public List<String> healList = new ArrayList<>();
   public DamageSource theDarkness;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("VoidPearl");
      spellstoneCooldown = builder.comment("Active ability cooldown for Pearl of the Void. Measured in ticks. 20 ticks equal to 1 second.")
         .getInt("Cooldown", 0);
      baseDarknessDamage = builder.comment("Base damage dealt by Darkness every half a second, when it devours a creature in proximity of bearer of the pearl.")
         .max(1000.0)
         .getDouble("BaseDarknessDamage", 4.0);
      regenerationDemodifier = builder.comment(
            "Modifier for slowing down player's regeneration when bearing the pearl. This includes natural regeneration, as well as artificial healing effects that work over time. The greater it is, the slower player will regenerate."
         )
         .max(1000.0)
         .getDouble("RegenerationModifier", 1.0);
      shadowRange = builder.comment("Range in which Pearl of the Void will force darkness to devour living creatures.")
         .max(128.0)
         .getDouble("ShadowRange", 16.0);
      undeadProbability = builder.comment(
            "Chance for Pearl of the Void to prevent it's bearer death from receiving lethal amout of damage. Defined as percentage."
         )
         .max(100.0)
         .getPerhaps("UndeadChance", 35);
      witheringTime = builder.comment(
            "Amout of ticks for which bearer of the pearl will apply Withering effect to entities they attack. 20 ticks equals to 1 second."
         )
         .getInt("WitheringTime", 100);
      witheringLevel = builder.comment("Level of Withering that bearer of the pearl will apply to entitities they attack.")
         .max(3.0)
         .getInt("WitheringLevel", 2);
      builder.popPrefix();
   }

   public VoidPearl() {
      super(ItemSpellstoneCurio.getDefaultProperties().m_41487_(1).m_41497_(Rarity.EPIC).m_41486_());
      this.immunityList.add(DamageSource.f_19312_.f_19326_);
      this.immunityList.add(DamageSource.f_19310_.f_19326_);
      this.healList.add(DamageSource.f_19320_.f_19326_);
      this.healList.add(DamageSource.f_19319_.f_19326_);
      this.theDarkness = new DamageSource("darkness");
      this.theDarkness.m_19382_();
      this.theDarkness.m_19380_();
      this.theDarkness.m_19389_();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearlCooldown", ChatFormatting.GOLD, (float)spellstoneCooldown.getValue() / 20.0F);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl11");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl12");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl13", ChatFormatting.GOLD, undeadProbability.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidPearl14");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      try {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list,
            "tooltip.enigmaticlegacy.currentKeybind",
            ChatFormatting.LIGHT_PURPLE,
            ((Component)KeyMapping.m_90842_("key.spellstoneAbility").get()).getString().toUpperCase()
         );
      } catch (NullPointerException var6) {
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof Player player) {
         if (player.m_20146_() < 300) {
            player.m_20301_(300);
         }

         if (player.m_6060_()) {
            player.m_20095_();
         }

         for (MobEffectInstance effect : new ArrayList(player.m_21220_())) {
            if (effect.m_19544_() == MobEffects.f_19611_
               ? effect.m_19557_() < 310 - 10 || effect.m_19557_() > 310
               : !ForgeRegistries.MOB_EFFECTS.getKey(effect.m_19544_()).equals(new ResourceLocation("mana-and-artifice", "chrono-exhaustion"))) {
               player.m_21195_(effect.m_19544_());
            }
         }

         if (player.f_19797_ % 10 == 0) {
            List<LivingEntity> entities = player.f_19853_
               .m_45976_(
                  LivingEntity.class,
                  new AABB(
                     player.m_20185_() - shadowRange.getValue(),
                     player.m_20186_() - shadowRange.getValue(),
                     player.m_20189_() - shadowRange.getValue(),
                     player.m_20185_() + shadowRange.getValue(),
                     player.m_20186_() + shadowRange.getValue(),
                     player.m_20189_() + shadowRange.getValue()
                  )
               );
            boolean hasAnimalGuide = SuperpositionHandler.hasItem(player, EnigmaticItems.ANIMAL_GUIDEBOOK);
            if (entities.contains(player)) {
               entities.remove(player);
            }

            for (LivingEntity victim : entities) {
               if ((victim.f_19853_.m_46849_(victim.m_20183_(), 0) < 3 || victim instanceof Phantom && !victim.m_6060_())
                  && (!hasAnimalGuide || !EnigmaticItems.ANIMAL_GUIDEBOOK.isProtectedAnimal(victim))) {
                  if (victim instanceof Player) {
                     Player playerVictim = (Player)victim;
                     if (SuperpositionHandler.hasCurio(playerVictim, EnigmaticItems.VOID_PEARL)) {
                        playerVictim.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 80, 1, false, true));
                        continue;
                     }
                  }

                  if (!(victim instanceof Player) || player.m_7099_((Player)victim)) {
                     IndirectEntityDamageSource darkness = new IndirectEntityDamageSource("darkness", player, null);
                     darkness.m_19382_().m_19380_().m_19389_();
                     boolean attack = victim.m_6469_(darkness, (float)baseDarknessDamage.getValue());
                     if (attack) {
                        player.f_19853_.m_5594_(null, victim.m_20183_(), SoundEvents.f_12228_, SoundSource.PLAYERS, 1.0F, (float)(0.3F + Math.random() * 0.4));
                        victim.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 80, 1, false, true));
                        victim.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 2, false, true));
                        victim.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 100, 0, false, true));
                        victim.m_7292_(new MobEffectInstance(MobEffects.f_19612_, 160, 2, false, true));
                        victim.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 100, 3, false, true));
                     }
                  }
               }
            }
         }
      }
   }
}
