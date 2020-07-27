package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.asynctask.BaseAlunoTelefoneTask;
import br.com.alura.agenda.asynctask.BuscaTodosTelefonesDoAluno;
import br.com.alura.agenda.asynctask.EditaAlunoTask;
import br.com.alura.agenda.asynctask.SalvaAlunoTask;
import br.com.alura.agenda.database.AgendaDataBase;
import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

import static br.com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private TextView campoNome;
    private TextView campoTelefoneFixo;
    private TextView campoTelefoneCelular;
    private TextView campoEmail;
    private AlunoDao dao;
    private Aluno aluno;
    private TelefoneDAO telefoneDAO;
    private List<Telefone> telefonesDoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        dao = AgendaDataBase.getInstance(this).getRoomAlunoDao();
        telefoneDAO = AgendaDataBase.getInstance(this).getTelefoneDAO();
        inicializacaoDosCampos();
        carregaAlunos();
    }
    private void carregaAlunos() {
        Intent dados = getIntent();

        if (dados.hasExtra(CHAVE_ALUNO)) {
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheOsCampos();
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }
    private void preencheOsCampos() {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        preencheCamposDeTelefone();
    }
    private void preencheCamposDeTelefone() {

        new BuscaTodosTelefonesDoAluno(telefoneDAO, aluno, new BuscaTodosTelefonesDoAluno.BuscaTodosTelefonesListener() {
            @Override
            public void buscaListener(List<Telefone> telefones) {

                telefonesDoAluno = telefones;
                for (Telefone telefone :
                        telefonesDoAluno) {
                    if(telefone.getTipo() == TipoTelefone.FIXO){

                        campoTelefoneFixo.setText(telefone.getNumero());
                    }else{

                        campoTelefoneCelular.setText(telefone.getNumero());
                    }
                }
            }
        }).execute();

    }
    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_aluno_telefone_fixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_aluno_telefone_celular);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salvar){

            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }
    private void finalizaFormulario() {
        preencheAluno();
        Telefone fixo = criaTelefone(R.id.activity_formulario_aluno_telefone_fixo, TipoTelefone.FIXO);
        Telefone celular = criaTelefone(R.id.activity_formulario_aluno_telefone_celular, TipoTelefone.CELULAR);
        if (aluno.temIdValido()) {
            editaAluno(fixo, celular);
        } else {
            salvaAluno(fixo, celular);
        }
    }
    private Telefone criaTelefone(int p, TipoTelefone fixo2) {
        TextView telefoneFixo = findViewById(p);
        return new Telefone(telefoneFixo.getText().toString(), fixo2);
    }
    private void salvaAluno(Telefone fixo, Telefone celular) {
        new SalvaAlunoTask(aluno, dao, fixo, celular, telefoneDAO, this::finish).execute();
    }
    private void editaAluno(Telefone fixo, Telefone celular) {
        new EditaAlunoTask(dao, aluno, telefoneDAO, fixo, celular, telefonesDoAluno, new BaseAlunoTelefoneTask.FinalizadaListener() {
            @Override
            public void quandoFinalizada() {
                finish();
            }
        }).execute();
    }
    private void preencheAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
    }
}