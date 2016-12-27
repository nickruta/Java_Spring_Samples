package edu.ucla.dt.studentweb.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RaceEthnicityValues")
public class EthnicityGroup implements Serializable, Comparable<EthnicityGroup>  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3844682176887399681L;

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "RaceEthnicCD")
	private String code;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "GroupCD")
	private String group;

	public EthnicityGroup() {}
	
	public EthnicityGroup(String code) {
		super();
		this.code = code;
	}

	public int compareTo(EthnicityGroup group) {
	if (group != null) {
	    return getDescription().compareTo(group.getDescription());
	}
	return 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EthnicityGroup [id=").append(id).append(", code=")
				.append(code).append(", description=").append(description)
				.append(", group=").append(group).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return ((code == null) ? 0 : code.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EthnicityGroup other = (EthnicityGroup) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;

		return true;
	}
		
}
