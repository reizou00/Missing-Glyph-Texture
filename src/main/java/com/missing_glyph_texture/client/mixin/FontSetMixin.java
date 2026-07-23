/*
 * Copyright (c) 2026 reizou00
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.missing_glyph_texture.client.mixin;

import com.missing_glyph_texture.client.MissingGlyph;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FontSet.class)
public abstract class FontSetMixin {

    @Shadow
    private BakedGlyph missingGlyph;

    @Inject(
            method = "resetTextures",
            at = @At("TAIL")
    )
    private void reizou$replaceMissingGlyph(CallbackInfo ci) {

        // はぁ...アクセサー？いるみたいね...めんどくせぇ...
        FontSetAccessor accessor = (FontSetAccessor) this;

        // 描画用のデータを焼いちゃうわよ！
        this.missingGlyph = MissingGlyph.INSTANCE.bake(accessor::invokeStitch);
    }
}