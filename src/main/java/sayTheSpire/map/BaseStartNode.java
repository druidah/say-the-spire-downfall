package sayTheSpire.map;

import java.util.List;
import java.util.ArrayList;
import com.megacrit.cardcrawl.map.MapRoomNode;

public class BaseStartNode extends BaseRoomNode {

    public BaseStartNode() {
        super(0, -1);
    }

    public BaseStartNode(int YLocation) {
        super(0, YLocation);
    }

    public List<VirtualMapEdge> getEdges() {
        ArrayList<VirtualMapEdge> edges = new ArrayList();
        VirtualMap map = this.getMap();

        // Ha Downfall módban vagyunk, a kezdő szint 15, különben -1
        int startY = (sayTheSpire.map.BaseMap.downfall) ? 14 : 0;

        for (int x = 0; x <= 6; x++) {
            VirtualMapNode target = map.getNodeAt(x, startY);
            if (target == null)
                continue;
            if (target.hasEdges())
                edges.add(new BaseMapEdge(this, target));
        }
        return edges;
    }

    public Boolean getIsVisited() {
        return true;
    }

    public String getName() {
        return "start location";
    }
}
