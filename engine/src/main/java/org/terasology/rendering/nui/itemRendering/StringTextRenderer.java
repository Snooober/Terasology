/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.rendering.nui.itemRendering;

import org.terasology.math.Vector2i;
import org.terasology.rendering.assets.font.Font;
import org.terasology.rendering.nui.Canvas;
import org.terasology.rendering.nui.TextLineBuilder;

import java.util.List;

/**
 *
 */
public abstract class StringTextRenderer<T> extends AbstractItemRenderer<T> {
    private final boolean wrap;

    protected StringTextRenderer() {
        this(true);
    }

    protected StringTextRenderer(boolean wrap) {
        this.wrap = wrap;
    }

    @Override
    public void draw(T value, Canvas canvas) {
        if (wrap) {
            canvas.drawText(getString(value));
        } else {
            int width = canvas.size().x;
            Font font = canvas.getCurrentStyle().getFont();
            String text = getString(value);
            if (font.getWidth(text) <= width) {
                canvas.drawText(text);
            } else {
                String shortText = "...";
                StringBuilder sb = new StringBuilder(text);
                while (sb.length() > 0) {
                    shortText = sb.toString() + "...";
                    if (font.getWidth(shortText) <= width) {
                        break;
                    }
                    sb.setLength(sb.length() - 1);
                }
                canvas.drawText(shortText);
            }
        }
    }

    @Override
    public Vector2i getPreferredSize(T value, Canvas canvas) {
        Font font = canvas.getCurrentStyle().getFont();
        String text = getString(value);
        if (wrap) {
            List<String> lines = TextLineBuilder.getLines(font, text, canvas.size().x);
            return font.getSize(lines);
        } else {
            return new Vector2i(font.getWidth(text), font.getLineHeight());
        }
    }

    public abstract String getString(T value);
}
