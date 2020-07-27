package br.com.alura.agenda.asynctask;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class SalvaAlunoTask extends BaseAlunoTelefoneTask{

    private final Aluno aluno;
    private final AlunoDao dao;
    private final Telefone fixo;
    private final Telefone celular;
    private final TelefoneDAO telefoneDAO;
    private final FinalizadaListener listener;

    public SalvaAlunoTask(Aluno aluno, AlunoDao dao, Telefone fixo, Telefone celular, TelefoneDAO telefoneDAO,
                          FinalizadaListener listener) {
        super(listener);
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

}
