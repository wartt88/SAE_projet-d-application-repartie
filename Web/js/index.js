import { fetchIncidents } from "./Incidents.js";

let ip = "10.11.58.65";

// Créer une carte avec Leaflet et la centrer sur les coordonnées spécifiées
var map = L.map("map").setView([48.6921, 6.1844], 13);

// Ajouter une couche de tuiles OpenStreetMap à la carte
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  maxZoom: 19,
  attribution:
    '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

export const defaultIcon = L.Icon.extend({
  options: {
    iconSize: [30, 30],
    //iconAnchor: [22, 94],
    //popupAnchor: [-3, -76], Déplace les logos quand on unzoom ?
  },
});
const etablissementIcon = new defaultIcon({
  iconUrl: "../logo/EtablissementSup.png",
});
const restaurantIcon = new defaultIcon({ iconUrl: "../logo/Restaurant.png" });
export const stationIcon = new defaultIcon({
  iconUrl: "../logo/StationVelo.png",
});

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
  // on met sur le bouton d'id send l'envent click la fonction envoyerReservation
  let sendbtn = document.querySelector("#send");
  sendbtn.addEventListener("click", function (event) {
    envoyerReservation(restaurant.id);
  });
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
let URL_API = ip + ":9000/restaurant/getTables";

// Effectuer une requête vers l'API pour récupérer les restaurants
function fetchRestaurants() {
  fetch("http://" + URL_API, { method: "GET" })
    .then((response) => response.json())
    .then((data) => {
      // Ajouter les marqueurs des restaurants récupérés à la carte
      addRestaurantMarkers(data);
    })
    .catch((error) => console.log(error));
}
fetchRestaurants();

// Effectuer une requête vers l'API pour récupérer les etablissements
function fetchEtablissement() {
  fetch("http://" + ip + ":9000/recupererListEtablissement", { method: "GET" })
    .then((response) => response.json())
    .then((data) => {
      addEtablissementMarker(data);
    });
}
fetchEtablissement;

export function addEtablissementMarker(etablissement) {
  etablissement.forEach(function (etablissement) {
    // Créer un marqueur pour chaque restaurant et l'ajouter à la carte
    if (!etablissement.fields.coordonnees) return;
    var marker = L.marker(
      [
        etablissement.fields.coordonnees[0],
        etablissement.fields.coordonnees[1],
      ],
      { icon: etablissementIcon }
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
      { method: "GET" }
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

export const addMarker = (nord, est, icon, descr) => {
  var marker = L.marker([nord, est], {
    icon: icon,
  }).addTo(map);
  // Associer une info-bulle au marqueur contenant le nom du restaurant
  marker.bindPopup(descr);
};

fetchIncidents();

function envoyerReservation(idRestaurant) {
  let prenom = document.getElementById("name").value;
  let nom = document.getElementById("surname").value;
  let nombrePersonnes = document.getElementById("nbper").value;
  let idTable = document.getElementById("numtab").value;
  let date = document.getElementById("date").value;
  let heure = document.getElementById("time").value;
  let phone = document.getElementById("phone").value;
  console.log(idRestaurant);
  // si phone est vide ou ne contient pas 10 chiffres
  if (phone == "" || phone.length != 10) {
    alert("Veuillez entrer un numéro de téléphone valide");
    return;
  }
  console.log(date);
  console.log(heure);
  const formattedDate = date + " " + heure + ":00";
  console.log(formattedDate);

  // Créer un objet contenant les données de réservation
  const reservationData = {
    id: idRestaurant,
    nom: nom,
    prenom: prenom,
    nbPersonnes: nombrePersonnes,
    numTable: idTable,
    date: formattedDate,
    telephone: phone,
  };

  // Envoyer les données de réservation à l'API
  fetch("http://" + ip + ":9000/restaurant/reserverTable", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(reservationData),
  })
    .then((response) => {
      if (response.ok) {
        return "Réservation effectuée avec succès !";
        // Effectuer des actions supplémentaires après une réservation réussie
      } else {
        return response.text();
      }
    })
    .then((text) => {
      alert(text);
    })
    .catch((error) => {
      console.log(error);
      console.log("Erreur lors de la réservation : " + error.message);
      alert("Erreur lors de la réservation : " + error.message);
      // Gérer les erreurs de réservation
    });
}

let check = document.querySelectorAll(".check");

check.forEach((element) => {
  element.addEventListener("click", displayItemsMap);
});

function displayItemsMap() {
  console.log("displayItemsMap");

  // on remove to les markers
  map.eachLayer(function (layer) {
    if (layer instanceof L.Marker) {
      map.removeLayer(layer);
    }
  });

  let check_rest = document.getElementById("check_rest");
  let check_vel = document.getElementById("check_vel");
  let check_inc = document.getElementById("check_inc");
  let check_etab = document.getElementById("check_etab");
  try {
    if (check_rest.checked) {
      fetchRestaurants();
    }
  } catch (error) {
    console.log(error);
  }

  try {
    if (check_vel.checked) {
      bike();
    }
  } catch (error) {
    console.log(error);
  }

  try {
    if (check_inc.checked) {
      fetchIncidents();
    }
  } catch (error) {
    console.log(error);
  }

  try {
    if (check_etab.checked) {
      fetchEtablissement();
    }
  } catch (error) {
    console.log(error);
  }
}
