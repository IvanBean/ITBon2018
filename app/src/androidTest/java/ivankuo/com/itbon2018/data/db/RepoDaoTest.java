package ivankuo.com.itbon2018.data.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ivankuo.com.itbon2018.data.model.Owner;
import ivankuo.com.itbon2018.data.model.Repo;
import ivankuo.com.itbon2018.util.LiveDataTestUtil;
import ivankuo.com.itbon2018.util.TestUtil;

import static ivankuo.com.itbon2018.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class RepoDaoTest {

    private GithubDb db;
    private Repo repo;

    @Before
    public void setUp() throws Exception {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                GithubDb.class).build();
        repo = TestUtil.createRepo("foo", "bar", "desc");
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    @Test
    public void insertAndLoad() throws InterruptedException {
        // Insert repo
        db.repoDao().insert(repo);
        // Query repo
        final Repo loaded = getValue(db.repoDao().load("foo", "bar"));
        // Assert query result
        assertThat(loaded.getOwner().getLogin(), is("foo"));
        assertThat(loaded.getName(), is("bar"));
    }
}