var map = L.map("map").setView([48.6921, 6.1844], 13);
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  maxZoom: 19,
  attribution:
    '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

let resTest = [
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

function addRestaurantMarkers(restaurants) {
  restaurants.forEach(function (restaurant) {
    var marker = L.marker([restaurant.latitude, restaurant.longitude]).addTo(
      map
    );
    marker.bindPopup(restaurant.nom);

    // Ajouter un événement de clic pour chaque marqueur
    marker.on("click", function () {
        // Afficher le formulaire d'inscription
        showInscriptionForm(restaurant);
      });
  });
}


function showInscriptionForm(restaurant) {
    // Récupérer le formulaire d'inscription ou créer un nouvel élément
    var formElement = document.getElementById("inscription-form");
    if (!formElement) {
      formElement = document.createElement("form");
      formElement.id = "inscription-form";
      // Ajouter les champs du formulaire, par exemple :
      formElement.innerHTML = `
        <label for="name">Nom :</label>
        <input type="text" id="name" name="name" required>
        <label for="email">Email :</label>
        <input type="email" id="email" name="email" required>
        <button type="submit">Inscrire</button>
      `;
      // Ajouter le formulaire à la page
      document.body.appendChild(formElement);
    }

    // Pré-remplir les champs du formulaire avec les informations du restaurant
  document.getElementById("name").value = restaurant.nom;
  // Autres champs du formulaire...

  // Afficher le formulaire d'inscription
  formElement.style.display = "block";
}


addRestaurantMarkers(resTest);
