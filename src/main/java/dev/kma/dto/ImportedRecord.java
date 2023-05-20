package dev.kma.dto;

import lombok.Data;

import java.util.Date;

/**
 * imported excel data
 */
@Data
public class ImportedRecord {
    /**
     * id number
     */
    private String id;

    /**
     * Imported Date
     */
    private Date importedDate;

    /**
     * Actual Date
     */
    private Date actualDate;

    /**
     * amount
     */
    private String amount;

    /**
     * category enum
     */
    private short category;
}
