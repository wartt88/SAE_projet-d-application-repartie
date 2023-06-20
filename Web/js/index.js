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
        iconAnchor: [22, 94],
        //popupAnchor: [-3, -76], Déplace les logos quand on unzoom ?
    },
});
const etablissementIcon = new Icon({iconUrl: "../logo/EtablissementSup.png"});
const restaurantIcon = new Icon({iconUrl: "../logo/Restaurant.png"});

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

// Données de test pour les restaurants
/* let resTest = [
  {
    nom: "Restaurant A",
    longitude: 6.1784,
    latitude: 48.6921,
  },
  {
    nom: "Restaurant B",
    longitude: 6.183,
    latitude: 48.6929,
  },
  {
    nom: "Restaurant C",
    longitude: 6.1694,
    latitude: 48.6866,
  },
];
 */
// Ajouter les marqueurs des restaurants de test à la carte
// addRestaurantMarkers(resTest);

// URL de l'API pour récupérer les restaurants
let URL_API = "http://10.11.58.65:9000/restaurant/getTables";

// Effectuer une requête vers l'API pour récupérer les restaurants
fetch(URL_API, {method: "GET"})
    .then((response) => response.json())
    .then((data) => {
        console.log("Résultat de la requête :");
        console.log(data);
        // Ajouter les marqueurs des restaurants récupérés à la carte
        addRestaurantMarkers(data);
    })
    .catch((error) => console.log(error));

fetch("http://10.11.58.65:9000/recupererListEtablissement", {method: "GET"})
    .then((response) => response.json())
    .then((data) => {
        console.log("Résultat de la requête etablissemtns :");
        console.log(data[0]);
        console.log(data[0].fields.coordonnees);
        addEtablissementMarker(data);
    });

export function addEtablissementMarker(etablissement) {
    etablissement.forEach(function (etablissement) {
        console.log("coordonne", etablissement.fields.coordonnees);
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
