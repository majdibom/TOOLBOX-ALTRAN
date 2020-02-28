package com.altran.toolsbox.qualitymanagement.model.searchfilter;

import java.util.Set;

import com.altran.toolsbox.qualitymanagement.model.Exposure;
import com.altran.toolsbox.qualitymanagement.model.Probability;
import com.altran.toolsbox.qualitymanagement.model.RiskNature;
import com.altran.toolsbox.qualitymanagement.model.RiskStatus;

public class RiskFilter {

	private Set<Probability> probabilities;

	private Set<RiskNature> riskNatures;

	private Set<Exposure> exposures;

	private Set<RiskStatus> status;

	public Set<Probability> getProbabilities() {
		return probabilities;
	}

	public void setProbabilities(Set<Probability> probabilities) {
		this.probabilities = probabilities;
	}

	public Set<RiskNature> getRiskNatures() {
		return riskNatures;
	}

	public void setRiskNatures(Set<RiskNature> riskNatures) {
		this.riskNatures = riskNatures;
	}

	public Set<Exposure> getExposures() {
		return exposures;
	}

	public void setExposures(Set<Exposure> exposures) {
		this.exposures = exposures;
	}

	public Set<RiskStatus> getStatus() {
		return status;
	}

	public void setStatus(Set<RiskStatus> status) {
		this.status = status;
	}

}
