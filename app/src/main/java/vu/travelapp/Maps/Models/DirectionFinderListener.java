package vu.travelapp.Maps.Models;

import java.util.List;

/**
 * Created by trongphuong1011 on 8/10/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
