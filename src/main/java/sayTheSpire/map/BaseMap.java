package sayTheSpire.map;

import java.util.ArrayList;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import downfall.patches.EvilModeCharacterSelect;
import com.evacipated.cardcrawl.modthespire.Loader;
import sayTheSpire.utils.MapUtils;

public class BaseMap extends VirtualMap {

    private String id;
    public static boolean downfall = Loader.isModLoaded("downfall");

    public BaseMap() {
        this.id = "base." + AbstractDungeon.id;
    }

    public String getId() {
        return this.id;
    }

    private static ArrayList<ArrayList<MapRoomNode>> getDungeonMap() {
        if (downfall && EvilModeCharacterSelect.evilMode) {
            return AbstractDungeon.map; // Downfall térkép
        }
        return CardCrawlGame.dungeon.getMap();
    }

    public VirtualMapNode getNodeFromObject(Object obj) {
        if (obj == null || !(obj instanceof MapRoomNode)) {
            return null;
        }
        return new BaseRoomNode((MapRoomNode) obj);
    }

    public VirtualMapEdge getParentEdge(VirtualMapNode node) {
        if (node == null)
            return null;

        for (int y = node.getY(); y > -1; y--) {
            for (int x = 0; x <= 6; x++) {
                VirtualMapNode source = this.getNodeAt(x, y);
                if (source == null)
                    continue;

                if ((source.getIsVisited() && source.isConnectedTo(node)) || (source instanceof BaseStartNode)) {
                    return new BaseMapEdge(node, source);
                }
            }
        }
        return null;
    }

    public VirtualMapNode getNodeAt(int x, int y) {
        ArrayList<ArrayList<MapRoomNode>> map = getDungeonMap();
        if (map == null)
            return null;

        if (y == -1)
            return new BaseStartNode();
        if (y >= map.size())
            return null;

        for (MapRoomNode node : map.get(y)) {
            if (node.x == x && node.y == y && node.hasEdges()) {
                return new BaseRoomNode(node);
            }
        }
        return null;
    }

    public VirtualMapNode getPlayerNode() {
        MapRoomNode node = MapUtils.getCurrentNode();

        if (Loader.isModLoaded("downfall") && EvilModeCharacterSelect.evilMode) {
            if (node == null || node.x < 0 || node.x > 6) {
                return new BaseStartNode(AbstractDungeon.currMapNode.y);
            }
            return this.getNodeAt(node.x, node.y);
        }

        if (node == null || node.x < 0 || node.x > 6) {
            return new BaseStartNode();
        }
        return this.getNodeAt(node.x, node.y);
    }
}
