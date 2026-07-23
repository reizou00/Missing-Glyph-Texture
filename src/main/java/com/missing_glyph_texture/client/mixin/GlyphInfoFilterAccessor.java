/*
 * Copyright (c) 2026 reizou00
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.missing_glyph_texture.client.mixin;

import com.mojang.blaze3d.font.GlyphInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.gui.font.FontSet$GlyphInfoFilter")
public interface GlyphInfoFilterAccessor {

    @Accessor("glyphInfo")
    GlyphInfo getGlyphInfo();

    @Accessor("glyphInfoNotFishy")
    GlyphInfo getGlyphInfoNotFishy();
}