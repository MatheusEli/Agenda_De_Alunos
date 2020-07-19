package br.com.alura.agenda;

import android.app.Application;

import androidx.room.Room;

import br.com.alura.agenda.database.AgendaDataBase;
import br.com.alura.agenda.database.dao.RoomAlunoDao;
import br.com.alura.agenda.model.Aluno;

public class AgendaApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosTeste();

    }

    private void criaAlunosTeste() {
        AgendaDataBase database = Room.databaseBuilder(this, AgendaDataBase.class, "agenda.db")
                .allowMainThreadQueries()
                .build();
        RoomAlunoDao dao = database.getRoomAlunoDao();
        dao.salvar(new Aluno("Matheus Eli", "5511974507933", "matheuseli12@gmail.com"));
    }
}
