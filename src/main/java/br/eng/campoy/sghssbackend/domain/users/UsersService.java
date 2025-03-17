package br.eng.campoy.sghssbackend.domain.users;

import br.eng.campoy.sghssbackend.domain.users.ValueObject.Status;
import br.eng.campoy.sghssbackend.domain.users.dto.UsersDto;
import br.eng.campoy.sghssbackend.domain.users.exception.UsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersEntity createUser(UsersDto dto) {
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

        return usersRepository.save(user);
    }

    public UsersEntity updateUser(Long id, UsersDto dto) {
        UsersEntity user = usersRepository.findById(id)
                .orElseThrow(() -> new br.eng.campoy.sghssbackend.domain.users.exception.UsersException("Usuário não encontrado com ID: " + id));

        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setUpdatedAt(LocalDateTime.now());

        return usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        UsersEntity user = usersRepository.findById(id)
                .orElseThrow(() -> new UsersException("Usuário não encontrado com ID: " + id));

        user.setStatus(Status.INATIVO);
        usersRepository.save(user);
    }

    public Optional<UsersEntity> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public List<UsersEntity> listUsers() {
        return usersRepository.findAll();
    }
}