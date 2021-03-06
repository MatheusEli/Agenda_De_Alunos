package br.com.alura.agenda.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import br.com.alura.agenda.asynctask.BuscaAlunoTask;
import br.com.alura.agenda.asynctask.RemoveAlunoTask;
import br.com.alura.agenda.database.AgendaDataBase;
import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {


    private final Context context;
    private final AlunoDao dao;
    private final ListaAlunosAdapter adapter;

    public ListaAlunosView(Context context) {
        this.context = context;
        dao = AgendaDataBase.getInstance(context).getRoomAlunoDao();
        adapter = new ListaAlunosAdapter(context);
    }

    @SuppressWarnings("unchecked")
    public void atualizaAlunos() {

        new BuscaAlunoTask(dao, adapter).execute();
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
        new RemoveAlunoTask(dao, adapter, aluno);
    }


    public void configuraAdapter(ListView listaDeAlunos) {
        listaDeAlunos.setAdapter(adapter);
    }
}
