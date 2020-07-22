package br.com.alura.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.converter.ConversorTipoTelefone;
import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

import static br.com.alura.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTipoTelefone.class})

public abstract class AgendaDataBase extends RoomDatabase {

    public static final String NOME_BANCO_DE_DADOS = "agenda.db";

    public abstract AlunoDao getRoomAlunoDao();
    public abstract TelefoneDAO getTelefoneDAO();

    public static AgendaDataBase getInstance(Context context) {

        return Room.databaseBuilder(context, AgendaDataBase.class, NOME_BANCO_DE_DADOS)
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }
}
