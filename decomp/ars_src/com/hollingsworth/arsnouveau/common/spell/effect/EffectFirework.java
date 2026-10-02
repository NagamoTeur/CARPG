package com.hollingsworth.arsnouveau.common.spell.effect;

import com.google.common.collect.Lists;
import com.hollingsworth.arsnouveau.api.item.inv.InteractType;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.item.inv.SlotReference;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.FireworkRocketItem.Shape;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class EffectFirework extends AbstractEffect implements IDamageEffect {
   public static EffectFirework INSTANCE = new EffectFirework();
   public static Shape[] shapes = Shape.values();
   private static List<DyeColor> dyes;

   public EffectFirework() {
      super(GlyphLib.EffectFireworkID, "Firework");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity) {
         ItemStack firework = this.fireworkFromInv(spellContext, spellStats, shooter);

         for (int i = 0; i < spellStats.getBuffCount(AugmentSplit.INSTANCE) + 1; i++) {
            this.spawnFireworkOnEntity(rayTraceResult, world, shooter, firework);
         }
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      ItemStack firework = this.fireworkFromInv(spellContext, spellStats, shooter);

      for (int i = 0; i < spellStats.getBuffCount(AugmentSplit.INSTANCE) + 1; i++) {
         this.spawnFireworkOnBlock(rayTraceResult, world, shooter, i, firework, spellContext);
      }
   }

   public ItemStack fireworkFromInv(SpellContext spellContext, SpellStats spellStats, LivingEntity shooter) {
      InventoryManager manager = spellContext.getCaster().getInvManager();
      SlotReference slotReference = manager.findItem(i -> i.m_41720_() == Items.f_42688_, InteractType.EXTRACT);
      if (slotReference.getHandler() != null) {
         ItemStack firework = slotReference.getHandler().getStackInSlot(slotReference.getSlot());
         if (!firework.m_41619_()) {
            return firework;
         }
      }

      return getFirework((int)spellStats.getDurationMultiplier(), (int)spellStats.getAmpMultiplier());
   }

   public void spawnFireworkOnBlock(BlockHitResult rayTraceResult, Level world, LivingEntity shooter, int i, ItemStack fireworkStack, SpellContext context) {
      FireworkRocketEntity fireworkrocketentity;
      if (context.getType() == SpellContext.CasterType.TURRET) {
         BlockPos pos = rayTraceResult.m_82425_();
         Direction direction = rayTraceResult.m_82434_().m_122424_();
         fireworkrocketentity = new FireworkRocketEntity(
            world, fireworkStack, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, true
         );
         fireworkrocketentity.m_6686_((double)direction.m_122429_(), (double)direction.m_122430_(), (double)direction.m_122431_(), 0.5F, 1.0F);
      } else {
         BlockPos pos = rayTraceResult.m_82425_().m_121945_(rayTraceResult.m_82434_());
         fireworkrocketentity = new FireworkRocketEntity(
            world,
            shooter,
            (double)pos.m_123341_() + 0.5 + (double)i * ParticleUtil.inRange(-0.3, 0.3),
            (double)pos.m_123342_() + 0.5,
            (double)pos.m_123343_() + 0.5 + (double)i * ParticleUtil.inRange(-0.3, 0.3),
            fireworkStack
         );
      }

      world.m_7967_(fireworkrocketentity);
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   public void spawnFireworkOnEntity(EntityHitResult rayTraceResult, Level world, LivingEntity shooter, ItemStack firework) {
      FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(world, firework, (LivingEntity)rayTraceResult.m_82443_());
      fireworkrocketentity.m_5602_(shooter);
      world.m_7967_(fireworkrocketentity);
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @Override
   public String getBookDescription() {
      return "Creates a firework at the location or entity. Amplify will add Firework Stars, while Extend Time will add additional flight time. If a firework exists in the casters inventory, the created firework will mimic the held one. Spell Turrets with Touch will create fireworks as if they were dispensed.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_FIRE});
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE, AugmentAmplify.INSTANCE, AugmentSplit.INSTANCE});
   }

   public static List<DyeColor> getColorfulDyes() {
      if (dyes == null) {
         dyes = Arrays.stream(DyeColor.values())
            .filter(d -> d != DyeColor.BLACK && d != DyeColor.GRAY && d != DyeColor.LIGHT_GRAY && d != DyeColor.BROWN)
            .collect(Collectors.toList());
      }

      return dyes;
   }

   public static ItemStack getFirework(int numGunpowder, int numStars) {
      ItemStack stack = new ItemStack(Items.f_42688_);
      CompoundTag rocketTag = stack.m_41698_("Fireworks");
      rocketTag.m_128344_("Flight", (byte)numGunpowder);
      ListTag listnbt = new ListTag();

      for (int i = 0; i < numStars; i++) {
         listnbt.add(getRandomStar().m_41737_("Explosion"));
      }

      if (!listnbt.isEmpty()) {
         rocketTag.m_128365_("Explosions", listnbt);
      }

      return stack;
   }

   public static ItemStack getRandomStar() {
      ItemStack star = new ItemStack(Items.f_42689_);
      CompoundTag starTag = star.m_41698_("Explosion");
      Random random = new Random();
      Shape fireworkrocketitem$shape = shapes[random.nextInt(shapes.length)];
      List<Integer> list = Lists.newArrayList();

      for (int i = 0; i < random.nextInt(8); i++) {
         list.add(getColorfulDyes().get(random.nextInt(getColorfulDyes().size())).m_41070_());
      }

      starTag.m_128379_("Flicker", random.nextBoolean());
      starTag.m_128379_("Trail", random.nextBoolean());
      starTag.m_128408_("Colors", list);
      starTag.m_128344_("Type", (byte)fireworkrocketitem$shape.m_41236_());
      return star;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }
}
