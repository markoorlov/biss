package biss.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import biss.entity.IpEntity;


@Repository
public interface IpEntityRepository extends CrudRepository<IpEntity, Long> {
	
	List<IpEntity> findByIp(String ip);

	List<IpEntity> deleteByIp(String ip);
}