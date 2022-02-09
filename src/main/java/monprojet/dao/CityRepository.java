package monprojet.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import monprojet.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {

    /**
     * Trouve une ville par son nom.
     * Cette méthode sera automatiquement implémentée par Spring Data JPA.
     * @param cityName
     * @return La ville correspondant au nom fourni, ou null si pas trouvé.
     */
    public City findByName(String cityName);

    /**
     * Trouve une ville par son id.
     * Cette méthode sera automatiquement implémentée par Spring Data JPA.
     * @param cityName
     * @return La ville correspondant au nom fourni, ou null si pas trouvé.
     */
    public Optional<City> findById(Integer cityid);

}
