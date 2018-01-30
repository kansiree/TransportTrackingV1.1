package th.co.gosoft.storemobile.transporttracking.Utility;

import java.util.List;

import th.co.gosoft.storemobile.transporttracking.Models.Map.Route;

/**
 * Created by Jubjang on 8/29/2017.
 */

public interface  DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
