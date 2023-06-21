import {addMarker} from "./index.js";

export const fetchIncidents = async () => {
    fetch("https://carto.g-ny.org/data/cifs/cifs_waze_v2.json", {method: "GET"})
        .then((response) => response.json())
        .then((data) => {
            data.incidents.forEach((incident) => {
                addIncidentMarker(incident);
            });
        });
};

function addIncidentMarker(incident) {

    const north = incident.location.polyline.split(" ")[0];
    const east = incident.location.polyline.split(" ")[1];
    const defaultIcon = L.Icon.extend({
        options: {
            iconSize: [40, 40],
        },
    });
    const incidentIcon = new defaultIcon({
        iconUrl: "../logo/Incident.png",
    });
    const descr =
        " Du " +
        incident.starttime +
        " au " +
        incident.starttime +
        ", " +
        incident.description;
    addMarker(north, east, incidentIcon, descr);
}
