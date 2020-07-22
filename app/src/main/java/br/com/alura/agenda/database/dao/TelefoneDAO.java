package br.com.alura.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.alura.agenda.model.Telefone;

@Dao
public interface TelefoneDAO {

    @Query("SELECT * FROM Telefone WHERE idAluno = :idAluno LIMIT 1")
    Telefone pegaPrimeiroTelefone(int idAluno);

    @Insert
    void adiciona(Telefone... telefones);

    @Query("SELECT * FROM Telefone WHERE idAluno = :idAluno")
    List<Telefone> devolveTelefonesDoAluno(int idAluno);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void atualiza(Telefone... telefones);
}
