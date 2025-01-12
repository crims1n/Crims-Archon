package safro.archon.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import safro.archon.entity.projectile.spell.HitExecutor;
import safro.archon.entity.projectile.spell.SpellProjectileEntity;

import javax.annotation.Nullable;

public class SpellUtil {

    public static SpellProjectileEntity create(World world, PlayerEntity player, ItemConvertible item, HitExecutor executor) {
        return create(world, player, item, executor, 2.0F);
    }

    public static SpellProjectileEntity create(World world, PlayerEntity player, ItemConvertible item, HitExecutor hitExecutor, float speed) {
        Vec3d vec3d = player.getRotationVec(0.0F);
        double vX = (player.getX() + vec3d.x * 4.0D) - player.getX();
        double vY = (player.getY() + vec3d.y * 4.0D) - player.getY();
        double vZ = (player.getZ() + vec3d.z * 4.0D) - player.getZ();
        return spawn(world, player, new SpellProjectileEntity(world, player, vX, vY, vZ, hitExecutor, new ItemStack(item)), speed);
    }

    public static SpellProjectileEntity spawn(World world, PlayerEntity player, SpellProjectileEntity projectile, float speed) {
        projectile.updatePosition(player.getX(), player.getEyeY(), player.getZ());
        projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, speed, 1.0F);

        world.spawnEntity(projectile);
        return projectile;
    }

    @Nullable
    public static Entity getTargeted(PlayerEntity player, double range) {
        double e = range * range;
        Vec3d vec = player.getCameraPosVec(1.0F);
        Vec3d vec3d2 = player.getRotationVec(1.0F);
        Vec3d vec3d3 = vec.add(vec3d2.x * range, vec3d2.y * range, vec3d2.z * range);
        Box box = player.getBoundingBox().stretch(vec3d2.multiply(range)).expand(1.0D, 1.0D, 1.0D);

        EntityHitResult hit = ProjectileUtil.raycast(player, vec, vec3d3, box, (entityx) -> !entityx.isSpectator() && entityx.canHit(), e);
        if (hit == null || hit.getEntity() == null) {
            return null;
        }
        return hit.getEntity();
    }
}
