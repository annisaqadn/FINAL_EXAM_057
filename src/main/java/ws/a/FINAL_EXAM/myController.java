/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.FINAL_EXAM;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fuadi
 */
@RestController
@ResponseBody
public class myController {
    Surat data = new Surat(); 
    SuratJpaController ctrl = new SuratJpaController();
    
    //Menampilkan data Surat
    @GetMapping(value= "/GET", produces = APPLICATION_JSON_VALUE)
    public List<Surat> getData(){
        try{
            List<Surat> buffer = new ArrayList<>(); 
            buffer = ctrl.findSuratEntities();
            return buffer; //Jika data tersedia
        } catch (Exception e) {
            return List.of(); //Jika data tidak tersedia
        }
    }
    
    //Menambahkan data Surat
    @PostMapping(value= "/POST", consumes = APPLICATION_JSON_VALUE)
    public String postData(HttpEntity<String> senddata) throws JsonProcessingException{ //Mengambil data pada tabel Produk
        String feedback = "Do Noting";
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(senddata.getBody(), Surat.class);
        try {
            ctrl.create(data);
            feedback = data.getJudul()+" Saved";
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }
    
    //Mengubah/edit data Surat
    @PutMapping(value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> senddata) throws JsonProcessingException{ 
        String feedback = "Do Nothing";
        
        ObjectMapper maper = new ObjectMapper();
        data = maper.readValue(senddata.getBody(), Surat.class);
        try{
            ctrl.edit(data);
            feedback = data.getJudul()+ " Edited";
        }catch(Exception error){
            feedback = error.getMessage();
        }
        return feedback;
    }
    
    //Menghapus data Surat
    @DeleteMapping(value = "/DELETE", consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> senddata) throws JsonProcessingException{
        String feedback = "Do Nothing";
        
        ObjectMapper maper = new ObjectMapper();
        data = maper.readValue(senddata.getBody(), Surat.class);
        try{
            ctrl.destroy(data.getId());
            feedback = "Data has been Deleted";
        }catch(Exception error){
            feedback = error.getMessage();
        }
        return feedback;
    }
}