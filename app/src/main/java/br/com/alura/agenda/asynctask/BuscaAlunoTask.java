package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class BuscaAlunoTask extends AsyncTask<Void,Void,List<Aluno>> {


    private final AlunoDao dao;
    private final ListaAlunosAdapter adapter;

    public BuscaAlunoTask(AlunoDao dao, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected List<Aluno> doInBackground(Void... voids) {
        List<Aluno> todosAlunos = dao.todos();
        return todosAlunos;
    }

    @Override
    protected void onPostExecute(List<Aluno> alunos) {
        super.onPostExecute(alunos);
        adapter.atualiza(alunos);
    }
}
