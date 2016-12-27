package edu.ucla.dt.studentweb.svc.dto;

import java.util.Set;
import java.util.TreeSet;

import edu.ucla.dt.studentweb.dao.domain.EthnicityGroup;

public class RaceEthnicityData {
	private Boolean isUpdatable;
	
	private Boolean hispanicLatino;
	private Boolean africanAmerican;
	private Boolean americanIndian;
	private Boolean asian;
	private Boolean nativeHawaiian;
	private Boolean whiteCaucasian;
	
	private Set<EthnicityGroup> ethnicGroups;
	
	public RaceEthnicityData() {
		ethnicGroups = new TreeSet<EthnicityGroup>();
	}

	public Boolean getIsUpdatable() {
		return isUpdatable;
	}

	public void setIsUpdatable(Boolean isUpdatable) {
		this.isUpdatable = isUpdatable;
	}

	public Boolean getHispanicLatino() {
		return hispanicLatino;
	}

	public void setHispanicLatino(Boolean hispanicLatino) {
		this.hispanicLatino = hispanicLatino;
	}

	public Boolean getAfricanAmerican() {
		return africanAmerican;
	}

	public void setAfricanAmerican(Boolean africanAmerican) {
		this.africanAmerican = africanAmerican;
	}

	public Boolean getAmericanIndian() {
		return americanIndian;
	}

	public void setAmericanIndian(Boolean americanIndian) {
		this.americanIndian = americanIndian;
	}

	public Boolean getAsian() {
		return asian;
	}

	public void setAsian(Boolean asian) {
		this.asian = asian;
	}

	public Boolean getNativeHawaiian() {
		return nativeHawaiian;
	}

	public void setNativeHawaiian(Boolean nativeHawaiian) {
		this.nativeHawaiian = nativeHawaiian;
	}

	public Boolean getWhiteCaucasian() {
		return whiteCaucasian;
	}

	public void setWhiteCaucasian(Boolean whiteCaucasian) {
		this.whiteCaucasian = whiteCaucasian;
	}

	public Set<EthnicityGroup> getEthnicGroups() {
		return ethnicGroups;
	}

	public void setEthnicGroups(Set<EthnicityGroup> ethnicGroups) {
		this.ethnicGroups = ethnicGroups;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RaceEthnicityData [isUpdatable=").append(isUpdatable)
				.append(", hispanicLatino=").append(hispanicLatino)
				.append(", africanAmerican=").append(africanAmerican)
				.append(", americanIndian=").append(americanIndian)
				.append(", asian=").append(asian).append(", nativeHawaiian=")
				.append(nativeHawaiian).append(", whiteCaucasian=")
				.append(whiteCaucasian).append(", ethnicGroups=")
				.append(ethnicGroups).append("]");
		return builder.toString();
	}
	
}
