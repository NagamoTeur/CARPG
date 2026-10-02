package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class BananaLootModifier extends LootModifier {
   private static final Codec<BananaLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, BananaLootModifier::new));

   public BananaLootModifier() {
      super(new LootItemCondition[0]);
   }

   public BananaLootModifier(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      BlockState state = (BlockState)context.m_78953_(LootContextParams.f_81461_);
      if (AMConfig.bananasDropFromLeaves && state != null && state.m_204336_(AMTagRegistry.DROPS_BANANAS)) {
         ItemStack ctxTool = (ItemStack)context.m_78953_(LootContextParams.f_81463_);
         RandomSource random = context.m_230907_();
         if (ctxTool != null) {
            int silkTouch = EnchantmentHelper.m_44843_(Enchantments.f_44985_, ctxTool);
            if (silkTouch > 0 || ctxTool.m_41720_() instanceof ShearsItem) {
               return generatedLoot;
            }
         }

         int bonusLevel = ctxTool != null ? EnchantmentHelper.m_44843_(Enchantments.f_44987_, ctxTool) : 0;
         int bananaStep = (int)Math.min((float)AMConfig.bananaChance * 0.1F, 0.0F);
         int bananaRarity = AMConfig.bananaChance - bonusLevel * bananaStep;
         if (bananaRarity < 1 || random.m_188503_(bananaRarity) == 0) {
            generatedLoot.add(new ItemStack((ItemLike)AMItemRegistry.BANANA.get()));
         }
      }

      return generatedLoot;
   }

   public Codec<? extends IGlobalLootModifier> codec() {
      return CODEC;
   }

   public static Codec<BananaLootModifier> makeCodec() {
      return Codec.unit(BananaLootModifier::new);
   }
}
