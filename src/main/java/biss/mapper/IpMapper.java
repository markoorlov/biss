package biss.mapper;

import org.springframework.stereotype.Service;

import biss.entity.IpEntity;
import biss.external.response.Ip;

@Service
public class IpMapper {

	// From Entity to DTO
	public Ip toDto(IpEntity entity) {
		Ip dto = new Ip();
		dto.setIp(entity.getIp());
		dto.setCountryName(entity.getCountryName());

		return dto;
	}

	// From DTO to Entity
	public IpEntity toEntity(Ip dto) {

		return new IpEntity(dto.getIp(), dto.getCountryName());
	}
}
