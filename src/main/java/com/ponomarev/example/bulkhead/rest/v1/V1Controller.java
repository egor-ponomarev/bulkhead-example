package com.ponomarev.example.bulkhead.rest.v1;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller with prefix v1
 * @author Egor Ponomarev
 */
@RestController
@RequestMapping("/v1")
public class V1Controller {
    private static final Logger LOGGER = Logger.getLogger(V1Controller.class.getSimpleName());

    @GetMapping
    public void performAction() throws InterruptedException {
        LOGGER.log(Level.INFO, "V1 endpoint operation is in progress");
        Thread.sleep(500);
    }
}
