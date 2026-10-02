package io.redspace.ironsspellbooks.jei;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.capabilities.magic.UpgradeData;
import io.redspace.ironsspellbooks.item.InkItem;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.util.UpgradeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class ArcaneAnvilRecipe {
   @NotNull
   ArcaneAnvilRecipe.Type type;
   @Nullable
   ItemStack leftItem;
   @Nullable
   List<ItemStack> rightItem;
   @Nullable
   AbstractSpell spell;
   @Nullable
   int level;

   public ArcaneAnvilRecipe(ItemStack leftItem, List<ItemStack> rightItem) {
      this.leftItem = leftItem;
      this.rightItem = rightItem;
      this.type = ArcaneAnvilRecipe.Type.Item_Upgrade;
   }

   public ArcaneAnvilRecipe(ItemStack leftItem, AbstractSpell spell) {
      this.leftItem = leftItem;
      this.spell = spell;
      this.type = ArcaneAnvilRecipe.Type.Imbue;
   }

   public ArcaneAnvilRecipe(AbstractSpell spell, int baseLevel) {
      this.spell = spell;
      this.level = baseLevel;
      this.type = ArcaneAnvilRecipe.Type.Scroll_Upgrade;
   }

   public ArcaneAnvilRecipe(AbstractSpell spell) {
      this.spell = spell;
      this.type = ArcaneAnvilRecipe.Type.Affinity_Ring_Attune;
   }

   public ArcaneAnvilRecipe.Tuple<List<ItemStack>, List<ItemStack>, List<ItemStack>> getRecipeItems() {
      return switch (this.type) {
         case Scroll_Upgrade -> {
            ItemStack scroll1 = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
            ItemStack scroll2 = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
            ItemStack ink = new ItemStack(InkItem.getInkForRarity(this.spell.getRarity(this.level + 1)));
            ISpellContainer.createScrollContainer(this.spell, this.level, scroll1);
            ISpellContainer.createScrollContainer(this.spell, this.level + 1, scroll2);
            yield new ArcaneAnvilRecipe.Tuple<>(List.of(scroll1), List.of(ink), List.of(scroll2));
         }
         case Imbue -> {
            ArcaneAnvilRecipe.Tuple<List<ItemStack>, List<ItemStack>, List<ItemStack>> tuple = new ArcaneAnvilRecipe.Tuple<>(
               new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            );
            tuple.a.add(this.leftItem);
            SpellRegistry.getEnabledSpells().forEach(spell -> IntStream.rangeClosed(spell.getMinLevel(), spell.getMaxLevel()).forEach(i -> {
                  ItemStack scroll = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
                  ISpellContainer.createScrollContainer(spell, i, scroll);
                  ItemStack resultx = this.leftItem.m_41777_();
                  ISpellContainer.createScrollContainer(spell, i, resultx);
                  tuple.b.add(scroll);
                  tuple.c.add(resultx);
               }));
            yield tuple;
         }
         case Item_Upgrade -> {
            ArcaneAnvilRecipe.Tuple<List<ItemStack>, List<ItemStack>, List<ItemStack>> tuple = new ArcaneAnvilRecipe.Tuple<>(
               new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            );
            tuple.a.add(this.leftItem);
            this.rightItem
               .forEach(
                  upgradeStack -> {
                     ItemStack resultx = this.leftItem.m_41777_();
                     UpgradeData.setUpgradeData(
                        resultx,
                        UpgradeData.NONE
                           .addUpgrade(
                              resultx, ((UpgradeOrbItem)upgradeStack.m_41720_()).getUpgradeType(), UpgradeUtils.getRelevantEquipmentSlot(this.leftItem)
                           )
                     );
                     tuple.b.add(upgradeStack);
                     tuple.c.add(resultx);
                  }
               );
            yield tuple;
         }
         case Affinity_Ring_Attune -> {
            ArcaneAnvilRecipe.Tuple<List<ItemStack>, List<ItemStack>, List<ItemStack>> tuple = new ArcaneAnvilRecipe.Tuple<>(
               new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            );
            ItemStack result = new ItemStack((ItemLike)ItemRegistry.AFFINITY_RING.get());
            AffinityData.setAffinityData(result, this.spell);
            SpellRegistry.getEnabledSpells().forEach(randomSpell -> {
               ItemStack baseRing = new ItemStack((ItemLike)ItemRegistry.AFFINITY_RING.get());
               AffinityData.setAffinityData(baseRing, randomSpell);
               tuple.a.add(baseRing);
            });
            IntStream.rangeClosed(this.spell.getMinLevel(), this.spell.getMaxLevel()).forEach(i -> {
               ItemStack scroll = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
               ISpellContainer.createScrollContainer(this.spell, i, scroll);
               tuple.b.add(scroll);
            });
            tuple.c.add(result);
            yield tuple;
         }
      };
   }

   private ItemStack spellContainerOf(ItemStack stack, ISpellContainer container) {
      container.save(stack);
      return stack;
   }

   public static record Tuple<A, B, C>(A a, B b, C c) {
   }

   static enum Type {
      Scroll_Upgrade,
      Item_Upgrade,
      Imbue,
      Affinity_Ring_Attune;
   }
}
