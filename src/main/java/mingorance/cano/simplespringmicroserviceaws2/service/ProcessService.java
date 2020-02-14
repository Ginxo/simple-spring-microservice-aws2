package mingorance.cano.simplespringmicroserviceaws2.service;

import java.util.Optional;

import mingorance.cano.simplespringmicroserviceaws2.resource.dto.ProcessDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessService {

    ProcessDTO save(ProcessDTO process);

    Page<ProcessDTO> findAll(Pageable pageable);

    Optional<ProcessDTO> findOne(Long id);

    void delete(Long id);
}
