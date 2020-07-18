package br.com.alura.agenda.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import br.com.alura.agenda.dao.DAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {


    private final Context context;
    private final DAO dao;
    private final ListaAlunosAdapter adapter;

    public ListaAlunosView(Context context) {
        this.context = context;
        dao = new DAO();
        adapter = new ListaAlunosAdapter(context);
    }

    @SuppressWarnings("unchecked")
    public void atualizaAlunos() {

        adapter.atualiza(dao.todos());
    }

    public void confirmaRemocao(@NonNull final MenuItem item) {
        new AlertDialog.Builder(context).setTitle("Removendo Aluno")
                .setMessage("Tem certeza que deseja remover o aluno?")
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Aluno aluno = adapter.getItem(menuinfo.position);
                        remove(aluno);
                    }
                })
                .setNegativeButton("não", null)
                .show();
    }

    private void remove(Aluno aluno) {
        dao.remover(aluno);
        adapter.remove(aluno);
    }


    public void configuraAdapter(ListView listaDeAlunos) {
        listaDeAlunos.setAdapter(adapter);
    }
}
