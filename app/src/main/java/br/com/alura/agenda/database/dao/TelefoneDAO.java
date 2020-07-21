package br.com.alura.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import br.com.alura.agenda.model.Telefone;

@Dao
public interface TelefoneDAO {

    @Query("SELECT t.* FROM Telefone t JOIN Aluno a ON t.idAluno = a.id WHERE t.idAluno = :idAluno LIMIT 1")
    Telefone pegaPrimeiroTelefone(int idAluno);
}
