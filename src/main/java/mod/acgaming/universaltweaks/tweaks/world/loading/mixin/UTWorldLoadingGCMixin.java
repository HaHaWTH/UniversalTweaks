package mod.acgaming.universaltweaks.tweaks.world.loading.mixin;

import net.minecraft.client.Minecraft;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of Sk1erLLC
@Mixin(Minecraft.class)
public class UTWorldLoadingGCMixin
{
    @WrapWithCondition(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Ljava/lang/System;gc()V"))
    public boolean utWorldLoadingGC()
    {
        return !UTConfig.TWEAKS_PERFORMANCE.utWorldLoadingToggle;
    }
}