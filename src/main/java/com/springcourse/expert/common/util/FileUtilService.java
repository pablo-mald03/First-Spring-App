package com.springcourse.expert.common.util;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

/*
 * Esta dentro de un paquete util porque es algo comun dentro de la aplicacion
 * este paquete es el que se comparte en la aplicacion o ayuda en general
 *
 *
 *
 *
 * ESTE ESTANDAR ES VARIABLE PERO ES BUENA PRACTICA IDENTIFICAR QUE ES UN SERVICE YA QUE OFRECE LOGICA DE NEGOCIO
 * */

@Service
public class FileUtilService {

    public String saveProductImage(MultipartFile file) {

        String uniqueFileName;
        try (InputStream inputStream = file.getInputStream()) {

            /*Se obtiene el nombre del archivo para poderlo convertir al product
             * FORMA PROVISIONAL
             * */
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            uniqueFileName = UUID.randomUUID().toString().concat("-").concat(fileName);

            /*
             * PATH RELATIVO A LA APLICACION PARA PODER ALMACENAR LOS ARCHIVOS SUBIDOS
             * */
            Path pathFile = Path.of("uploads/products/");

            if (!Files.exists(pathFile)) {
                Files.createDirectories(pathFile);
            }

            /*
             * Metodo que permite copiar el inputStream que viene en la peticion y almacenar el archivo
             * */
            Files.copy(inputStream, pathFile.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception ex) {
            throw new RuntimeException("Cant read the input file");
        }

        return uniqueFileName;
    }
}
