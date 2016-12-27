package edu.ucla.dt.studentweb.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import edu.ucla.dt.studentweb.dao.CountryDAO;
import edu.ucla.dt.studentweb.dao.domain.Country;

@Repository(value = "countryDAO")
public class CountryDAOImpl implements CountryDAO {
    //private static final Logger logger = Logger.getLogger(CountryDAOImpl.class);

    @Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
	public List<Country> getCountries() {
    	
    	List<Country> listCountry = this.sessionFactory.getCurrentSession().createQuery("from Country c where c.code <> :code and c.code <> :usa  order by c.code")
				.setParameter("code", "")
				.setParameter("usa", "US")
				.list();
    		
		return listCountry;
	}

	@SuppressWarnings("unchecked")
	public Country getCountry(String code) {
		List<Country> countries = sessionFactory.getCurrentSession().createQuery("from Country where code = :code")
			.setParameter("code", code)
			.list();
		return (CollectionUtils.isEmpty(countries) ? null : countries.get(0));
	}
}
