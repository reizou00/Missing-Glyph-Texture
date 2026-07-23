/*
 * Copyright (c) 2026 reizou00
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.missing_glyph_texture.client;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public enum MissingGlyph implements GlyphInfo {

    INSTANCE;

    private static final NativeImage IMAGE = createImage();

    // ここであのチェッカーを作る。
    private static NativeImage createImage() {

        final int width = 8;
        final int height = 8;

        // 取り合えず空の画像を作っておく。
        NativeImage image = new NativeImage(
                        NativeImage.Format.RGBA,
                        width,
                        height,
                        false
                    );

        // チェッカーの作成
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                boolean checker = ((x / 4) + (y / 4)) % 2 == 0;

                // さっき言ってた空の画像に色をつける感じだと思われます()
                image.setPixel(
                        x,
                        y,
                        checker
                                ? 0xFFFF00FF
                                : 0xFF000000
                );
            }
        }

        image.untrack();

        return image;
    }

    // 間を作っちまおう！文字がかぶるのはきついのさ！
    @Override
    public float getAdvance() {
        return (float)(IMAGE.getWidth() + 1);
    }

    // GlyphInfoを描画用のBakedGlyphに変えちゃうわよ！
    @Override
    public @NotNull BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> stitch) {

        // FontTextureに登録して、描画用のBakedGlyphを作っちまいます。
        return stitch.apply(
                new SheetGlyphInfo() {

                    @Override
                    public int getPixelWidth() {
                        return IMAGE.getWidth();
                    }

                    @Override
                    public int getPixelHeight() {
                        return IMAGE.getHeight();
                    }

                    @Override
                    public float getOversample() {
                        return 1.0F;
                    }

                    @Override
                    public boolean isColored() {
                        return true;
                    }

                    // さっき作ったやつをアップロード！！！
                    @Override
                    public void upload(int x, int y, GpuTexture texture) {

                        RenderSystem.getDevice()
                                .createCommandEncoder()
                                .writeToTexture(
                                        texture,
                                        IMAGE,
                                        0,
                                        x,
                                        y,
                                        IMAGE.getWidth(),
                                        IMAGE.getHeight(),
                                        0,
                                        0
                                );
                    }
                }
        );
    }
}