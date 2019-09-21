package biss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ipapi")
public class IpEntity {

	@Id
	@Column(name= "ip")
	private String ip;

	@Column(name= "country_name")
	private String countryName;
	
	public IpEntity() {}
	
	public IpEntity(String ip, String countryName) {
		this.ip = ip;
		this.countryName = countryName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
