package mod.acgaming.universaltweaks.tweaks.items.repairing.mixin;

import net.minecraft.item.Item;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class UTCraftingRepairMixin
{
    @Inject(method = "isRepairable", at = @At("RETURN"), cancellable = true, remap = false)
    public void utCraftingRepair(CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfig.TWEAKS_ITEMS.utCraftingRepairToggle)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCraftingRepair ::: Is repairable check");
            cir.setReturnValue(false);
        }
    }
}