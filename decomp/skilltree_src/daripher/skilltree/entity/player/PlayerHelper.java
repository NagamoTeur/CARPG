package daripher.skilltree.entity.player;

import com.google.common.collect.Streams;
import daripher.skilltree.skill.bonus.SkillBonusHandler;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.player.GemPowerBonus;
import daripher.skilltree.skill.bonus.player.PlayerSocketsBonus;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class PlayerHelper {
   public static void hurtShield(Player player, ItemStack shield, float amount) {
      if (shield.canPerformAction(ToolActions.SHIELD_BLOCK)) {
         if (!player.f_19853_.f_46443_) {
            player.m_36246_(Stats.f_12982_.m_12902_(shield.m_41720_()));
         }

         if (!(amount < 3.0F)) {
            amount = (float)(1 + Mth.m_14143_(amount));
            shield.m_41622_((int)amount, player, p -> {
               p.m_21190_(InteractionHand.OFF_HAND);
               ForgeEventFactory.onPlayerDestroyItem(player, shield, InteractionHand.OFF_HAND);
            });
            if (shield.m_41619_()) {
               player.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
               player.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + player.f_19853_.f_46441_.m_188501_() * 0.4F);
            }
         }
      }
   }

   public static int getPlayerSockets(ItemStack stack, @Nonnull Player player) {
      return SkillBonusHandler.getSkillBonuses(player, PlayerSocketsBonus.class)
         .stream()
         .filter(bonus -> bonus.getItemCondition().met(stack))
         .map(PlayerSocketsBonus::getSockets)
         .reduce(Integer::sum)
         .orElse(0);
   }

   public static float getGemPower(Player player, ItemStack stack) {
      float power = 1.0F;
      return power
         + SkillBonusHandler.getSkillBonuses(player, GemPowerBonus.class)
            .stream()
            .filter(bonus -> bonus.getItemCondition().met(stack))
            .map(GemPowerBonus::getMultiplier)
            .reduce(Float::sum)
            .orElse(0.0F);
   }

   public static Stream<ItemStack> getAllEquipment(LivingEntity living) {
      return Streams.concat(new Stream[]{getEquipment(living), getCurios(living)});
   }

   public static Stream<ItemStack> getItemsInHands(LivingEntity living) {
      return Stream.of(living.m_21205_(), living.m_21206_());
   }

   public static Stream<ItemStack> getEquipment(LivingEntity living) {
      return Arrays.stream(EquipmentSlot.values()).map(slot -> getEquipment(living, slot));
   }

   @NotNull
   private static ItemStack getEquipment(LivingEntity living, EquipmentSlot slot) {
      ItemStack stack = living.m_6844_(slot);
      return slot == EquipmentSlot.MAINHAND && !EquipmentCondition.isWeapon(stack) && !EquipmentCondition.isTool(stack) ? ItemStack.f_41583_ : stack;
   }

   public static Stream<ItemStack> getCurios(LivingEntity living) {
      return CuriosApi.getCuriosHelper().findCurios(living, s -> true).stream().map(SlotResult::stack);
   }
}
