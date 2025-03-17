package br.eng.campoy.sghssbackend.domain.patients;

import br.eng.campoy.sghssbackend.domain.patients.ValueObject.Status;
import br.eng.campoy.sghssbackend.domain.patients.dto.PatientsDto;
import br.eng.campoy.sghssbackend.domain.users.UsersEntity;
import br.eng.campoy.sghssbackend.domain.users.UsersRepository;
import br.eng.campoy.sghssbackend.domain.users.dto.UsersPatientsDto;
import br.eng.campoy.sghssbackend.exceptions.PatientsException;
import br.eng.campoy.sghssbackend.types.Age;
import br.eng.campoy.sghssbackend.types.Cpf;
import br.eng.campoy.sghssbackend.types.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientsService {

    private final PatientsRepository patientsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public PatientsService(PatientsRepository patientsRepository, UsersRepository usersRepository) {
        this.patientsRepository = patientsRepository;
        this.usersRepository = usersRepository;
    }

    public UsersPatientsDto createPatient(PatientsDto dto) {
        UsersEntity user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new PatientsException("Usuário não encontrado para este paciente.", HttpStatus.NOT_FOUND));

        PatientsEntity patient = new PatientsEntity();
        patient.setName(dto.getName());
        patient.setCpf(new Cpf(dto.getCpf()));
        patient.setBirthDate(new Age(dto.getBirthDate()));
        patient.setAddress(dto.getAddress());
        patient.setNumber(dto.getNumber());
        patient.setCity(dto.getCity());
        patient.setState(dto.getState());
        patient.setCountry(dto.getCountry());
        patient.setEmail(new Email(dto.getEmail()));
        patient.setPhone(dto.getPhone());
        patient.setPhoneContact(dto.getPhoneContact());
        patient.setMobile(dto.getMobile());
        patient.setStatus(Status.valueOf(dto.getStatus()));
        patient.setUser(user);
        patient.setCreatedAt(String.valueOf(LocalDateTime.now()));
        patient.setUpdatedAt(String.valueOf(LocalDateTime.now()));

        PatientsEntity savedPatient = patientsRepository.save(patient);

        return new UsersPatientsDto(
                user.getId(),
                new Email(user.getEmail()),
                savedPatient.getName(),
                savedPatient.getBirthDate(),
                savedPatient.getCpf(),
                savedPatient.getAddress(),
                savedPatient.getNumber(),
                savedPatient.getCity(),
                savedPatient.getState(),
                savedPatient.getCountry(),
                savedPatient.getPhone(),
                savedPatient.getPhoneContact(),
                savedPatient.getMobile(),
                savedPatient.getStatus()
        );
    }

    public UsersPatientsDto updatePatient(Long id, PatientsDto dto) {
        PatientsEntity patient = patientsRepository.findById(id)
                .orElseThrow(() -> new PatientsException("Paciente não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        patient.setName(dto.getName());
        patient.setCpf(new Cpf(dto.getCpf()));
        patient.setBirthDate(new Age(dto.getBirthDate()));
        patient.setAddress(dto.getAddress());
        patient.setNumber(dto.getNumber());
        patient.setCity(dto.getCity());
        patient.setState(dto.getState());
        patient.setCountry(dto.getCountry());
        patient.setEmail(new Email(dto.getEmail()));
        patient.setPhone(dto.getPhone());
        patient.setPhoneContact(dto.getPhoneContact());
        patient.setMobile(dto.getMobile());
        patient.setStatus(Status.valueOf(dto.getStatus()));
        patient.setUpdatedAt(String.valueOf(LocalDateTime.now()));

        PatientsEntity updatedPatient = patientsRepository.save(patient);

        return new UsersPatientsDto(
                updatedPatient.getUser().getId(),
                new Email(updatedPatient.getUser().getEmail()),
                updatedPatient.getName(),
                updatedPatient.getBirthDate(),
                updatedPatient.getCpf(),
                updatedPatient.getAddress(),
                updatedPatient.getNumber(),
                updatedPatient.getCity(),
                updatedPatient.getState(),
                updatedPatient.getCountry(),
                updatedPatient.getPhone(),
                updatedPatient.getPhoneContact(),
                updatedPatient.getMobile(),
                updatedPatient.getStatus()
        );
    }

    public void deletePatient(Long id) {
        PatientsEntity patient = patientsRepository.findById(id)
                .orElseThrow(() -> new PatientsException("Paciente não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        patient.setStatus(Status.DESATIVADO);
        patient.setUpdatedAt(String.valueOf(LocalDateTime.now()));

        patientsRepository.save(patient);
    }

    public List<UsersPatientsDto> listPatients() {
        return patientsRepository.findAll().stream()
                .map(patient -> new UsersPatientsDto(
                        patient.getUser().getId(),
                        new Email(patient.getUser().getEmail()),
                        patient.getName(),
                        patient.getBirthDate(),
                        patient.getCpf(),
                        patient.getAddress(),
                        patient.getNumber(),
                        patient.getCity(),
                        patient.getState(),
                        patient.getCountry(),
                        patient.getPhone(),
                        patient.getPhoneContact(),
                        patient.getMobile(),
                        patient.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public UsersPatientsDto getPatientById(Long id) {
        PatientsEntity patient = patientsRepository.findById(id)
                .orElseThrow(() -> new PatientsException("Paciente não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        return new UsersPatientsDto(
                patient.getUser().getId(),
                new Email(patient.getUser().getEmail()),
                patient.getName(),
                patient.getBirthDate(),
                patient.getCpf(),
                patient.getAddress(),
                patient.getNumber(),
                patient.getCity(),
                patient.getState(),
                patient.getCountry(),
                patient.getPhone(),
                patient.getPhoneContact(),
                patient.getMobile(),
                patient.getStatus()
        );
    }
}