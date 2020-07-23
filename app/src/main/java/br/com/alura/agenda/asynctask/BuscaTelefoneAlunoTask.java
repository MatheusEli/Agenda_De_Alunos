package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Telefone;

public class BuscaTelefoneAlunoTask extends AsyncTask<Void,Void, Telefone> {

    private final TelefoneDAO dao;
    private final int alunoId;
    private final TextView campoTelefone;

    public BuscaTelefoneAlunoTask(TelefoneDAO dao, int alunoId, TextView campoTelefone) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.campoTelefone = campoTelefone;
    }


    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.pegaPrimeiroTelefone(alunoId);
    }

    @Override
    protected void onPostExecute(Telefone telefone) {
        super.onPostExecute(telefone);
        if (telefone != null) {
            campoTelefone.setText(telefone.getNumero());
        }
    }
}
