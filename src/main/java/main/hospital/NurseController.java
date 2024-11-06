package main.hospital;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/nurse")
public class NurseController {
    private final NurseRepository nurseRepository;
    private final NurseService nurseService;

    @Autowired
    public NurseController(NurseService nurseService, NurseRepository nurseRepository) {
    	this.nurseRepository = nurseRepository;
        this.nurseService = nurseService;
    }

	// This method is mapped to the HTTP POST request at the "/login" endpoint.
	// It handles user authentication by verifying the provided username and password.
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Nurse nurseLogin) {
		boolean isAuthenticated = nurseService.LoginAuthentication(nurseLogin.getUser(), nurseLogin.getPassword());
		return isAuthenticated ? ResponseEntity.ok("Welcome to the application!")
				: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login.");
	}

	// This method is mapped to the HTTP GET request at the "/index" endpoint.
	// It retrieves a list of all nurses from the database.
	@GetMapping("/index")
	public ResponseEntity<List<Nurse>> getAll() {
	    List<Nurse> nurses = new ArrayList<>();
	    nurseRepository.findAll().forEach(nurses::add);
	    return ResponseEntity.ok(nurses);
	}
	
	// This method is mapped to the HTTP GET request at the "/name/{name}" endpoint.
	// It retrieves a nurse from the database by their name.
	@GetMapping("/name/{name}")	
	public ResponseEntity<Nurse> findByName(@PathVariable String name) {
		return nurseService.findByName(name);
	}
	
	// This method is mapped tothe HTTP POST request at the "/create" endpoint.
	// It creates a new nurse and returns the saved nurse.
	@PostMapping("/create")
	public ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) {
	    nurse.setId(null);
	    Nurse savedNurse = nurseRepository.save(nurse);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedNurse);
	}
	
	//Read
	//Update
	
	// This method is mapped tothe HTTP DELETE request at the "/delete/{id}" endpoint.
	// It deletes a nurse.
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteNurse(@PathVariable Integer id) {
	    if (nurseRepository.existsById(id)) {
	        nurseRepository.deleteById(id);
	        return ResponseEntity.ok("Nurse deleted successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found");
	    }
	}
}