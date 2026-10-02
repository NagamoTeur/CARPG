package shadows.apotheosis.potion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apotheosis;

public class PotionCharmItem extends Item {
   public static final Set<ResourceLocation> EXTENDED_POTIONS = new HashSet<>();

   public PotionCharmItem() {
      super(new Properties().m_41487_(1).m_41503_(192).m_41491_(Apotheosis.APOTH_GROUP).setNoRepair());
   }

   public ItemStack m_7968_() {
      return PotionUtils.m_43549_(super.m_7968_(), Potions.f_43606_);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
      if (hasPotion(stack)) {
         if (!PotionModule.charmsInCuriosOnly || slot == -1) {
            if (stack.m_41784_().m_128471_("charm_enabled") && entity instanceof ServerPlayer) {
               Potion p = PotionUtils.m_43579_(stack);
               MobEffectInstance contained = (MobEffectInstance)p.m_43488_().get(0);
               MobEffectInstance active = ((ServerPlayer)entity).m_21124_(contained.m_19544_());
               if (active == null || active.m_19557_() < getCriticalDuration(active.m_19544_())) {
                  int durationOffset = getCriticalDuration(contained.m_19544_());
                  if (contained.m_19544_() == MobEffects.f_19605_) {
                     durationOffset += 50 >> contained.m_19564_();
                  }

                  MobEffectInstance newEffect = new MobEffectInstance(
                     contained.m_19544_(), (int)Math.ceil((double)contained.m_19557_() / 24.0) + durationOffset, contained.m_19564_(), false, false
                  );
                  ((ServerPlayer)entity).m_7292_(newEffect);
                  if (stack.m_220157_(contained.m_19544_() == MobEffects.f_19605_ ? 2 : 1, world.f_46441_, (ServerPlayer)entity)) {
                     stack.m_41774_(1);
                  }
               }
            }
         }
      }
   }

   private static int getCriticalDuration(MobEffect effect) {
      return EXTENDED_POTIONS.contains(ForgeRegistries.MOB_EFFECTS.getKey(effect)) ? 210 : 5;
   }

   public boolean m_5812_(ItemStack stack) {
      return stack.m_41784_().m_128471_("charm_enabled");
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (!world.f_46443_) {
         stack.m_41784_().m_128379_("charm_enabled", !stack.m_41783_().m_128471_("charm_enabled"));
      } else if (!stack.m_41783_().m_128471_("charm_enabled")) {
         world.m_5594_(player, player.m_20183_(), SoundEvents.f_11871_, SoundSource.PLAYERS, 1.0F, 0.3F);
      }

      return InteractionResultHolder.m_19090_(stack);
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
      if (PotionModule.charmsInCuriosOnly) {
         tooltip.add(Component.m_237115_(this.m_5524_() + ".curios_only").m_130940_(ChatFormatting.RED));
      }

      if (hasPotion(stack)) {
         Potion p = PotionUtils.m_43579_(stack);
         MobEffectInstance effect = (MobEffectInstance)p.m_43488_().get(0);
         MutableComponent potionCmp = Component.m_237115_(effect.m_19576_());
         if (effect.m_19564_() > 0) {
            potionCmp = Component.m_237110_("potion.withAmplifier", new Object[]{potionCmp, Component.m_237115_("potion.potency." + effect.m_19564_())});
         }

         potionCmp.m_130940_(effect.m_19544_().m_19483_().m_19497_());
         tooltip.add(Component.m_237110_(this.m_5524_() + ".desc", new Object[]{potionCmp}).m_130940_(ChatFormatting.GRAY));
         boolean enabled = stack.m_41784_().m_128471_("charm_enabled");
         MutableComponent enabledCmp = Component.m_237115_(this.m_5524_() + (enabled ? ".enabled" : ".disabled"));
         enabledCmp.m_130940_(enabled ? ChatFormatting.BLUE : ChatFormatting.RED);
         if (effect.m_19557_() > 20) {
            potionCmp = Component.m_237110_("potion.withDuration", new Object[]{potionCmp, MobEffectUtil.m_19581_(effect, 1.0F)});
         }

         potionCmp.m_130940_(effect.m_19544_().m_19483_().m_19497_());
         tooltip.add(Component.m_237110_(this.m_5524_() + ".desc3", new Object[]{potionCmp}).m_130940_(ChatFormatting.GRAY));
      }
   }

   public int getMaxDamage(ItemStack stack) {
      return !hasPotion(stack) ? 1 : 192;
   }

   public Component m_7626_(ItemStack stack) {
      if (!hasPotion(stack)) {
         return Component.m_237115_("item.apotheosis.potion_charm_broke");
      } else {
         Potion p = PotionUtils.m_43579_(stack);
         MobEffectInstance effect = (MobEffectInstance)p.m_43488_().get(0);
         MutableComponent potionCmp = Component.m_237115_(effect.m_19576_());
         if (effect.m_19564_() > 0) {
            potionCmp = Component.m_237110_("potion.withAmplifier", new Object[]{potionCmp, Component.m_237115_("potion.potency." + effect.m_19564_())});
         }

         return Component.m_237110_("item.apotheosis.potion_charm", new Object[]{potionCmp});
      }
   }

   public static boolean hasPotion(ItemStack stack) {
      return PotionUtils.m_43579_(stack) != Potions.f_43598_;
   }

   public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
      if (this.m_220152_(group)) {
         for (Potion potion : ForgeRegistries.POTIONS) {
            if (potion.m_43488_().size() == 1 && !((MobEffectInstance)potion.m_43488_().get(0)).m_19544_().m_8093_()) {
               items.add(PotionUtils.m_43549_(new ItemStack(this), potion));
            }
         }
      }
   }

   public String getCreatorModId(ItemStack itemStack) {
      Potion potionType = PotionUtils.m_43579_(itemStack);
      ResourceLocation resourceLocation = ForgeRegistries.POTIONS.getKey(potionType);
      return resourceLocation != null ? resourceLocation.m_135827_() : ForgeRegistries.ITEMS.getKey(this).m_135827_();
   }

   public int m_6473_() {
      return 0;
   }
}
