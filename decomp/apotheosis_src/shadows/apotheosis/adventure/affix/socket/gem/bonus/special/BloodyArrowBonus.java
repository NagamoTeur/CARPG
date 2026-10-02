package shadows.apotheosis.adventure.affix.socket.gem.bonus.special;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;

public class BloodyArrowBonus extends GemBonus {
   public static Codec<BloodyArrowBonus> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(LootRarity.mapCodec(BloodyArrowBonus.Data.CODEC).fieldOf("values").forGetter(a -> a.values)).apply(inst, BloodyArrowBonus::new)
   );
   protected final Map<LootRarity, BloodyArrowBonus.Data> values;

   public BloodyArrowBonus(Map<LootRarity, BloodyArrowBonus.Data> values) {
      super(Apotheosis.loc("bloody_arrow"), new GemClass("ranged_weapon", ImmutableSet.of(LootCategory.BOW, LootCategory.CROSSBOW)));
      this.values = values;
   }

   @Override
   public void onArrowFired(ItemStack gem, LootRarity rarity, LivingEntity user, AbstractArrow arrow) {
      BloodyArrowBonus.Data d = this.values.get(rarity);
      if (!Affix.isOnCooldown(this.getCooldownId(gem), d.cooldown, user)) {
         user.m_6469_(Apotheosis.CORRUPTED, user.m_21233_() * d.healthCost);
         arrow.m_36781_(arrow.m_36789_() * (double)d.dmgMultiplier);
         Affix.startCooldown(this.getCooldownId(gem), user);
      }
   }

   public Codec<? extends GemBonus> getCodec() {
      return CODEC;
   }

   @Override
   public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
      BloodyArrowBonus.Data d = this.values.get(rarity);
      Component cooldown = Component.m_237110_("affix.apotheosis.cooldown", new Object[]{StringUtil.m_14404_(d.cooldown)});
      return Component.m_237110_(
            "bonus." + this.getId() + ".desc", new Object[]{Affix.fmt(d.healthCost * 100.0F), Affix.fmt(100.0F * d.dmgMultiplier), cooldown}
         )
         .m_130940_(ChatFormatting.YELLOW);
   }

   public BloodyArrowBonus validate() {
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

   static record Data(float healthCost, float dmgMultiplier, int cooldown) {
      public static final Codec<BloodyArrowBonus.Data> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.FLOAT.fieldOf("health_cost").forGetter(BloodyArrowBonus.Data::healthCost),
                  Codec.FLOAT.fieldOf("damage_mult").forGetter(BloodyArrowBonus.Data::dmgMultiplier),
                  Codec.INT.fieldOf("cooldown").forGetter(BloodyArrowBonus.Data::cooldown)
               )
               .apply(inst, BloodyArrowBonus.Data::new)
      );
   }
}
