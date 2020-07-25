package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class SalvaAlunoTask extends AsyncTask {

    private final Aluno aluno;
    private final AlunoDao dao;
    private final Telefone fixo;
    private final Telefone celular;
    private final TelefoneDAO telefoneDAO;
    private final SalvaAlunoListener listener;

    public SalvaAlunoTask(Aluno aluno, AlunoDao dao, Telefone fixo, Telefone celular, TelefoneDAO telefoneDAO,
                          SalvaAlunoListener listener) {
        this.aluno = aluno;
        this.dao = dao;
        this.fixo = fixo;
        this.celular = celular;
        this.telefoneDAO = telefoneDAO;
        this.listener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        int idAluno = dao.salvar(aluno).intValue();
        vinculaAlunoComTelefone(idAluno, fixo, celular);
        telefoneDAO.adiciona(fixo, celular);
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        listener.AlunoSalvo();
    }

    private void vinculaAlunoComTelefone(int idAluno, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setIdAluno(idAluno);
        }

    }

    public interface SalvaAlunoListener{

        void AlunoSalvo();
    }

}
