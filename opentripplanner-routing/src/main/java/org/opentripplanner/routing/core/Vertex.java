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

package org.opentripplanner.routing.core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

import org.onebusaway.gtfs.model.AgencyAndId;

import com.vividsolutions.jts.geom.Coordinate;

public interface Vertex extends Cloneable {

    /**
     * Every vertex has a label which is globally unique
     */
    public String getLabel();

    /**
     * For vertices that represent stops, the passenger-facing stop ID (for systems like TriMet that
     * have this feature).
     */
    public AgencyAndId getStopId();

    /**
     * Distance in meters to the coordinate
     */
    public double distance(Coordinate c);
    
    /**
     * Distance in meters to the vertex
     */
    public double distance(Vertex v);

    /**
     * @return The location of the vertex in longitude/latitude
     */
    public Coordinate getCoordinate();

    public String toString();

    public double getX();

    public double getY();

    public String getName();

    public void setDistanceToNearestTransitStop(double distance);

    public double getDistanceToNearestTransitStop();
    
    /****
     * Ex-HasEdges Interface
     ****/

    public int getDegreeIn();

    public int getDegreeOut();

    public Collection<Edge> getIncoming();

    public Collection<Edge> getOutgoing();
    
    /****
     * Ex-GraphVertex
     ****/

    public void addOutgoing(Edge ee);

    public void addIncoming(Edge ee);
    
    public void removeOutgoing(Edge ee);

    public void removeIncoming(Edge ee);

    public void removeAllEdges();

}