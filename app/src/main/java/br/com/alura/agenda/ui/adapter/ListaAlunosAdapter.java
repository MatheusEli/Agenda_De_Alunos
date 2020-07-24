package br.com.alura.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.asynctask.BuscaTelefoneAlunoTask;
import br.com.alura.agenda.database.AgendaDataBase;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class ListaAlunosAdapter extends BaseAdapter {


    private final List<Aluno> alunos = new ArrayList<>();
    private final Context context;
    private final TelefoneDAO dao;

    public ListaAlunosAdapter(Context context) {
        this.context = context;
        dao = AgendaDataBase.getInstance(context).getTelefoneDAO();
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View viewCriada = criaView(viewGroup);
        Aluno alunoDevolvido = alunos.get(posicao);
        vincula(viewCriada, alunoDevolvido);
        return viewCriada;
    }

    private void vincula(View viewCriada, Aluno alunoDevolvido) {
        TextView nome = viewCriada.findViewById(R.id.item_aluno_nome);
        nome.setText(alunoDevolvido.getNome());
        final TextView campoTelefone = viewCriada.findViewById(R.id.item_aluno_telefone);

        new BuscaTelefoneAlunoTask(dao, alunoDevolvido.getId(), new BuscaTelefoneAlunoTask.BuscaTelefoneAlunoListener() {
            @Override
            public void buscaListener(Telefone telefone) {
                if (telefone != null) {
                    campoTelefone.setText(telefone.getNumero());
                }
            }
        });
//
    }

    private View criaView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_aluno, viewGroup, false);
    }

    public void atualiza(List<Aluno> alunos){

        this.alunos.clear();
        this.alunos.addAll(alunos);
        notifyDataSetChanged();

    }
    public void remove(Aluno aluno) {

        alunos.remove(aluno);
        notifyDataSetChanged();
    }
}
