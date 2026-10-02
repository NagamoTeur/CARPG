package daripher.skilltree.item.quiver;

import daripher.skilltree.init.PSTCreativeTabs;
import daripher.skilltree.item.ItemBonusProvider;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.QuiverCapacityBonus;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingGetProjectileEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(
   modid = "skilltree"
)
public class QuiverItem extends Item implements ICurioItem, ItemBonusProvider {
   private static final String ARROWS_TAG = "Arrows";
   private static final String ARROWS_COUNT_TAG = "ArrowsCount";
   private final int capacity;

   public QuiverItem(int capacity) {
      super(new Properties().m_41491_(PSTCreativeTabs.SKILLTREE).m_41487_(1).m_41503_(capacity));
      this.capacity = capacity;
   }

   public QuiverItem() {
      this(250);
   }

   @SubscribeEvent
   public static void storeArrowsOnPickup(EntityItemPickupEvent event) {
      ItemStack arrows = event.getItem().m_32055_();
      if (arrows.m_41720_() instanceof ArrowItem) {
         Optional<SlotResult> quiverCurio = CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), ItemHelper::isQuiver);
         quiverCurio.<ItemStack>map(SlotResult::stack).ifPresent(quiver -> {
            if (!isFull(quiver)) {
               if (!containsArrows(quiver)) {
                  setArrows(quiver, arrows.m_41777_(), arrows.m_41613_());
                  event.getItem().m_32045_(ItemStack.f_41583_);
                  event.setResult(Result.ALLOW);
               } else if (ItemStack.m_150942_(getArrows(quiver), arrows)) {
                  int capacity = getCapacity(quiver);
                  int arrowsTaken = Math.min(capacity - getArrowsCount(quiver), arrows.m_41613_());
                  addArrows(quiver, arrowsTaken);
                  if (arrows.m_41613_() == arrowsTaken) {
                     event.getItem().m_32045_(ItemStack.f_41583_);
                  } else {
                     arrows.m_41774_(arrowsTaken);
                  }

                  event.setResult(Result.ALLOW);
               }
            }
         });
      }
   }

   @SubscribeEvent
   public static void takeArrowFromQuiver(LivingGetProjectileEvent event) {
      Optional<SlotResult> quiverCurio = CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), ItemHelper::isQuiver);
      quiverCurio.<ItemStack>map(SlotResult::stack).ifPresent(quiver -> {
         if (containsArrows(quiver)) {
            event.setProjectileItemStack(getArrows(quiver).m_41777_());
         }
      });
   }

   @SubscribeEvent
   public static void removeArrowFromQuiver(ArrowLooseEvent event) {
      if (!event.getEntity().m_7500_()) {
         Optional<SlotResult> quiverCurio = CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), ItemHelper::isQuiver);
         quiverCurio.<ItemStack>map(SlotResult::stack).ifPresent(quiver -> {
            if (containsArrows(quiver)) {
               addArrows(quiver, -1);
            }
         });
      }
   }

   public static boolean isFull(ItemStack quiver) {
      return getArrowsCount(quiver) == getCapacity(quiver);
   }

   public static boolean isEmpty(ItemStack quiver) {
      return getArrowsCount(quiver) == 0;
   }

   public static int getCapacity(ItemStack quiver) {
      int capacity = ((QuiverItem)quiver.m_41720_()).capacity;
      capacity += (int)getCapacityBonus(quiver, Operation.ADDITION);
      float multiplier = 1.0F + getCapacityBonus(quiver, Operation.MULTIPLY_BASE);
      capacity = (int)((float)capacity * multiplier);
      multiplier = 1.0F + getCapacityBonus(quiver, Operation.MULTIPLY_TOTAL);
      return (int)((float)capacity * multiplier);
   }

   private static float getCapacityBonus(ItemStack quiver, Operation operation) {
      return ItemHelper.getItemBonuses(quiver, QuiverCapacityBonus.class)
         .stream()
         .filter(b -> b.getOperation() == operation)
         .map(QuiverCapacityBonus::getAmount)
         .reduce(Float::sum)
         .orElse(0.0F);
   }

   public static boolean containsArrows(ItemStack stack) {
      return stack.m_41782_() && Objects.requireNonNull(stack.m_41783_()).m_128441_("Arrows") && !getArrows(stack).m_41619_() && getArrowsCount(stack) > 0;
   }

   public static ItemStack getArrows(ItemStack stack) {
      return ItemStack.m_41712_(Objects.requireNonNull(stack.m_41784_().m_128423_("Arrows")));
   }

   public static int getArrowsCount(ItemStack stack) {
      return stack.m_41784_().m_128451_("ArrowsCount");
   }

   public static void addArrows(ItemStack stack, ItemStack arrows, int count) {
      if (isEmpty(stack)) {
         setArrows(stack, arrows);
      }

      addArrows(stack, count);
   }

   public static void setArrows(ItemStack stack, ItemStack arrows, int count) {
      arrows = arrows.m_41777_();
      arrows.m_41764_(1);
      stack.m_41784_().m_128365_("Arrows", arrows.m_41739_(new CompoundTag()));
      setArrowsCount(stack, count);
   }

   public static void setArrows(ItemStack stack, ItemStack arrow) {
      stack.m_41784_().m_128365_("Arrows", arrow.m_41739_(new CompoundTag()));
   }

   public static void setArrowsCount(ItemStack stack, int count) {
      stack.m_41784_().m_128405_("ArrowsCount", count);
   }

   public static void addArrows(ItemStack stack, int count) {
      stack.m_41784_().m_128405_("ArrowsCount", getArrowsCount(stack) + count);
   }

   public void m_7373_(@NotNull ItemStack stack, Level level, List<Component> components, @NotNull TooltipFlag tooltipFlag) {
      Component capacity = Component.m_237113_(getCapacity(stack) + "").m_130940_(ChatFormatting.BLUE);
      components.add(Component.m_237110_("quiver.capacity", new Object[]{capacity}).m_130940_(ChatFormatting.YELLOW));
      if (containsArrows(stack)) {
         ItemStack arrows = getArrows(stack);
         Component arrowName = Component.m_237119_().m_7220_(arrows.m_41786_()).m_130940_(ChatFormatting.GRAY);
         Component contents = Component.m_237110_("quiver.contents", new Object[]{arrowName}).m_130940_(ChatFormatting.YELLOW);
         components.add(contents);
         arrows.m_41720_().m_7373_(arrows, level, components, tooltipFlag);
      }
   }

   public int getDamage(ItemStack stack) {
      return !containsArrows(stack) ? 0 : getCapacity(stack) - getArrowsCount(stack);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return false;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (level.f_46443_) {
         return InteractionResultHolder.m_19090_(stack);
      } else {
         int arrowsLeft;
         for (arrowsLeft = getArrowsCount(stack); arrowsLeft >= 64; arrowsLeft -= 64) {
            this.dropArrows(player, stack, 64);
         }

         if (arrowsLeft > 0) {
            this.dropArrows(player, stack, arrowsLeft);
         }

         setArrows(stack, ItemStack.f_41583_, 0);
         return InteractionResultHolder.m_19090_(stack);
      }
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
      this.getItemBonuses().stream().map(ItemBonus::getTooltip).forEach(tooltips::add);
      return tooltips;
   }

   @NotNull
   @Override
   public List<ItemBonus<?>> getItemBonuses() {
      return List.of();
   }

   private void dropArrows(Player player, ItemStack stack, int count) {
      ItemStack arrowsStack = getArrows(stack).m_41777_();
      arrowsStack.m_41764_(count);
      player.m_19983_(arrowsStack);
   }
}
