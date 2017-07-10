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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Holds data about a GTFS route from routes.txt.  Data includes id,
 * short name, long name, color, etc.
 */

@XmlRootElement(name = "route")
public class RouteType {

    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    private AgencyAndId id;

    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    private AgencyAndId serviceId;

    @XmlAttribute
    @JsonSerialize
    private String routeShortName;

    @XmlAttribute
    @JsonSerialize
    private String routeLongName;

    @XmlAttribute
    @JsonSerialize
    private String routeDesc;

    @XmlAttribute
    @JsonSerialize
    private String routeUrl;

    @XmlAttribute
    @JsonSerialize
    private String routeColor;

    @XmlAttribute
    @JsonSerialize
    private Integer routeTypeAtr;

    @XmlAttribute
    @JsonSerialize
    private String routeTextColor;

    @XmlAttribute
    @JsonSerialize
    private Integer routeBikesAllowed;

    public RouteType() {
        /*
    Why is this empty?
*/
    }

    RouteType(Route route) {
        this.id = route.getId();
        this.routeShortName = route.getShortName();
        this.routeLongName = route.getLongName();
        this.routeDesc = route.getDesc();
        this.routeTypeAtr = route.getType();
        this.routeUrl = route.getUrl();
        this.routeColor = route.getColor();
        this.routeTextColor = route.getTextColor();
        this.routeBikesAllowed = route.getBikesAllowed();
    }

    public RouteType(Route route, Boolean extended) {
        this.id = route.getId();
        this.routeShortName = route.getShortName();
        this.routeTypeAtr = route.getType();
        this.routeLongName = route.getLongName();
        if (extended != null && extended.equals(true)) {
            this.routeDesc = route.getDesc();
            this.routeTypeAtr = route.getType();
            this.routeUrl = route.getUrl();
            this.routeColor = route.getColor();
            this.routeTextColor = route.getTextColor();
        }
    }

    public AgencyAndId getId() {
        return this.id;
    }

}
