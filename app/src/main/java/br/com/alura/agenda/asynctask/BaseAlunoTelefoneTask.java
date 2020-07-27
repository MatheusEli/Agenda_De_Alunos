package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.model.Telefone;

public abstract class BaseAlunoTelefoneTask extends AsyncTask<Void, Void, Void> {

    private final FinalizadaListener listener;

    BaseAlunoTelefoneTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setIdAluno(alunoId);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizada();
    }

    public interface FinalizadaListener{

        void quandoFinalizada();
    }
}
