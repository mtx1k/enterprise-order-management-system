package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.repositories.AvailableProductsRepository;

public class DBPricelistCRUDServiceImpl implements DBPricelistCRUDService {

    private AvailableProductsRepository availableProductsRepository;

    @Override
    public void createFinalPricelist() {
    }

    @Override
    public void readAllProducts() {

    }

    @Override
    public void updatePriceLists() {

    }

    @Override
    public void deleteAllProducts() {

        availableProductsRepository.deleteAll();

    }
}
