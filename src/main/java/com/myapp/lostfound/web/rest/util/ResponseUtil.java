package com.myapp.lostfound.web.rest.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public interface ResponseUtil {

    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

}
