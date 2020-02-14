package mingorance.cano.simplespringmicroserviceaws2.mapper;

import mingorance.cano.simplespringmicroserviceaws2.persistence.entity.Process;
import mingorance.cano.simplespringmicroserviceaws2.resource.dto.ProcessDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProcessMapper extends EntityMapper<ProcessDTO, Process> {

    default Process fromId(Long id) {
        if (id == null) {
            return null;
        }
        Process process = new Process();
        process.setId(id);
        return process;
    }
}
