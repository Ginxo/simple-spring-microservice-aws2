package mingorance.cano.simplespringmicroserviceaws2.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import mingorance.cano.simplespringmicroserviceaws2.resource.criteria.ProcessCriteria;
import mingorance.cano.simplespringmicroserviceaws2.resource.dto.ProcessDTO;
import mingorance.cano.simplespringmicroserviceaws2.resource.errors.BadRequestAlertException;
import mingorance.cano.simplespringmicroserviceaws2.resource.util.HeaderUtil;
import mingorance.cano.simplespringmicroserviceaws2.resource.util.PaginationUtil;
import mingorance.cano.simplespringmicroserviceaws2.resource.util.ResponseUtil;
import mingorance.cano.simplespringmicroserviceaws2.service.ProcessService;
import mingorance.cano.simplespringmicroserviceaws2.resource.service.query.ProcessQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/processes")
public class ProcessResource {

    private final ProcessService processService;
    private final ProcessQueryService processQueryService;

    public ProcessResource(ProcessService processService, ProcessQueryService processQueryService) {
        this.processService = processService;
        this.processQueryService = processQueryService;
    }

    @PostMapping
    public ResponseEntity<ProcessDTO> create(@RequestBody ProcessDTO processDTO) throws URISyntaxException {
        if (processDTO.getId() != null) {
            throw new BadRequestAlertException("A new process cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessDTO result = processService.save(processDTO);
        return ResponseEntity.created(new URI("/api/processes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<ProcessDTO> update(@RequestBody ProcessDTO processDTO) throws URISyntaxException {
        if (processDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessDTO result = processService.save(processDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, processDTO.getId().toString()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<ProcessDTO>> getAll(ProcessCriteria criteria, Pageable pageable) {
        Page<ProcessDTO> page = processQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/processes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(ProcessCriteria criteria) {
        return ResponseEntity.ok().body(processQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessDTO> getById(@PathVariable Long id) {
        Optional<ProcessDTO> processDTO = processService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        processService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
