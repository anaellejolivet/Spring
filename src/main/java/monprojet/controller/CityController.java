package monprojet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.entity.City;
import monprojet.entity.Country;

@Controller
@RequestMapping(path = "/monprojet/city")
@Log4j2 // Génère le 'logger' pour afficher les messages de trace
public class CityController {

	@Autowired
	private CityRepository cityDAO;

	@Autowired
	private CountryRepository countryDAO;

	/**
	 * Affiche toutes les city dans la base
	 * 
	 * @param model pour transmettre les informations à la vue
	 * @return le nom de la vue à afficher ('showCity.html')
	 */
	@GetMapping(path = "show")
	public String afficherListeCity(Model model) {
		model.addAttribute("city", cityDAO.findAll());
		model.addAttribute("country", countryDAO.findAll());
		return "showCity";
	}

	@PostMapping(path = "show")
	public String ajouteLaCityPuisMontreLaListe(String nom, String pays, Integer population, Model model) {
		City city = new City(nom, countryDAO.findByName(pays));
		city.setPopulation(population);
		cityDAO.save(city);
		return afficherListeCity(model); // POST-Redirect-GET : on se redirige vers l'affichage de la liste
	}

	@GetMapping(path = "delete")
	public String supprimeUneCity(@RequestParam Integer code, Model model) {
		log.info("code : " + code);
		Optional<City> opCity = cityDAO.findById(code);
		if (opCity.isPresent()) {
			City city = opCity.get();
			cityDAO.delete(city);
		}
		return afficherListeCity(model); // on se redirige vers l'affichage de la liste
	}

	@GetMapping(path = "edit")
	public String montreLeFormulairePourEdition(@RequestParam Integer code, Model model) {
		City ville = cityDAO.findById(code).orElseThrow();
		model.addAttribute("city", ville);
		model.addAttribute("country", countryDAO.findAll());
		return "editForm";
	}

	@PostMapping(path = "save")
	public String modifierPuisAfficher(@RequestParam Integer id, String nom, String pays, Integer population, Model model) {
		City city = cityDAO.findById(id).orElseThrow();
		log.info("City trouvé " +city);
		Country country = countryDAO.findByName(pays);
		city.setCountry(country);
		log.info("City set country " +city);
		city.setName(nom);
		log.info("City set nom " +city);
		city.setPopulation(population);
		log.info("City set population " +city);
		cityDAO.save(city);
		return afficherListeCity(model);	
	}	
}