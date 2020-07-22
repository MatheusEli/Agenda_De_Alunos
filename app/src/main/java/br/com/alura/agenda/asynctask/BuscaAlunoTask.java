package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class BuscaAlunoTask extends AsyncTask {


    private final AlunoDao dao;
    private final ListaAlunosAdapter adapter;

    public BuscaAlunoTask(AlunoDao dao, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        adapter.atualiza(dao.todos());
        return null;
    }
}
