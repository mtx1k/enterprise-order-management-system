package com.final_project.ua_team_final_project.services;

import java.io.InputStream;
import java.util.List;

public interface StorageService {

    public List<String> listCsvFiles();

    public InputStream downloadFile(String fileName);

}
