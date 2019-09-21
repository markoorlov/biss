
package biss.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biss.entity.IpEntity;
import biss.external.response.Ip;
import biss.mapper.IpMapper;
import biss.repository.IpEntityRepository;

@Service
@Transactional
public class IpEntityService {

	private ExternalApiFetcher externalApiFetcher;
	private IpEntityRepository ipEntityRepository;
	private IpMapper ipMapper;

	public IpEntityService(ExternalApiFetcher externalApiFetcher, IpEntityRepository ipEntityRepository,
			IpMapper ipMapper) {
		this.externalApiFetcher = externalApiFetcher;
		this.ipEntityRepository = ipEntityRepository;
		this.ipMapper = ipMapper;
	}

	public List<IpEntity> findByIp(String ip) {
		return this.ipEntityRepository.findByIp(ip);
	}

	public void deleteByIp(String ip) {
		this.ipEntityRepository.deleteByIp(ip);
	}

	public void save(IpEntity ipEntity) {
		ipEntityRepository.save(ipEntity);
	}

	public void saveIpEntity(String ip) {
		Ip ipRaw = externalApiFetcher.getIp(ip);
		if (!ipRaw.isError()) {
			IpEntity ipEntity = ipMapper.toEntity(ipRaw);
			this.save(ipEntity);
		}
	}

	@Cacheable("getByParam")
	public List<Ip> getByParam(String ip) {

		return this.getFromDatabaseBy(ip).stream().map(ipMapper::toDto).collect(Collectors.toList());
	}

	private Collection<IpEntity> getFromDatabaseBy(String ip) {
		Optional<String> ipParam = Optional.ofNullable(ip);

		if (ipParam.isPresent()) {

			System.out.println("I'm here.");
			return ipEntityRepository.findByIp(ip);
		} else {

			return (Collection<IpEntity>) ipEntityRepository.findAll();
		}
	}
}