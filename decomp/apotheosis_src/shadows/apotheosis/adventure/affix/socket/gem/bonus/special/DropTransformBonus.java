package shadows.apotheosis.adventure.affix.socket.gem.bonus.special;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.codec.PlaceboCodecs;
import shadows.placebo.codec.PlaceboCodecs.IngredientCodec;
import shadows.placebo.json.ItemAdapter;
import shadows.placebo.util.StepFunction;

public class DropTransformBonus extends GemBonus {
   public static Codec<DropTransformBonus> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               gemClass(),
               PlaceboCodecs.nullableField(TagKey.m_203877_(Registry.f_122901_), "blocks").forGetter(a -> a.tag),
               IngredientCodec.INSTANCE.fieldOf("inputs").forGetter(a -> a.inputs),
               ItemAdapter.CODEC.fieldOf("output").forGetter(a -> a.output),
               VALUES_CODEC.fieldOf("values").forGetter(a -> a.values),
               Codec.STRING.fieldOf("desc").forGetter(a -> a.descKey)
            )
            .apply(inst, DropTransformBonus::new)
   );
   protected final Optional<TagKey<Block>> tag;
   protected final Ingredient inputs;
   protected final ItemStack output;
   protected final Map<LootRarity, StepFunction> values;
   protected final String descKey;

   public DropTransformBonus(
      GemClass gemClass, Optional<TagKey<Block>> tag, Ingredient inputs, ItemStack output, Map<LootRarity, StepFunction> values, String descKey
   ) {
      super(Apotheosis.loc("drop_transform"), gemClass);
      this.tag = tag;
      this.inputs = inputs;
      this.output = output;
      this.values = values;
      this.descKey = descKey;
   }

   public Codec<? extends GemBonus> getCodec() {
      return CODEC;
   }

   @Override
   public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
      float chance = this.values.get(rarity).min();
      return Component.m_237110_(this.descKey, new Object[]{Affix.fmt(chance * 100.0F)}).m_130940_(ChatFormatting.YELLOW);
   }

   @Override
   public void modifyLoot(ItemStack gem, LootRarity rarity, ObjectArrayList<ItemStack> loot, LootContext ctx) {
      if (ctx.m_78936_(LootContextParams.f_81461_)) {
         BlockState state = (BlockState)ctx.m_165124_(LootContextParams.f_81461_);
         if (this.tag.isPresent() && !state.m_204336_(this.tag.get())) {
            return;
         }

         if (ctx.m_230907_().m_188501_() <= this.values.get(rarity).min()) {
            for (int i = 0; i < loot.size(); i++) {
               ItemStack stack = (ItemStack)loot.get(i);
               if (this.inputs.test(stack)) {
                  ItemStack outCopy = this.output.m_41777_();
                  outCopy.m_41764_(stack.m_41613_());
                  loot.set(i, outCopy);
               }
            }
         }
      }
   }

   public DropTransformBonus validate() {
      Preconditions.checkNotNull(this.values);
      this.values.forEach((k, v) -> {
         Preconditions.checkNotNull(k);
         Preconditions.checkNotNull(v);
      });
      return this;
   }

   @Override
   public boolean supports(LootRarity rarity) {
      return this.values.containsKey(rarity);
   }

   @Override
   public int getNumberOfUUIDs() {
      return 0;
   }
}
