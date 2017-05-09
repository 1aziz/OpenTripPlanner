package org.opentripplanner.analyst;

import java.util.List;

/**
 * Created by aziz on 5/9/17.
 */
public interface PointSetCacheService {
    PointSet get(String pointSetId);
    List<String> getPointSetIds();
}
