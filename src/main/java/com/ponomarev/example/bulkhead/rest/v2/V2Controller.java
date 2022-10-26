package com.ponomarev.example.bulkhead.rest.v2;

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
@RequestMapping("/v2")
public class V2Controller {
    private static final Logger LOGGER = Logger.getLogger(V2Controller.class.getSimpleName());

    @GetMapping
    public void performAction() throws InterruptedException {
        LOGGER.log(Level.INFO, "V2 endpoint operation is in progress");
        Thread.sleep(500);
    }
}
