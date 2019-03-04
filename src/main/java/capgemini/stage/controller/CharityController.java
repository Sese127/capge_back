package capgemini.stage.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capgemini.stage.model.charity.Charity;
import capgemini.stage.model.charity.ICharityRepository;
import capgemini.stage.model.city.City;
import capgemini.stage.model.city.ICityRepository;

@CrossOrigin(origins="http://localhost:8080")
@RestController
@RequestMapping("/charities")
public class CharityController {


			@Autowired 
			private ICharityRepository charityRepo;
			
			@Autowired
			private ICityRepository citys;
			
			
			@GetMapping(value="")
			//methode pour consulter les associations
			public List<Charity> getCharity(){
				return charityRepo.findAll();
			}
			
			@GetMapping(value="/{id}")
			//afficher une association
			public Charity getAssociation(@PathVariable Long id){
				return  charityRepo.findById(id).orElse(null);		
		}
			
			@PostMapping(value="/add")
			//methode pour créer une association
			public Charity save(@RequestBody Charity c){
				City paris = citys.findById((long) c.getCity().getId()).get();
				c.setCity(paris);
				return charityRepo.save(c);
			}
			
			@PutMapping(value="/update")
			//modifier una association
			public Charity update(@RequestBody Charity c){
				return charityRepo.save(c);
		}
			
			@DeleteMapping(value="/{id}")
			//supprimer une association
			public ResponseEntity<Charity> delete (@PathVariable Long id){
				Charity asso = charityRepo.findById(id).get();
				if (asso==null)
				{
					System.out.println("cette association n'existe pas");
					return new ResponseEntity<Charity>(HttpStatus.NOT_FOUND);
				}
				charityRepo.deleteById(id);
				System.out.println("cette association n'existe pas");
				return new ResponseEntity<Charity>(HttpStatus.NO_CONTENT);
				
		}
			

		}
	