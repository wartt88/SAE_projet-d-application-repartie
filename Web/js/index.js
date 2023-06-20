// Créer une carte avec Leaflet et la centrer sur les coordonnées spécifiées
var map = L.map("map").setView([48.6921, 6.1844], 13);

// Ajouter une couche de tuiles OpenStreetMap à la carte
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

const Icon = L.Icon.extend({
    options: {
        iconSize: [40, 40],
        //iconAnchor: [22, 94],
        //popupAnchor: [-3, -76], Déplace les logos quand on unzoom ?
    },
});
const etablissementIcon = new Icon({iconUrl: "../logo/EtablissementSup.png"});
const restaurantIcon = new Icon({iconUrl: "../logo/Restaurant.png"});
export const stationIcon = new Icon({iconUrl: "../logo/StationVelo.png"});

/**
 * Ajoute des marqueurs de restaurant à la carte.
 * @param {Array} restaurants - Un tableau d'objets représentant les restaurants.
 */
export function addRestaurantMarkers(restaurants) {
    restaurants.forEach(function (restaurant) {
        // Créer un marqueur pour chaque restaurant et l'ajouter à la carte
        var marker = L.marker([restaurant.latitude, restaurant.longitude], {
            icon: restaurantIcon,
        }).addTo(map);

        // Associer une info-bulle au marqueur contenant le nom du restaurant
        marker.bindPopup(restaurant.nom);

        // Ajouter un événement de clic pour chaque marqueur
        marker.on("click", function () {
            // Afficher le formulaire d'inscription
            showInscriptionForm(restaurant);
        });
    });
}

/**
 * Affiche le formulaire d'inscription pour un restaurant spécifique.
 * @param {Object} restaurant - L'objet représentant le restaurant.
 */
function showInscriptionForm(restaurant) {
  let formElement = document.querySelector(".reservation");
  formElement.style.display = "flex";
}

// Ajouter un gestionnaire d'événement au bouton de fermeture du formulaire
let closebtn = document.querySelector("#close");
closebtn.addEventListener("click", function (event) {
  let formElement = document.querySelector(".reservation");
  formElement.style.display = "none";
});

// Ajouter les marqueurs des restaurants de test à la carte
// addRestaurantMarkers(resTest);

// URL de l'API pour récupérer les restaurants
let URL_API = "http://10.11.58.65:9000/restaurant/getTables";

// Effectuer une requête vers l'API pour récupérer les restaurants
fetch(URL_API, {method: "GET"})
    .then((response) => response.json())
    .then((data) => {
        // Ajouter les marqueurs des restaurants récupérés à la carte
        addRestaurantMarkers(data);
    })
    .catch((error) => console.log(error));

// Effectuer une requête vers l'API pour récupérer les etablissements

fetch("http://10.11.58.65:9000/recupererListEtablissement", {method: "GET"})
    .then((response) => response.json())
    .then((data) => {
        addEtablissementMarker(data);
    });

export function addEtablissementMarker(etablissement) {
    etablissement.forEach(function (etablissement) {
        // Créer un marqueur pour chaque restaurant et l'ajouter à la carte
        if (!etablissement.fields.coordonnees) return;
        var marker = L.marker(
            [
                etablissement.fields.coordonnees[0],
                etablissement.fields.coordonnees[1],
            ],
            {icon: etablissementIcon}
        ).addTo(map);

        // Associer une info-bulle au marqueur contenant le nom du restaurant
        marker.bindPopup(etablissement.fields.url);

        // Ajouter un événement de clic pour chaque marqueur
        marker.on("click", function () {
            // Afficher le formulaire d'inscription
        });
    });
}

function addStationMarker(station) {
    var marker = L.marker([station.lat, station.lon], {
        icon: stationIcon,
    }).addTo(map);
    // Associer une info-bulle au marqueur contenant le nom du restaurant
    marker.bindPopup(
        "Adresse de la station: " +
        station.address +
        " nombre de vélos disponible sur la station: " +
        station.veloDispo +
        " nombre de place de parkings libre sur la station: " +
        station.placesDispo
    );

    // Ajouter un événement de clic pour chaque marqueur
    marker.on("click", function () {
        // Afficher le formulaire d'inscription
    });
}

// Partie pour les stations de vélo
const bike = async () => {
    // Effectuer une requête vers l'API ouverte transport pour récupérer les stations de vélo
    const [infoStations, statusStations] = await Promise.all([
        // Information sur les stations
        fetch(
            "https://transport.data.gouv.fr/gbfs/nancy/station_information.json",
            {method: "GET"}
        ),
        // Status en temps réel des stations
        fetch("https://transport.data.gouv.fr/gbfs/nancy/station_status.json", {
            method: "GET",
        }),
    ]);
    // Extraire les données JSON des réponses
    const infoStationJson = await infoStations.json();
    const statusStationsJson = await statusStations.json();

    // Ajouter les marqueurs des stations récupérées à la carte
    console.log(infoStationJson);
    console.log(statusStationsJson);
    infoStationJson.data.stations.forEach((station) => {
        const status = statusStationsJson.data.stations.find(
            (s) => s.station_id === station.station_id
        );
        if (status) {
            station.veloDispo = status.num_bikes_available;
            station.placesDispo = status.num_docks_available;
            addStationMarker(station);
        }
    });
};

bike();
