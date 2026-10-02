package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.item.IRadialProvider;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.StackUtil;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.GuiRadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenuSlot;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.hollingsworth.arsnouveau.client.keybindings.ModKeyBindings;
import com.hollingsworth.arsnouveau.client.renderer.item.SpellBookRenderer;
import com.hollingsworth.arsnouveau.common.capability.ANPlayerDataCap;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import com.hollingsworth.arsnouveau.common.crafting.recipes.IDyeable;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSetBookMode;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class SpellBook extends ModItem implements IAnimatable, ICasterTool, IDyeable, IRadialProvider {
   public SpellTier tier;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public SpellBook(SpellTier tier) {
      super(new Properties().m_41487_(1).m_41491_(ArsNouveau.itemGroup));
      this.tier = tier;
   }

   public SpellBook(Properties properties, SpellTier tier) {
      super(properties);
      this.tier = tier;
   }

   public boolean m_41465_() {
      return false;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.m_21120_(handIn);
      if (this != ItemsRegistry.CREATIVE_SPELLBOOK.get()) {
         CapabilityRegistry.getMana(playerIn).ifPresent(iMana -> {
            if (iMana.getBookTier() < this.tier.value) {
               iMana.setBookTier(this.tier.value);
            }

            IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(playerIn).orElse(new ANPlayerDataCap());
            if (iMana.getGlyphBonus() < cap.getKnownGlyphs().size()) {
               iMana.setGlyphBonus(cap.getKnownGlyphs().size());
            }
         });
      }

      ISpellCaster caster = this.getSpellCaster(stack);
      return caster.castSpell(worldIn, playerIn, handIn, Component.m_237115_("ars_nouveau.invalid_spell"));
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
      super.m_7373_(stack, world, tooltip, flag);
      tooltip.add(Component.m_237110_("ars_nouveau.spell_book.select", new Object[]{KeyMapping.m_90842_(ModKeyBindings.OPEN_RADIAL_HUD.m_90860_()).get()}));
      tooltip.add(Component.m_237110_("ars_nouveau.spell_book.craft", new Object[]{KeyMapping.m_90842_(ModKeyBindings.OPEN_BOOK.m_90860_()).get()}));
      tooltip.add(
         Component.m_237110_("tooltip.ars_nouveau.caster_level", new Object[]{this.getTier().value}).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.BLUE))
      );
   }

   public SpellTier getTier() {
      return this.tier;
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @NotNull
   @Override
   public ISpellCaster getSpellCaster(ItemStack stack) {
      return new SpellBook.BookCaster(stack);
   }

   @Override
   public ISpellCaster getSpellCaster() {
      return new SpellBook.BookCaster(new CompoundTag());
   }

   @Override
   public ISpellCaster getSpellCaster(CompoundTag tag) {
      return new SpellBook.BookCaster(tag);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new SpellBookRenderer();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void onOpenBookMenuKeyPressed(ItemStack stack, Player player) {
      InteractionHand hand = StackUtil.getBookHand(player);
      if (hand != null) {
         Minecraft.m_91087_().m_91152_(new GuiSpellBook(hand));
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void onRadialKeyPressed(ItemStack stack, Player player) {
      Minecraft.m_91087_().m_91152_(new GuiRadialMenu<>(this.getRadialMenuProviderForSpellpart(stack)));
   }

   public RadialMenu<AbstractSpellPart> getRadialMenuProviderForSpellpart(ItemStack itemStack) {
      return new RadialMenu<>(slot -> {
         SpellBook.BookCaster caster = new SpellBook.BookCaster(itemStack);
         caster.setCurrentSlot(slot);
         Networking.INSTANCE.sendToServer(new PacketSetBookMode(itemStack.m_41783_()));
      }, this.getRadialMenuSlotsForSpellpart(itemStack), RenderUtils::drawSpellPart, 0);
   }

   public List<RadialMenuSlot<AbstractSpellPart>> getRadialMenuSlotsForSpellpart(ItemStack itemStack) {
      SpellBook.BookCaster spellCaster = new SpellBook.BookCaster(itemStack);
      List<RadialMenuSlot<AbstractSpellPart>> radialMenuSlots = new ArrayList<>();

      for (int i = 0; i < spellCaster.getMaxSlots(); i++) {
         Spell spell = spellCaster.getSpell(i);
         AbstractSpellPart primaryIcon = null;
         List<AbstractSpellPart> secondaryIcons = new ArrayList<>();

         for (AbstractSpellPart p : spell.recipe) {
            if (p instanceof AbstractCastMethod) {
               secondaryIcons.add(p);
            }

            if (p instanceof AbstractEffect) {
               primaryIcon = p;
               break;
            }
         }

         radialMenuSlots.add(new RadialMenuSlot<>(spellCaster.getSpellName(i), primaryIcon, secondaryIcons));
      }

      return radialMenuSlots;
   }

   @Override
   public boolean canQuickCast() {
      return true;
   }

   public static class BookCaster extends SpellCaster {
      public BookCaster(ItemStack stack) {
         super(stack);
      }

      public BookCaster(CompoundTag tag) {
         super(tag);
      }

      @Override
      public int getMaxSlots() {
         return 10;
      }
   }
}
