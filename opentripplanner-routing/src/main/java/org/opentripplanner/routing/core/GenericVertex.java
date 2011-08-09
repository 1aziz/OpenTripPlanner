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

import static org.opentripplanner.common.IterableLibrary.cast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.onebusaway.gtfs.model.AgencyAndId;
import org.opentripplanner.routing.impl.DistanceLibrary;

import com.vividsolutions.jts.geom.Coordinate;

public class GenericVertex implements Vertex, Serializable {

    private static final long serialVersionUID = 20110808L;

    public final String label;
    
    protected String name;

    private AgencyAndId stopId = null;

    private double y;

    private double x;

    int index;

    private double distanceToNearestTransitStop = 0;

    static int maxIndex = 0;
    
    /* --- ex-graphvertex ---*/
    
    private transient ArrayList<Edge> incoming = new ArrayList<Edge>();
    private transient ArrayList<Edge> outgoing = new ArrayList<Edge>();

    public void addOutgoing(Edge ee) {
        outgoing.add(ee);
    }
    
    public void addIncoming(Edge ee) {
        incoming.add(ee);
    }
    
    public void removeOutgoing(Edge ee) {
        outgoing.remove(ee);
    }
    
    public void removeIncoming(Edge ee) {
        incoming.remove(ee);
    }
    
    @Override
    public int getDegreeIn() {
        return incoming.size();
    }

    @Override
    public int getDegreeOut() {
        return outgoing.size();
    }

    @Override
    public Collection<Edge> getIncoming() {
        return incoming;
    }

    @Override
    public Collection<Edge> getOutgoing() {
        return outgoing;
    }

    @Override
    public void removeAllEdges() {
        for (Edge e : outgoing) {
            if (e instanceof DirectEdge) {
                DirectEdge edge = (DirectEdge) e;
                // this used to grab the graphvertex by label... now it could possibly be a vertex
                // that is not in the graph
                // Vertex target = vertices.get(edge.getToVertex().getLabel());
                Vertex target = edge.getToVertex();
                if (target != null) {
                    target.removeIncoming(e);
                }
            }
        }
        for (Edge e : incoming) {
            // why only directedges?
            if (e instanceof DirectEdge) {
                DirectEdge edge = (DirectEdge) e;
                // Vertex source = vertices.get(edge.getFromVertex().getLabel());
                Vertex source = edge.getFromVertex();
                if (source != null) {
                    // changed to removeOutgoing (AB)
                    source.removeOutgoing(e);
                }
            }
        }
        incoming = new ArrayList<Edge>();
        outgoing = new ArrayList<Edge>();
    }
    
    /* --- END ex-graphvertex ---*/
    
    public GenericVertex(String label, Coordinate coord, String name) {
        this(label, coord.x, coord.y, name);
    }

    public GenericVertex(String label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.index = maxIndex  ++;
    }

    public GenericVertex(String label, double x, double y, String name) {
        this(label, x, y);
        this.name = name;
    }
    
    public GenericVertex(String label, double x, double y, String name, AgencyAndId stopId) {
        this(label, x, y);
        this.name = name;
        this.stopId = stopId;
    }

    public double distance(Coordinate c) {
        return DistanceLibrary.distance(getY(), getX(), c.y, c.x);
    }

    @Override
    public double distance(Vertex v) {
        return DistanceLibrary.distance(getY(), getX(), v.getY(), v.getX());
    }

    public Coordinate getCoordinate() {
        return new Coordinate(getX(), getY());
    }

    public String toString() {
        return "<" + this.label + ">";
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AgencyAndId getStopId() {
        return stopId;
    }

    /**
     * Merge another vertex into this one.  Useful during graph construction for handling 
     * sequential non-branching streets, and empty dwells.
     */
    public void mergeFrom(Graph graph, GenericVertex other) {
        // GraphVertex gv = graph.getGraphVertex(this);

        // We only support Vertices that are direct edges when merging
        Iterable<DirectEdge> incoming = cast(this.incoming);
        Iterable<DirectEdge> outgoing = cast(this.outgoing);

        //remove incoming edges from other to this
        Iterator<DirectEdge> it = incoming.iterator();
        while(it.hasNext()) {
            DirectEdge edge = it.next();
            if (edge.getFromVertex() == other) {
                it.remove();
            }
        }
        //remove outgoing edges from this to other
        it = outgoing.iterator();
        while(it.hasNext()) {
            DirectEdge edge = it.next();
            if (edge.getToVertex() == other) {
                it.remove();
            }
        }
        //make incoming edges to other point to this
        for (AbstractEdge edge : cast(other.getIncoming(), AbstractEdge.class)) {
            if (edge.getFromVertex() == this) {
                continue;
            }
            edge.setToVertex(this);
            this.addIncoming(edge);
        }
        //add outgoing edges from other to outgoing from this
        for (AbstractEdge edge : cast(other.getOutgoing(), AbstractEdge.class)) {
            if (edge.getToVertex() == this) {
                continue;
            }
            edge.setFromVertex(this);
            this.addOutgoing(edge);
        }
        graph.removeVertex(other);
    }
    
    public int hashCode() {
        return index;
    }

    /* SERIALIZATION */
    
    private void writeObject(ObjectOutputStream out) throws IOException {
// Transient so no need to trim
//        incoming.trimToSize();
//        outgoing.trimToSize();
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.incoming = new ArrayList<Edge>(2);
        this.outgoing = new ArrayList<Edge>(2);
        index = maxIndex++;
    }

    public void setDistanceToNearestTransitStop(double distance) {
        distanceToNearestTransitStop = distance;
    }

    public double getDistanceToNearestTransitStop() {
        return distanceToNearestTransitStop;
    }

    public int getIndex() {
    	return index;
    }

}
