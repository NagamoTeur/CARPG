package io.redspace.ironsspellbooks.entity.mobs.wizards.priest;

import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobModel;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class PriestModel extends AbstractSpellCastingMobModel {
   public static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/priest/priest.png");
   public static final ResourceLocation TEXTURE_ARMOR = new ResourceLocation("irons_spellbooks", "textures/entity/priest/priest_armored.png");
   public static final ResourceLocation MODEL = new ResourceLocation("irons_spellbooks", "geo/archevoker.geo.json");

   @Override
   public ResourceLocation getModelResource(AbstractSpellCastingMob object) {
      return MODEL;
   }

   @Override
   public ResourceLocation getTextureResource(AbstractSpellCastingMob object) {
      return object.m_6844_(EquipmentSlot.HEAD).m_150930_((Item)ItemRegistry.PRIEST_HELMET.get()) ? TEXTURE_ARMOR : TEXTURE;
   }

   @Override
   public void setCustomAnimations(AbstractSpellCastingMob entity, int instanceId, AnimationEvent animationEvent) {
      super.setCustomAnimations(entity, instanceId, animationEvent);
      if (entity instanceof PriestEntity priest && priest.isUnhappy()) {
         if (Minecraft.m_91087_().m_91104_() || !entity.shouldBeExtraAnimated()) {
            return;
         }

         IBone head = this.getAnimationProcessor().getBone("head");
         head.setRotationZ(0.3F * Mth.m_14031_(0.45F * ((float)entity.f_19797_ + animationEvent.getPartialTick())));
         head.setRotationX(-0.4F);
      }
   }
}
