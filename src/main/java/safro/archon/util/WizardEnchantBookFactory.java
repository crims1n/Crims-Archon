package safro.archon.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import safro.archon.registry.MiscRegistry;

public class WizardEnchantBookFactory implements TradeOffers.Factory {
    private final int experience;

    public WizardEnchantBookFactory(int exp) {
        this.experience = exp;
    }

    public TradeOffer create(Entity entity, Random random) {
        Enchantment enchantment = MiscRegistry.ARCANE;

        int arcaneEnchantLevel = 1 + random.nextInt(3);
        int emeraldCost = (5 + random.nextInt(11)) * arcaneEnchantLevel;
        ItemStack itemStack = EnchantedBookItem
                .forEnchantment(new EnchantmentLevelEntry(enchantment, arcaneEnchantLevel));

        return new TradeOffer(new ItemStack(Items.EMERALD, emeraldCost), new ItemStack(Items.BOOK), itemStack, 12,
                this.experience, 0.2F);
    }
}
