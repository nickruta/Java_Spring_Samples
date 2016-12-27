package edu.ucla.dt.studentweb.svc.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.ucla.dt.studentweb.dao.CountryDAO;
import edu.ucla.dt.studentweb.dao.ForeignSchoolsDAO;
import edu.ucla.dt.studentweb.dao.GradSchoolLocationDAO;
import edu.ucla.dt.studentweb.dao.ProvinceDAO;
import edu.ucla.dt.studentweb.dao.GradSchoolDAO;
import edu.ucla.dt.studentweb.dao.StateDAO;
import edu.ucla.dt.studentweb.dao.domain.Country;
import edu.ucla.dt.studentweb.dao.domain.Province;
import edu.ucla.dt.studentweb.dao.domain.State;
import edu.ucla.dt.studentweb.dao.domain.admission.ForeignSchool;
import edu.ucla.dt.studentweb.dao.domain.admission.GradSchool;
import edu.ucla.dt.studentweb.dao.domain.admission.GradSchoolLocation;
import edu.ucla.dt.studentweb.svc.HelperService;
import edu.ucla.dt.studentweb.svc.domain.Option;

@Service("helperService")
public class HelperServiceImpl implements HelperService {
//	private static final Logger logger = Logger.getLogger(HelperServiceImpl.class);
	
	@Autowired
	private StateDAO stateDAO;
	
	@Autowired
	private CountryDAO countryDAO;
	
	@Autowired
	private ProvinceDAO provinceDAO;
	

	@Autowired
	private GradSchoolDAO schoolsDAO;
	
	
	@Autowired
	private GradSchoolLocationDAO gradSchoolDAO;
	
	@Autowired
	private ForeignSchoolsDAO foreignSchoolsDAO;

	@Override
	public List<Country> getCountries() {
		List<Country> countries = countryDAO.getCountries();
		Country topOfList = new Country();
		topOfList.setId(211);
		topOfList.setCode("US");
		topOfList.setName("USA");
		topOfList.setLocationCode("3");
		countries.add(0, topOfList);										
		return countries;
	}

	@Override
	public Country getCountry(String code) {
		return countryDAO.getCountry(code);
	}

	@Override
	public List<Option> getStates() {
		List<State> states = stateDAO.getStates();
		List<Option> options = new ArrayList<Option>();
		for (State state : states) {
			options.add(new Option(state.getCode(), state.getName()));
		}
		return options;
	}
	
	@Override
	public List<Option> getStandaloneStates() {
		List<State> states = stateDAO.getStates();
		
		List<Option> options = new ArrayList<Option>();
		for (State state : states) {
			options.add(new Option(state.getCode(), state.getName()));

			if (!StringUtils.hasText(state.getCode())) {
				options.add(foreignCountry);
			}
		}
		return options;
	}

	@Override
	public List<Option> getProvinces() {
		List<Province> provinces = provinceDAO.selectAllProvinces();;
		List<Option> options = new ArrayList<Option>();
		for (Province province : provinces) {
			options.add(new Option(province.getProvinceCode(), province.getProvinceName()));
		}
		return options;
	}

	@Override
	public String getStateProvinceName(String country, String code) {
		String name = null;
		if (COUNTRY_USA.equalsIgnoreCase(country)) {
			State state = stateDAO.getState(code);
			name = (state != null) ? state.getName() : null;
		}
		else if (COUNTRY_CANADA.equalsIgnoreCase(country)) {
			Province province = provinceDAO.getProvince(code);
			name = (province != null) ? province.getProvinceName() : null;
		}

		return name;
	}

	@Override
	public List<GradSchool> getStatesByCountries(String country) {
		return schoolsDAO.getStateByCountry("US");
	}
	
	@Override
	public List<GradSchool> getSchoolsByState(String state) {
		return schoolsDAO.getSchoolByState(state);
	}

	@Override
	public List<GradSchoolLocation> getGradSchool() {
		return gradSchoolDAO.getGradSchoolLocations();
	}

	@Override
	public List<ForeignSchool> getForeignSchoolsByCountry(String country) {
		return foreignSchoolsDAO.getForeignSchoolByLocationID(country);
	}

}
