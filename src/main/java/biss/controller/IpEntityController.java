package biss.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import biss.entity.IpEntity;
import biss.external.response.Ip;
import biss.service.IpEntityService;

@RestController
public class IpEntityController {

	private IpEntityService ipEntityService;

	private AtomicInteger successfulCnt = new AtomicInteger(0);
	private AtomicInteger unsuccessfulCnt = new AtomicInteger(0);

	public IpEntityController(IpEntityService ipEntityService) {
		this.ipEntityService = ipEntityService;
	}

	@GetMapping("/country")
	public List<Ip> getByIp(@RequestParam(required = true) String ip) {

		ipEntityService.saveIpEntity(ip);

		List<Ip> IPs = ipEntityService.getByParam(ip);
		boolean isIPv4 = isIPv4Address(ip);
		boolean isFound = !IPs.isEmpty();

		switch (isIPv4 + "-" + isFound) {

		case "true-true":
			successfulCnt.incrementAndGet();
			return IPs;
		case "true-false":
			unsuccessfulCnt.incrementAndGet();
			throw new ResourceNotFoundException();
		default:
			unsuccessfulCnt.incrementAndGet();
			throw new IllegalArgumentException("Given parameter is not valid IPv4 address.");
		}
	}

	@DeleteMapping("/ip/{ip}")
	public ResponseEntity<String> deleteById(@PathVariable String ip) {
		List<IpEntity> entity = ipEntityService.findByIp(ip);
		if (!entity.isEmpty()) {
			String entityIp = entity.get(0).getIp();
			ipEntityService.deleteByIp(entityIp);
			return new ResponseEntity<>("Deleted IP: " + entityIp, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/requests/successful")
	public String numberOfSuccessfulRequests() {

		return "Number successful requests: " + successfulCnt;
	}

	@GetMapping("/requests/unsuccessful")
	public String numberOfunsuccessfulRequests() {

		return "Number unsuccessful requests: " + unsuccessfulCnt;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class ResourceNotFoundException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	public static Boolean isIPv4Address(String address) {
		if (address.isEmpty()) {
			return false;
		}
		try {
			Object res = InetAddress.getByName(address);
			return res instanceof Inet4Address;
		} catch (final UnknownHostException ex) {
			return false;
		}
	}
}