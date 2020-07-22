package br.com.alura.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.alura.agenda.model.TipoTelefone;

import static br.com.alura.agenda.model.TipoTelefone.FIXO;

public class AgendaMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            //Criar nova tabela com as informações desejadas;

            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefone` TEXT, " +
                    "`email` TEXT)");

            //Copiar dados da tabela antiga para a nova;

            database.execSQL("INSERT INTO Aluno_novo(id,nome,telefone,email) SELECT id, nome, telefone, email FROM Aluno");

            //Remove tabela antiga;

            database.execSQL("DROP TABLE Aluno");

            //Renomear a tabela nova com o nome da tabela antiga;

            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
        }
    };
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN momentoDeCadastro INTEGER");
        }
    };


    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            //Criar nova tabela com as informações desejadas;
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefoneFixo` TEXT, " +
                    "`telefoneCelular` TEXT," +
                    "`email` TEXT, " +
                    "`momentoDeCadastro` INTEGER)");

            //Copiar dados da tabela antiga para a nova;
            database.execSQL("INSERT INTO Aluno_novo (id,nome,telefoneFixo,email,momentoDeCadastro) SELECT id, nome, telefone, email, momentoDeCadastro FROM Aluno");


            //Remove tabela antiga;

            database.execSQL("DROP TABLE Aluno");

            //Renomear a tabela nova com o nome da tabela antiga;

            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");

        }
    };

    private static final Migration MIGRATION_5_6 = new Migration(5,6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            //Criação e cópia da tabela para aluno;
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`email` TEXT, " +
                    "`momentoDeCadastro` INTEGER)");

            database.execSQL("INSERT INTO Aluno_novo(id,nome,email,momentoDeCadastro) SELECT id, nome, email, momentoDeCadastro FROM Aluno");

            //Criação da tabela para telefone;
            database.execSQL("CREATE TABLE IF NOT EXISTS `Telefone` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`numero` TEXT, " +
                    "`tipo` TEXT, " +
                    "`idAluno` INTEGER NOT NULL)");

            //Envio de telefones da tabela aluno para telefone;
            database.execSQL("INSERT INTO Telefone(numero, idAluno) SELECT telefoneFixo, id FROM Aluno");

            //Atualização do tipo do telefone para fixo em todos;
            database.execSQL("UPDATE Telefone SET tipo = ?", new TipoTelefone[] {FIXO});

            //Remove tabela antiga;
            database.execSQL("DROP TABLE Aluno");

            //Renomear a tabela nova com o nome da tabela antiga;
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
        }
    };

    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6};
}
