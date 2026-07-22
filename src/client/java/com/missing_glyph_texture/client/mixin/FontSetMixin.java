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
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Unique
    private Object createGlyphInfoFilter() {
        try {
            // どうやらこやつimportできないらしい...なので邪道をする。
            Class<?> clazz =
                    Class.forName("net.minecraft.client.gui.font.FontSet$GlyphInfoFilter");

            // 俺の奴に置き換えだっ！
            return clazz
                    .getConstructor(GlyphInfo.class, GlyphInfo.class)
                    .newInstance(MissingGlyph.INSTANCE, MissingGlyph.INSTANCE);

        } catch (Exception e) {
            // ｳｯｹﾞ!...失敗だ！
            throw new RuntimeException(e);
        }
    }

    // Missingかどうか確認！
    @Unique
    private boolean isMissing(Object filter) {
        return filter.toString().contains("glyphInfo=MISSING");
    }

    // 計算用のものを書き換え、こうしないと文字がかぶっちゃうわ
    @Inject(
            method = "computeGlyphInfo",
            at = @At("RETURN"),
            cancellable = true
    )
    private void replaceMissingGlyphInfo(int codepoint, CallbackInfoReturnable<Object> cir) {
        Object result = cir.getReturnValue();

        if (isMissing(result)) {
            // 自作Missingに置き換えだっ！
            cir.setReturnValue(createGlyphInfoFilter());
        }
    }
}