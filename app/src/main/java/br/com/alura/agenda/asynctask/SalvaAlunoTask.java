package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class SalvaAlunoTask extends AsyncTask<Void, Void, Void> {

    private final Aluno aluno;
    private final AlunoDao dao;
    private final Telefone fixo;
    private final Telefone celular;
    private final TelefoneDAO telefoneDAO;
    private final QuandoAlunoSalvoListener listener;

    public SalvaAlunoTask(Aluno aluno, AlunoDao dao, Telefone fixo, Telefone celular, TelefoneDAO telefoneDAO,
                          QuandoAlunoSalvoListener listener) {
        this.aluno = aluno;
        this.dao = dao;
        this.fixo = fixo;
        this.celular = celular;
        this.telefoneDAO = telefoneDAO;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int alunoId = dao.salvar(aluno).intValue();
        vinculaAlunoComTelefone(alunoId, fixo, celular);
        telefoneDAO.adiciona(fixo, celular);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoSalvo();
    }

    private void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setIdAluno(alunoId);
        }
    }

    public interface QuandoAlunoSalvoListener {
        void quandoSalvo();
    }
}
