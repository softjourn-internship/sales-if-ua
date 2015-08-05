package sales.users.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sales.users.domain.City;
import sales.users.domain.Country;
import sales.users.domain.Region;
import sales.users.service.CityService;
import sales.users.service.CountryService;
import sales.users.service.RegionService;

import sales.users.domain.Country;
import sales.users.service.CountryService;

import java.util.List;

/**
 * Created by taras on 04.08.15.
 */
@RestController
@RequestMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

    final static Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CityService cityService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/countries")
    public List<Country> getAllCountries() {
        logger.debug("Get list of countries");
        return countryService.getAll();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/country/{id}")
    public Country getOneCountry(@PathVariable("id") Long id) {
        logger.debug("Get country by id");
        return countryService.getOne(id);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/country")
    public Country getOneCountryByName(@RequestParam(required = true, value = "name") String name) {
        logger.debug("Get country by name");
        return countryService.getOneByName(name);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/country/{id}/regions")
    public List<Region> getAllRegions(@PathVariable("id") Long id) {
        logger.debug("Get list of regions");
        return regionService.getAll(countryService.getOne(id));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/country/{id}/region/{idR}")
    public Region getOneRegion(@PathVariable("id") Long id, @PathVariable("idR") Long idR) {
        logger.debug("Get region by id");
        return regionService.getOne(countryService.getOne(id), idR);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/region")
    public Region getOneRegionByName(@RequestParam(required = true, value = "name") String name) {
        logger.debug("Get region by name");
        return regionService.getOneByName(name);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/country/{id}/region/{idR}/cities")
    public List<City> getAllCities(@PathVariable("id") Long id,
                                   @PathVariable("idR") Long idR) {
        logger.debug("Get list of cities");
        return cityService.getAll(regionService.getOne(countryService.getOne(id), idR));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/country/{id}/region/{idR}/city/{idC}")
    public City getOneCity(@PathVariable("id") Long id,
                           @PathVariable("idR") Long idR,
                           @PathVariable("idC") Long idC) {
        logger.debug("Get city by id");
        return cityService.getOne(regionService.getOne(countryService.getOne(id), idR), idC);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/city")
    public City getOneCityByName(@RequestParam(required = true, value = "name") String name) {
        logger.debug("Get city by name");
        return cityService.getOneByName(name);
    }
}
