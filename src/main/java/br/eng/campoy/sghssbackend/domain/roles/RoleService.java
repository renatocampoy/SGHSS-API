package br.eng.campoy.sghssbackend.domain.roles;

import br.eng.campoy.sghssbackend.domain.roles.dto.RoleDto;
import br.eng.campoy.sghssbackend.domain.roles.dto.RoleResponseDto;
import br.eng.campoy.sghssbackend.domain.roles.exception.RoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleResponseDto createRole(RoleDto dto) {
        if (roleRepository.findByName(dto.getName()).isPresent()) {
            throw new RoleException("Papel já existe.");
        }

        RoleEntity role = new RoleEntity();
        role.setName(dto.getName());

        return convertToDto(roleRepository.save(role));
    }

    public List<RoleResponseDto> listAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteRole(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleException("Papel não encontrado."));

        roleRepository.delete(role);
    }

    private RoleResponseDto convertToDto(RoleEntity entity) {
        return new RoleResponseDto(
                entity.getId(),
                entity.getName()
        );
    }
}