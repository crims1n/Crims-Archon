package safro.archon.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import safro.archon.item.ManaWeapon;
import safro.archon.item.WandItem;
import safro.archon.registry.MiscRegistry;
import safro.archon.util.ArchonUtil;

public class ArcaneEnchantment extends Enchantment {

    public ArcaneEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    public int getMinPower(int level) {
        return level * 25;
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 50;
    }

    public boolean isTreasure() {
        return true;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ManaWeapon || stack.getItem() instanceof WandItem;
    }

    /**
     * Checks for the arcane enchantment and removes mana accordingly. If arcane is
     * not present, the normal mana amount will be removed.
     * 
     * @param player   Player using the item
     * @param stack    Stack of the item
     * @param manaCost Mana to be removed
     */
    public static void applyArcane(PlayerEntity player, ItemStack stack, int manaCost) {
        int level = EnchantmentHelper.getLevel(MiscRegistry.ARCANE, stack);
        if (level >= 1) {
            double raw = manaCost * (0.1 * level);
            int removed = (int) Math.round(raw);
            ArchonUtil.get(player).removeMana(manaCost - removed);
        } else {
            ArchonUtil.get(player).removeMana(manaCost);
        }
    }

    public static int getNewManaCost(ItemStack stack, int manaCost) {
        int level = EnchantmentHelper.getLevel(MiscRegistry.ARCANE, stack);
        if (level >= 1) {
            double raw = manaCost * (0.1 * level);
            int removed = (int) Math.round(raw);
            return manaCost - removed;
        } else {
            return manaCost;
        }
    }
}
