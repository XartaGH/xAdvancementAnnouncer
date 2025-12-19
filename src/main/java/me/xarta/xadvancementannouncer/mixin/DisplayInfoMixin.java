package me.xarta.xadvancementannouncer.mixin;

import net.minecraft.advancements.DisplayInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DisplayInfo.class)
public class DisplayInfoMixin {
    @Inject(method = "shouldAnnounceChat", at = @At("HEAD"), cancellable = true)
    private void disableVanillaChat(CallbackInfoReturnable<Boolean> cir) {
        // Always return false to prevent vanilla announcements
        // Our event handler will send custom messages instead
        cir.setReturnValue(false);
    }
}