package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class SpectralShotAffix extends Affix {
   public static final Codec<SpectralShotAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(GemBonus.VALUES_CODEC.fieldOf("values").forGetter(a -> a.values)).apply(inst, SpectralShotAffix::new)
   );
   public static final PSerializer<SpectralShotAffix> SERIALIZER = PSerializer.fromCodec("Spectral Shot Affix", CODEC);
   protected final Map<LootRarity, StepFunction> values;

   public SpectralShotAffix(Map<LootRarity, StepFunction> values) {
      super(AffixType.ABILITY);
      this.values = values;
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat.isRanged() && this.values.containsKey(rarity);
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      list.accept(
         Component.m_237110_("affix." + this.getId() + ".desc", new Object[]{ItemStack.f_41584_.format((double)(100.0F * this.getTrueLevel(rarity, level)))})
      );
   }

   @Override
   public void onArrowFired(ItemStack stack, LootRarity rarity, float level, LivingEntity user, AbstractArrow arrow) {
      if (user.f_19853_.f_46441_.m_188501_() <= this.getTrueLevel(rarity, level) && !user.f_19853_.f_46443_) {
         ArrowItem arrowitem = (ArrowItem)Items.f_42737_;
         AbstractArrow spectralArrow = arrowitem.m_6394_(user.f_19853_, ItemStack.f_41583_, user);
         spectralArrow.m_6686_((double)user.m_146909_(), (double)user.m_146908_(), 0.0, 2.0F, 1.0F);
         this.cloneMotion(arrow, spectralArrow);
         spectralArrow.m_36762_(arrow.m_36792_());
         spectralArrow.m_36781_(arrow.m_36789_());
         spectralArrow.m_36735_(arrow.f_36699_);
         spectralArrow.m_7311_(arrow.m_20094_());
         spectralArrow.f_36705_ = Pickup.CREATIVE_ONLY;
         arrow.f_19853_.m_7967_(spectralArrow);
      }
   }

   private void cloneMotion(AbstractArrow src, AbstractArrow dest) {
      dest.m_20256_(src.m_20184_().m_82490_(1.0));
      dest.m_146922_(src.m_146908_());
      dest.m_146926_(src.m_146909_());
      dest.f_19859_ = dest.f_19859_;
      dest.f_19860_ = dest.f_19860_;
   }

   private float getTrueLevel(LootRarity rarity, float level) {
      return this.values.get(rarity).get(level);
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }
}
