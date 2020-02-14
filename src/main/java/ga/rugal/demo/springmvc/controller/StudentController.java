package ga.rugal.demo.springmvc.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Student controller.
 *
 * @author Rugal Bernstein
 */
@Api(tags = "StudentController")
@RestController
@Slf4j
public class StudentController {

  @GetMapping("/test")
  public ResponseEntity<Void> getStudent() {
    return ResponseEntity.ok().build();
  }
}
