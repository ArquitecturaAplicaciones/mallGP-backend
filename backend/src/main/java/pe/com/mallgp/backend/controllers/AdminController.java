package pe.com.mallgp.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.Admin;

import pe.com.mallgp.backend.entities.Product;
import pe.com.mallgp.backend.exceptions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.AdminRepository;
import pe.com.mallgp.backend.services.AdminService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/admins")
    public ResponseEntity<List<Admin>>getAllAdmins(){
        List<Admin>admins = adminService.listAll();
        return new ResponseEntity<List<Admin>>(admins, HttpStatus.OK);
        /*List<Admin>admins;
        admins=adminRepository.findAll();
        for(Admin a:admins){
            a.setStoreMall(null);
        }
        return new ResponseEntity<List<Admin>>(admins, HttpStatus.OK);*/
    }
    //hecho

    @PostMapping("/admins")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){

        Admin newAdmin = adminService.save(new Admin(admin.getName(),admin.getPassword()));
        return new ResponseEntity<Admin>(newAdmin, HttpStatus.CREATED);
    }
    //hecho

    @DeleteMapping("/admins/{id}/forced/{forced}")
    public ResponseEntity<HttpStatus> deleteAdminByIdForced(@PathVariable("id") Long id, @PathVariable("forced") int forced) {
    /*
        if (forced==1) {
            Admin foundAdmin = adminRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("Not found New with id="+id));

        }
        adminRepository.deleteById(id);*/
        adminService.delete(id, forced);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //hecho

    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Long id){
        Admin admin=adminService.findById(id);
        return new ResponseEntity<Admin>(admin,HttpStatus.OK);
    }
    //hecho

    @PutMapping("/admins/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") Long id, @RequestBody Admin admin){
        Admin foundAdmin =adminService.findById(id);
        /*if(admin.getName()!=null)
            foundAdmin.setName(admin.getName());
        if(admin.getPassword()!=null)
            foundAdmin.setPassword(admin.getPassword());*/
        Admin updateAdmin = adminService.save(foundAdmin);
        //updateAdmin.setStoreMall(null);
        return new ResponseEntity<Admin>(updateAdmin, HttpStatus.OK);
    }//hecho

}
