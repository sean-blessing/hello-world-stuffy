package com.stuffy.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stuffy.business.Stuffy;
import com.stuffy.db.StuffyRepository;

@CrossOrigin
@RestController
@RequestMapping("/stuffies")
public class StuffyController {
	@Autowired
	private StuffyRepository stuffyRepo;
	
	// list - return all stuffies
	@GetMapping("/")
	public JsonResponse listStuffies() {
		JsonResponse jr = null;
		try  {
			jr = JsonResponse.getInstance(stuffyRepo.findAll());			
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/list")
	public List<Stuffy> listStuffiesNoJR() {
		List<Stuffy> stuffies = stuffyRepo.findAll();
		ArrayList<Stuffy> stuffyArrayList = new ArrayList<>(stuffies);
		System.out.println(stuffyArrayList);
		return stuffies;
	}
	
	@GetMapping("/list-by-color")
	public List<Stuffy> listStuffiesByColor(@RequestParam String color) {
		List<Stuffy> stuffies = stuffyRepo.findAllByColor(color);
		return stuffies;
	}
	
	
	
	// get - return 1 stuff for the given id
	@GetMapping("/{id}")
	public JsonResponse getStuffy(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.findById(id));
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	// demo of Request Parameters
	// url = http://localhost:8080/stuffies?id=10&type=Fish&color=pink&size=small&limbs=0
//	@GetMapping("")
//	public Stuffy createAStuffy(@RequestParam int id, @RequestParam String type, 
//			@RequestParam String color, @RequestParam String size, @RequestParam int limbs) {
//		Stuffy s = new Stuffy(id, type, color, size, limbs);
//		return s;
//	}
	
	// add - adds a new Stuffy
	@PostMapping("/")
	public JsonResponse addStuffy(@RequestBody Stuffy s) {
		// add a new stuffy
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.save(s));
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	// update - update a Stuffy
	@PutMapping("/")
	public JsonResponse updateStuffy(@RequestBody Stuffy s) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (stuffyRepo.existsById(s.getId())) {
				jr = JsonResponse.getInstance(stuffyRepo.save(s));
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating Stuffy.  id: "+
											s.getId() + " doesn't exist!");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	// delete - delete a Stuffy
	@DeleteMapping("/{id}")
	public JsonResponse deleteStuffy(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		
		try {
			if (stuffyRepo.existsById(id)) {
				stuffyRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful!");
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error deleting Stuffy.  id: "+
											id + " doesn't exist!");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

}
