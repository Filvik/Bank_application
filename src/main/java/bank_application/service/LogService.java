package bank_application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;

@Service
public class LogService {
    private static final Logger log = LogManager.getLogger(AccountService.class);
    private static final String path = "logs/application.log";

    /**
     * Метод проверяет есть ли файл с логами и возвращает подготовленный ответ.
     *
     * @return Объект класса ResponseEntity.
     */
    public ResponseEntity<InputStreamResource> logs() {

        File logs = new File(path);

        if(logs.exists()){

            String fileNameRef = "application.log";

            try {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNameRef + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(logs.length()) //
                        .body(new InputStreamResource(new FileInputStream(logs)));
            } catch (Exception exception) {
                log.error("Внутренняя ошибка сервера: " + exception);
            }
        }
        log.error("Файл с логами не найден!");
        return ResponseEntity.badRequest().build();
    }
}
