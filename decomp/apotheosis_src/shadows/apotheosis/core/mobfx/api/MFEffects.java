package shadows.apotheosis.core.mobfx.api;

import net.minecraftforge.registries.RegistryObject;
import shadows.apotheosis.core.mobfx.MobFxLib;
import shadows.apotheosis.core.mobfx.potions.BleedingEffect;
import shadows.apotheosis.core.mobfx.potions.GrievousEffect;
import shadows.apotheosis.core.mobfx.potions.KnowledgeEffect;
import shadows.apotheosis.core.mobfx.potions.SunderingEffect;
import shadows.apotheosis.core.mobfx.potions.VitalityEffect;

public class MFEffects {
   public static final RegistryObject<SunderingEffect> SUNDERING = MobFxLib.REG_OBJS.effect("sundering");
   public static final RegistryObject<KnowledgeEffect> KNOWLEDGE = MobFxLib.REG_OBJS.effect("knowledge");
   public static final RegistryObject<VitalityEffect> VITALITY = MobFxLib.REG_OBJS.effect("vitality");
   public static final RegistryObject<GrievousEffect> GRIEVOUS = MobFxLib.REG_OBJS.effect("grievous");
   public static final RegistryObject<BleedingEffect> BLEEDING = MobFxLib.REG_OBJS.effect("bleeding");
}
