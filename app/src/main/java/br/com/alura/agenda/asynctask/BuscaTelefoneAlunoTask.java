package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Telefone;

public class BuscaTelefoneAlunoTask extends AsyncTask<Void,Void, Telefone> {

    private final TelefoneDAO dao;
    private final int alunoId;
    private final BuscaTelefoneAlunoListener listener;

    public BuscaTelefoneAlunoTask(TelefoneDAO dao, int alunoId, BuscaTelefoneAlunoListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }


    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.pegaPrimeiroTelefone(alunoId);
    }

    @Override
    protected void onPostExecute(Telefone telefone) {
        super.onPostExecute(telefone);
        listener.buscaListener(telefone);
    }

    public interface BuscaTelefoneAlunoListener{

       void buscaListener(Telefone telefone);

    }
}
