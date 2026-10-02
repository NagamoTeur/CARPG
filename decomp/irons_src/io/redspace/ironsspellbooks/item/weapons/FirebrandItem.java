package io.redspace.ironsspellbooks.item.weapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Map;
import java.util.UUID;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.Vec3;

public class FirebrandItem extends ExtendedSwordItem {
   public FirebrandItem() {
      super(
         ExtendedWeaponTiers.KEEPER_FLAMBERGE,
         10.0,
         -1.8,
         Map.of(
            (Attribute)AttributeRegistry.FIRE_SPELL_POWER.get(),
            new AttributeModifier(UUID.fromString("c552273e-6669-4cd2-80b3-a703b7616336"), "weapon mod", 0.1, Operation.MULTIPLY_BASE)
         ),
         ItemPropertiesHelper.equipment().m_41487_(1).m_41497_(Rarity.UNCOMMON)
      );
   }

   public boolean m_7579_(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
      Vec3 center = pAttacker.m_146892_().m_82549_(pAttacker.m_20156_().m_82490_(2.5));

      for (int i = 0; i < 25; i++) {
         MagicManager.spawnParticles(
            pAttacker.f_19853_,
            ParticleHelper.EMBERS,
            center.f_82479_ + (double)(((float)i - 12.5F) * 0.1F * Mth.m_14089_(pAttacker.m_146908_() * (float) (Math.PI / 180.0))),
            center.f_82480_ + (double)Mth.m_14031_(pAttacker.m_146909_() * (float) (Math.PI / 180.0)),
            center.f_82481_
               + (double)(((float)i - 12.5F) * 0.1F * Mth.m_14031_(pAttacker.m_146908_() * (float) (Math.PI / 180.0)))
               + (double)(Mth.m_14031_(Mth.m_14179_((float)i / 25.0F, 0.0F, (float) Math.PI)) * -0.85F),
            1,
            0.0,
            0.0,
            0.0,
            0.08,
            false
         );
      }

      return super.m_7579_(pStack, pTarget, pAttacker);
   }
}
