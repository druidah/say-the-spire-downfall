package sayTheSpire.map;

import java.util.HashSet;
import com.megacrit.cardcrawl.map.MapRoomNode;
import downfall.patches.EvilModeCharacterSelect;
import com.evacipated.cardcrawl.modthespire.Loader;

public class BaseMapEdge extends VirtualMapEdge {

    private VirtualMapNode start, end;
    private HashSet<String> tags;
    public static boolean downfall = Loader.isModLoaded("downfall");

    public BaseMapEdge(VirtualMapNode start, VirtualMapNode end) {
        this.start = start;
        this.end = end;
        this.tags = new HashSet();
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public HashSet<String> getTags() {
        return this.tags;
    }

    public VirtualMapNode getEnd() {
        return this.end;
    }

    public Boolean getIsForward() {
        if (Loader.isModLoaded("downfall") && EvilModeCharacterSelect.evilMode) {
            return this.getEnd().getY() < this.getStart().getY();
        }
        return this.getEnd().getY() > this.getStart().getY();
    }

    public VirtualMapNode getStart() {
        return this.start;
    }

    public VirtualMapEdge invert() {
        return new BaseMapEdge(this.getEnd(), this.getStart());
    }

}
