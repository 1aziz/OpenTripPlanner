/* This program is free software: you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public License
 as published by the Free Software Foundation, either version 3 of
 the License, or (props, at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package org.opentripplanner.api.adapters;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Trip;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlRootElement(name = "trip")
public class TripType {


    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    AgencyAndId id;


    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    private
    AgencyAndId serviceId;

    @XmlAttribute
    @JsonSerialize
    private String tripShortName;

    @XmlAttribute
    @JsonSerialize
    private String tripHeadsign;

    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    private AgencyAndId routeId;

    @XmlAttribute
    @JsonSerialize
    private String directionId;

    @XmlAttribute
    @JsonSerialize
    private String blockId;

    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    private AgencyAndId shapeId;

    @XmlAttribute
    @JsonSerialize
    private Integer wheelchairAccessible;


    @XmlAttribute
    @JsonSerialize
    private Integer tripBikesAllowed;


    @XmlAttribute
    @JsonSerialize
    private Integer bikesAllowed;

    private Route route;

    @SuppressWarnings("deprecation")
    TripType(Trip obj) {
        this.id = obj.getId();
        this.serviceId = obj.getServiceId();
        this.tripShortName = obj.getTripShortName();
        this.tripHeadsign = obj.getTripHeadsign();
        this.routeId = obj.getRoute().getId();
        this.directionId = obj.getDirectionId();
        this.blockId = obj.getBlockId();
        this.shapeId = obj.getShapeId();
        this.wheelchairAccessible = obj.getWheelchairAccessible();
        this.bikesAllowed = obj.getBikesAllowed();
        this.route = obj.getRoute();
    }

    @SuppressWarnings("deprecation")
    public TripType(Trip obj, Boolean extended) {
        this.id = obj.getId();
        this.tripShortName = obj.getTripShortName();
        this.tripHeadsign = obj.getTripHeadsign();
        if (extended != null && extended.equals(true)) {
            this.route = obj.getRoute();
            this.serviceId = obj.getServiceId();
            this.routeId = obj.getRoute().getId();
            this.directionId = obj.getDirectionId();
            this.blockId = obj.getBlockId();
            this.shapeId = obj.getShapeId();
            this.wheelchairAccessible = obj.getWheelchairAccessible();
            this.bikesAllowed = obj.getBikesAllowed();
        }
    }
    public Route getRoute() {
        return route;
    }
}
