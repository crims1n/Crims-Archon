package safro.archon.item.sky;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import safro.archon.item.ManaWeapon;
import safro.archon.util.LightningAccess;
import safro.archon.util.SpellUtil;
import net.minecraft.entity.Entity;

public class ThunderBoltItem extends ManaWeapon {

    public ThunderBoltItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public int getManaCost() {
        return 30;
    }

    @Override
    public boolean activate(World world, PlayerEntity player, ItemStack stack, Hand hand) {
        Entity target = SpellUtil.getTargeted(player, 30);
        if (target != null) {
            BlockPos north = target.getBlockPos().north();
            BlockPos south = target.getBlockPos().south();
            BlockPos east = target.getBlockPos().east();
            BlockPos west = target.getBlockPos().west();

            LightningEntity northE = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world);
            northE.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(north));
            ((LightningAccess) northE).setFireSpawning(false);
            world.spawnEntity(northE);

            LightningEntity southE = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world);
            ((LightningAccess) southE).setFireSpawning(false);
            southE.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(south));
            world.spawnEntity(southE);

            LightningEntity eastE = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world);
            ((LightningAccess) eastE).setFireSpawning(false);
            eastE.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(east));
            world.spawnEntity(eastE);

            LightningEntity westE = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world);
            ((LightningAccess) westE).setFireSpawning(false);
            westE.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(west));
            world.spawnEntity(westE);
            return true;
        }
        return false;
    }
}
