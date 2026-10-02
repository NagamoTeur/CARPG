package daripher.skilltree.item.gem;

import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.compat.apotheosis.ApotheosisCompatibility;
import daripher.skilltree.data.reloader.GemTypesReloader;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.init.PSTCreativeTabs;
import daripher.skilltree.init.PSTItems;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.item.gem.bonus.GemBonusProvider;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   bus = Bus.MOD
)
public class GemItem extends Item {
   public GemItem() {
      super(new Properties().m_41491_(PSTCreativeTabs.SKILLTREE));
   }

   public void m_6787_(@NotNull CreativeModeTab category, @NotNull NonNullList<ItemStack> items) {
      if (this.m_220152_(category)) {
         GemTypesReloader.getGemTypes().values().stream().sorted().map(GemItem::getDefaultGemStack).forEach(items::add);
      }
   }

   @NotNull
   public String m_5671_(@NotNull ItemStack stack) {
      String gemId = getGemType(stack).id().toString().replace(":", ".");
      return this.m_5524_() + "." + gemId;
   }

   public boolean m_5812_(@NotNull ItemStack stack) {
      return true;
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
      if (SkillTreeMod.apotheosisEnabled()) {
         components.add(Component.m_237115_("gem.disabled").m_130940_(ChatFormatting.RED));
      } else {
         Component gemTooltip = Component.m_237115_("gem.tooltip").m_130940_(ChatFormatting.YELLOW);
         components.add(gemTooltip);
         appendItemBonusesTooltips(stack, components);
      }
   }

   private static void appendItemBonusesTooltips(@NotNull ItemStack stack, @NotNull List<Component> components) {
      getGemType(stack).bonuses().forEach((c, b) -> {
         Component itemDescription = c.getTooltip();
         Component bonusDescription = b.getTooltip(stack);
         Component tooltip = Component.m_237110_("gem_class_format", new Object[]{itemDescription, bonusDescription}).m_130940_(ChatFormatting.GRAY);
         components.add(tooltip);
      });
   }

   public static GemType getGemType(ItemStack gemStack) {
      if (!gemStack.m_41782_()) {
         return GemTypesReloader.NO_TYPE;
      } else {
         ResourceLocation id = new ResourceLocation(gemStack.m_41784_().m_128461_("type"));
         return GemTypesReloader.getGemTypeById(id);
      }
   }

   public static boolean canInsertGem(Player player, ItemStack itemStack, ItemStack gemStack) {
      if (!ItemHelper.canInsertGem(itemStack)) {
         return false;
      } else {
         GemBonusProvider bonusProvider = getGemType(gemStack).getBonusProvider(itemStack);
         return bonusProvider == null ? false : bonusProvider.canApply(player, itemStack, gemStack);
      }
   }

   public static void insertGem(Player player, ItemStack itemStack, ItemStack gemStack) {
      GemBonusProvider bonusProvider = getGemType(gemStack).getBonusProvider(itemStack);
      if (bonusProvider != null) {
         bonusProvider.addGemBonus(player, itemStack, gemStack);
      }
   }

   public static void addGemBonus(@Nonnull Player player, @Nonnull ItemStack itemStack, @Nonnull ItemStack gemStack, @Nonnull ItemBonus<?> bonus) {
      GemType gemType = getGemType(gemStack);
      float gemPower = PlayerHelper.getGemPower(player, itemStack);
      bonus = bonus.copy().multiply((double)gemPower);
      ListTag bonusesTag = itemStack.m_41784_().m_128437_("gem_bonuses", 10);
      ListTag gemsTag = itemStack.m_41784_().m_128437_("gems", 8);
      CompoundTag bonusTag = new CompoundTag();
      SerializationHelper.serializeItemBonus(bonusTag, bonus);
      bonusesTag.add(bonusTag);
      gemsTag.add(StringTag.m_129297_(gemType.id().toString()));
      itemStack.m_41784_().m_128365_("gem_bonuses", bonusesTag);
      itemStack.m_41784_().m_128365_("gems", gemsTag);
   }

   public static void removeGemBonuses(ItemStack stack) {
      if (stack.m_41782_()) {
         stack.m_41784_().m_128473_("gem_bonuses");
         stack.m_41784_().m_128473_("gems");
      }
   }

   public static boolean hasGem(ItemStack stack, int socket) {
      return getGemBonuses(stack).size() > socket;
   }

   public static ItemStack getDefaultGemStack(GemType gemType) {
      ItemStack gemStack = new ItemStack((ItemLike)PSTItems.GEM.get());
      gemStack.m_41784_().m_128359_("type", gemType.id().toString());
      return gemStack;
   }

   public static List<? extends ItemBonus<?>> getGemBonuses(ItemStack stack) {
      if (SkillTreeMod.apotheosisEnabled()) {
         return ApotheosisCompatibility.INSTANCE.getGemBonuses(stack);
      } else {
         return !stack.m_41782_()
            ? List.of()
            : stack.m_41784_().m_128437_("gem_bonuses", 10).stream().map(CompoundTag.class::cast).map(SerializationHelper::deserializeItemBonus).toList();
      }
   }

   public static List<ItemStack> getGems(ItemStack stack) {
      if (SkillTreeMod.apotheosisEnabled()) {
         return ApotheosisCompatibility.INSTANCE.getGems(stack);
      } else {
         return !stack.m_41782_()
            ? List.of()
            : stack.m_41784_()
               .m_128437_("gems", 8)
               .stream()
               .map(StringTag.class::cast)
               .map(StringTag::m_7916_)
               .<ResourceLocation>map(ResourceLocation::new)
               .map(GemTypesReloader::getGemTypeById)
               .map(GemItem::getDefaultGemStack)
               .toList();
      }
   }

   @SubscribeEvent
   public static void addGemModels(RegisterAdditional event) {
      Set<ResourceLocation> textures = Minecraft.m_91087_()
         .m_91098_()
         .m_214159_("models", l -> "skilltree".equals(l.m_135827_()) && l.m_135815_().contains("/gems/") && l.m_135815_().endsWith(".json"))
         .keySet();
      textures.stream()
         .map(l -> l.m_135815_().substring("models/".length(), l.m_135815_().length() - ".json".length()))
         .map(path -> new ResourceLocation("skilltree", path))
         .forEach(event::register);
   }

   @SubscribeEvent
   public static void replaceGemModel(BakingCompleted event) {
      ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation("skilltree", "gem"), "inventory");
      BakedModel oldModel = (BakedModel)event.getModels().get(model);
      if (oldModel != null) {
         event.getModels().put(model, new GemModel(oldModel, event.getModelBakery()));
      }
   }
}
