package org.cloudburstmc.protocol.bedrock.data.skin;

import lombok.Data;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

@Data
public class PersonaPieceTintData {
    PersonaPieceType pieceType;
    @Deprecated List<String> colors;
    List<Color> colorsNew;

    public PersonaPieceTintData(String type, List<String> colors) {
        this.pieceType = PersonaPieceType.fromName(type);
        this.colors = colors;
    }

    public PersonaPieceTintData(PersonaPieceType type, List<Color> colorsNew) {
        this.pieceType = type;
        this.colorsNew = colorsNew;
    }

    // Compatibility with the record API used by codecs before v2168.
    public String type() { return getType(); }
    @Deprecated
    public List<String> colors() { return getColors(); }

    public String getType() { return pieceType.getSerializeName(); }

    @Deprecated
    public List<String> getColors() {
        if ((colors == null || colors.isEmpty()) && colorsNew != null && !colorsNew.isEmpty()) {
            colors = new ArrayList<>(colorsNew.size());
            for (Color c : colorsNew) {
                colors.add(c.getAlpha() == 0 ? "#0" : String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
            }
        }
        return colors;
    }

    public List<Color> getColorsNew() {
        if ((colorsNew == null || colorsNew.isEmpty()) && colors != null && !colors.isEmpty()) {
            colorsNew = new ArrayList<>(colors.size());
            for (String s : colors) {
                colorsNew.add(s.equals("#0") ? new Color(0, true) : new Color((int) Long.parseLong(s.startsWith("#") ? s.substring(1) : s, 16), true));
            }
        }
        return colorsNew;
    }
}
