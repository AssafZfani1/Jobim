package zfani.assaf.jobim.interfaces;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import zfani.assaf.jobim.models.Job;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface JobDao {

    @Query("Select * from job_table order by distance asc")
    LiveData<List<Job>> getAllJobs();

    @RawQuery
    List<Job> getAllJobs(SupportSQLiteQuery query);

    @Insert(onConflict = REPLACE)
    void insertJob(Job job);

    @Query("Delete from job_table where id = :jobId")
    void deleteJob(int jobId);

    @Query("Select * from job_table where type = :jobType limit 1")
    Job getJobByJobType(String jobType);
}
