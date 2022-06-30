/**
 * 
 */
package edu.neu.csye6225.controller;

import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pratiknakave
 *
 */
@RestController
public class Health {

	private static final Logger LOGGER = LoggerFactory.getLogger(Health.class);

	@Autowired
	private StatsDClient metricClient;

	@GetMapping("/health")
	public ResponseEntity<String> getStatus() {

		metricClient.incrementCounter("endpoint.healthz.http.get");
		LOGGER.info("Getting health status");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-control-allow-credentials", "true");
		responseHeaders.set("access-control-allow-headers", "X-Requested-With,Content-Type,Accept,Origin");
		responseHeaders.set("access-control-allow-origin", "*");
		responseHeaders.set("access-control-allow-methods", "*");
		responseHeaders.set("cache-control", "no-cache");
		responseHeaders.set("content-encoding", "gzip");
		responseHeaders.set("content-type", "application/json; charset=utf-8");
		LOGGER.info("Health status returned OK response");

		return new ResponseEntity<String>(null, responseHeaders, HttpStatus.OK);
	}
}
