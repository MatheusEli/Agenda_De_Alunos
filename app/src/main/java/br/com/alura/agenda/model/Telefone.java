package br.com.alura.agenda.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Telefone {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String numero;
    private TipoTelefone tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }
}
