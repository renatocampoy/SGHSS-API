package br.eng.campoy.sghssbackend.domain.permissions;

import br.eng.campoy.sghssbackend.domain.permissions.dto.PermissionDto;
import br.eng.campoy.sghssbackend.domain.permissions.dto.PermissionResponseDto;
import br.eng.campoy.sghssbackend.domain.permissions.exception.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Permission {

    private final PermissionRepository permissionRepository;

    @Autowired
    public Permission(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public PermissionResponseDto createPermission(PermissionDto dto) {
        if (permissionRepository.findByName(dto.getName()).isPresent()) {
            throw new PermissionException("Permissão já existe.");
        }

        PermissionEntity permission = new PermissionEntity();
        permission.setName(dto.getName());

        return convertToDto(permissionRepository.save(permission));
    }

    public List<PermissionResponseDto> listAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PermissionResponseDto convertToDto(PermissionEntity entity) {
        return new PermissionResponseDto(
                entity.getId(),
                entity.getName()
        );
    }

    public void deletePermission(Long id) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionException("Permissão não encontrada."));

        permissionRepository.delete(permission);
    }
}