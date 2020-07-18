package br.com.alura.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.Aluno;

public class DAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salvar(Aluno aluno) {

        aluno.setId(contadorDeIds);
        alunos.add(aluno);
        atualizaIds();
    }

    private void atualizaIds() {
        contadorDeIds++;
    }

    public void editar(Aluno aluno) {


        Aluno alunoEscolhido = buscaAlunoPeloId(aluno);

        if (alunoEscolhido != null) {

            alunos.set(alunos.indexOf(alunoEscolhido), aluno);
        }
    }

    private Aluno buscaAlunoPeloId(Aluno aluno) {
        for (Aluno a : alunos) {
            if (a.getId() == aluno.getId()) {
                return a;
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList todos() {
        return new ArrayList<>(alunos);
    }

    public void remover(Aluno aluno) {
        Aluno alunoDevolvido = buscaAlunoPeloId(aluno);
        if (alunoDevolvido != null) {
            alunos.remove(alunoDevolvido);
        }
    }
}
