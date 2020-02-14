package mingorance.cano.simplespringmicroserviceaws2.service.impl;

import java.util.Optional;

import mingorance.cano.simplespringmicroserviceaws2.mapper.ProcessMapper;
import mingorance.cano.simplespringmicroserviceaws2.persistence.repository.ProcessRepository;
import mingorance.cano.simplespringmicroserviceaws2.resource.dto.ProcessDTO;
import mingorance.cano.simplespringmicroserviceaws2.service.ProcessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;
    private final ProcessMapper processMapper;

    public ProcessServiceImpl(ProcessRepository processRepository, ProcessMapper processMapper) {
        this.processRepository = processRepository;
        this.processMapper = processMapper;
    }

    @Override
    public ProcessDTO save(ProcessDTO process) {
        return this.processMapper.toDto(this.processRepository.save(this.processMapper.toEntity(process)));
    }

    @Override
    public Page<ProcessDTO> findAll(Pageable pageable) {
        return this.processRepository.findAll(pageable)
                .map(this.processMapper::toDto);
    }

    @Override
    public Optional<ProcessDTO> findOne(Long id) {
        return this.processRepository.findById(id)
                .map(this.processMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        this.processRepository.deleteById(id);
    }
}
