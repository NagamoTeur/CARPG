package lykrast.meetyourfight.registry;

import lykrast.meetyourfight.MeetYourFight;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
   public static final DeferredRegister<SoundEvent> REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "meetyourfight");
   public static RegistryObject<SoundEvent> bellringerIdle = initSound("entity.bellringer.idle");
   public static RegistryObject<SoundEvent> bellringerHurt = initSound("entity.bellringer.hurt");
   public static RegistryObject<SoundEvent> bellringerDeath = initSound("entity.bellringer.death");
   public static RegistryObject<SoundEvent> dameFortunaIdle = initSound("entity.dame_fortuna.idle");
   public static RegistryObject<SoundEvent> dameFortunaHurt = initSound("entity.dame_fortuna.hurt");
   public static RegistryObject<SoundEvent> dameFortunaDeath = initSound("entity.dame_fortuna.death");
   public static RegistryObject<SoundEvent> dameFortunaAttack = initSound("entity.dame_fortuna.attack");
   public static RegistryObject<SoundEvent> dameFortunaShoot = initSound("entity.dame_fortuna.shoot");
   public static RegistryObject<SoundEvent> swampjawIdle = initSound("entity.swampjaw.idle");
   public static RegistryObject<SoundEvent> swampjawHurt = initSound("entity.swampjaw.hurt");
   public static RegistryObject<SoundEvent> swampjawDeath = initSound("entity.swampjaw.death");
   public static RegistryObject<SoundEvent> swampjawCharge = initSound("entity.swampjaw.charge");
   public static RegistryObject<SoundEvent> swampjawBomb = initSound("entity.swampjaw.bomb");
   public static RegistryObject<SoundEvent> rosalyneHurt = initSound("entity.rosalyne.hurt");
   public static RegistryObject<SoundEvent> rosalyneDeath = initSound("entity.rosalyne.death");
   public static RegistryObject<SoundEvent> rosalyneCrack = initSound("entity.rosalyne.crack");
   public static RegistryObject<SoundEvent> rosalyneSwing = initSound("entity.rosalyne.swing");
   public static RegistryObject<SoundEvent> rosalyneSwingPrepare = initSound("entity.rosalyne.swing.prepare");
   public static RegistryObject<SoundEvent> roseSpiritIdle = initSound("entity.rosespirit.idle");
   public static RegistryObject<SoundEvent> roseSpiritHurt = initSound("entity.rosespirit.hurt");
   public static RegistryObject<SoundEvent> roseSpiritHurtBig = initSound("entity.rosespirit.hurt.big");
   public static RegistryObject<SoundEvent> roseSpiritDeath = initSound("entity.rosespirit.death");
   public static RegistryObject<SoundEvent> roseSpiritWarn = initSound("entity.rosespirit.warn");
   public static RegistryObject<SoundEvent> roseSpiritShoot = initSound("entity.rosespirit.shoot");
   public static RegistryObject<SoundEvent> slicersDiceProc = initSound("item.proc.slicers_dice");
   public static RegistryObject<SoundEvent> aceOfIronProc = initSound("item.proc.ace_of_iron");
   public static RegistryObject<SoundEvent> cagedHeartProc = initSound("item.proc.caged_heart");
   public static RegistryObject<SoundEvent> musicMagnum = initSound("music.magnum");
   public static RegistryObject<SoundEvent> musicFrogPunch = initSound("music.frog_punch");

   public static RegistryObject<SoundEvent> initSound(String name) {
      return REG.register(name, () -> new SoundEvent(MeetYourFight.rl(name)));
   }
}
