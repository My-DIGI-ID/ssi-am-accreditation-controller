package com.bka.ssi.controller.accreditation.company.application.utilities;

import com.bka.ssi.controller.accreditation.company.application.exceptions.JsonParseException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Component
public class I18nJsonParser {

    private final static String JSON_FILE_EXTENSION = ".json";

    private final String path;

    private final Logger logger;

    public I18nJsonParser(@Value("src/main/resources/i18n/ui/") String defaultPath,
        @Value("${accreditation.ui.i18n.path}") String path, Logger logger)
        throws NotFoundException {
        this.logger = logger;

        if (this.isValidDirectory(path)) {
            this.path = path;
        } else {
            if (this.isValidDirectory(defaultPath)) {
                logger.warn(
                    "Path {} for i18n files does not exist or is not a directory. Fallback to "
                        + "default path {}.", path, defaultPath);
                this.path = defaultPath;
            } else {
                logger.error("Default path {} for i18n files does not exist or is not a directory.",
                    defaultPath);
                throw new NotFoundException(
                    "Default path for i18n files does not exist or is not a directory");
            }
        }
    }

    private boolean isValidDirectory(String path) throws NotFoundException {
        if (path == null) {
            logger.error("Path is null");
            throw new NotFoundException("Path is null");
        }

        File f = new File(path);
        return f.exists() || f.isDirectory();
    }

    private boolean isValidFile(String path) throws NotFoundException {
        if (path == null) {
            logger.error("Path is null");
            throw new NotFoundException("Path is null");
        }

        File f = new File(path);
        return f.exists() || f.isFile();
    }

    private String getFileNameWithFileExtension(String filename) throws NotFoundException {
        if (filename == null) {
            logger.error("File name is null");
            throw new NotFoundException("File name is null");
        }

        if (filename.length() >= 5) {
            return filename.substring(filename.length() - 5).equals(JSON_FILE_EXTENSION)
                ? filename : filename + JSON_FILE_EXTENSION;
        }

        return filename + JSON_FILE_EXTENSION;
    }

    private String getPathWithEndingSlash(String path) throws NotFoundException {
        if (path == null) {
            logger.error("Path is null");
            throw new NotFoundException("Path is null");
        }

        if (path.length() >= 1) {
            return path.substring(path.length() - 1).equals("/")
                ? path : path + "/";
        }

        return "/";
    }

    public Object parseI18nJsonFile(String filename) throws NotFoundException, JsonParseException {
        String fullPath =
            this.getPathWithEndingSlash(this.path) + this.getFileNameWithFileExtension(filename);

        if (!this.isValidFile(fullPath)) {
            logger.error("i18n file {} does not exist or is not a file", fullPath);
            throw new NotFoundException("i18n file does not exist or is not a file");
        }

        File f = new File(fullPath);
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            FileReader reader = new FileReader(f);
            object = jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            logger.error("i18n file {} does not exist", fullPath);
            throw new NotFoundException("18n file does not exist");
        } catch (IOException | ParseException e) {
            logger.error("Failed to parse i18n file {}", fullPath);
            throw new JsonParseException();
        }

        return object;
    }
}
