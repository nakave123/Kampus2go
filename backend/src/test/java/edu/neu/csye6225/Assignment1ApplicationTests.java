package edu.neu.csye6225;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import edu.neu.csye6225.controller.Health;

class Assignment1ApplicationTests {
	
	@Test
	public void validateHealthStatus() {
		assertEquals(HttpStatus.OK, HttpStatus.OK);
	}

}
