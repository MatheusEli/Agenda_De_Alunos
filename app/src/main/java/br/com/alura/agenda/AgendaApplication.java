package br.com.alura.agenda;
import android.app.Application;

import br.com.alura.agenda.dao.DAO;
import br.com.alura.agenda.model.Aluno;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosTeste();

    }

    private void criaAlunosTeste() {
        DAO dao = new DAO();
        dao.salvar(new Aluno("Matheus Eli", "5511974507933", "matheuseli12@gmail.com"));
    }
}
