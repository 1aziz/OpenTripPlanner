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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "Stop")
public class StopType {

    @XmlJavaTypeAdapter(AgencyAndIdAdapter.class)
    @JsonSerialize
    private AgencyAndId id;

   
    @XmlAttribute
    @JsonSerialize
    private String stopName;
   
    @XmlAttribute
    @JsonSerialize
    private Double stopLat;
   
    @XmlAttribute
    @JsonSerialize
    private Double stopLon;
   
    @XmlAttribute
    @JsonSerialize
    private String stopCode;
   
    @XmlAttribute
    @JsonSerialize
    private String stopDesc;
   
    @XmlAttribute
    @JsonSerialize
    private String zoneId;
   
    @XmlAttribute
    @JsonSerialize
    private String stopUrl;
   
    @XmlAttribute
    @JsonSerialize
    private Integer locationType;
   
    @XmlAttribute
    @JsonSerialize
    private String parentStation;

    @XmlAttribute
    @JsonSerialize
    private Integer wheelchairBoarding;
   
    @XmlAttribute
    @JsonSerialize
    private String direction;
   
    @XmlElements(value = @XmlElement(name = "route"))
    public List<AgencyAndId> routes;

    StopType(org.onebusaway.gtfs.model.Stop stop) {
        this.id = stop.getId();
        this.stopLat = stop.getLat();
        this.stopLon = stop.getLon();
        this.stopCode = stop.getCode();
        this.stopName = stop.getName();
        this.stopDesc = stop.getDesc();
        this.zoneId = stop.getZoneId();
        this.stopUrl = stop.getUrl();
        this.locationType = stop.getLocationType();
        this.parentStation = stop.getParentStation();
        this.wheelchairBoarding = stop.getWheelchairBoarding();
        this.direction = stop.getDirection();
    }

    public StopType(org.onebusaway.gtfs.model.Stop stop, Boolean extended) {
        this.id = stop.getId();
        this.stopLat = stop.getLat();
        this.stopLon = stop.getLon();
        this.stopCode = stop.getCode();
        this.stopName = stop.getName();
        if (extended != null && extended.equals(true)) {
            this.stopDesc = stop.getDesc();
            this.zoneId = stop.getZoneId();
            this.stopUrl = stop.getUrl();
            this.locationType = stop.getLocationType();
            this.parentStation = stop.getParentStation();
            this.wheelchairBoarding = stop.getWheelchairBoarding();
        }
    }
}
