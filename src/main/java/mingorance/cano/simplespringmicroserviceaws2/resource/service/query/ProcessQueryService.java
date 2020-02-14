package mingorance.cano.simplespringmicroserviceaws2.resource.service.query;

import java.util.List;

import mingorance.cano.simplespringmicroserviceaws2.mapper.ProcessMapper;
import mingorance.cano.simplespringmicroserviceaws2.persistence.entity.Process;
import mingorance.cano.simplespringmicroserviceaws2.persistence.entity.Process_;
import mingorance.cano.simplespringmicroserviceaws2.persistence.repository.ProcessRepository;
import mingorance.cano.simplespringmicroserviceaws2.resource.criteria.ProcessCriteria;
import mingorance.cano.simplespringmicroserviceaws2.resource.dto.ProcessDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProcessQueryService extends QueryService<Process> {

    private final ProcessRepository processRepository;

    private final ProcessMapper processMapper;

    public ProcessQueryService(ProcessRepository processRepository, ProcessMapper processMapper) {
        this.processRepository = processRepository;
        this.processMapper = processMapper;
    }

    @Transactional(readOnly = true)
    public List<ProcessDTO> findByCriteria(ProcessCriteria criteria) {
        final Specification<Process> specification = createSpecification(criteria);
        return processMapper.toDto(processRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<ProcessDTO> findByCriteria(ProcessCriteria criteria, Pageable page) {
        final Specification<Process> specification = createSpecification(criteria);
        return processRepository.findAll(specification, page)
                .map(processMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(ProcessCriteria criteria) {
        final Specification<Process> specification = createSpecification(criteria);
        return processRepository.count(specification);
    }

    private Specification<Process> createSpecification(ProcessCriteria criteria) {
        Specification<Process> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Process_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Process_.name));
            }
            if (criteria.getStartingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartingDate(), Process_.startingDate));
            }
        }
        return specification;
    }
}
