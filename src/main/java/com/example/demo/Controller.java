package com.example.demo;


import com.example.demo.exceptions.NoCatedraMatchedForThisId;
import com.example.demo.exceptions.NoProfesorMatchedForThisId;
import com.example.demo.model.Catedra;
import com.example.demo.model.Profesor;
import com.example.demo.model.ProfesorCatedra;
import com.example.demo.repository.CatedraRepo;
import com.example.demo.repository.ProfesorCatedraRepo;
import com.example.demo.repository.ProfesorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    CatedraRepo catedraRepo;


    @Autowired
    ProfesorRepo profesorRepo;

    @Autowired
    ProfesorCatedraRepo profesorCatedraRepo;

    @GetMapping("/")
     public String getHelloWorld(){
        return "homepage";
    }

    @GetMapping("/profesorPage/{id}")
    public String getProfesorPage(@PathVariable Long id, Model model) throws NoProfesorMatchedForThisId{
    model.addAttribute("currentProfesor", profesorRepo.findById(id).orElseThrow(NoProfesorMatchedForThisId::new));
        System.out.println(profesorRepo.findById(id));
    return "profesorPage";
    }

    @GetMapping("/catedraPage/{id}")
    public String getCatedraPage(@PathVariable Long id, Model model) throws NoCatedraMatchedForThisId{
        model.addAttribute("currentCatedra", catedraRepo.findById(id).orElseThrow(NoCatedraMatchedForThisId::new));
        return "catedraPage";
    }


    @GetMapping("/allProfesori")
    @ResponseBody
    public ResponseEntity<List<Profesor>> getAllProfesori(){
        return new ResponseEntity<>(profesorRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/profesorById/{profesorId}")
    @ResponseBody
    public ResponseEntity<Profesor> getProfesoriById(@PathVariable Long profesorId){
      return new ResponseEntity<>(profesorRepo.findById(profesorId).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/addProfesor")
    @ResponseBody
    public ResponseEntity<Profesor> addProfesor(@RequestBody Profesor profesor){

        return new ResponseEntity<>(profesorRepo.saveAndFlush(profesor), HttpStatus.OK);
    }

    @GetMapping("/allCatedre")
    @ResponseBody
    public ResponseEntity<List<Catedra>> getAllCatedre(){
        return new ResponseEntity<>(catedraRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/catedraById/{catedraId}")
    @ResponseBody
    public ResponseEntity<Catedra> getCatedraById(@PathVariable Long catedraId){
        return new ResponseEntity<>(catedraRepo.findById(catedraId).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/addCatedra")
    @ResponseBody
    public ResponseEntity<Catedra> addCatedra(@RequestBody Catedra catedra){
        catedra.setMembri(0l);
        return new ResponseEntity<>(catedraRepo.saveAndFlush(catedra), HttpStatus.OK);
    }


    @GetMapping("/allProfesorCatedra")
    @ResponseBody
    public ResponseEntity<List<ProfesorCatedra>> getAllProfesorCatedra(){
        return new ResponseEntity<>(profesorCatedraRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/profesorCatedraById/{profesorCatedraId}")
    @ResponseBody
    public ResponseEntity<ProfesorCatedra> getProfesorCatedraById(@PathVariable Long profesorCatedraId){
        return new ResponseEntity<>(profesorCatedraRepo.findById(profesorCatedraId).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/addProfesorCatedra")
    @ResponseBody
    public ResponseEntity<ProfesorCatedra> addProfesorCatedra(@RequestParam("profesorId") Long profesorId,
                                                              @RequestParam("catedraId")Long catedraId) throws NoProfesorMatchedForThisId, NoCatedraMatchedForThisId {
        ProfesorCatedra profesorCatedra = new ProfesorCatedra();
        profesorCatedra.setProfesor(profesorRepo.findById(profesorId).orElseThrow(NoProfesorMatchedForThisId::new));
        profesorCatedra.setCatedra(catedraRepo.findById(catedraId).orElseThrow(NoCatedraMatchedForThisId::new));
        profesorCatedra.getCatedra().setMembri(profesorCatedra.getCatedra().getMembri() + 1 );
        return new ResponseEntity<>(profesorCatedraRepo.saveAndFlush(profesorCatedra), HttpStatus.OK);
    }

    @GetMapping("/catedreForSpecificProfesor/{profesorId}")
    @ResponseBody
    public ResponseEntity<List<Catedra>> getCatedreForSpecificSProfesor(@PathVariable Long profesorId){
        List<Catedra> specificCatedre = profesorCatedraRepo.findAll().stream()
                .filter(t-> t.getProfesor().getId().equals(profesorId))
                .map(ProfesorCatedra::getCatedra)
                .collect(Collectors.toList());
        return new ResponseEntity<>(specificCatedre, HttpStatus.OK);
    }

    @GetMapping("/catedreAvailableForSpecificProfesor/{profesorId}")
    @ResponseBody
    public ResponseEntity<List<Catedra>> getCatedreAvailableForSpecificProfesor(@PathVariable Long profesorId){
        List<Catedra> specificCatedre = profesorCatedraRepo.findAll().stream()
            .filter(t -> t.getProfesor().getId().equals(profesorId))
            .map(ProfesorCatedra::getCatedra)
            .collect(Collectors.toList());
        List<Catedra> availableCatedre = catedraRepo.findAll();
        specificCatedre.forEach(availableCatedre::remove);
        return new ResponseEntity<>(availableCatedre, HttpStatus.OK);
    }


    @GetMapping("/profesoriForSpecificCatedra/{catedraId}")
    @ResponseBody
    public ResponseEntity<List<Profesor>> getProfesoriForSpecificCatedra(@PathVariable Long catedraId) {
        List<Profesor> specificProfesori = profesorCatedraRepo.findAll().stream()
                .filter(t -> t.getCatedra().getId().equals(catedraId))
                .map(ProfesorCatedra::getProfesor)
                .collect(Collectors.toList());
        return new ResponseEntity<>(specificProfesori, HttpStatus.OK);
    }
         @GetMapping("/profesoriAvailableForSpecificCatedra/{catedraId}")
         @ResponseBody
        public ResponseEntity<List<Profesor>> getAvailableProfesoriForSpecificCatedra(@PathVariable Long catedraId){
           List<Profesor> specificProfesori = profesorCatedraRepo.findAll().stream()
           .filter(t-> t.getCatedra().getId().equals(catedraId))
           .map(ProfesorCatedra::getProfesor)
           .collect(Collectors.toList());
          List<Profesor> availableProfesori = profesorRepo.findAll();
          specificProfesori.forEach(availableProfesori::remove);
        return new ResponseEntity<>(availableProfesori, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProfesor/{profesorId}")
    public void deleteProfesor(@PathVariable Long profesorId){
        profesorRepo.deleteById(profesorId);
    }

    @DeleteMapping("/deleteCatedra/{catedraId}")
    public void deleteCatedra(@PathVariable Long catedraId){
        catedraRepo.deleteById(catedraId);
    }

    @DeleteMapping("/deleteProfesorCatedra")
    public void deleteProfesorCatedra(@RequestParam Long profesorId, @RequestParam Long catedraId){
        System.out.println(profesorId.toString()+ catedraId.toString());
        profesorCatedraRepo.findAll().forEach(t->{
            if(t.getCatedra().getId().equals(catedraId) && t.getProfesor().getId().equals(profesorId))
                profesorCatedraRepo.delete(t);
        });

        Catedra c1 = catedraRepo.findById(catedraId).orElse(null);
        c1.setMembri(c1.getMembri() - 1);
        catedraRepo.saveAndFlush(c1);
    }
}
