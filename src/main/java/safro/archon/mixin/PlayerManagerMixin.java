package safro.archon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import safro.archon.api.ManaAttributes;
import safro.archon.util.ArchonUtil;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "respawnPlayer", at = @At("TAIL"))
    public void removeScroll(ServerPlayerEntity _player, boolean alive,
            CallbackInfoReturnable<ServerPlayerEntity> info) {
        if (!alive) {
            ServerPlayerEntity player = info.getReturnValue();
            String name = ArchonUtil.getScroll(player);
            if (ArchonUtil.hasScroll(player)) {
                if (name.equals("capacity")) {
                    ArchonUtil.get(player).removeMaxModifier(ManaAttributes.CAPACITY_SCROLL_MODIFIER);
                    ArchonUtil.get(player).clampMana();
                } else if (name.equals("accelerate")) {
                    ArchonUtil.get(player).setRegenSpeed(20);
                }
                ArchonUtil.setScroll(player, "none");
            }
        }
    }
}
