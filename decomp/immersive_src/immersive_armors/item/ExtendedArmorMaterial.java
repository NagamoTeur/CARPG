package immersive_armors.item;

import immersive_armors.Main;
import immersive_armors.armorEffects.ArmorEffect;
import immersive_armors.client.render.entity.piece.Piece;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;

public class ExtendedArmorMaterial implements ArmorMaterial {
   private final String name;
   private int durabilityMultiplier;
   private int[] protectionAmount;
   private final boolean[] hidesSecondLayer = new boolean[]{false, false, false, false};
   private float toughness;
   private float knockbackResistance;
   private int enchantability;
   private float weight;
   private int extraHealth;
   private int color = 10511680;
   private float attackDamage;
   private float attackSpeed;
   private int luck;
   private final List<ArmorEffect> effects = new LinkedList<>();
   private final Map<Enchantment, Integer> enchantments = new HashMap<>();
   private final Map<String, Float> loot = new HashMap<>();
   private boolean antiSkeleton;
   private final Map<EquipmentSlot, List<Piece>> pieces = new HashMap<>();
   private boolean hideCape;
   private SoundEvent equipSound;
   private Supplier<Ingredient> repairIngredient;
   private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

   public ExtendedArmorMaterial(String name) {
      this.pieces.put(EquipmentSlot.HEAD, new LinkedList<>());
      this.pieces.put(EquipmentSlot.CHEST, new LinkedList<>());
      this.pieces.put(EquipmentSlot.LEGS, new LinkedList<>());
      this.pieces.put(EquipmentSlot.FEET, new LinkedList<>());
      this.name = name;
      this.protectionAmount(0, 0, 0, 0);
   }

   public ExtendedArmorMaterial durabilityMultiplier(int durabilityMultiplier) {
      this.durabilityMultiplier = durabilityMultiplier;
      return this;
   }

   public ExtendedArmorMaterial protectionAmount(int helmet, int chestplate, int legging, int boots) {
      this.protectionAmount = new int[]{boots, legging, chestplate, helmet};
      return this;
   }

   public ExtendedArmorMaterial toughness(float toughness) {
      this.toughness = toughness;
      return this;
   }

   public ExtendedArmorMaterial enchantability(int enchantability) {
      this.enchantability = enchantability;
      return this;
   }

   public ExtendedArmorMaterial equipSound(SoundEvent equipSound) {
      this.equipSound = equipSound;
      return this;
   }

   public ExtendedArmorMaterial repairIngredient(Supplier<Ingredient> repairIngredient) {
      this.repairIngredient = repairIngredient;
      return this;
   }

   public ExtendedArmorMaterial knockbackReduction(float knockbackReduction) {
      this.knockbackResistance = knockbackReduction;
      return this;
   }

   public ExtendedArmorMaterial weight(float weight) {
      this.weight = weight;
      return this;
   }

   public ExtendedArmorMaterial extraHealth(int extraHealth) {
      this.extraHealth = extraHealth;
      return this;
   }

   public ExtendedArmorMaterial color(int color) {
      this.color = color;
      return this;
   }

   public ExtendedArmorMaterial attackDamage(int attackDamage) {
      this.attackDamage = (float)attackDamage;
      return this;
   }

   public ExtendedArmorMaterial attackSpeed(int attackSpeed) {
      this.attackSpeed = (float)attackSpeed;
      return this;
   }

   public ExtendedArmorMaterial luck(int luck) {
      this.luck = luck;
      return this;
   }

   public ExtendedArmorMaterial effect(ArmorEffect effect) {
      this.effects.add(effect);
      return this;
   }

   public ExtendedArmorMaterial enchantment(Enchantment enchantment, int level) {
      this.enchantments.put(enchantment, level);
      return this;
   }

   public ExtendedArmorMaterial antiSkeleton() {
      this.antiSkeleton = true;
      return this;
   }

   public ExtendedArmorMaterial hideCape() {
      this.hideCape = true;
      return this;
   }

   public ExtendedArmorMaterial head(Piece pieceSupplier) {
      this.pieces.get(EquipmentSlot.HEAD).add(pieceSupplier);
      return this;
   }

   public ExtendedArmorMaterial chest(Piece pieceSupplier) {
      this.pieces.get(EquipmentSlot.CHEST).add(pieceSupplier);
      return this;
   }

   public ExtendedArmorMaterial legs(Piece pieceSupplier) {
      this.pieces.get(EquipmentSlot.LEGS).add(pieceSupplier);
      return this;
   }

   public ExtendedArmorMaterial feet(Piece pieceSupplier) {
      this.pieces.get(EquipmentSlot.FEET).add(pieceSupplier);
      return this;
   }

   public ExtendedArmorMaterial upper(Piece pieceSupplier) {
      this.head(pieceSupplier);
      this.chest(pieceSupplier);
      this.feet(pieceSupplier);
      return this;
   }

   public ExtendedArmorMaterial lower(Piece pieceSupplier) {
      this.legs(pieceSupplier);
      return this;
   }

   public ExtendedArmorMaterial full(Piece pieceSupplier) {
      this.upper(pieceSupplier);
      this.lower(pieceSupplier);
      return this;
   }

   public ExtendedArmorMaterial addLoot(String name, float chance) {
      this.loot.put(name, chance);
      return this;
   }

   public String m_6082_() {
      return this.name;
   }

   public int m_7366_(EquipmentSlot slot) {
      return BASE_DURABILITY[slot.m_20749_()] * this.durabilityMultiplier;
   }

   public int m_7365_(EquipmentSlot slot) {
      return this.protectionAmount[slot.m_20749_()];
   }

   public float m_6651_() {
      return this.toughness;
   }

   public int m_6646_() {
      return this.enchantability;
   }

   public SoundEvent m_7344_() {
      return this.equipSound;
   }

   public Ingredient m_6230_() {
      return this.repairIngredient.get();
   }

   public float m_6649_() {
      return this.knockbackResistance;
   }

   public float getWeight() {
      return this.weight;
   }

   public int getExtraHealth() {
      return this.extraHealth;
   }

   public int getColor() {
      return this.color;
   }

   public float getAttackDamage() {
      return this.attackDamage;
   }

   public float getAttackSpeed() {
      return this.attackSpeed;
   }

   public int getLuck() {
      return this.luck;
   }

   public List<ArmorEffect> getEffects() {
      return Main.sharedConfig.enableEffects ? this.effects : Collections.emptyList();
   }

   public Map<Enchantment, Integer> getEnchantments() {
      return this.enchantments;
   }

   public boolean hasEnchantment(Enchantment enchantment) {
      return this.enchantments.containsKey(enchantment);
   }

   public int getEnchantment(Enchantment enchantment) {
      return this.enchantments.get(enchantment);
   }

   public boolean shouldHideCape() {
      return this.hideCape;
   }

   public List<Piece> getPieces(EquipmentSlot slot) {
      return this.pieces.get(slot);
   }

   public ExtendedArmorMaterial hidesSecondLayer(boolean head, boolean chest, boolean legs, boolean feet) {
      this.hidesSecondLayer[0] = head;
      this.hidesSecondLayer[1] = chest;
      this.hidesSecondLayer[2] = legs;
      this.hidesSecondLayer[3] = feet;
      return this;
   }

   public boolean[] shouldHideSecondLayer() {
      return this.hidesSecondLayer;
   }

   public boolean isAntiSkeleton() {
      return this.antiSkeleton;
   }

   public int[] getProtectionAmounts() {
      return this.protectionAmount;
   }

   public int getDurabilityMultiplier() {
      return this.durabilityMultiplier;
   }

   public Map<String, Float> getLoot() {
      return this.loot;
   }
}
