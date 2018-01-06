package ivankuo.com.itbon2018.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ivankuo.com.itbon2018.data.model.Repo;

import static ivankuo.com.itbon2018.data.db.GithubDb.MIGRATION_1_2;
import static ivankuo.com.itbon2018.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {

    private static final String TEST_DB_NAME = "migration-test";

    @Rule
    public MigrationTestHelper testHelper =
            new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                    GithubDb.class.getCanonicalName(),
                    new FrameworkSQLiteOpenHelperFactory());

    @Test
    public void migrate1To2() throws IOException, InterruptedException {
        SupportSQLiteDatabase db = testHelper.createDatabase(TEST_DB_NAME, 1);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        insertRepo("foo_name", "foo_login", db);

        // Prepare for the next version.
        db.close();

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = testHelper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, MIGRATION_1_2);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.

        // open the db with Room.
        GithubDb githubDb = getMigratedRoomDatabase();
        final Repo loaded = getValue(githubDb.repoDao().load("foo_login", "foo_name"));
        assertThat(loaded.owner.login, is("foo_login"));
        assertThat(loaded.name, is("foo_name"));
    }

    private void insertRepo(String name, String owner_login, SupportSQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("name", name);
        values.put("stars", 50);
        values.put("owner_login", owner_login);

        db.insert("Repo", SQLiteDatabase.CONFLICT_REPLACE, values);
    }

    private GithubDb getMigratedRoomDatabase() {
        GithubDb database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                GithubDb.class, TEST_DB_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
        // close the database and release any stream resources when the test finishes
        testHelper.closeWhenFinished(database);
        return database;
    }
}