package br.eng.campoy.sghssbackend.domain.users;

import br.eng.campoy.sghssbackend.domain.users.ValueObject.Status;
import br.eng.campoy.sghssbackend.domain.users.dto.UsersDto;
import br.eng.campoy.sghssbackend.domain.users.dto.UsersResponseDto;
import br.eng.campoy.sghssbackend.domain.users.exception.UsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Users {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Users(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersResponseDto createUser(UsersDto dto) {
        if (usersRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UsersException("E-mail já está em uso.");
        }

        UsersEntity user = new UsersEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UsersEntity savedUser = usersRepository.save(user);

        return convertToDto(savedUser);
    }

    public UsersResponseDto updateUser(Long id, UsersDto dto) {
        UsersEntity user = usersRepository.findById(id)
                .orElseThrow(() -> new UsersException("Usuário não encontrado com ID: " + id));

        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setUpdatedAt(LocalDateTime.now());

        UsersEntity updatedUser = usersRepository.save(user);

        return convertToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        UsersEntity user = usersRepository.findById(id)
                .orElseThrow(() -> new UsersException("Usuário não encontrado com ID: " + id));

        user.setStatus(Status.INATIVO);
        usersRepository.save(user);
    }

    public Optional<UsersResponseDto> getUserById(Long id) {
        return usersRepository.findById(id).map(this::convertToDto);
    }

    public List<UsersResponseDto> listUsers() {
        return usersRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UsersResponseDto convertToDto(UsersEntity entity) {
        return new UsersResponseDto(
                entity.getId(),
                entity.getEmail(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}