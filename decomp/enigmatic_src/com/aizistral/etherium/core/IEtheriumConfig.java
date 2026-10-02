package com.aizistral.etherium.core;

import com.aizistral.enigmaticlegacy.objects.Perhaps;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.ModList;

public interface IEtheriumConfig {
   Ingredient getRepairMaterial();

   CreativeModeTab getCreativeTab();

   String getOwnerMod();

   ArmorMaterial getArmorMaterial();

   Tier getToolMaterial();

   Perhaps getShieldThreshold(@Nullable Player var1);

   Perhaps getShieldReduction();

   boolean disableAOEShiftInhibition();

   SoundEvent getAOESoundOn();

   SoundEvent getAOESoundOff();

   SoundEvent getShieldTriggerSound();

   int getAxeMiningVolume();

   int getScytheMiningVolume();

   int getPickaxeMiningRadius();

   int getPickaxeMiningDepth();

   int getShovelMiningRadius();

   int getShovelMiningDepth();

   int getSwordCooldown();

   int getAOEBoost(@Nullable Player var1);

   void knockBack(LivingEntity var1, float var2, double var3, double var5);

   boolean isStandalone();

   default Optional<Material> getSorceryMaterial(String name) {
      if (ModList.get().isLoaded("astralsorcery")) {
         try {
            Class<?> sorceryBlockMaterials = Class.forName("hellfirepvp.astralsorcery.common.lib.MaterialsAS");
            Material material = (Material)sorceryBlockMaterials.getField(name).get(null);
            return Optional.ofNullable(material);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return Optional.empty();
   }
}
