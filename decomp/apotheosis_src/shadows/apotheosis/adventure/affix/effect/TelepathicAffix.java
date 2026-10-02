package shadows.apotheosis.adventure.affix.effect;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;

public class TelepathicAffix extends Affix {
   public static final Codec<TelepathicAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(LootRarity.CODEC.fieldOf("min_rarity").forGetter(a -> a.minRarity)).apply(inst, TelepathicAffix::new)
   );
   public static final PSerializer<TelepathicAffix> SERIALIZER = PSerializer.fromCodec("Telepathic Affix", CODEC);
   public static Vec3 blockDropTargetPos = null;
   protected LootRarity minRarity;

   public TelepathicAffix(LootRarity minRarity) {
      super(AffixType.ABILITY);
      this.minRarity = minRarity;
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return (cat.isRanged() || cat.isLightWeapon() || cat.isBreaker()) && rarity.isAtLeast(this.minRarity);
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      LootCategory cat = LootCategory.forItem(stack);
      String type = !cat.isRanged() && !cat.isWeapon() ? "tool" : "weapon";
      list.accept(Component.m_237115_("affix." + this.getId() + ".desc." + type));
   }

   @Override
   public boolean enablesTelepathy() {
      return true;
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }

   public static void drops(LivingDropsEvent e) {
      boolean canTeleport;
      Vec3 targetPos;
      label25: {
         DamageSource src = e.getSource();
         canTeleport = false;
         targetPos = null;
         if (src.m_7640_() instanceof AbstractArrow arrow && arrow.m_37282_() != null) {
            canTeleport = AffixHelper.streamAffixes(arrow).anyMatch(AffixInstance::enablesTelepathy);
            targetPos = arrow.m_37282_().m_20182_();
            break label25;
         }

         if (src.m_7640_() instanceof LivingEntity living) {
            ItemStack weapon = living.m_21205_();
            canTeleport = AffixHelper.streamAffixes(weapon).anyMatch(AffixInstance::enablesTelepathy);
            targetPos = living.m_20182_();
         }
      }

      if (canTeleport) {
         for (ItemEntity item : e.getDrops()) {
            item.m_6034_(targetPos.f_82479_, targetPos.f_82480_, targetPos.f_82481_);
            item.m_32010_(0);
         }
      }
   }

   public static Affix read(JsonObject obj) {
      return new TelepathicAffix((LootRarity)GSON.fromJson(obj.get("min_rarity"), LootRarity.class));
   }

   public JsonObject write() {
      return new JsonObject();
   }

   public void write(FriendlyByteBuf buf) {
      buf.m_130070_(this.minRarity.id());
   }

   public static Affix read(FriendlyByteBuf buf) {
      return new TelepathicAffix(LootRarity.byId(buf.m_130277_()));
   }
}
