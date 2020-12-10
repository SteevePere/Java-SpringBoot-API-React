package com.quest.etna.controllers;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.etna.model.dtos.metrics.ReadMetricsDTO;
import com.quest.etna.services.metrics.MetricsService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RequestMapping("/metrics")
@RestController
@Tag(name = "Metrics", description = "Metrics-related Endpoints.")
public class MetricsController
{
	
	@Autowired
	private MetricsService _metricsService;
	
	
	@GetMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ReadMetricsDTO getMetrics() throws Exception
	{
		return _metricsService.getMetricsObject();
	}
	
	
	@GetMapping("/export")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<InputStreamResource> getMetricsDownload(HttpServletResponse response)
		throws JsonProcessingException
	{
		ReadMetricsDTO metrics = _metricsService.getMetricsObject();
		ObjectMapper mapper = new ObjectMapper();
		
		String fileName = "metrics.json";
		
		response.addHeader("Content-disposition", "attachment;filename=" + fileName);
		response.setContentType("application/octet-stream");
		
		byte[] buf = mapper.writeValueAsBytes(metrics);

		return ResponseEntity
			.ok()
			.contentLength(buf.length)
			.contentType(
				MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(new ByteArrayInputStream(buf)));
	}
}