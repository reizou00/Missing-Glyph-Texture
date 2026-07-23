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