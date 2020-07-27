package br.com.alura.agenda.asynctask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;

public class EditaAlunoTask extends BaseAlunoTelefoneTask {
    private final AlunoDao dao;
    private final Aluno aluno;
    private final TelefoneDAO telefoneDAO;
    private final Telefone fixo;
    private final Telefone celular;
    private final List<Telefone> telefonesDoAluno;
    private final FinalizadaListener listener;

    public EditaAlunoTask(AlunoDao dao,
                          Aluno aluno,
                          TelefoneDAO telefoneDAO,
                          Telefone fixo,
                          Telefone celular,
                          List<Telefone> telefonesDoAluno,
                          FinalizadaListener listener) {
        super(listener);
        this.dao = dao;
        this.aluno = aluno;
        this.telefoneDAO = telefoneDAO;
        this.fixo = fixo;
        this.celular = celular;
        this.telefonesDoAluno = telefonesDoAluno;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.editar(aluno);
        vinculaAlunoComTelefone(aluno.getId(),fixo, celular);
        atualizaIdsDosTelefones(fixo, celular);
        telefoneDAO.atualiza(fixo, celular);
        return null;
    }


    private void atualizaIdsDosTelefones(Telefone fixo, Telefone celular) {
        for (Telefone telefone :
                telefonesDoAluno) {
            if(telefone.getTipo() == TipoTelefone.FIXO){

                telefone.setId(fixo.getId());
            }else{

                telefone.setId(celular.getId());
            }
        }
    }

}
