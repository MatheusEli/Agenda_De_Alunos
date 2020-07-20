package br.com.alura.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;

import static br.com.alura.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class}, version = 5, exportSchema = false)
@TypeConverters({ConversorCalendar.class})

public abstract class AgendaDataBase extends RoomDatabase {

    public abstract AlunoDao getRoomAlunoDao();

    public static AgendaDataBase getInstance(Context context) {

        return Room.databaseBuilder(context, AgendaDataBase.class, "agenda.db").allowMainThreadQueries()
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }
}
