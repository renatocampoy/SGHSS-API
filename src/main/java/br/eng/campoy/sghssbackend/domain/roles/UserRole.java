package br.eng.campoy.sghssbackend.domain.roles;

import br.eng.campoy.sghssbackend.domain.roles.dto.AssignRoleDto;
import br.eng.campoy.sghssbackend.domain.users.UsersEntity;
import br.eng.campoy.sghssbackend.domain.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRole {

    private final UserRoleRepository userRoleRepository;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserRole(UserRoleRepository userRoleRepository, UsersRepository usersRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }

    public void assignRoleToUser(AssignRoleDto dto) {
        UsersEntity user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        RoleEntity role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Papel não encontrado."));

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUser(user);
        userRole.setRole(role);

        userRoleRepository.save(userRole);
    }
}