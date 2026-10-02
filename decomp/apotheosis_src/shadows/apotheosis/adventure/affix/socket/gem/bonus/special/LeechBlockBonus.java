package shadows.apotheosis.adventure.affix.socket.gem.bonus.special;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;

public class LeechBlockBonus extends GemBonus {
   public static Codec<LeechBlockBonus> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(LootRarity.mapCodec(LeechBlockBonus.Data.CODEC).fieldOf("values").forGetter(a -> a.values)).apply(inst, LeechBlockBonus::new)
   );
   protected final Map<LootRarity, LeechBlockBonus.Data> values;

   public LeechBlockBonus(Map<LootRarity, LeechBlockBonus.Data> values) {
      super(Apotheosis.loc("leech_block"), new GemClass("shield", ImmutableSet.of(LootCategory.SHIELD)));
      this.values = values;
   }

   @Override
   public float onShieldBlock(ItemStack gem, LootRarity rarity, LivingEntity entity, DamageSource source, float amount) {
      LeechBlockBonus.Data d = this.values.get(rarity);
      if (!(amount <= 2.0F) && !Affix.isOnCooldown(this.getCooldownId(gem), d.cooldown, entity)) {
         entity.m_5634_(amount * d.healFactor);
         Affix.startCooldown(this.getCooldownId(gem), entity);
         return amount;
      } else {
         return amount;
      }
   }

   public Codec<? extends GemBonus> getCodec() {
      return CODEC;
   }

   @Override
   public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
      LeechBlockBonus.Data d = this.values.get(rarity);
      Component cooldown = Component.m_237110_("affix.apotheosis.cooldown", new Object[]{StringUtil.m_14404_(d.cooldown)});
      return Component.m_237110_("bonus." + this.getId() + ".desc", new Object[]{Affix.fmt(d.healFactor * 100.0F), cooldown}).m_130940_(ChatFormatting.YELLOW);
   }

   public LeechBlockBonus validate() {
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

   static record Data(float healFactor, int cooldown) {
      public static final Codec<LeechBlockBonus.Data> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.FLOAT.fieldOf("heal_factor").forGetter(LeechBlockBonus.Data::healFactor),
                  Codec.INT.fieldOf("cooldown").forGetter(LeechBlockBonus.Data::cooldown)
               )
               .apply(inst, LeechBlockBonus.Data::new)
      );
   }
}
