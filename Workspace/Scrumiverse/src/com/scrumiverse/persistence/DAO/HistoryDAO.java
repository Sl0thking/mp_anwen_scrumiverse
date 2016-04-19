package com.scrumiverse.persistence.DAO;

import com.scrumiverse.model.scrumFeatures.HistoryEntry;
/**
 * DAO Interface for Histories
 * @author Kevin Jolitz
 * @version 19.04.2016
 */
public interface HistoryDAO {
	public void saveHistoryEntry(HistoryEntry entry);

}
