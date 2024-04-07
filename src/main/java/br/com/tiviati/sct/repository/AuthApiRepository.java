package br.com.tiviati.sct.repository;

import br.com.tiviati.sct.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthApiRepository extends JpaRepository<Token, String>  {
    @Query(nativeQuery= true, value="SELECT * FROM tiviati_security WHERE akey = ?")
    Token findByApiKey(String apiKey);
}
