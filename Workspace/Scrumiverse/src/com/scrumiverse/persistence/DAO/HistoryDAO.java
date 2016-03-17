package com.scrumiverse.persistence.DAO;

import com.scrumiverse.model.scrumFeatures.HistoryEntry;

public interface HistoryDAO {
	public void saveHistoryEntry(HistoryEntry entry);

}
