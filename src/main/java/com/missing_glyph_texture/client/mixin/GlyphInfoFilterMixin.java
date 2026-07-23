/*
 * Copyright (c) 2026 reizou00
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.missing_glyph_texture.client.mixin;

import com.missing_glyph_texture.client.MissingGlyph;
import com.mojang.blaze3d.font.GlyphInfo;
import net.minecraft.client.gui.font.glyphs.SpecialGlyphs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.gui.font.FontSet$GlyphInfoFilter")
public abstract class GlyphInfoFilterMixin {
    @Inject(
            method = "select",
            at = @At("HEAD"),
            cancellable = true
    )
    private void replaceMissing(boolean notFishy, CallbackInfoReturnable<GlyphInfo> cir) {

        // 君、ホントなんなん？アクセサー必要なん？
        GlyphInfoFilterAccessor accessor = (GlyphInfoFilterAccessor) this;

        // 元のMissingなら自作Missingに置き換えや！
        GlyphInfo glyph = notFishy
                ? accessor.getGlyphInfoNotFishy()
                : accessor.getGlyphInfo();

        if (glyph == SpecialGlyphs.MISSING) {
            cir.setReturnValue(MissingGlyph.INSTANCE);
        }
    }
}
