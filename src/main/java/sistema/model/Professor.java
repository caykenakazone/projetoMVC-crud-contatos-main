package sistema.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Professor {
    private int id;
    private String nome;

    public Professor(int id,String nome){
        this.id = id;
        this.nome = nome;
    }
}
