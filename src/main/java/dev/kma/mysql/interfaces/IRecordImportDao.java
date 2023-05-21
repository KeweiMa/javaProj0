package dev.kma.mysql.interfaces;

import dev.kma.dto.ImportedRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;
public interface IRecordImportDao {

    void insertBatchData(@Param("importRecord") List<ImportedRecord> importedRecordList);
    void insert(ImportedRecord importedRecord);

    List<ImportedRecord> selectAll();

}