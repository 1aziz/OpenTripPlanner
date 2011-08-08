/* This program is free software: you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public License
 as published by the Free Software Foundation, either version 3 of
 the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package org.opentripplanner.graph_builder.impl;

import static org.opentripplanner.common.IterableLibrary.filter;

import org.opentripplanner.graph_builder.services.GraphBuilder;
import org.opentripplanner.routing.core.EdgeNarrative;
import org.opentripplanner.routing.core.Graph;
import org.opentripplanner.routing.core.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Check the geometry of every edge in the graph for any bogus geometry --
 * that is, geometry with coordinates of NaN.
 * This is mainly good for debugging, but probably worth keeping on for production
 * because the cost is small compared to the pain of debugging.
 */
public class CheckGeometryGraphBuilderImpl implements GraphBuilder {

    private static final Logger _log = LoggerFactory.getLogger(CheckGeometryGraphBuilderImpl.class);

    @Override
    public void buildGraph(Graph graph) {
        for (Vertex gv : graph.getVertices()) {
            if (Double.isNaN(gv.getCoordinate().x) || Double.isNaN(gv.getCoordinate().y)) {
                _log.warn("Vertex " + gv + " has NaN location; this will cause doom.");
            }
            
            for (EdgeNarrative e : filter(gv.getOutgoing(),EdgeNarrative.class)) {
                Geometry g = e.getGeometry();
                if (g == null) {
                    continue;
                }
                for (Coordinate c : g.getCoordinates()) {
                    if (Double.isNaN(c.x) || Double.isNaN(c.y)) {
                        _log.warn("Edge " + e + " has bogus geometry");
                    }
                }
            }
        }

    }
}
