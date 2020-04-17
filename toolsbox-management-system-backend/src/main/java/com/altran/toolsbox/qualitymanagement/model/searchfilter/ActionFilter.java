package com.altran.toolsbox.qualitymanagement.model.searchfilter;

import java.util.Set;

import com.altran.toolsbox.qualitymanagement.model.ActionStatus;
import com.altran.toolsbox.qualitymanagement.model.Origin;
import com.altran.toolsbox.qualitymanagement.model.Priority;
import com.altran.toolsbox.qualitymanagement.model.TypeAction;

/**
 * Represents Action Filter
 * 
 * @author Majdi.BEN.OTHMEN
 * @version 1.0
 */
public class ActionFilter {

	private Set<ActionStatus> status;
	private Set<Priority> priorities;
	private Set<TypeAction> types;
	private Set<Origin> origins;

	public Set<ActionStatus> getStatus() {
		return status;
	}

	public void setStatus(Set<ActionStatus> status) {
		this.status = status;
	}

	public Set<Priority> getPriorities() {
		return priorities;
	}

	public void setPriorities(Set<Priority> priorities) {
		this.priorities = priorities;
	}

	public Set<TypeAction> getTypes() {
		return types;
	}

	public void setTypes(Set<TypeAction> types) {
		this.types = types;
	}

	public Set<Origin> getOrigins() {
		return origins;
	}

	public void setOrigins(Set<Origin> origins) {
		this.origins = origins;
	}

}
